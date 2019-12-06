package com.zelix.yikondi.web.rest;

import com.zelix.yikondi.domain.HealthCentre;
import com.zelix.yikondi.service.HealthCentreService;
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
 * REST controller for managing {@link com.zelix.yikondi.domain.HealthCentre}.
 */
@RestController
@RequestMapping("/api")
public class HealthCentreResource {

    private final Logger log = LoggerFactory.getLogger(HealthCentreResource.class);

    private static final String ENTITY_NAME = "healthCentre";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HealthCentreService healthCentreService;

    public HealthCentreResource(HealthCentreService healthCentreService) {
        this.healthCentreService = healthCentreService;
    }

    /**
     * {@code POST  /health-centres} : Create a new healthCentre.
     *
     * @param healthCentre the healthCentre to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new healthCentre, or with status {@code 400 (Bad Request)} if the healthCentre has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/health-centres")
    public ResponseEntity<HealthCentre> createHealthCentre(@RequestBody HealthCentre healthCentre) throws URISyntaxException {
        log.debug("REST request to save HealthCentre : {}", healthCentre);
        if (healthCentre.getId() != null) {
            throw new BadRequestAlertException("A new healthCentre cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HealthCentre result = healthCentreService.save(healthCentre);
        return ResponseEntity.created(new URI("/api/health-centres/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /health-centres} : Updates an existing healthCentre.
     *
     * @param healthCentre the healthCentre to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated healthCentre,
     * or with status {@code 400 (Bad Request)} if the healthCentre is not valid,
     * or with status {@code 500 (Internal Server Error)} if the healthCentre couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/health-centres")
    public ResponseEntity<HealthCentre> updateHealthCentre(@RequestBody HealthCentre healthCentre) throws URISyntaxException {
        log.debug("REST request to update HealthCentre : {}", healthCentre);
        if (healthCentre.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        HealthCentre result = healthCentreService.save(healthCentre);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, healthCentre.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /health-centres} : get all the healthCentres.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of healthCentres in body.
     */
    @GetMapping("/health-centres")
    public List<HealthCentre> getAllHealthCentres() {
        log.debug("REST request to get all HealthCentres");
        return healthCentreService.findAll();
    }

    /**
     * {@code GET  /health-centres/:id} : get the "id" healthCentre.
     *
     * @param id the id of the healthCentre to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the healthCentre, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/health-centres/{id}")
    public ResponseEntity<HealthCentre> getHealthCentre(@PathVariable Long id) {
        log.debug("REST request to get HealthCentre : {}", id);
        Optional<HealthCentre> healthCentre = healthCentreService.findOne(id);
        return ResponseUtil.wrapOrNotFound(healthCentre);
    }

    /**
     * {@code DELETE  /health-centres/:id} : delete the "id" healthCentre.
     *
     * @param id the id of the healthCentre to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/health-centres/{id}")
    public ResponseEntity<Void> deleteHealthCentre(@PathVariable Long id) {
        log.debug("REST request to delete HealthCentre : {}", id);
        healthCentreService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/health-centres?query=:query} : search for the healthCentre corresponding
     * to the query.
     *
     * @param query the query of the healthCentre search.
     * @return the result of the search.
     */
    @GetMapping("/_search/health-centres")
    public List<HealthCentre> searchHealthCentres(@RequestParam String query) {
        log.debug("REST request to search HealthCentres for query {}", query);
        return healthCentreService.search(query);
    }
}
