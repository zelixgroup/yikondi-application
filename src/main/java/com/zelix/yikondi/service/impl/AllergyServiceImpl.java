package com.zelix.yikondi.service.impl;

import com.zelix.yikondi.service.AllergyService;
import com.zelix.yikondi.domain.Allergy;
import com.zelix.yikondi.repository.AllergyRepository;
import com.zelix.yikondi.repository.search.AllergySearchRepository;
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
 * Service Implementation for managing {@link Allergy}.
 */
@Service
@Transactional
public class AllergyServiceImpl implements AllergyService {

    private final Logger log = LoggerFactory.getLogger(AllergyServiceImpl.class);

    private final AllergyRepository allergyRepository;

    private final AllergySearchRepository allergySearchRepository;

    public AllergyServiceImpl(AllergyRepository allergyRepository, AllergySearchRepository allergySearchRepository) {
        this.allergyRepository = allergyRepository;
        this.allergySearchRepository = allergySearchRepository;
    }

    /**
     * Save a allergy.
     *
     * @param allergy the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Allergy save(Allergy allergy) {
        log.debug("Request to save Allergy : {}", allergy);
        Allergy result = allergyRepository.save(allergy);
        allergySearchRepository.save(result);
        return result;
    }

    /**
     * Get all the allergies.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Allergy> findAll() {
        log.debug("Request to get all Allergies");
        return allergyRepository.findAll();
    }


    /**
     * Get one allergy by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Allergy> findOne(Long id) {
        log.debug("Request to get Allergy : {}", id);
        return allergyRepository.findById(id);
    }

    /**
     * Delete the allergy by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Allergy : {}", id);
        allergyRepository.deleteById(id);
        allergySearchRepository.deleteById(id);
    }

    /**
     * Search for the allergy corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Allergy> search(String query) {
        log.debug("Request to search Allergies for query {}", query);
        return StreamSupport
            .stream(allergySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
