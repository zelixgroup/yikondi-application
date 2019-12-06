package com.zelix.yikondi.service;

import com.zelix.yikondi.domain.HealthCentre;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link HealthCentre}.
 */
public interface HealthCentreService {

    /**
     * Save a healthCentre.
     *
     * @param healthCentre the entity to save.
     * @return the persisted entity.
     */
    HealthCentre save(HealthCentre healthCentre);

    /**
     * Get all the healthCentres.
     *
     * @return the list of entities.
     */
    List<HealthCentre> findAll();


    /**
     * Get the "id" healthCentre.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<HealthCentre> findOne(Long id);

    /**
     * Delete the "id" healthCentre.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the healthCentre corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<HealthCentre> search(String query);
}
