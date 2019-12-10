package com.zelix.yikondi.web.rest;

import com.zelix.yikondi.domain.PatientInsuranceCoverage;
import com.zelix.yikondi.service.PatientInsuranceCoverageService;
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
 * REST controller for managing {@link com.zelix.yikondi.domain.PatientInsuranceCoverage}.
 */
@RestController
@RequestMapping("/api")
public class PatientInsuranceCoverageResource {

    private final Logger log = LoggerFactory.getLogger(PatientInsuranceCoverageResource.class);

    private static final String ENTITY_NAME = "patientInsuranceCoverage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PatientInsuranceCoverageService patientInsuranceCoverageService;

    public PatientInsuranceCoverageResource(PatientInsuranceCoverageService patientInsuranceCoverageService) {
        this.patientInsuranceCoverageService = patientInsuranceCoverageService;
    }

    /**
     * {@code POST  /patient-insurance-coverages} : Create a new patientInsuranceCoverage.
     *
     * @param patientInsuranceCoverage the patientInsuranceCoverage to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new patientInsuranceCoverage, or with status {@code 400 (Bad Request)} if the patientInsuranceCoverage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/patient-insurance-coverages")
    public ResponseEntity<PatientInsuranceCoverage> createPatientInsuranceCoverage(@RequestBody PatientInsuranceCoverage patientInsuranceCoverage) throws URISyntaxException {
        log.debug("REST request to save PatientInsuranceCoverage : {}", patientInsuranceCoverage);
        if (patientInsuranceCoverage.getId() != null) {
            throw new BadRequestAlertException("A new patientInsuranceCoverage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PatientInsuranceCoverage result = patientInsuranceCoverageService.save(patientInsuranceCoverage);
        return ResponseEntity.created(new URI("/api/patient-insurance-coverages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /patient-insurance-coverages} : Updates an existing patientInsuranceCoverage.
     *
     * @param patientInsuranceCoverage the patientInsuranceCoverage to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated patientInsuranceCoverage,
     * or with status {@code 400 (Bad Request)} if the patientInsuranceCoverage is not valid,
     * or with status {@code 500 (Internal Server Error)} if the patientInsuranceCoverage couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/patient-insurance-coverages")
    public ResponseEntity<PatientInsuranceCoverage> updatePatientInsuranceCoverage(@RequestBody PatientInsuranceCoverage patientInsuranceCoverage) throws URISyntaxException {
        log.debug("REST request to update PatientInsuranceCoverage : {}", patientInsuranceCoverage);
        if (patientInsuranceCoverage.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PatientInsuranceCoverage result = patientInsuranceCoverageService.save(patientInsuranceCoverage);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, patientInsuranceCoverage.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /patient-insurance-coverages} : get all the patientInsuranceCoverages.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of patientInsuranceCoverages in body.
     */
    @GetMapping("/patient-insurance-coverages")
    public List<PatientInsuranceCoverage> getAllPatientInsuranceCoverages() {
        log.debug("REST request to get all PatientInsuranceCoverages");
        return patientInsuranceCoverageService.findAll();
    }

    /**
     * {@code GET  /patient-insurance-coverages/:id} : get the "id" patientInsuranceCoverage.
     *
     * @param id the id of the patientInsuranceCoverage to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the patientInsuranceCoverage, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/patient-insurance-coverages/{id}")
    public ResponseEntity<PatientInsuranceCoverage> getPatientInsuranceCoverage(@PathVariable Long id) {
        log.debug("REST request to get PatientInsuranceCoverage : {}", id);
        Optional<PatientInsuranceCoverage> patientInsuranceCoverage = patientInsuranceCoverageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(patientInsuranceCoverage);
    }

    /**
     * {@code DELETE  /patient-insurance-coverages/:id} : delete the "id" patientInsuranceCoverage.
     *
     * @param id the id of the patientInsuranceCoverage to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/patient-insurance-coverages/{id}")
    public ResponseEntity<Void> deletePatientInsuranceCoverage(@PathVariable Long id) {
        log.debug("REST request to delete PatientInsuranceCoverage : {}", id);
        patientInsuranceCoverageService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/patient-insurance-coverages?query=:query} : search for the patientInsuranceCoverage corresponding
     * to the query.
     *
     * @param query the query of the patientInsuranceCoverage search.
     * @return the result of the search.
     */
    @GetMapping("/_search/patient-insurance-coverages")
    public List<PatientInsuranceCoverage> searchPatientInsuranceCoverages(@RequestParam String query) {
        log.debug("REST request to search PatientInsuranceCoverages for query {}", query);
        return patientInsuranceCoverageService.search(query);
    }
}
