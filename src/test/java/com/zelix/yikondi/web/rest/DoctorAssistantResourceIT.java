package com.zelix.yikondi.web.rest;

import com.zelix.yikondi.YikondiApp;
import com.zelix.yikondi.domain.DoctorAssistant;
import com.zelix.yikondi.repository.DoctorAssistantRepository;
import com.zelix.yikondi.repository.search.DoctorAssistantSearchRepository;
import com.zelix.yikondi.service.DoctorAssistantService;
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
 * Integration tests for the {@link DoctorAssistantResource} REST controller.
 */
@SpringBootTest(classes = YikondiApp.class)
public class DoctorAssistantResourceIT {

    private static final Boolean DEFAULT_CAN_PRESCRIBE = false;
    private static final Boolean UPDATED_CAN_PRESCRIBE = true;

    @Autowired
    private DoctorAssistantRepository doctorAssistantRepository;

    @Autowired
    private DoctorAssistantService doctorAssistantService;

    /**
     * This repository is mocked in the com.zelix.yikondi.repository.search test package.
     *
     * @see com.zelix.yikondi.repository.search.DoctorAssistantSearchRepositoryMockConfiguration
     */
    @Autowired
    private DoctorAssistantSearchRepository mockDoctorAssistantSearchRepository;

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

    private MockMvc restDoctorAssistantMockMvc;

