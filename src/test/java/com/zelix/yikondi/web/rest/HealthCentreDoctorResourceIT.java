package com.zelix.yikondi.web.rest;

import com.zelix.yikondi.YikondiApp;
import com.zelix.yikondi.domain.HealthCentreDoctor;
import com.zelix.yikondi.repository.HealthCentreDoctorRepository;
import com.zelix.yikondi.repository.search.HealthCentreDoctorSearchRepository;
import com.zelix.yikondi.service.HealthCentreDoctorService;
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
import java.math.BigDecimal;
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
 * Integration tests for the {@link HealthCentreDoctorResource} REST controller.
 */
@SpringBootTest(classes = YikondiApp.class)
public class HealthCentreDoctorResourceIT {

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_CONSULTING_FEES = new BigDecimal(1);
    private static final BigDecimal UPDATED_CONSULTING_FEES = new BigDecimal(2);

    @Autowired
    private HealthCentreDoctorRepository healthCentreDoctorRepository;

    @Autowired
    private HealthCentreDoctorService healthCentreDoctorService;

    /**
     * This repository is mocked in the com.zelix.yikondi.repository.search test package.
     *
     * @see com.zelix.yikondi.repository.search.HealthCentreDoctorSearchRepositoryMockConfiguration
     */
    @Autowired
    private HealthCentreDoctorSearchRepository mockHealthCentreDoctorSearchRepository;

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

    private MockMvc restHealthCentreDoctorMockMvc;

