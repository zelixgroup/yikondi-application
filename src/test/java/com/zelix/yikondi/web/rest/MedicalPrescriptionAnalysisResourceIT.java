package com.zelix.yikondi.web.rest;

import com.zelix.yikondi.YikondiApp;
import com.zelix.yikondi.domain.MedicalPrescriptionAnalysis;
import com.zelix.yikondi.repository.MedicalPrescriptionAnalysisRepository;
import com.zelix.yikondi.repository.search.MedicalPrescriptionAnalysisSearchRepository;
import com.zelix.yikondi.service.MedicalPrescriptionAnalysisService;
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
 * Integration tests for the {@link MedicalPrescriptionAnalysisResource} REST controller.
 */
@SpringBootTest(classes = YikondiApp.class)
public class MedicalPrescriptionAnalysisResourceIT {

    @Autowired
    private MedicalPrescriptionAnalysisRepository medicalPrescriptionAnalysisRepository;

    @Autowired
    private MedicalPrescriptionAnalysisService medicalPrescriptionAnalysisService;

    /**
     * This repository is mocked in the com.zelix.yikondi.repository.search test package.
     *
     * @see com.zelix.yikondi.repository.search.MedicalPrescriptionAnalysisSearchRepositoryMockConfiguration
     */
    @Autowired
    private MedicalPrescriptionAnalysisSearchRepository mockMedicalPrescriptionAnalysisSearchRepository;

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

    private MockMvc restMedicalPrescriptionAnalysisMockMvc;

