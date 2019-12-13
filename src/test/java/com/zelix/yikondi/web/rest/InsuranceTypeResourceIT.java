package com.zelix.yikondi.web.rest;

import com.zelix.yikondi.YikondiApp;
import com.zelix.yikondi.domain.InsuranceType;
import com.zelix.yikondi.repository.InsuranceTypeRepository;
import com.zelix.yikondi.repository.search.InsuranceTypeSearchRepository;
import com.zelix.yikondi.service.InsuranceTypeService;
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
 * Integration tests for the {@link InsuranceTypeResource} REST controller.
 */
@SpringBootTest(classes = YikondiApp.class)
public class InsuranceTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private InsuranceTypeRepository insuranceTypeRepository;

    @Autowired
    private InsuranceTypeService insuranceTypeService;

    /**
     * This repository is mocked in the com.zelix.yikondi.repository.search test package.
     *
     * @see com.zelix.yikondi.repository.search.InsuranceTypeSearchRepositoryMockConfiguration
     */
    @Autowired
    private InsuranceTypeSearchRepository mockInsuranceTypeSearchRepository;

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

    private MockMvc restInsuranceTypeMockMvc;

    private InsuranceType insuranceType;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InsuranceTypeResource insuranceTypeResource = new InsuranceTypeResource(insuranceTypeService);
        this.restInsuranceTypeMockMvc = MockMvcBuilders.standaloneSetup(insuranceTypeResource)
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
    public static InsuranceType createEntity(EntityManager em) {
        InsuranceType insuranceType = new InsuranceType()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return insuranceType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InsuranceType createUpdatedEntity(EntityManager em) {
        InsuranceType insuranceType = new InsuranceType()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return insuranceType;
    }

    @BeforeEach
    public void initTest() {
        insuranceType = createEntity(em);
    }

    @Test
    @Transactional
    public void createInsuranceType() throws Exception {
        int databaseSizeBeforeCreate = insuranceTypeRepository.findAll().size();

        // Create the InsuranceType
        restInsuranceTypeMockMvc.perform(post("/api/insurance-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(insuranceType)))
            .andExpect(status().isCreated());

        // Validate the InsuranceType in the database
        List<InsuranceType> insuranceTypeList = insuranceTypeRepository.findAll();
        assertThat(insuranceTypeList).hasSize(databaseSizeBeforeCreate + 1);
        InsuranceType testInsuranceType = insuranceTypeList.get(insuranceTypeList.size() - 1);
        assertThat(testInsuranceType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testInsuranceType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the InsuranceType in Elasticsearch
        verify(mockInsuranceTypeSearchRepository, times(1)).save(testInsuranceType);
    }

    @Test
    @Transactional
    public void createInsuranceTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = insuranceTypeRepository.findAll().size();

        // Create the InsuranceType with an existing ID
        insuranceType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInsuranceTypeMockMvc.perform(post("/api/insurance-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(insuranceType)))
            .andExpect(status().isBadRequest());

        // Validate the InsuranceType in the database
        List<InsuranceType> insuranceTypeList = insuranceTypeRepository.findAll();
        assertThat(insuranceTypeList).hasSize(databaseSizeBeforeCreate);

        // Validate the InsuranceType in Elasticsearch
        verify(mockInsuranceTypeSearchRepository, times(0)).save(insuranceType);
    }


    @Test
    @Transactional
    public void getAllInsuranceTypes() throws Exception {
        // Initialize the database
        insuranceTypeRepository.saveAndFlush(insuranceType);

        // Get all the insuranceTypeList
        restInsuranceTypeMockMvc.perform(get("/api/insurance-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(insuranceType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getInsuranceType() throws Exception {
        // Initialize the database
        insuranceTypeRepository.saveAndFlush(insuranceType);

        // Get the insuranceType
        restInsuranceTypeMockMvc.perform(get("/api/insurance-types/{id}", insuranceType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(insuranceType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    public void getNonExistingInsuranceType() throws Exception {
        // Get the insuranceType
        restInsuranceTypeMockMvc.perform(get("/api/insurance-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInsuranceType() throws Exception {
        // Initialize the database
        insuranceTypeService.save(insuranceType);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockInsuranceTypeSearchRepository);

        int databaseSizeBeforeUpdate = insuranceTypeRepository.findAll().size();

        // Update the insuranceType
        InsuranceType updatedInsuranceType = insuranceTypeRepository.findById(insuranceType.getId()).get();
        // Disconnect from session so that the updates on updatedInsuranceType are not directly saved in db
        em.detach(updatedInsuranceType);
        updatedInsuranceType
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restInsuranceTypeMockMvc.perform(put("/api/insurance-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedInsuranceType)))
            .andExpect(status().isOk());

        // Validate the InsuranceType in the database
        List<InsuranceType> insuranceTypeList = insuranceTypeRepository.findAll();
        assertThat(insuranceTypeList).hasSize(databaseSizeBeforeUpdate);
        InsuranceType testInsuranceType = insuranceTypeList.get(insuranceTypeList.size() - 1);
        assertThat(testInsuranceType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testInsuranceType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the InsuranceType in Elasticsearch
        verify(mockInsuranceTypeSearchRepository, times(1)).save(testInsuranceType);
    }

    @Test
    @Transactional
    public void updateNonExistingInsuranceType() throws Exception {
        int databaseSizeBeforeUpdate = insuranceTypeRepository.findAll().size();

        // Create the InsuranceType

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInsuranceTypeMockMvc.perform(put("/api/insurance-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(insuranceType)))
            .andExpect(status().isBadRequest());

        // Validate the InsuranceType in the database
        List<InsuranceType> insuranceTypeList = insuranceTypeRepository.findAll();
        assertThat(insuranceTypeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the InsuranceType in Elasticsearch
        verify(mockInsuranceTypeSearchRepository, times(0)).save(insuranceType);
    }

    @Test
    @Transactional
    public void deleteInsuranceType() throws Exception {
        // Initialize the database
        insuranceTypeService.save(insuranceType);

        int databaseSizeBeforeDelete = insuranceTypeRepository.findAll().size();

        // Delete the insuranceType
        restInsuranceTypeMockMvc.perform(delete("/api/insurance-types/{id}", insuranceType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<InsuranceType> insuranceTypeList = insuranceTypeRepository.findAll();
        assertThat(insuranceTypeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the InsuranceType in Elasticsearch
        verify(mockInsuranceTypeSearchRepository, times(1)).deleteById(insuranceType.getId());
    }

    @Test
    @Transactional
    public void searchInsuranceType() throws Exception {
        // Initialize the database
        insuranceTypeService.save(insuranceType);
        when(mockInsuranceTypeSearchRepository.search(queryStringQuery("id:" + insuranceType.getId())))
            .thenReturn(Collections.singletonList(insuranceType));
        // Search the insuranceType
        restInsuranceTypeMockMvc.perform(get("/api/_search/insurance-types?query=id:" + insuranceType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(insuranceType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
}
