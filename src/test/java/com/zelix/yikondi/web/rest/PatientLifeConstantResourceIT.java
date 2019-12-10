package com.zelix.yikondi.web.rest;

import com.zelix.yikondi.YikondiApp;
import com.zelix.yikondi.domain.PatientLifeConstant;
import com.zelix.yikondi.repository.PatientLifeConstantRepository;
import com.zelix.yikondi.repository.search.PatientLifeConstantSearchRepository;
import com.zelix.yikondi.service.PatientLifeConstantService;
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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;

import static com.zelix.yikondi.web.rest.TestUtil.sameInstant;
import static com.zelix.yikondi.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PatientLifeConstantResource} REST controller.
 */
@SpringBootTest(classes = YikondiApp.class)
public class PatientLifeConstantResourceIT {

    private static final ZonedDateTime DEFAULT_MEASUREMENT_DATETIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_MEASUREMENT_DATETIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_MEASURED_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_MEASURED_VALUE = "BBBBBBBBBB";

    @Autowired
    private PatientLifeConstantRepository patientLifeConstantRepository;

    @Autowired
    private PatientLifeConstantService patientLifeConstantService;

    /**
     * This repository is mocked in the com.zelix.yikondi.repository.search test package.
     *
     * @see com.zelix.yikondi.repository.search.PatientLifeConstantSearchRepositoryMockConfiguration
     */
    @Autowired
    private PatientLifeConstantSearchRepository mockPatientLifeConstantSearchRepository;

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

    private MockMvc restPatientLifeConstantMockMvc;

