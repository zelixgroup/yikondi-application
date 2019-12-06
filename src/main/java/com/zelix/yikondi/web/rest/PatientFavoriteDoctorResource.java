package com.zelix.yikondi.web.rest;

import com.zelix.yikondi.domain.PatientFavoriteDoctor;
import com.zelix.yikondi.service.PatientFavoriteDoctorService;
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
 * REST controller for managing {@link com.zelix.yikondi.domain.PatientFavoriteDoctor}.
 */
@RestController
@RequestMapping("/api")
public class PatientFavoriteDoctorResource {

    private final Logger log = LoggerFactory.getLogger(PatientFavoriteDoctorResource.class);

    private static final String ENTITY_NAME = "patientFavoriteDoctor";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PatientFavoriteDoctorService patientFavoriteDoctorService;

    public PatientFavoriteDoctorResource(PatientFavoriteDoctorService patientFavoriteDoctorService) {
        this.patientFavoriteDoctorService = patientFavoriteDoctorService;
    }

    /**
     * {@code POST  /patient-favorite-doctors} : Create a new patientFavoriteDoctor.
     *
     * @param patientFavoriteDoctor the patientFavoriteDoctor to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new patientFavoriteDoctor, or with status {@code 400 (Bad Request)} if the patientFavoriteDoctor has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/patient-favorite-doctors")
    public ResponseEntity<PatientFavoriteDoctor> createPatientFavoriteDoctor(@RequestBody PatientFavoriteDoctor patientFavoriteDoctor) throws URISyntaxException {
        log.debug("REST request to save PatientFavoriteDoctor : {}", patientFavoriteDoctor);
        if (patientFavoriteDoctor.getId() != null) {
            throw new BadRequestAlertException("A new patientFavoriteDoctor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PatientFavoriteDoctor result = patientFavoriteDoctorService.save(patientFavoriteDoctor);
        return ResponseEntity.created(new URI("/api/patient-favorite-doctors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /patient-favorite-doctors} : Updates an existing patientFavoriteDoctor.
     *
     * @param patientFavoriteDoctor the patientFavoriteDoctor to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated patientFavoriteDoctor,
     * or with status {@code 400 (Bad Request)} if the patientFavoriteDoctor is not valid,
     * or with status {@code 500 (Internal Server Error)} if the patientFavoriteDoctor couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/patient-favorite-doctors")
    public ResponseEntity<PatientFavoriteDoctor> updatePatientFavoriteDoctor(@RequestBody PatientFavoriteDoctor patientFavoriteDoctor) throws URISyntaxException {
        log.debug("REST request to update PatientFavoriteDoctor : {}", patientFavoriteDoctor);
        if (patientFavoriteDoctor.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PatientFavoriteDoctor result = patientFavoriteDoctorService.save(patientFavoriteDoctor);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, patientFavoriteDoctor.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /patient-favorite-doctors} : get all the patientFavoriteDoctors.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of patientFavoriteDoctors in body.
     */
    @GetMapping("/patient-favorite-doctors")
    public List<PatientFavoriteDoctor> getAllPatientFavoriteDoctors() {
        log.debug("REST request to get all PatientFavoriteDoctors");
        return patientFavoriteDoctorService.findAll();
    }

    /**
     * {@code GET  /patient-favorite-doctors/:id} : get the "id" patientFavoriteDoctor.
     *
     * @param id the id of the patientFavoriteDoctor to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the patientFavoriteDoctor, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/patient-favorite-doctors/{id}")
    public ResponseEntity<PatientFavoriteDoctor> getPatientFavoriteDoctor(@PathVariable Long id) {
        log.debug("REST request to get PatientFavoriteDoctor : {}", id);
        Optional<PatientFavoriteDoctor> patientFavoriteDoctor = patientFavoriteDoctorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(patientFavoriteDoctor);
    }

    /**
     * {@code DELETE  /patient-favorite-doctors/:id} : delete the "id" patientFavoriteDoctor.
     *
     * @param id the id of the patientFavoriteDoctor to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/patient-favorite-doctors/{id}")
    public ResponseEntity<Void> deletePatientFavoriteDoctor(@PathVariable Long id) {
        log.debug("REST request to delete PatientFavoriteDoctor : {}", id);
        patientFavoriteDoctorService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/patient-favorite-doctors?query=:query} : search for the patientFavoriteDoctor corresponding
     * to the query.
     *
     * @param query the query of the patientFavoriteDoctor search.
     * @return the result of the search.
     */
    @GetMapping("/_search/patient-favorite-doctors")
    public List<PatientFavoriteDoctor> searchPatientFavoriteDoctors(@RequestParam String query) {
        log.debug("REST request to search PatientFavoriteDoctors for query {}", query);
        return patientFavoriteDoctorService.search(query);
    }
}
