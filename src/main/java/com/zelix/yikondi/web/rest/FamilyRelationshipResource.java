package com.zelix.yikondi.web.rest;

import com.zelix.yikondi.domain.FamilyRelationship;
import com.zelix.yikondi.service.FamilyRelationshipService;
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
 * REST controller for managing {@link com.zelix.yikondi.domain.FamilyRelationship}.
 */
@RestController
@RequestMapping("/api")
public class FamilyRelationshipResource {

    private final Logger log = LoggerFactory.getLogger(FamilyRelationshipResource.class);

    private static final String ENTITY_NAME = "familyRelationship";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FamilyRelationshipService familyRelationshipService;

    public FamilyRelationshipResource(FamilyRelationshipService familyRelationshipService) {
        this.familyRelationshipService = familyRelationshipService;
    }

    /**
     * {@code POST  /family-relationships} : Create a new familyRelationship.
     *
     * @param familyRelationship the familyRelationship to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new familyRelationship, or with status {@code 400 (Bad Request)} if the familyRelationship has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/family-relationships")
    public ResponseEntity<FamilyRelationship> createFamilyRelationship(@RequestBody FamilyRelationship familyRelationship) throws URISyntaxException {
        log.debug("REST request to save FamilyRelationship : {}", familyRelationship);
        if (familyRelationship.getId() != null) {
            throw new BadRequestAlertException("A new familyRelationship cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FamilyRelationship result = familyRelationshipService.save(familyRelationship);
        return ResponseEntity.created(new URI("/api/family-relationships/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /family-relationships} : Updates an existing familyRelationship.
     *
     * @param familyRelationship the familyRelationship to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated familyRelationship,
     * or with status {@code 400 (Bad Request)} if the familyRelationship is not valid,
     * or with status {@code 500 (Internal Server Error)} if the familyRelationship couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/family-relationships")
    public ResponseEntity<FamilyRelationship> updateFamilyRelationship(@RequestBody FamilyRelationship familyRelationship) throws URISyntaxException {
        log.debug("REST request to update FamilyRelationship : {}", familyRelationship);
        if (familyRelationship.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FamilyRelationship result = familyRelationshipService.save(familyRelationship);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, familyRelationship.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /family-relationships} : get all the familyRelationships.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of familyRelationships in body.
     */
    @GetMapping("/family-relationships")
    public List<FamilyRelationship> getAllFamilyRelationships() {
        log.debug("REST request to get all FamilyRelationships");
        return familyRelationshipService.findAll();
    }

    /**
     * {@code GET  /family-relationships/:id} : get the "id" familyRelationship.
     *
     * @param id the id of the familyRelationship to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the familyRelationship, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/family-relationships/{id}")
    public ResponseEntity<FamilyRelationship> getFamilyRelationship(@PathVariable Long id) {
        log.debug("REST request to get FamilyRelationship : {}", id);
        Optional<FamilyRelationship> familyRelationship = familyRelationshipService.findOne(id);
        return ResponseUtil.wrapOrNotFound(familyRelationship);
    }

    /**
     * {@code DELETE  /family-relationships/:id} : delete the "id" familyRelationship.
     *
     * @param id the id of the familyRelationship to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/family-relationships/{id}")
    public ResponseEntity<Void> deleteFamilyRelationship(@PathVariable Long id) {
        log.debug("REST request to delete FamilyRelationship : {}", id);
        familyRelationshipService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/family-relationships?query=:query} : search for the familyRelationship corresponding
     * to the query.
     *
     * @param query the query of the familyRelationship search.
     * @return the result of the search.
     */
    @GetMapping("/_search/family-relationships")
    public List<FamilyRelationship> searchFamilyRelationships(@RequestParam String query) {
        log.debug("REST request to search FamilyRelationships for query {}", query);
        return familyRelationshipService.search(query);
    }
}
