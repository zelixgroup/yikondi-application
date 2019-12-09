package com.zelix.yikondi.web.rest;

import com.zelix.yikondi.YikondiApp;
import com.zelix.yikondi.domain.PatientPathology;
import com.zelix.yikondi.repository.PatientPathologyRepository;
import com.zelix.yikondi.repository.search.PatientPathologySearchRepository;
import com.zelix.yikondi.service.PatientPathologyService;
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
 * Integration tests for the {@link PatientPathologyResource} REST controller.
 */
@SpringBootTest(classes = YikondiApp.class)
public class PatientPathologyResourceIT {

    private static final LocalDate DEFAULT_OBSERVATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_OBSERVATION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_OBSERVATIONS = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVATIONS = "BBBBBBBBBB";

    @Autowired
    private PatientPathologyRepository patientPathologyRepository;

    @Autowired
    private PatientPathologyService patientPathologyService;

    /**
     * This repository is mocked in the com.zelix.yikondi.repository.search test package.
     *
     * @see com.zelix.yikondi.repository.search.PatientPathologySearchRepositoryMockConfiguration
     */
    @Autowired
    private PatientPathologySearchRepository mockPatientPathologySearchRepository;

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

    private MockMvc restPatientPathologyMockMvc;

    private PatientPathology patientPathology;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PatientPathologyResource patientPathologyResource = new PatientPathologyResource(patientPathologyService);
        this.restPatientPathologyMockMvc = MockMvcBuilders.standaloneSetup(patientPathologyResource)
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
    public static PatientPathology createEntity(EntityManager em) {
        PatientPathology patientPathology = new PatientPathology()
            .observationDate(DEFAULT_OBSERVATION_DATE)
            .observations(DEFAULT_OBSERVATIONS);
        return patientPathology;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PatientPathology createUpdatedEntity(EntityManager em) {
        PatientPathology patientPathology = new PatientPathology()
            .observationDate(UPDATED_OBSERVATION_DATE)
            .observations(UPDATED_OBSERVATIONS);
        return patientPathology;
    }

    @BeforeEach
    public void initTest() {
        patientPathology = createEntity(em);
    }

    @Test
    @Transactional
    public void createPatientPathology() throws Exception {
        int databaseSizeBeforeCreate = patientPathologyRepository.findAll().size();

        // Create the PatientPathology
        restPatientPathologyMockMvc.perform(post("/api/patient-pathologies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patientPathology)))
            .andExpect(status().isCreated());

        // Validate the PatientPathology in the database
        List<PatientPathology> patientPathologyList = patientPathologyRepository.findAll();
        assertThat(patientPathologyList).hasSize(databaseSizeBeforeCreate + 1);
        PatientPathology testPatientPathology = patientPathologyList.get(patientPathologyList.size() - 1);
        assertThat(testPatientPathology.getObservationDate()).isEqualTo(DEFAULT_OBSERVATION_DATE);
        assertThat(testPatientPathology.getObservations()).isEqualTo(DEFAULT_OBSERVATIONS);

        // Validate the PatientPathology in Elasticsearch
        verify(mockPatientPathologySearchRepository, times(1)).save(testPatientPathology);
    }

    @Test
    @Transactional
    public void createPatientPathologyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = patientPathologyRepository.findAll().size();

        // Create the PatientPathology with an existing ID
        patientPathology.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPatientPathologyMockMvc.perform(post("/api/patient-pathologies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patientPathology)))
            .andExpect(status().isBadRequest());

        // Validate the PatientPathology in the database
        List<PatientPathology> patientPathologyList = patientPathologyRepository.findAll();
        assertThat(patientPathologyList).hasSize(databaseSizeBeforeCreate);

        // Validate the PatientPathology in Elasticsearch
        verify(mockPatientPathologySearchRepository, times(0)).save(patientPathology);
    }


    @Test
    @Transactional
    public void getAllPatientPathologies() throws Exception {
        // Initialize the database
        patientPathologyRepository.saveAndFlush(patientPathology);

        // Get all the patientPathologyList
        restPatientPathologyMockMvc.perform(get("/api/patient-pathologies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(patientPathology.getId().intValue())))
            .andExpect(jsonPath("$.[*].observationDate").value(hasItem(DEFAULT_OBSERVATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].observations").value(hasItem(DEFAULT_OBSERVATIONS)));
    }
    
    @Test
    @Transactional
    public void getPatientPathology() throws Exception {
        // Initialize the database
        patientPathologyRepository.saveAndFlush(patientPathology);

        // Get the patientPathology
        restPatientPathologyMockMvc.perform(get("/api/patient-pathologies/{id}", patientPathology.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(patientPathology.getId().intValue()))
            .andExpect(jsonPath("$.observationDate").value(DEFAULT_OBSERVATION_DATE.toString()))
            .andExpect(jsonPath("$.observations").value(DEFAULT_OBSERVATIONS));
    }

    @Test
    @Transactional
    public void getNonExistingPatientPathology() throws Exception {
        // Get the patientPathology
        restPatientPathologyMockMvc.perform(get("/api/patient-pathologies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePatientPathology() throws Exception {
        // Initialize the database
        patientPathologyService.save(patientPathology);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockPatientPathologySearchRepository);

        int databaseSizeBeforeUpdate = patientPathologyRepository.findAll().size();

        // Update the patientPathology
        PatientPathology updatedPatientPathology = patientPathologyRepository.findById(patientPathology.getId()).get();
        // Disconnect from session so that the updates on updatedPatientPathology are not directly saved in db
        em.detach(updatedPatientPathology);
        updatedPatientPathology
            .observationDate(UPDATED_OBSERVATION_DATE)
            .observations(UPDATED_OBSERVATIONS);

        restPatientPathologyMockMvc.perform(put("/api/patient-pathologies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPatientPathology)))
            .andExpect(status().isOk());

        // Validate the PatientPathology in the database
        List<PatientPathology> patientPathologyList = patientPathologyRepository.findAll();
        assertThat(patientPathologyList).hasSize(databaseSizeBeforeUpdate);
        PatientPathology testPatientPathology = patientPathologyList.get(patientPathologyList.size() - 1);
        assertThat(testPatientPathology.getObservationDate()).isEqualTo(UPDATED_OBSERVATION_DATE);
        assertThat(testPatientPathology.getObservations()).isEqualTo(UPDATED_OBSERVATIONS);

        // Validate the PatientPathology in Elasticsearch
        verify(mockPatientPathologySearchRepository, times(1)).save(testPatientPathology);
    }

    @Test
    @Transactional
    public void updateNonExistingPatientPathology() throws Exception {
        int databaseSizeBeforeUpdate = patientPathologyRepository.findAll().size();

        // Create the PatientPathology

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPatientPathologyMockMvc.perform(put("/api/patient-pathologies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patientPathology)))
            .andExpect(status().isBadRequest());

        // Validate the PatientPathology in the database
        List<PatientPathology> patientPathologyList = patientPathologyRepository.findAll();
        assertThat(patientPathologyList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PatientPathology in Elasticsearch
        verify(mockPatientPathologySearchRepository, times(0)).save(patientPathology);
    }

    @Test
    @Transactional
    public void deletePatientPathology() throws Exception {
        // Initialize the database
        patientPathologyService.save(patientPathology);

        int databaseSizeBeforeDelete = patientPathologyRepository.findAll().size();

        // Delete the patientPathology
        restPatientPathologyMockMvc.perform(delete("/api/patient-pathologies/{id}", patientPathology.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PatientPathology> patientPathologyList = patientPathologyRepository.findAll();
        assertThat(patientPathologyList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PatientPathology in Elasticsearch
        verify(mockPatientPathologySearchRepository, times(1)).deleteById(patientPathology.getId());
    }

    @Test
    @Transactional
    public void searchPatientPathology() throws Exception {
        // Initialize the database
        patientPathologyService.save(patientPathology);
        when(mockPatientPathologySearchRepository.search(queryStringQuery("id:" + patientPathology.getId())))
            .thenReturn(Collections.singletonList(patientPathology));
        // Search the patientPathology
        restPatientPathologyMockMvc.perform(get("/api/_search/patient-pathologies?query=id:" + patientPathology.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(patientPathology.getId().intValue())))
            .andExpect(jsonPath("$.[*].observationDate").value(hasItem(DEFAULT_OBSERVATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].observations").value(hasItem(DEFAULT_OBSERVATIONS)));
    }
}
