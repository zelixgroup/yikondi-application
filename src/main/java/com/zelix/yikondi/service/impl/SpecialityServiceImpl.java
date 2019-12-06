package com.zelix.yikondi.service.impl;

import com.zelix.yikondi.service.SpecialityService;
import com.zelix.yikondi.domain.Speciality;
import com.zelix.yikondi.repository.SpecialityRepository;
import com.zelix.yikondi.repository.search.SpecialitySearchRepository;
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
 * Service Implementation for managing {@link Speciality}.
 */
@Service
@Transactional
public class SpecialityServiceImpl implements SpecialityService {

    private final Logger log = LoggerFactory.getLogger(SpecialityServiceImpl.class);

    private final SpecialityRepository specialityRepository;

    private final SpecialitySearchRepository specialitySearchRepository;

    public SpecialityServiceImpl(SpecialityRepository specialityRepository, SpecialitySearchRepository specialitySearchRepository) {
        this.specialityRepository = specialityRepository;
        this.specialitySearchRepository = specialitySearchRepository;
    }

    /**
     * Save a speciality.
     *
     * @param speciality the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Speciality save(Speciality speciality) {
        log.debug("Request to save Speciality : {}", speciality);
        Speciality result = specialityRepository.save(speciality);
        specialitySearchRepository.save(result);
        return result;
    }

    /**
     * Get all the specialities.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Speciality> findAll() {
        log.debug("Request to get all Specialities");
        return specialityRepository.findAll();
    }


    /**
     * Get one speciality by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Speciality> findOne(Long id) {
        log.debug("Request to get Speciality : {}", id);
        return specialityRepository.findById(id);
    }

    /**
     * Delete the speciality by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Speciality : {}", id);
        specialityRepository.deleteById(id);
        specialitySearchRepository.deleteById(id);
    }

    /**
     * Search for the speciality corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Speciality> search(String query) {
        log.debug("Request to search Specialities for query {}", query);
        return StreamSupport
            .stream(specialitySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
