package com.zelix.yikondi.service.impl;

import com.zelix.yikondi.service.MedicalRecordAuthorizationService;
import com.zelix.yikondi.domain.MedicalRecordAuthorization;
import com.zelix.yikondi.repository.MedicalRecordAuthorizationRepository;
import com.zelix.yikondi.repository.search.MedicalRecordAuthorizationSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link MedicalRecordAuthorization}.
 */
@Service
@Transactional
public class MedicalRecordAuthorizationServiceImpl implements MedicalRecordAuthorizationService {

    private final Logger log = LoggerFactory.getLogger(MedicalRecordAuthorizationServiceImpl.class);

    private final MedicalRecordAuthorizationRepository medicalRecordAuthorizationRepository;

    private final MedicalRecordAuthorizationSearchRepository medicalRecordAuthorizationSearchRepository;

    public MedicalRecordAuthorizationServiceImpl(MedicalRecordAuthorizationRepository medicalRecordAuthorizationRepository, MedicalRecordAuthorizationSearchRepository medicalRecordAuthorizationSearchRepository) {
        this.medicalRecordAuthorizationRepository = medicalRecordAuthorizationRepository;
        this.medicalRecordAuthorizationSearchRepository = medicalRecordAuthorizationSearchRepository;
    }

    /**
     * Save a medicalRecordAuthorization.
     *
     * @param medicalRecordAuthorization the entity to save.
     * @return the persisted entity.
     */
    @Override
    public MedicalRecordAuthorization save(MedicalRecordAuthorization medicalRecordAuthorization) {
        log.debug("Request to save MedicalRecordAuthorization : {}", medicalRecordAuthorization);
        MedicalRecordAuthorization result = medicalRecordAuthorizationRepository.save(medicalRecordAuthorization);
        medicalRecordAuthorizationSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the medicalRecordAuthorizations.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<MedicalRecordAuthorization> findAll() {
        log.debug("Request to get all MedicalRecordAuthorizations");
        return medicalRecordAuthorizationRepository.findAll();
    }


    /**
     * Get one medicalRecordAuthorization by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MedicalRecordAuthorization> findOne(Long id) {
        log.debug("Request to get MedicalRecordAuthorization : {}", id);
        return medicalRecordAuthorizationRepository.findById(id);
    }

    /**
     * Delete the medicalRecordAuthorization by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MedicalRecordAuthorization : {}", id);
        medicalRecordAuthorizationRepository.deleteById(id);
        medicalRecordAuthorizationSearchRepository.deleteById(id);
    }

    /**
     * Search for the medicalRecordAuthorization corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<MedicalRecordAuthorization> search(String query) {
        log.debug("Request to search MedicalRecordAuthorizations for query {}", query);
        return StreamSupport
            .stream(medicalRecordAuthorizationSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
