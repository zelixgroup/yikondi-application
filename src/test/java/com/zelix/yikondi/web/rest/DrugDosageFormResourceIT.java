package com.zelix.yikondi.web.rest;

import com.zelix.yikondi.YikondiApp;
import com.zelix.yikondi.domain.DrugDosageForm;
import com.zelix.yikondi.repository.DrugDosageFormRepository;
import com.zelix.yikondi.repository.search.DrugDosageFormSearchRepository;
import com.zelix.yikondi.service.DrugDosageFormService;
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
 * Integration tests for the {@link DrugDosageFormResource} REST controller.
 */
@SpringBootTest(classes = YikondiApp.class)
public class DrugDosageFormResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private DrugDosageFormRepository drugDosageFormRepository;

    @Autowired
    private DrugDosageFormService drugDosageFormService;

    /**
     * This repository is mocked in the com.zelix.yikondi.repository.search test package.
     *
     * @see com.zelix.yikondi.repository.search.DrugDosageFormSearchRepositoryMockConfiguration
     */
    @Autowired
    private DrugDosageFormSearchRepository mockDrugDosageFormSearchRepository;

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

    private MockMvc restDrugDosageFormMockMvc;

    private DrugDosageForm drugDosageForm;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DrugDosageFormResource drugDosageFormResource = new DrugDosageFormResource(drugDosageFormService);
        this.restDrugDosageFormMockMvc = MockMvcBuilders.standaloneSetup(drugDosageFormResource)
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
    public static DrugDosageForm createEntity(EntityManager em) {
        DrugDosageForm drugDosageForm = new DrugDosageForm()
            .name(DEFAULT_NAME);
        return drugDosageForm;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DrugDosageForm createUpdatedEntity(EntityManager em) {
        DrugDosageForm drugDosageForm = new DrugDosageForm()
            .name(UPDATED_NAME);
        return drugDosageForm;
    }

    @BeforeEach
    public void initTest() {
        drugDosageForm = createEntity(em);
    }

    @Test
    @Transactional
    public void createDrugDosageForm() throws Exception {
        int databaseSizeBeforeCreate = drugDosageFormRepository.findAll().size();

        // Create the DrugDosageForm
        restDrugDosageFormMockMvc.perform(post("/api/drug-dosage-forms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(drugDosageForm)))
            .andExpect(status().isCreated());

        // Validate the DrugDosageForm in the database
        List<DrugDosageForm> drugDosageFormList = drugDosageFormRepository.findAll();
        assertThat(drugDosageFormList).hasSize(databaseSizeBeforeCreate + 1);
        DrugDosageForm testDrugDosageForm = drugDosageFormList.get(drugDosageFormList.size() - 1);
        assertThat(testDrugDosageForm.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the DrugDosageForm in Elasticsearch
        verify(mockDrugDosageFormSearchRepository, times(1)).save(testDrugDosageForm);
    }

    @Test
    @Transactional
    public void createDrugDosageFormWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = drugDosageFormRepository.findAll().size();

        // Create the DrugDosageForm with an existing ID
        drugDosageForm.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDrugDosageFormMockMvc.perform(post("/api/drug-dosage-forms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(drugDosageForm)))
            .andExpect(status().isBadRequest());

        // Validate the DrugDosageForm in the database
        List<DrugDosageForm> drugDosageFormList = drugDosageFormRepository.findAll();
        assertThat(drugDosageFormList).hasSize(databaseSizeBeforeCreate);

        // Validate the DrugDosageForm in Elasticsearch
        verify(mockDrugDosageFormSearchRepository, times(0)).save(drugDosageForm);
    }


    @Test
    @Transactional
    public void getAllDrugDosageForms() throws Exception {
        // Initialize the database
        drugDosageFormRepository.saveAndFlush(drugDosageForm);

        // Get all the drugDosageFormList
        restDrugDosageFormMockMvc.perform(get("/api/drug-dosage-forms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(drugDosageForm.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getDrugDosageForm() throws Exception {
        // Initialize the database
        drugDosageFormRepository.saveAndFlush(drugDosageForm);

        // Get the drugDosageForm
        restDrugDosageFormMockMvc.perform(get("/api/drug-dosage-forms/{id}", drugDosageForm.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(drugDosageForm.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    public void getNonExistingDrugDosageForm() throws Exception {
        // Get the drugDosageForm
        restDrugDosageFormMockMvc.perform(get("/api/drug-dosage-forms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDrugDosageForm() throws Exception {
        // Initialize the database
        drugDosageFormService.save(drugDosageForm);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockDrugDosageFormSearchRepository);

        int databaseSizeBeforeUpdate = drugDosageFormRepository.findAll().size();

        // Update the drugDosageForm
        DrugDosageForm updatedDrugDosageForm = drugDosageFormRepository.findById(drugDosageForm.getId()).get();
        // Disconnect from session so that the updates on updatedDrugDosageForm are not directly saved in db
        em.detach(updatedDrugDosageForm);
        updatedDrugDosageForm
            .name(UPDATED_NAME);

        restDrugDosageFormMockMvc.perform(put("/api/drug-dosage-forms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDrugDosageForm)))
            .andExpect(status().isOk());

        // Validate the DrugDosageForm in the database
        List<DrugDosageForm> drugDosageFormList = drugDosageFormRepository.findAll();
        assertThat(drugDosageFormList).hasSize(databaseSizeBeforeUpdate);
        DrugDosageForm testDrugDosageForm = drugDosageFormList.get(drugDosageFormList.size() - 1);
        assertThat(testDrugDosageForm.getName()).isEqualTo(UPDATED_NAME);

        // Validate the DrugDosageForm in Elasticsearch
        verify(mockDrugDosageFormSearchRepository, times(1)).save(testDrugDosageForm);
    }

    @Test
    @Transactional
    public void updateNonExistingDrugDosageForm() throws Exception {
        int databaseSizeBeforeUpdate = drugDosageFormRepository.findAll().size();

        // Create the DrugDosageForm

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDrugDosageFormMockMvc.perform(put("/api/drug-dosage-forms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(drugDosageForm)))
            .andExpect(status().isBadRequest());

        // Validate the DrugDosageForm in the database
        List<DrugDosageForm> drugDosageFormList = drugDosageFormRepository.findAll();
        assertThat(drugDosageFormList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DrugDosageForm in Elasticsearch
        verify(mockDrugDosageFormSearchRepository, times(0)).save(drugDosageForm);
    }

    @Test
    @Transactional
    public void deleteDrugDosageForm() throws Exception {
        // Initialize the database
        drugDosageFormService.save(drugDosageForm);

        int databaseSizeBeforeDelete = drugDosageFormRepository.findAll().size();

        // Delete the drugDosageForm
        restDrugDosageFormMockMvc.perform(delete("/api/drug-dosage-forms/{id}", drugDosageForm.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DrugDosageForm> drugDosageFormList = drugDosageFormRepository.findAll();
        assertThat(drugDosageFormList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the DrugDosageForm in Elasticsearch
        verify(mockDrugDosageFormSearchRepository, times(1)).deleteById(drugDosageForm.getId());
    }

    @Test
    @Transactional
    public void searchDrugDosageForm() throws Exception {
        // Initialize the database
        drugDosageFormService.save(drugDosageForm);
        when(mockDrugDosageFormSearchRepository.search(queryStringQuery("id:" + drugDosageForm.getId())))
            .thenReturn(Collections.singletonList(drugDosageForm));
        // Search the drugDosageForm
        restDrugDosageFormMockMvc.perform(get("/api/_search/drug-dosage-forms?query=id:" + drugDosageForm.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(drugDosageForm.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
}
