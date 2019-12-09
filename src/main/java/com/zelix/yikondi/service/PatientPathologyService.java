package com.zelix.yikondi.service;

import com.zelix.yikondi.domain.PatientPathology;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link PatientPathology}.
 */
public interface PatientPathologyService {

    /**
     * Save a patientPathology.
     *
     * @param patientPathology the entity to save.
     * @return the persisted entity.
     */
    PatientPathology save(PatientPathology patientPathology);

    /**
     * Get all the patientPathologies.
     *
     * @return the list of entities.
     */
    List<PatientPathology> findAll();


    /**
     * Get the "id" patientPathology.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PatientPathology> findOne(Long id);

    /**
     * Delete the "id" patientPathology.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the patientPathology corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<PatientPathology> search(String query);
}
