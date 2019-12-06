package com.zelix.yikondi.web.rest;

import com.zelix.yikondi.YikondiApp;
import com.zelix.yikondi.domain.PatientFavoriteDoctor;
import com.zelix.yikondi.repository.PatientFavoriteDoctorRepository;
import com.zelix.yikondi.repository.search.PatientFavoriteDoctorSearchRepository;
import com.zelix.yikondi.service.PatientFavoriteDoctorService;
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
 * Integration tests for the {@link PatientFavoriteDoctorResource} REST controller.
 */
@SpringBootTest(classes = YikondiApp.class)
public class PatientFavoriteDoctorResourceIT {

    private static final ZonedDateTime DEFAULT_ACTIVATION_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ACTIVATION_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private PatientFavoriteDoctorRepository patientFavoriteDoctorRepository;

    @Autowired
    private PatientFavoriteDoctorService patientFavoriteDoctorService;

    /**
     * This repository is mocked in the com.zelix.yikondi.repository.search test package.
     *
     * @see com.zelix.yikondi.repository.search.PatientFavoriteDoctorSearchRepositoryMockConfiguration
     */
    @Autowired
    private PatientFavoriteDoctorSearchRepository mockPatientFavoriteDoctorSearchRepository;

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

    private MockMvc restPatientFavoriteDoctorMockMvc;

