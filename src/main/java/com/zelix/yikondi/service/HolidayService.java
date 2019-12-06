package com.zelix.yikondi.service;

import com.zelix.yikondi.domain.Holiday;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Holiday}.
 */
public interface HolidayService {

    /**
     * Save a holiday.
     *
     * @param holiday the entity to save.
     * @return the persisted entity.
     */
    Holiday save(Holiday holiday);

    /**
     * Get all the holidays.
     *
     * @return the list of entities.
     */
    List<Holiday> findAll();


    /**
     * Get the "id" holiday.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Holiday> findOne(Long id);

    /**
     * Delete the "id" holiday.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the holiday corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<Holiday> search(String query);
}
