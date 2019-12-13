package com.zelix.yikondi.service;

import com.zelix.yikondi.domain.FamilyRelationship;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link FamilyRelationship}.
 */
public interface FamilyRelationshipService {

    /**
     * Save a familyRelationship.
     *
     * @param familyRelationship the entity to save.
     * @return the persisted entity.
     */
    FamilyRelationship save(FamilyRelationship familyRelationship);

    /**
     * Get all the familyRelationships.
     *
     * @return the list of entities.
     */
    List<FamilyRelationship> findAll();


    /**
     * Get the "id" familyRelationship.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FamilyRelationship> findOne(Long id);

    /**
     * Delete the "id" familyRelationship.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the familyRelationship corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<FamilyRelationship> search(String query);
}
