package com.zelix.yikondi.service;

import com.zelix.yikondi.domain.DrugDosageForm;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link DrugDosageForm}.
 */
public interface DrugDosageFormService {

    /**
     * Save a drugDosageForm.
     *
     * @param drugDosageForm the entity to save.
     * @return the persisted entity.
     */
    DrugDosageForm save(DrugDosageForm drugDosageForm);

    /**
     * Get all the drugDosageForms.
     *
     * @return the list of entities.
     */
    List<DrugDosageForm> findAll();


    /**
     * Get the "id" drugDosageForm.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DrugDosageForm> findOne(Long id);

    /**
     * Delete the "id" drugDosageForm.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the drugDosageForm corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<DrugDosageForm> search(String query);
}
