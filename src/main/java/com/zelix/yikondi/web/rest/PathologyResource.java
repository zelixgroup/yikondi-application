package com.zelix.yikondi.web.rest;

import com.zelix.yikondi.domain.Pathology;
import com.zelix.yikondi.service.PathologyService;
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
 * REST controller for managing {@link com.zelix.yikondi.domain.Pathology}.
 */
@RestController
@RequestMapping("/api")
public class PathologyResource {

    private final Logger log = LoggerFactory.getLogger(PathologyResource.class);

    private static final String ENTITY_NAME = "pathology";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PathologyService pathologyService;

    public PathologyResource(PathologyService pathologyService) {
        this.pathologyService = pathologyService;
    }

    /**
     * {@code POST  /pathologies} : Create a new pathology.
     *
     * @param pathology the pathology to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pathology, or with status {@code 400 (Bad Request)} if the pathology has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pathologies")
    public ResponseEntity<Pathology> createPathology(@RequestBody Pathology pathology) throws URISyntaxException {
        log.debug("REST request to save Pathology : {}", pathology);
        if (pathology.getId() != null) {
            throw new BadRequestAlertException("A new pathology cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Pathology result = pathologyService.save(pathology);
        return ResponseEntity.created(new URI("/api/pathologies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pathologies} : Updates an existing pathology.
     *
     * @param pathology the pathology to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pathology,
     * or with status {@code 400 (Bad Request)} if the pathology is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pathology couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pathologies")
    public ResponseEntity<Pathology> updatePathology(@RequestBody Pathology pathology) throws URISyntaxException {
        log.debug("REST request to update Pathology : {}", pathology);
        if (pathology.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Pathology result = pathologyService.save(pathology);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pathology.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pathologies} : get all the pathologies.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pathologies in body.
     */
    @GetMapping("/pathologies")
    public List<Pathology> getAllPathologies() {
        log.debug("REST request to get all Pathologies");
        return pathologyService.findAll();
    }

    /**
     * {@code GET  /pathologies/:id} : get the "id" pathology.
     *
     * @param id the id of the pathology to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pathology, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pathologies/{id}")
    public ResponseEntity<Pathology> getPathology(@PathVariable Long id) {
        log.debug("REST request to get Pathology : {}", id);
        Optional<Pathology> pathology = pathologyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pathology);
    }

    /**
     * {@code DELETE  /pathologies/:id} : delete the "id" pathology.
     *
     * @param id the id of the pathology to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pathologies/{id}")
    public ResponseEntity<Void> deletePathology(@PathVariable Long id) {
        log.debug("REST request to delete Pathology : {}", id);
        pathologyService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/pathologies?query=:query} : search for the pathology corresponding
     * to the query.
     *
     * @param query the query of the pathology search.
     * @return the result of the search.
     */
    @GetMapping("/_search/pathologies")
    public List<Pathology> searchPathologies(@RequestParam String query) {
        log.debug("REST request to search Pathologies for query {}", query);
        return pathologyService.search(query);
    }
}
