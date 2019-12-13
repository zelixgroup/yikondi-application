package com.zelix.yikondi.service;

import com.zelix.yikondi.domain.InsuranceType;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link InsuranceType}.
 */
public interface InsuranceTypeService {

    /**
     * Save a insuranceType.
     *
     * @param insuranceType the entity to save.
     * @return the persisted entity.
     */
    InsuranceType save(InsuranceType insuranceType);

    /**
     * Get all the insuranceTypes.
     *
     * @return the list of entities.
     */
    List<InsuranceType> findAll();


    /**
     * Get the "id" insuranceType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<InsuranceType> findOne(Long id);

    /**
     * Delete the "id" insuranceType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the insuranceType corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<InsuranceType> search(String query);
}
