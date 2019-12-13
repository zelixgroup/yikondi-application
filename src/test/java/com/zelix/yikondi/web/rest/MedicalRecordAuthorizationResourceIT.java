package com.zelix.yikondi.web.rest;

import com.zelix.yikondi.YikondiApp;
import com.zelix.yikondi.domain.MedicalRecordAuthorization;
import com.zelix.yikondi.repository.MedicalRecordAuthorizationRepository;
import com.zelix.yikondi.repository.search.MedicalRecordAuthorizationSearchRepository;
import com.zelix.yikondi.service.MedicalRecordAuthorizationService;
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
 * Integration tests for the {@link MedicalRecordAuthorizationResource} REST controller.
 */
@SpringBootTest(classes = YikondiApp.class)
public class MedicalRecordAuthorizationResourceIT {

    private static final ZonedDateTime DEFAULT_AUTHORIZATION_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_AUTHORIZATION_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_AUTHORIZATION_START_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_AUTHORIZATION_START_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_AUTHORIZATION_END_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_AUTHORIZATION_END_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_OBSERVATIONS = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVATIONS = "BBBBBBBBBB";

    @Autowired
    private MedicalRecordAuthorizationRepository medicalRecordAuthorizationRepository;

    @Autowired
    private MedicalRecordAuthorizationService medicalRecordAuthorizationService;

    /**
     * This repository is mocked in the com.zelix.yikondi.repository.search test package.
     *
     * @see com.zelix.yikondi.repository.search.MedicalRecordAuthorizationSearchRepositoryMockConfiguration
     */
    @Autowired
    private MedicalRecordAuthorizationSearchRepository mockMedicalRecordAuthorizationSearchRepository;

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

    private MockMvc restMedicalRecordAuthorizationMockMvc;

