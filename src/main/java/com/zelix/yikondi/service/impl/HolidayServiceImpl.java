package com.zelix.yikondi.service.impl;

import com.zelix.yikondi.service.HolidayService;
import com.zelix.yikondi.domain.Holiday;
import com.zelix.yikondi.repository.HolidayRepository;
import com.zelix.yikondi.repository.search.HolidaySearchRepository;
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
 * Service Implementation for managing {@link Holiday}.
 */
@Service
@Transactional
public class HolidayServiceImpl implements HolidayService {

    private final Logger log = LoggerFactory.getLogger(HolidayServiceImpl.class);

    private final HolidayRepository holidayRepository;

    private final HolidaySearchRepository holidaySearchRepository;

    public HolidayServiceImpl(HolidayRepository holidayRepository, HolidaySearchRepository holidaySearchRepository) {
        this.holidayRepository = holidayRepository;
        this.holidaySearchRepository = holidaySearchRepository;
    }

    /**
     * Save a holiday.
     *
     * @param holiday the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Holiday save(Holiday holiday) {
        log.debug("Request to save Holiday : {}", holiday);
        Holiday result = holidayRepository.save(holiday);
        holidaySearchRepository.save(result);
        return result;
    }

    /**
     * Get all the holidays.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Holiday> findAll() {
        log.debug("Request to get all Holidays");
        return holidayRepository.findAll();
    }


    /**
     * Get one holiday by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Holiday> findOne(Long id) {
        log.debug("Request to get Holiday : {}", id);
        return holidayRepository.findById(id);
    }

    /**
     * Delete the holiday by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Holiday : {}", id);
        holidayRepository.deleteById(id);
        holidaySearchRepository.deleteById(id);
    }

    /**
     * Search for the holiday corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Holiday> search(String query) {
        log.debug("Request to search Holidays for query {}", query);
        return StreamSupport
            .stream(holidaySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
