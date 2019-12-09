package com.zelix.yikondi.web.rest;

import com.zelix.yikondi.domain.PatientAllergy;
import com.zelix.yikondi.service.PatientAllergyService;
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
 * REST controller for managing {@link com.zelix.yikondi.domain.PatientAllergy}.
 */
@RestController
@RequestMapping("/api")
public class PatientAllergyResource {

    private final Logger log = LoggerFactory.getLogger(PatientAllergyResource.class);

    private static final String ENTITY_NAME = "patientAllergy";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PatientAllergyService patientAllergyService;

    public PatientAllergyResource(PatientAllergyService patientAllergyService) {
        this.patientAllergyService = patientAllergyService;
    }

    /**
     * {@code POST  /patient-allergies} : Create a new patientAllergy.
     *
     * @param patientAllergy the patientAllergy to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new patientAllergy, or with status {@code 400 (Bad Request)} if the patientAllergy has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/patient-allergies")
    public ResponseEntity<PatientAllergy> createPatientAllergy(@RequestBody PatientAllergy patientAllergy) throws URISyntaxException {
        log.debug("REST request to save PatientAllergy : {}", patientAllergy);
        if (patientAllergy.getId() != null) {
            throw new BadRequestAlertException("A new patientAllergy cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PatientAllergy result = patientAllergyService.save(patientAllergy);
        return ResponseEntity.created(new URI("/api/patient-allergies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /patient-allergies} : Updates an existing patientAllergy.
     *
     * @param patientAllergy the patientAllergy to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated patientAllergy,
     * or with status {@code 400 (Bad Request)} if the patientAllergy is not valid,
     * or with status {@code 500 (Internal Server Error)} if the patientAllergy couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/patient-allergies")
    public ResponseEntity<PatientAllergy> updatePatientAllergy(@RequestBody PatientAllergy patientAllergy) throws URISyntaxException {
        log.debug("REST request to update PatientAllergy : {}", patientAllergy);
        if (patientAllergy.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PatientAllergy result = patientAllergyService.save(patientAllergy);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, patientAllergy.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /patient-allergies} : get all the patientAllergies.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of patientAllergies in body.
     */
    @GetMapping("/patient-allergies")
    public List<PatientAllergy> getAllPatientAllergies() {
        log.debug("REST request to get all PatientAllergies");
        return patientAllergyService.findAll();
    }

    /**
     * {@code GET  /patient-allergies/:id} : get the "id" patientAllergy.
     *
     * @param id the id of the patientAllergy to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the patientAllergy, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/patient-allergies/{id}")
    public ResponseEntity<PatientAllergy> getPatientAllergy(@PathVariable Long id) {
        log.debug("REST request to get PatientAllergy : {}", id);
        Optional<PatientAllergy> patientAllergy = patientAllergyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(patientAllergy);
    }

    /**
     * {@code DELETE  /patient-allergies/:id} : delete the "id" patientAllergy.
     *
     * @param id the id of the patientAllergy to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/patient-allergies/{id}")
    public ResponseEntity<Void> deletePatientAllergy(@PathVariable Long id) {
        log.debug("REST request to delete PatientAllergy : {}", id);
        patientAllergyService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/patient-allergies?query=:query} : search for the patientAllergy corresponding
     * to the query.
     *
     * @param query the query of the patientAllergy search.
     * @return the result of the search.
     */
    @GetMapping("/_search/patient-allergies")
    public List<PatientAllergy> searchPatientAllergies(@RequestParam String query) {
        log.debug("REST request to search PatientAllergies for query {}", query);
        return patientAllergyService.search(query);
    }
}
