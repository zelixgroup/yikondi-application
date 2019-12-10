package com.zelix.yikondi.web.rest;

import com.zelix.yikondi.domain.PatientEmergencyNumber;
import com.zelix.yikondi.service.PatientEmergencyNumberService;
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
 * REST controller for managing {@link com.zelix.yikondi.domain.PatientEmergencyNumber}.
 */
@RestController
@RequestMapping("/api")
public class PatientEmergencyNumberResource {

    private final Logger log = LoggerFactory.getLogger(PatientEmergencyNumberResource.class);

    private static final String ENTITY_NAME = "patientEmergencyNumber";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PatientEmergencyNumberService patientEmergencyNumberService;

    public PatientEmergencyNumberResource(PatientEmergencyNumberService patientEmergencyNumberService) {
        this.patientEmergencyNumberService = patientEmergencyNumberService;
    }

    /**
     * {@code POST  /patient-emergency-numbers} : Create a new patientEmergencyNumber.
     *
     * @param patientEmergencyNumber the patientEmergencyNumber to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new patientEmergencyNumber, or with status {@code 400 (Bad Request)} if the patientEmergencyNumber has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/patient-emergency-numbers")
    public ResponseEntity<PatientEmergencyNumber> createPatientEmergencyNumber(@RequestBody PatientEmergencyNumber patientEmergencyNumber) throws URISyntaxException {
        log.debug("REST request to save PatientEmergencyNumber : {}", patientEmergencyNumber);
        if (patientEmergencyNumber.getId() != null) {
            throw new BadRequestAlertException("A new patientEmergencyNumber cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PatientEmergencyNumber result = patientEmergencyNumberService.save(patientEmergencyNumber);
        return ResponseEntity.created(new URI("/api/patient-emergency-numbers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /patient-emergency-numbers} : Updates an existing patientEmergencyNumber.
     *
     * @param patientEmergencyNumber the patientEmergencyNumber to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated patientEmergencyNumber,
     * or with status {@code 400 (Bad Request)} if the patientEmergencyNumber is not valid,
     * or with status {@code 500 (Internal Server Error)} if the patientEmergencyNumber couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/patient-emergency-numbers")
    public ResponseEntity<PatientEmergencyNumber> updatePatientEmergencyNumber(@RequestBody PatientEmergencyNumber patientEmergencyNumber) throws URISyntaxException {
        log.debug("REST request to update PatientEmergencyNumber : {}", patientEmergencyNumber);
        if (patientEmergencyNumber.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PatientEmergencyNumber result = patientEmergencyNumberService.save(patientEmergencyNumber);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, patientEmergencyNumber.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /patient-emergency-numbers} : get all the patientEmergencyNumbers.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of patientEmergencyNumbers in body.
     */
    @GetMapping("/patient-emergency-numbers")
    public List<PatientEmergencyNumber> getAllPatientEmergencyNumbers() {
        log.debug("REST request to get all PatientEmergencyNumbers");
        return patientEmergencyNumberService.findAll();
    }

    /**
     * {@code GET  /patient-emergency-numbers/:id} : get the "id" patientEmergencyNumber.
     *
     * @param id the id of the patientEmergencyNumber to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the patientEmergencyNumber, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/patient-emergency-numbers/{id}")
    public ResponseEntity<PatientEmergencyNumber> getPatientEmergencyNumber(@PathVariable Long id) {
        log.debug("REST request to get PatientEmergencyNumber : {}", id);
        Optional<PatientEmergencyNumber> patientEmergencyNumber = patientEmergencyNumberService.findOne(id);
        return ResponseUtil.wrapOrNotFound(patientEmergencyNumber);
    }

    /**
     * {@code DELETE  /patient-emergency-numbers/:id} : delete the "id" patientEmergencyNumber.
     *
     * @param id the id of the patientEmergencyNumber to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/patient-emergency-numbers/{id}")
    public ResponseEntity<Void> deletePatientEmergencyNumber(@PathVariable Long id) {
        log.debug("REST request to delete PatientEmergencyNumber : {}", id);
        patientEmergencyNumberService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/patient-emergency-numbers?query=:query} : search for the patientEmergencyNumber corresponding
     * to the query.
     *
     * @param query the query of the patientEmergencyNumber search.
     * @return the result of the search.
     */
    @GetMapping("/_search/patient-emergency-numbers")
    public List<PatientEmergencyNumber> searchPatientEmergencyNumbers(@RequestParam String query) {
        log.debug("REST request to search PatientEmergencyNumbers for query {}", query);
        return patientEmergencyNumberService.search(query);
    }
}
