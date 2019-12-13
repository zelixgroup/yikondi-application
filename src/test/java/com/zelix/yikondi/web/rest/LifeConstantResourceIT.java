package com.zelix.yikondi.web.rest;

import com.zelix.yikondi.YikondiApp;
import com.zelix.yikondi.domain.LifeConstant;
import com.zelix.yikondi.repository.LifeConstantRepository;
import com.zelix.yikondi.repository.search.LifeConstantSearchRepository;
import com.zelix.yikondi.service.LifeConstantService;
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
 * Integration tests for the {@link LifeConstantResource} REST controller.
 */
@SpringBootTest(classes = YikondiApp.class)
public class LifeConstantResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private LifeConstantRepository lifeConstantRepository;

    @Autowired
    private LifeConstantService lifeConstantService;

    /**
     * This repository is mocked in the com.zelix.yikondi.repository.search test package.
     *
     * @see com.zelix.yikondi.repository.search.LifeConstantSearchRepositoryMockConfiguration
     */
    @Autowired
    private LifeConstantSearchRepository mockLifeConstantSearchRepository;

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

    private MockMvc restLifeConstantMockMvc;

    private LifeConstant lifeConstant;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LifeConstantResource lifeConstantResource = new LifeConstantResource(lifeConstantService);
        this.restLifeConstantMockMvc = MockMvcBuilders.standaloneSetup(lifeConstantResource)
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
    public static LifeConstant createEntity(EntityManager em) {
        LifeConstant lifeConstant = new LifeConstant()
            .name(DEFAULT_NAME);
        return lifeConstant;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LifeConstant createUpdatedEntity(EntityManager em) {
        LifeConstant lifeConstant = new LifeConstant()
            .name(UPDATED_NAME);
        return lifeConstant;
    }

    @BeforeEach
    public void initTest() {
        lifeConstant = createEntity(em);
    }

    @Test
    @Transactional
    public void createLifeConstant() throws Exception {
        int databaseSizeBeforeCreate = lifeConstantRepository.findAll().size();

        // Create the LifeConstant
        restLifeConstantMockMvc.perform(post("/api/life-constants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lifeConstant)))
            .andExpect(status().isCreated());

        // Validate the LifeConstant in the database
        List<LifeConstant> lifeConstantList = lifeConstantRepository.findAll();
        assertThat(lifeConstantList).hasSize(databaseSizeBeforeCreate + 1);
        LifeConstant testLifeConstant = lifeConstantList.get(lifeConstantList.size() - 1);
        assertThat(testLifeConstant.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the LifeConstant in Elasticsearch
        verify(mockLifeConstantSearchRepository, times(1)).save(testLifeConstant);
    }

    @Test
    @Transactional
    public void createLifeConstantWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lifeConstantRepository.findAll().size();

        // Create the LifeConstant with an existing ID
        lifeConstant.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLifeConstantMockMvc.perform(post("/api/life-constants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lifeConstant)))
            .andExpect(status().isBadRequest());

        // Validate the LifeConstant in the database
        List<LifeConstant> lifeConstantList = lifeConstantRepository.findAll();
        assertThat(lifeConstantList).hasSize(databaseSizeBeforeCreate);

        // Validate the LifeConstant in Elasticsearch
        verify(mockLifeConstantSearchRepository, times(0)).save(lifeConstant);
    }


    @Test
    @Transactional
    public void getAllLifeConstants() throws Exception {
        // Initialize the database
        lifeConstantRepository.saveAndFlush(lifeConstant);

        // Get all the lifeConstantList
        restLifeConstantMockMvc.perform(get("/api/life-constants?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lifeConstant.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getLifeConstant() throws Exception {
        // Initialize the database
        lifeConstantRepository.saveAndFlush(lifeConstant);

        // Get the lifeConstant
        restLifeConstantMockMvc.perform(get("/api/life-constants/{id}", lifeConstant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(lifeConstant.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    public void getNonExistingLifeConstant() throws Exception {
        // Get the lifeConstant
        restLifeConstantMockMvc.perform(get("/api/life-constants/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLifeConstant() throws Exception {
        // Initialize the database
        lifeConstantService.save(lifeConstant);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockLifeConstantSearchRepository);

        int databaseSizeBeforeUpdate = lifeConstantRepository.findAll().size();

        // Update the lifeConstant
        LifeConstant updatedLifeConstant = lifeConstantRepository.findById(lifeConstant.getId()).get();
        // Disconnect from session so that the updates on updatedLifeConstant are not directly saved in db
        em.detach(updatedLifeConstant);
        updatedLifeConstant
            .name(UPDATED_NAME);

        restLifeConstantMockMvc.perform(put("/api/life-constants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLifeConstant)))
            .andExpect(status().isOk());

        // Validate the LifeConstant in the database
        List<LifeConstant> lifeConstantList = lifeConstantRepository.findAll();
        assertThat(lifeConstantList).hasSize(databaseSizeBeforeUpdate);
        LifeConstant testLifeConstant = lifeConstantList.get(lifeConstantList.size() - 1);
        assertThat(testLifeConstant.getName()).isEqualTo(UPDATED_NAME);

        // Validate the LifeConstant in Elasticsearch
        verify(mockLifeConstantSearchRepository, times(1)).save(testLifeConstant);
    }

    @Test
    @Transactional
    public void updateNonExistingLifeConstant() throws Exception {
        int databaseSizeBeforeUpdate = lifeConstantRepository.findAll().size();

        // Create the LifeConstant

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLifeConstantMockMvc.perform(put("/api/life-constants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lifeConstant)))
            .andExpect(status().isBadRequest());

        // Validate the LifeConstant in the database
        List<LifeConstant> lifeConstantList = lifeConstantRepository.findAll();
        assertThat(lifeConstantList).hasSize(databaseSizeBeforeUpdate);

        // Validate the LifeConstant in Elasticsearch
        verify(mockLifeConstantSearchRepository, times(0)).save(lifeConstant);
    }

    @Test
    @Transactional
    public void deleteLifeConstant() throws Exception {
        // Initialize the database
        lifeConstantService.save(lifeConstant);

        int databaseSizeBeforeDelete = lifeConstantRepository.findAll().size();

        // Delete the lifeConstant
        restLifeConstantMockMvc.perform(delete("/api/life-constants/{id}", lifeConstant.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LifeConstant> lifeConstantList = lifeConstantRepository.findAll();
        assertThat(lifeConstantList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the LifeConstant in Elasticsearch
        verify(mockLifeConstantSearchRepository, times(1)).deleteById(lifeConstant.getId());
    }

    @Test
    @Transactional
    public void searchLifeConstant() throws Exception {
        // Initialize the database
        lifeConstantService.save(lifeConstant);
        when(mockLifeConstantSearchRepository.search(queryStringQuery("id:" + lifeConstant.getId())))
            .thenReturn(Collections.singletonList(lifeConstant));
        // Search the lifeConstant
        restLifeConstantMockMvc.perform(get("/api/_search/life-constants?query=id:" + lifeConstant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lifeConstant.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
}
