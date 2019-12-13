package com.zelix.yikondi.web.rest;

import com.zelix.yikondi.domain.MedicalRecordAuthorization;
import com.zelix.yikondi.service.MedicalRecordAuthorizationService;
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
 * REST controller for managing {@link com.zelix.yikondi.domain.MedicalRecordAuthorization}.
 */
@RestController
@RequestMapping("/api")
public class MedicalRecordAuthorizationResource {

    private final Logger log = LoggerFactory.getLogger(MedicalRecordAuthorizationResource.class);

    private static final String ENTITY_NAME = "medicalRecordAuthorization";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MedicalRecordAuthorizationService medicalRecordAuthorizationService;

    public MedicalRecordAuthorizationResource(MedicalRecordAuthorizationService medicalRecordAuthorizationService) {
        this.medicalRecordAuthorizationService = medicalRecordAuthorizationService;
    }

    /**
     * {@code POST  /medical-record-authorizations} : Create a new medicalRecordAuthorization.
     *
     * @param medicalRecordAuthorization the medicalRecordAuthorization to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new medicalRecordAuthorization, or with status {@code 400 (Bad Request)} if the medicalRecordAuthorization has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/medical-record-authorizations")
    public ResponseEntity<MedicalRecordAuthorization> createMedicalRecordAuthorization(@RequestBody MedicalRecordAuthorization medicalRecordAuthorization) throws URISyntaxException {
        log.debug("REST request to save MedicalRecordAuthorization : {}", medicalRecordAuthorization);
        if (medicalRecordAuthorization.getId() != null) {
            throw new BadRequestAlertException("A new medicalRecordAuthorization cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MedicalRecordAuthorization result = medicalRecordAuthorizationService.save(medicalRecordAuthorization);
        return ResponseEntity.created(new URI("/api/medical-record-authorizations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /medical-record-authorizations} : Updates an existing medicalRecordAuthorization.
     *
     * @param medicalRecordAuthorization the medicalRecordAuthorization to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated medicalRecordAuthorization,
     * or with status {@code 400 (Bad Request)} if the medicalRecordAuthorization is not valid,
     * or with status {@code 500 (Internal Server Error)} if the medicalRecordAuthorization couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/medical-record-authorizations")
    public ResponseEntity<MedicalRecordAuthorization> updateMedicalRecordAuthorization(@RequestBody MedicalRecordAuthorization medicalRecordAuthorization) throws URISyntaxException {
        log.debug("REST request to update MedicalRecordAuthorization : {}", medicalRecordAuthorization);
        if (medicalRecordAuthorization.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MedicalRecordAuthorization result = medicalRecordAuthorizationService.save(medicalRecordAuthorization);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, medicalRecordAuthorization.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /medical-record-authorizations} : get all the medicalRecordAuthorizations.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of medicalRecordAuthorizations in body.
     */
    @GetMapping("/medical-record-authorizations")
    public List<MedicalRecordAuthorization> getAllMedicalRecordAuthorizations() {
        log.debug("REST request to get all MedicalRecordAuthorizations");
        return medicalRecordAuthorizationService.findAll();
    }

    /**
     * {@code GET  /medical-record-authorizations/:id} : get the "id" medicalRecordAuthorization.
     *
     * @param id the id of the medicalRecordAuthorization to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the medicalRecordAuthorization, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/medical-record-authorizations/{id}")
    public ResponseEntity<MedicalRecordAuthorization> getMedicalRecordAuthorization(@PathVariable Long id) {
        log.debug("REST request to get MedicalRecordAuthorization : {}", id);
        Optional<MedicalRecordAuthorization> medicalRecordAuthorization = medicalRecordAuthorizationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(medicalRecordAuthorization);
    }

    /**
     * {@code DELETE  /medical-record-authorizations/:id} : delete the "id" medicalRecordAuthorization.
     *
     * @param id the id of the medicalRecordAuthorization to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/medical-record-authorizations/{id}")
    public ResponseEntity<Void> deleteMedicalRecordAuthorization(@PathVariable Long id) {
        log.debug("REST request to delete MedicalRecordAuthorization : {}", id);
        medicalRecordAuthorizationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/medical-record-authorizations?query=:query} : search for the medicalRecordAuthorization corresponding
     * to the query.
     *
     * @param query the query of the medicalRecordAuthorization search.
     * @return the result of the search.
     */
    @GetMapping("/_search/medical-record-authorizations")
    public List<MedicalRecordAuthorization> searchMedicalRecordAuthorizations(@RequestParam String query) {
        log.debug("REST request to search MedicalRecordAuthorizations for query {}", query);
        return medicalRecordAuthorizationService.search(query);
    }
}
