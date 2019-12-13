package com.zelix.yikondi.web.rest;

import com.zelix.yikondi.domain.MedicalPrescriptionAnalysis;
import com.zelix.yikondi.service.MedicalPrescriptionAnalysisService;
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
 * REST controller for managing {@link com.zelix.yikondi.domain.MedicalPrescriptionAnalysis}.
 */
@RestController
@RequestMapping("/api")
public class MedicalPrescriptionAnalysisResource {

    private final Logger log = LoggerFactory.getLogger(MedicalPrescriptionAnalysisResource.class);

    private static final String ENTITY_NAME = "medicalPrescriptionAnalysis";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MedicalPrescriptionAnalysisService medicalPrescriptionAnalysisService;

    public MedicalPrescriptionAnalysisResource(MedicalPrescriptionAnalysisService medicalPrescriptionAnalysisService) {
        this.medicalPrescriptionAnalysisService = medicalPrescriptionAnalysisService;
    }

    /**
     * {@code POST  /medical-prescription-analyses} : Create a new medicalPrescriptionAnalysis.
     *
     * @param medicalPrescriptionAnalysis the medicalPrescriptionAnalysis to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new medicalPrescriptionAnalysis, or with status {@code 400 (Bad Request)} if the medicalPrescriptionAnalysis has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/medical-prescription-analyses")
    public ResponseEntity<MedicalPrescriptionAnalysis> createMedicalPrescriptionAnalysis(@RequestBody MedicalPrescriptionAnalysis medicalPrescriptionAnalysis) throws URISyntaxException {
        log.debug("REST request to save MedicalPrescriptionAnalysis : {}", medicalPrescriptionAnalysis);
        if (medicalPrescriptionAnalysis.getId() != null) {
            throw new BadRequestAlertException("A new medicalPrescriptionAnalysis cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MedicalPrescriptionAnalysis result = medicalPrescriptionAnalysisService.save(medicalPrescriptionAnalysis);
        return ResponseEntity.created(new URI("/api/medical-prescription-analyses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /medical-prescription-analyses} : Updates an existing medicalPrescriptionAnalysis.
     *
     * @param medicalPrescriptionAnalysis the medicalPrescriptionAnalysis to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated medicalPrescriptionAnalysis,
     * or with status {@code 400 (Bad Request)} if the medicalPrescriptionAnalysis is not valid,
     * or with status {@code 500 (Internal Server Error)} if the medicalPrescriptionAnalysis couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/medical-prescription-analyses")
    public ResponseEntity<MedicalPrescriptionAnalysis> updateMedicalPrescriptionAnalysis(@RequestBody MedicalPrescriptionAnalysis medicalPrescriptionAnalysis) throws URISyntaxException {
        log.debug("REST request to update MedicalPrescriptionAnalysis : {}", medicalPrescriptionAnalysis);
        if (medicalPrescriptionAnalysis.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MedicalPrescriptionAnalysis result = medicalPrescriptionAnalysisService.save(medicalPrescriptionAnalysis);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, medicalPrescriptionAnalysis.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /medical-prescription-analyses} : get all the medicalPrescriptionAnalyses.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of medicalPrescriptionAnalyses in body.
     */
    @GetMapping("/medical-prescription-analyses")
    public List<MedicalPrescriptionAnalysis> getAllMedicalPrescriptionAnalyses() {
        log.debug("REST request to get all MedicalPrescriptionAnalyses");
        return medicalPrescriptionAnalysisService.findAll();
    }

    /**
     * {@code GET  /medical-prescription-analyses/:id} : get the "id" medicalPrescriptionAnalysis.
     *
     * @param id the id of the medicalPrescriptionAnalysis to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the medicalPrescriptionAnalysis, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/medical-prescription-analyses/{id}")
    public ResponseEntity<MedicalPrescriptionAnalysis> getMedicalPrescriptionAnalysis(@PathVariable Long id) {
        log.debug("REST request to get MedicalPrescriptionAnalysis : {}", id);
        Optional<MedicalPrescriptionAnalysis> medicalPrescriptionAnalysis = medicalPrescriptionAnalysisService.findOne(id);
        return ResponseUtil.wrapOrNotFound(medicalPrescriptionAnalysis);
    }

    /**
     * {@code DELETE  /medical-prescription-analyses/:id} : delete the "id" medicalPrescriptionAnalysis.
     *
     * @param id the id of the medicalPrescriptionAnalysis to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/medical-prescription-analyses/{id}")
    public ResponseEntity<Void> deleteMedicalPrescriptionAnalysis(@PathVariable Long id) {
        log.debug("REST request to delete MedicalPrescriptionAnalysis : {}", id);
        medicalPrescriptionAnalysisService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/medical-prescription-analyses?query=:query} : search for the medicalPrescriptionAnalysis corresponding
     * to the query.
     *
     * @param query the query of the medicalPrescriptionAnalysis search.
     * @return the result of the search.
     */
    @GetMapping("/_search/medical-prescription-analyses")
    public List<MedicalPrescriptionAnalysis> searchMedicalPrescriptionAnalyses(@RequestParam String query) {
        log.debug("REST request to search MedicalPrescriptionAnalyses for query {}", query);
        return medicalPrescriptionAnalysisService.search(query);
    }
}
