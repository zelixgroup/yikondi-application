package com.zelix.yikondi.web.rest;

import com.zelix.yikondi.domain.PatientPathology;
import com.zelix.yikondi.service.PatientPathologyService;
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
 * REST controller for managing {@link com.zelix.yikondi.domain.PatientPathology}.
 */
@RestController
@RequestMapping("/api")
public class PatientPathologyResource {

    private final Logger log = LoggerFactory.getLogger(PatientPathologyResource.class);

    private static final String ENTITY_NAME = "patientPathology";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PatientPathologyService patientPathologyService;

    public PatientPathologyResource(PatientPathologyService patientPathologyService) {
        this.patientPathologyService = patientPathologyService;
    }

    /**
     * {@code POST  /patient-pathologies} : Create a new patientPathology.
     *
     * @param patientPathology the patientPathology to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new patientPathology, or with status {@code 400 (Bad Request)} if the patientPathology has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/patient-pathologies")
    public ResponseEntity<PatientPathology> createPatientPathology(@RequestBody PatientPathology patientPathology) throws URISyntaxException {
        log.debug("REST request to save PatientPathology : {}", patientPathology);
        if (patientPathology.getId() != null) {
            throw new BadRequestAlertException("A new patientPathology cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PatientPathology result = patientPathologyService.save(patientPathology);
        return ResponseEntity.created(new URI("/api/patient-pathologies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /patient-pathologies} : Updates an existing patientPathology.
     *
     * @param patientPathology the patientPathology to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated patientPathology,
     * or with status {@code 400 (Bad Request)} if the patientPathology is not valid,
     * or with status {@code 500 (Internal Server Error)} if the patientPathology couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/patient-pathologies")
    public ResponseEntity<PatientPathology> updatePatientPathology(@RequestBody PatientPathology patientPathology) throws URISyntaxException {
        log.debug("REST request to update PatientPathology : {}", patientPathology);
        if (patientPathology.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PatientPathology result = patientPathologyService.save(patientPathology);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, patientPathology.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /patient-pathologies} : get all the patientPathologies.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of patientPathologies in body.
     */
    @GetMapping("/patient-pathologies")
    public List<PatientPathology> getAllPatientPathologies() {
        log.debug("REST request to get all PatientPathologies");
        return patientPathologyService.findAll();
    }

    /**
     * {@code GET  /patient-pathologies/:id} : get the "id" patientPathology.
     *
     * @param id the id of the patientPathology to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the patientPathology, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/patient-pathologies/{id}")
    public ResponseEntity<PatientPathology> getPatientPathology(@PathVariable Long id) {
        log.debug("REST request to get PatientPathology : {}", id);
        Optional<PatientPathology> patientPathology = patientPathologyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(patientPathology);
    }

    /**
     * {@code DELETE  /patient-pathologies/:id} : delete the "id" patientPathology.
     *
     * @param id the id of the patientPathology to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/patient-pathologies/{id}")
    public ResponseEntity<Void> deletePatientPathology(@PathVariable Long id) {
        log.debug("REST request to delete PatientPathology : {}", id);
        patientPathologyService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/patient-pathologies?query=:query} : search for the patientPathology corresponding
     * to the query.
     *
     * @param query the query of the patientPathology search.
     * @return the result of the search.
     */
    @GetMapping("/_search/patient-pathologies")
    public List<PatientPathology> searchPatientPathologies(@RequestParam String query) {
        log.debug("REST request to search PatientPathologies for query {}", query);
        return patientPathologyService.search(query);
    }
}