    private PatientFavoriteDoctor patientFavoriteDoctor;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PatientFavoriteDoctorResource patientFavoriteDoctorResource = new PatientFavoriteDoctorResource(patientFavoriteDoctorService);
        this.restPatientFavoriteDoctorMockMvc = MockMvcBuilders.standaloneSetup(patientFavoriteDoctorResource)
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
    public static PatientFavoriteDoctor createEntity(EntityManager em) {
        PatientFavoriteDoctor patientFavoriteDoctor = new PatientFavoriteDoctor()
            .activationDate(DEFAULT_ACTIVATION_DATE);
        return patientFavoriteDoctor;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PatientFavoriteDoctor createUpdatedEntity(EntityManager em) {
        PatientFavoriteDoctor patientFavoriteDoctor = new PatientFavoriteDoctor()
            .activationDate(UPDATED_ACTIVATION_DATE);
        return patientFavoriteDoctor;
    }

    @BeforeEach
    public void initTest() {
        patientFavoriteDoctor = createEntity(em);
    }

    @Test
    @Transactional
    public void createPatientFavoriteDoctor() throws Exception {
        int databaseSizeBeforeCreate = patientFavoriteDoctorRepository.findAll().size();

        // Create the PatientFavoriteDoctor
        restPatientFavoriteDoctorMockMvc.perform(post("/api/patient-favorite-doctors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patientFavoriteDoctor)))
            .andExpect(status().isCreated());

        // Validate the PatientFavoriteDoctor in the database
        List<PatientFavoriteDoctor> patientFavoriteDoctorList = patientFavoriteDoctorRepository.findAll();
        assertThat(patientFavoriteDoctorList).hasSize(databaseSizeBeforeCreate + 1);
        PatientFavoriteDoctor testPatientFavoriteDoctor = patientFavoriteDoctorList.get(patientFavoriteDoctorList.size() - 1);
        assertThat(testPatientFavoriteDoctor.getActivationDate()).isEqualTo(DEFAULT_ACTIVATION_DATE);

        // Validate the PatientFavoriteDoctor in Elasticsearch
        verify(mockPatientFavoriteDoctorSearchRepository, times(1)).save(testPatientFavoriteDoctor);
    }

    @Test
    @Transactional
    public void createPatientFavoriteDoctorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = patientFavoriteDoctorRepository.findAll().size();

        // Create the PatientFavoriteDoctor with an existing ID
        patientFavoriteDoctor.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPatientFavoriteDoctorMockMvc.perform(post("/api/patient-favorite-doctors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patientFavoriteDoctor)))
            .andExpect(status().isBadRequest());

        // Validate the PatientFavoriteDoctor in the database
        List<PatientFavoriteDoctor> patientFavoriteDoctorList = patientFavoriteDoctorRepository.findAll();
        assertThat(patientFavoriteDoctorList).hasSize(databaseSizeBeforeCreate);

        // Validate the PatientFavoriteDoctor in Elasticsearch
        verify(mockPatientFavoriteDoctorSearchRepository, times(0)).save(patientFavoriteDoctor);
    }


    @Test
    @Transactional
    public void getAllPatientFavoriteDoctors() throws Exception {
        // Initialize the database
        patientFavoriteDoctorRepository.saveAndFlush(patientFavoriteDoctor);

        // Get all the patientFavoriteDoctorList
        restPatientFavoriteDoctorMockMvc.perform(get("/api/patient-favorite-doctors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(patientFavoriteDoctor.getId().intValue())))
            .andExpect(jsonPath("$.[*].activationDate").value(hasItem(sameInstant(DEFAULT_ACTIVATION_DATE))));
    }
    
    @Test
    @Transactional
    public void getPatientFavoriteDoctor() throws Exception {
        // Initialize the database
        patientFavoriteDoctorRepository.saveAndFlush(patientFavoriteDoctor);

        // Get the patientFavoriteDoctor
        restPatientFavoriteDoctorMockMvc.perform(get("/api/patient-favorite-doctors/{id}", patientFavoriteDoctor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(patientFavoriteDoctor.getId().intValue()))
            .andExpect(jsonPath("$.activationDate").value(sameInstant(DEFAULT_ACTIVATION_DATE)));
    }

    @Test
    @Transactional
    public void getNonExistingPatientFavoriteDoctor() throws Exception {
        // Get the patientFavoriteDoctor
        restPatientFavoriteDoctorMockMvc.perform(get("/api/patient-favorite-doctors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePatientFavoriteDoctor() throws Exception {
        // Initialize the database
        patientFavoriteDoctorService.save(patientFavoriteDoctor);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockPatientFavoriteDoctorSearchRepository);

        int databaseSizeBeforeUpdate = patientFavoriteDoctorRepository.findAll().size();

        // Update the patientFavoriteDoctor
        PatientFavoriteDoctor updatedPatientFavoriteDoctor = patientFavoriteDoctorRepository.findById(patientFavoriteDoctor.getId()).get();
        // Disconnect from session so that the updates on updatedPatientFavoriteDoctor are not directly saved in db
        em.detach(updatedPatientFavoriteDoctor);
        updatedPatientFavoriteDoctor
            .activationDate(UPDATED_ACTIVATION_DATE);

        restPatientFavoriteDoctorMockMvc.perform(put("/api/patient-favorite-doctors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPatientFavoriteDoctor)))
            .andExpect(status().isOk());

        // Validate the PatientFavoriteDoctor in the database
        List<PatientFavoriteDoctor> patientFavoriteDoctorList = patientFavoriteDoctorRepository.findAll();
        assertThat(patientFavoriteDoctorList).hasSize(databaseSizeBeforeUpdate);
        PatientFavoriteDoctor testPatientFavoriteDoctor = patientFavoriteDoctorList.get(patientFavoriteDoctorList.size() - 1);
        assertThat(testPatientFavoriteDoctor.getActivationDate()).isEqualTo(UPDATED_ACTIVATION_DATE);

        // Validate the PatientFavoriteDoctor in Elasticsearch
        verify(mockPatientFavoriteDoctorSearchRepository, times(1)).save(testPatientFavoriteDoctor);
    }

    @Test
    @Transactional
    public void updateNonExistingPatientFavoriteDoctor() throws Exception {
        int databaseSizeBeforeUpdate = patientFavoriteDoctorRepository.findAll().size();

        // Create the PatientFavoriteDoctor

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPatientFavoriteDoctorMockMvc.perform(put("/api/patient-favorite-doctors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patientFavoriteDoctor)))
            .andExpect(status().isBadRequest());

        // Validate the PatientFavoriteDoctor in the database
        List<PatientFavoriteDoctor> patientFavoriteDoctorList = patientFavoriteDoctorRepository.findAll();
        assertThat(patientFavoriteDoctorList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PatientFavoriteDoctor in Elasticsearch
        verify(mockPatientFavoriteDoctorSearchRepository, times(0)).save(patientFavoriteDoctor);
    }

    @Test
    @Transactional
    public void deletePatientFavoriteDoctor() throws Exception {
        // Initialize the database
        patientFavoriteDoctorService.save(patientFavoriteDoctor);

        int databaseSizeBeforeDelete = patientFavoriteDoctorRepository.findAll().size();

        // Delete the patientFavoriteDoctor
        restPatientFavoriteDoctorMockMvc.perform(delete("/api/patient-favorite-doctors/{id}", patientFavoriteDoctor.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PatientFavoriteDoctor> patientFavoriteDoctorList = patientFavoriteDoctorRepository.findAll();
        assertThat(patientFavoriteDoctorList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PatientFavoriteDoctor in Elasticsearch
        verify(mockPatientFavoriteDoctorSearchRepository, times(1)).deleteById(patientFavoriteDoctor.getId());
    }

    @Test
    @Transactional
    public void searchPatientFavoriteDoctor() throws Exception {
        // Initialize the database
        patientFavoriteDoctorService.save(patientFavoriteDoctor);
        when(mockPatientFavoriteDoctorSearchRepository.search(queryStringQuery("id:" + patientFavoriteDoctor.getId())))
            .thenReturn(Collections.singletonList(patientFavoriteDoctor));
        // Search the patientFavoriteDoctor
        restPatientFavoriteDoctorMockMvc.perform(get("/api/_search/patient-favorite-doctors?query=id:" + patientFavoriteDoctor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(patientFavoriteDoctor.getId().intValue())))
            .andExpect(jsonPath("$.[*].activationDate").value(hasItem(sameInstant(DEFAULT_ACTIVATION_DATE))));
    }
}
