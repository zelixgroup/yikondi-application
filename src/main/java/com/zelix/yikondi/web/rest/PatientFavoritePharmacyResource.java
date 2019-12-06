package com.zelix.yikondi.web.rest;

import com.zelix.yikondi.domain.PatientFavoritePharmacy;
import com.zelix.yikondi.repository.PatientFavoritePharmacyRepository;
import com.zelix.yikondi.repository.search.PatientFavoritePharmacySearchRepository;
import com.zelix.yikondi.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional; 
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.zelix.yikondi.domain.PatientFavoritePharmacy}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PatientFavoritePharmacyResource {

    private final Logger log = LoggerFactory.getLogger(PatientFavoritePharmacyResource.class);

    private static final String ENTITY_NAME = "patientFavoritePharmacy";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PatientFavoritePharmacyRepository patientFavoritePharmacyRepository;

    private final PatientFavoritePharmacySearchRepository patientFavoritePharmacySearchRepository;

    public PatientFavoritePharmacyResource(PatientFavoritePharmacyRepository patientFavoritePharmacyRepository, PatientFavoritePharmacySearchRepository patientFavoritePharmacySearchRepository) {
        this.patientFavoritePharmacyRepository = patientFavoritePharmacyRepository;
        this.patientFavoritePharmacySearchRepository = patientFavoritePharmacySearchRepository;
    }

    /**
     * {@code POST  /patient-favorite-pharmacies} : Create a new patientFavoritePharmacy.
     *
     * @param patientFavoritePharmacy the patientFavoritePharmacy to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new patientFavoritePharmacy, or with status {@code 400 (Bad Request)} if the patientFavoritePharmacy has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/patient-favorite-pharmacies")
    public ResponseEntity<PatientFavoritePharmacy> createPatientFavoritePharmacy(@RequestBody PatientFavoritePharmacy patientFavoritePharmacy) throws URISyntaxException {
        log.debug("REST request to save PatientFavoritePharmacy : {}", patientFavoritePharmacy);
        if (patientFavoritePharmacy.getId() != null) {
            throw new BadRequestAlertException("A new patientFavoritePharmacy cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PatientFavoritePharmacy result = patientFavoritePharmacyRepository.save(patientFavoritePharmacy);
        patientFavoritePharmacySearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/patient-favorite-pharmacies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /patient-favorite-pharmacies} : Updates an existing patientFavoritePharmacy.
     *
     * @param patientFavoritePharmacy the patientFavoritePharmacy to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated patientFavoritePharmacy,
     * or with status {@code 400 (Bad Request)} if the patientFavoritePharmacy is not valid,
     * or with status {@code 500 (Internal Server Error)} if the patientFavoritePharmacy couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/patient-favorite-pharmacies")
    public ResponseEntity<PatientFavoritePharmacy> updatePatientFavoritePharmacy(@RequestBody PatientFavoritePharmacy patientFavoritePharmacy) throws URISyntaxException {
        log.debug("REST request to update PatientFavoritePharmacy : {}", patientFavoritePharmacy);
        if (patientFavoritePharmacy.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PatientFavoritePharmacy result = patientFavoritePharmacyRepository.save(patientFavoritePharmacy);
        patientFavoritePharmacySearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, patientFavoritePharmacy.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /patient-favorite-pharmacies} : get all the patientFavoritePharmacies.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of patientFavoritePharmacies in body.
     */
    @GetMapping("/patient-favorite-pharmacies")
    public List<PatientFavoritePharmacy> getAllPatientFavoritePharmacies() {
        log.debug("REST request to get all PatientFavoritePharmacies");
        return patientFavoritePharmacyRepository.findAll();
    }

    /**
     * {@code GET  /patient-favorite-pharmacies/:id} : get the "id" patientFavoritePharmacy.
     *
     * @param id the id of the patientFavoritePharmacy to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the patientFavoritePharmacy, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/patient-favorite-pharmacies/{id}")
    public ResponseEntity<PatientFavoritePharmacy> getPatientFavoritePharmacy(@PathVariable Long id) {
        log.debug("REST request to get PatientFavoritePharmacy : {}", id);
        Optional<PatientFavoritePharmacy> patientFavoritePharmacy = patientFavoritePharmacyRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(patientFavoritePharmacy);
    }

    /**
     * {@code DELETE  /patient-favorite-pharmacies/:id} : delete the "id" patientFavoritePharmacy.
     *
     * @param id the id of the patientFavoritePharmacy to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/patient-favorite-pharmacies/{id}")
    public ResponseEntity<Void> deletePatientFavoritePharmacy(@PathVariable Long id) {
        log.debug("REST request to delete PatientFavoritePharmacy : {}", id);
        patientFavoritePharmacyRepository.deleteById(id);
        patientFavoritePharmacySearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/patient-favorite-pharmacies?query=:query} : search for the patientFavoritePharmacy corresponding
     * to the query.
     *
     * @param query the query of the patientFavoritePharmacy search.
     * @return the result of the search.
     */
    @GetMapping("/_search/patient-favorite-pharmacies")
    public List<PatientFavoritePharmacy> searchPatientFavoritePharmacies(@RequestParam String query) {
        log.debug("REST request to search PatientFavoritePharmacies for query {}", query);
        return StreamSupport
            .stream(patientFavoritePharmacySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
