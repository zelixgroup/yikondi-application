package com.zelix.yikondi.web.rest;

import com.zelix.yikondi.YikondiApp;
import com.zelix.yikondi.domain.DoctorWorkingSlot;
import com.zelix.yikondi.repository.DoctorWorkingSlotRepository;
import com.zelix.yikondi.repository.search.DoctorWorkingSlotSearchRepository;
import com.zelix.yikondi.service.DoctorWorkingSlotService;
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

import com.zelix.yikondi.domain.enumeration.DayOfTheWeek;
/**
 * Integration tests for the {@link DoctorWorkingSlotResource} REST controller.
 */
@SpringBootTest(classes = YikondiApp.class)
public class DoctorWorkingSlotResourceIT {

    private static final DayOfTheWeek DEFAULT_DAY_OF_THE_WEEK = DayOfTheWeek.MONDAY;
    private static final DayOfTheWeek UPDATED_DAY_OF_THE_WEEK = DayOfTheWeek.TUESDAY;

    private static final String DEFAULT_START_TIME = "AAAAAAAAAA";
    private static final String UPDATED_START_TIME = "BBBBBBBBBB";

    private static final String DEFAULT_END_TIME = "AAAAAAAAAA";
    private static final String UPDATED_END_TIME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private DoctorWorkingSlotRepository doctorWorkingSlotRepository;

    @Autowired
    private DoctorWorkingSlotService doctorWorkingSlotService;

    /**
     * This repository is mocked in the com.zelix.yikondi.repository.search test package.
     *
     * @see com.zelix.yikondi.repository.search.DoctorWorkingSlotSearchRepositoryMockConfiguration
     */
    @Autowired
    private DoctorWorkingSlotSearchRepository mockDoctorWorkingSlotSearchRepository;

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

    private MockMvc restDoctorWorkingSlotMockMvc;

    private DoctorWorkingSlot doctorWorkingSlot;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DoctorWorkingSlotResource doctorWorkingSlotResource = new DoctorWorkingSlotResource(doctorWorkingSlotService);
        this.restDoctorWorkingSlotMockMvc = MockMvcBuilders.standaloneSetup(doctorWorkingSlotResource)
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
    public static DoctorWorkingSlot createEntity(EntityManager em) {
        DoctorWorkingSlot doctorWorkingSlot = new DoctorWorkingSlot()
            .dayOfTheWeek(DEFAULT_DAY_OF_THE_WEEK)
            .startTime(DEFAULT_START_TIME)
            .endTime(DEFAULT_END_TIME)
            .description(DEFAULT_DESCRIPTION);
        return doctorWorkingSlot;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DoctorWorkingSlot createUpdatedEntity(EntityManager em) {
        DoctorWorkingSlot doctorWorkingSlot = new DoctorWorkingSlot()
            .dayOfTheWeek(UPDATED_DAY_OF_THE_WEEK)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME)
            .description(UPDATED_DESCRIPTION);
        return doctorWorkingSlot;
    }

    @BeforeEach
    public void initTest() {
        doctorWorkingSlot = createEntity(em);
    }

