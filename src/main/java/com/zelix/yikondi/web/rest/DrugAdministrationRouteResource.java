package com.zelix.yikondi.web.rest;

import com.zelix.yikondi.domain.DrugAdministrationRoute;
import com.zelix.yikondi.service.DrugAdministrationRouteService;
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
 * REST controller for managing {@link com.zelix.yikondi.domain.DrugAdministrationRoute}.
 */
@RestController
@RequestMapping("/api")
public class DrugAdministrationRouteResource {

    private final Logger log = LoggerFactory.getLogger(DrugAdministrationRouteResource.class);

    private static final String ENTITY_NAME = "drugAdministrationRoute";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DrugAdministrationRouteService drugAdministrationRouteService;

    public DrugAdministrationRouteResource(DrugAdministrationRouteService drugAdministrationRouteService) {
        this.drugAdministrationRouteService = drugAdministrationRouteService;
    }

    /**
     * {@code POST  /drug-administration-routes} : Create a new drugAdministrationRoute.
     *
     * @param drugAdministrationRoute the drugAdministrationRoute to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new drugAdministrationRoute, or with status {@code 400 (Bad Request)} if the drugAdministrationRoute has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/drug-administration-routes")
    public ResponseEntity<DrugAdministrationRoute> createDrugAdministrationRoute(@RequestBody DrugAdministrationRoute drugAdministrationRoute) throws URISyntaxException {
        log.debug("REST request to save DrugAdministrationRoute : {}", drugAdministrationRoute);
        if (drugAdministrationRoute.getId() != null) {
            throw new BadRequestAlertException("A new drugAdministrationRoute cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DrugAdministrationRoute result = drugAdministrationRouteService.save(drugAdministrationRoute);
        return ResponseEntity.created(new URI("/api/drug-administration-routes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /drug-administration-routes} : Updates an existing drugAdministrationRoute.
     *
     * @param drugAdministrationRoute the drugAdministrationRoute to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated drugAdministrationRoute,
     * or with status {@code 400 (Bad Request)} if the drugAdministrationRoute is not valid,
     * or with status {@code 500 (Internal Server Error)} if the drugAdministrationRoute couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/drug-administration-routes")
    public ResponseEntity<DrugAdministrationRoute> updateDrugAdministrationRoute(@RequestBody DrugAdministrationRoute drugAdministrationRoute) throws URISyntaxException {
        log.debug("REST request to update DrugAdministrationRoute : {}", drugAdministrationRoute);
        if (drugAdministrationRoute.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DrugAdministrationRoute result = drugAdministrationRouteService.save(drugAdministrationRoute);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, drugAdministrationRoute.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /drug-administration-routes} : get all the drugAdministrationRoutes.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of drugAdministrationRoutes in body.
     */
    @GetMapping("/drug-administration-routes")
    public List<DrugAdministrationRoute> getAllDrugAdministrationRoutes() {
        log.debug("REST request to get all DrugAdministrationRoutes");
        return drugAdministrationRouteService.findAll();
    }

    /**
     * {@code GET  /drug-administration-routes/:id} : get the "id" drugAdministrationRoute.
     *
     * @param id the id of the drugAdministrationRoute to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the drugAdministrationRoute, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/drug-administration-routes/{id}")
    public ResponseEntity<DrugAdministrationRoute> getDrugAdministrationRoute(@PathVariable Long id) {
        log.debug("REST request to get DrugAdministrationRoute : {}", id);
        Optional<DrugAdministrationRoute> drugAdministrationRoute = drugAdministrationRouteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(drugAdministrationRoute);
    }

    /**
     * {@code DELETE  /drug-administration-routes/:id} : delete the "id" drugAdministrationRoute.
     *
     * @param id the id of the drugAdministrationRoute to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/drug-administration-routes/{id}")
    public ResponseEntity<Void> deleteDrugAdministrationRoute(@PathVariable Long id) {
        log.debug("REST request to delete DrugAdministrationRoute : {}", id);
        drugAdministrationRouteService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/drug-administration-routes?query=:query} : search for the drugAdministrationRoute corresponding
     * to the query.
     *
     * @param query the query of the drugAdministrationRoute search.
     * @return the result of the search.
     */
    @GetMapping("/_search/drug-administration-routes")
    public List<DrugAdministrationRoute> searchDrugAdministrationRoutes(@RequestParam String query) {
        log.debug("REST request to search DrugAdministrationRoutes for query {}", query);
        return drugAdministrationRouteService.search(query);
    }
}
