package com.zelix.yikondi.web.rest;

import com.zelix.yikondi.YikondiApp;
import com.zelix.yikondi.domain.PatientAppointement;
import com.zelix.yikondi.repository.PatientAppointementRepository;
import com.zelix.yikondi.repository.search.PatientAppointementSearchRepository;
import com.zelix.yikondi.service.PatientAppointementService;
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
 * Integration tests for the {@link PatientAppointementResource} REST controller.
 */
@SpringBootTest(classes = YikondiApp.class)
public class PatientAppointementResourceIT {

    private static final ZonedDateTime DEFAULT_APPOINTEMENT_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_APPOINTEMENT_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_APPOINTEMENT_MAKING_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_APPOINTEMENT_MAKING_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private PatientAppointementRepository patientAppointementRepository;

    @Autowired
    private PatientAppointementService patientAppointementService;

    /**
     * This repository is mocked in the com.zelix.yikondi.repository.search test package.
     *
     * @see com.zelix.yikondi.repository.search.PatientAppointementSearchRepositoryMockConfiguration
     */
    @Autowired
    private PatientAppointementSearchRepository mockPatientAppointementSearchRepository;

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

    private MockMvc restPatientAppointementMockMvc;

    private PatientAppointement patientAppointement;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PatientAppointementResource patientAppointementResource = new PatientAppointementResource(patientAppointementService);
        this.restPatientAppointementMockMvc = MockMvcBuilders.standaloneSetup(patientAppointementResource)
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
    public static PatientAppointement createEntity(EntityManager em) {
        PatientAppointement patientAppointement = new PatientAppointement()
            .appointementDateTime(DEFAULT_APPOINTEMENT_DATE_TIME)
            .appointementMakingDateTime(DEFAULT_APPOINTEMENT_MAKING_DATE_TIME);
        return patientAppointement;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PatientAppointement createUpdatedEntity(EntityManager em) {
        PatientAppointement patientAppointement = new PatientAppointement()
            .appointementDateTime(UPDATED_APPOINTEMENT_DATE_TIME)
            .appointementMakingDateTime(UPDATED_APPOINTEMENT_MAKING_DATE_TIME);
        return patientAppointement;
    }

    @BeforeEach
    public void initTest() {
        patientAppointement = createEntity(em);
    }

    @Test
    @Transactional
    public void createPatientAppointement() throws Exception {
        int databaseSizeBeforeCreate = patientAppointementRepository.findAll().size();

        // Create the PatientAppointement
        restPatientAppointementMockMvc.perform(post("/api/patient-appointements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patientAppointement)))
            .andExpect(status().isCreated());

        // Validate the PatientAppointement in the database
        List<PatientAppointement> patientAppointementList = patientAppointementRepository.findAll();
        assertThat(patientAppointementList).hasSize(databaseSizeBeforeCreate + 1);
        PatientAppointement testPatientAppointement = patientAppointementList.get(patientAppointementList.size() - 1);
        assertThat(testPatientAppointement.getAppointementDateTime()).isEqualTo(DEFAULT_APPOINTEMENT_DATE_TIME);
        assertThat(testPatientAppointement.getAppointementMakingDateTime()).isEqualTo(DEFAULT_APPOINTEMENT_MAKING_DATE_TIME);

        // Validate the PatientAppointement in Elasticsearch
        verify(mockPatientAppointementSearchRepository, times(1)).save(testPatientAppointement);
    }

    @Test
    @Transactional
    public void createPatientAppointementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = patientAppointementRepository.findAll().size();

        // Create the PatientAppointement with an existing ID
        patientAppointement.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPatientAppointementMockMvc.perform(post("/api/patient-appointements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patientAppointement)))
            .andExpect(status().isBadRequest());

        // Validate the PatientAppointement in the database
        List<PatientAppointement> patientAppointementList = patientAppointementRepository.findAll();
        assertThat(patientAppointementList).hasSize(databaseSizeBeforeCreate);

        // Validate the PatientAppointement in Elasticsearch
        verify(mockPatientAppointementSearchRepository, times(0)).save(patientAppointement);
    }


    @Test
    @Transactional
    public void getAllPatientAppointements() throws Exception {
        // Initialize the database
        patientAppointementRepository.saveAndFlush(patientAppointement);

        // Get all the patientAppointementList
        restPatientAppointementMockMvc.perform(get("/api/patient-appointements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(patientAppointement.getId().intValue())))
            .andExpect(jsonPath("$.[*].appointementDateTime").value(hasItem(sameInstant(DEFAULT_APPOINTEMENT_DATE_TIME))))
            .andExpect(jsonPath("$.[*].appointementMakingDateTime").value(hasItem(sameInstant(DEFAULT_APPOINTEMENT_MAKING_DATE_TIME))));
    }
    
    @Test
    @Transactional
    public void getPatientAppointement() throws Exception {
        // Initialize the database
        patientAppointementRepository.saveAndFlush(patientAppointement);

        // Get the patientAppointement
        restPatientAppointementMockMvc.perform(get("/api/patient-appointements/{id}", patientAppointement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(patientAppointement.getId().intValue()))
            .andExpect(jsonPath("$.appointementDateTime").value(sameInstant(DEFAULT_APPOINTEMENT_DATE_TIME)))
            .andExpect(jsonPath("$.appointementMakingDateTime").value(sameInstant(DEFAULT_APPOINTEMENT_MAKING_DATE_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingPatientAppointement() throws Exception {
        // Get the patientAppointement
        restPatientAppointementMockMvc.perform(get("/api/patient-appointements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePatientAppointement() throws Exception {
        // Initialize the database
        patientAppointementService.save(patientAppointement);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockPatientAppointementSearchRepository);

        int databaseSizeBeforeUpdate = patientAppointementRepository.findAll().size();

        // Update the patientAppointement
        PatientAppointement updatedPatientAppointement = patientAppointementRepository.findById(patientAppointement.getId()).get();
        // Disconnect from session so that the updates on updatedPatientAppointement are not directly saved in db
        em.detach(updatedPatientAppointement);
        updatedPatientAppointement
            .appointementDateTime(UPDATED_APPOINTEMENT_DATE_TIME)
            .appointementMakingDateTime(UPDATED_APPOINTEMENT_MAKING_DATE_TIME);

        restPatientAppointementMockMvc.perform(put("/api/patient-appointements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPatientAppointement)))
            .andExpect(status().isOk());

        // Validate the PatientAppointement in the database
        List<PatientAppointement> patientAppointementList = patientAppointementRepository.findAll();
        assertThat(patientAppointementList).hasSize(databaseSizeBeforeUpdate);
        PatientAppointement testPatientAppointement = patientAppointementList.get(patientAppointementList.size() - 1);
        assertThat(testPatientAppointement.getAppointementDateTime()).isEqualTo(UPDATED_APPOINTEMENT_DATE_TIME);
        assertThat(testPatientAppointement.getAppointementMakingDateTime()).isEqualTo(UPDATED_APPOINTEMENT_MAKING_DATE_TIME);

        // Validate the PatientAppointement in Elasticsearch
        verify(mockPatientAppointementSearchRepository, times(1)).save(testPatientAppointement);
    }

    @Test
    @Transactional
    public void updateNonExistingPatientAppointement() throws Exception {
        int databaseSizeBeforeUpdate = patientAppointementRepository.findAll().size();

        // Create the PatientAppointement

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPatientAppointementMockMvc.perform(put("/api/patient-appointements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patientAppointement)))
            .andExpect(status().isBadRequest());

        // Validate the PatientAppointement in the database
        List<PatientAppointement> patientAppointementList = patientAppointementRepository.findAll();
        assertThat(patientAppointementList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PatientAppointement in Elasticsearch
        verify(mockPatientAppointementSearchRepository, times(0)).save(patientAppointement);
    }

    @Test
    @Transactional
    public void deletePatientAppointement() throws Exception {
        // Initialize the database
        patientAppointementService.save(patientAppointement);

        int databaseSizeBeforeDelete = patientAppointementRepository.findAll().size();

        // Delete the patientAppointement
        restPatientAppointementMockMvc.perform(delete("/api/patient-appointements/{id}", patientAppointement.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PatientAppointement> patientAppointementList = patientAppointementRepository.findAll();
        assertThat(patientAppointementList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PatientAppointement in Elasticsearch
        verify(mockPatientAppointementSearchRepository, times(1)).deleteById(patientAppointement.getId());
    }

    @Test
    @Transactional
    public void searchPatientAppointement() throws Exception {
        // Initialize the database
        patientAppointementService.save(patientAppointement);
        when(mockPatientAppointementSearchRepository.search(queryStringQuery("id:" + patientAppointement.getId())))
            .thenReturn(Collections.singletonList(patientAppointement));
        // Search the patientAppointement
        restPatientAppointementMockMvc.perform(get("/api/_search/patient-appointements?query=id:" + patientAppointement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(patientAppointement.getId().intValue())))
            .andExpect(jsonPath("$.[*].appointementDateTime").value(hasItem(sameInstant(DEFAULT_APPOINTEMENT_DATE_TIME))))
            .andExpect(jsonPath("$.[*].appointementMakingDateTime").value(hasItem(sameInstant(DEFAULT_APPOINTEMENT_MAKING_DATE_TIME))));
    }
}
