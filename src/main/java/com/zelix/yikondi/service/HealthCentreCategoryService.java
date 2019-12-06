package com.zelix.yikondi.service;

import com.zelix.yikondi.domain.HealthCentreCategory;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link HealthCentreCategory}.
 */
public interface HealthCentreCategoryService {

    /**
     * Save a healthCentreCategory.
     *
     * @param healthCentreCategory the entity to save.
     * @return the persisted entity.
     */
    HealthCentreCategory save(HealthCentreCategory healthCentreCategory);

    /**
     * Get all the healthCentreCategories.
     *
     * @return the list of entities.
     */
    List<HealthCentreCategory> findAll();


    /**
     * Get the "id" healthCentreCategory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<HealthCentreCategory> findOne(Long id);

    /**
     * Delete the "id" healthCentreCategory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the healthCentreCategory corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<HealthCentreCategory> search(String query);
}
