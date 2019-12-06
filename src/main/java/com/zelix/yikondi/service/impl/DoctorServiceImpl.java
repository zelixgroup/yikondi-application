package com.zelix.yikondi.service.impl;

import com.zelix.yikondi.service.DoctorService;
import com.zelix.yikondi.domain.Doctor;
import com.zelix.yikondi.repository.DoctorRepository;
import com.zelix.yikondi.repository.search.DoctorSearchRepository;
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
 * Service Implementation for managing {@link Doctor}.
 */
@Service
@Transactional
public class DoctorServiceImpl implements DoctorService {

    private final Logger log = LoggerFactory.getLogger(DoctorServiceImpl.class);

    private final DoctorRepository doctorRepository;

    private final DoctorSearchRepository doctorSearchRepository;

    public DoctorServiceImpl(DoctorRepository doctorRepository, DoctorSearchRepository doctorSearchRepository) {
        this.doctorRepository = doctorRepository;
        this.doctorSearchRepository = doctorSearchRepository;
    }

    /**
     * Save a doctor.
     *
     * @param doctor the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Doctor save(Doctor doctor) {
        log.debug("Request to save Doctor : {}", doctor);
        Doctor result = doctorRepository.save(doctor);
        doctorSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the doctors.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Doctor> findAll() {
        log.debug("Request to get all Doctors");
        return doctorRepository.findAll();
    }


    /**
     * Get one doctor by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Doctor> findOne(Long id) {
        log.debug("Request to get Doctor : {}", id);
        return doctorRepository.findById(id);
    }

    /**
     * Delete the doctor by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Doctor : {}", id);
        doctorRepository.deleteById(id);
        doctorSearchRepository.deleteById(id);
    }

    /**
     * Search for the doctor corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Doctor> search(String query) {
        log.debug("Request to search Doctors for query {}", query);
        return StreamSupport
            .stream(doctorSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
