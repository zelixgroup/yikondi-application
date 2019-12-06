package com.zelix.yikondi.web.rest;

import com.zelix.yikondi.domain.DoctorWorkingSlot;
import com.zelix.yikondi.service.DoctorWorkingSlotService;
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
 * REST controller for managing {@link com.zelix.yikondi.domain.DoctorWorkingSlot}.
 */
@RestController
@RequestMapping("/api")
public class DoctorWorkingSlotResource {

    private final Logger log = LoggerFactory.getLogger(DoctorWorkingSlotResource.class);

    private static final String ENTITY_NAME = "doctorWorkingSlot";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DoctorWorkingSlotService doctorWorkingSlotService;

    public DoctorWorkingSlotResource(DoctorWorkingSlotService doctorWorkingSlotService) {
        this.doctorWorkingSlotService = doctorWorkingSlotService;
    }

    /**
     * {@code POST  /doctor-working-slots} : Create a new doctorWorkingSlot.
     *
     * @param doctorWorkingSlot the doctorWorkingSlot to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new doctorWorkingSlot, or with status {@code 400 (Bad Request)} if the doctorWorkingSlot has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/doctor-working-slots")
    public ResponseEntity<DoctorWorkingSlot> createDoctorWorkingSlot(@RequestBody DoctorWorkingSlot doctorWorkingSlot) throws URISyntaxException {
        log.debug("REST request to save DoctorWorkingSlot : {}", doctorWorkingSlot);
        if (doctorWorkingSlot.getId() != null) {
            throw new BadRequestAlertException("A new doctorWorkingSlot cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DoctorWorkingSlot result = doctorWorkingSlotService.save(doctorWorkingSlot);
        return ResponseEntity.created(new URI("/api/doctor-working-slots/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /doctor-working-slots} : Updates an existing doctorWorkingSlot.
     *
     * @param doctorWorkingSlot the doctorWorkingSlot to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated doctorWorkingSlot,
     * or with status {@code 400 (Bad Request)} if the doctorWorkingSlot is not valid,
     * or with status {@code 500 (Internal Server Error)} if the doctorWorkingSlot couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/doctor-working-slots")
    public ResponseEntity<DoctorWorkingSlot> updateDoctorWorkingSlot(@RequestBody DoctorWorkingSlot doctorWorkingSlot) throws URISyntaxException {
        log.debug("REST request to update DoctorWorkingSlot : {}", doctorWorkingSlot);
        if (doctorWorkingSlot.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DoctorWorkingSlot result = doctorWorkingSlotService.save(doctorWorkingSlot);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, doctorWorkingSlot.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /doctor-working-slots} : get all the doctorWorkingSlots.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of doctorWorkingSlots in body.
     */
    @GetMapping("/doctor-working-slots")
    public List<DoctorWorkingSlot> getAllDoctorWorkingSlots() {
        log.debug("REST request to get all DoctorWorkingSlots");
        return doctorWorkingSlotService.findAll();
    }

    /**
     * {@code GET  /doctor-working-slots/:id} : get the "id" doctorWorkingSlot.
     *
     * @param id the id of the doctorWorkingSlot to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the doctorWorkingSlot, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/doctor-working-slots/{id}")
    public ResponseEntity<DoctorWorkingSlot> getDoctorWorkingSlot(@PathVariable Long id) {
        log.debug("REST request to get DoctorWorkingSlot : {}", id);
        Optional<DoctorWorkingSlot> doctorWorkingSlot = doctorWorkingSlotService.findOne(id);
        return ResponseUtil.wrapOrNotFound(doctorWorkingSlot);
    }

    /**
     * {@code DELETE  /doctor-working-slots/:id} : delete the "id" doctorWorkingSlot.
     *
     * @param id the id of the doctorWorkingSlot to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/doctor-working-slots/{id}")
    public ResponseEntity<Void> deleteDoctorWorkingSlot(@PathVariable Long id) {
        log.debug("REST request to delete DoctorWorkingSlot : {}", id);
        doctorWorkingSlotService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/doctor-working-slots?query=:query} : search for the doctorWorkingSlot corresponding
     * to the query.
     *
     * @param query the query of the doctorWorkingSlot search.
     * @return the result of the search.
     */
    @GetMapping("/_search/doctor-working-slots")
    public List<DoctorWorkingSlot> searchDoctorWorkingSlots(@RequestParam String query) {
        log.debug("REST request to search DoctorWorkingSlots for query {}", query);
        return doctorWorkingSlotService.search(query);
    }
}