    private PatientLifeConstant patientLifeConstant;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PatientLifeConstantResource patientLifeConstantResource = new PatientLifeConstantResource(patientLifeConstantService);
        this.restPatientLifeConstantMockMvc = MockMvcBuilders.standaloneSetup(patientLifeConstantResource)
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
    public static PatientLifeConstant createEntity(EntityManager em) {
        PatientLifeConstant patientLifeConstant = new PatientLifeConstant()
            .measurementDatetime(DEFAULT_MEASUREMENT_DATETIME)
            .measuredValue(DEFAULT_MEASURED_VALUE);
        return patientLifeConstant;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PatientLifeConstant createUpdatedEntity(EntityManager em) {
        PatientLifeConstant patientLifeConstant = new PatientLifeConstant()
            .measurementDatetime(UPDATED_MEASUREMENT_DATETIME)
            .measuredValue(UPDATED_MEASURED_VALUE);
        return patientLifeConstant;
    }

    @BeforeEach
    public void initTest() {
        patientLifeConstant = createEntity(em);
    }

    @Test
    @Transactional
    public void createPatientLifeConstant() throws Exception {
        int databaseSizeBeforeCreate = patientLifeConstantRepository.findAll().size();

        // Create the PatientLifeConstant
        restPatientLifeConstantMockMvc.perform(post("/api/patient-life-constants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patientLifeConstant)))
            .andExpect(status().isCreated());

        // Validate the PatientLifeConstant in the database
        List<PatientLifeConstant> patientLifeConstantList = patientLifeConstantRepository.findAll();
        assertThat(patientLifeConstantList).hasSize(databaseSizeBeforeCreate + 1);
        PatientLifeConstant testPatientLifeConstant = patientLifeConstantList.get(patientLifeConstantList.size() - 1);
        assertThat(testPatientLifeConstant.getMeasurementDatetime()).isEqualTo(DEFAULT_MEASUREMENT_DATETIME);
        assertThat(testPatientLifeConstant.getMeasuredValue()).isEqualTo(DEFAULT_MEASURED_VALUE);

        // Validate the PatientLifeConstant in Elasticsearch
        verify(mockPatientLifeConstantSearchRepository, times(1)).save(testPatientLifeConstant);
    }

    @Test
    @Transactional
    public void createPatientLifeConstantWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = patientLifeConstantRepository.findAll().size();

        // Create the PatientLifeConstant with an existing ID
        patientLifeConstant.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPatientLifeConstantMockMvc.perform(post("/api/patient-life-constants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patientLifeConstant)))
            .andExpect(status().isBadRequest());

        // Validate the PatientLifeConstant in the database
        List<PatientLifeConstant> patientLifeConstantList = patientLifeConstantRepository.findAll();
        assertThat(patientLifeConstantList).hasSize(databaseSizeBeforeCreate);

        // Validate the PatientLifeConstant in Elasticsearch
        verify(mockPatientLifeConstantSearchRepository, times(0)).save(patientLifeConstant);
    }


    @Test
    @Transactional
    public void getAllPatientLifeConstants() throws Exception {
        // Initialize the database
        patientLifeConstantRepository.saveAndFlush(patientLifeConstant);

        // Get all the patientLifeConstantList
        restPatientLifeConstantMockMvc.perform(get("/api/patient-life-constants?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(patientLifeConstant.getId().intValue())))
            .andExpect(jsonPath("$.[*].measurementDatetime").value(hasItem(sameInstant(DEFAULT_MEASUREMENT_DATETIME))))
            .andExpect(jsonPath("$.[*].measuredValue").value(hasItem(DEFAULT_MEASURED_VALUE)));
    }
    
    @Test
    @Transactional
    public void getPatientLifeConstant() throws Exception {
        // Initialize the database
        patientLifeConstantRepository.saveAndFlush(patientLifeConstant);

        // Get the patientLifeConstant
        restPatientLifeConstantMockMvc.perform(get("/api/patient-life-constants/{id}", patientLifeConstant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(patientLifeConstant.getId().intValue()))
            .andExpect(jsonPath("$.measurementDatetime").value(sameInstant(DEFAULT_MEASUREMENT_DATETIME)))
            .andExpect(jsonPath("$.measuredValue").value(DEFAULT_MEASURED_VALUE));
    }

    @Test
    @Transactional
    public void getNonExistingPatientLifeConstant() throws Exception {
        // Get the patientLifeConstant
        restPatientLifeConstantMockMvc.perform(get("/api/patient-life-constants/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePatientLifeConstant() throws Exception {
        // Initialize the database
        patientLifeConstantService.save(patientLifeConstant);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockPatientLifeConstantSearchRepository);

        int databaseSizeBeforeUpdate = patientLifeConstantRepository.findAll().size();

        // Update the patientLifeConstant
        PatientLifeConstant updatedPatientLifeConstant = patientLifeConstantRepository.findById(patientLifeConstant.getId()).get();
        // Disconnect from session so that the updates on updatedPatientLifeConstant are not directly saved in db
        em.detach(updatedPatientLifeConstant);
        updatedPatientLifeConstant
            .measurementDatetime(UPDATED_MEASUREMENT_DATETIME)
            .measuredValue(UPDATED_MEASURED_VALUE);

        restPatientLifeConstantMockMvc.perform(put("/api/patient-life-constants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPatientLifeConstant)))
            .andExpect(status().isOk());

        // Validate the PatientLifeConstant in the database
        List<PatientLifeConstant> patientLifeConstantList = patientLifeConstantRepository.findAll();
        assertThat(patientLifeConstantList).hasSize(databaseSizeBeforeUpdate);
        PatientLifeConstant testPatientLifeConstant = patientLifeConstantList.get(patientLifeConstantList.size() - 1);
        assertThat(testPatientLifeConstant.getMeasurementDatetime()).isEqualTo(UPDATED_MEASUREMENT_DATETIME);
        assertThat(testPatientLifeConstant.getMeasuredValue()).isEqualTo(UPDATED_MEASURED_VALUE);

        // Validate the PatientLifeConstant in Elasticsearch
        verify(mockPatientLifeConstantSearchRepository, times(1)).save(testPatientLifeConstant);
    }

    @Test
    @Transactional
    public void updateNonExistingPatientLifeConstant() throws Exception {
        int databaseSizeBeforeUpdate = patientLifeConstantRepository.findAll().size();

        // Create the PatientLifeConstant

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPatientLifeConstantMockMvc.perform(put("/api/patient-life-constants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patientLifeConstant)))
            .andExpect(status().isBadRequest());

        // Validate the PatientLifeConstant in the database
        List<PatientLifeConstant> patientLifeConstantList = patientLifeConstantRepository.findAll();
        assertThat(patientLifeConstantList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PatientLifeConstant in Elasticsearch
        verify(mockPatientLifeConstantSearchRepository, times(0)).save(patientLifeConstant);
    }

    @Test
    @Transactional
    public void deletePatientLifeConstant() throws Exception {
        // Initialize the database
        patientLifeConstantService.save(patientLifeConstant);

        int databaseSizeBeforeDelete = patientLifeConstantRepository.findAll().size();

        // Delete the patientLifeConstant
        restPatientLifeConstantMockMvc.perform(delete("/api/patient-life-constants/{id}", patientLifeConstant.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PatientLifeConstant> patientLifeConstantList = patientLifeConstantRepository.findAll();
        assertThat(patientLifeConstantList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PatientLifeConstant in Elasticsearch
        verify(mockPatientLifeConstantSearchRepository, times(1)).deleteById(patientLifeConstant.getId());
    }

    @Test
    @Transactional
    public void searchPatientLifeConstant() throws Exception {
        // Initialize the database
        patientLifeConstantService.save(patientLifeConstant);
        when(mockPatientLifeConstantSearchRepository.search(queryStringQuery("id:" + patientLifeConstant.getId())))
            .thenReturn(Collections.singletonList(patientLifeConstant));
        // Search the patientLifeConstant
        restPatientLifeConstantMockMvc.perform(get("/api/_search/patient-life-constants?query=id:" + patientLifeConstant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(patientLifeConstant.getId().intValue())))
            .andExpect(jsonPath("$.[*].measurementDatetime").value(hasItem(sameInstant(DEFAULT_MEASUREMENT_DATETIME))))
            .andExpect(jsonPath("$.[*].measuredValue").value(hasItem(DEFAULT_MEASURED_VALUE)));
    }
}
