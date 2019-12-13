package com.zelix.yikondi.web.rest;

import com.zelix.yikondi.domain.Drug;
import com.zelix.yikondi.service.DrugService;
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
 * REST controller for managing {@link com.zelix.yikondi.domain.Drug}.
 */
@RestController
@RequestMapping("/api")
public class DrugResource {

    private final Logger log = LoggerFactory.getLogger(DrugResource.class);

    private static final String ENTITY_NAME = "drug";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DrugService drugService;

    public DrugResource(DrugService drugService) {
        this.drugService = drugService;
    }

    /**
     * {@code POST  /drugs} : Create a new drug.
     *
     * @param drug the drug to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new drug, or with status {@code 400 (Bad Request)} if the drug has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/drugs")
    public ResponseEntity<Drug> createDrug(@RequestBody Drug drug) throws URISyntaxException {
        log.debug("REST request to save Drug : {}", drug);
        if (drug.getId() != null) {
            throw new BadRequestAlertException("A new drug cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Drug result = drugService.save(drug);
        return ResponseEntity.created(new URI("/api/drugs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /drugs} : Updates an existing drug.
     *
     * @param drug the drug to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated drug,
     * or with status {@code 400 (Bad Request)} if the drug is not valid,
     * or with status {@code 500 (Internal Server Error)} if the drug couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/drugs")
    public ResponseEntity<Drug> updateDrug(@RequestBody Drug drug) throws URISyntaxException {
        log.debug("REST request to update Drug : {}", drug);
        if (drug.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Drug result = drugService.save(drug);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, drug.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /drugs} : get all the drugs.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of drugs in body.
     */
    @GetMapping("/drugs")
    public List<Drug> getAllDrugs() {
        log.debug("REST request to get all Drugs");
        return drugService.findAll();
    }

    /**
     * {@code GET  /drugs/:id} : get the "id" drug.
     *
     * @param id the id of the drug to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the drug, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/drugs/{id}")
    public ResponseEntity<Drug> getDrug(@PathVariable Long id) {
        log.debug("REST request to get Drug : {}", id);
        Optional<Drug> drug = drugService.findOne(id);
        return ResponseUtil.wrapOrNotFound(drug);
    }

    /**
     * {@code DELETE  /drugs/:id} : delete the "id" drug.
     *
     * @param id the id of the drug to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/drugs/{id}")
    public ResponseEntity<Void> deleteDrug(@PathVariable Long id) {
        log.debug("REST request to delete Drug : {}", id);
        drugService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/drugs?query=:query} : search for the drug corresponding
     * to the query.
     *
     * @param query the query of the drug search.
     * @return the result of the search.
     */
    @GetMapping("/_search/drugs")
    public List<Drug> searchDrugs(@RequestParam String query) {
        log.debug("REST request to search Drugs for query {}", query);
        return drugService.search(query);
    }
}
