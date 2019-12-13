package com.zelix.yikondi.service.impl;

import com.zelix.yikondi.service.DrugDosageFormService;
import com.zelix.yikondi.domain.DrugDosageForm;
import com.zelix.yikondi.repository.DrugDosageFormRepository;
import com.zelix.yikondi.repository.search.DrugDosageFormSearchRepository;
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
 * Service Implementation for managing {@link DrugDosageForm}.
 */
@Service
@Transactional
public class DrugDosageFormServiceImpl implements DrugDosageFormService {

    private final Logger log = LoggerFactory.getLogger(DrugDosageFormServiceImpl.class);

    private final DrugDosageFormRepository drugDosageFormRepository;

    private final DrugDosageFormSearchRepository drugDosageFormSearchRepository;

    public DrugDosageFormServiceImpl(DrugDosageFormRepository drugDosageFormRepository, DrugDosageFormSearchRepository drugDosageFormSearchRepository) {
        this.drugDosageFormRepository = drugDosageFormRepository;
        this.drugDosageFormSearchRepository = drugDosageFormSearchRepository;
    }

    /**
     * Save a drugDosageForm.
     *
     * @param drugDosageForm the entity to save.
     * @return the persisted entity.
     */
    @Override
    public DrugDosageForm save(DrugDosageForm drugDosageForm) {
        log.debug("Request to save DrugDosageForm : {}", drugDosageForm);
        DrugDosageForm result = drugDosageFormRepository.save(drugDosageForm);
        drugDosageFormSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the drugDosageForms.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<DrugDosageForm> findAll() {
        log.debug("Request to get all DrugDosageForms");
        return drugDosageFormRepository.findAll();
    }


    /**
     * Get one drugDosageForm by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DrugDosageForm> findOne(Long id) {
        log.debug("Request to get DrugDosageForm : {}", id);
        return drugDosageFormRepository.findById(id);
    }

    /**
     * Delete the drugDosageForm by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DrugDosageForm : {}", id);
        drugDosageFormRepository.deleteById(id);
        drugDosageFormSearchRepository.deleteById(id);
    }

    /**
     * Search for the drugDosageForm corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<DrugDosageForm> search(String query) {
        log.debug("Request to search DrugDosageForms for query {}", query);
        return StreamSupport
            .stream(drugDosageFormSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
