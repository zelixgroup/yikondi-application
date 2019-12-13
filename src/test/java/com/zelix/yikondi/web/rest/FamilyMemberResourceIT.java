package com.zelix.yikondi.web.rest;

import com.zelix.yikondi.YikondiApp;
import com.zelix.yikondi.domain.FamilyMember;
import com.zelix.yikondi.repository.FamilyMemberRepository;
import com.zelix.yikondi.repository.search.FamilyMemberSearchRepository;
import com.zelix.yikondi.service.FamilyMemberService;
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
 * Integration tests for the {@link FamilyMemberResource} REST controller.
 */
@SpringBootTest(classes = YikondiApp.class)
public class FamilyMemberResourceIT {

    private static final String DEFAULT_OBSERVATIONS = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVATIONS = "BBBBBBBBBB";

    @Autowired
    private FamilyMemberRepository familyMemberRepository;

    @Autowired
    private FamilyMemberService familyMemberService;

    /**
     * This repository is mocked in the com.zelix.yikondi.repository.search test package.
     *
     * @see com.zelix.yikondi.repository.search.FamilyMemberSearchRepositoryMockConfiguration
     */
    @Autowired
    private FamilyMemberSearchRepository mockFamilyMemberSearchRepository;

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

    private MockMvc restFamilyMemberMockMvc;

    private FamilyMember familyMember;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FamilyMemberResource familyMemberResource = new FamilyMemberResource(familyMemberService);
        this.restFamilyMemberMockMvc = MockMvcBuilders.standaloneSetup(familyMemberResource)
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
    public static FamilyMember createEntity(EntityManager em) {
        FamilyMember familyMember = new FamilyMember()
            .observations(DEFAULT_OBSERVATIONS);
        return familyMember;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FamilyMember createUpdatedEntity(EntityManager em) {
        FamilyMember familyMember = new FamilyMember()
            .observations(UPDATED_OBSERVATIONS);
        return familyMember;
    }

    @BeforeEach
    public void initTest() {
        familyMember = createEntity(em);
    }

    @Test
    @Transactional
    public void createFamilyMember() throws Exception {
        int databaseSizeBeforeCreate = familyMemberRepository.findAll().size();

        // Create the FamilyMember
        restFamilyMemberMockMvc.perform(post("/api/family-members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(familyMember)))
            .andExpect(status().isCreated());

        // Validate the FamilyMember in the database
        List<FamilyMember> familyMemberList = familyMemberRepository.findAll();
        assertThat(familyMemberList).hasSize(databaseSizeBeforeCreate + 1);
        FamilyMember testFamilyMember = familyMemberList.get(familyMemberList.size() - 1);
        assertThat(testFamilyMember.getObservations()).isEqualTo(DEFAULT_OBSERVATIONS);

        // Validate the FamilyMember in Elasticsearch
        verify(mockFamilyMemberSearchRepository, times(1)).save(testFamilyMember);
    }

    @Test
    @Transactional
    public void createFamilyMemberWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = familyMemberRepository.findAll().size();

        // Create the FamilyMember with an existing ID
        familyMember.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFamilyMemberMockMvc.perform(post("/api/family-members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(familyMember)))
            .andExpect(status().isBadRequest());

        // Validate the FamilyMember in the database
        List<FamilyMember> familyMemberList = familyMemberRepository.findAll();
        assertThat(familyMemberList).hasSize(databaseSizeBeforeCreate);

        // Validate the FamilyMember in Elasticsearch
        verify(mockFamilyMemberSearchRepository, times(0)).save(familyMember);
    }


    @Test
    @Transactional
    public void getAllFamilyMembers() throws Exception {
        // Initialize the database
        familyMemberRepository.saveAndFlush(familyMember);

        // Get all the familyMemberList
        restFamilyMemberMockMvc.perform(get("/api/family-members?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(familyMember.getId().intValue())))
            .andExpect(jsonPath("$.[*].observations").value(hasItem(DEFAULT_OBSERVATIONS)));
    }
    
    @Test
    @Transactional
    public void getFamilyMember() throws Exception {
        // Initialize the database
        familyMemberRepository.saveAndFlush(familyMember);

        // Get the familyMember
        restFamilyMemberMockMvc.perform(get("/api/family-members/{id}", familyMember.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(familyMember.getId().intValue()))
            .andExpect(jsonPath("$.observations").value(DEFAULT_OBSERVATIONS));
    }

    @Test
    @Transactional
    public void getNonExistingFamilyMember() throws Exception {
        // Get the familyMember
        restFamilyMemberMockMvc.perform(get("/api/family-members/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFamilyMember() throws Exception {
        // Initialize the database
        familyMemberService.save(familyMember);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockFamilyMemberSearchRepository);

        int databaseSizeBeforeUpdate = familyMemberRepository.findAll().size();

        // Update the familyMember
        FamilyMember updatedFamilyMember = familyMemberRepository.findById(familyMember.getId()).get();
        // Disconnect from session so that the updates on updatedFamilyMember are not directly saved in db
        em.detach(updatedFamilyMember);
        updatedFamilyMember
            .observations(UPDATED_OBSERVATIONS);

        restFamilyMemberMockMvc.perform(put("/api/family-members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFamilyMember)))
            .andExpect(status().isOk());

        // Validate the FamilyMember in the database
        List<FamilyMember> familyMemberList = familyMemberRepository.findAll();
        assertThat(familyMemberList).hasSize(databaseSizeBeforeUpdate);
        FamilyMember testFamilyMember = familyMemberList.get(familyMemberList.size() - 1);
        assertThat(testFamilyMember.getObservations()).isEqualTo(UPDATED_OBSERVATIONS);

        // Validate the FamilyMember in Elasticsearch
        verify(mockFamilyMemberSearchRepository, times(1)).save(testFamilyMember);
    }

    @Test
    @Transactional
    public void updateNonExistingFamilyMember() throws Exception {
        int databaseSizeBeforeUpdate = familyMemberRepository.findAll().size();

        // Create the FamilyMember

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFamilyMemberMockMvc.perform(put("/api/family-members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(familyMember)))
            .andExpect(status().isBadRequest());

        // Validate the FamilyMember in the database
        List<FamilyMember> familyMemberList = familyMemberRepository.findAll();
        assertThat(familyMemberList).hasSize(databaseSizeBeforeUpdate);

        // Validate the FamilyMember in Elasticsearch
        verify(mockFamilyMemberSearchRepository, times(0)).save(familyMember);
    }

    @Test
    @Transactional
    public void deleteFamilyMember() throws Exception {
        // Initialize the database
        familyMemberService.save(familyMember);

        int databaseSizeBeforeDelete = familyMemberRepository.findAll().size();

        // Delete the familyMember
        restFamilyMemberMockMvc.perform(delete("/api/family-members/{id}", familyMember.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FamilyMember> familyMemberList = familyMemberRepository.findAll();
        assertThat(familyMemberList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the FamilyMember in Elasticsearch
        verify(mockFamilyMemberSearchRepository, times(1)).deleteById(familyMember.getId());
    }

    @Test
    @Transactional
    public void searchFamilyMember() throws Exception {
        // Initialize the database
        familyMemberService.save(familyMember);
        when(mockFamilyMemberSearchRepository.search(queryStringQuery("id:" + familyMember.getId())))
            .thenReturn(Collections.singletonList(familyMember));
        // Search the familyMember
        restFamilyMemberMockMvc.perform(get("/api/_search/family-members?query=id:" + familyMember.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(familyMember.getId().intValue())))
            .andExpect(jsonPath("$.[*].observations").value(hasItem(DEFAULT_OBSERVATIONS)));
    }
}
