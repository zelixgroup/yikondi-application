package com.zelix.yikondi.web.rest;

import com.zelix.yikondi.YikondiApp;
import com.zelix.yikondi.domain.PatientEmergencyNumber;
import com.zelix.yikondi.repository.PatientEmergencyNumberRepository;
import com.zelix.yikondi.repository.search.PatientEmergencyNumberSearchRepository;
import com.zelix.yikondi.service.PatientEmergencyNumberService;
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
 * Integration tests for the {@link PatientEmergencyNumberResource} REST controller.
 */
@SpringBootTest(classes = YikondiApp.class)
public class PatientEmergencyNumberResourceIT {

    private static final String DEFAULT_EMERGENCY_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_EMERGENCY_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_FULL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FULL_NAME = "BBBBBBBBBB";

    @Autowired
    private PatientEmergencyNumberRepository patientEmergencyNumberRepository;

    @Autowired
    private PatientEmergencyNumberService patientEmergencyNumberService;

    /**
     * This repository is mocked in the com.zelix.yikondi.repository.search test package.
     *
     * @see com.zelix.yikondi.repository.search.PatientEmergencyNumberSearchRepositoryMockConfiguration
     */
    @Autowired
    private PatientEmergencyNumberSearchRepository mockPatientEmergencyNumberSearchRepository;

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

    private MockMvc restPatientEmergencyNumberMockMvc;

