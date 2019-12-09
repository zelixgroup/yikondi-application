package com.zelix.yikondi.service.impl;

import com.zelix.yikondi.service.PatientLifeConstantService;
import com.zelix.yikondi.domain.PatientLifeConstant;
import com.zelix.yikondi.repository.PatientLifeConstantRepository;
import com.zelix.yikondi.repository.search.PatientLifeConstantSearchRepository;
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
 * Service Implementation for managing {@link PatientLifeConstant}.
 */
@Service
@Transactional
public class PatientLifeConstantServiceImpl implements PatientLifeConstantService {

    private final Logger log = LoggerFactory.getLogger(PatientLifeConstantServiceImpl.class);

    private final PatientLifeConstantRepository patientLifeConstantRepository;

    private final PatientLifeConstantSearchRepository patientLifeConstantSearchRepository;

    public PatientLifeConstantServiceImpl(PatientLifeConstantRepository patientLifeConstantRepository, PatientLifeConstantSearchRepository patientLifeConstantSearchRepository) {
        this.patientLifeConstantRepository = patientLifeConstantRepository;
        this.patientLifeConstantSearchRepository = patientLifeConstantSearchRepository;
    }

    /**
     * Save a patientLifeConstant.
     *
     * @param patientLifeConstant the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PatientLifeConstant save(PatientLifeConstant patientLifeConstant) {
        log.debug("Request to save PatientLifeConstant : {}", patientLifeConstant);
        PatientLifeConstant result = patientLifeConstantRepository.save(patientLifeConstant);
        patientLifeConstantSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the patientLifeConstants.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PatientLifeConstant> findAll() {
        log.debug("Request to get all PatientLifeConstants");
        return patientLifeConstantRepository.findAll();
    }


    /**
     * Get one patientLifeConstant by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PatientLifeConstant> findOne(Long id) {
        log.debug("Request to get PatientLifeConstant : {}", id);
        return patientLifeConstantRepository.findById(id);
    }

    /**
     * Delete the patientLifeConstant by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PatientLifeConstant : {}", id);
        patientLifeConstantRepository.deleteById(id);
        patientLifeConstantSearchRepository.deleteById(id);
    }

    /**
     * Search for the patientLifeConstant corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PatientLifeConstant> search(String query) {
        log.debug("Request to search PatientLifeConstants for query {}", query);
        return StreamSupport
            .stream(patientLifeConstantSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
