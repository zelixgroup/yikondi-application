package com.zelix.yikondi.web.rest;

import com.zelix.yikondi.domain.LifeConstantUnit;
import com.zelix.yikondi.service.LifeConstantUnitService;
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
 * REST controller for managing {@link com.zelix.yikondi.domain.LifeConstantUnit}.
 */
@RestController
@RequestMapping("/api")
public class LifeConstantUnitResource {

    private final Logger log = LoggerFactory.getLogger(LifeConstantUnitResource.class);

    private static final String ENTITY_NAME = "lifeConstantUnit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LifeConstantUnitService lifeConstantUnitService;

    public LifeConstantUnitResource(LifeConstantUnitService lifeConstantUnitService) {
        this.lifeConstantUnitService = lifeConstantUnitService;
    }

    /**
     * {@code POST  /life-constant-units} : Create a new lifeConstantUnit.
     *
     * @param lifeConstantUnit the lifeConstantUnit to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new lifeConstantUnit, or with status {@code 400 (Bad Request)} if the lifeConstantUnit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/life-constant-units")
    public ResponseEntity<LifeConstantUnit> createLifeConstantUnit(@RequestBody LifeConstantUnit lifeConstantUnit) throws URISyntaxException {
        log.debug("REST request to save LifeConstantUnit : {}", lifeConstantUnit);
        if (lifeConstantUnit.getId() != null) {
            throw new BadRequestAlertException("A new lifeConstantUnit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LifeConstantUnit result = lifeConstantUnitService.save(lifeConstantUnit);
        return ResponseEntity.created(new URI("/api/life-constant-units/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /life-constant-units} : Updates an existing lifeConstantUnit.
     *
     * @param lifeConstantUnit the lifeConstantUnit to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lifeConstantUnit,
     * or with status {@code 400 (Bad Request)} if the lifeConstantUnit is not valid,
     * or with status {@code 500 (Internal Server Error)} if the lifeConstantUnit couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/life-constant-units")
    public ResponseEntity<LifeConstantUnit> updateLifeConstantUnit(@RequestBody LifeConstantUnit lifeConstantUnit) throws URISyntaxException {
        log.debug("REST request to update LifeConstantUnit : {}", lifeConstantUnit);
        if (lifeConstantUnit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LifeConstantUnit result = lifeConstantUnitService.save(lifeConstantUnit);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, lifeConstantUnit.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /life-constant-units} : get all the lifeConstantUnits.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of lifeConstantUnits in body.
     */
    @GetMapping("/life-constant-units")
    public List<LifeConstantUnit> getAllLifeConstantUnits() {
        log.debug("REST request to get all LifeConstantUnits");
        return lifeConstantUnitService.findAll();
    }

    /**
     * {@code GET  /life-constant-units/:id} : get the "id" lifeConstantUnit.
     *
     * @param id the id of the lifeConstantUnit to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the lifeConstantUnit, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/life-constant-units/{id}")
    public ResponseEntity<LifeConstantUnit> getLifeConstantUnit(@PathVariable Long id) {
        log.debug("REST request to get LifeConstantUnit : {}", id);
        Optional<LifeConstantUnit> lifeConstantUnit = lifeConstantUnitService.findOne(id);
        return ResponseUtil.wrapOrNotFound(lifeConstantUnit);
    }

    /**
     * {@code DELETE  /life-constant-units/:id} : delete the "id" lifeConstantUnit.
     *
     * @param id the id of the lifeConstantUnit to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/life-constant-units/{id}")
    public ResponseEntity<Void> deleteLifeConstantUnit(@PathVariable Long id) {
        log.debug("REST request to delete LifeConstantUnit : {}", id);
        lifeConstantUnitService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/life-constant-units?query=:query} : search for the lifeConstantUnit corresponding
     * to the query.
     *
     * @param query the query of the lifeConstantUnit search.
     * @return the result of the search.
     */
    @GetMapping("/_search/life-constant-units")
    public List<LifeConstantUnit> searchLifeConstantUnits(@RequestParam String query) {
        log.debug("REST request to search LifeConstantUnits for query {}", query);
        return lifeConstantUnitService.search(query);
    }
}
