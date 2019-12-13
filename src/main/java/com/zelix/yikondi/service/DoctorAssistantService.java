package com.zelix.yikondi.service;

import com.zelix.yikondi.domain.DoctorAssistant;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link DoctorAssistant}.
 */
public interface DoctorAssistantService {

    /**
     * Save a doctorAssistant.
     *
     * @param doctorAssistant the entity to save.
     * @return the persisted entity.
     */
    DoctorAssistant save(DoctorAssistant doctorAssistant);

    /**
     * Get all the doctorAssistants.
     *
     * @return the list of entities.
     */
    List<DoctorAssistant> findAll();


    /**
     * Get the "id" doctorAssistant.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DoctorAssistant> findOne(Long id);

    /**
     * Delete the "id" doctorAssistant.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the doctorAssistant corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<DoctorAssistant> search(String query);
}
