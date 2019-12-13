package com.zelix.yikondi.web.rest;

import com.zelix.yikondi.YikondiApp;
import com.zelix.yikondi.domain.LifeConstantUnit;
import com.zelix.yikondi.repository.LifeConstantUnitRepository;
import com.zelix.yikondi.repository.search.LifeConstantUnitSearchRepository;
import com.zelix.yikondi.service.LifeConstantUnitService;
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
 * Integration tests for the {@link LifeConstantUnitResource} REST controller.
 */
@SpringBootTest(classes = YikondiApp.class)
public class LifeConstantUnitResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private LifeConstantUnitRepository lifeConstantUnitRepository;

    @Autowired
    private LifeConstantUnitService lifeConstantUnitService;

    /**
     * This repository is mocked in the com.zelix.yikondi.repository.search test package.
     *
     * @see com.zelix.yikondi.repository.search.LifeConstantUnitSearchRepositoryMockConfiguration
     */
    @Autowired
    private LifeConstantUnitSearchRepository mockLifeConstantUnitSearchRepository;

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

    private MockMvc restLifeConstantUnitMockMvc;

    private LifeConstantUnit lifeConstantUnit;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LifeConstantUnitResource lifeConstantUnitResource = new LifeConstantUnitResource(lifeConstantUnitService);
        this.restLifeConstantUnitMockMvc = MockMvcBuilders.standaloneSetup(lifeConstantUnitResource)
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
    public static LifeConstantUnit createEntity(EntityManager em) {
        LifeConstantUnit lifeConstantUnit = new LifeConstantUnit()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return lifeConstantUnit;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LifeConstantUnit createUpdatedEntity(EntityManager em) {
        LifeConstantUnit lifeConstantUnit = new LifeConstantUnit()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return lifeConstantUnit;
    }

    @BeforeEach
    public void initTest() {
        lifeConstantUnit = createEntity(em);
    }

    @Test
    @Transactional
    public void createLifeConstantUnit() throws Exception {
        int databaseSizeBeforeCreate = lifeConstantUnitRepository.findAll().size();

        // Create the LifeConstantUnit
        restLifeConstantUnitMockMvc.perform(post("/api/life-constant-units")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lifeConstantUnit)))
            .andExpect(status().isCreated());

        // Validate the LifeConstantUnit in the database
        List<LifeConstantUnit> lifeConstantUnitList = lifeConstantUnitRepository.findAll();
        assertThat(lifeConstantUnitList).hasSize(databaseSizeBeforeCreate + 1);
        LifeConstantUnit testLifeConstantUnit = lifeConstantUnitList.get(lifeConstantUnitList.size() - 1);
        assertThat(testLifeConstantUnit.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLifeConstantUnit.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the LifeConstantUnit in Elasticsearch
        verify(mockLifeConstantUnitSearchRepository, times(1)).save(testLifeConstantUnit);
    }

    @Test
    @Transactional
    public void createLifeConstantUnitWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lifeConstantUnitRepository.findAll().size();

        // Create the LifeConstantUnit with an existing ID
        lifeConstantUnit.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLifeConstantUnitMockMvc.perform(post("/api/life-constant-units")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lifeConstantUnit)))
            .andExpect(status().isBadRequest());

        // Validate the LifeConstantUnit in the database
        List<LifeConstantUnit> lifeConstantUnitList = lifeConstantUnitRepository.findAll();
        assertThat(lifeConstantUnitList).hasSize(databaseSizeBeforeCreate);

        // Validate the LifeConstantUnit in Elasticsearch
        verify(mockLifeConstantUnitSearchRepository, times(0)).save(lifeConstantUnit);
    }


    @Test
    @Transactional
    public void getAllLifeConstantUnits() throws Exception {
        // Initialize the database
        lifeConstantUnitRepository.saveAndFlush(lifeConstantUnit);

        // Get all the lifeConstantUnitList
        restLifeConstantUnitMockMvc.perform(get("/api/life-constant-units?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lifeConstantUnit.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getLifeConstantUnit() throws Exception {
        // Initialize the database
        lifeConstantUnitRepository.saveAndFlush(lifeConstantUnit);

        // Get the lifeConstantUnit
        restLifeConstantUnitMockMvc.perform(get("/api/life-constant-units/{id}", lifeConstantUnit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(lifeConstantUnit.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    public void getNonExistingLifeConstantUnit() throws Exception {
        // Get the lifeConstantUnit
        restLifeConstantUnitMockMvc.perform(get("/api/life-constant-units/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLifeConstantUnit() throws Exception {
        // Initialize the database
        lifeConstantUnitService.save(lifeConstantUnit);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockLifeConstantUnitSearchRepository);

        int databaseSizeBeforeUpdate = lifeConstantUnitRepository.findAll().size();

        // Update the lifeConstantUnit
        LifeConstantUnit updatedLifeConstantUnit = lifeConstantUnitRepository.findById(lifeConstantUnit.getId()).get();
        // Disconnect from session so that the updates on updatedLifeConstantUnit are not directly saved in db
        em.detach(updatedLifeConstantUnit);
        updatedLifeConstantUnit
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restLifeConstantUnitMockMvc.perform(put("/api/life-constant-units")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLifeConstantUnit)))
            .andExpect(status().isOk());

        // Validate the LifeConstantUnit in the database
        List<LifeConstantUnit> lifeConstantUnitList = lifeConstantUnitRepository.findAll();
        assertThat(lifeConstantUnitList).hasSize(databaseSizeBeforeUpdate);
        LifeConstantUnit testLifeConstantUnit = lifeConstantUnitList.get(lifeConstantUnitList.size() - 1);
        assertThat(testLifeConstantUnit.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLifeConstantUnit.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the LifeConstantUnit in Elasticsearch
        verify(mockLifeConstantUnitSearchRepository, times(1)).save(testLifeConstantUnit);
    }

    @Test
    @Transactional
    public void updateNonExistingLifeConstantUnit() throws Exception {
        int databaseSizeBeforeUpdate = lifeConstantUnitRepository.findAll().size();

        // Create the LifeConstantUnit

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLifeConstantUnitMockMvc.perform(put("/api/life-constant-units")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lifeConstantUnit)))
            .andExpect(status().isBadRequest());

        // Validate the LifeConstantUnit in the database
        List<LifeConstantUnit> lifeConstantUnitList = lifeConstantUnitRepository.findAll();
        assertThat(lifeConstantUnitList).hasSize(databaseSizeBeforeUpdate);

        // Validate the LifeConstantUnit in Elasticsearch
        verify(mockLifeConstantUnitSearchRepository, times(0)).save(lifeConstantUnit);
    }

    @Test
    @Transactional
    public void deleteLifeConstantUnit() throws Exception {
        // Initialize the database
        lifeConstantUnitService.save(lifeConstantUnit);

        int databaseSizeBeforeDelete = lifeConstantUnitRepository.findAll().size();

        // Delete the lifeConstantUnit
        restLifeConstantUnitMockMvc.perform(delete("/api/life-constant-units/{id}", lifeConstantUnit.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LifeConstantUnit> lifeConstantUnitList = lifeConstantUnitRepository.findAll();
        assertThat(lifeConstantUnitList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the LifeConstantUnit in Elasticsearch
        verify(mockLifeConstantUnitSearchRepository, times(1)).deleteById(lifeConstantUnit.getId());
    }

    @Test
    @Transactional
    public void searchLifeConstantUnit() throws Exception {
        // Initialize the database
        lifeConstantUnitService.save(lifeConstantUnit);
        when(mockLifeConstantUnitSearchRepository.search(queryStringQuery("id:" + lifeConstantUnit.getId())))
            .thenReturn(Collections.singletonList(lifeConstantUnit));
        // Search the lifeConstantUnit
        restLifeConstantUnitMockMvc.perform(get("/api/_search/life-constant-units?query=id:" + lifeConstantUnit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lifeConstantUnit.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
}
