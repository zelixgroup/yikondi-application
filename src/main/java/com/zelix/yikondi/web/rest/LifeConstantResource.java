package com.zelix.yikondi.web.rest;

import com.zelix.yikondi.domain.LifeConstant;
import com.zelix.yikondi.service.LifeConstantService;
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
 * REST controller for managing {@link com.zelix.yikondi.domain.LifeConstant}.
 */
@RestController
@RequestMapping("/api")
public class LifeConstantResource {

    private final Logger log = LoggerFactory.getLogger(LifeConstantResource.class);

    private static final String ENTITY_NAME = "lifeConstant";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LifeConstantService lifeConstantService;

    public LifeConstantResource(LifeConstantService lifeConstantService) {
        this.lifeConstantService = lifeConstantService;
    }

    /**
     * {@code POST  /life-constants} : Create a new lifeConstant.
     *
     * @param lifeConstant the lifeConstant to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new lifeConstant, or with status {@code 400 (Bad Request)} if the lifeConstant has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/life-constants")
    public ResponseEntity<LifeConstant> createLifeConstant(@RequestBody LifeConstant lifeConstant) throws URISyntaxException {
        log.debug("REST request to save LifeConstant : {}", lifeConstant);
        if (lifeConstant.getId() != null) {
            throw new BadRequestAlertException("A new lifeConstant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LifeConstant result = lifeConstantService.save(lifeConstant);
        return ResponseEntity.created(new URI("/api/life-constants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /life-constants} : Updates an existing lifeConstant.
     *
     * @param lifeConstant the lifeConstant to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lifeConstant,
     * or with status {@code 400 (Bad Request)} if the lifeConstant is not valid,
     * or with status {@code 500 (Internal Server Error)} if the lifeConstant couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/life-constants")
    public ResponseEntity<LifeConstant> updateLifeConstant(@RequestBody LifeConstant lifeConstant) throws URISyntaxException {
        log.debug("REST request to update LifeConstant : {}", lifeConstant);
        if (lifeConstant.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LifeConstant result = lifeConstantService.save(lifeConstant);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, lifeConstant.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /life-constants} : get all the lifeConstants.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of lifeConstants in body.
     */
    @GetMapping("/life-constants")
    public List<LifeConstant> getAllLifeConstants() {
        log.debug("REST request to get all LifeConstants");
        return lifeConstantService.findAll();
    }

    /**
     * {@code GET  /life-constants/:id} : get the "id" lifeConstant.
     *
     * @param id the id of the lifeConstant to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the lifeConstant, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/life-constants/{id}")
    public ResponseEntity<LifeConstant> getLifeConstant(@PathVariable Long id) {
        log.debug("REST request to get LifeConstant : {}", id);
        Optional<LifeConstant> lifeConstant = lifeConstantService.findOne(id);
        return ResponseUtil.wrapOrNotFound(lifeConstant);
    }

    /**
     * {@code DELETE  /life-constants/:id} : delete the "id" lifeConstant.
     *
     * @param id the id of the lifeConstant to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/life-constants/{id}")
    public ResponseEntity<Void> deleteLifeConstant(@PathVariable Long id) {
        log.debug("REST request to delete LifeConstant : {}", id);
        lifeConstantService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/life-constants?query=:query} : search for the lifeConstant corresponding
     * to the query.
     *
     * @param query the query of the lifeConstant search.
     * @return the result of the search.
     */
    @GetMapping("/_search/life-constants")
    public List<LifeConstant> searchLifeConstants(@RequestParam String query) {
        log.debug("REST request to search LifeConstants for query {}", query);
        return lifeConstantService.search(query);
    }
}
