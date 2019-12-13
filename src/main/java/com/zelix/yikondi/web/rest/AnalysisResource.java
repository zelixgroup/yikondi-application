package com.zelix.yikondi.web.rest;

import com.zelix.yikondi.domain.Analysis;
import com.zelix.yikondi.service.AnalysisService;
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
 * REST controller for managing {@link com.zelix.yikondi.domain.Analysis}.
 */
@RestController
@RequestMapping("/api")
public class AnalysisResource {

    private final Logger log = LoggerFactory.getLogger(AnalysisResource.class);

    private static final String ENTITY_NAME = "analysis";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AnalysisService analysisService;

    public AnalysisResource(AnalysisService analysisService) {
        this.analysisService = analysisService;
    }

    /**
     * {@code POST  /analyses} : Create a new analysis.
     *
     * @param analysis the analysis to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new analysis, or with status {@code 400 (Bad Request)} if the analysis has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/analyses")
    public ResponseEntity<Analysis> createAnalysis(@RequestBody Analysis analysis) throws URISyntaxException {
        log.debug("REST request to save Analysis : {}", analysis);
        if (analysis.getId() != null) {
            throw new BadRequestAlertException("A new analysis cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Analysis result = analysisService.save(analysis);
        return ResponseEntity.created(new URI("/api/analyses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /analyses} : Updates an existing analysis.
     *
     * @param analysis the analysis to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated analysis,
     * or with status {@code 400 (Bad Request)} if the analysis is not valid,
     * or with status {@code 500 (Internal Server Error)} if the analysis couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/analyses")
    public ResponseEntity<Analysis> updateAnalysis(@RequestBody Analysis analysis) throws URISyntaxException {
        log.debug("REST request to update Analysis : {}", analysis);
        if (analysis.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Analysis result = analysisService.save(analysis);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, analysis.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /analyses} : get all the analyses.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of analyses in body.
     */
    @GetMapping("/analyses")
    public List<Analysis> getAllAnalyses() {
        log.debug("REST request to get all Analyses");
        return analysisService.findAll();
    }

    /**
     * {@code GET  /analyses/:id} : get the "id" analysis.
     *
     * @param id the id of the analysis to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the analysis, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/analyses/{id}")
    public ResponseEntity<Analysis> getAnalysis(@PathVariable Long id) {
        log.debug("REST request to get Analysis : {}", id);
        Optional<Analysis> analysis = analysisService.findOne(id);
        return ResponseUtil.wrapOrNotFound(analysis);
    }

    /**
     * {@code DELETE  /analyses/:id} : delete the "id" analysis.
     *
     * @param id the id of the analysis to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/analyses/{id}")
    public ResponseEntity<Void> deleteAnalysis(@PathVariable Long id) {
        log.debug("REST request to delete Analysis : {}", id);
        analysisService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/analyses?query=:query} : search for the analysis corresponding
     * to the query.
     *
     * @param query the query of the analysis search.
     * @return the result of the search.
     */
    @GetMapping("/_search/analyses")
    public List<Analysis> searchAnalyses(@RequestParam String query) {
        log.debug("REST request to search Analyses for query {}", query);
        return analysisService.search(query);
    }
}
