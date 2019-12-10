package com.zelix.yikondi.service;

import com.zelix.yikondi.domain.LifeConstant;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link LifeConstant}.
 */
public interface LifeConstantService {

    /**
     * Save a lifeConstant.
     *
     * @param lifeConstant the entity to save.
     * @return the persisted entity.
     */
    LifeConstant save(LifeConstant lifeConstant);

    /**
     * Get all the lifeConstants.
     *
     * @return the list of entities.
     */
    List<LifeConstant> findAll();


    /**
     * Get the "id" lifeConstant.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LifeConstant> findOne(Long id);

    /**
     * Delete the "id" lifeConstant.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the lifeConstant corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<LifeConstant> search(String query);
}
