package com.zelix.yikondi.web.rest;

import com.zelix.yikondi.YikondiApp;
import com.zelix.yikondi.domain.Speciality;
import com.zelix.yikondi.repository.SpecialityRepository;
import com.zelix.yikondi.repository.search.SpecialitySearchRepository;
import com.zelix.yikondi.service.SpecialityService;
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
 * Integration tests for the {@link SpecialityResource} REST controller.
 */
@SpringBootTest(classes = YikondiApp.class)
public class SpecialityResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private SpecialityRepository specialityRepository;

    @Autowired
    private SpecialityService specialityService;

    /**
     * This repository is mocked in the com.zelix.yikondi.repository.search test package.
     *
     * @see com.zelix.yikondi.repository.search.SpecialitySearchRepositoryMockConfiguration
     */
    @Autowired
    private SpecialitySearchRepository mockSpecialitySearchRepository;

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

    private MockMvc restSpecialityMockMvc;

    private Speciality speciality;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SpecialityResource specialityResource = new SpecialityResource(specialityService);
        this.restSpecialityMockMvc = MockMvcBuilders.standaloneSetup(specialityResource)
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
    public static Speciality createEntity(EntityManager em) {
        Speciality speciality = new Speciality()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return speciality;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Speciality createUpdatedEntity(EntityManager em) {
        Speciality speciality = new Speciality()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return speciality;
    }

    @BeforeEach
    public void initTest() {
        speciality = createEntity(em);
    }

    @Test
    @Transactional
    public void createSpeciality() throws Exception {
        int databaseSizeBeforeCreate = specialityRepository.findAll().size();

        // Create the Speciality
        restSpecialityMockMvc.perform(post("/api/specialities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(speciality)))
            .andExpect(status().isCreated());

        // Validate the Speciality in the database
        List<Speciality> specialityList = specialityRepository.findAll();
        assertThat(specialityList).hasSize(databaseSizeBeforeCreate + 1);
        Speciality testSpeciality = specialityList.get(specialityList.size() - 1);
        assertThat(testSpeciality.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSpeciality.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the Speciality in Elasticsearch
        verify(mockSpecialitySearchRepository, times(1)).save(testSpeciality);
    }

    @Test
    @Transactional
    public void createSpecialityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = specialityRepository.findAll().size();

        // Create the Speciality with an existing ID
        speciality.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSpecialityMockMvc.perform(post("/api/specialities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(speciality)))
            .andExpect(status().isBadRequest());

        // Validate the Speciality in the database
        List<Speciality> specialityList = specialityRepository.findAll();
        assertThat(specialityList).hasSize(databaseSizeBeforeCreate);

        // Validate the Speciality in Elasticsearch
        verify(mockSpecialitySearchRepository, times(0)).save(speciality);
    }


    @Test
    @Transactional
    public void getAllSpecialities() throws Exception {
        // Initialize the database
        specialityRepository.saveAndFlush(speciality);

        // Get all the specialityList
        restSpecialityMockMvc.perform(get("/api/specialities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(speciality.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getSpeciality() throws Exception {
        // Initialize the database
        specialityRepository.saveAndFlush(speciality);

        // Get the speciality
        restSpecialityMockMvc.perform(get("/api/specialities/{id}", speciality.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(speciality.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    public void getNonExistingSpeciality() throws Exception {
        // Get the speciality
        restSpecialityMockMvc.perform(get("/api/specialities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSpeciality() throws Exception {
        // Initialize the database
        specialityService.save(speciality);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockSpecialitySearchRepository);

        int databaseSizeBeforeUpdate = specialityRepository.findAll().size();

        // Update the speciality
        Speciality updatedSpeciality = specialityRepository.findById(speciality.getId()).get();
        // Disconnect from session so that the updates on updatedSpeciality are not directly saved in db
        em.detach(updatedSpeciality);
        updatedSpeciality
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restSpecialityMockMvc.perform(put("/api/specialities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSpeciality)))
            .andExpect(status().isOk());

        // Validate the Speciality in the database
        List<Speciality> specialityList = specialityRepository.findAll();
        assertThat(specialityList).hasSize(databaseSizeBeforeUpdate);
        Speciality testSpeciality = specialityList.get(specialityList.size() - 1);
        assertThat(testSpeciality.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSpeciality.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the Speciality in Elasticsearch
        verify(mockSpecialitySearchRepository, times(1)).save(testSpeciality);
    }

    @Test
    @Transactional
    public void updateNonExistingSpeciality() throws Exception {
        int databaseSizeBeforeUpdate = specialityRepository.findAll().size();

        // Create the Speciality

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSpecialityMockMvc.perform(put("/api/specialities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(speciality)))
            .andExpect(status().isBadRequest());

        // Validate the Speciality in the database
        List<Speciality> specialityList = specialityRepository.findAll();
        assertThat(specialityList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Speciality in Elasticsearch
        verify(mockSpecialitySearchRepository, times(0)).save(speciality);
    }

    @Test
    @Transactional
    public void deleteSpeciality() throws Exception {
        // Initialize the database
        specialityService.save(speciality);

        int databaseSizeBeforeDelete = specialityRepository.findAll().size();

        // Delete the speciality
        restSpecialityMockMvc.perform(delete("/api/specialities/{id}", speciality.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Speciality> specialityList = specialityRepository.findAll();
        assertThat(specialityList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Speciality in Elasticsearch
        verify(mockSpecialitySearchRepository, times(1)).deleteById(speciality.getId());
    }

    @Test
    @Transactional
    public void searchSpeciality() throws Exception {
        // Initialize the database
        specialityService.save(speciality);
        when(mockSpecialitySearchRepository.search(queryStringQuery("id:" + speciality.getId())))
            .thenReturn(Collections.singletonList(speciality));
        // Search the speciality
        restSpecialityMockMvc.perform(get("/api/_search/specialities?query=id:" + speciality.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(speciality.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
}
