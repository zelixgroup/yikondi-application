package com.zelix.yikondi.service.impl;

import com.zelix.yikondi.service.PatientAppointementService;
import com.zelix.yikondi.domain.PatientAppointement;
import com.zelix.yikondi.repository.PatientAppointementRepository;
import com.zelix.yikondi.repository.search.PatientAppointementSearchRepository;
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
 * Service Implementation for managing {@link PatientAppointement}.
 */
@Service
@Transactional
public class PatientAppointementServiceImpl implements PatientAppointementService {

    private final Logger log = LoggerFactory.getLogger(PatientAppointementServiceImpl.class);

    private final PatientAppointementRepository patientAppointementRepository;

    private final PatientAppointementSearchRepository patientAppointementSearchRepository;

    public PatientAppointementServiceImpl(PatientAppointementRepository patientAppointementRepository, PatientAppointementSearchRepository patientAppointementSearchRepository) {
        this.patientAppointementRepository = patientAppointementRepository;
        this.patientAppointementSearchRepository = patientAppointementSearchRepository;
    }

    /**
     * Save a patientAppointement.
     *
     * @param patientAppointement the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PatientAppointement save(PatientAppointement patientAppointement) {
        log.debug("Request to save PatientAppointement : {}", patientAppointement);
        PatientAppointement result = patientAppointementRepository.save(patientAppointement);
        patientAppointementSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the patientAppointements.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PatientAppointement> findAll() {
        log.debug("Request to get all PatientAppointements");
        return patientAppointementRepository.findAll();
    }


    /**
     * Get one patientAppointement by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PatientAppointement> findOne(Long id) {
        log.debug("Request to get PatientAppointement : {}", id);
        return patientAppointementRepository.findById(id);
    }

    /**
     * Delete the patientAppointement by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PatientAppointement : {}", id);
        patientAppointementRepository.deleteById(id);
        patientAppointementSearchRepository.deleteById(id);
    }

    /**
     * Search for the patientAppointement corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PatientAppointement> search(String query) {
        log.debug("Request to search PatientAppointements for query {}", query);
        return StreamSupport
            .stream(patientAppointementSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
