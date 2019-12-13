package com.zelix.yikondi.service.impl;

import com.zelix.yikondi.service.AnalysisService;
import com.zelix.yikondi.domain.Analysis;
import com.zelix.yikondi.repository.AnalysisRepository;
import com.zelix.yikondi.repository.search.AnalysisSearchRepository;
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
 * Service Implementation for managing {@link Analysis}.
 */
@Service
@Transactional
public class AnalysisServiceImpl implements AnalysisService {

    private final Logger log = LoggerFactory.getLogger(AnalysisServiceImpl.class);

    private final AnalysisRepository analysisRepository;

    private final AnalysisSearchRepository analysisSearchRepository;

    public AnalysisServiceImpl(AnalysisRepository analysisRepository, AnalysisSearchRepository analysisSearchRepository) {
        this.analysisRepository = analysisRepository;
        this.analysisSearchRepository = analysisSearchRepository;
    }

    /**
     * Save a analysis.
     *
     * @param analysis the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Analysis save(Analysis analysis) {
        log.debug("Request to save Analysis : {}", analysis);
        Analysis result = analysisRepository.save(analysis);
        analysisSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the analyses.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Analysis> findAll() {
        log.debug("Request to get all Analyses");
        return analysisRepository.findAll();
    }


    /**
     * Get one analysis by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Analysis> findOne(Long id) {
        log.debug("Request to get Analysis : {}", id);
        return analysisRepository.findById(id);
    }

    /**
     * Delete the analysis by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Analysis : {}", id);
        analysisRepository.deleteById(id);
        analysisSearchRepository.deleteById(id);
    }

    /**
     * Search for the analysis corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Analysis> search(String query) {
        log.debug("Request to search Analyses for query {}", query);
        return StreamSupport
            .stream(analysisSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
