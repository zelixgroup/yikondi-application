package com.zelix.yikondi.web.rest;

import com.zelix.yikondi.domain.PatientLifeConstant;
import com.zelix.yikondi.service.PatientLifeConstantService;
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
 * REST controller for managing {@link com.zelix.yikondi.domain.PatientLifeConstant}.
 */
@RestController
@RequestMapping("/api")
public class PatientLifeConstantResource {

    private final Logger log = LoggerFactory.getLogger(PatientLifeConstantResource.class);

    private static final String ENTITY_NAME = "patientLifeConstant";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PatientLifeConstantService patientLifeConstantService;

    public PatientLifeConstantResource(PatientLifeConstantService patientLifeConstantService) {
        this.patientLifeConstantService = patientLifeConstantService;
    }

    /**
     * {@code POST  /patient-life-constants} : Create a new patientLifeConstant.
     *
     * @param patientLifeConstant the patientLifeConstant to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new patientLifeConstant, or with status {@code 400 (Bad Request)} if the patientLifeConstant has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/patient-life-constants")
    public ResponseEntity<PatientLifeConstant> createPatientLifeConstant(@RequestBody PatientLifeConstant patientLifeConstant) throws URISyntaxException {
        log.debug("REST request to save PatientLifeConstant : {}", patientLifeConstant);
        if (patientLifeConstant.getId() != null) {
            throw new BadRequestAlertException("A new patientLifeConstant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PatientLifeConstant result = patientLifeConstantService.save(patientLifeConstant);
        return ResponseEntity.created(new URI("/api/patient-life-constants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /patient-life-constants} : Updates an existing patientLifeConstant.
     *
     * @param patientLifeConstant the patientLifeConstant to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated patientLifeConstant,
     * or with status {@code 400 (Bad Request)} if the patientLifeConstant is not valid,
     * or with status {@code 500 (Internal Server Error)} if the patientLifeConstant couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/patient-life-constants")
    public ResponseEntity<PatientLifeConstant> updatePatientLifeConstant(@RequestBody PatientLifeConstant patientLifeConstant) throws URISyntaxException {
        log.debug("REST request to update PatientLifeConstant : {}", patientLifeConstant);
        if (patientLifeConstant.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PatientLifeConstant result = patientLifeConstantService.save(patientLifeConstant);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, patientLifeConstant.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /patient-life-constants} : get all the patientLifeConstants.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of patientLifeConstants in body.
     */
    @GetMapping("/patient-life-constants")
    public List<PatientLifeConstant> getAllPatientLifeConstants() {
        log.debug("REST request to get all PatientLifeConstants");
        return patientLifeConstantService.findAll();
    }

    /**
     * {@code GET  /patient-life-constants/:id} : get the "id" patientLifeConstant.
     *
     * @param id the id of the patientLifeConstant to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the patientLifeConstant, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/patient-life-constants/{id}")
    public ResponseEntity<PatientLifeConstant> getPatientLifeConstant(@PathVariable Long id) {
        log.debug("REST request to get PatientLifeConstant : {}", id);
        Optional<PatientLifeConstant> patientLifeConstant = patientLifeConstantService.findOne(id);
        return ResponseUtil.wrapOrNotFound(patientLifeConstant);
    }

    /**
     * {@code DELETE  /patient-life-constants/:id} : delete the "id" patientLifeConstant.
     *
     * @param id the id of the patientLifeConstant to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/patient-life-constants/{id}")
    public ResponseEntity<Void> deletePatientLifeConstant(@PathVariable Long id) {
        log.debug("REST request to delete PatientLifeConstant : {}", id);
        patientLifeConstantService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/patient-life-constants?query=:query} : search for the patientLifeConstant corresponding
     * to the query.
     *
     * @param query the query of the patientLifeConstant search.
     * @return the result of the search.
     */
    @GetMapping("/_search/patient-life-constants")
    public List<PatientLifeConstant> searchPatientLifeConstants(@RequestParam String query) {
        log.debug("REST request to search PatientLifeConstants for query {}", query);
        return patientLifeConstantService.search(query);
    }
}
