package com.zelix.yikondi.web.rest;

import com.zelix.yikondi.domain.DrugDosageForm;
import com.zelix.yikondi.service.DrugDosageFormService;
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
 * REST controller for managing {@link com.zelix.yikondi.domain.DrugDosageForm}.
 */
@RestController
@RequestMapping("/api")
public class DrugDosageFormResource {

    private final Logger log = LoggerFactory.getLogger(DrugDosageFormResource.class);

    private static final String ENTITY_NAME = "drugDosageForm";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DrugDosageFormService drugDosageFormService;

    public DrugDosageFormResource(DrugDosageFormService drugDosageFormService) {
        this.drugDosageFormService = drugDosageFormService;
    }

    /**
     * {@code POST  /drug-dosage-forms} : Create a new drugDosageForm.
     *
     * @param drugDosageForm the drugDosageForm to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new drugDosageForm, or with status {@code 400 (Bad Request)} if the drugDosageForm has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/drug-dosage-forms")
    public ResponseEntity<DrugDosageForm> createDrugDosageForm(@RequestBody DrugDosageForm drugDosageForm) throws URISyntaxException {
        log.debug("REST request to save DrugDosageForm : {}", drugDosageForm);
        if (drugDosageForm.getId() != null) {
            throw new BadRequestAlertException("A new drugDosageForm cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DrugDosageForm result = drugDosageFormService.save(drugDosageForm);
        return ResponseEntity.created(new URI("/api/drug-dosage-forms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /drug-dosage-forms} : Updates an existing drugDosageForm.
     *
     * @param drugDosageForm the drugDosageForm to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated drugDosageForm,
     * or with status {@code 400 (Bad Request)} if the drugDosageForm is not valid,
     * or with status {@code 500 (Internal Server Error)} if the drugDosageForm couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/drug-dosage-forms")
    public ResponseEntity<DrugDosageForm> updateDrugDosageForm(@RequestBody DrugDosageForm drugDosageForm) throws URISyntaxException {
        log.debug("REST request to update DrugDosageForm : {}", drugDosageForm);
        if (drugDosageForm.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DrugDosageForm result = drugDosageFormService.save(drugDosageForm);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, drugDosageForm.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /drug-dosage-forms} : get all the drugDosageForms.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of drugDosageForms in body.
     */
    @GetMapping("/drug-dosage-forms")
    public List<DrugDosageForm> getAllDrugDosageForms() {
        log.debug("REST request to get all DrugDosageForms");
        return drugDosageFormService.findAll();
    }

    /**
     * {@code GET  /drug-dosage-forms/:id} : get the "id" drugDosageForm.
     *
     * @param id the id of the drugDosageForm to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the drugDosageForm, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/drug-dosage-forms/{id}")
    public ResponseEntity<DrugDosageForm> getDrugDosageForm(@PathVariable Long id) {
        log.debug("REST request to get DrugDosageForm : {}", id);
        Optional<DrugDosageForm> drugDosageForm = drugDosageFormService.findOne(id);
        return ResponseUtil.wrapOrNotFound(drugDosageForm);
    }

    /**
     * {@code DELETE  /drug-dosage-forms/:id} : delete the "id" drugDosageForm.
     *
     * @param id the id of the drugDosageForm to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/drug-dosage-forms/{id}")
    public ResponseEntity<Void> deleteDrugDosageForm(@PathVariable Long id) {
        log.debug("REST request to delete DrugDosageForm : {}", id);
        drugDosageFormService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/drug-dosage-forms?query=:query} : search for the drugDosageForm corresponding
     * to the query.
     *
     * @param query the query of the drugDosageForm search.
     * @return the result of the search.
     */
    @GetMapping("/_search/drug-dosage-forms")
    public List<DrugDosageForm> searchDrugDosageForms(@RequestParam String query) {
        log.debug("REST request to search DrugDosageForms for query {}", query);
        return drugDosageFormService.search(query);
    }
}
