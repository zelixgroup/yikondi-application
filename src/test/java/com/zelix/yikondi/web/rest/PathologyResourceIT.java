package com.zelix.yikondi.web.rest;

import com.zelix.yikondi.YikondiApp;
import com.zelix.yikondi.domain.Pathology;
import com.zelix.yikondi.repository.PathologyRepository;
import com.zelix.yikondi.repository.search.PathologySearchRepository;
import com.zelix.yikondi.service.PathologyService;
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
 * Integration tests for the {@link PathologyResource} REST controller.
 */
@SpringBootTest(classes = YikondiApp.class)
public class PathologyResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private PathologyRepository pathologyRepository;

    @Autowired
    private PathologyService pathologyService;

    /**
     * This repository is mocked in the com.zelix.yikondi.repository.search test package.
     *
     * @see com.zelix.yikondi.repository.search.PathologySearchRepositoryMockConfiguration
     */
    @Autowired
    private PathologySearchRepository mockPathologySearchRepository;

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

    private MockMvc restPathologyMockMvc;

    private Pathology pathology;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PathologyResource pathologyResource = new PathologyResource(pathologyService);
        this.restPathologyMockMvc = MockMvcBuilders.standaloneSetup(pathologyResource)
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
    public static Pathology createEntity(EntityManager em) {
        Pathology pathology = new Pathology()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return pathology;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pathology createUpdatedEntity(EntityManager em) {
        Pathology pathology = new Pathology()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return pathology;
    }

    @BeforeEach
    public void initTest() {
        pathology = createEntity(em);
    }

    @Test
    @Transactional
    public void createPathology() throws Exception {
        int databaseSizeBeforeCreate = pathologyRepository.findAll().size();

        // Create the Pathology
        restPathologyMockMvc.perform(post("/api/pathologies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pathology)))
            .andExpect(status().isCreated());

        // Validate the Pathology in the database
        List<Pathology> pathologyList = pathologyRepository.findAll();
        assertThat(pathologyList).hasSize(databaseSizeBeforeCreate + 1);
        Pathology testPathology = pathologyList.get(pathologyList.size() - 1);
        assertThat(testPathology.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPathology.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the Pathology in Elasticsearch
        verify(mockPathologySearchRepository, times(1)).save(testPathology);
    }

    @Test
    @Transactional
    public void createPathologyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pathologyRepository.findAll().size();

        // Create the Pathology with an existing ID
        pathology.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPathologyMockMvc.perform(post("/api/pathologies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pathology)))
            .andExpect(status().isBadRequest());

        // Validate the Pathology in the database
        List<Pathology> pathologyList = pathologyRepository.findAll();
        assertThat(pathologyList).hasSize(databaseSizeBeforeCreate);

        // Validate the Pathology in Elasticsearch
        verify(mockPathologySearchRepository, times(0)).save(pathology);
    }


    @Test
    @Transactional
    public void getAllPathologies() throws Exception {
        // Initialize the database
        pathologyRepository.saveAndFlush(pathology);

        // Get all the pathologyList
        restPathologyMockMvc.perform(get("/api/pathologies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pathology.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getPathology() throws Exception {
        // Initialize the database
        pathologyRepository.saveAndFlush(pathology);

        // Get the pathology
        restPathologyMockMvc.perform(get("/api/pathologies/{id}", pathology.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pathology.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    public void getNonExistingPathology() throws Exception {
        // Get the pathology
        restPathologyMockMvc.perform(get("/api/pathologies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePathology() throws Exception {
        // Initialize the database
        pathologyService.save(pathology);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockPathologySearchRepository);

        int databaseSizeBeforeUpdate = pathologyRepository.findAll().size();

        // Update the pathology
        Pathology updatedPathology = pathologyRepository.findById(pathology.getId()).get();
        // Disconnect from session so that the updates on updatedPathology are not directly saved in db
        em.detach(updatedPathology);
        updatedPathology
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restPathologyMockMvc.perform(put("/api/pathologies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPathology)))
            .andExpect(status().isOk());

        // Validate the Pathology in the database
        List<Pathology> pathologyList = pathologyRepository.findAll();
        assertThat(pathologyList).hasSize(databaseSizeBeforeUpdate);
        Pathology testPathology = pathologyList.get(pathologyList.size() - 1);
        assertThat(testPathology.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPathology.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the Pathology in Elasticsearch
        verify(mockPathologySearchRepository, times(1)).save(testPathology);
    }

    @Test
    @Transactional
    public void updateNonExistingPathology() throws Exception {
        int databaseSizeBeforeUpdate = pathologyRepository.findAll().size();

        // Create the Pathology

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPathologyMockMvc.perform(put("/api/pathologies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pathology)))
            .andExpect(status().isBadRequest());

        // Validate the Pathology in the database
        List<Pathology> pathologyList = pathologyRepository.findAll();
        assertThat(pathologyList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Pathology in Elasticsearch
        verify(mockPathologySearchRepository, times(0)).save(pathology);
    }

    @Test
    @Transactional
    public void deletePathology() throws Exception {
        // Initialize the database
        pathologyService.save(pathology);

        int databaseSizeBeforeDelete = pathologyRepository.findAll().size();

        // Delete the pathology
        restPathologyMockMvc.perform(delete("/api/pathologies/{id}", pathology.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Pathology> pathologyList = pathologyRepository.findAll();
        assertThat(pathologyList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Pathology in Elasticsearch
        verify(mockPathologySearchRepository, times(1)).deleteById(pathology.getId());
    }

    @Test
    @Transactional
    public void searchPathology() throws Exception {
        // Initialize the database
        pathologyService.save(pathology);
        when(mockPathologySearchRepository.search(queryStringQuery("id:" + pathology.getId())))
            .thenReturn(Collections.singletonList(pathology));
        // Search the pathology
        restPathologyMockMvc.perform(get("/api/_search/pathologies?query=id:" + pathology.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pathology.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
}
