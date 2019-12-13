package com.zelix.yikondi.service;

import com.zelix.yikondi.domain.Analysis;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Analysis}.
 */
public interface AnalysisService {

    /**
     * Save a analysis.
     *
     * @param analysis the entity to save.
     * @return the persisted entity.
     */
    Analysis save(Analysis analysis);

    /**
     * Get all the analyses.
     *
     * @return the list of entities.
     */
    List<Analysis> findAll();


    /**
     * Get the "id" analysis.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Analysis> findOne(Long id);

    /**
     * Delete the "id" analysis.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the analysis corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<Analysis> search(String query);
}
