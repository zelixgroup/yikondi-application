package com.zelix.yikondi.web.rest;

import com.zelix.yikondi.domain.Pharmacy;
import com.zelix.yikondi.repository.PharmacyRepository;
import com.zelix.yikondi.repository.search.PharmacySearchRepository;
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
 * REST controller for managing {@link com.zelix.yikondi.domain.Pharmacy}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PharmacyResource {

    private final Logger log = LoggerFactory.getLogger(PharmacyResource.class);

    private static final String ENTITY_NAME = "pharmacy";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PharmacyRepository pharmacyRepository;

    private final PharmacySearchRepository pharmacySearchRepository;

    public PharmacyResource(PharmacyRepository pharmacyRepository, PharmacySearchRepository pharmacySearchRepository) {
        this.pharmacyRepository = pharmacyRepository;
        this.pharmacySearchRepository = pharmacySearchRepository;
    }

    /**
     * {@code POST  /pharmacies} : Create a new pharmacy.
     *
     * @param pharmacy the pharmacy to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pharmacy, or with status {@code 400 (Bad Request)} if the pharmacy has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pharmacies")
    public ResponseEntity<Pharmacy> createPharmacy(@RequestBody Pharmacy pharmacy) throws URISyntaxException {
        log.debug("REST request to save Pharmacy : {}", pharmacy);
        if (pharmacy.getId() != null) {
            throw new BadRequestAlertException("A new pharmacy cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Pharmacy result = pharmacyRepository.save(pharmacy);
        pharmacySearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/pharmacies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pharmacies} : Updates an existing pharmacy.
     *
     * @param pharmacy the pharmacy to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pharmacy,
     * or with status {@code 400 (Bad Request)} if the pharmacy is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pharmacy couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pharmacies")
    public ResponseEntity<Pharmacy> updatePharmacy(@RequestBody Pharmacy pharmacy) throws URISyntaxException {
        log.debug("REST request to update Pharmacy : {}", pharmacy);
        if (pharmacy.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Pharmacy result = pharmacyRepository.save(pharmacy);
        pharmacySearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pharmacy.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pharmacies} : get all the pharmacies.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pharmacies in body.
     */
    @GetMapping("/pharmacies")
    public List<Pharmacy> getAllPharmacies() {
        log.debug("REST request to get all Pharmacies");
        return pharmacyRepository.findAll();
    }

    /**
     * {@code GET  /pharmacies/:id} : get the "id" pharmacy.
     *
     * @param id the id of the pharmacy to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pharmacy, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pharmacies/{id}")
    public ResponseEntity<Pharmacy> getPharmacy(@PathVariable Long id) {
        log.debug("REST request to get Pharmacy : {}", id);
        Optional<Pharmacy> pharmacy = pharmacyRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(pharmacy);
    }

    /**
     * {@code DELETE  /pharmacies/:id} : delete the "id" pharmacy.
     *
     * @param id the id of the pharmacy to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pharmacies/{id}")
    public ResponseEntity<Void> deletePharmacy(@PathVariable Long id) {
        log.debug("REST request to delete Pharmacy : {}", id);
        pharmacyRepository.deleteById(id);
        pharmacySearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/pharmacies?query=:query} : search for the pharmacy corresponding
     * to the query.
     *
     * @param query the query of the pharmacy search.
     * @return the result of the search.
     */
    @GetMapping("/_search/pharmacies")
    public List<Pharmacy> searchPharmacies(@RequestParam String query) {
        log.debug("REST request to search Pharmacies for query {}", query);
        return StreamSupport
            .stream(pharmacySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