    private MedicalPrescriptionAnalysis medicalPrescriptionAnalysis;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MedicalPrescriptionAnalysisResource medicalPrescriptionAnalysisResource = new MedicalPrescriptionAnalysisResource(medicalPrescriptionAnalysisService);
        this.restMedicalPrescriptionAnalysisMockMvc = MockMvcBuilders.standaloneSetup(medicalPrescriptionAnalysisResource)
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
    public static MedicalPrescriptionAnalysis createEntity(EntityManager em) {
        MedicalPrescriptionAnalysis medicalPrescriptionAnalysis = new MedicalPrescriptionAnalysis();
        return medicalPrescriptionAnalysis;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MedicalPrescriptionAnalysis createUpdatedEntity(EntityManager em) {
        MedicalPrescriptionAnalysis medicalPrescriptionAnalysis = new MedicalPrescriptionAnalysis();
        return medicalPrescriptionAnalysis;
    }

    @BeforeEach
    public void initTest() {
        medicalPrescriptionAnalysis = createEntity(em);
    }

    @Test
    @Transactional
    public void createMedicalPrescriptionAnalysis() throws Exception {
        int databaseSizeBeforeCreate = medicalPrescriptionAnalysisRepository.findAll().size();

        // Create the MedicalPrescriptionAnalysis
        restMedicalPrescriptionAnalysisMockMvc.perform(post("/api/medical-prescription-analyses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicalPrescriptionAnalysis)))
            .andExpect(status().isCreated());

        // Validate the MedicalPrescriptionAnalysis in the database
        List<MedicalPrescriptionAnalysis> medicalPrescriptionAnalysisList = medicalPrescriptionAnalysisRepository.findAll();
        assertThat(medicalPrescriptionAnalysisList).hasSize(databaseSizeBeforeCreate + 1);
        MedicalPrescriptionAnalysis testMedicalPrescriptionAnalysis = medicalPrescriptionAnalysisList.get(medicalPrescriptionAnalysisList.size() - 1);

        // Validate the MedicalPrescriptionAnalysis in Elasticsearch
        verify(mockMedicalPrescriptionAnalysisSearchRepository, times(1)).save(testMedicalPrescriptionAnalysis);
    }

    @Test
    @Transactional
    public void createMedicalPrescriptionAnalysisWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = medicalPrescriptionAnalysisRepository.findAll().size();

        // Create the MedicalPrescriptionAnalysis with an existing ID
        medicalPrescriptionAnalysis.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMedicalPrescriptionAnalysisMockMvc.perform(post("/api/medical-prescription-analyses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicalPrescriptionAnalysis)))
            .andExpect(status().isBadRequest());

        // Validate the MedicalPrescriptionAnalysis in the database
        List<MedicalPrescriptionAnalysis> medicalPrescriptionAnalysisList = medicalPrescriptionAnalysisRepository.findAll();
        assertThat(medicalPrescriptionAnalysisList).hasSize(databaseSizeBeforeCreate);

        // Validate the MedicalPrescriptionAnalysis in Elasticsearch
        verify(mockMedicalPrescriptionAnalysisSearchRepository, times(0)).save(medicalPrescriptionAnalysis);
    }


    @Test
    @Transactional
    public void getAllMedicalPrescriptionAnalyses() throws Exception {
        // Initialize the database
        medicalPrescriptionAnalysisRepository.saveAndFlush(medicalPrescriptionAnalysis);

        // Get all the medicalPrescriptionAnalysisList
        restMedicalPrescriptionAnalysisMockMvc.perform(get("/api/medical-prescription-analyses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medicalPrescriptionAnalysis.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getMedicalPrescriptionAnalysis() throws Exception {
        // Initialize the database
        medicalPrescriptionAnalysisRepository.saveAndFlush(medicalPrescriptionAnalysis);

        // Get the medicalPrescriptionAnalysis
        restMedicalPrescriptionAnalysisMockMvc.perform(get("/api/medical-prescription-analyses/{id}", medicalPrescriptionAnalysis.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(medicalPrescriptionAnalysis.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMedicalPrescriptionAnalysis() throws Exception {
        // Get the medicalPrescriptionAnalysis
        restMedicalPrescriptionAnalysisMockMvc.perform(get("/api/medical-prescription-analyses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMedicalPrescriptionAnalysis() throws Exception {
        // Initialize the database
        medicalPrescriptionAnalysisService.save(medicalPrescriptionAnalysis);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockMedicalPrescriptionAnalysisSearchRepository);

        int databaseSizeBeforeUpdate = medicalPrescriptionAnalysisRepository.findAll().size();

        // Update the medicalPrescriptionAnalysis
        MedicalPrescriptionAnalysis updatedMedicalPrescriptionAnalysis = medicalPrescriptionAnalysisRepository.findById(medicalPrescriptionAnalysis.getId()).get();
        // Disconnect from session so that the updates on updatedMedicalPrescriptionAnalysis are not directly saved in db
        em.detach(updatedMedicalPrescriptionAnalysis);

        restMedicalPrescriptionAnalysisMockMvc.perform(put("/api/medical-prescription-analyses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMedicalPrescriptionAnalysis)))
            .andExpect(status().isOk());

        // Validate the MedicalPrescriptionAnalysis in the database
        List<MedicalPrescriptionAnalysis> medicalPrescriptionAnalysisList = medicalPrescriptionAnalysisRepository.findAll();
        assertThat(medicalPrescriptionAnalysisList).hasSize(databaseSizeBeforeUpdate);
        MedicalPrescriptionAnalysis testMedicalPrescriptionAnalysis = medicalPrescriptionAnalysisList.get(medicalPrescriptionAnalysisList.size() - 1);

        // Validate the MedicalPrescriptionAnalysis in Elasticsearch
        verify(mockMedicalPrescriptionAnalysisSearchRepository, times(1)).save(testMedicalPrescriptionAnalysis);
    }

    @Test
    @Transactional
    public void updateNonExistingMedicalPrescriptionAnalysis() throws Exception {
        int databaseSizeBeforeUpdate = medicalPrescriptionAnalysisRepository.findAll().size();

        // Create the MedicalPrescriptionAnalysis

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMedicalPrescriptionAnalysisMockMvc.perform(put("/api/medical-prescription-analyses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicalPrescriptionAnalysis)))
            .andExpect(status().isBadRequest());

        // Validate the MedicalPrescriptionAnalysis in the database
        List<MedicalPrescriptionAnalysis> medicalPrescriptionAnalysisList = medicalPrescriptionAnalysisRepository.findAll();
        assertThat(medicalPrescriptionAnalysisList).hasSize(databaseSizeBeforeUpdate);

        // Validate the MedicalPrescriptionAnalysis in Elasticsearch
        verify(mockMedicalPrescriptionAnalysisSearchRepository, times(0)).save(medicalPrescriptionAnalysis);
    }

    @Test
    @Transactional
    public void deleteMedicalPrescriptionAnalysis() throws Exception {
        // Initialize the database
        medicalPrescriptionAnalysisService.save(medicalPrescriptionAnalysis);

        int databaseSizeBeforeDelete = medicalPrescriptionAnalysisRepository.findAll().size();

        // Delete the medicalPrescriptionAnalysis
        restMedicalPrescriptionAnalysisMockMvc.perform(delete("/api/medical-prescription-analyses/{id}", medicalPrescriptionAnalysis.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MedicalPrescriptionAnalysis> medicalPrescriptionAnalysisList = medicalPrescriptionAnalysisRepository.findAll();
        assertThat(medicalPrescriptionAnalysisList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the MedicalPrescriptionAnalysis in Elasticsearch
        verify(mockMedicalPrescriptionAnalysisSearchRepository, times(1)).deleteById(medicalPrescriptionAnalysis.getId());
    }

    @Test
    @Transactional
    public void searchMedicalPrescriptionAnalysis() throws Exception {
        // Initialize the database
        medicalPrescriptionAnalysisService.save(medicalPrescriptionAnalysis);
        when(mockMedicalPrescriptionAnalysisSearchRepository.search(queryStringQuery("id:" + medicalPrescriptionAnalysis.getId())))
            .thenReturn(Collections.singletonList(medicalPrescriptionAnalysis));
        // Search the medicalPrescriptionAnalysis
        restMedicalPrescriptionAnalysisMockMvc.perform(get("/api/_search/medical-prescription-analyses?query=id:" + medicalPrescriptionAnalysis.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medicalPrescriptionAnalysis.getId().intValue())));
    }
}
