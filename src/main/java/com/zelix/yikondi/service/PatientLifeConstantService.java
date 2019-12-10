package com.zelix.yikondi.service;

import com.zelix.yikondi.domain.PatientLifeConstant;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link PatientLifeConstant}.
 */
public interface PatientLifeConstantService {

    /**
     * Save a patientLifeConstant.
     *
     * @param patientLifeConstant the entity to save.
     * @return the persisted entity.
     */
    PatientLifeConstant save(PatientLifeConstant patientLifeConstant);

    /**
     * Get all the patientLifeConstants.
     *
     * @return the list of entities.
     */
    List<PatientLifeConstant> findAll();


    /**
     * Get the "id" patientLifeConstant.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PatientLifeConstant> findOne(Long id);

    /**
     * Delete the "id" patientLifeConstant.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the patientLifeConstant corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<PatientLifeConstant> search(String query);
}
