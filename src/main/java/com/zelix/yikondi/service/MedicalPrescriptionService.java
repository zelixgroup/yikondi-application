package com.zelix.yikondi.service;

import com.zelix.yikondi.domain.MedicalPrescription;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link MedicalPrescription}.
 */
public interface MedicalPrescriptionService {

    /**
     * Save a medicalPrescription.
     *
     * @param medicalPrescription the entity to save.
     * @return the persisted entity.
     */
    MedicalPrescription save(MedicalPrescription medicalPrescription);

    /**
     * Get all the medicalPrescriptions.
     *
     * @return the list of entities.
     */
    List<MedicalPrescription> findAll();


    /**
     * Get the "id" medicalPrescription.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MedicalPrescription> findOne(Long id);

    /**
     * Delete the "id" medicalPrescription.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the medicalPrescription corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<MedicalPrescription> search(String query);
}
