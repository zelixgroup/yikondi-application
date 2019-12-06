package com.zelix.yikondi.service;

import com.zelix.yikondi.domain.PatientFavoriteDoctor;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link PatientFavoriteDoctor}.
 */
public interface PatientFavoriteDoctorService {

    /**
     * Save a patientFavoriteDoctor.
     *
     * @param patientFavoriteDoctor the entity to save.
     * @return the persisted entity.
     */
    PatientFavoriteDoctor save(PatientFavoriteDoctor patientFavoriteDoctor);

    /**
     * Get all the patientFavoriteDoctors.
     *
     * @return the list of entities.
     */
    List<PatientFavoriteDoctor> findAll();


    /**
     * Get the "id" patientFavoriteDoctor.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PatientFavoriteDoctor> findOne(Long id);

    /**
     * Delete the "id" patientFavoriteDoctor.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the patientFavoriteDoctor corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<PatientFavoriteDoctor> search(String query);
}
