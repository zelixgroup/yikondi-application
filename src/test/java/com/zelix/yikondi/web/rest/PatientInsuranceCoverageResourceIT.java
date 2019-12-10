package com.zelix.yikondi.web.rest;

import com.zelix.yikondi.YikondiApp;
import com.zelix.yikondi.domain.PatientInsuranceCoverage;
import com.zelix.yikondi.repository.PatientInsuranceCoverageRepository;
import com.zelix.yikondi.repository.search.PatientInsuranceCoverageSearchRepository;
import com.zelix.yikondi.service.PatientInsuranceCoverageService;
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
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link PatientInsuranceCoverageResource} REST controller.
 */
@SpringBootTest(classes = YikondiApp.class)
public class PatientInsuranceCoverageResourceIT {

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_REFERENCE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE_NUMBER = "BBBBBBBBBB";

    @Autowired
    private PatientInsuranceCoverageRepository patientInsuranceCoverageRepository;

    @Autowired
    private PatientInsuranceCoverageService patientInsuranceCoverageService;

    /**
     * This repository is mocked in the com.zelix.yikondi.repository.search test package.
     *
     * @see com.zelix.yikondi.repository.search.PatientInsuranceCoverageSearchRepositoryMockConfiguration
     */
    @Autowired
    private PatientInsuranceCoverageSearchRepository mockPatientInsuranceCoverageSearchRepository;

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

    private MockMvc restPatientInsuranceCoverageMockMvc;

