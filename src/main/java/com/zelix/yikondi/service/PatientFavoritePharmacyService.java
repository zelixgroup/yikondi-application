package com.zelix.yikondi.service;

import com.zelix.yikondi.domain.PatientFavoritePharmacy;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link PatientFavoritePharmacy}.
 */
public interface PatientFavoritePharmacyService {

    /**
     * Save a patientFavoritePharmacy.
     *
     * @param patientFavoritePharmacy the entity to save.
     * @return the persisted entity.
     */
    PatientFavoritePharmacy save(PatientFavoritePharmacy patientFavoritePharmacy);

    /**
     * Get all the patientFavoritePharmacies.
     *
     * @return the list of entities.
     */
    List<PatientFavoritePharmacy> findAll();


    /**
     * Get the "id" patientFavoritePharmacy.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PatientFavoritePharmacy> findOne(Long id);

    /**
     * Delete the "id" patientFavoritePharmacy.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the patientFavoritePharmacy corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<PatientFavoritePharmacy> search(String query);
}
