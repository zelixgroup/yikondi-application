package com.zelix.yikondi.web.rest;

import com.zelix.yikondi.YikondiApp;
import com.zelix.yikondi.domain.DoctorSchedule;
import com.zelix.yikondi.repository.DoctorScheduleRepository;
import com.zelix.yikondi.repository.search.DoctorScheduleSearchRepository;
import com.zelix.yikondi.service.DoctorScheduleService;
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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;

import static com.zelix.yikondi.web.rest.TestUtil.sameInstant;
import static com.zelix.yikondi.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link DoctorScheduleResource} REST controller.
 */
@SpringBootTest(classes = YikondiApp.class)
public class DoctorScheduleResourceIT {

    private static final ZonedDateTime DEFAULT_SCHEDULE_START_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_SCHEDULE_START_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_SCHEDULE_END_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_SCHEDULE_END_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private DoctorScheduleRepository doctorScheduleRepository;

    @Autowired
    private DoctorScheduleService doctorScheduleService;

    /**
     * This repository is mocked in the com.zelix.yikondi.repository.search test package.
     *
     * @see com.zelix.yikondi.repository.search.DoctorScheduleSearchRepositoryMockConfiguration
     */
    @Autowired
    private DoctorScheduleSearchRepository mockDoctorScheduleSearchRepository;

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

    private MockMvc restDoctorScheduleMockMvc;

