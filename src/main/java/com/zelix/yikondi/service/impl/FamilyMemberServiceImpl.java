package com.zelix.yikondi.service.impl;

import com.zelix.yikondi.service.FamilyMemberService;
import com.zelix.yikondi.domain.FamilyMember;
import com.zelix.yikondi.repository.FamilyMemberRepository;
import com.zelix.yikondi.repository.search.FamilyMemberSearchRepository;
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
 * Service Implementation for managing {@link FamilyMember}.
 */
@Service
@Transactional
public class FamilyMemberServiceImpl implements FamilyMemberService {

    private final Logger log = LoggerFactory.getLogger(FamilyMemberServiceImpl.class);

    private final FamilyMemberRepository familyMemberRepository;

    private final FamilyMemberSearchRepository familyMemberSearchRepository;

    public FamilyMemberServiceImpl(FamilyMemberRepository familyMemberRepository, FamilyMemberSearchRepository familyMemberSearchRepository) {
        this.familyMemberRepository = familyMemberRepository;
        this.familyMemberSearchRepository = familyMemberSearchRepository;
    }

    /**
     * Save a familyMember.
     *
     * @param familyMember the entity to save.
     * @return the persisted entity.
     */
    @Override
    public FamilyMember save(FamilyMember familyMember) {
        log.debug("Request to save FamilyMember : {}", familyMember);
        FamilyMember result = familyMemberRepository.save(familyMember);
        familyMemberSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the familyMembers.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<FamilyMember> findAll() {
        log.debug("Request to get all FamilyMembers");
        return familyMemberRepository.findAll();
    }


    /**
     * Get one familyMember by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<FamilyMember> findOne(Long id) {
        log.debug("Request to get FamilyMember : {}", id);
        return familyMemberRepository.findById(id);
    }

    /**
     * Delete the familyMember by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete FamilyMember : {}", id);
        familyMemberRepository.deleteById(id);
        familyMemberSearchRepository.deleteById(id);
    }

    /**
     * Search for the familyMember corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<FamilyMember> search(String query) {
        log.debug("Request to search FamilyMembers for query {}", query);
        return StreamSupport
            .stream(familyMemberSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
