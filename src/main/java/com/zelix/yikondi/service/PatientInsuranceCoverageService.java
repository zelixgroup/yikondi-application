package com.zelix.yikondi.service;

import com.zelix.yikondi.domain.PatientInsuranceCoverage;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link PatientInsuranceCoverage}.
 */
public interface PatientInsuranceCoverageService {

    /**
     * Save a patientInsuranceCoverage.
     *
     * @param patientInsuranceCoverage the entity to save.
     * @return the persisted entity.
     */
    PatientInsuranceCoverage save(PatientInsuranceCoverage patientInsuranceCoverage);

    /**
     * Get all the patientInsuranceCoverages.
     *
     * @return the list of entities.
     */
    List<PatientInsuranceCoverage> findAll();


    /**
     * Get the "id" patientInsuranceCoverage.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PatientInsuranceCoverage> findOne(Long id);

    /**
     * Delete the "id" patientInsuranceCoverage.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the patientInsuranceCoverage corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<PatientInsuranceCoverage> search(String query);
}
