package com.zelix.yikondi.service;

import com.zelix.yikondi.domain.DoctorSchedule;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link DoctorSchedule}.
 */
public interface DoctorScheduleService {

    /**
     * Save a doctorSchedule.
     *
     * @param doctorSchedule the entity to save.
     * @return the persisted entity.
     */
    DoctorSchedule save(DoctorSchedule doctorSchedule);

    /**
     * Get all the doctorSchedules.
     *
     * @return the list of entities.
     */
    List<DoctorSchedule> findAll();


    /**
     * Get the "id" doctorSchedule.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DoctorSchedule> findOne(Long id);

    /**
     * Delete the "id" doctorSchedule.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the doctorSchedule corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<DoctorSchedule> search(String query);
}
