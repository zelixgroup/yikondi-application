package com.zelix.yikondi.service.impl;

import com.zelix.yikondi.service.MedicalPrescriptionAnalysisService;
import com.zelix.yikondi.domain.MedicalPrescriptionAnalysis;
import com.zelix.yikondi.repository.MedicalPrescriptionAnalysisRepository;
import com.zelix.yikondi.repository.search.MedicalPrescriptionAnalysisSearchRepository;
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
 * Service Implementation for managing {@link MedicalPrescriptionAnalysis}.
 */
@Service
@Transactional
public class MedicalPrescriptionAnalysisServiceImpl implements MedicalPrescriptionAnalysisService {

    private final Logger log = LoggerFactory.getLogger(MedicalPrescriptionAnalysisServiceImpl.class);

    private final MedicalPrescriptionAnalysisRepository medicalPrescriptionAnalysisRepository;

    private final MedicalPrescriptionAnalysisSearchRepository medicalPrescriptionAnalysisSearchRepository;

    public MedicalPrescriptionAnalysisServiceImpl(MedicalPrescriptionAnalysisRepository medicalPrescriptionAnalysisRepository, MedicalPrescriptionAnalysisSearchRepository medicalPrescriptionAnalysisSearchRepository) {
        this.medicalPrescriptionAnalysisRepository = medicalPrescriptionAnalysisRepository;
        this.medicalPrescriptionAnalysisSearchRepository = medicalPrescriptionAnalysisSearchRepository;
    }

    /**
     * Save a medicalPrescriptionAnalysis.
     *
     * @param medicalPrescriptionAnalysis the entity to save.
     * @return the persisted entity.
     */
    @Override
    public MedicalPrescriptionAnalysis save(MedicalPrescriptionAnalysis medicalPrescriptionAnalysis) {
        log.debug("Request to save MedicalPrescriptionAnalysis : {}", medicalPrescriptionAnalysis);
        MedicalPrescriptionAnalysis result = medicalPrescriptionAnalysisRepository.save(medicalPrescriptionAnalysis);
        medicalPrescriptionAnalysisSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the medicalPrescriptionAnalyses.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<MedicalPrescriptionAnalysis> findAll() {
        log.debug("Request to get all MedicalPrescriptionAnalyses");
        return medicalPrescriptionAnalysisRepository.findAll();
    }


    /**
     * Get one medicalPrescriptionAnalysis by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MedicalPrescriptionAnalysis> findOne(Long id) {
        log.debug("Request to get MedicalPrescriptionAnalysis : {}", id);
        return medicalPrescriptionAnalysisRepository.findById(id);
    }

    /**
     * Delete the medicalPrescriptionAnalysis by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MedicalPrescriptionAnalysis : {}", id);
        medicalPrescriptionAnalysisRepository.deleteById(id);
        medicalPrescriptionAnalysisSearchRepository.deleteById(id);
    }

    /**
     * Search for the medicalPrescriptionAnalysis corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<MedicalPrescriptionAnalysis> search(String query) {
        log.debug("Request to search MedicalPrescriptionAnalyses for query {}", query);
        return StreamSupport
            .stream(medicalPrescriptionAnalysisSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
