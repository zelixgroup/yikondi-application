package com.zelix.yikondi.web.rest;

import com.zelix.yikondi.YikondiApp;
import com.zelix.yikondi.domain.MedicalPrescriptionDrug;
import com.zelix.yikondi.repository.MedicalPrescriptionDrugRepository;
import com.zelix.yikondi.repository.search.MedicalPrescriptionDrugSearchRepository;
import com.zelix.yikondi.service.MedicalPrescriptionDrugService;
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
 * Integration tests for the {@link MedicalPrescriptionDrugResource} REST controller.
 */
@SpringBootTest(classes = YikondiApp.class)
public class MedicalPrescriptionDrugResourceIT {

    private static final String DEFAULT_DOSAGE = "AAAAAAAAAA";
    private static final String UPDATED_DOSAGE = "BBBBBBBBBB";

    @Autowired
    private MedicalPrescriptionDrugRepository medicalPrescriptionDrugRepository;

    @Autowired
    private MedicalPrescriptionDrugService medicalPrescriptionDrugService;

    /**
     * This repository is mocked in the com.zelix.yikondi.repository.search test package.
     *
     * @see com.zelix.yikondi.repository.search.MedicalPrescriptionDrugSearchRepositoryMockConfiguration
     */
    @Autowired
    private MedicalPrescriptionDrugSearchRepository mockMedicalPrescriptionDrugSearchRepository;

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

    private MockMvc restMedicalPrescriptionDrugMockMvc;

