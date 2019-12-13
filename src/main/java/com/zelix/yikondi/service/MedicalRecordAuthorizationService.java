package com.zelix.yikondi.service;

import com.zelix.yikondi.domain.MedicalRecordAuthorization;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link MedicalRecordAuthorization}.
 */
public interface MedicalRecordAuthorizationService {

    /**
     * Save a medicalRecordAuthorization.
     *
     * @param medicalRecordAuthorization the entity to save.
     * @return the persisted entity.
     */
    MedicalRecordAuthorization save(MedicalRecordAuthorization medicalRecordAuthorization);

    /**
     * Get all the medicalRecordAuthorizations.
     *
     * @return the list of entities.
     */
    List<MedicalRecordAuthorization> findAll();


    /**
     * Get the "id" medicalRecordAuthorization.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MedicalRecordAuthorization> findOne(Long id);

    /**
     * Delete the "id" medicalRecordAuthorization.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the medicalRecordAuthorization corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<MedicalRecordAuthorization> search(String query);
}
