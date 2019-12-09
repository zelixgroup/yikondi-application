package com.zelix.yikondi.web.rest;

import com.zelix.yikondi.YikondiApp;
import com.zelix.yikondi.domain.Allergy;
import com.zelix.yikondi.repository.AllergyRepository;
import com.zelix.yikondi.repository.search.AllergySearchRepository;
import com.zelix.yikondi.service.AllergyService;
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
 * Integration tests for the {@link AllergyResource} REST controller.
 */
@SpringBootTest(classes = YikondiApp.class)
public class AllergyResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private AllergyRepository allergyRepository;

    @Autowired
    private AllergyService allergyService;

    /**
     * This repository is mocked in the com.zelix.yikondi.repository.search test package.
     *
     * @see com.zelix.yikondi.repository.search.AllergySearchRepositoryMockConfiguration
     */
    @Autowired
    private AllergySearchRepository mockAllergySearchRepository;

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

    private MockMvc restAllergyMockMvc;

    private Allergy allergy;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AllergyResource allergyResource = new AllergyResource(allergyService);
        this.restAllergyMockMvc = MockMvcBuilders.standaloneSetup(allergyResource)
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
    public static Allergy createEntity(EntityManager em) {
        Allergy allergy = new Allergy()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return allergy;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Allergy createUpdatedEntity(EntityManager em) {
        Allergy allergy = new Allergy()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return allergy;
    }

    @BeforeEach
    public void initTest() {
        allergy = createEntity(em);
    }

    @Test
    @Transactional
    public void createAllergy() throws Exception {
        int databaseSizeBeforeCreate = allergyRepository.findAll().size();

        // Create the Allergy
        restAllergyMockMvc.perform(post("/api/allergies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(allergy)))
            .andExpect(status().isCreated());

        // Validate the Allergy in the database
        List<Allergy> allergyList = allergyRepository.findAll();
        assertThat(allergyList).hasSize(databaseSizeBeforeCreate + 1);
        Allergy testAllergy = allergyList.get(allergyList.size() - 1);
        assertThat(testAllergy.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAllergy.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the Allergy in Elasticsearch
        verify(mockAllergySearchRepository, times(1)).save(testAllergy);
    }

    @Test
    @Transactional
    public void createAllergyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = allergyRepository.findAll().size();

        // Create the Allergy with an existing ID
        allergy.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAllergyMockMvc.perform(post("/api/allergies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(allergy)))
            .andExpect(status().isBadRequest());

        // Validate the Allergy in the database
        List<Allergy> allergyList = allergyRepository.findAll();
        assertThat(allergyList).hasSize(databaseSizeBeforeCreate);

        // Validate the Allergy in Elasticsearch
        verify(mockAllergySearchRepository, times(0)).save(allergy);
    }


    @Test
    @Transactional
    public void getAllAllergies() throws Exception {
        // Initialize the database
        allergyRepository.saveAndFlush(allergy);

        // Get all the allergyList
        restAllergyMockMvc.perform(get("/api/allergies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(allergy.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getAllergy() throws Exception {
        // Initialize the database
        allergyRepository.saveAndFlush(allergy);

        // Get the allergy
        restAllergyMockMvc.perform(get("/api/allergies/{id}", allergy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(allergy.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    public void getNonExistingAllergy() throws Exception {
        // Get the allergy
        restAllergyMockMvc.perform(get("/api/allergies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAllergy() throws Exception {
        // Initialize the database
        allergyService.save(allergy);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockAllergySearchRepository);

        int databaseSizeBeforeUpdate = allergyRepository.findAll().size();

        // Update the allergy
        Allergy updatedAllergy = allergyRepository.findById(allergy.getId()).get();
        // Disconnect from session so that the updates on updatedAllergy are not directly saved in db
        em.detach(updatedAllergy);
        updatedAllergy
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restAllergyMockMvc.perform(put("/api/allergies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAllergy)))
            .andExpect(status().isOk());

        // Validate the Allergy in the database
        List<Allergy> allergyList = allergyRepository.findAll();
        assertThat(allergyList).hasSize(databaseSizeBeforeUpdate);
        Allergy testAllergy = allergyList.get(allergyList.size() - 1);
        assertThat(testAllergy.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAllergy.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the Allergy in Elasticsearch
        verify(mockAllergySearchRepository, times(1)).save(testAllergy);
    }

    @Test
    @Transactional
    public void updateNonExistingAllergy() throws Exception {
        int databaseSizeBeforeUpdate = allergyRepository.findAll().size();

        // Create the Allergy

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAllergyMockMvc.perform(put("/api/allergies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(allergy)))
            .andExpect(status().isBadRequest());

        // Validate the Allergy in the database
        List<Allergy> allergyList = allergyRepository.findAll();
        assertThat(allergyList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Allergy in Elasticsearch
        verify(mockAllergySearchRepository, times(0)).save(allergy);
    }

    @Test
    @Transactional
    public void deleteAllergy() throws Exception {
        // Initialize the database
        allergyService.save(allergy);

        int databaseSizeBeforeDelete = allergyRepository.findAll().size();

        // Delete the allergy
        restAllergyMockMvc.perform(delete("/api/allergies/{id}", allergy.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Allergy> allergyList = allergyRepository.findAll();
        assertThat(allergyList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Allergy in Elasticsearch
        verify(mockAllergySearchRepository, times(1)).deleteById(allergy.getId());
    }

    @Test
    @Transactional
    public void searchAllergy() throws Exception {
        // Initialize the database
        allergyService.save(allergy);
        when(mockAllergySearchRepository.search(queryStringQuery("id:" + allergy.getId())))
            .thenReturn(Collections.singletonList(allergy));
        // Search the allergy
        restAllergyMockMvc.perform(get("/api/_search/allergies?query=id:" + allergy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(allergy.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
}
