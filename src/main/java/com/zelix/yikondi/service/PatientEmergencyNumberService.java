package com.zelix.yikondi.service;

import com.zelix.yikondi.domain.PatientEmergencyNumber;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link PatientEmergencyNumber}.
 */
public interface PatientEmergencyNumberService {

    /**
     * Save a patientEmergencyNumber.
     *
     * @param patientEmergencyNumber the entity to save.
     * @return the persisted entity.
     */
    PatientEmergencyNumber save(PatientEmergencyNumber patientEmergencyNumber);

    /**
     * Get all the patientEmergencyNumbers.
     *
     * @return the list of entities.
     */
    List<PatientEmergencyNumber> findAll();


    /**
     * Get the "id" patientEmergencyNumber.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PatientEmergencyNumber> findOne(Long id);

    /**
     * Delete the "id" patientEmergencyNumber.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the patientEmergencyNumber corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<PatientEmergencyNumber> search(String query);
}
