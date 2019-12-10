package com.zelix.yikondi.web.rest;

import com.zelix.yikondi.domain.EmergencyAmbulance;
import com.zelix.yikondi.service.EmergencyAmbulanceService;
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
 * REST controller for managing {@link com.zelix.yikondi.domain.EmergencyAmbulance}.
 */
@RestController
@RequestMapping("/api")
public class EmergencyAmbulanceResource {

    private final Logger log = LoggerFactory.getLogger(EmergencyAmbulanceResource.class);

    private static final String ENTITY_NAME = "emergencyAmbulance";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmergencyAmbulanceService emergencyAmbulanceService;

    public EmergencyAmbulanceResource(EmergencyAmbulanceService emergencyAmbulanceService) {
        this.emergencyAmbulanceService = emergencyAmbulanceService;
    }

    /**
     * {@code POST  /emergency-ambulances} : Create a new emergencyAmbulance.
     *
     * @param emergencyAmbulance the emergencyAmbulance to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new emergencyAmbulance, or with status {@code 400 (Bad Request)} if the emergencyAmbulance has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/emergency-ambulances")
    public ResponseEntity<EmergencyAmbulance> createEmergencyAmbulance(@RequestBody EmergencyAmbulance emergencyAmbulance) throws URISyntaxException {
        log.debug("REST request to save EmergencyAmbulance : {}", emergencyAmbulance);
        if (emergencyAmbulance.getId() != null) {
            throw new BadRequestAlertException("A new emergencyAmbulance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmergencyAmbulance result = emergencyAmbulanceService.save(emergencyAmbulance);
        return ResponseEntity.created(new URI("/api/emergency-ambulances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /emergency-ambulances} : Updates an existing emergencyAmbulance.
     *
     * @param emergencyAmbulance the emergencyAmbulance to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated emergencyAmbulance,
     * or with status {@code 400 (Bad Request)} if the emergencyAmbulance is not valid,
     * or with status {@code 500 (Internal Server Error)} if the emergencyAmbulance couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/emergency-ambulances")
    public ResponseEntity<EmergencyAmbulance> updateEmergencyAmbulance(@RequestBody EmergencyAmbulance emergencyAmbulance) throws URISyntaxException {
        log.debug("REST request to update EmergencyAmbulance : {}", emergencyAmbulance);
        if (emergencyAmbulance.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EmergencyAmbulance result = emergencyAmbulanceService.save(emergencyAmbulance);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, emergencyAmbulance.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /emergency-ambulances} : get all the emergencyAmbulances.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of emergencyAmbulances in body.
     */
    @GetMapping("/emergency-ambulances")
    public List<EmergencyAmbulance> getAllEmergencyAmbulances() {
        log.debug("REST request to get all EmergencyAmbulances");
        return emergencyAmbulanceService.findAll();
    }

    /**
     * {@code GET  /emergency-ambulances/:id} : get the "id" emergencyAmbulance.
     *
     * @param id the id of the emergencyAmbulance to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the emergencyAmbulance, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/emergency-ambulances/{id}")
    public ResponseEntity<EmergencyAmbulance> getEmergencyAmbulance(@PathVariable Long id) {
        log.debug("REST request to get EmergencyAmbulance : {}", id);
        Optional<EmergencyAmbulance> emergencyAmbulance = emergencyAmbulanceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(emergencyAmbulance);
    }

    /**
     * {@code DELETE  /emergency-ambulances/:id} : delete the "id" emergencyAmbulance.
     *
     * @param id the id of the emergencyAmbulance to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/emergency-ambulances/{id}")
    public ResponseEntity<Void> deleteEmergencyAmbulance(@PathVariable Long id) {
        log.debug("REST request to delete EmergencyAmbulance : {}", id);
        emergencyAmbulanceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/emergency-ambulances?query=:query} : search for the emergencyAmbulance corresponding
     * to the query.
     *
     * @param query the query of the emergencyAmbulance search.
     * @return the result of the search.
     */
    @GetMapping("/_search/emergency-ambulances")
    public List<EmergencyAmbulance> searchEmergencyAmbulances(@RequestParam String query) {
        log.debug("REST request to search EmergencyAmbulances for query {}", query);
        return emergencyAmbulanceService.search(query);
    }
}
