package com.zelix.yikondi.web.rest;

import com.zelix.yikondi.YikondiApp;
import com.zelix.yikondi.domain.MedicalPrescription;
import com.zelix.yikondi.repository.MedicalPrescriptionRepository;
import com.zelix.yikondi.repository.search.MedicalPrescriptionSearchRepository;
import com.zelix.yikondi.service.MedicalPrescriptionService;
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
 * Integration tests for the {@link MedicalPrescriptionResource} REST controller.
 */
@SpringBootTest(classes = YikondiApp.class)
public class MedicalPrescriptionResourceIT {

    private static final ZonedDateTime DEFAULT_PRESCRIPTION_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_PRESCRIPTION_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_OBSERVATIONS = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVATIONS = "BBBBBBBBBB";

    @Autowired
    private MedicalPrescriptionRepository medicalPrescriptionRepository;

    @Autowired
    private MedicalPrescriptionService medicalPrescriptionService;

    /**
     * This repository is mocked in the com.zelix.yikondi.repository.search test package.
     *
     * @see com.zelix.yikondi.repository.search.MedicalPrescriptionSearchRepositoryMockConfiguration
     */
    @Autowired
    private MedicalPrescriptionSearchRepository mockMedicalPrescriptionSearchRepository;

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

    private MockMvc restMedicalPrescriptionMockMvc;

