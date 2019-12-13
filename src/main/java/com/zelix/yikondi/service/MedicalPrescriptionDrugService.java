package com.zelix.yikondi.service;

import com.zelix.yikondi.domain.MedicalPrescriptionDrug;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link MedicalPrescriptionDrug}.
 */
public interface MedicalPrescriptionDrugService {

    /**
     * Save a medicalPrescriptionDrug.
     *
     * @param medicalPrescriptionDrug the entity to save.
     * @return the persisted entity.
     */
    MedicalPrescriptionDrug save(MedicalPrescriptionDrug medicalPrescriptionDrug);

    /**
     * Get all the medicalPrescriptionDrugs.
     *
     * @return the list of entities.
     */
    List<MedicalPrescriptionDrug> findAll();


    /**
     * Get the "id" medicalPrescriptionDrug.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MedicalPrescriptionDrug> findOne(Long id);

    /**
     * Delete the "id" medicalPrescriptionDrug.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the medicalPrescriptionDrug corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<MedicalPrescriptionDrug> search(String query);
}