    private PatientInsuranceCoverage patientInsuranceCoverage;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PatientInsuranceCoverageResource patientInsuranceCoverageResource = new PatientInsuranceCoverageResource(patientInsuranceCoverageService);
        this.restPatientInsuranceCoverageMockMvc = MockMvcBuilders.standaloneSetup(patientInsuranceCoverageResource)
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
    public static PatientInsuranceCoverage createEntity(EntityManager em) {
        PatientInsuranceCoverage patientInsuranceCoverage = new PatientInsuranceCoverage()
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .referenceNumber(DEFAULT_REFERENCE_NUMBER);
        return patientInsuranceCoverage;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PatientInsuranceCoverage createUpdatedEntity(EntityManager em) {
        PatientInsuranceCoverage patientInsuranceCoverage = new PatientInsuranceCoverage()
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .referenceNumber(UPDATED_REFERENCE_NUMBER);
        return patientInsuranceCoverage;
    }

    @BeforeEach
    public void initTest() {
        patientInsuranceCoverage = createEntity(em);
    }

    @Test
    @Transactional
    public void createPatientInsuranceCoverage() throws Exception {
        int databaseSizeBeforeCreate = patientInsuranceCoverageRepository.findAll().size();

        // Create the PatientInsuranceCoverage
        restPatientInsuranceCoverageMockMvc.perform(post("/api/patient-insurance-coverages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patientInsuranceCoverage)))
            .andExpect(status().isCreated());

        // Validate the PatientInsuranceCoverage in the database
        List<PatientInsuranceCoverage> patientInsuranceCoverageList = patientInsuranceCoverageRepository.findAll();
        assertThat(patientInsuranceCoverageList).hasSize(databaseSizeBeforeCreate + 1);
        PatientInsuranceCoverage testPatientInsuranceCoverage = patientInsuranceCoverageList.get(patientInsuranceCoverageList.size() - 1);
        assertThat(testPatientInsuranceCoverage.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testPatientInsuranceCoverage.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testPatientInsuranceCoverage.getReferenceNumber()).isEqualTo(DEFAULT_REFERENCE_NUMBER);

        // Validate the PatientInsuranceCoverage in Elasticsearch
        verify(mockPatientInsuranceCoverageSearchRepository, times(1)).save(testPatientInsuranceCoverage);
    }

    @Test
    @Transactional
    public void createPatientInsuranceCoverageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = patientInsuranceCoverageRepository.findAll().size();

        // Create the PatientInsuranceCoverage with an existing ID
        patientInsuranceCoverage.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPatientInsuranceCoverageMockMvc.perform(post("/api/patient-insurance-coverages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patientInsuranceCoverage)))
            .andExpect(status().isBadRequest());

        // Validate the PatientInsuranceCoverage in the database
        List<PatientInsuranceCoverage> patientInsuranceCoverageList = patientInsuranceCoverageRepository.findAll();
        assertThat(patientInsuranceCoverageList).hasSize(databaseSizeBeforeCreate);

        // Validate the PatientInsuranceCoverage in Elasticsearch
        verify(mockPatientInsuranceCoverageSearchRepository, times(0)).save(patientInsuranceCoverage);
    }


    @Test
    @Transactional
    public void getAllPatientInsuranceCoverages() throws Exception {
        // Initialize the database
        patientInsuranceCoverageRepository.saveAndFlush(patientInsuranceCoverage);

        // Get all the patientInsuranceCoverageList
        restPatientInsuranceCoverageMockMvc.perform(get("/api/patient-insurance-coverages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(patientInsuranceCoverage.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].referenceNumber").value(hasItem(DEFAULT_REFERENCE_NUMBER)));
    }
    
    @Test
    @Transactional
    public void getPatientInsuranceCoverage() throws Exception {
        // Initialize the database
        patientInsuranceCoverageRepository.saveAndFlush(patientInsuranceCoverage);

        // Get the patientInsuranceCoverage
        restPatientInsuranceCoverageMockMvc.perform(get("/api/patient-insurance-coverages/{id}", patientInsuranceCoverage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(patientInsuranceCoverage.getId().intValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.referenceNumber").value(DEFAULT_REFERENCE_NUMBER));
    }

    @Test
    @Transactional
    public void getNonExistingPatientInsuranceCoverage() throws Exception {
        // Get the patientInsuranceCoverage
        restPatientInsuranceCoverageMockMvc.perform(get("/api/patient-insurance-coverages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePatientInsuranceCoverage() throws Exception {
        // Initialize the database
        patientInsuranceCoverageService.save(patientInsuranceCoverage);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockPatientInsuranceCoverageSearchRepository);

        int databaseSizeBeforeUpdate = patientInsuranceCoverageRepository.findAll().size();

        // Update the patientInsuranceCoverage
        PatientInsuranceCoverage updatedPatientInsuranceCoverage = patientInsuranceCoverageRepository.findById(patientInsuranceCoverage.getId()).get();
        // Disconnect from session so that the updates on updatedPatientInsuranceCoverage are not directly saved in db
        em.detach(updatedPatientInsuranceCoverage);
        updatedPatientInsuranceCoverage
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .referenceNumber(UPDATED_REFERENCE_NUMBER);

        restPatientInsuranceCoverageMockMvc.perform(put("/api/patient-insurance-coverages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPatientInsuranceCoverage)))
            .andExpect(status().isOk());

        // Validate the PatientInsuranceCoverage in the database
        List<PatientInsuranceCoverage> patientInsuranceCoverageList = patientInsuranceCoverageRepository.findAll();
        assertThat(patientInsuranceCoverageList).hasSize(databaseSizeBeforeUpdate);
        PatientInsuranceCoverage testPatientInsuranceCoverage = patientInsuranceCoverageList.get(patientInsuranceCoverageList.size() - 1);
        assertThat(testPatientInsuranceCoverage.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testPatientInsuranceCoverage.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testPatientInsuranceCoverage.getReferenceNumber()).isEqualTo(UPDATED_REFERENCE_NUMBER);

        // Validate the PatientInsuranceCoverage in Elasticsearch
        verify(mockPatientInsuranceCoverageSearchRepository, times(1)).save(testPatientInsuranceCoverage);
    }

    @Test
    @Transactional
    public void updateNonExistingPatientInsuranceCoverage() throws Exception {
        int databaseSizeBeforeUpdate = patientInsuranceCoverageRepository.findAll().size();

        // Create the PatientInsuranceCoverage

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPatientInsuranceCoverageMockMvc.perform(put("/api/patient-insurance-coverages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patientInsuranceCoverage)))
            .andExpect(status().isBadRequest());

        // Validate the PatientInsuranceCoverage in the database
        List<PatientInsuranceCoverage> patientInsuranceCoverageList = patientInsuranceCoverageRepository.findAll();
        assertThat(patientInsuranceCoverageList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PatientInsuranceCoverage in Elasticsearch
        verify(mockPatientInsuranceCoverageSearchRepository, times(0)).save(patientInsuranceCoverage);
    }

    @Test
    @Transactional
    public void deletePatientInsuranceCoverage() throws Exception {
        // Initialize the database
        patientInsuranceCoverageService.save(patientInsuranceCoverage);

        int databaseSizeBeforeDelete = patientInsuranceCoverageRepository.findAll().size();

        // Delete the patientInsuranceCoverage
        restPatientInsuranceCoverageMockMvc.perform(delete("/api/patient-insurance-coverages/{id}", patientInsuranceCoverage.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PatientInsuranceCoverage> patientInsuranceCoverageList = patientInsuranceCoverageRepository.findAll();
        assertThat(patientInsuranceCoverageList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PatientInsuranceCoverage in Elasticsearch
        verify(mockPatientInsuranceCoverageSearchRepository, times(1)).deleteById(patientInsuranceCoverage.getId());
    }

    @Test
    @Transactional
    public void searchPatientInsuranceCoverage() throws Exception {
        // Initialize the database
        patientInsuranceCoverageService.save(patientInsuranceCoverage);
        when(mockPatientInsuranceCoverageSearchRepository.search(queryStringQuery("id:" + patientInsuranceCoverage.getId())))
            .thenReturn(Collections.singletonList(patientInsuranceCoverage));
        // Search the patientInsuranceCoverage
        restPatientInsuranceCoverageMockMvc.perform(get("/api/_search/patient-insurance-coverages?query=id:" + patientInsuranceCoverage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(patientInsuranceCoverage.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].referenceNumber").value(hasItem(DEFAULT_REFERENCE_NUMBER)));
    }
}
