package com.zelix.yikondi.service.impl;

import com.zelix.yikondi.service.HealthCentreDoctorService;
import com.zelix.yikondi.domain.HealthCentreDoctor;
import com.zelix.yikondi.repository.HealthCentreDoctorRepository;
import com.zelix.yikondi.repository.search.HealthCentreDoctorSearchRepository;
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
 * Service Implementation for managing {@link HealthCentreDoctor}.
 */
@Service
@Transactional
public class HealthCentreDoctorServiceImpl implements HealthCentreDoctorService {

    private final Logger log = LoggerFactory.getLogger(HealthCentreDoctorServiceImpl.class);

    private final HealthCentreDoctorRepository healthCentreDoctorRepository;

    private final HealthCentreDoctorSearchRepository healthCentreDoctorSearchRepository;

    public HealthCentreDoctorServiceImpl(HealthCentreDoctorRepository healthCentreDoctorRepository, HealthCentreDoctorSearchRepository healthCentreDoctorSearchRepository) {
        this.healthCentreDoctorRepository = healthCentreDoctorRepository;
        this.healthCentreDoctorSearchRepository = healthCentreDoctorSearchRepository;
    }

    /**
     * Save a healthCentreDoctor.
     *
     * @param healthCentreDoctor the entity to save.
     * @return the persisted entity.
     */
    @Override
    public HealthCentreDoctor save(HealthCentreDoctor healthCentreDoctor) {
        log.debug("Request to save HealthCentreDoctor : {}", healthCentreDoctor);
        HealthCentreDoctor result = healthCentreDoctorRepository.save(healthCentreDoctor);
        healthCentreDoctorSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the healthCentreDoctors.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<HealthCentreDoctor> findAll() {
        log.debug("Request to get all HealthCentreDoctors");
        return healthCentreDoctorRepository.findAll();
    }


    /**
     * Get one healthCentreDoctor by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<HealthCentreDoctor> findOne(Long id) {
        log.debug("Request to get HealthCentreDoctor : {}", id);
        return healthCentreDoctorRepository.findById(id);
    }

    /**
     * Delete the healthCentreDoctor by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete HealthCentreDoctor : {}", id);
        healthCentreDoctorRepository.deleteById(id);
        healthCentreDoctorSearchRepository.deleteById(id);
    }

    /**
     * Search for the healthCentreDoctor corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<HealthCentreDoctor> search(String query) {
        log.debug("Request to search HealthCentreDoctors for query {}", query);
        return StreamSupport
            .stream(healthCentreDoctorSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
