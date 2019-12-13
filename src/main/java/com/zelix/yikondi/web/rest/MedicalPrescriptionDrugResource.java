package com.zelix.yikondi.web.rest;

import com.zelix.yikondi.domain.MedicalPrescriptionDrug;
import com.zelix.yikondi.service.MedicalPrescriptionDrugService;
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
 * REST controller for managing {@link com.zelix.yikondi.domain.MedicalPrescriptionDrug}.
 */
@RestController
@RequestMapping("/api")
public class MedicalPrescriptionDrugResource {

    private final Logger log = LoggerFactory.getLogger(MedicalPrescriptionDrugResource.class);

    private static final String ENTITY_NAME = "medicalPrescriptionDrug";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MedicalPrescriptionDrugService medicalPrescriptionDrugService;

    public MedicalPrescriptionDrugResource(MedicalPrescriptionDrugService medicalPrescriptionDrugService) {
        this.medicalPrescriptionDrugService = medicalPrescriptionDrugService;
    }

    /**
     * {@code POST  /medical-prescription-drugs} : Create a new medicalPrescriptionDrug.
     *
     * @param medicalPrescriptionDrug the medicalPrescriptionDrug to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new medicalPrescriptionDrug, or with status {@code 400 (Bad Request)} if the medicalPrescriptionDrug has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/medical-prescription-drugs")
    public ResponseEntity<MedicalPrescriptionDrug> createMedicalPrescriptionDrug(@RequestBody MedicalPrescriptionDrug medicalPrescriptionDrug) throws URISyntaxException {
        log.debug("REST request to save MedicalPrescriptionDrug : {}", medicalPrescriptionDrug);
        if (medicalPrescriptionDrug.getId() != null) {
            throw new BadRequestAlertException("A new medicalPrescriptionDrug cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MedicalPrescriptionDrug result = medicalPrescriptionDrugService.save(medicalPrescriptionDrug);
        return ResponseEntity.created(new URI("/api/medical-prescription-drugs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /medical-prescription-drugs} : Updates an existing medicalPrescriptionDrug.
     *
     * @param medicalPrescriptionDrug the medicalPrescriptionDrug to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated medicalPrescriptionDrug,
     * or with status {@code 400 (Bad Request)} if the medicalPrescriptionDrug is not valid,
     * or with status {@code 500 (Internal Server Error)} if the medicalPrescriptionDrug couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/medical-prescription-drugs")
    public ResponseEntity<MedicalPrescriptionDrug> updateMedicalPrescriptionDrug(@RequestBody MedicalPrescriptionDrug medicalPrescriptionDrug) throws URISyntaxException {
        log.debug("REST request to update MedicalPrescriptionDrug : {}", medicalPrescriptionDrug);
        if (medicalPrescriptionDrug.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MedicalPrescriptionDrug result = medicalPrescriptionDrugService.save(medicalPrescriptionDrug);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, medicalPrescriptionDrug.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /medical-prescription-drugs} : get all the medicalPrescriptionDrugs.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of medicalPrescriptionDrugs in body.
     */
    @GetMapping("/medical-prescription-drugs")
    public List<MedicalPrescriptionDrug> getAllMedicalPrescriptionDrugs() {
        log.debug("REST request to get all MedicalPrescriptionDrugs");
        return medicalPrescriptionDrugService.findAll();
    }

    /**
     * {@code GET  /medical-prescription-drugs/:id} : get the "id" medicalPrescriptionDrug.
     *
     * @param id the id of the medicalPrescriptionDrug to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the medicalPrescriptionDrug, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/medical-prescription-drugs/{id}")
    public ResponseEntity<MedicalPrescriptionDrug> getMedicalPrescriptionDrug(@PathVariable Long id) {
        log.debug("REST request to get MedicalPrescriptionDrug : {}", id);
        Optional<MedicalPrescriptionDrug> medicalPrescriptionDrug = medicalPrescriptionDrugService.findOne(id);
        return ResponseUtil.wrapOrNotFound(medicalPrescriptionDrug);
    }

    /**
     * {@code DELETE  /medical-prescription-drugs/:id} : delete the "id" medicalPrescriptionDrug.
     *
     * @param id the id of the medicalPrescriptionDrug to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/medical-prescription-drugs/{id}")
    public ResponseEntity<Void> deleteMedicalPrescriptionDrug(@PathVariable Long id) {
        log.debug("REST request to delete MedicalPrescriptionDrug : {}", id);
        medicalPrescriptionDrugService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/medical-prescription-drugs?query=:query} : search for the medicalPrescriptionDrug corresponding
     * to the query.
     *
     * @param query the query of the medicalPrescriptionDrug search.
     * @return the result of the search.
     */
    @GetMapping("/_search/medical-prescription-drugs")
    public List<MedicalPrescriptionDrug> searchMedicalPrescriptionDrugs(@RequestParam String query) {
        log.debug("REST request to search MedicalPrescriptionDrugs for query {}", query);
        return medicalPrescriptionDrugService.search(query);
    }
}
