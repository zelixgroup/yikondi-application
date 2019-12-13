package com.zelix.yikondi.service;

import com.zelix.yikondi.domain.DrugAdministrationRoute;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link DrugAdministrationRoute}.
 */
public interface DrugAdministrationRouteService {

    /**
     * Save a drugAdministrationRoute.
     *
     * @param drugAdministrationRoute the entity to save.
     * @return the persisted entity.
     */
    DrugAdministrationRoute save(DrugAdministrationRoute drugAdministrationRoute);

    /**
     * Get all the drugAdministrationRoutes.
     *
     * @return the list of entities.
     */
    List<DrugAdministrationRoute> findAll();


    /**
     * Get the "id" drugAdministrationRoute.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DrugAdministrationRoute> findOne(Long id);

    /**
     * Delete the "id" drugAdministrationRoute.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the drugAdministrationRoute corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<DrugAdministrationRoute> search(String query);
}
