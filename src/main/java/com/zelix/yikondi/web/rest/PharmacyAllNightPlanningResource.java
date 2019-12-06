package com.zelix.yikondi.web.rest;

import com.zelix.yikondi.domain.PharmacyAllNightPlanning;
import com.zelix.yikondi.service.PharmacyAllNightPlanningService;
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
 * REST controller for managing {@link com.zelix.yikondi.domain.PharmacyAllNightPlanning}.
 */
@RestController
@RequestMapping("/api")
public class PharmacyAllNightPlanningResource {

    private final Logger log = LoggerFactory.getLogger(PharmacyAllNightPlanningResource.class);

    private static final String ENTITY_NAME = "pharmacyAllNightPlanning";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PharmacyAllNightPlanningService pharmacyAllNightPlanningService;

    public PharmacyAllNightPlanningResource(PharmacyAllNightPlanningService pharmacyAllNightPlanningService) {
        this.pharmacyAllNightPlanningService = pharmacyAllNightPlanningService;
    }

    /**
     * {@code POST  /pharmacy-all-night-plannings} : Create a new pharmacyAllNightPlanning.
     *
     * @param pharmacyAllNightPlanning the pharmacyAllNightPlanning to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pharmacyAllNightPlanning, or with status {@code 400 (Bad Request)} if the pharmacyAllNightPlanning has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pharmacy-all-night-plannings")
    public ResponseEntity<PharmacyAllNightPlanning> createPharmacyAllNightPlanning(@RequestBody PharmacyAllNightPlanning pharmacyAllNightPlanning) throws URISyntaxException {
        log.debug("REST request to save PharmacyAllNightPlanning : {}", pharmacyAllNightPlanning);
        if (pharmacyAllNightPlanning.getId() != null) {
            throw new BadRequestAlertException("A new pharmacyAllNightPlanning cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PharmacyAllNightPlanning result = pharmacyAllNightPlanningService.save(pharmacyAllNightPlanning);
        return ResponseEntity.created(new URI("/api/pharmacy-all-night-plannings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pharmacy-all-night-plannings} : Updates an existing pharmacyAllNightPlanning.
     *
     * @param pharmacyAllNightPlanning the pharmacyAllNightPlanning to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pharmacyAllNightPlanning,
     * or with status {@code 400 (Bad Request)} if the pharmacyAllNightPlanning is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pharmacyAllNightPlanning couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pharmacy-all-night-plannings")
    public ResponseEntity<PharmacyAllNightPlanning> updatePharmacyAllNightPlanning(@RequestBody PharmacyAllNightPlanning pharmacyAllNightPlanning) throws URISyntaxException {
        log.debug("REST request to update PharmacyAllNightPlanning : {}", pharmacyAllNightPlanning);
        if (pharmacyAllNightPlanning.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PharmacyAllNightPlanning result = pharmacyAllNightPlanningService.save(pharmacyAllNightPlanning);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pharmacyAllNightPlanning.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pharmacy-all-night-plannings} : get all the pharmacyAllNightPlannings.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pharmacyAllNightPlannings in body.
     */
    @GetMapping("/pharmacy-all-night-plannings")
    public List<PharmacyAllNightPlanning> getAllPharmacyAllNightPlannings() {
        log.debug("REST request to get all PharmacyAllNightPlannings");
        return pharmacyAllNightPlanningService.findAll();
    }

    /**
     * {@code GET  /pharmacy-all-night-plannings/:id} : get the "id" pharmacyAllNightPlanning.
     *
     * @param id the id of the pharmacyAllNightPlanning to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pharmacyAllNightPlanning, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pharmacy-all-night-plannings/{id}")
    public ResponseEntity<PharmacyAllNightPlanning> getPharmacyAllNightPlanning(@PathVariable Long id) {
        log.debug("REST request to get PharmacyAllNightPlanning : {}", id);
        Optional<PharmacyAllNightPlanning> pharmacyAllNightPlanning = pharmacyAllNightPlanningService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pharmacyAllNightPlanning);
    }

    /**
     * {@code DELETE  /pharmacy-all-night-plannings/:id} : delete the "id" pharmacyAllNightPlanning.
     *
     * @param id the id of the pharmacyAllNightPlanning to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pharmacy-all-night-plannings/{id}")
    public ResponseEntity<Void> deletePharmacyAllNightPlanning(@PathVariable Long id) {
        log.debug("REST request to delete PharmacyAllNightPlanning : {}", id);
        pharmacyAllNightPlanningService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/pharmacy-all-night-plannings?query=:query} : search for the pharmacyAllNightPlanning corresponding
     * to the query.
     *
     * @param query the query of the pharmacyAllNightPlanning search.
     * @return the result of the search.
     */
    @GetMapping("/_search/pharmacy-all-night-plannings")
    public List<PharmacyAllNightPlanning> searchPharmacyAllNightPlannings(@RequestParam String query) {
        log.debug("REST request to search PharmacyAllNightPlannings for query {}", query);
        return pharmacyAllNightPlanningService.search(query);
    }
}
