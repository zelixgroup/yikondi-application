package com.zelix.yikondi.service;

import com.zelix.yikondi.domain.PharmacyAllNightPlanning;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link PharmacyAllNightPlanning}.
 */
public interface PharmacyAllNightPlanningService {

    /**
     * Save a pharmacyAllNightPlanning.
     *
     * @param pharmacyAllNightPlanning the entity to save.
     * @return the persisted entity.
     */
    PharmacyAllNightPlanning save(PharmacyAllNightPlanning pharmacyAllNightPlanning);

    /**
     * Get all the pharmacyAllNightPlannings.
     *
     * @return the list of entities.
     */
    List<PharmacyAllNightPlanning> findAll();


    /**
     * Get the "id" pharmacyAllNightPlanning.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PharmacyAllNightPlanning> findOne(Long id);

    /**
     * Delete the "id" pharmacyAllNightPlanning.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the pharmacyAllNightPlanning corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<PharmacyAllNightPlanning> search(String query);
}