    private HealthCentreDoctor healthCentreDoctor;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HealthCentreDoctorResource healthCentreDoctorResource = new HealthCentreDoctorResource(healthCentreDoctorService);
        this.restHealthCentreDoctorMockMvc = MockMvcBuilders.standaloneSetup(healthCentreDoctorResource)
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
    public static HealthCentreDoctor createEntity(EntityManager em) {
        HealthCentreDoctor healthCentreDoctor = new HealthCentreDoctor()
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .consultingFees(DEFAULT_CONSULTING_FEES);
        return healthCentreDoctor;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HealthCentreDoctor createUpdatedEntity(EntityManager em) {
        HealthCentreDoctor healthCentreDoctor = new HealthCentreDoctor()
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .consultingFees(UPDATED_CONSULTING_FEES);
        return healthCentreDoctor;
    }

    @BeforeEach
    public void initTest() {
        healthCentreDoctor = createEntity(em);
    }

    @Test
    @Transactional
    public void createHealthCentreDoctor() throws Exception {
        int databaseSizeBeforeCreate = healthCentreDoctorRepository.findAll().size();

        // Create the HealthCentreDoctor
        restHealthCentreDoctorMockMvc.perform(post("/api/health-centre-doctors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(healthCentreDoctor)))
            .andExpect(status().isCreated());

        // Validate the HealthCentreDoctor in the database
        List<HealthCentreDoctor> healthCentreDoctorList = healthCentreDoctorRepository.findAll();
        assertThat(healthCentreDoctorList).hasSize(databaseSizeBeforeCreate + 1);
        HealthCentreDoctor testHealthCentreDoctor = healthCentreDoctorList.get(healthCentreDoctorList.size() - 1);
        assertThat(testHealthCentreDoctor.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testHealthCentreDoctor.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testHealthCentreDoctor.getConsultingFees()).isEqualTo(DEFAULT_CONSULTING_FEES);

        // Validate the HealthCentreDoctor in Elasticsearch
        verify(mockHealthCentreDoctorSearchRepository, times(1)).save(testHealthCentreDoctor);
    }

    @Test
    @Transactional
    public void createHealthCentreDoctorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = healthCentreDoctorRepository.findAll().size();

        // Create the HealthCentreDoctor with an existing ID
        healthCentreDoctor.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHealthCentreDoctorMockMvc.perform(post("/api/health-centre-doctors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(healthCentreDoctor)))
            .andExpect(status().isBadRequest());

        // Validate the HealthCentreDoctor in the database
        List<HealthCentreDoctor> healthCentreDoctorList = healthCentreDoctorRepository.findAll();
        assertThat(healthCentreDoctorList).hasSize(databaseSizeBeforeCreate);

        // Validate the HealthCentreDoctor in Elasticsearch
        verify(mockHealthCentreDoctorSearchRepository, times(0)).save(healthCentreDoctor);
    }


    @Test
    @Transactional
    public void getAllHealthCentreDoctors() throws Exception {
        // Initialize the database
        healthCentreDoctorRepository.saveAndFlush(healthCentreDoctor);

        // Get all the healthCentreDoctorList
        restHealthCentreDoctorMockMvc.perform(get("/api/health-centre-doctors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(healthCentreDoctor.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].consultingFees").value(hasItem(DEFAULT_CONSULTING_FEES.intValue())));
    }
    
    @Test
    @Transactional
    public void getHealthCentreDoctor() throws Exception {
        // Initialize the database
        healthCentreDoctorRepository.saveAndFlush(healthCentreDoctor);

        // Get the healthCentreDoctor
        restHealthCentreDoctorMockMvc.perform(get("/api/health-centre-doctors/{id}", healthCentreDoctor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(healthCentreDoctor.getId().intValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.consultingFees").value(DEFAULT_CONSULTING_FEES.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingHealthCentreDoctor() throws Exception {
        // Get the healthCentreDoctor
        restHealthCentreDoctorMockMvc.perform(get("/api/health-centre-doctors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHealthCentreDoctor() throws Exception {
        // Initialize the database
        healthCentreDoctorService.save(healthCentreDoctor);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockHealthCentreDoctorSearchRepository);

        int databaseSizeBeforeUpdate = healthCentreDoctorRepository.findAll().size();

        // Update the healthCentreDoctor
        HealthCentreDoctor updatedHealthCentreDoctor = healthCentreDoctorRepository.findById(healthCentreDoctor.getId()).get();
        // Disconnect from session so that the updates on updatedHealthCentreDoctor are not directly saved in db
        em.detach(updatedHealthCentreDoctor);
        updatedHealthCentreDoctor
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .consultingFees(UPDATED_CONSULTING_FEES);

        restHealthCentreDoctorMockMvc.perform(put("/api/health-centre-doctors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedHealthCentreDoctor)))
            .andExpect(status().isOk());

        // Validate the HealthCentreDoctor in the database
        List<HealthCentreDoctor> healthCentreDoctorList = healthCentreDoctorRepository.findAll();
        assertThat(healthCentreDoctorList).hasSize(databaseSizeBeforeUpdate);
        HealthCentreDoctor testHealthCentreDoctor = healthCentreDoctorList.get(healthCentreDoctorList.size() - 1);
        assertThat(testHealthCentreDoctor.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testHealthCentreDoctor.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testHealthCentreDoctor.getConsultingFees()).isEqualTo(UPDATED_CONSULTING_FEES);

        // Validate the HealthCentreDoctor in Elasticsearch
        verify(mockHealthCentreDoctorSearchRepository, times(1)).save(testHealthCentreDoctor);
    }

    @Test
    @Transactional
    public void updateNonExistingHealthCentreDoctor() throws Exception {
        int databaseSizeBeforeUpdate = healthCentreDoctorRepository.findAll().size();

        // Create the HealthCentreDoctor

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHealthCentreDoctorMockMvc.perform(put("/api/health-centre-doctors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(healthCentreDoctor)))
            .andExpect(status().isBadRequest());

        // Validate the HealthCentreDoctor in the database
        List<HealthCentreDoctor> healthCentreDoctorList = healthCentreDoctorRepository.findAll();
        assertThat(healthCentreDoctorList).hasSize(databaseSizeBeforeUpdate);

        // Validate the HealthCentreDoctor in Elasticsearch
        verify(mockHealthCentreDoctorSearchRepository, times(0)).save(healthCentreDoctor);
    }

    @Test
    @Transactional
    public void deleteHealthCentreDoctor() throws Exception {
        // Initialize the database
        healthCentreDoctorService.save(healthCentreDoctor);

        int databaseSizeBeforeDelete = healthCentreDoctorRepository.findAll().size();

        // Delete the healthCentreDoctor
        restHealthCentreDoctorMockMvc.perform(delete("/api/health-centre-doctors/{id}", healthCentreDoctor.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<HealthCentreDoctor> healthCentreDoctorList = healthCentreDoctorRepository.findAll();
        assertThat(healthCentreDoctorList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the HealthCentreDoctor in Elasticsearch
        verify(mockHealthCentreDoctorSearchRepository, times(1)).deleteById(healthCentreDoctor.getId());
    }

    @Test
    @Transactional
    public void searchHealthCentreDoctor() throws Exception {
        // Initialize the database
        healthCentreDoctorService.save(healthCentreDoctor);
        when(mockHealthCentreDoctorSearchRepository.search(queryStringQuery("id:" + healthCentreDoctor.getId())))
            .thenReturn(Collections.singletonList(healthCentreDoctor));
        // Search the healthCentreDoctor
        restHealthCentreDoctorMockMvc.perform(get("/api/_search/health-centre-doctors?query=id:" + healthCentreDoctor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(healthCentreDoctor.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].consultingFees").value(hasItem(DEFAULT_CONSULTING_FEES.intValue())));
    }
}
