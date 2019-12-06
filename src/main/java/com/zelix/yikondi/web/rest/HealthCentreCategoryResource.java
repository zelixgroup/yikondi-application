package com.zelix.yikondi.web.rest;

import com.zelix.yikondi.domain.HealthCentreCategory;
import com.zelix.yikondi.service.HealthCentreCategoryService;
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
 * REST controller for managing {@link com.zelix.yikondi.domain.HealthCentreCategory}.
 */
@RestController
@RequestMapping("/api")
public class HealthCentreCategoryResource {

    private final Logger log = LoggerFactory.getLogger(HealthCentreCategoryResource.class);

    private static final String ENTITY_NAME = "healthCentreCategory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HealthCentreCategoryService healthCentreCategoryService;

    public HealthCentreCategoryResource(HealthCentreCategoryService healthCentreCategoryService) {
        this.healthCentreCategoryService = healthCentreCategoryService;
    }

    /**
     * {@code POST  /health-centre-categories} : Create a new healthCentreCategory.
     *
     * @param healthCentreCategory the healthCentreCategory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new healthCentreCategory, or with status {@code 400 (Bad Request)} if the healthCentreCategory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/health-centre-categories")
    public ResponseEntity<HealthCentreCategory> createHealthCentreCategory(@RequestBody HealthCentreCategory healthCentreCategory) throws URISyntaxException {
        log.debug("REST request to save HealthCentreCategory : {}", healthCentreCategory);
        if (healthCentreCategory.getId() != null) {
            throw new BadRequestAlertException("A new healthCentreCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HealthCentreCategory result = healthCentreCategoryService.save(healthCentreCategory);
        return ResponseEntity.created(new URI("/api/health-centre-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /health-centre-categories} : Updates an existing healthCentreCategory.
     *
     * @param healthCentreCategory the healthCentreCategory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated healthCentreCategory,
     * or with status {@code 400 (Bad Request)} if the healthCentreCategory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the healthCentreCategory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/health-centre-categories")
    public ResponseEntity<HealthCentreCategory> updateHealthCentreCategory(@RequestBody HealthCentreCategory healthCentreCategory) throws URISyntaxException {
        log.debug("REST request to update HealthCentreCategory : {}", healthCentreCategory);
        if (healthCentreCategory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        HealthCentreCategory result = healthCentreCategoryService.save(healthCentreCategory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, healthCentreCategory.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /health-centre-categories} : get all the healthCentreCategories.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of healthCentreCategories in body.
     */
    @GetMapping("/health-centre-categories")
    public List<HealthCentreCategory> getAllHealthCentreCategories() {
        log.debug("REST request to get all HealthCentreCategories");
        return healthCentreCategoryService.findAll();
    }

    /**
     * {@code GET  /health-centre-categories/:id} : get the "id" healthCentreCategory.
     *
     * @param id the id of the healthCentreCategory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the healthCentreCategory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/health-centre-categories/{id}")
    public ResponseEntity<HealthCentreCategory> getHealthCentreCategory(@PathVariable Long id) {
        log.debug("REST request to get HealthCentreCategory : {}", id);
        Optional<HealthCentreCategory> healthCentreCategory = healthCentreCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(healthCentreCategory);
    }

    /**
     * {@code DELETE  /health-centre-categories/:id} : delete the "id" healthCentreCategory.
     *
     * @param id the id of the healthCentreCategory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/health-centre-categories/{id}")
    public ResponseEntity<Void> deleteHealthCentreCategory(@PathVariable Long id) {
        log.debug("REST request to delete HealthCentreCategory : {}", id);
        healthCentreCategoryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/health-centre-categories?query=:query} : search for the healthCentreCategory corresponding
     * to the query.
     *
     * @param query the query of the healthCentreCategory search.
     * @return the result of the search.
     */
    @GetMapping("/_search/health-centre-categories")
    public List<HealthCentreCategory> searchHealthCentreCategories(@RequestParam String query) {
        log.debug("REST request to search HealthCentreCategories for query {}", query);
        return healthCentreCategoryService.search(query);
    }
}
