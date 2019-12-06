package com.zelix.yikondi.service.impl;

import com.zelix.yikondi.service.HealthCentreService;
import com.zelix.yikondi.domain.HealthCentre;
import com.zelix.yikondi.repository.HealthCentreRepository;
import com.zelix.yikondi.repository.search.HealthCentreSearchRepository;
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
 * Service Implementation for managing {@link HealthCentre}.
 */
@Service
@Transactional
public class HealthCentreServiceImpl implements HealthCentreService {

    private final Logger log = LoggerFactory.getLogger(HealthCentreServiceImpl.class);

    private final HealthCentreRepository healthCentreRepository;

    private final HealthCentreSearchRepository healthCentreSearchRepository;

    public HealthCentreServiceImpl(HealthCentreRepository healthCentreRepository, HealthCentreSearchRepository healthCentreSearchRepository) {
        this.healthCentreRepository = healthCentreRepository;
        this.healthCentreSearchRepository = healthCentreSearchRepository;
    }

    /**
     * Save a healthCentre.
     *
     * @param healthCentre the entity to save.
     * @return the persisted entity.
     */
    @Override
    public HealthCentre save(HealthCentre healthCentre) {
        log.debug("Request to save HealthCentre : {}", healthCentre);
        HealthCentre result = healthCentreRepository.save(healthCentre);
        healthCentreSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the healthCentres.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<HealthCentre> findAll() {
        log.debug("Request to get all HealthCentres");
        return healthCentreRepository.findAll();
    }


    /**
     * Get one healthCentre by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<HealthCentre> findOne(Long id) {
        log.debug("Request to get HealthCentre : {}", id);
        return healthCentreRepository.findById(id);
    }

    /**
     * Delete the healthCentre by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete HealthCentre : {}", id);
        healthCentreRepository.deleteById(id);
        healthCentreSearchRepository.deleteById(id);
    }

    /**
     * Search for the healthCentre corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<HealthCentre> search(String query) {
        log.debug("Request to search HealthCentres for query {}", query);
        return StreamSupport
            .stream(healthCentreSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
