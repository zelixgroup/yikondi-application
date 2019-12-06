package com.zelix.yikondi.service;

import com.zelix.yikondi.domain.Pharmacy;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Pharmacy}.
 */
public interface PharmacyService {

    /**
     * Save a pharmacy.
     *
     * @param pharmacy the entity to save.
     * @return the persisted entity.
     */
    Pharmacy save(Pharmacy pharmacy);

    /**
     * Get all the pharmacies.
     *
     * @return the list of entities.
     */
    List<Pharmacy> findAll();


    /**
     * Get the "id" pharmacy.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Pharmacy> findOne(Long id);

    /**
     * Delete the "id" pharmacy.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the pharmacy corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<Pharmacy> search(String query);
}
