package com.zelix.yikondi.service;

import com.zelix.yikondi.domain.MedicalPrescriptionAnalysis;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link MedicalPrescriptionAnalysis}.
 */
public interface MedicalPrescriptionAnalysisService {

    /**
     * Save a medicalPrescriptionAnalysis.
     *
     * @param medicalPrescriptionAnalysis the entity to save.
     * @return the persisted entity.
     */
    MedicalPrescriptionAnalysis save(MedicalPrescriptionAnalysis medicalPrescriptionAnalysis);

    /**
     * Get all the medicalPrescriptionAnalyses.
     *
     * @return the list of entities.
     */
    List<MedicalPrescriptionAnalysis> findAll();


    /**
     * Get the "id" medicalPrescriptionAnalysis.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MedicalPrescriptionAnalysis> findOne(Long id);

    /**
     * Delete the "id" medicalPrescriptionAnalysis.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the medicalPrescriptionAnalysis corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<MedicalPrescriptionAnalysis> search(String query);
}
