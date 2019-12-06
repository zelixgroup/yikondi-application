package com.zelix.yikondi.service;

import com.zelix.yikondi.domain.PatientAppointement;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link PatientAppointement}.
 */
public interface PatientAppointementService {

    /**
     * Save a patientAppointement.
     *
     * @param patientAppointement the entity to save.
     * @return the persisted entity.
     */
    PatientAppointement save(PatientAppointement patientAppointement);

    /**
     * Get all the patientAppointements.
     *
     * @return the list of entities.
     */
    List<PatientAppointement> findAll();


    /**
     * Get the "id" patientAppointement.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PatientAppointement> findOne(Long id);

    /**
     * Delete the "id" patientAppointement.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the patientAppointement corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<PatientAppointement> search(String query);
}