    private MedicalRecordAuthorization medicalRecordAuthorization;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MedicalRecordAuthorizationResource medicalRecordAuthorizationResource = new MedicalRecordAuthorizationResource(medicalRecordAuthorizationService);
        this.restMedicalRecordAuthorizationMockMvc = MockMvcBuilders.standaloneSetup(medicalRecordAuthorizationResource)
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
    public static MedicalRecordAuthorization createEntity(EntityManager em) {
        MedicalRecordAuthorization medicalRecordAuthorization = new MedicalRecordAuthorization()
            .authorizationDateTime(DEFAULT_AUTHORIZATION_DATE_TIME)
            .authorizationStartDateTime(DEFAULT_AUTHORIZATION_START_DATE_TIME)
            .authorizationEndDateTime(DEFAULT_AUTHORIZATION_END_DATE_TIME)
            .observations(DEFAULT_OBSERVATIONS);
        return medicalRecordAuthorization;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MedicalRecordAuthorization createUpdatedEntity(EntityManager em) {
        MedicalRecordAuthorization medicalRecordAuthorization = new MedicalRecordAuthorization()
            .authorizationDateTime(UPDATED_AUTHORIZATION_DATE_TIME)
            .authorizationStartDateTime(UPDATED_AUTHORIZATION_START_DATE_TIME)
            .authorizationEndDateTime(UPDATED_AUTHORIZATION_END_DATE_TIME)
            .observations(UPDATED_OBSERVATIONS);
        return medicalRecordAuthorization;
    }

    @BeforeEach
    public void initTest() {
        medicalRecordAuthorization = createEntity(em);
    }

    @Test
    @Transactional
    public void createMedicalRecordAuthorization() throws Exception {
        int databaseSizeBeforeCreate = medicalRecordAuthorizationRepository.findAll().size();

        // Create the MedicalRecordAuthorization
        restMedicalRecordAuthorizationMockMvc.perform(post("/api/medical-record-authorizations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicalRecordAuthorization)))
            .andExpect(status().isCreated());

        // Validate the MedicalRecordAuthorization in the database
        List<MedicalRecordAuthorization> medicalRecordAuthorizationList = medicalRecordAuthorizationRepository.findAll();
        assertThat(medicalRecordAuthorizationList).hasSize(databaseSizeBeforeCreate + 1);
        MedicalRecordAuthorization testMedicalRecordAuthorization = medicalRecordAuthorizationList.get(medicalRecordAuthorizationList.size() - 1);
        assertThat(testMedicalRecordAuthorization.getAuthorizationDateTime()).isEqualTo(DEFAULT_AUTHORIZATION_DATE_TIME);
        assertThat(testMedicalRecordAuthorization.getAuthorizationStartDateTime()).isEqualTo(DEFAULT_AUTHORIZATION_START_DATE_TIME);
        assertThat(testMedicalRecordAuthorization.getAuthorizationEndDateTime()).isEqualTo(DEFAULT_AUTHORIZATION_END_DATE_TIME);
        assertThat(testMedicalRecordAuthorization.getObservations()).isEqualTo(DEFAULT_OBSERVATIONS);

        // Validate the MedicalRecordAuthorization in Elasticsearch
        verify(mockMedicalRecordAuthorizationSearchRepository, times(1)).save(testMedicalRecordAuthorization);
    }

    @Test
    @Transactional
    public void createMedicalRecordAuthorizationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = medicalRecordAuthorizationRepository.findAll().size();

        // Create the MedicalRecordAuthorization with an existing ID
        medicalRecordAuthorization.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMedicalRecordAuthorizationMockMvc.perform(post("/api/medical-record-authorizations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicalRecordAuthorization)))
            .andExpect(status().isBadRequest());

        // Validate the MedicalRecordAuthorization in the database
        List<MedicalRecordAuthorization> medicalRecordAuthorizationList = medicalRecordAuthorizationRepository.findAll();
        assertThat(medicalRecordAuthorizationList).hasSize(databaseSizeBeforeCreate);

        // Validate the MedicalRecordAuthorization in Elasticsearch
        verify(mockMedicalRecordAuthorizationSearchRepository, times(0)).save(medicalRecordAuthorization);
    }


    @Test
    @Transactional
    public void getAllMedicalRecordAuthorizations() throws Exception {
        // Initialize the database
        medicalRecordAuthorizationRepository.saveAndFlush(medicalRecordAuthorization);

        // Get all the medicalRecordAuthorizationList
        restMedicalRecordAuthorizationMockMvc.perform(get("/api/medical-record-authorizations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medicalRecordAuthorization.getId().intValue())))
            .andExpect(jsonPath("$.[*].authorizationDateTime").value(hasItem(sameInstant(DEFAULT_AUTHORIZATION_DATE_TIME))))
            .andExpect(jsonPath("$.[*].authorizationStartDateTime").value(hasItem(sameInstant(DEFAULT_AUTHORIZATION_START_DATE_TIME))))
            .andExpect(jsonPath("$.[*].authorizationEndDateTime").value(hasItem(sameInstant(DEFAULT_AUTHORIZATION_END_DATE_TIME))))
            .andExpect(jsonPath("$.[*].observations").value(hasItem(DEFAULT_OBSERVATIONS)));
    }
    
    @Test
    @Transactional
    public void getMedicalRecordAuthorization() throws Exception {
        // Initialize the database
        medicalRecordAuthorizationRepository.saveAndFlush(medicalRecordAuthorization);

        // Get the medicalRecordAuthorization
        restMedicalRecordAuthorizationMockMvc.perform(get("/api/medical-record-authorizations/{id}", medicalRecordAuthorization.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(medicalRecordAuthorization.getId().intValue()))
            .andExpect(jsonPath("$.authorizationDateTime").value(sameInstant(DEFAULT_AUTHORIZATION_DATE_TIME)))
            .andExpect(jsonPath("$.authorizationStartDateTime").value(sameInstant(DEFAULT_AUTHORIZATION_START_DATE_TIME)))
            .andExpect(jsonPath("$.authorizationEndDateTime").value(sameInstant(DEFAULT_AUTHORIZATION_END_DATE_TIME)))
            .andExpect(jsonPath("$.observations").value(DEFAULT_OBSERVATIONS));
    }

    @Test
    @Transactional
    public void getNonExistingMedicalRecordAuthorization() throws Exception {
        // Get the medicalRecordAuthorization
        restMedicalRecordAuthorizationMockMvc.perform(get("/api/medical-record-authorizations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMedicalRecordAuthorization() throws Exception {
        // Initialize the database
        medicalRecordAuthorizationService.save(medicalRecordAuthorization);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockMedicalRecordAuthorizationSearchRepository);

        int databaseSizeBeforeUpdate = medicalRecordAuthorizationRepository.findAll().size();

        // Update the medicalRecordAuthorization
        MedicalRecordAuthorization updatedMedicalRecordAuthorization = medicalRecordAuthorizationRepository.findById(medicalRecordAuthorization.getId()).get();
        // Disconnect from session so that the updates on updatedMedicalRecordAuthorization are not directly saved in db
        em.detach(updatedMedicalRecordAuthorization);
        updatedMedicalRecordAuthorization
            .authorizationDateTime(UPDATED_AUTHORIZATION_DATE_TIME)
            .authorizationStartDateTime(UPDATED_AUTHORIZATION_START_DATE_TIME)
            .authorizationEndDateTime(UPDATED_AUTHORIZATION_END_DATE_TIME)
            .observations(UPDATED_OBSERVATIONS);

        restMedicalRecordAuthorizationMockMvc.perform(put("/api/medical-record-authorizations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMedicalRecordAuthorization)))
            .andExpect(status().isOk());

        // Validate the MedicalRecordAuthorization in the database
        List<MedicalRecordAuthorization> medicalRecordAuthorizationList = medicalRecordAuthorizationRepository.findAll();
        assertThat(medicalRecordAuthorizationList).hasSize(databaseSizeBeforeUpdate);
        MedicalRecordAuthorization testMedicalRecordAuthorization = medicalRecordAuthorizationList.get(medicalRecordAuthorizationList.size() - 1);
        assertThat(testMedicalRecordAuthorization.getAuthorizationDateTime()).isEqualTo(UPDATED_AUTHORIZATION_DATE_TIME);
        assertThat(testMedicalRecordAuthorization.getAuthorizationStartDateTime()).isEqualTo(UPDATED_AUTHORIZATION_START_DATE_TIME);
        assertThat(testMedicalRecordAuthorization.getAuthorizationEndDateTime()).isEqualTo(UPDATED_AUTHORIZATION_END_DATE_TIME);
        assertThat(testMedicalRecordAuthorization.getObservations()).isEqualTo(UPDATED_OBSERVATIONS);

        // Validate the MedicalRecordAuthorization in Elasticsearch
        verify(mockMedicalRecordAuthorizationSearchRepository, times(1)).save(testMedicalRecordAuthorization);
    }

    @Test
    @Transactional
    public void updateNonExistingMedicalRecordAuthorization() throws Exception {
        int databaseSizeBeforeUpdate = medicalRecordAuthorizationRepository.findAll().size();

        // Create the MedicalRecordAuthorization

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMedicalRecordAuthorizationMockMvc.perform(put("/api/medical-record-authorizations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicalRecordAuthorization)))
            .andExpect(status().isBadRequest());

        // Validate the MedicalRecordAuthorization in the database
        List<MedicalRecordAuthorization> medicalRecordAuthorizationList = medicalRecordAuthorizationRepository.findAll();
        assertThat(medicalRecordAuthorizationList).hasSize(databaseSizeBeforeUpdate);

        // Validate the MedicalRecordAuthorization in Elasticsearch
        verify(mockMedicalRecordAuthorizationSearchRepository, times(0)).save(medicalRecordAuthorization);
    }

    @Test
    @Transactional
    public void deleteMedicalRecordAuthorization() throws Exception {
        // Initialize the database
        medicalRecordAuthorizationService.save(medicalRecordAuthorization);

        int databaseSizeBeforeDelete = medicalRecordAuthorizationRepository.findAll().size();

        // Delete the medicalRecordAuthorization
        restMedicalRecordAuthorizationMockMvc.perform(delete("/api/medical-record-authorizations/{id}", medicalRecordAuthorization.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MedicalRecordAuthorization> medicalRecordAuthorizationList = medicalRecordAuthorizationRepository.findAll();
        assertThat(medicalRecordAuthorizationList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the MedicalRecordAuthorization in Elasticsearch
        verify(mockMedicalRecordAuthorizationSearchRepository, times(1)).deleteById(medicalRecordAuthorization.getId());
    }

    @Test
    @Transactional
    public void searchMedicalRecordAuthorization() throws Exception {
        // Initialize the database
        medicalRecordAuthorizationService.save(medicalRecordAuthorization);
        when(mockMedicalRecordAuthorizationSearchRepository.search(queryStringQuery("id:" + medicalRecordAuthorization.getId())))
            .thenReturn(Collections.singletonList(medicalRecordAuthorization));
        // Search the medicalRecordAuthorization
        restMedicalRecordAuthorizationMockMvc.perform(get("/api/_search/medical-record-authorizations?query=id:" + medicalRecordAuthorization.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medicalRecordAuthorization.getId().intValue())))
            .andExpect(jsonPath("$.[*].authorizationDateTime").value(hasItem(sameInstant(DEFAULT_AUTHORIZATION_DATE_TIME))))
            .andExpect(jsonPath("$.[*].authorizationStartDateTime").value(hasItem(sameInstant(DEFAULT_AUTHORIZATION_START_DATE_TIME))))
            .andExpect(jsonPath("$.[*].authorizationEndDateTime").value(hasItem(sameInstant(DEFAULT_AUTHORIZATION_END_DATE_TIME))))
            .andExpect(jsonPath("$.[*].observations").value(hasItem(DEFAULT_OBSERVATIONS)));
    }
}
