package com.zelix.yikondi.web.rest;

import com.zelix.yikondi.domain.MedicalPrescription;
import com.zelix.yikondi.service.MedicalPrescriptionService;
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
 * REST controller for managing {@link com.zelix.yikondi.domain.MedicalPrescription}.
 */
@RestController
@RequestMapping("/api")
public class MedicalPrescriptionResource {

    private final Logger log = LoggerFactory.getLogger(MedicalPrescriptionResource.class);

    private static final String ENTITY_NAME = "medicalPrescription";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MedicalPrescriptionService medicalPrescriptionService;

    public MedicalPrescriptionResource(MedicalPrescriptionService medicalPrescriptionService) {
        this.medicalPrescriptionService = medicalPrescriptionService;
    }

    /**
     * {@code POST  /medical-prescriptions} : Create a new medicalPrescription.
     *
     * @param medicalPrescription the medicalPrescription to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new medicalPrescription, or with status {@code 400 (Bad Request)} if the medicalPrescription has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/medical-prescriptions")
    public ResponseEntity<MedicalPrescription> createMedicalPrescription(@RequestBody MedicalPrescription medicalPrescription) throws URISyntaxException {
        log.debug("REST request to save MedicalPrescription : {}", medicalPrescription);
        if (medicalPrescription.getId() != null) {
            throw new BadRequestAlertException("A new medicalPrescription cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MedicalPrescription result = medicalPrescriptionService.save(medicalPrescription);
        return ResponseEntity.created(new URI("/api/medical-prescriptions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /medical-prescriptions} : Updates an existing medicalPrescription.
     *
     * @param medicalPrescription the medicalPrescription to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated medicalPrescription,
     * or with status {@code 400 (Bad Request)} if the medicalPrescription is not valid,
     * or with status {@code 500 (Internal Server Error)} if the medicalPrescription couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/medical-prescriptions")
    public ResponseEntity<MedicalPrescription> updateMedicalPrescription(@RequestBody MedicalPrescription medicalPrescription) throws URISyntaxException {
        log.debug("REST request to update MedicalPrescription : {}", medicalPrescription);
        if (medicalPrescription.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MedicalPrescription result = medicalPrescriptionService.save(medicalPrescription);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, medicalPrescription.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /medical-prescriptions} : get all the medicalPrescriptions.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of medicalPrescriptions in body.
     */
    @GetMapping("/medical-prescriptions")
    public List<MedicalPrescription> getAllMedicalPrescriptions() {
        log.debug("REST request to get all MedicalPrescriptions");
        return medicalPrescriptionService.findAll();
    }

    /**
     * {@code GET  /medical-prescriptions/:id} : get the "id" medicalPrescription.
     *
     * @param id the id of the medicalPrescription to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the medicalPrescription, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/medical-prescriptions/{id}")
    public ResponseEntity<MedicalPrescription> getMedicalPrescription(@PathVariable Long id) {
        log.debug("REST request to get MedicalPrescription : {}", id);
        Optional<MedicalPrescription> medicalPrescription = medicalPrescriptionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(medicalPrescription);
    }

    /**
     * {@code DELETE  /medical-prescriptions/:id} : delete the "id" medicalPrescription.
     *
     * @param id the id of the medicalPrescription to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/medical-prescriptions/{id}")
    public ResponseEntity<Void> deleteMedicalPrescription(@PathVariable Long id) {
        log.debug("REST request to delete MedicalPrescription : {}", id);
        medicalPrescriptionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/medical-prescriptions?query=:query} : search for the medicalPrescription corresponding
     * to the query.
     *
     * @param query the query of the medicalPrescription search.
     * @return the result of the search.
     */
    @GetMapping("/_search/medical-prescriptions")
    public List<MedicalPrescription> searchMedicalPrescriptions(@RequestParam String query) {
        log.debug("REST request to search MedicalPrescriptions for query {}", query);
        return medicalPrescriptionService.search(query);
    }
}
