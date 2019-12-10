package com.zelix.yikondi.service.impl;

import com.zelix.yikondi.service.PatientEmergencyNumberService;
import com.zelix.yikondi.domain.PatientEmergencyNumber;
import com.zelix.yikondi.repository.PatientEmergencyNumberRepository;
import com.zelix.yikondi.repository.search.PatientEmergencyNumberSearchRepository;
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
 * Service Implementation for managing {@link PatientEmergencyNumber}.
 */
@Service
@Transactional
public class PatientEmergencyNumberServiceImpl implements PatientEmergencyNumberService {

    private final Logger log = LoggerFactory.getLogger(PatientEmergencyNumberServiceImpl.class);

    private final PatientEmergencyNumberRepository patientEmergencyNumberRepository;

    private final PatientEmergencyNumberSearchRepository patientEmergencyNumberSearchRepository;

    public PatientEmergencyNumberServiceImpl(PatientEmergencyNumberRepository patientEmergencyNumberRepository, PatientEmergencyNumberSearchRepository patientEmergencyNumberSearchRepository) {
        this.patientEmergencyNumberRepository = patientEmergencyNumberRepository;
        this.patientEmergencyNumberSearchRepository = patientEmergencyNumberSearchRepository;
    }

    /**
     * Save a patientEmergencyNumber.
     *
     * @param patientEmergencyNumber the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PatientEmergencyNumber save(PatientEmergencyNumber patientEmergencyNumber) {
        log.debug("Request to save PatientEmergencyNumber : {}", patientEmergencyNumber);
        PatientEmergencyNumber result = patientEmergencyNumberRepository.save(patientEmergencyNumber);
        patientEmergencyNumberSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the patientEmergencyNumbers.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PatientEmergencyNumber> findAll() {
        log.debug("Request to get all PatientEmergencyNumbers");
        return patientEmergencyNumberRepository.findAll();
    }


    /**
     * Get one patientEmergencyNumber by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PatientEmergencyNumber> findOne(Long id) {
        log.debug("Request to get PatientEmergencyNumber : {}", id);
        return patientEmergencyNumberRepository.findById(id);
    }

    /**
     * Delete the patientEmergencyNumber by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PatientEmergencyNumber : {}", id);
        patientEmergencyNumberRepository.deleteById(id);
        patientEmergencyNumberSearchRepository.deleteById(id);
    }

    /**
     * Search for the patientEmergencyNumber corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PatientEmergencyNumber> search(String query) {
        log.debug("Request to search PatientEmergencyNumbers for query {}", query);
        return StreamSupport
            .stream(patientEmergencyNumberSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
