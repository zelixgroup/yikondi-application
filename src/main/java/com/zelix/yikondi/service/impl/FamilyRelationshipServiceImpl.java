package com.zelix.yikondi.service.impl;

import com.zelix.yikondi.service.FamilyRelationshipService;
import com.zelix.yikondi.domain.FamilyRelationship;
import com.zelix.yikondi.repository.FamilyRelationshipRepository;
import com.zelix.yikondi.repository.search.FamilyRelationshipSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link FamilyRelationship}.
 */
@Service
@Transactional
public class FamilyRelationshipServiceImpl implements FamilyRelationshipService {

    private final Logger log = LoggerFactory.getLogger(FamilyRelationshipServiceImpl.class);

    private final FamilyRelationshipRepository familyRelationshipRepository;

    private final FamilyRelationshipSearchRepository familyRelationshipSearchRepository;

    public FamilyRelationshipServiceImpl(FamilyRelationshipRepository familyRelationshipRepository, FamilyRelationshipSearchRepository familyRelationshipSearchRepository) {
        this.familyRelationshipRepository = familyRelationshipRepository;
        this.familyRelationshipSearchRepository = familyRelationshipSearchRepository;
    }

    /**
     * Save a familyRelationship.
     *
     * @param familyRelationship the entity to save.
     * @return the persisted entity.
     */
    @Override
    public FamilyRelationship save(FamilyRelationship familyRelationship) {
        log.debug("Request to save FamilyRelationship : {}", familyRelationship);
        FamilyRelationship result = familyRelationshipRepository.save(familyRelationship);
        familyRelationshipSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the familyRelationships.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<FamilyRelationship> findAll() {
        log.debug("Request to get all FamilyRelationships");
        return familyRelationshipRepository.findAll();
    }


    /**
     * Get one familyRelationship by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<FamilyRelationship> findOne(Long id) {
        log.debug("Request to get FamilyRelationship : {}", id);
        return familyRelationshipRepository.findById(id);
    }

    /**
     * Delete the familyRelationship by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete FamilyRelationship : {}", id);
        familyRelationshipRepository.deleteById(id);
        familyRelationshipSearchRepository.deleteById(id);
    }

    /**
     * Search for the familyRelationship corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<FamilyRelationship> search(String query) {
        log.debug("Request to search FamilyRelationships for query {}", query);
        return StreamSupport
            .stream(familyRelationshipSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
