package com.zelix.yikondi.web.rest;

import com.zelix.yikondi.YikondiApp;
import com.zelix.yikondi.domain.PharmacyAllNightPlanning;
import com.zelix.yikondi.repository.PharmacyAllNightPlanningRepository;
import com.zelix.yikondi.repository.search.PharmacyAllNightPlanningSearchRepository;
import com.zelix.yikondi.service.PharmacyAllNightPlanningService;
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
 * Integration tests for the {@link PharmacyAllNightPlanningResource} REST controller.
 */
@SpringBootTest(classes = YikondiApp.class)
public class PharmacyAllNightPlanningResourceIT {

    private static final LocalDate DEFAULT_PLANNED_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PLANNED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_PLANNED_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PLANNED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private PharmacyAllNightPlanningRepository pharmacyAllNightPlanningRepository;

    @Autowired
    private PharmacyAllNightPlanningService pharmacyAllNightPlanningService;

    /**
     * This repository is mocked in the com.zelix.yikondi.repository.search test package.
     *
     * @see com.zelix.yikondi.repository.search.PharmacyAllNightPlanningSearchRepositoryMockConfiguration
     */
    @Autowired
    private PharmacyAllNightPlanningSearchRepository mockPharmacyAllNightPlanningSearchRepository;

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

    private MockMvc restPharmacyAllNightPlanningMockMvc;

