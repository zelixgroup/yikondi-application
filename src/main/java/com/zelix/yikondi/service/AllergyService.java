package com.zelix.yikondi.service;

import com.zelix.yikondi.domain.Allergy;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Allergy}.
 */
public interface AllergyService {

    /**
     * Save a allergy.
     *
     * @param allergy the entity to save.
     * @return the persisted entity.
     */
    Allergy save(Allergy allergy);

    /**
     * Get all the allergies.
     *
     * @return the list of entities.
     */
    List<Allergy> findAll();


    /**
     * Get the "id" allergy.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Allergy> findOne(Long id);

    /**
     * Delete the "id" allergy.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the allergy corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<Allergy> search(String query);
}
