package com.zelix.yikondi.service.impl;

import com.zelix.yikondi.service.PathologyService;
import com.zelix.yikondi.domain.Pathology;
import com.zelix.yikondi.repository.PathologyRepository;
import com.zelix.yikondi.repository.search.PathologySearchRepository;
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
 * Service Implementation for managing {@link Pathology}.
 */
@Service
@Transactional
public class PathologyServiceImpl implements PathologyService {

    private final Logger log = LoggerFactory.getLogger(PathologyServiceImpl.class);

    private final PathologyRepository pathologyRepository;

    private final PathologySearchRepository pathologySearchRepository;

    public PathologyServiceImpl(PathologyRepository pathologyRepository, PathologySearchRepository pathologySearchRepository) {
        this.pathologyRepository = pathologyRepository;
        this.pathologySearchRepository = pathologySearchRepository;
    }

    /**
     * Save a pathology.
     *
     * @param pathology the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Pathology save(Pathology pathology) {
        log.debug("Request to save Pathology : {}", pathology);
        Pathology result = pathologyRepository.save(pathology);
        pathologySearchRepository.save(result);
        return result;
    }

    /**
     * Get all the pathologies.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Pathology> findAll() {
        log.debug("Request to get all Pathologies");
        return pathologyRepository.findAll();
    }


    /**
     * Get one pathology by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Pathology> findOne(Long id) {
        log.debug("Request to get Pathology : {}", id);
        return pathologyRepository.findById(id);
    }

    /**
     * Delete the pathology by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Pathology : {}", id);
        pathologyRepository.deleteById(id);
        pathologySearchRepository.deleteById(id);
    }

    /**
     * Search for the pathology corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Pathology> search(String query) {
        log.debug("Request to search Pathologies for query {}", query);
        return StreamSupport
            .stream(pathologySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