    private PharmacyAllNightPlanning pharmacyAllNightPlanning;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PharmacyAllNightPlanningResource pharmacyAllNightPlanningResource = new PharmacyAllNightPlanningResource(pharmacyAllNightPlanningService);
        this.restPharmacyAllNightPlanningMockMvc = MockMvcBuilders.standaloneSetup(pharmacyAllNightPlanningResource)
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
    public static PharmacyAllNightPlanning createEntity(EntityManager em) {
        PharmacyAllNightPlanning pharmacyAllNightPlanning = new PharmacyAllNightPlanning()
            .plannedStartDate(DEFAULT_PLANNED_START_DATE)
            .plannedEndDate(DEFAULT_PLANNED_END_DATE);
        return pharmacyAllNightPlanning;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PharmacyAllNightPlanning createUpdatedEntity(EntityManager em) {
        PharmacyAllNightPlanning pharmacyAllNightPlanning = new PharmacyAllNightPlanning()
            .plannedStartDate(UPDATED_PLANNED_START_DATE)
            .plannedEndDate(UPDATED_PLANNED_END_DATE);
        return pharmacyAllNightPlanning;
    }

    @BeforeEach
    public void initTest() {
        pharmacyAllNightPlanning = createEntity(em);
    }

    @Test
    @Transactional
    public void createPharmacyAllNightPlanning() throws Exception {
        int databaseSizeBeforeCreate = pharmacyAllNightPlanningRepository.findAll().size();

        // Create the PharmacyAllNightPlanning
        restPharmacyAllNightPlanningMockMvc.perform(post("/api/pharmacy-all-night-plannings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pharmacyAllNightPlanning)))
            .andExpect(status().isCreated());

        // Validate the PharmacyAllNightPlanning in the database
        List<PharmacyAllNightPlanning> pharmacyAllNightPlanningList = pharmacyAllNightPlanningRepository.findAll();
        assertThat(pharmacyAllNightPlanningList).hasSize(databaseSizeBeforeCreate + 1);
        PharmacyAllNightPlanning testPharmacyAllNightPlanning = pharmacyAllNightPlanningList.get(pharmacyAllNightPlanningList.size() - 1);
        assertThat(testPharmacyAllNightPlanning.getPlannedStartDate()).isEqualTo(DEFAULT_PLANNED_START_DATE);
        assertThat(testPharmacyAllNightPlanning.getPlannedEndDate()).isEqualTo(DEFAULT_PLANNED_END_DATE);

        // Validate the PharmacyAllNightPlanning in Elasticsearch
        verify(mockPharmacyAllNightPlanningSearchRepository, times(1)).save(testPharmacyAllNightPlanning);
    }

    @Test
    @Transactional
    public void createPharmacyAllNightPlanningWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pharmacyAllNightPlanningRepository.findAll().size();

        // Create the PharmacyAllNightPlanning with an existing ID
        pharmacyAllNightPlanning.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPharmacyAllNightPlanningMockMvc.perform(post("/api/pharmacy-all-night-plannings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pharmacyAllNightPlanning)))
            .andExpect(status().isBadRequest());

        // Validate the PharmacyAllNightPlanning in the database
        List<PharmacyAllNightPlanning> pharmacyAllNightPlanningList = pharmacyAllNightPlanningRepository.findAll();
        assertThat(pharmacyAllNightPlanningList).hasSize(databaseSizeBeforeCreate);

        // Validate the PharmacyAllNightPlanning in Elasticsearch
        verify(mockPharmacyAllNightPlanningSearchRepository, times(0)).save(pharmacyAllNightPlanning);
    }


    @Test
    @Transactional
    public void getAllPharmacyAllNightPlannings() throws Exception {
        // Initialize the database
        pharmacyAllNightPlanningRepository.saveAndFlush(pharmacyAllNightPlanning);

        // Get all the pharmacyAllNightPlanningList
        restPharmacyAllNightPlanningMockMvc.perform(get("/api/pharmacy-all-night-plannings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pharmacyAllNightPlanning.getId().intValue())))
            .andExpect(jsonPath("$.[*].plannedStartDate").value(hasItem(DEFAULT_PLANNED_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].plannedEndDate").value(hasItem(DEFAULT_PLANNED_END_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getPharmacyAllNightPlanning() throws Exception {
        // Initialize the database
        pharmacyAllNightPlanningRepository.saveAndFlush(pharmacyAllNightPlanning);

        // Get the pharmacyAllNightPlanning
        restPharmacyAllNightPlanningMockMvc.perform(get("/api/pharmacy-all-night-plannings/{id}", pharmacyAllNightPlanning.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pharmacyAllNightPlanning.getId().intValue()))
            .andExpect(jsonPath("$.plannedStartDate").value(DEFAULT_PLANNED_START_DATE.toString()))
            .andExpect(jsonPath("$.plannedEndDate").value(DEFAULT_PLANNED_END_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPharmacyAllNightPlanning() throws Exception {
        // Get the pharmacyAllNightPlanning
        restPharmacyAllNightPlanningMockMvc.perform(get("/api/pharmacy-all-night-plannings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePharmacyAllNightPlanning() throws Exception {
        // Initialize the database
        pharmacyAllNightPlanningService.save(pharmacyAllNightPlanning);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockPharmacyAllNightPlanningSearchRepository);

        int databaseSizeBeforeUpdate = pharmacyAllNightPlanningRepository.findAll().size();

        // Update the pharmacyAllNightPlanning
        PharmacyAllNightPlanning updatedPharmacyAllNightPlanning = pharmacyAllNightPlanningRepository.findById(pharmacyAllNightPlanning.getId()).get();
        // Disconnect from session so that the updates on updatedPharmacyAllNightPlanning are not directly saved in db
        em.detach(updatedPharmacyAllNightPlanning);
        updatedPharmacyAllNightPlanning
            .plannedStartDate(UPDATED_PLANNED_START_DATE)
            .plannedEndDate(UPDATED_PLANNED_END_DATE);

        restPharmacyAllNightPlanningMockMvc.perform(put("/api/pharmacy-all-night-plannings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPharmacyAllNightPlanning)))
            .andExpect(status().isOk());

        // Validate the PharmacyAllNightPlanning in the database
        List<PharmacyAllNightPlanning> pharmacyAllNightPlanningList = pharmacyAllNightPlanningRepository.findAll();
        assertThat(pharmacyAllNightPlanningList).hasSize(databaseSizeBeforeUpdate);
        PharmacyAllNightPlanning testPharmacyAllNightPlanning = pharmacyAllNightPlanningList.get(pharmacyAllNightPlanningList.size() - 1);
        assertThat(testPharmacyAllNightPlanning.getPlannedStartDate()).isEqualTo(UPDATED_PLANNED_START_DATE);
        assertThat(testPharmacyAllNightPlanning.getPlannedEndDate()).isEqualTo(UPDATED_PLANNED_END_DATE);

        // Validate the PharmacyAllNightPlanning in Elasticsearch
        verify(mockPharmacyAllNightPlanningSearchRepository, times(1)).save(testPharmacyAllNightPlanning);
    }

    @Test
    @Transactional
    public void updateNonExistingPharmacyAllNightPlanning() throws Exception {
        int databaseSizeBeforeUpdate = pharmacyAllNightPlanningRepository.findAll().size();

        // Create the PharmacyAllNightPlanning

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPharmacyAllNightPlanningMockMvc.perform(put("/api/pharmacy-all-night-plannings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pharmacyAllNightPlanning)))
            .andExpect(status().isBadRequest());

        // Validate the PharmacyAllNightPlanning in the database
        List<PharmacyAllNightPlanning> pharmacyAllNightPlanningList = pharmacyAllNightPlanningRepository.findAll();
        assertThat(pharmacyAllNightPlanningList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PharmacyAllNightPlanning in Elasticsearch
        verify(mockPharmacyAllNightPlanningSearchRepository, times(0)).save(pharmacyAllNightPlanning);
    }

    @Test
    @Transactional
    public void deletePharmacyAllNightPlanning() throws Exception {
        // Initialize the database
        pharmacyAllNightPlanningService.save(pharmacyAllNightPlanning);

        int databaseSizeBeforeDelete = pharmacyAllNightPlanningRepository.findAll().size();

        // Delete the pharmacyAllNightPlanning
        restPharmacyAllNightPlanningMockMvc.perform(delete("/api/pharmacy-all-night-plannings/{id}", pharmacyAllNightPlanning.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PharmacyAllNightPlanning> pharmacyAllNightPlanningList = pharmacyAllNightPlanningRepository.findAll();
        assertThat(pharmacyAllNightPlanningList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PharmacyAllNightPlanning in Elasticsearch
        verify(mockPharmacyAllNightPlanningSearchRepository, times(1)).deleteById(pharmacyAllNightPlanning.getId());
    }

    @Test
    @Transactional
    public void searchPharmacyAllNightPlanning() throws Exception {
        // Initialize the database
        pharmacyAllNightPlanningService.save(pharmacyAllNightPlanning);
        when(mockPharmacyAllNightPlanningSearchRepository.search(queryStringQuery("id:" + pharmacyAllNightPlanning.getId())))
            .thenReturn(Collections.singletonList(pharmacyAllNightPlanning));
        // Search the pharmacyAllNightPlanning
        restPharmacyAllNightPlanningMockMvc.perform(get("/api/_search/pharmacy-all-night-plannings?query=id:" + pharmacyAllNightPlanning.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pharmacyAllNightPlanning.getId().intValue())))
            .andExpect(jsonPath("$.[*].plannedStartDate").value(hasItem(DEFAULT_PLANNED_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].plannedEndDate").value(hasItem(DEFAULT_PLANNED_END_DATE.toString())));
    }
}
