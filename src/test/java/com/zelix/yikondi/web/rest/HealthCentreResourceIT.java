package com.zelix.yikondi.web.rest;

import com.zelix.yikondi.YikondiApp;
import com.zelix.yikondi.domain.HealthCentre;
import com.zelix.yikondi.repository.HealthCentreRepository;
import com.zelix.yikondi.repository.search.HealthCentreSearchRepository;
import com.zelix.yikondi.service.HealthCentreService;
import com.zelix.yikondi.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static com.zelix.yikondi.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link HealthCentreResource} REST controller.
 */
@SpringBootTest(classes = YikondiApp.class)
public class HealthCentreResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final byte[] DEFAULT_LOGO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_LOGO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_LOGO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_LOGO_CONTENT_TYPE = "image/png";

    @Autowired
    private HealthCentreRepository healthCentreRepository;

    @Autowired
    private HealthCentreService healthCentreService;

    /**
     * This repository is mocked in the com.zelix.yikondi.repository.search test package.
     *
     * @see com.zelix.yikondi.repository.search.HealthCentreSearchRepositoryMockConfiguration
     */
    @Autowired
    private HealthCentreSearchRepository mockHealthCentreSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restHealthCentreMockMvc;

    private HealthCentre healthCentre;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HealthCentreResource healthCentreResource = new HealthCentreResource(healthCentreService);
        this.restHealthCentreMockMvc = MockMvcBuilders.standaloneSetup(healthCentreResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HealthCentre createEntity(EntityManager em) {
        HealthCentre healthCentre = new HealthCentre()
            .name(DEFAULT_NAME)
            .logo(DEFAULT_LOGO)
            .logoContentType(DEFAULT_LOGO_CONTENT_TYPE);
        return healthCentre;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HealthCentre createUpdatedEntity(EntityManager em) {
        HealthCentre healthCentre = new HealthCentre()
            .name(UPDATED_NAME)
            .logo(UPDATED_LOGO)
            .logoContentType(UPDATED_LOGO_CONTENT_TYPE);
        return healthCentre;
    }

    @BeforeEach
    public void initTest() {
        healthCentre = createEntity(em);
    }

    @Test
    @Transactional
    public void createHealthCentre() throws Exception {
        int databaseSizeBeforeCreate = healthCentreRepository.findAll().size();

        // Create the HealthCentre
        restHealthCentreMockMvc.perform(post("/api/health-centres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(healthCentre)))
            .andExpect(status().isCreated());

        // Validate the HealthCentre in the database
        List<HealthCentre> healthCentreList = healthCentreRepository.findAll();
        assertThat(healthCentreList).hasSize(databaseSizeBeforeCreate + 1);
        HealthCentre testHealthCentre = healthCentreList.get(healthCentreList.size() - 1);
        assertThat(testHealthCentre.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testHealthCentre.getLogo()).isEqualTo(DEFAULT_LOGO);
        assertThat(testHealthCentre.getLogoContentType()).isEqualTo(DEFAULT_LOGO_CONTENT_TYPE);

        // Validate the HealthCentre in Elasticsearch
        verify(mockHealthCentreSearchRepository, times(1)).save(testHealthCentre);
    }

    @Test
    @Transactional
    public void createHealthCentreWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = healthCentreRepository.findAll().size();

        // Create the HealthCentre with an existing ID
        healthCentre.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHealthCentreMockMvc.perform(post("/api/health-centres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(healthCentre)))
            .andExpect(status().isBadRequest());

        // Validate the HealthCentre in the database
        List<HealthCentre> healthCentreList = healthCentreRepository.findAll();
        assertThat(healthCentreList).hasSize(databaseSizeBeforeCreate);

        // Validate the HealthCentre in Elasticsearch
        verify(mockHealthCentreSearchRepository, times(0)).save(healthCentre);
    }


    @Test
    @Transactional
    public void getAllHealthCentres() throws Exception {
        // Initialize the database
        healthCentreRepository.saveAndFlush(healthCentre);

        // Get all the healthCentreList
        restHealthCentreMockMvc.perform(get("/api/health-centres?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(healthCentre.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].logoContentType").value(hasItem(DEFAULT_LOGO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].logo").value(hasItem(Base64Utils.encodeToString(DEFAULT_LOGO))));
    }
    
    @Test
    @Transactional
    public void getHealthCentre() throws Exception {
        // Initialize the database
        healthCentreRepository.saveAndFlush(healthCentre);

        // Get the healthCentre
        restHealthCentreMockMvc.perform(get("/api/health-centres/{id}", healthCentre.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(healthCentre.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.logoContentType").value(DEFAULT_LOGO_CONTENT_TYPE))
            .andExpect(jsonPath("$.logo").value(Base64Utils.encodeToString(DEFAULT_LOGO)));
    }

    @Test
    @Transactional
    public void getNonExistingHealthCentre() throws Exception {
        // Get the healthCentre
        restHealthCentreMockMvc.perform(get("/api/health-centres/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHealthCentre() throws Exception {
        // Initialize the database
        healthCentreService.save(healthCentre);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockHealthCentreSearchRepository);

        int databaseSizeBeforeUpdate = healthCentreRepository.findAll().size();

        // Update the healthCentre
        HealthCentre updatedHealthCentre = healthCentreRepository.findById(healthCentre.getId()).get();
        // Disconnect from session so that the updates on updatedHealthCentre are not directly saved in db
        em.detach(updatedHealthCentre);
        updatedHealthCentre
            .name(UPDATED_NAME)
            .logo(UPDATED_LOGO)
            .logoContentType(UPDATED_LOGO_CONTENT_TYPE);

        restHealthCentreMockMvc.perform(put("/api/health-centres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedHealthCentre)))
            .andExpect(status().isOk());

        // Validate the HealthCentre in the database
        List<HealthCentre> healthCentreList = healthCentreRepository.findAll();
        assertThat(healthCentreList).hasSize(databaseSizeBeforeUpdate);
        HealthCentre testHealthCentre = healthCentreList.get(healthCentreList.size() - 1);
        assertThat(testHealthCentre.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testHealthCentre.getLogo()).isEqualTo(UPDATED_LOGO);
        assertThat(testHealthCentre.getLogoContentType()).isEqualTo(UPDATED_LOGO_CONTENT_TYPE);

        // Validate the HealthCentre in Elasticsearch
        verify(mockHealthCentreSearchRepository, times(1)).save(testHealthCentre);
    }

    @Test
    @Transactional
    public void updateNonExistingHealthCentre() throws Exception {
        int databaseSizeBeforeUpdate = healthCentreRepository.findAll().size();

        // Create the HealthCentre

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHealthCentreMockMvc.perform(put("/api/health-centres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(healthCentre)))
            .andExpect(status().isBadRequest());

        // Validate the HealthCentre in the database
        List<HealthCentre> healthCentreList = healthCentreRepository.findAll();
        assertThat(healthCentreList).hasSize(databaseSizeBeforeUpdate);

        // Validate the HealthCentre in Elasticsearch
        verify(mockHealthCentreSearchRepository, times(0)).save(healthCentre);
    }

    @Test
    @Transactional
    public void deleteHealthCentre() throws Exception {
        // Initialize the database
        healthCentreService.save(healthCentre);

        int databaseSizeBeforeDelete = healthCentreRepository.findAll().size();

        // Delete the healthCentre
        restHealthCentreMockMvc.perform(delete("/api/health-centres/{id}", healthCentre.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<HealthCentre> healthCentreList = healthCentreRepository.findAll();
        assertThat(healthCentreList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the HealthCentre in Elasticsearch
        verify(mockHealthCentreSearchRepository, times(1)).deleteById(healthCentre.getId());
    }

    @Test
    @Transactional
    public void searchHealthCentre() throws Exception {
        // Initialize the database
        healthCentreService.save(healthCentre);
        when(mockHealthCentreSearchRepository.search(queryStringQuery("id:" + healthCentre.getId())))
            .thenReturn(Collections.singletonList(healthCentre));
        // Search the healthCentre
        restHealthCentreMockMvc.perform(get("/api/_search/health-centres?query=id:" + healthCentre.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(healthCentre.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].logoContentType").value(hasItem(DEFAULT_LOGO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].logo").value(hasItem(Base64Utils.encodeToString(DEFAULT_LOGO))));
    }
}
