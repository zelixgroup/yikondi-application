package com.zelix.yikondi.web.rest;

import com.zelix.yikondi.YikondiApp;
import com.zelix.yikondi.domain.PatientAllergy;
import com.zelix.yikondi.repository.PatientAllergyRepository;
import com.zelix.yikondi.repository.search.PatientAllergySearchRepository;
import com.zelix.yikondi.service.PatientAllergyService;
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
 * Integration tests for the {@link PatientAllergyResource} REST controller.
 */
@SpringBootTest(classes = YikondiApp.class)
public class PatientAllergyResourceIT {

    private static final LocalDate DEFAULT_OBSERVATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_OBSERVATION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_OBSERVATIONS = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVATIONS = "BBBBBBBBBB";

    @Autowired
    private PatientAllergyRepository patientAllergyRepository;

    @Autowired
    private PatientAllergyService patientAllergyService;

    /**
     * This repository is mocked in the com.zelix.yikondi.repository.search test package.
     *
     * @see com.zelix.yikondi.repository.search.PatientAllergySearchRepositoryMockConfiguration
     */
    @Autowired
    private PatientAllergySearchRepository mockPatientAllergySearchRepository;

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

    private MockMvc restPatientAllergyMockMvc;

    private PatientAllergy patientAllergy;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PatientAllergyResource patientAllergyResource = new PatientAllergyResource(patientAllergyService);
        this.restPatientAllergyMockMvc = MockMvcBuilders.standaloneSetup(patientAllergyResource)
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
    public static PatientAllergy createEntity(EntityManager em) {
        PatientAllergy patientAllergy = new PatientAllergy()
            .observationDate(DEFAULT_OBSERVATION_DATE)
            .observations(DEFAULT_OBSERVATIONS);
        return patientAllergy;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PatientAllergy createUpdatedEntity(EntityManager em) {
        PatientAllergy patientAllergy = new PatientAllergy()
            .observationDate(UPDATED_OBSERVATION_DATE)
            .observations(UPDATED_OBSERVATIONS);
        return patientAllergy;
    }

    @BeforeEach
    public void initTest() {
        patientAllergy = createEntity(em);
    }

    @Test
    @Transactional
    public void createPatientAllergy() throws Exception {
        int databaseSizeBeforeCreate = patientAllergyRepository.findAll().size();

        // Create the PatientAllergy
        restPatientAllergyMockMvc.perform(post("/api/patient-allergies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patientAllergy)))
            .andExpect(status().isCreated());

        // Validate the PatientAllergy in the database
        List<PatientAllergy> patientAllergyList = patientAllergyRepository.findAll();
        assertThat(patientAllergyList).hasSize(databaseSizeBeforeCreate + 1);
        PatientAllergy testPatientAllergy = patientAllergyList.get(patientAllergyList.size() - 1);
        assertThat(testPatientAllergy.getObservationDate()).isEqualTo(DEFAULT_OBSERVATION_DATE);
        assertThat(testPatientAllergy.getObservations()).isEqualTo(DEFAULT_OBSERVATIONS);

        // Validate the PatientAllergy in Elasticsearch
        verify(mockPatientAllergySearchRepository, times(1)).save(testPatientAllergy);
    }

    @Test
    @Transactional
    public void createPatientAllergyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = patientAllergyRepository.findAll().size();

        // Create the PatientAllergy with an existing ID
        patientAllergy.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPatientAllergyMockMvc.perform(post("/api/patient-allergies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patientAllergy)))
            .andExpect(status().isBadRequest());

        // Validate the PatientAllergy in the database
        List<PatientAllergy> patientAllergyList = patientAllergyRepository.findAll();
        assertThat(patientAllergyList).hasSize(databaseSizeBeforeCreate);

        // Validate the PatientAllergy in Elasticsearch
        verify(mockPatientAllergySearchRepository, times(0)).save(patientAllergy);
    }


    @Test
    @Transactional
    public void getAllPatientAllergies() throws Exception {
        // Initialize the database
        patientAllergyRepository.saveAndFlush(patientAllergy);

        // Get all the patientAllergyList
        restPatientAllergyMockMvc.perform(get("/api/patient-allergies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(patientAllergy.getId().intValue())))
            .andExpect(jsonPath("$.[*].observationDate").value(hasItem(DEFAULT_OBSERVATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].observations").value(hasItem(DEFAULT_OBSERVATIONS)));
    }
    
    @Test
    @Transactional
    public void getPatientAllergy() throws Exception {
        // Initialize the database
        patientAllergyRepository.saveAndFlush(patientAllergy);

        // Get the patientAllergy
        restPatientAllergyMockMvc.perform(get("/api/patient-allergies/{id}", patientAllergy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(patientAllergy.getId().intValue()))
            .andExpect(jsonPath("$.observationDate").value(DEFAULT_OBSERVATION_DATE.toString()))
            .andExpect(jsonPath("$.observations").value(DEFAULT_OBSERVATIONS));
    }

    @Test
    @Transactional
    public void getNonExistingPatientAllergy() throws Exception {
        // Get the patientAllergy
        restPatientAllergyMockMvc.perform(get("/api/patient-allergies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePatientAllergy() throws Exception {
        // Initialize the database
        patientAllergyService.save(patientAllergy);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockPatientAllergySearchRepository);

        int databaseSizeBeforeUpdate = patientAllergyRepository.findAll().size();

        // Update the patientAllergy
        PatientAllergy updatedPatientAllergy = patientAllergyRepository.findById(patientAllergy.getId()).get();
        // Disconnect from session so that the updates on updatedPatientAllergy are not directly saved in db
        em.detach(updatedPatientAllergy);
        updatedPatientAllergy
            .observationDate(UPDATED_OBSERVATION_DATE)
            .observations(UPDATED_OBSERVATIONS);

        restPatientAllergyMockMvc.perform(put("/api/patient-allergies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPatientAllergy)))
            .andExpect(status().isOk());

        // Validate the PatientAllergy in the database
        List<PatientAllergy> patientAllergyList = patientAllergyRepository.findAll();
        assertThat(patientAllergyList).hasSize(databaseSizeBeforeUpdate);
        PatientAllergy testPatientAllergy = patientAllergyList.get(patientAllergyList.size() - 1);
        assertThat(testPatientAllergy.getObservationDate()).isEqualTo(UPDATED_OBSERVATION_DATE);
        assertThat(testPatientAllergy.getObservations()).isEqualTo(UPDATED_OBSERVATIONS);

        // Validate the PatientAllergy in Elasticsearch
        verify(mockPatientAllergySearchRepository, times(1)).save(testPatientAllergy);
    }

    @Test
    @Transactional
    public void updateNonExistingPatientAllergy() throws Exception {
        int databaseSizeBeforeUpdate = patientAllergyRepository.findAll().size();

        // Create the PatientAllergy

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPatientAllergyMockMvc.perform(put("/api/patient-allergies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patientAllergy)))
            .andExpect(status().isBadRequest());

        // Validate the PatientAllergy in the database
        List<PatientAllergy> patientAllergyList = patientAllergyRepository.findAll();
        assertThat(patientAllergyList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PatientAllergy in Elasticsearch
        verify(mockPatientAllergySearchRepository, times(0)).save(patientAllergy);
    }

    @Test
    @Transactional
    public void deletePatientAllergy() throws Exception {
        // Initialize the database
        patientAllergyService.save(patientAllergy);

        int databaseSizeBeforeDelete = patientAllergyRepository.findAll().size();

        // Delete the patientAllergy
        restPatientAllergyMockMvc.perform(delete("/api/patient-allergies/{id}", patientAllergy.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PatientAllergy> patientAllergyList = patientAllergyRepository.findAll();
        assertThat(patientAllergyList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PatientAllergy in Elasticsearch
        verify(mockPatientAllergySearchRepository, times(1)).deleteById(patientAllergy.getId());
    }

    @Test
    @Transactional
    public void searchPatientAllergy() throws Exception {
        // Initialize the database
        patientAllergyService.save(patientAllergy);
        when(mockPatientAllergySearchRepository.search(queryStringQuery("id:" + patientAllergy.getId())))
            .thenReturn(Collections.singletonList(patientAllergy));
        // Search the patientAllergy
        restPatientAllergyMockMvc.perform(get("/api/_search/patient-allergies?query=id:" + patientAllergy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(patientAllergy.getId().intValue())))
            .andExpect(jsonPath("$.[*].observationDate").value(hasItem(DEFAULT_OBSERVATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].observations").value(hasItem(DEFAULT_OBSERVATIONS)));
    }
}