    private DoctorAssistant doctorAssistant;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DoctorAssistantResource doctorAssistantResource = new DoctorAssistantResource(doctorAssistantService);
        this.restDoctorAssistantMockMvc = MockMvcBuilders.standaloneSetup(doctorAssistantResource)
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
    public static DoctorAssistant createEntity(EntityManager em) {
        DoctorAssistant doctorAssistant = new DoctorAssistant()
            .canPrescribe(DEFAULT_CAN_PRESCRIBE);
        return doctorAssistant;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DoctorAssistant createUpdatedEntity(EntityManager em) {
        DoctorAssistant doctorAssistant = new DoctorAssistant()
            .canPrescribe(UPDATED_CAN_PRESCRIBE);
        return doctorAssistant;
    }

    @BeforeEach
    public void initTest() {
        doctorAssistant = createEntity(em);
    }

    @Test
    @Transactional
    public void createDoctorAssistant() throws Exception {
        int databaseSizeBeforeCreate = doctorAssistantRepository.findAll().size();

        // Create the DoctorAssistant
        restDoctorAssistantMockMvc.perform(post("/api/doctor-assistants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(doctorAssistant)))
            .andExpect(status().isCreated());

        // Validate the DoctorAssistant in the database
        List<DoctorAssistant> doctorAssistantList = doctorAssistantRepository.findAll();
        assertThat(doctorAssistantList).hasSize(databaseSizeBeforeCreate + 1);
        DoctorAssistant testDoctorAssistant = doctorAssistantList.get(doctorAssistantList.size() - 1);
        assertThat(testDoctorAssistant.isCanPrescribe()).isEqualTo(DEFAULT_CAN_PRESCRIBE);

        // Validate the DoctorAssistant in Elasticsearch
        verify(mockDoctorAssistantSearchRepository, times(1)).save(testDoctorAssistant);
    }

    @Test
    @Transactional
    public void createDoctorAssistantWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = doctorAssistantRepository.findAll().size();

        // Create the DoctorAssistant with an existing ID
        doctorAssistant.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDoctorAssistantMockMvc.perform(post("/api/doctor-assistants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(doctorAssistant)))
            .andExpect(status().isBadRequest());

        // Validate the DoctorAssistant in the database
        List<DoctorAssistant> doctorAssistantList = doctorAssistantRepository.findAll();
        assertThat(doctorAssistantList).hasSize(databaseSizeBeforeCreate);

        // Validate the DoctorAssistant in Elasticsearch
        verify(mockDoctorAssistantSearchRepository, times(0)).save(doctorAssistant);
    }


    @Test
    @Transactional
    public void getAllDoctorAssistants() throws Exception {
        // Initialize the database
        doctorAssistantRepository.saveAndFlush(doctorAssistant);

        // Get all the doctorAssistantList
        restDoctorAssistantMockMvc.perform(get("/api/doctor-assistants?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(doctorAssistant.getId().intValue())))
            .andExpect(jsonPath("$.[*].canPrescribe").value(hasItem(DEFAULT_CAN_PRESCRIBE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getDoctorAssistant() throws Exception {
        // Initialize the database
        doctorAssistantRepository.saveAndFlush(doctorAssistant);

        // Get the doctorAssistant
        restDoctorAssistantMockMvc.perform(get("/api/doctor-assistants/{id}", doctorAssistant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(doctorAssistant.getId().intValue()))
            .andExpect(jsonPath("$.canPrescribe").value(DEFAULT_CAN_PRESCRIBE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDoctorAssistant() throws Exception {
        // Get the doctorAssistant
        restDoctorAssistantMockMvc.perform(get("/api/doctor-assistants/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDoctorAssistant() throws Exception {
        // Initialize the database
        doctorAssistantService.save(doctorAssistant);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockDoctorAssistantSearchRepository);

        int databaseSizeBeforeUpdate = doctorAssistantRepository.findAll().size();

        // Update the doctorAssistant
        DoctorAssistant updatedDoctorAssistant = doctorAssistantRepository.findById(doctorAssistant.getId()).get();
        // Disconnect from session so that the updates on updatedDoctorAssistant are not directly saved in db
        em.detach(updatedDoctorAssistant);
        updatedDoctorAssistant
            .canPrescribe(UPDATED_CAN_PRESCRIBE);

        restDoctorAssistantMockMvc.perform(put("/api/doctor-assistants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDoctorAssistant)))
            .andExpect(status().isOk());

        // Validate the DoctorAssistant in the database
        List<DoctorAssistant> doctorAssistantList = doctorAssistantRepository.findAll();
        assertThat(doctorAssistantList).hasSize(databaseSizeBeforeUpdate);
        DoctorAssistant testDoctorAssistant = doctorAssistantList.get(doctorAssistantList.size() - 1);
        assertThat(testDoctorAssistant.isCanPrescribe()).isEqualTo(UPDATED_CAN_PRESCRIBE);

        // Validate the DoctorAssistant in Elasticsearch
        verify(mockDoctorAssistantSearchRepository, times(1)).save(testDoctorAssistant);
    }

    @Test
    @Transactional
    public void updateNonExistingDoctorAssistant() throws Exception {
        int databaseSizeBeforeUpdate = doctorAssistantRepository.findAll().size();

        // Create the DoctorAssistant

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDoctorAssistantMockMvc.perform(put("/api/doctor-assistants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(doctorAssistant)))
            .andExpect(status().isBadRequest());

        // Validate the DoctorAssistant in the database
        List<DoctorAssistant> doctorAssistantList = doctorAssistantRepository.findAll();
        assertThat(doctorAssistantList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DoctorAssistant in Elasticsearch
        verify(mockDoctorAssistantSearchRepository, times(0)).save(doctorAssistant);
    }

    @Test
    @Transactional
    public void deleteDoctorAssistant() throws Exception {
        // Initialize the database
        doctorAssistantService.save(doctorAssistant);

        int databaseSizeBeforeDelete = doctorAssistantRepository.findAll().size();

        // Delete the doctorAssistant
        restDoctorAssistantMockMvc.perform(delete("/api/doctor-assistants/{id}", doctorAssistant.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DoctorAssistant> doctorAssistantList = doctorAssistantRepository.findAll();
        assertThat(doctorAssistantList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the DoctorAssistant in Elasticsearch
        verify(mockDoctorAssistantSearchRepository, times(1)).deleteById(doctorAssistant.getId());
    }

    @Test
    @Transactional
    public void searchDoctorAssistant() throws Exception {
        // Initialize the database
        doctorAssistantService.save(doctorAssistant);
        when(mockDoctorAssistantSearchRepository.search(queryStringQuery("id:" + doctorAssistant.getId())))
            .thenReturn(Collections.singletonList(doctorAssistant));
        // Search the doctorAssistant
        restDoctorAssistantMockMvc.perform(get("/api/_search/doctor-assistants?query=id:" + doctorAssistant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(doctorAssistant.getId().intValue())))
            .andExpect(jsonPath("$.[*].canPrescribe").value(hasItem(DEFAULT_CAN_PRESCRIBE.booleanValue())));
    }
}