    private PatientEmergencyNumber patientEmergencyNumber;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PatientEmergencyNumberResource patientEmergencyNumberResource = new PatientEmergencyNumberResource(patientEmergencyNumberService);
        this.restPatientEmergencyNumberMockMvc = MockMvcBuilders.standaloneSetup(patientEmergencyNumberResource)
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
    public static PatientEmergencyNumber createEntity(EntityManager em) {
        PatientEmergencyNumber patientEmergencyNumber = new PatientEmergencyNumber()
            .emergencyNumber(DEFAULT_EMERGENCY_NUMBER)
            .fullName(DEFAULT_FULL_NAME);
        return patientEmergencyNumber;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PatientEmergencyNumber createUpdatedEntity(EntityManager em) {
        PatientEmergencyNumber patientEmergencyNumber = new PatientEmergencyNumber()
            .emergencyNumber(UPDATED_EMERGENCY_NUMBER)
            .fullName(UPDATED_FULL_NAME);
        return patientEmergencyNumber;
    }

    @BeforeEach
    public void initTest() {
        patientEmergencyNumber = createEntity(em);
    }

    @Test
    @Transactional
    public void createPatientEmergencyNumber() throws Exception {
        int databaseSizeBeforeCreate = patientEmergencyNumberRepository.findAll().size();

        // Create the PatientEmergencyNumber
        restPatientEmergencyNumberMockMvc.perform(post("/api/patient-emergency-numbers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patientEmergencyNumber)))
            .andExpect(status().isCreated());

        // Validate the PatientEmergencyNumber in the database
        List<PatientEmergencyNumber> patientEmergencyNumberList = patientEmergencyNumberRepository.findAll();
        assertThat(patientEmergencyNumberList).hasSize(databaseSizeBeforeCreate + 1);
        PatientEmergencyNumber testPatientEmergencyNumber = patientEmergencyNumberList.get(patientEmergencyNumberList.size() - 1);
        assertThat(testPatientEmergencyNumber.getEmergencyNumber()).isEqualTo(DEFAULT_EMERGENCY_NUMBER);
        assertThat(testPatientEmergencyNumber.getFullName()).isEqualTo(DEFAULT_FULL_NAME);

        // Validate the PatientEmergencyNumber in Elasticsearch
        verify(mockPatientEmergencyNumberSearchRepository, times(1)).save(testPatientEmergencyNumber);
    }

    @Test
    @Transactional
    public void createPatientEmergencyNumberWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = patientEmergencyNumberRepository.findAll().size();

        // Create the PatientEmergencyNumber with an existing ID
        patientEmergencyNumber.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPatientEmergencyNumberMockMvc.perform(post("/api/patient-emergency-numbers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patientEmergencyNumber)))
            .andExpect(status().isBadRequest());

        // Validate the PatientEmergencyNumber in the database
        List<PatientEmergencyNumber> patientEmergencyNumberList = patientEmergencyNumberRepository.findAll();
        assertThat(patientEmergencyNumberList).hasSize(databaseSizeBeforeCreate);

        // Validate the PatientEmergencyNumber in Elasticsearch
        verify(mockPatientEmergencyNumberSearchRepository, times(0)).save(patientEmergencyNumber);
    }


    @Test
    @Transactional
    public void getAllPatientEmergencyNumbers() throws Exception {
        // Initialize the database
        patientEmergencyNumberRepository.saveAndFlush(patientEmergencyNumber);

        // Get all the patientEmergencyNumberList
        restPatientEmergencyNumberMockMvc.perform(get("/api/patient-emergency-numbers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(patientEmergencyNumber.getId().intValue())))
            .andExpect(jsonPath("$.[*].emergencyNumber").value(hasItem(DEFAULT_EMERGENCY_NUMBER)))
            .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME)));
    }
    
    @Test
    @Transactional
    public void getPatientEmergencyNumber() throws Exception {
        // Initialize the database
        patientEmergencyNumberRepository.saveAndFlush(patientEmergencyNumber);

        // Get the patientEmergencyNumber
        restPatientEmergencyNumberMockMvc.perform(get("/api/patient-emergency-numbers/{id}", patientEmergencyNumber.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(patientEmergencyNumber.getId().intValue()))
            .andExpect(jsonPath("$.emergencyNumber").value(DEFAULT_EMERGENCY_NUMBER))
            .andExpect(jsonPath("$.fullName").value(DEFAULT_FULL_NAME));
    }

    @Test
    @Transactional
    public void getNonExistingPatientEmergencyNumber() throws Exception {
        // Get the patientEmergencyNumber
        restPatientEmergencyNumberMockMvc.perform(get("/api/patient-emergency-numbers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePatientEmergencyNumber() throws Exception {
        // Initialize the database
        patientEmergencyNumberService.save(patientEmergencyNumber);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockPatientEmergencyNumberSearchRepository);

        int databaseSizeBeforeUpdate = patientEmergencyNumberRepository.findAll().size();

        // Update the patientEmergencyNumber
        PatientEmergencyNumber updatedPatientEmergencyNumber = patientEmergencyNumberRepository.findById(patientEmergencyNumber.getId()).get();
        // Disconnect from session so that the updates on updatedPatientEmergencyNumber are not directly saved in db
        em.detach(updatedPatientEmergencyNumber);
        updatedPatientEmergencyNumber
            .emergencyNumber(UPDATED_EMERGENCY_NUMBER)
            .fullName(UPDATED_FULL_NAME);

        restPatientEmergencyNumberMockMvc.perform(put("/api/patient-emergency-numbers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPatientEmergencyNumber)))
            .andExpect(status().isOk());

        // Validate the PatientEmergencyNumber in the database
        List<PatientEmergencyNumber> patientEmergencyNumberList = patientEmergencyNumberRepository.findAll();
        assertThat(patientEmergencyNumberList).hasSize(databaseSizeBeforeUpdate);
        PatientEmergencyNumber testPatientEmergencyNumber = patientEmergencyNumberList.get(patientEmergencyNumberList.size() - 1);
        assertThat(testPatientEmergencyNumber.getEmergencyNumber()).isEqualTo(UPDATED_EMERGENCY_NUMBER);
        assertThat(testPatientEmergencyNumber.getFullName()).isEqualTo(UPDATED_FULL_NAME);

        // Validate the PatientEmergencyNumber in Elasticsearch
        verify(mockPatientEmergencyNumberSearchRepository, times(1)).save(testPatientEmergencyNumber);
    }

    @Test
    @Transactional
    public void updateNonExistingPatientEmergencyNumber() throws Exception {
        int databaseSizeBeforeUpdate = patientEmergencyNumberRepository.findAll().size();

        // Create the PatientEmergencyNumber

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPatientEmergencyNumberMockMvc.perform(put("/api/patient-emergency-numbers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patientEmergencyNumber)))
            .andExpect(status().isBadRequest());

        // Validate the PatientEmergencyNumber in the database
        List<PatientEmergencyNumber> patientEmergencyNumberList = patientEmergencyNumberRepository.findAll();
        assertThat(patientEmergencyNumberList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PatientEmergencyNumber in Elasticsearch
        verify(mockPatientEmergencyNumberSearchRepository, times(0)).save(patientEmergencyNumber);
    }

    @Test
    @Transactional
    public void deletePatientEmergencyNumber() throws Exception {
        // Initialize the database
        patientEmergencyNumberService.save(patientEmergencyNumber);

        int databaseSizeBeforeDelete = patientEmergencyNumberRepository.findAll().size();

        // Delete the patientEmergencyNumber
        restPatientEmergencyNumberMockMvc.perform(delete("/api/patient-emergency-numbers/{id}", patientEmergencyNumber.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PatientEmergencyNumber> patientEmergencyNumberList = patientEmergencyNumberRepository.findAll();
        assertThat(patientEmergencyNumberList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PatientEmergencyNumber in Elasticsearch
        verify(mockPatientEmergencyNumberSearchRepository, times(1)).deleteById(patientEmergencyNumber.getId());
    }

    @Test
    @Transactional
    public void searchPatientEmergencyNumber() throws Exception {
        // Initialize the database
        patientEmergencyNumberService.save(patientEmergencyNumber);
        when(mockPatientEmergencyNumberSearchRepository.search(queryStringQuery("id:" + patientEmergencyNumber.getId())))
            .thenReturn(Collections.singletonList(patientEmergencyNumber));
        // Search the patientEmergencyNumber
        restPatientEmergencyNumberMockMvc.perform(get("/api/_search/patient-emergency-numbers?query=id:" + patientEmergencyNumber.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(patientEmergencyNumber.getId().intValue())))
            .andExpect(jsonPath("$.[*].emergencyNumber").value(hasItem(DEFAULT_EMERGENCY_NUMBER)))
            .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME)));
    }
}
