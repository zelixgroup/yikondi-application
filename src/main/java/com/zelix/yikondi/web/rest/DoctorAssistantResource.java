package com.zelix.yikondi.web.rest;

import com.zelix.yikondi.domain.DoctorAssistant;
import com.zelix.yikondi.service.DoctorAssistantService;
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
 * REST controller for managing {@link com.zelix.yikondi.domain.DoctorAssistant}.
 */
@RestController
@RequestMapping("/api")
public class DoctorAssistantResource {

    private final Logger log = LoggerFactory.getLogger(DoctorAssistantResource.class);

    private static final String ENTITY_NAME = "doctorAssistant";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DoctorAssistantService doctorAssistantService;

    public DoctorAssistantResource(DoctorAssistantService doctorAssistantService) {
        this.doctorAssistantService = doctorAssistantService;
    }

    /**
     * {@code POST  /doctor-assistants} : Create a new doctorAssistant.
     *
     * @param doctorAssistant the doctorAssistant to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new doctorAssistant, or with status {@code 400 (Bad Request)} if the doctorAssistant has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/doctor-assistants")
    public ResponseEntity<DoctorAssistant> createDoctorAssistant(@RequestBody DoctorAssistant doctorAssistant) throws URISyntaxException {
        log.debug("REST request to save DoctorAssistant : {}", doctorAssistant);
        if (doctorAssistant.getId() != null) {
            throw new BadRequestAlertException("A new doctorAssistant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DoctorAssistant result = doctorAssistantService.save(doctorAssistant);
        return ResponseEntity.created(new URI("/api/doctor-assistants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /doctor-assistants} : Updates an existing doctorAssistant.
     *
     * @param doctorAssistant the doctorAssistant to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated doctorAssistant,
     * or with status {@code 400 (Bad Request)} if the doctorAssistant is not valid,
     * or with status {@code 500 (Internal Server Error)} if the doctorAssistant couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/doctor-assistants")
    public ResponseEntity<DoctorAssistant> updateDoctorAssistant(@RequestBody DoctorAssistant doctorAssistant) throws URISyntaxException {
        log.debug("REST request to update DoctorAssistant : {}", doctorAssistant);
        if (doctorAssistant.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DoctorAssistant result = doctorAssistantService.save(doctorAssistant);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, doctorAssistant.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /doctor-assistants} : get all the doctorAssistants.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of doctorAssistants in body.
     */
    @GetMapping("/doctor-assistants")
    public List<DoctorAssistant> getAllDoctorAssistants() {
        log.debug("REST request to get all DoctorAssistants");
        return doctorAssistantService.findAll();
    }

    /**
     * {@code GET  /doctor-assistants/:id} : get the "id" doctorAssistant.
     *
     * @param id the id of the doctorAssistant to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the doctorAssistant, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/doctor-assistants/{id}")
    public ResponseEntity<DoctorAssistant> getDoctorAssistant(@PathVariable Long id) {
        log.debug("REST request to get DoctorAssistant : {}", id);
        Optional<DoctorAssistant> doctorAssistant = doctorAssistantService.findOne(id);
        return ResponseUtil.wrapOrNotFound(doctorAssistant);
    }

    /**
     * {@code DELETE  /doctor-assistants/:id} : delete the "id" doctorAssistant.
     *
     * @param id the id of the doctorAssistant to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/doctor-assistants/{id}")
    public ResponseEntity<Void> deleteDoctorAssistant(@PathVariable Long id) {
        log.debug("REST request to delete DoctorAssistant : {}", id);
        doctorAssistantService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/doctor-assistants?query=:query} : search for the doctorAssistant corresponding
     * to the query.
     *
     * @param query the query of the doctorAssistant search.
     * @return the result of the search.
     */
    @GetMapping("/_search/doctor-assistants")
    public List<DoctorAssistant> searchDoctorAssistants(@RequestParam String query) {
        log.debug("REST request to search DoctorAssistants for query {}", query);
        return doctorAssistantService.search(query);
    }
}
