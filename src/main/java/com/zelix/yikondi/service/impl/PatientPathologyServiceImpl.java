package com.zelix.yikondi.service.impl;

import com.zelix.yikondi.service.PatientPathologyService;
import com.zelix.yikondi.domain.PatientPathology;
import com.zelix.yikondi.repository.PatientPathologyRepository;
import com.zelix.yikondi.repository.search.PatientPathologySearchRepository;
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
 * Service Implementation for managing {@link PatientPathology}.
 */
@Service
@Transactional
public class PatientPathologyServiceImpl implements PatientPathologyService {

    private final Logger log = LoggerFactory.getLogger(PatientPathologyServiceImpl.class);

    private final PatientPathologyRepository patientPathologyRepository;

    private final PatientPathologySearchRepository patientPathologySearchRepository;

    public PatientPathologyServiceImpl(PatientPathologyRepository patientPathologyRepository, PatientPathologySearchRepository patientPathologySearchRepository) {
        this.patientPathologyRepository = patientPathologyRepository;
        this.patientPathologySearchRepository = patientPathologySearchRepository;
    }

    /**
     * Save a patientPathology.
     *
     * @param patientPathology the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PatientPathology save(PatientPathology patientPathology) {
        log.debug("Request to save PatientPathology : {}", patientPathology);
        PatientPathology result = patientPathologyRepository.save(patientPathology);
        patientPathologySearchRepository.save(result);
        return result;
    }

    /**
     * Get all the patientPathologies.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PatientPathology> findAll() {
        log.debug("Request to get all PatientPathologies");
        return patientPathologyRepository.findAll();
    }


    /**
     * Get one patientPathology by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PatientPathology> findOne(Long id) {
        log.debug("Request to get PatientPathology : {}", id);
        return patientPathologyRepository.findById(id);
    }

    /**
     * Delete the patientPathology by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PatientPathology : {}", id);
        patientPathologyRepository.deleteById(id);
        patientPathologySearchRepository.deleteById(id);
    }

    /**
     * Search for the patientPathology corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PatientPathology> search(String query) {
        log.debug("Request to search PatientPathologies for query {}", query);
        return StreamSupport
            .stream(patientPathologySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
