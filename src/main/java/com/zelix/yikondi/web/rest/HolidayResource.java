package com.zelix.yikondi.web.rest;

import com.zelix.yikondi.domain.Holiday;
import com.zelix.yikondi.service.HolidayService;
import com.zelix.yikondi.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.zelix.yikondi.domain.Holiday}.
 */
@RestController
@RequestMapping("/api")
public class HolidayResource {

    private final Logger log = LoggerFactory.getLogger(HolidayResource.class);

    private static final String ENTITY_NAME = "holiday";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HolidayService holidayService;

    public HolidayResource(HolidayService holidayService) {
        this.holidayService = holidayService;
    }

    /**
     * {@code POST  /holidays} : Create a new holiday.
     *
     * @param holiday the holiday to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new holiday, or with status {@code 400 (Bad Request)} if the holiday has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/holidays")
    public ResponseEntity<Holiday> createHoliday(@RequestBody Holiday holiday) throws URISyntaxException {
        log.debug("REST request to save Holiday : {}", holiday);
        if (holiday.getId() != null) {
            throw new BadRequestAlertException("A new holiday cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Holiday result = holidayService.save(holiday);
        return ResponseEntity.created(new URI("/api/holidays/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /holidays} : Updates an existing holiday.
     *
     * @param holiday the holiday to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated holiday,
     * or with status {@code 400 (Bad Request)} if the holiday is not valid,
     * or with status {@code 500 (Internal Server Error)} if the holiday couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/holidays")
    public ResponseEntity<Holiday> updateHoliday(@RequestBody Holiday holiday) throws URISyntaxException {
        log.debug("REST request to update Holiday : {}", holiday);
        if (holiday.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Holiday result = holidayService.save(holiday);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, holiday.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /holidays} : get all the holidays.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of holidays in body.
     */
    @GetMapping("/holidays")
    public List<Holiday> getAllHolidays() {
        log.debug("REST request to get all Holidays");
        return holidayService.findAll();
    }

    /**
     * {@code GET  /holidays/:id} : get the "id" holiday.
     *
     * @param id the id of the holiday to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the holiday, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/holidays/{id}")
    public ResponseEntity<Holiday> getHoliday(@PathVariable Long id) {
        log.debug("REST request to get Holiday : {}", id);
        Optional<Holiday> holiday = holidayService.findOne(id);
        return ResponseUtil.wrapOrNotFound(holiday);
    }

    /**
     * {@code DELETE  /holidays/:id} : delete the "id" holiday.
     *
     * @param id the id of the holiday to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/holidays/{id}")
    public ResponseEntity<Void> deleteHoliday(@PathVariable Long id) {
        log.debug("REST request to delete Holiday : {}", id);
        holidayService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/holidays?query=:query} : search for the holiday corresponding
     * to the query.
     *
     * @param query the query of the holiday search.
     * @return the result of the search.
     */
    @GetMapping("/_search/holidays")
    public List<Holiday> searchHolidays(@RequestParam String query) {
        log.debug("REST request to search Holidays for query {}", query);
        return holidayService.search(query);
    }
}
