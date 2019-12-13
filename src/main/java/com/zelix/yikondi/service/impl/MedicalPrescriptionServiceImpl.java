package com.zelix.yikondi.service.impl;

import com.zelix.yikondi.service.MedicalPrescriptionService;
import com.zelix.yikondi.domain.MedicalPrescription;
import com.zelix.yikondi.repository.MedicalPrescriptionRepository;
import com.zelix.yikondi.repository.search.MedicalPrescriptionSearchRepository;
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
 * Service Implementation for managing {@link MedicalPrescription}.
 */
@Service
@Transactional
public class MedicalPrescriptionServiceImpl implements MedicalPrescriptionService {

    private final Logger log = LoggerFactory.getLogger(MedicalPrescriptionServiceImpl.class);

    private final MedicalPrescriptionRepository medicalPrescriptionRepository;

    private final MedicalPrescriptionSearchRepository medicalPrescriptionSearchRepository;

    public MedicalPrescriptionServiceImpl(MedicalPrescriptionRepository medicalPrescriptionRepository, MedicalPrescriptionSearchRepository medicalPrescriptionSearchRepository) {
        this.medicalPrescriptionRepository = medicalPrescriptionRepository;
        this.medicalPrescriptionSearchRepository = medicalPrescriptionSearchRepository;
    }

    /**
     * Save a medicalPrescription.
     *
     * @param medicalPrescription the entity to save.
     * @return the persisted entity.
     */
    @Override
    public MedicalPrescription save(MedicalPrescription medicalPrescription) {
        log.debug("Request to save MedicalPrescription : {}", medicalPrescription);
        MedicalPrescription result = medicalPrescriptionRepository.save(medicalPrescription);
        medicalPrescriptionSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the medicalPrescriptions.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<MedicalPrescription> findAll() {
        log.debug("Request to get all MedicalPrescriptions");
        return medicalPrescriptionRepository.findAll();
    }


    /**
     * Get one medicalPrescription by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MedicalPrescription> findOne(Long id) {
        log.debug("Request to get MedicalPrescription : {}", id);
        return medicalPrescriptionRepository.findById(id);
    }

    /**
     * Delete the medicalPrescription by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MedicalPrescription : {}", id);
        medicalPrescriptionRepository.deleteById(id);
        medicalPrescriptionSearchRepository.deleteById(id);
    }

    /**
     * Search for the medicalPrescription corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<MedicalPrescription> search(String query) {
        log.debug("Request to search MedicalPrescriptions for query {}", query);
        return StreamSupport
            .stream(medicalPrescriptionSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
