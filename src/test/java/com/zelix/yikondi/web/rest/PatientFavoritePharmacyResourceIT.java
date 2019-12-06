package com.zelix.yikondi.web.rest;

import com.zelix.yikondi.YikondiApp;
import com.zelix.yikondi.domain.PatientFavoritePharmacy;
import com.zelix.yikondi.repository.PatientFavoritePharmacyRepository;
import com.zelix.yikondi.repository.search.PatientFavoritePharmacySearchRepository;
import com.zelix.yikondi.service.PatientFavoritePharmacyService;
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
 * Integration tests for the {@link PatientFavoritePharmacyResource} REST controller.
 */
@SpringBootTest(classes = YikondiApp.class)
public class PatientFavoritePharmacyResourceIT {

    private static final ZonedDateTime DEFAULT_ACTIVATION_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ACTIVATION_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private PatientFavoritePharmacyRepository patientFavoritePharmacyRepository;

    @Autowired
    private PatientFavoritePharmacyService patientFavoritePharmacyService;

    /**
     * This repository is mocked in the com.zelix.yikondi.repository.search test package.
     *
     * @see com.zelix.yikondi.repository.search.PatientFavoritePharmacySearchRepositoryMockConfiguration
     */
    @Autowired
    private PatientFavoritePharmacySearchRepository mockPatientFavoritePharmacySearchRepository;

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

    private MockMvc restPatientFavoritePharmacyMockMvc;

