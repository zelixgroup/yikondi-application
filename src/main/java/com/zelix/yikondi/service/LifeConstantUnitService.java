package com.zelix.yikondi.service;

import com.zelix.yikondi.domain.LifeConstantUnit;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link LifeConstantUnit}.
 */
public interface LifeConstantUnitService {

    /**
     * Save a lifeConstantUnit.
     *
     * @param lifeConstantUnit the entity to save.
     * @return the persisted entity.
     */
    LifeConstantUnit save(LifeConstantUnit lifeConstantUnit);

    /**
     * Get all the lifeConstantUnits.
     *
     * @return the list of entities.
     */
    List<LifeConstantUnit> findAll();


    /**
     * Get the "id" lifeConstantUnit.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LifeConstantUnit> findOne(Long id);

    /**
     * Delete the "id" lifeConstantUnit.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the lifeConstantUnit corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<LifeConstantUnit> search(String query);
}
