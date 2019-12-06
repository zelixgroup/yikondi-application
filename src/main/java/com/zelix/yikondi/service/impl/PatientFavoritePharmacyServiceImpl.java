package com.zelix.yikondi.service.impl;

import com.zelix.yikondi.service.PatientFavoritePharmacyService;
import com.zelix.yikondi.domain.PatientFavoritePharmacy;
import com.zelix.yikondi.repository.PatientFavoritePharmacyRepository;
import com.zelix.yikondi.repository.search.PatientFavoritePharmacySearchRepository;
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
 * Service Implementation for managing {@link PatientFavoritePharmacy}.
 */
@Service
@Transactional
public class PatientFavoritePharmacyServiceImpl implements PatientFavoritePharmacyService {

    private final Logger log = LoggerFactory.getLogger(PatientFavoritePharmacyServiceImpl.class);

    private final PatientFavoritePharmacyRepository patientFavoritePharmacyRepository;

    private final PatientFavoritePharmacySearchRepository patientFavoritePharmacySearchRepository;

    public PatientFavoritePharmacyServiceImpl(PatientFavoritePharmacyRepository patientFavoritePharmacyRepository, PatientFavoritePharmacySearchRepository patientFavoritePharmacySearchRepository) {
        this.patientFavoritePharmacyRepository = patientFavoritePharmacyRepository;
        this.patientFavoritePharmacySearchRepository = patientFavoritePharmacySearchRepository;
    }

    /**
     * Save a patientFavoritePharmacy.
     *
     * @param patientFavoritePharmacy the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PatientFavoritePharmacy save(PatientFavoritePharmacy patientFavoritePharmacy) {
        log.debug("Request to save PatientFavoritePharmacy : {}", patientFavoritePharmacy);
        PatientFavoritePharmacy result = patientFavoritePharmacyRepository.save(patientFavoritePharmacy);
        patientFavoritePharmacySearchRepository.save(result);
        return result;
    }

    /**
     * Get all the patientFavoritePharmacies.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PatientFavoritePharmacy> findAll() {
        log.debug("Request to get all PatientFavoritePharmacies");
        return patientFavoritePharmacyRepository.findAll();
    }


    /**
     * Get one patientFavoritePharmacy by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PatientFavoritePharmacy> findOne(Long id) {
        log.debug("Request to get PatientFavoritePharmacy : {}", id);
        return patientFavoritePharmacyRepository.findById(id);
    }

    /**
     * Delete the patientFavoritePharmacy by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PatientFavoritePharmacy : {}", id);
        patientFavoritePharmacyRepository.deleteById(id);
        patientFavoritePharmacySearchRepository.deleteById(id);
    }

    /**
     * Search for the patientFavoritePharmacy corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PatientFavoritePharmacy> search(String query) {
        log.debug("Request to search PatientFavoritePharmacies for query {}", query);
        return StreamSupport
            .stream(patientFavoritePharmacySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