    private MedicalPrescriptionDrug medicalPrescriptionDrug;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MedicalPrescriptionDrugResource medicalPrescriptionDrugResource = new MedicalPrescriptionDrugResource(medicalPrescriptionDrugService);
        this.restMedicalPrescriptionDrugMockMvc = MockMvcBuilders.standaloneSetup(medicalPrescriptionDrugResource)
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
    public static MedicalPrescriptionDrug createEntity(EntityManager em) {
        MedicalPrescriptionDrug medicalPrescriptionDrug = new MedicalPrescriptionDrug()
            .dosage(DEFAULT_DOSAGE);
        return medicalPrescriptionDrug;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MedicalPrescriptionDrug createUpdatedEntity(EntityManager em) {
        MedicalPrescriptionDrug medicalPrescriptionDrug = new MedicalPrescriptionDrug()
            .dosage(UPDATED_DOSAGE);
        return medicalPrescriptionDrug;
    }

    @BeforeEach
    public void initTest() {
        medicalPrescriptionDrug = createEntity(em);
    }

    @Test
    @Transactional
    public void createMedicalPrescriptionDrug() throws Exception {
        int databaseSizeBeforeCreate = medicalPrescriptionDrugRepository.findAll().size();

        // Create the MedicalPrescriptionDrug
        restMedicalPrescriptionDrugMockMvc.perform(post("/api/medical-prescription-drugs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicalPrescriptionDrug)))
            .andExpect(status().isCreated());

        // Validate the MedicalPrescriptionDrug in the database
        List<MedicalPrescriptionDrug> medicalPrescriptionDrugList = medicalPrescriptionDrugRepository.findAll();
        assertThat(medicalPrescriptionDrugList).hasSize(databaseSizeBeforeCreate + 1);
        MedicalPrescriptionDrug testMedicalPrescriptionDrug = medicalPrescriptionDrugList.get(medicalPrescriptionDrugList.size() - 1);
        assertThat(testMedicalPrescriptionDrug.getDosage()).isEqualTo(DEFAULT_DOSAGE);

        // Validate the MedicalPrescriptionDrug in Elasticsearch
        verify(mockMedicalPrescriptionDrugSearchRepository, times(1)).save(testMedicalPrescriptionDrug);
    }

    @Test
    @Transactional
    public void createMedicalPrescriptionDrugWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = medicalPrescriptionDrugRepository.findAll().size();

        // Create the MedicalPrescriptionDrug with an existing ID
        medicalPrescriptionDrug.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMedicalPrescriptionDrugMockMvc.perform(post("/api/medical-prescription-drugs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicalPrescriptionDrug)))
            .andExpect(status().isBadRequest());

        // Validate the MedicalPrescriptionDrug in the database
        List<MedicalPrescriptionDrug> medicalPrescriptionDrugList = medicalPrescriptionDrugRepository.findAll();
        assertThat(medicalPrescriptionDrugList).hasSize(databaseSizeBeforeCreate);

        // Validate the MedicalPrescriptionDrug in Elasticsearch
        verify(mockMedicalPrescriptionDrugSearchRepository, times(0)).save(medicalPrescriptionDrug);
    }


    @Test
    @Transactional
    public void getAllMedicalPrescriptionDrugs() throws Exception {
        // Initialize the database
        medicalPrescriptionDrugRepository.saveAndFlush(medicalPrescriptionDrug);

        // Get all the medicalPrescriptionDrugList
        restMedicalPrescriptionDrugMockMvc.perform(get("/api/medical-prescription-drugs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medicalPrescriptionDrug.getId().intValue())))
            .andExpect(jsonPath("$.[*].dosage").value(hasItem(DEFAULT_DOSAGE)));
    }
    
    @Test
    @Transactional
    public void getMedicalPrescriptionDrug() throws Exception {
        // Initialize the database
        medicalPrescriptionDrugRepository.saveAndFlush(medicalPrescriptionDrug);

        // Get the medicalPrescriptionDrug
        restMedicalPrescriptionDrugMockMvc.perform(get("/api/medical-prescription-drugs/{id}", medicalPrescriptionDrug.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(medicalPrescriptionDrug.getId().intValue()))
            .andExpect(jsonPath("$.dosage").value(DEFAULT_DOSAGE));
    }

    @Test
    @Transactional
    public void getNonExistingMedicalPrescriptionDrug() throws Exception {
        // Get the medicalPrescriptionDrug
        restMedicalPrescriptionDrugMockMvc.perform(get("/api/medical-prescription-drugs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMedicalPrescriptionDrug() throws Exception {
        // Initialize the database
        medicalPrescriptionDrugService.save(medicalPrescriptionDrug);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockMedicalPrescriptionDrugSearchRepository);

        int databaseSizeBeforeUpdate = medicalPrescriptionDrugRepository.findAll().size();

        // Update the medicalPrescriptionDrug
        MedicalPrescriptionDrug updatedMedicalPrescriptionDrug = medicalPrescriptionDrugRepository.findById(medicalPrescriptionDrug.getId()).get();
        // Disconnect from session so that the updates on updatedMedicalPrescriptionDrug are not directly saved in db
        em.detach(updatedMedicalPrescriptionDrug);
        updatedMedicalPrescriptionDrug
            .dosage(UPDATED_DOSAGE);

        restMedicalPrescriptionDrugMockMvc.perform(put("/api/medical-prescription-drugs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMedicalPrescriptionDrug)))
            .andExpect(status().isOk());

        // Validate the MedicalPrescriptionDrug in the database
        List<MedicalPrescriptionDrug> medicalPrescriptionDrugList = medicalPrescriptionDrugRepository.findAll();
        assertThat(medicalPrescriptionDrugList).hasSize(databaseSizeBeforeUpdate);
        MedicalPrescriptionDrug testMedicalPrescriptionDrug = medicalPrescriptionDrugList.get(medicalPrescriptionDrugList.size() - 1);
        assertThat(testMedicalPrescriptionDrug.getDosage()).isEqualTo(UPDATED_DOSAGE);

        // Validate the MedicalPrescriptionDrug in Elasticsearch
        verify(mockMedicalPrescriptionDrugSearchRepository, times(1)).save(testMedicalPrescriptionDrug);
    }

    @Test
    @Transactional
    public void updateNonExistingMedicalPrescriptionDrug() throws Exception {
        int databaseSizeBeforeUpdate = medicalPrescriptionDrugRepository.findAll().size();

        // Create the MedicalPrescriptionDrug

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMedicalPrescriptionDrugMockMvc.perform(put("/api/medical-prescription-drugs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicalPrescriptionDrug)))
            .andExpect(status().isBadRequest());

        // Validate the MedicalPrescriptionDrug in the database
        List<MedicalPrescriptionDrug> medicalPrescriptionDrugList = medicalPrescriptionDrugRepository.findAll();
        assertThat(medicalPrescriptionDrugList).hasSize(databaseSizeBeforeUpdate);

        // Validate the MedicalPrescriptionDrug in Elasticsearch
        verify(mockMedicalPrescriptionDrugSearchRepository, times(0)).save(medicalPrescriptionDrug);
    }

    @Test
    @Transactional
    public void deleteMedicalPrescriptionDrug() throws Exception {
        // Initialize the database
        medicalPrescriptionDrugService.save(medicalPrescriptionDrug);

        int databaseSizeBeforeDelete = medicalPrescriptionDrugRepository.findAll().size();

        // Delete the medicalPrescriptionDrug
        restMedicalPrescriptionDrugMockMvc.perform(delete("/api/medical-prescription-drugs/{id}", medicalPrescriptionDrug.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MedicalPrescriptionDrug> medicalPrescriptionDrugList = medicalPrescriptionDrugRepository.findAll();
        assertThat(medicalPrescriptionDrugList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the MedicalPrescriptionDrug in Elasticsearch
        verify(mockMedicalPrescriptionDrugSearchRepository, times(1)).deleteById(medicalPrescriptionDrug.getId());
    }

    @Test
    @Transactional
    public void searchMedicalPrescriptionDrug() throws Exception {
        // Initialize the database
        medicalPrescriptionDrugService.save(medicalPrescriptionDrug);
        when(mockMedicalPrescriptionDrugSearchRepository.search(queryStringQuery("id:" + medicalPrescriptionDrug.getId())))
            .thenReturn(Collections.singletonList(medicalPrescriptionDrug));
        // Search the medicalPrescriptionDrug
        restMedicalPrescriptionDrugMockMvc.perform(get("/api/_search/medical-prescription-drugs?query=id:" + medicalPrescriptionDrug.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medicalPrescriptionDrug.getId().intValue())))
            .andExpect(jsonPath("$.[*].dosage").value(hasItem(DEFAULT_DOSAGE)));
    }
}
