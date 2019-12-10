package com.zelix.yikondi.web.rest;

import com.zelix.yikondi.domain.Allergy;
import com.zelix.yikondi.service.AllergyService;
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
 * REST controller for managing {@link com.zelix.yikondi.domain.Allergy}.
 */
@RestController
@RequestMapping("/api")
public class AllergyResource {

    private final Logger log = LoggerFactory.getLogger(AllergyResource.class);

    private static final String ENTITY_NAME = "allergy";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AllergyService allergyService;

    public AllergyResource(AllergyService allergyService) {
        this.allergyService = allergyService;
    }

    /**
     * {@code POST  /allergies} : Create a new allergy.
     *
     * @param allergy the allergy to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new allergy, or with status {@code 400 (Bad Request)} if the allergy has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/allergies")
    public ResponseEntity<Allergy> createAllergy(@RequestBody Allergy allergy) throws URISyntaxException {
        log.debug("REST request to save Allergy : {}", allergy);
        if (allergy.getId() != null) {
            throw new BadRequestAlertException("A new allergy cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Allergy result = allergyService.save(allergy);
        return ResponseEntity.created(new URI("/api/allergies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /allergies} : Updates an existing allergy.
     *
     * @param allergy the allergy to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated allergy,
     * or with status {@code 400 (Bad Request)} if the allergy is not valid,
     * or with status {@code 500 (Internal Server Error)} if the allergy couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/allergies")
    public ResponseEntity<Allergy> updateAllergy(@RequestBody Allergy allergy) throws URISyntaxException {
        log.debug("REST request to update Allergy : {}", allergy);
        if (allergy.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Allergy result = allergyService.save(allergy);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, allergy.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /allergies} : get all the allergies.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of allergies in body.
     */
    @GetMapping("/allergies")
    public List<Allergy> getAllAllergies() {
        log.debug("REST request to get all Allergies");
        return allergyService.findAll();
    }

    /**
     * {@code GET  /allergies/:id} : get the "id" allergy.
     *
     * @param id the id of the allergy to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the allergy, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/allergies/{id}")
    public ResponseEntity<Allergy> getAllergy(@PathVariable Long id) {
        log.debug("REST request to get Allergy : {}", id);
        Optional<Allergy> allergy = allergyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(allergy);
    }

    /**
     * {@code DELETE  /allergies/:id} : delete the "id" allergy.
     *
     * @param id the id of the allergy to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/allergies/{id}")
    public ResponseEntity<Void> deleteAllergy(@PathVariable Long id) {
        log.debug("REST request to delete Allergy : {}", id);
        allergyService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/allergies?query=:query} : search for the allergy corresponding
     * to the query.
     *
     * @param query the query of the allergy search.
     * @return the result of the search.
     */
    @GetMapping("/_search/allergies")
    public List<Allergy> searchAllergies(@RequestParam String query) {
        log.debug("REST request to search Allergies for query {}", query);
        return allergyService.search(query);
    }
}
