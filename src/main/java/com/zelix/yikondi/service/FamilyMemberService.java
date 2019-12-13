package com.zelix.yikondi.service;

import com.zelix.yikondi.domain.FamilyMember;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link FamilyMember}.
 */
public interface FamilyMemberService {

    /**
     * Save a familyMember.
     *
     * @param familyMember the entity to save.
     * @return the persisted entity.
     */
    FamilyMember save(FamilyMember familyMember);

    /**
     * Get all the familyMembers.
     *
     * @return the list of entities.
     */
    List<FamilyMember> findAll();


    /**
     * Get the "id" familyMember.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FamilyMember> findOne(Long id);

    /**
     * Delete the "id" familyMember.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the familyMember corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<FamilyMember> search(String query);
}
