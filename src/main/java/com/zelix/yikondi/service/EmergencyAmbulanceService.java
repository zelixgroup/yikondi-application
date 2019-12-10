package com.zelix.yikondi.service;

import com.zelix.yikondi.domain.EmergencyAmbulance;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link EmergencyAmbulance}.
 */
public interface EmergencyAmbulanceService {

    /**
     * Save a emergencyAmbulance.
     *
     * @param emergencyAmbulance the entity to save.
     * @return the persisted entity.
     */
    EmergencyAmbulance save(EmergencyAmbulance emergencyAmbulance);

    /**
     * Get all the emergencyAmbulances.
     *
     * @return the list of entities.
     */
    List<EmergencyAmbulance> findAll();


    /**
     * Get the "id" emergencyAmbulance.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EmergencyAmbulance> findOne(Long id);

    /**
     * Delete the "id" emergencyAmbulance.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the emergencyAmbulance corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<EmergencyAmbulance> search(String query);
}
