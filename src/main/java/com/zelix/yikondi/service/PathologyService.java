package com.zelix.yikondi.service;

import com.zelix.yikondi.domain.Pathology;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Pathology}.
 */
public interface PathologyService {

    /**
     * Save a pathology.
     *
     * @param pathology the entity to save.
     * @return the persisted entity.
     */
    Pathology save(Pathology pathology);

    /**
     * Get all the pathologies.
     *
     * @return the list of entities.
     */
    List<Pathology> findAll();


    /**
     * Get the "id" pathology.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Pathology> findOne(Long id);

    /**
     * Delete the "id" pathology.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the pathology corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<Pathology> search(String query);
}
