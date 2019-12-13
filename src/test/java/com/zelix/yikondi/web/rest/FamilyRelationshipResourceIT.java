package com.zelix.yikondi.web.rest;

import com.zelix.yikondi.YikondiApp;
import com.zelix.yikondi.domain.FamilyRelationship;
import com.zelix.yikondi.repository.FamilyRelationshipRepository;
import com.zelix.yikondi.repository.search.FamilyRelationshipSearchRepository;
import com.zelix.yikondi.service.FamilyRelationshipService;
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
 * Integration tests for the {@link FamilyRelationshipResource} REST controller.
 */
@SpringBootTest(classes = YikondiApp.class)
public class FamilyRelationshipResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private FamilyRelationshipRepository familyRelationshipRepository;

    @Autowired
    private FamilyRelationshipService familyRelationshipService;

    /**
     * This repository is mocked in the com.zelix.yikondi.repository.search test package.
     *
     * @see com.zelix.yikondi.repository.search.FamilyRelationshipSearchRepositoryMockConfiguration
     */
    @Autowired
    private FamilyRelationshipSearchRepository mockFamilyRelationshipSearchRepository;

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

    private MockMvc restFamilyRelationshipMockMvc;

    private FamilyRelationship familyRelationship;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FamilyRelationshipResource familyRelationshipResource = new FamilyRelationshipResource(familyRelationshipService);
        this.restFamilyRelationshipMockMvc = MockMvcBuilders.standaloneSetup(familyRelationshipResource)
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
    public static FamilyRelationship createEntity(EntityManager em) {
        FamilyRelationship familyRelationship = new FamilyRelationship()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return familyRelationship;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FamilyRelationship createUpdatedEntity(EntityManager em) {
        FamilyRelationship familyRelationship = new FamilyRelationship()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return familyRelationship;
    }

    @BeforeEach
    public void initTest() {
        familyRelationship = createEntity(em);
    }

    @Test
    @Transactional
    public void createFamilyRelationship() throws Exception {
        int databaseSizeBeforeCreate = familyRelationshipRepository.findAll().size();

        // Create the FamilyRelationship
        restFamilyRelationshipMockMvc.perform(post("/api/family-relationships")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(familyRelationship)))
            .andExpect(status().isCreated());

        // Validate the FamilyRelationship in the database
        List<FamilyRelationship> familyRelationshipList = familyRelationshipRepository.findAll();
        assertThat(familyRelationshipList).hasSize(databaseSizeBeforeCreate + 1);
        FamilyRelationship testFamilyRelationship = familyRelationshipList.get(familyRelationshipList.size() - 1);
        assertThat(testFamilyRelationship.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFamilyRelationship.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the FamilyRelationship in Elasticsearch
        verify(mockFamilyRelationshipSearchRepository, times(1)).save(testFamilyRelationship);
    }

    @Test
    @Transactional
    public void createFamilyRelationshipWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = familyRelationshipRepository.findAll().size();

        // Create the FamilyRelationship with an existing ID
        familyRelationship.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFamilyRelationshipMockMvc.perform(post("/api/family-relationships")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(familyRelationship)))
            .andExpect(status().isBadRequest());

        // Validate the FamilyRelationship in the database
        List<FamilyRelationship> familyRelationshipList = familyRelationshipRepository.findAll();
        assertThat(familyRelationshipList).hasSize(databaseSizeBeforeCreate);

        // Validate the FamilyRelationship in Elasticsearch
        verify(mockFamilyRelationshipSearchRepository, times(0)).save(familyRelationship);
    }


    @Test
    @Transactional
    public void getAllFamilyRelationships() throws Exception {
        // Initialize the database
        familyRelationshipRepository.saveAndFlush(familyRelationship);

        // Get all the familyRelationshipList
        restFamilyRelationshipMockMvc.perform(get("/api/family-relationships?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(familyRelationship.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getFamilyRelationship() throws Exception {
        // Initialize the database
        familyRelationshipRepository.saveAndFlush(familyRelationship);

        // Get the familyRelationship
        restFamilyRelationshipMockMvc.perform(get("/api/family-relationships/{id}", familyRelationship.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(familyRelationship.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    public void getNonExistingFamilyRelationship() throws Exception {
        // Get the familyRelationship
        restFamilyRelationshipMockMvc.perform(get("/api/family-relationships/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFamilyRelationship() throws Exception {
        // Initialize the database
        familyRelationshipService.save(familyRelationship);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockFamilyRelationshipSearchRepository);

        int databaseSizeBeforeUpdate = familyRelationshipRepository.findAll().size();

        // Update the familyRelationship
        FamilyRelationship updatedFamilyRelationship = familyRelationshipRepository.findById(familyRelationship.getId()).get();
        // Disconnect from session so that the updates on updatedFamilyRelationship are not directly saved in db
        em.detach(updatedFamilyRelationship);
        updatedFamilyRelationship
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restFamilyRelationshipMockMvc.perform(put("/api/family-relationships")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFamilyRelationship)))
            .andExpect(status().isOk());

        // Validate the FamilyRelationship in the database
        List<FamilyRelationship> familyRelationshipList = familyRelationshipRepository.findAll();
        assertThat(familyRelationshipList).hasSize(databaseSizeBeforeUpdate);
        FamilyRelationship testFamilyRelationship = familyRelationshipList.get(familyRelationshipList.size() - 1);
        assertThat(testFamilyRelationship.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFamilyRelationship.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the FamilyRelationship in Elasticsearch
        verify(mockFamilyRelationshipSearchRepository, times(1)).save(testFamilyRelationship);
    }

    @Test
    @Transactional
    public void updateNonExistingFamilyRelationship() throws Exception {
        int databaseSizeBeforeUpdate = familyRelationshipRepository.findAll().size();

        // Create the FamilyRelationship

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFamilyRelationshipMockMvc.perform(put("/api/family-relationships")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(familyRelationship)))
            .andExpect(status().isBadRequest());

        // Validate the FamilyRelationship in the database
        List<FamilyRelationship> familyRelationshipList = familyRelationshipRepository.findAll();
        assertThat(familyRelationshipList).hasSize(databaseSizeBeforeUpdate);

        // Validate the FamilyRelationship in Elasticsearch
        verify(mockFamilyRelationshipSearchRepository, times(0)).save(familyRelationship);
    }

    @Test
    @Transactional
    public void deleteFamilyRelationship() throws Exception {
        // Initialize the database
        familyRelationshipService.save(familyRelationship);

        int databaseSizeBeforeDelete = familyRelationshipRepository.findAll().size();

        // Delete the familyRelationship
        restFamilyRelationshipMockMvc.perform(delete("/api/family-relationships/{id}", familyRelationship.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FamilyRelationship> familyRelationshipList = familyRelationshipRepository.findAll();
        assertThat(familyRelationshipList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the FamilyRelationship in Elasticsearch
        verify(mockFamilyRelationshipSearchRepository, times(1)).deleteById(familyRelationship.getId());
    }

    @Test
    @Transactional
    public void searchFamilyRelationship() throws Exception {
        // Initialize the database
        familyRelationshipService.save(familyRelationship);
        when(mockFamilyRelationshipSearchRepository.search(queryStringQuery("id:" + familyRelationship.getId())))
            .thenReturn(Collections.singletonList(familyRelationship));
        // Search the familyRelationship
        restFamilyRelationshipMockMvc.perform(get("/api/_search/family-relationships?query=id:" + familyRelationship.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(familyRelationship.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
}
