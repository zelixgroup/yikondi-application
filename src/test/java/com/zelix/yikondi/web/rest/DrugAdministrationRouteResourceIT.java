package com.zelix.yikondi.web.rest;

import com.zelix.yikondi.YikondiApp;
import com.zelix.yikondi.domain.DrugAdministrationRoute;
import com.zelix.yikondi.repository.DrugAdministrationRouteRepository;
import com.zelix.yikondi.repository.search.DrugAdministrationRouteSearchRepository;
import com.zelix.yikondi.service.DrugAdministrationRouteService;
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
 * Integration tests for the {@link DrugAdministrationRouteResource} REST controller.
 */
@SpringBootTest(classes = YikondiApp.class)
public class DrugAdministrationRouteResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private DrugAdministrationRouteRepository drugAdministrationRouteRepository;

    @Autowired
    private DrugAdministrationRouteService drugAdministrationRouteService;

    /**
     * This repository is mocked in the com.zelix.yikondi.repository.search test package.
     *
     * @see com.zelix.yikondi.repository.search.DrugAdministrationRouteSearchRepositoryMockConfiguration
     */
    @Autowired
    private DrugAdministrationRouteSearchRepository mockDrugAdministrationRouteSearchRepository;

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

    private MockMvc restDrugAdministrationRouteMockMvc;

    private DrugAdministrationRoute drugAdministrationRoute;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DrugAdministrationRouteResource drugAdministrationRouteResource = new DrugAdministrationRouteResource(drugAdministrationRouteService);
        this.restDrugAdministrationRouteMockMvc = MockMvcBuilders.standaloneSetup(drugAdministrationRouteResource)
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
    public static DrugAdministrationRoute createEntity(EntityManager em) {
        DrugAdministrationRoute drugAdministrationRoute = new DrugAdministrationRoute()
            .name(DEFAULT_NAME);
        return drugAdministrationRoute;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DrugAdministrationRoute createUpdatedEntity(EntityManager em) {
        DrugAdministrationRoute drugAdministrationRoute = new DrugAdministrationRoute()
            .name(UPDATED_NAME);
        return drugAdministrationRoute;
    }

    @BeforeEach
    public void initTest() {
        drugAdministrationRoute = createEntity(em);
    }

    @Test
    @Transactional
    public void createDrugAdministrationRoute() throws Exception {
        int databaseSizeBeforeCreate = drugAdministrationRouteRepository.findAll().size();

        // Create the DrugAdministrationRoute
        restDrugAdministrationRouteMockMvc.perform(post("/api/drug-administration-routes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(drugAdministrationRoute)))
            .andExpect(status().isCreated());

        // Validate the DrugAdministrationRoute in the database
        List<DrugAdministrationRoute> drugAdministrationRouteList = drugAdministrationRouteRepository.findAll();
        assertThat(drugAdministrationRouteList).hasSize(databaseSizeBeforeCreate + 1);
        DrugAdministrationRoute testDrugAdministrationRoute = drugAdministrationRouteList.get(drugAdministrationRouteList.size() - 1);
        assertThat(testDrugAdministrationRoute.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the DrugAdministrationRoute in Elasticsearch
        verify(mockDrugAdministrationRouteSearchRepository, times(1)).save(testDrugAdministrationRoute);
    }

    @Test
    @Transactional
    public void createDrugAdministrationRouteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = drugAdministrationRouteRepository.findAll().size();

        // Create the DrugAdministrationRoute with an existing ID
        drugAdministrationRoute.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDrugAdministrationRouteMockMvc.perform(post("/api/drug-administration-routes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(drugAdministrationRoute)))
            .andExpect(status().isBadRequest());

        // Validate the DrugAdministrationRoute in the database
        List<DrugAdministrationRoute> drugAdministrationRouteList = drugAdministrationRouteRepository.findAll();
        assertThat(drugAdministrationRouteList).hasSize(databaseSizeBeforeCreate);

        // Validate the DrugAdministrationRoute in Elasticsearch
        verify(mockDrugAdministrationRouteSearchRepository, times(0)).save(drugAdministrationRoute);
    }


    @Test
    @Transactional
    public void getAllDrugAdministrationRoutes() throws Exception {
        // Initialize the database
        drugAdministrationRouteRepository.saveAndFlush(drugAdministrationRoute);

        // Get all the drugAdministrationRouteList
        restDrugAdministrationRouteMockMvc.perform(get("/api/drug-administration-routes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(drugAdministrationRoute.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getDrugAdministrationRoute() throws Exception {
        // Initialize the database
        drugAdministrationRouteRepository.saveAndFlush(drugAdministrationRoute);

        // Get the drugAdministrationRoute
        restDrugAdministrationRouteMockMvc.perform(get("/api/drug-administration-routes/{id}", drugAdministrationRoute.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(drugAdministrationRoute.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    public void getNonExistingDrugAdministrationRoute() throws Exception {
        // Get the drugAdministrationRoute
        restDrugAdministrationRouteMockMvc.perform(get("/api/drug-administration-routes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDrugAdministrationRoute() throws Exception {
        // Initialize the database
        drugAdministrationRouteService.save(drugAdministrationRoute);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockDrugAdministrationRouteSearchRepository);

        int databaseSizeBeforeUpdate = drugAdministrationRouteRepository.findAll().size();

        // Update the drugAdministrationRoute
        DrugAdministrationRoute updatedDrugAdministrationRoute = drugAdministrationRouteRepository.findById(drugAdministrationRoute.getId()).get();
        // Disconnect from session so that the updates on updatedDrugAdministrationRoute are not directly saved in db
        em.detach(updatedDrugAdministrationRoute);
        updatedDrugAdministrationRoute
            .name(UPDATED_NAME);

        restDrugAdministrationRouteMockMvc.perform(put("/api/drug-administration-routes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDrugAdministrationRoute)))
            .andExpect(status().isOk());

        // Validate the DrugAdministrationRoute in the database
        List<DrugAdministrationRoute> drugAdministrationRouteList = drugAdministrationRouteRepository.findAll();
        assertThat(drugAdministrationRouteList).hasSize(databaseSizeBeforeUpdate);
        DrugAdministrationRoute testDrugAdministrationRoute = drugAdministrationRouteList.get(drugAdministrationRouteList.size() - 1);
        assertThat(testDrugAdministrationRoute.getName()).isEqualTo(UPDATED_NAME);

        // Validate the DrugAdministrationRoute in Elasticsearch
        verify(mockDrugAdministrationRouteSearchRepository, times(1)).save(testDrugAdministrationRoute);
    }

    @Test
    @Transactional
    public void updateNonExistingDrugAdministrationRoute() throws Exception {
        int databaseSizeBeforeUpdate = drugAdministrationRouteRepository.findAll().size();

        // Create the DrugAdministrationRoute

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDrugAdministrationRouteMockMvc.perform(put("/api/drug-administration-routes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(drugAdministrationRoute)))
            .andExpect(status().isBadRequest());

        // Validate the DrugAdministrationRoute in the database
        List<DrugAdministrationRoute> drugAdministrationRouteList = drugAdministrationRouteRepository.findAll();
        assertThat(drugAdministrationRouteList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DrugAdministrationRoute in Elasticsearch
        verify(mockDrugAdministrationRouteSearchRepository, times(0)).save(drugAdministrationRoute);
    }

    @Test
    @Transactional
    public void deleteDrugAdministrationRoute() throws Exception {
        // Initialize the database
        drugAdministrationRouteService.save(drugAdministrationRoute);

        int databaseSizeBeforeDelete = drugAdministrationRouteRepository.findAll().size();

        // Delete the drugAdministrationRoute
        restDrugAdministrationRouteMockMvc.perform(delete("/api/drug-administration-routes/{id}", drugAdministrationRoute.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DrugAdministrationRoute> drugAdministrationRouteList = drugAdministrationRouteRepository.findAll();
        assertThat(drugAdministrationRouteList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the DrugAdministrationRoute in Elasticsearch
        verify(mockDrugAdministrationRouteSearchRepository, times(1)).deleteById(drugAdministrationRoute.getId());
    }

    @Test
    @Transactional
    public void searchDrugAdministrationRoute() throws Exception {
        // Initialize the database
        drugAdministrationRouteService.save(drugAdministrationRoute);
        when(mockDrugAdministrationRouteSearchRepository.search(queryStringQuery("id:" + drugAdministrationRoute.getId())))
            .thenReturn(Collections.singletonList(drugAdministrationRoute));
        // Search the drugAdministrationRoute
        restDrugAdministrationRouteMockMvc.perform(get("/api/_search/drug-administration-routes?query=id:" + drugAdministrationRoute.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(drugAdministrationRoute.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
}
