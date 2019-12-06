package com.zelix.yikondi.service;

import com.zelix.yikondi.domain.DoctorWorkingSlot;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link DoctorWorkingSlot}.
 */
public interface DoctorWorkingSlotService {

    /**
     * Save a doctorWorkingSlot.
     *
     * @param doctorWorkingSlot the entity to save.
     * @return the persisted entity.
     */
    DoctorWorkingSlot save(DoctorWorkingSlot doctorWorkingSlot);

    /**
     * Get all the doctorWorkingSlots.
     *
     * @return the list of entities.
     */
    List<DoctorWorkingSlot> findAll();


    /**
     * Get the "id" doctorWorkingSlot.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DoctorWorkingSlot> findOne(Long id);

    /**
     * Delete the "id" doctorWorkingSlot.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the doctorWorkingSlot corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<DoctorWorkingSlot> search(String query);
}