    private MedicalPrescription medicalPrescription;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MedicalPrescriptionResource medicalPrescriptionResource = new MedicalPrescriptionResource(medicalPrescriptionService);
        this.restMedicalPrescriptionMockMvc = MockMvcBuilders.standaloneSetup(medicalPrescriptionResource)
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
    public static MedicalPrescription createEntity(EntityManager em) {
        MedicalPrescription medicalPrescription = new MedicalPrescription()
            .prescriptionDateTime(DEFAULT_PRESCRIPTION_DATE_TIME)
            .observations(DEFAULT_OBSERVATIONS);
        return medicalPrescription;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MedicalPrescription createUpdatedEntity(EntityManager em) {
        MedicalPrescription medicalPrescription = new MedicalPrescription()
            .prescriptionDateTime(UPDATED_PRESCRIPTION_DATE_TIME)
            .observations(UPDATED_OBSERVATIONS);
        return medicalPrescription;
    }

    @BeforeEach
    public void initTest() {
        medicalPrescription = createEntity(em);
    }

    @Test
    @Transactional
    public void createMedicalPrescription() throws Exception {
        int databaseSizeBeforeCreate = medicalPrescriptionRepository.findAll().size();

        // Create the MedicalPrescription
        restMedicalPrescriptionMockMvc.perform(post("/api/medical-prescriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicalPrescription)))
            .andExpect(status().isCreated());

        // Validate the MedicalPrescription in the database
        List<MedicalPrescription> medicalPrescriptionList = medicalPrescriptionRepository.findAll();
        assertThat(medicalPrescriptionList).hasSize(databaseSizeBeforeCreate + 1);
        MedicalPrescription testMedicalPrescription = medicalPrescriptionList.get(medicalPrescriptionList.size() - 1);
        assertThat(testMedicalPrescription.getPrescriptionDateTime()).isEqualTo(DEFAULT_PRESCRIPTION_DATE_TIME);
        assertThat(testMedicalPrescription.getObservations()).isEqualTo(DEFAULT_OBSERVATIONS);

        // Validate the MedicalPrescription in Elasticsearch
        verify(mockMedicalPrescriptionSearchRepository, times(1)).save(testMedicalPrescription);
    }

    @Test
    @Transactional
    public void createMedicalPrescriptionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = medicalPrescriptionRepository.findAll().size();

        // Create the MedicalPrescription with an existing ID
        medicalPrescription.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMedicalPrescriptionMockMvc.perform(post("/api/medical-prescriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicalPrescription)))
            .andExpect(status().isBadRequest());

        // Validate the MedicalPrescription in the database
        List<MedicalPrescription> medicalPrescriptionList = medicalPrescriptionRepository.findAll();
        assertThat(medicalPrescriptionList).hasSize(databaseSizeBeforeCreate);

        // Validate the MedicalPrescription in Elasticsearch
        verify(mockMedicalPrescriptionSearchRepository, times(0)).save(medicalPrescription);
    }


    @Test
    @Transactional
    public void getAllMedicalPrescriptions() throws Exception {
        // Initialize the database
        medicalPrescriptionRepository.saveAndFlush(medicalPrescription);

        // Get all the medicalPrescriptionList
        restMedicalPrescriptionMockMvc.perform(get("/api/medical-prescriptions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medicalPrescription.getId().intValue())))
            .andExpect(jsonPath("$.[*].prescriptionDateTime").value(hasItem(sameInstant(DEFAULT_PRESCRIPTION_DATE_TIME))))
            .andExpect(jsonPath("$.[*].observations").value(hasItem(DEFAULT_OBSERVATIONS.toString())));
    }
    
    @Test
    @Transactional
    public void getMedicalPrescription() throws Exception {
        // Initialize the database
        medicalPrescriptionRepository.saveAndFlush(medicalPrescription);

        // Get the medicalPrescription
        restMedicalPrescriptionMockMvc.perform(get("/api/medical-prescriptions/{id}", medicalPrescription.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(medicalPrescription.getId().intValue()))
            .andExpect(jsonPath("$.prescriptionDateTime").value(sameInstant(DEFAULT_PRESCRIPTION_DATE_TIME)))
            .andExpect(jsonPath("$.observations").value(DEFAULT_OBSERVATIONS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMedicalPrescription() throws Exception {
        // Get the medicalPrescription
        restMedicalPrescriptionMockMvc.perform(get("/api/medical-prescriptions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMedicalPrescription() throws Exception {
        // Initialize the database
        medicalPrescriptionService.save(medicalPrescription);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockMedicalPrescriptionSearchRepository);

        int databaseSizeBeforeUpdate = medicalPrescriptionRepository.findAll().size();

        // Update the medicalPrescription
        MedicalPrescription updatedMedicalPrescription = medicalPrescriptionRepository.findById(medicalPrescription.getId()).get();
        // Disconnect from session so that the updates on updatedMedicalPrescription are not directly saved in db
        em.detach(updatedMedicalPrescription);
        updatedMedicalPrescription
            .prescriptionDateTime(UPDATED_PRESCRIPTION_DATE_TIME)
            .observations(UPDATED_OBSERVATIONS);

        restMedicalPrescriptionMockMvc.perform(put("/api/medical-prescriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMedicalPrescription)))
            .andExpect(status().isOk());

        // Validate the MedicalPrescription in the database
        List<MedicalPrescription> medicalPrescriptionList = medicalPrescriptionRepository.findAll();
        assertThat(medicalPrescriptionList).hasSize(databaseSizeBeforeUpdate);
        MedicalPrescription testMedicalPrescription = medicalPrescriptionList.get(medicalPrescriptionList.size() - 1);
        assertThat(testMedicalPrescription.getPrescriptionDateTime()).isEqualTo(UPDATED_PRESCRIPTION_DATE_TIME);
        assertThat(testMedicalPrescription.getObservations()).isEqualTo(UPDATED_OBSERVATIONS);

        // Validate the MedicalPrescription in Elasticsearch
        verify(mockMedicalPrescriptionSearchRepository, times(1)).save(testMedicalPrescription);
    }

    @Test
    @Transactional
    public void updateNonExistingMedicalPrescription() throws Exception {
        int databaseSizeBeforeUpdate = medicalPrescriptionRepository.findAll().size();

        // Create the MedicalPrescription

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMedicalPrescriptionMockMvc.perform(put("/api/medical-prescriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicalPrescription)))
            .andExpect(status().isBadRequest());

        // Validate the MedicalPrescription in the database
        List<MedicalPrescription> medicalPrescriptionList = medicalPrescriptionRepository.findAll();
        assertThat(medicalPrescriptionList).hasSize(databaseSizeBeforeUpdate);

        // Validate the MedicalPrescription in Elasticsearch
        verify(mockMedicalPrescriptionSearchRepository, times(0)).save(medicalPrescription);
    }

    @Test
    @Transactional
    public void deleteMedicalPrescription() throws Exception {
        // Initialize the database
        medicalPrescriptionService.save(medicalPrescription);

        int databaseSizeBeforeDelete = medicalPrescriptionRepository.findAll().size();

        // Delete the medicalPrescription
        restMedicalPrescriptionMockMvc.perform(delete("/api/medical-prescriptions/{id}", medicalPrescription.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MedicalPrescription> medicalPrescriptionList = medicalPrescriptionRepository.findAll();
        assertThat(medicalPrescriptionList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the MedicalPrescription in Elasticsearch
        verify(mockMedicalPrescriptionSearchRepository, times(1)).deleteById(medicalPrescription.getId());
    }

    @Test
    @Transactional
    public void searchMedicalPrescription() throws Exception {
        // Initialize the database
        medicalPrescriptionService.save(medicalPrescription);
        when(mockMedicalPrescriptionSearchRepository.search(queryStringQuery("id:" + medicalPrescription.getId())))
            .thenReturn(Collections.singletonList(medicalPrescription));
        // Search the medicalPrescription
        restMedicalPrescriptionMockMvc.perform(get("/api/_search/medical-prescriptions?query=id:" + medicalPrescription.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medicalPrescription.getId().intValue())))
            .andExpect(jsonPath("$.[*].prescriptionDateTime").value(hasItem(sameInstant(DEFAULT_PRESCRIPTION_DATE_TIME))))
            .andExpect(jsonPath("$.[*].observations").value(hasItem(DEFAULT_OBSERVATIONS.toString())));
    }
}
