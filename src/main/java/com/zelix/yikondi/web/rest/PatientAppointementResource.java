package com.zelix.yikondi.web.rest;

import com.zelix.yikondi.domain.PatientAppointement;
import com.zelix.yikondi.service.PatientAppointementService;
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
 * REST controller for managing {@link com.zelix.yikondi.domain.PatientAppointement}.
 */
@RestController
@RequestMapping("/api")
public class PatientAppointementResource {

    private final Logger log = LoggerFactory.getLogger(PatientAppointementResource.class);

    private static final String ENTITY_NAME = "patientAppointement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PatientAppointementService patientAppointementService;

    public PatientAppointementResource(PatientAppointementService patientAppointementService) {
        this.patientAppointementService = patientAppointementService;
    }

    /**
     * {@code POST  /patient-appointements} : Create a new patientAppointement.
     *
     * @param patientAppointement the patientAppointement to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new patientAppointement, or with status {@code 400 (Bad Request)} if the patientAppointement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/patient-appointements")
    public ResponseEntity<PatientAppointement> createPatientAppointement(@RequestBody PatientAppointement patientAppointement) throws URISyntaxException {
        log.debug("REST request to save PatientAppointement : {}", patientAppointement);
        if (patientAppointement.getId() != null) {
            throw new BadRequestAlertException("A new patientAppointement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PatientAppointement result = patientAppointementService.save(patientAppointement);
        return ResponseEntity.created(new URI("/api/patient-appointements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /patient-appointements} : Updates an existing patientAppointement.
     *
     * @param patientAppointement the patientAppointement to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated patientAppointement,
     * or with status {@code 400 (Bad Request)} if the patientAppointement is not valid,
     * or with status {@code 500 (Internal Server Error)} if the patientAppointement couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/patient-appointements")
    public ResponseEntity<PatientAppointement> updatePatientAppointement(@RequestBody PatientAppointement patientAppointement) throws URISyntaxException {
        log.debug("REST request to update PatientAppointement : {}", patientAppointement);
        if (patientAppointement.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PatientAppointement result = patientAppointementService.save(patientAppointement);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, patientAppointement.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /patient-appointements} : get all the patientAppointements.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of patientAppointements in body.
     */
    @GetMapping("/patient-appointements")
    public List<PatientAppointement> getAllPatientAppointements() {
        log.debug("REST request to get all PatientAppointements");
        return patientAppointementService.findAll();
    }

    /**
     * {@code GET  /patient-appointements/:id} : get the "id" patientAppointement.
     *
     * @param id the id of the patientAppointement to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the patientAppointement, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/patient-appointements/{id}")
    public ResponseEntity<PatientAppointement> getPatientAppointement(@PathVariable Long id) {
        log.debug("REST request to get PatientAppointement : {}", id);
        Optional<PatientAppointement> patientAppointement = patientAppointementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(patientAppointement);
    }

    /**
     * {@code DELETE  /patient-appointements/:id} : delete the "id" patientAppointement.
     *
     * @param id the id of the patientAppointement to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/patient-appointements/{id}")
    public ResponseEntity<Void> deletePatientAppointement(@PathVariable Long id) {
        log.debug("REST request to delete PatientAppointement : {}", id);
        patientAppointementService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/patient-appointements?query=:query} : search for the patientAppointement corresponding
     * to the query.
     *
     * @param query the query of the patientAppointement search.
     * @return the result of the search.
     */
    @GetMapping("/_search/patient-appointements")
    public List<PatientAppointement> searchPatientAppointements(@RequestParam String query) {
        log.debug("REST request to search PatientAppointements for query {}", query);
        return patientAppointementService.search(query);
    }
}