    @Test
    @Transactional
    public void createDoctorWorkingSlot() throws Exception {
        int databaseSizeBeforeCreate = doctorWorkingSlotRepository.findAll().size();

        // Create the DoctorWorkingSlot
        restDoctorWorkingSlotMockMvc.perform(post("/api/doctor-working-slots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(doctorWorkingSlot)))
            .andExpect(status().isCreated());

        // Validate the DoctorWorkingSlot in the database
        List<DoctorWorkingSlot> doctorWorkingSlotList = doctorWorkingSlotRepository.findAll();
        assertThat(doctorWorkingSlotList).hasSize(databaseSizeBeforeCreate + 1);
        DoctorWorkingSlot testDoctorWorkingSlot = doctorWorkingSlotList.get(doctorWorkingSlotList.size() - 1);
        assertThat(testDoctorWorkingSlot.getDayOfTheWeek()).isEqualTo(DEFAULT_DAY_OF_THE_WEEK);
        assertThat(testDoctorWorkingSlot.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testDoctorWorkingSlot.getEndTime()).isEqualTo(DEFAULT_END_TIME);
        assertThat(testDoctorWorkingSlot.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the DoctorWorkingSlot in Elasticsearch
        verify(mockDoctorWorkingSlotSearchRepository, times(1)).save(testDoctorWorkingSlot);
    }

    @Test
    @Transactional
    public void createDoctorWorkingSlotWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = doctorWorkingSlotRepository.findAll().size();

        // Create the DoctorWorkingSlot with an existing ID
        doctorWorkingSlot.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDoctorWorkingSlotMockMvc.perform(post("/api/doctor-working-slots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(doctorWorkingSlot)))
            .andExpect(status().isBadRequest());

        // Validate the DoctorWorkingSlot in the database
        List<DoctorWorkingSlot> doctorWorkingSlotList = doctorWorkingSlotRepository.findAll();
        assertThat(doctorWorkingSlotList).hasSize(databaseSizeBeforeCreate);

        // Validate the DoctorWorkingSlot in Elasticsearch
        verify(mockDoctorWorkingSlotSearchRepository, times(0)).save(doctorWorkingSlot);
    }


    @Test
    @Transactional
    public void getAllDoctorWorkingSlots() throws Exception {
        // Initialize the database
        doctorWorkingSlotRepository.saveAndFlush(doctorWorkingSlot);

        // Get all the doctorWorkingSlotList
        restDoctorWorkingSlotMockMvc.perform(get("/api/doctor-working-slots?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(doctorWorkingSlot.getId().intValue())))
            .andExpect(jsonPath("$.[*].dayOfTheWeek").value(hasItem(DEFAULT_DAY_OF_THE_WEEK.toString())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME)))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getDoctorWorkingSlot() throws Exception {
        // Initialize the database
        doctorWorkingSlotRepository.saveAndFlush(doctorWorkingSlot);

        // Get the doctorWorkingSlot
        restDoctorWorkingSlotMockMvc.perform(get("/api/doctor-working-slots/{id}", doctorWorkingSlot.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(doctorWorkingSlot.getId().intValue()))
            .andExpect(jsonPath("$.dayOfTheWeek").value(DEFAULT_DAY_OF_THE_WEEK.toString()))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    public void getNonExistingDoctorWorkingSlot() throws Exception {
        // Get the doctorWorkingSlot
        restDoctorWorkingSlotMockMvc.perform(get("/api/doctor-working-slots/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDoctorWorkingSlot() throws Exception {
        // Initialize the database
        doctorWorkingSlotService.save(doctorWorkingSlot);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockDoctorWorkingSlotSearchRepository);

        int databaseSizeBeforeUpdate = doctorWorkingSlotRepository.findAll().size();

        // Update the doctorWorkingSlot
        DoctorWorkingSlot updatedDoctorWorkingSlot = doctorWorkingSlotRepository.findById(doctorWorkingSlot.getId()).get();
        // Disconnect from session so that the updates on updatedDoctorWorkingSlot are not directly saved in db
        em.detach(updatedDoctorWorkingSlot);
        updatedDoctorWorkingSlot
            .dayOfTheWeek(UPDATED_DAY_OF_THE_WEEK)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME)
            .description(UPDATED_DESCRIPTION);

        restDoctorWorkingSlotMockMvc.perform(put("/api/doctor-working-slots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDoctorWorkingSlot)))
            .andExpect(status().isOk());

        // Validate the DoctorWorkingSlot in the database
        List<DoctorWorkingSlot> doctorWorkingSlotList = doctorWorkingSlotRepository.findAll();
        assertThat(doctorWorkingSlotList).hasSize(databaseSizeBeforeUpdate);
        DoctorWorkingSlot testDoctorWorkingSlot = doctorWorkingSlotList.get(doctorWorkingSlotList.size() - 1);
        assertThat(testDoctorWorkingSlot.getDayOfTheWeek()).isEqualTo(UPDATED_DAY_OF_THE_WEEK);
        assertThat(testDoctorWorkingSlot.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testDoctorWorkingSlot.getEndTime()).isEqualTo(UPDATED_END_TIME);
        assertThat(testDoctorWorkingSlot.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the DoctorWorkingSlot in Elasticsearch
        verify(mockDoctorWorkingSlotSearchRepository, times(1)).save(testDoctorWorkingSlot);
    }

    @Test
    @Transactional
    public void updateNonExistingDoctorWorkingSlot() throws Exception {
        int databaseSizeBeforeUpdate = doctorWorkingSlotRepository.findAll().size();

        // Create the DoctorWorkingSlot

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDoctorWorkingSlotMockMvc.perform(put("/api/doctor-working-slots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(doctorWorkingSlot)))
            .andExpect(status().isBadRequest());

        // Validate the DoctorWorkingSlot in the database
        List<DoctorWorkingSlot> doctorWorkingSlotList = doctorWorkingSlotRepository.findAll();
        assertThat(doctorWorkingSlotList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DoctorWorkingSlot in Elasticsearch
        verify(mockDoctorWorkingSlotSearchRepository, times(0)).save(doctorWorkingSlot);
    }

    @Test
    @Transactional
    public void deleteDoctorWorkingSlot() throws Exception {
        // Initialize the database
        doctorWorkingSlotService.save(doctorWorkingSlot);

        int databaseSizeBeforeDelete = doctorWorkingSlotRepository.findAll().size();

        // Delete the doctorWorkingSlot
        restDoctorWorkingSlotMockMvc.perform(delete("/api/doctor-working-slots/{id}", doctorWorkingSlot.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DoctorWorkingSlot> doctorWorkingSlotList = doctorWorkingSlotRepository.findAll();
        assertThat(doctorWorkingSlotList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the DoctorWorkingSlot in Elasticsearch
        verify(mockDoctorWorkingSlotSearchRepository, times(1)).deleteById(doctorWorkingSlot.getId());
    }

    @Test
    @Transactional
    public void searchDoctorWorkingSlot() throws Exception {
        // Initialize the database
        doctorWorkingSlotService.save(doctorWorkingSlot);
        when(mockDoctorWorkingSlotSearchRepository.search(queryStringQuery("id:" + doctorWorkingSlot.getId())))
            .thenReturn(Collections.singletonList(doctorWorkingSlot));
        // Search the doctorWorkingSlot
        restDoctorWorkingSlotMockMvc.perform(get("/api/_search/doctor-working-slots?query=id:" + doctorWorkingSlot.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(doctorWorkingSlot.getId().intValue())))
            .andExpect(jsonPath("$.[*].dayOfTheWeek").value(hasItem(DEFAULT_DAY_OF_THE_WEEK.toString())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME)))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
}
