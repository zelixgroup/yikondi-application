package com.zelix.yikondi.web.rest;

import com.zelix.yikondi.YikondiApp;
import com.zelix.yikondi.domain.Analysis;
import com.zelix.yikondi.repository.AnalysisRepository;
import com.zelix.yikondi.repository.search.AnalysisSearchRepository;
import com.zelix.yikondi.service.AnalysisService;
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
 * Integration tests for the {@link AnalysisResource} REST controller.
 */
@SpringBootTest(classes = YikondiApp.class)
public class AnalysisResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private AnalysisRepository analysisRepository;

    @Autowired
    private AnalysisService analysisService;

    /**
     * This repository is mocked in the com.zelix.yikondi.repository.search test package.
     *
     * @see com.zelix.yikondi.repository.search.AnalysisSearchRepositoryMockConfiguration
     */
    @Autowired
    private AnalysisSearchRepository mockAnalysisSearchRepository;

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

    private MockMvc restAnalysisMockMvc;

    private Analysis analysis;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AnalysisResource analysisResource = new AnalysisResource(analysisService);
        this.restAnalysisMockMvc = MockMvcBuilders.standaloneSetup(analysisResource)
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
    public static Analysis createEntity(EntityManager em) {
        Analysis analysis = new Analysis()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return analysis;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Analysis createUpdatedEntity(EntityManager em) {
        Analysis analysis = new Analysis()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return analysis;
    }

    @BeforeEach
    public void initTest() {
        analysis = createEntity(em);
    }

    @Test
    @Transactional
    public void createAnalysis() throws Exception {
        int databaseSizeBeforeCreate = analysisRepository.findAll().size();

        // Create the Analysis
        restAnalysisMockMvc.perform(post("/api/analyses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(analysis)))
            .andExpect(status().isCreated());

        // Validate the Analysis in the database
        List<Analysis> analysisList = analysisRepository.findAll();
        assertThat(analysisList).hasSize(databaseSizeBeforeCreate + 1);
        Analysis testAnalysis = analysisList.get(analysisList.size() - 1);
        assertThat(testAnalysis.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAnalysis.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the Analysis in Elasticsearch
        verify(mockAnalysisSearchRepository, times(1)).save(testAnalysis);
    }

    @Test
    @Transactional
    public void createAnalysisWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = analysisRepository.findAll().size();

        // Create the Analysis with an existing ID
        analysis.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnalysisMockMvc.perform(post("/api/analyses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(analysis)))
            .andExpect(status().isBadRequest());

        // Validate the Analysis in the database
        List<Analysis> analysisList = analysisRepository.findAll();
        assertThat(analysisList).hasSize(databaseSizeBeforeCreate);

        // Validate the Analysis in Elasticsearch
        verify(mockAnalysisSearchRepository, times(0)).save(analysis);
    }


    @Test
    @Transactional
    public void getAllAnalyses() throws Exception {
        // Initialize the database
        analysisRepository.saveAndFlush(analysis);

        // Get all the analysisList
        restAnalysisMockMvc.perform(get("/api/analyses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(analysis.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getAnalysis() throws Exception {
        // Initialize the database
        analysisRepository.saveAndFlush(analysis);

        // Get the analysis
        restAnalysisMockMvc.perform(get("/api/analyses/{id}", analysis.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(analysis.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    public void getNonExistingAnalysis() throws Exception {
        // Get the analysis
        restAnalysisMockMvc.perform(get("/api/analyses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAnalysis() throws Exception {
        // Initialize the database
        analysisService.save(analysis);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockAnalysisSearchRepository);

        int databaseSizeBeforeUpdate = analysisRepository.findAll().size();

        // Update the analysis
        Analysis updatedAnalysis = analysisRepository.findById(analysis.getId()).get();
        // Disconnect from session so that the updates on updatedAnalysis are not directly saved in db
        em.detach(updatedAnalysis);
        updatedAnalysis
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restAnalysisMockMvc.perform(put("/api/analyses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAnalysis)))
            .andExpect(status().isOk());

        // Validate the Analysis in the database
        List<Analysis> analysisList = analysisRepository.findAll();
        assertThat(analysisList).hasSize(databaseSizeBeforeUpdate);
        Analysis testAnalysis = analysisList.get(analysisList.size() - 1);
        assertThat(testAnalysis.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAnalysis.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the Analysis in Elasticsearch
        verify(mockAnalysisSearchRepository, times(1)).save(testAnalysis);
    }

    @Test
    @Transactional
    public void updateNonExistingAnalysis() throws Exception {
        int databaseSizeBeforeUpdate = analysisRepository.findAll().size();

        // Create the Analysis

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnalysisMockMvc.perform(put("/api/analyses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(analysis)))
            .andExpect(status().isBadRequest());

        // Validate the Analysis in the database
        List<Analysis> analysisList = analysisRepository.findAll();
        assertThat(analysisList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Analysis in Elasticsearch
        verify(mockAnalysisSearchRepository, times(0)).save(analysis);
    }

    @Test
    @Transactional
    public void deleteAnalysis() throws Exception {
        // Initialize the database
        analysisService.save(analysis);

        int databaseSizeBeforeDelete = analysisRepository.findAll().size();

        // Delete the analysis
        restAnalysisMockMvc.perform(delete("/api/analyses/{id}", analysis.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Analysis> analysisList = analysisRepository.findAll();
        assertThat(analysisList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Analysis in Elasticsearch
        verify(mockAnalysisSearchRepository, times(1)).deleteById(analysis.getId());
    }

    @Test
    @Transactional
    public void searchAnalysis() throws Exception {
        // Initialize the database
        analysisService.save(analysis);
        when(mockAnalysisSearchRepository.search(queryStringQuery("id:" + analysis.getId())))
            .thenReturn(Collections.singletonList(analysis));
        // Search the analysis
        restAnalysisMockMvc.perform(get("/api/_search/analyses?query=id:" + analysis.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(analysis.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
}