    private PatientFavoritePharmacy patientFavoritePharmacy;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PatientFavoritePharmacyResource patientFavoritePharmacyResource = new PatientFavoritePharmacyResource(patientFavoritePharmacyService);
        this.restPatientFavoritePharmacyMockMvc = MockMvcBuilders.standaloneSetup(patientFavoritePharmacyResource)
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
    public static PatientFavoritePharmacy createEntity(EntityManager em) {
        PatientFavoritePharmacy patientFavoritePharmacy = new PatientFavoritePharmacy()
            .activationDate(DEFAULT_ACTIVATION_DATE);
        return patientFavoritePharmacy;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PatientFavoritePharmacy createUpdatedEntity(EntityManager em) {
        PatientFavoritePharmacy patientFavoritePharmacy = new PatientFavoritePharmacy()
            .activationDate(UPDATED_ACTIVATION_DATE);
        return patientFavoritePharmacy;
    }

    @BeforeEach
    public void initTest() {
        patientFavoritePharmacy = createEntity(em);
    }

    @Test
    @Transactional
    public void createPatientFavoritePharmacy() throws Exception {
        int databaseSizeBeforeCreate = patientFavoritePharmacyRepository.findAll().size();

        // Create the PatientFavoritePharmacy
        restPatientFavoritePharmacyMockMvc.perform(post("/api/patient-favorite-pharmacies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patientFavoritePharmacy)))
            .andExpect(status().isCreated());

        // Validate the PatientFavoritePharmacy in the database
        List<PatientFavoritePharmacy> patientFavoritePharmacyList = patientFavoritePharmacyRepository.findAll();
        assertThat(patientFavoritePharmacyList).hasSize(databaseSizeBeforeCreate + 1);
        PatientFavoritePharmacy testPatientFavoritePharmacy = patientFavoritePharmacyList.get(patientFavoritePharmacyList.size() - 1);
        assertThat(testPatientFavoritePharmacy.getActivationDate()).isEqualTo(DEFAULT_ACTIVATION_DATE);

        // Validate the PatientFavoritePharmacy in Elasticsearch
        verify(mockPatientFavoritePharmacySearchRepository, times(1)).save(testPatientFavoritePharmacy);
    }

    @Test
    @Transactional
    public void createPatientFavoritePharmacyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = patientFavoritePharmacyRepository.findAll().size();

        // Create the PatientFavoritePharmacy with an existing ID
        patientFavoritePharmacy.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPatientFavoritePharmacyMockMvc.perform(post("/api/patient-favorite-pharmacies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patientFavoritePharmacy)))
            .andExpect(status().isBadRequest());

        // Validate the PatientFavoritePharmacy in the database
        List<PatientFavoritePharmacy> patientFavoritePharmacyList = patientFavoritePharmacyRepository.findAll();
        assertThat(patientFavoritePharmacyList).hasSize(databaseSizeBeforeCreate);

        // Validate the PatientFavoritePharmacy in Elasticsearch
        verify(mockPatientFavoritePharmacySearchRepository, times(0)).save(patientFavoritePharmacy);
    }


    @Test
    @Transactional
    public void getAllPatientFavoritePharmacies() throws Exception {
        // Initialize the database
        patientFavoritePharmacyRepository.saveAndFlush(patientFavoritePharmacy);

        // Get all the patientFavoritePharmacyList
        restPatientFavoritePharmacyMockMvc.perform(get("/api/patient-favorite-pharmacies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(patientFavoritePharmacy.getId().intValue())))
            .andExpect(jsonPath("$.[*].activationDate").value(hasItem(sameInstant(DEFAULT_ACTIVATION_DATE))));
    }
    
    @Test
    @Transactional
    public void getPatientFavoritePharmacy() throws Exception {
        // Initialize the database
        patientFavoritePharmacyRepository.saveAndFlush(patientFavoritePharmacy);

        // Get the patientFavoritePharmacy
        restPatientFavoritePharmacyMockMvc.perform(get("/api/patient-favorite-pharmacies/{id}", patientFavoritePharmacy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(patientFavoritePharmacy.getId().intValue()))
            .andExpect(jsonPath("$.activationDate").value(sameInstant(DEFAULT_ACTIVATION_DATE)));
    }

    @Test
    @Transactional
    public void getNonExistingPatientFavoritePharmacy() throws Exception {
        // Get the patientFavoritePharmacy
        restPatientFavoritePharmacyMockMvc.perform(get("/api/patient-favorite-pharmacies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePatientFavoritePharmacy() throws Exception {
        // Initialize the database
        patientFavoritePharmacyService.save(patientFavoritePharmacy);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockPatientFavoritePharmacySearchRepository);

        int databaseSizeBeforeUpdate = patientFavoritePharmacyRepository.findAll().size();

        // Update the patientFavoritePharmacy
        PatientFavoritePharmacy updatedPatientFavoritePharmacy = patientFavoritePharmacyRepository.findById(patientFavoritePharmacy.getId()).get();
        // Disconnect from session so that the updates on updatedPatientFavoritePharmacy are not directly saved in db
        em.detach(updatedPatientFavoritePharmacy);
        updatedPatientFavoritePharmacy
            .activationDate(UPDATED_ACTIVATION_DATE);

        restPatientFavoritePharmacyMockMvc.perform(put("/api/patient-favorite-pharmacies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPatientFavoritePharmacy)))
            .andExpect(status().isOk());

        // Validate the PatientFavoritePharmacy in the database
        List<PatientFavoritePharmacy> patientFavoritePharmacyList = patientFavoritePharmacyRepository.findAll();
        assertThat(patientFavoritePharmacyList).hasSize(databaseSizeBeforeUpdate);
        PatientFavoritePharmacy testPatientFavoritePharmacy = patientFavoritePharmacyList.get(patientFavoritePharmacyList.size() - 1);
        assertThat(testPatientFavoritePharmacy.getActivationDate()).isEqualTo(UPDATED_ACTIVATION_DATE);

        // Validate the PatientFavoritePharmacy in Elasticsearch
        verify(mockPatientFavoritePharmacySearchRepository, times(1)).save(testPatientFavoritePharmacy);
    }

    @Test
    @Transactional
    public void updateNonExistingPatientFavoritePharmacy() throws Exception {
        int databaseSizeBeforeUpdate = patientFavoritePharmacyRepository.findAll().size();

        // Create the PatientFavoritePharmacy

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPatientFavoritePharmacyMockMvc.perform(put("/api/patient-favorite-pharmacies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patientFavoritePharmacy)))
            .andExpect(status().isBadRequest());

        // Validate the PatientFavoritePharmacy in the database
        List<PatientFavoritePharmacy> patientFavoritePharmacyList = patientFavoritePharmacyRepository.findAll();
        assertThat(patientFavoritePharmacyList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PatientFavoritePharmacy in Elasticsearch
        verify(mockPatientFavoritePharmacySearchRepository, times(0)).save(patientFavoritePharmacy);
    }

    @Test
    @Transactional
    public void deletePatientFavoritePharmacy() throws Exception {
        // Initialize the database
        patientFavoritePharmacyService.save(patientFavoritePharmacy);

        int databaseSizeBeforeDelete = patientFavoritePharmacyRepository.findAll().size();

        // Delete the patientFavoritePharmacy
        restPatientFavoritePharmacyMockMvc.perform(delete("/api/patient-favorite-pharmacies/{id}", patientFavoritePharmacy.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PatientFavoritePharmacy> patientFavoritePharmacyList = patientFavoritePharmacyRepository.findAll();
        assertThat(patientFavoritePharmacyList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PatientFavoritePharmacy in Elasticsearch
        verify(mockPatientFavoritePharmacySearchRepository, times(1)).deleteById(patientFavoritePharmacy.getId());
    }

    @Test
    @Transactional
    public void searchPatientFavoritePharmacy() throws Exception {
        // Initialize the database
        patientFavoritePharmacyService.save(patientFavoritePharmacy);
        when(mockPatientFavoritePharmacySearchRepository.search(queryStringQuery("id:" + patientFavoritePharmacy.getId())))
            .thenReturn(Collections.singletonList(patientFavoritePharmacy));
        // Search the patientFavoritePharmacy
        restPatientFavoritePharmacyMockMvc.perform(get("/api/_search/patient-favorite-pharmacies?query=id:" + patientFavoritePharmacy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(patientFavoritePharmacy.getId().intValue())))
            .andExpect(jsonPath("$.[*].activationDate").value(hasItem(sameInstant(DEFAULT_ACTIVATION_DATE))));
    }
}
