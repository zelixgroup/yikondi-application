package com.zelix.yikondi.web.rest;

import com.zelix.yikondi.YikondiApp;
import com.zelix.yikondi.domain.Pharmacy;
import com.zelix.yikondi.repository.PharmacyRepository;
import com.zelix.yikondi.repository.search.PharmacySearchRepository;
import com.zelix.yikondi.service.PharmacyService;
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
import org.springframework.util.Base64Utils;
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
 * Integration tests for the {@link PharmacyResource} REST controller.
 */
@SpringBootTest(classes = YikondiApp.class)
public class PharmacyResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final byte[] DEFAULT_LOGO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_LOGO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_LOGO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_LOGO_CONTENT_TYPE = "image/png";

    @Autowired
    private PharmacyRepository pharmacyRepository;

    @Autowired
    private PharmacyService pharmacyService;

    /**
     * This repository is mocked in the com.zelix.yikondi.repository.search test package.
     *
     * @see com.zelix.yikondi.repository.search.PharmacySearchRepositoryMockConfiguration
     */
    @Autowired
    private PharmacySearchRepository mockPharmacySearchRepository;

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

    private MockMvc restPharmacyMockMvc;

    private Pharmacy pharmacy;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PharmacyResource pharmacyResource = new PharmacyResource(pharmacyService);
        this.restPharmacyMockMvc = MockMvcBuilders.standaloneSetup(pharmacyResource)
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
    public static Pharmacy createEntity(EntityManager em) {
        Pharmacy pharmacy = new Pharmacy()
            .name(DEFAULT_NAME)
            .logo(DEFAULT_LOGO)
            .logoContentType(DEFAULT_LOGO_CONTENT_TYPE);
        return pharmacy;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pharmacy createUpdatedEntity(EntityManager em) {
        Pharmacy pharmacy = new Pharmacy()
            .name(UPDATED_NAME)
            .logo(UPDATED_LOGO)
            .logoContentType(UPDATED_LOGO_CONTENT_TYPE);
        return pharmacy;
    }

    @BeforeEach
    public void initTest() {
        pharmacy = createEntity(em);
    }

    @Test
    @Transactional
    public void createPharmacy() throws Exception {
        int databaseSizeBeforeCreate = pharmacyRepository.findAll().size();

        // Create the Pharmacy
        restPharmacyMockMvc.perform(post("/api/pharmacies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pharmacy)))
            .andExpect(status().isCreated());

        // Validate the Pharmacy in the database
        List<Pharmacy> pharmacyList = pharmacyRepository.findAll();
        assertThat(pharmacyList).hasSize(databaseSizeBeforeCreate + 1);
        Pharmacy testPharmacy = pharmacyList.get(pharmacyList.size() - 1);
        assertThat(testPharmacy.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPharmacy.getLogo()).isEqualTo(DEFAULT_LOGO);
        assertThat(testPharmacy.getLogoContentType()).isEqualTo(DEFAULT_LOGO_CONTENT_TYPE);

        // Validate the Pharmacy in Elasticsearch
        verify(mockPharmacySearchRepository, times(1)).save(testPharmacy);
    }

    @Test
    @Transactional
    public void createPharmacyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pharmacyRepository.findAll().size();

        // Create the Pharmacy with an existing ID
        pharmacy.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPharmacyMockMvc.perform(post("/api/pharmacies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pharmacy)))
            .andExpect(status().isBadRequest());

        // Validate the Pharmacy in the database
        List<Pharmacy> pharmacyList = pharmacyRepository.findAll();
        assertThat(pharmacyList).hasSize(databaseSizeBeforeCreate);

        // Validate the Pharmacy in Elasticsearch
        verify(mockPharmacySearchRepository, times(0)).save(pharmacy);
    }


    @Test
    @Transactional
    public void getAllPharmacies() throws Exception {
        // Initialize the database
        pharmacyRepository.saveAndFlush(pharmacy);

        // Get all the pharmacyList
        restPharmacyMockMvc.perform(get("/api/pharmacies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pharmacy.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].logoContentType").value(hasItem(DEFAULT_LOGO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].logo").value(hasItem(Base64Utils.encodeToString(DEFAULT_LOGO))));
    }
    
    @Test
    @Transactional
    public void getPharmacy() throws Exception {
        // Initialize the database
        pharmacyRepository.saveAndFlush(pharmacy);

        // Get the pharmacy
        restPharmacyMockMvc.perform(get("/api/pharmacies/{id}", pharmacy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pharmacy.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.logoContentType").value(DEFAULT_LOGO_CONTENT_TYPE))
            .andExpect(jsonPath("$.logo").value(Base64Utils.encodeToString(DEFAULT_LOGO)));
    }

    @Test
    @Transactional
    public void getNonExistingPharmacy() throws Exception {
        // Get the pharmacy
        restPharmacyMockMvc.perform(get("/api/pharmacies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePharmacy() throws Exception {
        // Initialize the database
        pharmacyService.save(pharmacy);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockPharmacySearchRepository);

        int databaseSizeBeforeUpdate = pharmacyRepository.findAll().size();

        // Update the pharmacy
        Pharmacy updatedPharmacy = pharmacyRepository.findById(pharmacy.getId()).get();
        // Disconnect from session so that the updates on updatedPharmacy are not directly saved in db
        em.detach(updatedPharmacy);
        updatedPharmacy
            .name(UPDATED_NAME)
            .logo(UPDATED_LOGO)
            .logoContentType(UPDATED_LOGO_CONTENT_TYPE);

        restPharmacyMockMvc.perform(put("/api/pharmacies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPharmacy)))
            .andExpect(status().isOk());

        // Validate the Pharmacy in the database
        List<Pharmacy> pharmacyList = pharmacyRepository.findAll();
        assertThat(pharmacyList).hasSize(databaseSizeBeforeUpdate);
        Pharmacy testPharmacy = pharmacyList.get(pharmacyList.size() - 1);
        assertThat(testPharmacy.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPharmacy.getLogo()).isEqualTo(UPDATED_LOGO);
        assertThat(testPharmacy.getLogoContentType()).isEqualTo(UPDATED_LOGO_CONTENT_TYPE);

        // Validate the Pharmacy in Elasticsearch
        verify(mockPharmacySearchRepository, times(1)).save(testPharmacy);
    }

    @Test
    @Transactional
    public void updateNonExistingPharmacy() throws Exception {
        int databaseSizeBeforeUpdate = pharmacyRepository.findAll().size();

        // Create the Pharmacy

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPharmacyMockMvc.perform(put("/api/pharmacies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pharmacy)))
            .andExpect(status().isBadRequest());

        // Validate the Pharmacy in the database
        List<Pharmacy> pharmacyList = pharmacyRepository.findAll();
        assertThat(pharmacyList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Pharmacy in Elasticsearch
        verify(mockPharmacySearchRepository, times(0)).save(pharmacy);
    }

    @Test
    @Transactional
    public void deletePharmacy() throws Exception {
        // Initialize the database
        pharmacyService.save(pharmacy);

        int databaseSizeBeforeDelete = pharmacyRepository.findAll().size();

        // Delete the pharmacy
        restPharmacyMockMvc.perform(delete("/api/pharmacies/{id}", pharmacy.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Pharmacy> pharmacyList = pharmacyRepository.findAll();
        assertThat(pharmacyList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Pharmacy in Elasticsearch
        verify(mockPharmacySearchRepository, times(1)).deleteById(pharmacy.getId());
    }

    @Test
    @Transactional
    public void searchPharmacy() throws Exception {
        // Initialize the database
        pharmacyService.save(pharmacy);
        when(mockPharmacySearchRepository.search(queryStringQuery("id:" + pharmacy.getId())))
            .thenReturn(Collections.singletonList(pharmacy));
        // Search the pharmacy
        restPharmacyMockMvc.perform(get("/api/_search/pharmacies?query=id:" + pharmacy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pharmacy.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].logoContentType").value(hasItem(DEFAULT_LOGO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].logo").value(hasItem(Base64Utils.encodeToString(DEFAULT_LOGO))));
    }
}
