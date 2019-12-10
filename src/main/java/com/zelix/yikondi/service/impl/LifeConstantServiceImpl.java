package com.zelix.yikondi.service.impl;

import com.zelix.yikondi.service.LifeConstantService;
import com.zelix.yikondi.domain.LifeConstant;
import com.zelix.yikondi.repository.LifeConstantRepository;
import com.zelix.yikondi.repository.search.LifeConstantSearchRepository;
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
 * Service Implementation for managing {@link LifeConstant}.
 */
@Service
@Transactional
public class LifeConstantServiceImpl implements LifeConstantService {

    private final Logger log = LoggerFactory.getLogger(LifeConstantServiceImpl.class);

    private final LifeConstantRepository lifeConstantRepository;

    private final LifeConstantSearchRepository lifeConstantSearchRepository;

    public LifeConstantServiceImpl(LifeConstantRepository lifeConstantRepository, LifeConstantSearchRepository lifeConstantSearchRepository) {
        this.lifeConstantRepository = lifeConstantRepository;
        this.lifeConstantSearchRepository = lifeConstantSearchRepository;
    }

    /**
     * Save a lifeConstant.
     *
     * @param lifeConstant the entity to save.
     * @return the persisted entity.
     */
    @Override
    public LifeConstant save(LifeConstant lifeConstant) {
        log.debug("Request to save LifeConstant : {}", lifeConstant);
        LifeConstant result = lifeConstantRepository.save(lifeConstant);
        lifeConstantSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the lifeConstants.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<LifeConstant> findAll() {
        log.debug("Request to get all LifeConstants");
        return lifeConstantRepository.findAll();
    }


    /**
     * Get one lifeConstant by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<LifeConstant> findOne(Long id) {
        log.debug("Request to get LifeConstant : {}", id);
        return lifeConstantRepository.findById(id);
    }

    /**
     * Delete the lifeConstant by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete LifeConstant : {}", id);
        lifeConstantRepository.deleteById(id);
        lifeConstantSearchRepository.deleteById(id);
    }

    /**
     * Search for the lifeConstant corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<LifeConstant> search(String query) {
        log.debug("Request to search LifeConstants for query {}", query);
        return StreamSupport
            .stream(lifeConstantSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
