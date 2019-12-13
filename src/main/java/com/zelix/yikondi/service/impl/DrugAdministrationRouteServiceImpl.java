package com.zelix.yikondi.service.impl;

import com.zelix.yikondi.service.DrugAdministrationRouteService;
import com.zelix.yikondi.domain.DrugAdministrationRoute;
import com.zelix.yikondi.repository.DrugAdministrationRouteRepository;
import com.zelix.yikondi.repository.search.DrugAdministrationRouteSearchRepository;
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
 * Service Implementation for managing {@link DrugAdministrationRoute}.
 */
@Service
@Transactional
public class DrugAdministrationRouteServiceImpl implements DrugAdministrationRouteService {

    private final Logger log = LoggerFactory.getLogger(DrugAdministrationRouteServiceImpl.class);

    private final DrugAdministrationRouteRepository drugAdministrationRouteRepository;

    private final DrugAdministrationRouteSearchRepository drugAdministrationRouteSearchRepository;

    public DrugAdministrationRouteServiceImpl(DrugAdministrationRouteRepository drugAdministrationRouteRepository, DrugAdministrationRouteSearchRepository drugAdministrationRouteSearchRepository) {
        this.drugAdministrationRouteRepository = drugAdministrationRouteRepository;
        this.drugAdministrationRouteSearchRepository = drugAdministrationRouteSearchRepository;
    }

    /**
     * Save a drugAdministrationRoute.
     *
     * @param drugAdministrationRoute the entity to save.
     * @return the persisted entity.
     */
    @Override
    public DrugAdministrationRoute save(DrugAdministrationRoute drugAdministrationRoute) {
        log.debug("Request to save DrugAdministrationRoute : {}", drugAdministrationRoute);
        DrugAdministrationRoute result = drugAdministrationRouteRepository.save(drugAdministrationRoute);
        drugAdministrationRouteSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the drugAdministrationRoutes.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<DrugAdministrationRoute> findAll() {
        log.debug("Request to get all DrugAdministrationRoutes");
        return drugAdministrationRouteRepository.findAll();
    }


    /**
     * Get one drugAdministrationRoute by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DrugAdministrationRoute> findOne(Long id) {
        log.debug("Request to get DrugAdministrationRoute : {}", id);
        return drugAdministrationRouteRepository.findById(id);
    }

    /**
     * Delete the drugAdministrationRoute by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DrugAdministrationRoute : {}", id);
        drugAdministrationRouteRepository.deleteById(id);
        drugAdministrationRouteSearchRepository.deleteById(id);
    }

    /**
     * Search for the drugAdministrationRoute corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<DrugAdministrationRoute> search(String query) {
        log.debug("Request to search DrugAdministrationRoutes for query {}", query);
        return StreamSupport
            .stream(drugAdministrationRouteSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
