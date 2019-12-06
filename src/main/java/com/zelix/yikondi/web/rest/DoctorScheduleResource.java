package com.zelix.yikondi.web.rest;

import com.zelix.yikondi.domain.DoctorSchedule;
import com.zelix.yikondi.service.DoctorScheduleService;
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
 * REST controller for managing {@link com.zelix.yikondi.domain.DoctorSchedule}.
 */
@RestController
@RequestMapping("/api")
public class DoctorScheduleResource {

    private final Logger log = LoggerFactory.getLogger(DoctorScheduleResource.class);

    private static final String ENTITY_NAME = "doctorSchedule";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DoctorScheduleService doctorScheduleService;

    public DoctorScheduleResource(DoctorScheduleService doctorScheduleService) {
        this.doctorScheduleService = doctorScheduleService;
    }

    /**
     * {@code POST  /doctor-schedules} : Create a new doctorSchedule.
     *
     * @param doctorSchedule the doctorSchedule to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new doctorSchedule, or with status {@code 400 (Bad Request)} if the doctorSchedule has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/doctor-schedules")
    public ResponseEntity<DoctorSchedule> createDoctorSchedule(@RequestBody DoctorSchedule doctorSchedule) throws URISyntaxException {
        log.debug("REST request to save DoctorSchedule : {}", doctorSchedule);
        if (doctorSchedule.getId() != null) {
            throw new BadRequestAlertException("A new doctorSchedule cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DoctorSchedule result = doctorScheduleService.save(doctorSchedule);
        return ResponseEntity.created(new URI("/api/doctor-schedules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /doctor-schedules} : Updates an existing doctorSchedule.
     *
     * @param doctorSchedule the doctorSchedule to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated doctorSchedule,
     * or with status {@code 400 (Bad Request)} if the doctorSchedule is not valid,
     * or with status {@code 500 (Internal Server Error)} if the doctorSchedule couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/doctor-schedules")
    public ResponseEntity<DoctorSchedule> updateDoctorSchedule(@RequestBody DoctorSchedule doctorSchedule) throws URISyntaxException {
        log.debug("REST request to update DoctorSchedule : {}", doctorSchedule);
        if (doctorSchedule.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DoctorSchedule result = doctorScheduleService.save(doctorSchedule);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, doctorSchedule.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /doctor-schedules} : get all the doctorSchedules.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of doctorSchedules in body.
     */
    @GetMapping("/doctor-schedules")
    public List<DoctorSchedule> getAllDoctorSchedules() {
        log.debug("REST request to get all DoctorSchedules");
        return doctorScheduleService.findAll();
    }

    /**
     * {@code GET  /doctor-schedules/:id} : get the "id" doctorSchedule.
     *
     * @param id the id of the doctorSchedule to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the doctorSchedule, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/doctor-schedules/{id}")
    public ResponseEntity<DoctorSchedule> getDoctorSchedule(@PathVariable Long id) {
        log.debug("REST request to get DoctorSchedule : {}", id);
        Optional<DoctorSchedule> doctorSchedule = doctorScheduleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(doctorSchedule);
    }

    /**
     * {@code DELETE  /doctor-schedules/:id} : delete the "id" doctorSchedule.
     *
     * @param id the id of the doctorSchedule to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/doctor-schedules/{id}")
    public ResponseEntity<Void> deleteDoctorSchedule(@PathVariable Long id) {
        log.debug("REST request to delete DoctorSchedule : {}", id);
        doctorScheduleService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/doctor-schedules?query=:query} : search for the doctorSchedule corresponding
     * to the query.
     *
     * @param query the query of the doctorSchedule search.
     * @return the result of the search.
     */
    @GetMapping("/_search/doctor-schedules")
    public List<DoctorSchedule> searchDoctorSchedules(@RequestParam String query) {
        log.debug("REST request to search DoctorSchedules for query {}", query);
        return doctorScheduleService.search(query);
    }
}
