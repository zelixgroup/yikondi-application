package com.zelix.yikondi.service;

import com.zelix.yikondi.domain.PatientAllergy;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link PatientAllergy}.
 */
public interface PatientAllergyService {

    /**
     * Save a patientAllergy.
     *
     * @param patientAllergy the entity to save.
     * @return the persisted entity.
     */
    PatientAllergy save(PatientAllergy patientAllergy);

    /**
     * Get all the patientAllergies.
     *
     * @return the list of entities.
     */
    List<PatientAllergy> findAll();


    /**
     * Get the "id" patientAllergy.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PatientAllergy> findOne(Long id);

    /**
     * Delete the "id" patientAllergy.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the patientAllergy corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<PatientAllergy> search(String query);
}
