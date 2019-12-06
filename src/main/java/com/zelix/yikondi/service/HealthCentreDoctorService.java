package com.zelix.yikondi.service;

import com.zelix.yikondi.domain.HealthCentreDoctor;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link HealthCentreDoctor}.
 */
public interface HealthCentreDoctorService {

    /**
     * Save a healthCentreDoctor.
     *
     * @param healthCentreDoctor the entity to save.
     * @return the persisted entity.
     */
    HealthCentreDoctor save(HealthCentreDoctor healthCentreDoctor);

    /**
     * Get all the healthCentreDoctors.
     *
     * @return the list of entities.
     */
    List<HealthCentreDoctor> findAll();


    /**
     * Get the "id" healthCentreDoctor.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<HealthCentreDoctor> findOne(Long id);

    /**
     * Delete the "id" healthCentreDoctor.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the healthCentreDoctor corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<HealthCentreDoctor> search(String query);
}
