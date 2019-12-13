package com.zelix.yikondi.service.impl;

import com.zelix.yikondi.service.LifeConstantUnitService;
import com.zelix.yikondi.domain.LifeConstantUnit;
import com.zelix.yikondi.repository.LifeConstantUnitRepository;
import com.zelix.yikondi.repository.search.LifeConstantUnitSearchRepository;
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
 * Service Implementation for managing {@link LifeConstantUnit}.
 */
@Service
@Transactional
public class LifeConstantUnitServiceImpl implements LifeConstantUnitService {

    private final Logger log = LoggerFactory.getLogger(LifeConstantUnitServiceImpl.class);

    private final LifeConstantUnitRepository lifeConstantUnitRepository;

    private final LifeConstantUnitSearchRepository lifeConstantUnitSearchRepository;

    public LifeConstantUnitServiceImpl(LifeConstantUnitRepository lifeConstantUnitRepository, LifeConstantUnitSearchRepository lifeConstantUnitSearchRepository) {
        this.lifeConstantUnitRepository = lifeConstantUnitRepository;
        this.lifeConstantUnitSearchRepository = lifeConstantUnitSearchRepository;
    }

    /**
     * Save a lifeConstantUnit.
     *
     * @param lifeConstantUnit the entity to save.
     * @return the persisted entity.
     */
    @Override
    public LifeConstantUnit save(LifeConstantUnit lifeConstantUnit) {
        log.debug("Request to save LifeConstantUnit : {}", lifeConstantUnit);
        LifeConstantUnit result = lifeConstantUnitRepository.save(lifeConstantUnit);
        lifeConstantUnitSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the lifeConstantUnits.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<LifeConstantUnit> findAll() {
        log.debug("Request to get all LifeConstantUnits");
        return lifeConstantUnitRepository.findAll();
    }


    /**
     * Get one lifeConstantUnit by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<LifeConstantUnit> findOne(Long id) {
        log.debug("Request to get LifeConstantUnit : {}", id);
        return lifeConstantUnitRepository.findById(id);
    }

    /**
     * Delete the lifeConstantUnit by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete LifeConstantUnit : {}", id);
        lifeConstantUnitRepository.deleteById(id);
        lifeConstantUnitSearchRepository.deleteById(id);
    }

    /**
     * Search for the lifeConstantUnit corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<LifeConstantUnit> search(String query) {
        log.debug("Request to search LifeConstantUnits for query {}", query);
        return StreamSupport
            .stream(lifeConstantUnitSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
