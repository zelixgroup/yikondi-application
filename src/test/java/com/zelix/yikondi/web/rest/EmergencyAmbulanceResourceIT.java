package com.zelix.yikondi.web.rest;

import com.zelix.yikondi.YikondiApp;
import com.zelix.yikondi.domain.EmergencyAmbulance;
import com.zelix.yikondi.repository.EmergencyAmbulanceRepository;
import com.zelix.yikondi.repository.search.EmergencyAmbulanceSearchRepository;
import com.zelix.yikondi.service.EmergencyAmbulanceService;
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
 * Integration tests for the {@link EmergencyAmbulanceResource} REST controller.
 */
@SpringBootTest(classes = YikondiApp.class)
public class EmergencyAmbulanceResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DESCRIPTION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DESCRIPTION = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    @Autowired
    private EmergencyAmbulanceRepository emergencyAmbulanceRepository;

    @Autowired
    private EmergencyAmbulanceService emergencyAmbulanceService;

    /**
     * This repository is mocked in the com.zelix.yikondi.repository.search test package.
     *
     * @see com.zelix.yikondi.repository.search.EmergencyAmbulanceSearchRepositoryMockConfiguration
     */
    @Autowired
    private EmergencyAmbulanceSearchRepository mockEmergencyAmbulanceSearchRepository;

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

    private MockMvc restEmergencyAmbulanceMockMvc;

    private EmergencyAmbulance emergencyAmbulance;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EmergencyAmbulanceResource emergencyAmbulanceResource = new EmergencyAmbulanceResource(emergencyAmbulanceService);
        this.restEmergencyAmbulanceMockMvc = MockMvcBuilders.standaloneSetup(emergencyAmbulanceResource)
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
    public static EmergencyAmbulance createEntity(EntityManager em) {
        EmergencyAmbulance emergencyAmbulance = new EmergencyAmbulance()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .phoneNumber(DEFAULT_PHONE_NUMBER);
        return emergencyAmbulance;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmergencyAmbulance createUpdatedEntity(EntityManager em) {
        EmergencyAmbulance emergencyAmbulance = new EmergencyAmbulance()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .phoneNumber(UPDATED_PHONE_NUMBER);
        return emergencyAmbulance;
    }

    @BeforeEach
    public void initTest() {
        emergencyAmbulance = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmergencyAmbulance() throws Exception {
        int databaseSizeBeforeCreate = emergencyAmbulanceRepository.findAll().size();

        // Create the EmergencyAmbulance
        restEmergencyAmbulanceMockMvc.perform(post("/api/emergency-ambulances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emergencyAmbulance)))
            .andExpect(status().isCreated());

        // Validate the EmergencyAmbulance in the database
        List<EmergencyAmbulance> emergencyAmbulanceList = emergencyAmbulanceRepository.findAll();
        assertThat(emergencyAmbulanceList).hasSize(databaseSizeBeforeCreate + 1);
        EmergencyAmbulance testEmergencyAmbulance = emergencyAmbulanceList.get(emergencyAmbulanceList.size() - 1);
        assertThat(testEmergencyAmbulance.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEmergencyAmbulance.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testEmergencyAmbulance.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);

        // Validate the EmergencyAmbulance in Elasticsearch
        verify(mockEmergencyAmbulanceSearchRepository, times(1)).save(testEmergencyAmbulance);
    }

    @Test
    @Transactional
    public void createEmergencyAmbulanceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = emergencyAmbulanceRepository.findAll().size();

        // Create the EmergencyAmbulance with an existing ID
        emergencyAmbulance.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmergencyAmbulanceMockMvc.perform(post("/api/emergency-ambulances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emergencyAmbulance)))
            .andExpect(status().isBadRequest());

        // Validate the EmergencyAmbulance in the database
        List<EmergencyAmbulance> emergencyAmbulanceList = emergencyAmbulanceRepository.findAll();
        assertThat(emergencyAmbulanceList).hasSize(databaseSizeBeforeCreate);

        // Validate the EmergencyAmbulance in Elasticsearch
        verify(mockEmergencyAmbulanceSearchRepository, times(0)).save(emergencyAmbulance);
    }


    @Test
    @Transactional
    public void getAllEmergencyAmbulances() throws Exception {
        // Initialize the database
        emergencyAmbulanceRepository.saveAndFlush(emergencyAmbulance);

        // Get all the emergencyAmbulanceList
        restEmergencyAmbulanceMockMvc.perform(get("/api/emergency-ambulances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emergencyAmbulance.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)));
    }
    
    @Test
    @Transactional
    public void getEmergencyAmbulance() throws Exception {
        // Initialize the database
        emergencyAmbulanceRepository.saveAndFlush(emergencyAmbulance);

        // Get the emergencyAmbulance
        restEmergencyAmbulanceMockMvc.perform(get("/api/emergency-ambulances/{id}", emergencyAmbulance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(emergencyAmbulance.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER));
    }

    @Test
    @Transactional
    public void getNonExistingEmergencyAmbulance() throws Exception {
        // Get the emergencyAmbulance
        restEmergencyAmbulanceMockMvc.perform(get("/api/emergency-ambulances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmergencyAmbulance() throws Exception {
        // Initialize the database
        emergencyAmbulanceService.save(emergencyAmbulance);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockEmergencyAmbulanceSearchRepository);

        int databaseSizeBeforeUpdate = emergencyAmbulanceRepository.findAll().size();

        // Update the emergencyAmbulance
        EmergencyAmbulance updatedEmergencyAmbulance = emergencyAmbulanceRepository.findById(emergencyAmbulance.getId()).get();
        // Disconnect from session so that the updates on updatedEmergencyAmbulance are not directly saved in db
        em.detach(updatedEmergencyAmbulance);
        updatedEmergencyAmbulance
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .phoneNumber(UPDATED_PHONE_NUMBER);

        restEmergencyAmbulanceMockMvc.perform(put("/api/emergency-ambulances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEmergencyAmbulance)))
            .andExpect(status().isOk());

        // Validate the EmergencyAmbulance in the database
        List<EmergencyAmbulance> emergencyAmbulanceList = emergencyAmbulanceRepository.findAll();
        assertThat(emergencyAmbulanceList).hasSize(databaseSizeBeforeUpdate);
        EmergencyAmbulance testEmergencyAmbulance = emergencyAmbulanceList.get(emergencyAmbulanceList.size() - 1);
        assertThat(testEmergencyAmbulance.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEmergencyAmbulance.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testEmergencyAmbulance.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);

        // Validate the EmergencyAmbulance in Elasticsearch
        verify(mockEmergencyAmbulanceSearchRepository, times(1)).save(testEmergencyAmbulance);
    }

    @Test
    @Transactional
    public void updateNonExistingEmergencyAmbulance() throws Exception {
        int databaseSizeBeforeUpdate = emergencyAmbulanceRepository.findAll().size();

        // Create the EmergencyAmbulance

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmergencyAmbulanceMockMvc.perform(put("/api/emergency-ambulances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emergencyAmbulance)))
            .andExpect(status().isBadRequest());

        // Validate the EmergencyAmbulance in the database
        List<EmergencyAmbulance> emergencyAmbulanceList = emergencyAmbulanceRepository.findAll();
        assertThat(emergencyAmbulanceList).hasSize(databaseSizeBeforeUpdate);

        // Validate the EmergencyAmbulance in Elasticsearch
        verify(mockEmergencyAmbulanceSearchRepository, times(0)).save(emergencyAmbulance);
    }

    @Test
    @Transactional
    public void deleteEmergencyAmbulance() throws Exception {
        // Initialize the database
        emergencyAmbulanceService.save(emergencyAmbulance);

        int databaseSizeBeforeDelete = emergencyAmbulanceRepository.findAll().size();

        // Delete the emergencyAmbulance
        restEmergencyAmbulanceMockMvc.perform(delete("/api/emergency-ambulances/{id}", emergencyAmbulance.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmergencyAmbulance> emergencyAmbulanceList = emergencyAmbulanceRepository.findAll();
        assertThat(emergencyAmbulanceList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the EmergencyAmbulance in Elasticsearch
        verify(mockEmergencyAmbulanceSearchRepository, times(1)).deleteById(emergencyAmbulance.getId());
    }

    @Test
    @Transactional
    public void searchEmergencyAmbulance() throws Exception {
        // Initialize the database
        emergencyAmbulanceService.save(emergencyAmbulance);
        when(mockEmergencyAmbulanceSearchRepository.search(queryStringQuery("id:" + emergencyAmbulance.getId())))
            .thenReturn(Collections.singletonList(emergencyAmbulance));
        // Search the emergencyAmbulance
        restEmergencyAmbulanceMockMvc.perform(get("/api/_search/emergency-ambulances?query=id:" + emergencyAmbulance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emergencyAmbulance.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)));
    }
}