    private DoctorSchedule doctorSchedule;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DoctorScheduleResource doctorScheduleResource = new DoctorScheduleResource(doctorScheduleService);
        this.restDoctorScheduleMockMvc = MockMvcBuilders.standaloneSetup(doctorScheduleResource)
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
    public static DoctorSchedule createEntity(EntityManager em) {
        DoctorSchedule doctorSchedule = new DoctorSchedule()
            .scheduleStartDate(DEFAULT_SCHEDULE_START_DATE)
            .scheduleEndDate(DEFAULT_SCHEDULE_END_DATE);
        return doctorSchedule;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DoctorSchedule createUpdatedEntity(EntityManager em) {
        DoctorSchedule doctorSchedule = new DoctorSchedule()
            .scheduleStartDate(UPDATED_SCHEDULE_START_DATE)
            .scheduleEndDate(UPDATED_SCHEDULE_END_DATE);
        return doctorSchedule;
    }

    @BeforeEach
    public void initTest() {
        doctorSchedule = createEntity(em);
    }

    @Test
    @Transactional
    public void createDoctorSchedule() throws Exception {
        int databaseSizeBeforeCreate = doctorScheduleRepository.findAll().size();

        // Create the DoctorSchedule
        restDoctorScheduleMockMvc.perform(post("/api/doctor-schedules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(doctorSchedule)))
            .andExpect(status().isCreated());

        // Validate the DoctorSchedule in the database
        List<DoctorSchedule> doctorScheduleList = doctorScheduleRepository.findAll();
        assertThat(doctorScheduleList).hasSize(databaseSizeBeforeCreate + 1);
        DoctorSchedule testDoctorSchedule = doctorScheduleList.get(doctorScheduleList.size() - 1);
        assertThat(testDoctorSchedule.getScheduleStartDate()).isEqualTo(DEFAULT_SCHEDULE_START_DATE);
        assertThat(testDoctorSchedule.getScheduleEndDate()).isEqualTo(DEFAULT_SCHEDULE_END_DATE);

        // Validate the DoctorSchedule in Elasticsearch
        verify(mockDoctorScheduleSearchRepository, times(1)).save(testDoctorSchedule);
    }

    @Test
    @Transactional
    public void createDoctorScheduleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = doctorScheduleRepository.findAll().size();

        // Create the DoctorSchedule with an existing ID
        doctorSchedule.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDoctorScheduleMockMvc.perform(post("/api/doctor-schedules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(doctorSchedule)))
            .andExpect(status().isBadRequest());

        // Validate the DoctorSchedule in the database
        List<DoctorSchedule> doctorScheduleList = doctorScheduleRepository.findAll();
        assertThat(doctorScheduleList).hasSize(databaseSizeBeforeCreate);

        // Validate the DoctorSchedule in Elasticsearch
        verify(mockDoctorScheduleSearchRepository, times(0)).save(doctorSchedule);
    }


    @Test
    @Transactional
    public void getAllDoctorSchedules() throws Exception {
        // Initialize the database
        doctorScheduleRepository.saveAndFlush(doctorSchedule);

        // Get all the doctorScheduleList
        restDoctorScheduleMockMvc.perform(get("/api/doctor-schedules?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(doctorSchedule.getId().intValue())))
            .andExpect(jsonPath("$.[*].scheduleStartDate").value(hasItem(sameInstant(DEFAULT_SCHEDULE_START_DATE))))
            .andExpect(jsonPath("$.[*].scheduleEndDate").value(hasItem(sameInstant(DEFAULT_SCHEDULE_END_DATE))));
    }
    
    @Test
    @Transactional
    public void getDoctorSchedule() throws Exception {
        // Initialize the database
        doctorScheduleRepository.saveAndFlush(doctorSchedule);

        // Get the doctorSchedule
        restDoctorScheduleMockMvc.perform(get("/api/doctor-schedules/{id}", doctorSchedule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(doctorSchedule.getId().intValue()))
            .andExpect(jsonPath("$.scheduleStartDate").value(sameInstant(DEFAULT_SCHEDULE_START_DATE)))
            .andExpect(jsonPath("$.scheduleEndDate").value(sameInstant(DEFAULT_SCHEDULE_END_DATE)));
    }

    @Test
    @Transactional
    public void getNonExistingDoctorSchedule() throws Exception {
        // Get the doctorSchedule
        restDoctorScheduleMockMvc.perform(get("/api/doctor-schedules/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDoctorSchedule() throws Exception {
        // Initialize the database
        doctorScheduleService.save(doctorSchedule);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockDoctorScheduleSearchRepository);

        int databaseSizeBeforeUpdate = doctorScheduleRepository.findAll().size();

        // Update the doctorSchedule
        DoctorSchedule updatedDoctorSchedule = doctorScheduleRepository.findById(doctorSchedule.getId()).get();
        // Disconnect from session so that the updates on updatedDoctorSchedule are not directly saved in db
        em.detach(updatedDoctorSchedule);
        updatedDoctorSchedule
            .scheduleStartDate(UPDATED_SCHEDULE_START_DATE)
            .scheduleEndDate(UPDATED_SCHEDULE_END_DATE);

        restDoctorScheduleMockMvc.perform(put("/api/doctor-schedules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDoctorSchedule)))
            .andExpect(status().isOk());

        // Validate the DoctorSchedule in the database
        List<DoctorSchedule> doctorScheduleList = doctorScheduleRepository.findAll();
        assertThat(doctorScheduleList).hasSize(databaseSizeBeforeUpdate);
        DoctorSchedule testDoctorSchedule = doctorScheduleList.get(doctorScheduleList.size() - 1);
        assertThat(testDoctorSchedule.getScheduleStartDate()).isEqualTo(UPDATED_SCHEDULE_START_DATE);
        assertThat(testDoctorSchedule.getScheduleEndDate()).isEqualTo(UPDATED_SCHEDULE_END_DATE);

        // Validate the DoctorSchedule in Elasticsearch
        verify(mockDoctorScheduleSearchRepository, times(1)).save(testDoctorSchedule);
    }

    @Test
    @Transactional
    public void updateNonExistingDoctorSchedule() throws Exception {
        int databaseSizeBeforeUpdate = doctorScheduleRepository.findAll().size();

        // Create the DoctorSchedule

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDoctorScheduleMockMvc.perform(put("/api/doctor-schedules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(doctorSchedule)))
            .andExpect(status().isBadRequest());

        // Validate the DoctorSchedule in the database
        List<DoctorSchedule> doctorScheduleList = doctorScheduleRepository.findAll();
        assertThat(doctorScheduleList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DoctorSchedule in Elasticsearch
        verify(mockDoctorScheduleSearchRepository, times(0)).save(doctorSchedule);
    }

    @Test
    @Transactional
    public void deleteDoctorSchedule() throws Exception {
        // Initialize the database
        doctorScheduleService.save(doctorSchedule);

        int databaseSizeBeforeDelete = doctorScheduleRepository.findAll().size();

        // Delete the doctorSchedule
        restDoctorScheduleMockMvc.perform(delete("/api/doctor-schedules/{id}", doctorSchedule.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DoctorSchedule> doctorScheduleList = doctorScheduleRepository.findAll();
        assertThat(doctorScheduleList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the DoctorSchedule in Elasticsearch
        verify(mockDoctorScheduleSearchRepository, times(1)).deleteById(doctorSchedule.getId());
    }

    @Test
    @Transactional
    public void searchDoctorSchedule() throws Exception {
        // Initialize the database
        doctorScheduleService.save(doctorSchedule);
        when(mockDoctorScheduleSearchRepository.search(queryStringQuery("id:" + doctorSchedule.getId())))
            .thenReturn(Collections.singletonList(doctorSchedule));
        // Search the doctorSchedule
        restDoctorScheduleMockMvc.perform(get("/api/_search/doctor-schedules?query=id:" + doctorSchedule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(doctorSchedule.getId().intValue())))
            .andExpect(jsonPath("$.[*].scheduleStartDate").value(hasItem(sameInstant(DEFAULT_SCHEDULE_START_DATE))))
            .andExpect(jsonPath("$.[*].scheduleEndDate").value(hasItem(sameInstant(DEFAULT_SCHEDULE_END_DATE))));
    }
}
