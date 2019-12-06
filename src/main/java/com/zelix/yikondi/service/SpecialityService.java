package com.zelix.yikondi.service;

import com.zelix.yikondi.domain.Speciality;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Speciality}.
 */
public interface SpecialityService {

    /**
     * Save a speciality.
     *
     * @param speciality the entity to save.
     * @return the persisted entity.
     */
    Speciality save(Speciality speciality);

    /**
     * Get all the specialities.
     *
     * @return the list of entities.
     */
    List<Speciality> findAll();


    /**
     * Get the "id" speciality.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Speciality> findOne(Long id);

    /**
     * Delete the "id" speciality.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the speciality corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<Speciality> search(String query);
}
