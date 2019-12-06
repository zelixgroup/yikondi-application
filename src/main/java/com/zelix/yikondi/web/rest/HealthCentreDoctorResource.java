package com.zelix.yikondi.web.rest;

import com.zelix.yikondi.domain.HealthCentreDoctor;
import com.zelix.yikondi.service.HealthCentreDoctorService;
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
 * REST controller for managing {@link com.zelix.yikondi.domain.HealthCentreDoctor}.
 */
@RestController
@RequestMapping("/api")
public class HealthCentreDoctorResource {

    private final Logger log = LoggerFactory.getLogger(HealthCentreDoctorResource.class);

    private static final String ENTITY_NAME = "healthCentreDoctor";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HealthCentreDoctorService healthCentreDoctorService;

    public HealthCentreDoctorResource(HealthCentreDoctorService healthCentreDoctorService) {
        this.healthCentreDoctorService = healthCentreDoctorService;
    }

    /**
     * {@code POST  /health-centre-doctors} : Create a new healthCentreDoctor.
     *
     * @param healthCentreDoctor the healthCentreDoctor to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new healthCentreDoctor, or with status {@code 400 (Bad Request)} if the healthCentreDoctor has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/health-centre-doctors")
    public ResponseEntity<HealthCentreDoctor> createHealthCentreDoctor(@RequestBody HealthCentreDoctor healthCentreDoctor) throws URISyntaxException {
        log.debug("REST request to save HealthCentreDoctor : {}", healthCentreDoctor);
        if (healthCentreDoctor.getId() != null) {
            throw new BadRequestAlertException("A new healthCentreDoctor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HealthCentreDoctor result = healthCentreDoctorService.save(healthCentreDoctor);
        return ResponseEntity.created(new URI("/api/health-centre-doctors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /health-centre-doctors} : Updates an existing healthCentreDoctor.
     *
     * @param healthCentreDoctor the healthCentreDoctor to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated healthCentreDoctor,
     * or with status {@code 400 (Bad Request)} if the healthCentreDoctor is not valid,
     * or with status {@code 500 (Internal Server Error)} if the healthCentreDoctor couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/health-centre-doctors")
    public ResponseEntity<HealthCentreDoctor> updateHealthCentreDoctor(@RequestBody HealthCentreDoctor healthCentreDoctor) throws URISyntaxException {
        log.debug("REST request to update HealthCentreDoctor : {}", healthCentreDoctor);
        if (healthCentreDoctor.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        HealthCentreDoctor result = healthCentreDoctorService.save(healthCentreDoctor);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, healthCentreDoctor.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /health-centre-doctors} : get all the healthCentreDoctors.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of healthCentreDoctors in body.
     */
    @GetMapping("/health-centre-doctors")
    public List<HealthCentreDoctor> getAllHealthCentreDoctors() {
        log.debug("REST request to get all HealthCentreDoctors");
        return healthCentreDoctorService.findAll();
    }

    /**
     * {@code GET  /health-centre-doctors/:id} : get the "id" healthCentreDoctor.
     *
     * @param id the id of the healthCentreDoctor to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the healthCentreDoctor, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/health-centre-doctors/{id}")
    public ResponseEntity<HealthCentreDoctor> getHealthCentreDoctor(@PathVariable Long id) {
        log.debug("REST request to get HealthCentreDoctor : {}", id);
        Optional<HealthCentreDoctor> healthCentreDoctor = healthCentreDoctorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(healthCentreDoctor);
    }

    /**
     * {@code DELETE  /health-centre-doctors/:id} : delete the "id" healthCentreDoctor.
     *
     * @param id the id of the healthCentreDoctor to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/health-centre-doctors/{id}")
    public ResponseEntity<Void> deleteHealthCentreDoctor(@PathVariable Long id) {
        log.debug("REST request to delete HealthCentreDoctor : {}", id);
        healthCentreDoctorService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/health-centre-doctors?query=:query} : search for the healthCentreDoctor corresponding
     * to the query.
     *
     * @param query the query of the healthCentreDoctor search.
     * @return the result of the search.
     */
    @GetMapping("/_search/health-centre-doctors")
    public List<HealthCentreDoctor> searchHealthCentreDoctors(@RequestParam String query) {
        log.debug("REST request to search HealthCentreDoctors for query {}", query);
        return healthCentreDoctorService.search(query);
    }
}
