package com.zelix.yikondi.web.rest;

import com.zelix.yikondi.domain.InsuranceType;
import com.zelix.yikondi.service.InsuranceTypeService;
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
 * REST controller for managing {@link com.zelix.yikondi.domain.InsuranceType}.
 */
@RestController
@RequestMapping("/api")
public class InsuranceTypeResource {

    private final Logger log = LoggerFactory.getLogger(InsuranceTypeResource.class);

    private static final String ENTITY_NAME = "insuranceType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InsuranceTypeService insuranceTypeService;

    public InsuranceTypeResource(InsuranceTypeService insuranceTypeService) {
        this.insuranceTypeService = insuranceTypeService;
    }

    /**
     * {@code POST  /insurance-types} : Create a new insuranceType.
     *
     * @param insuranceType the insuranceType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new insuranceType, or with status {@code 400 (Bad Request)} if the insuranceType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/insurance-types")
    public ResponseEntity<InsuranceType> createInsuranceType(@RequestBody InsuranceType insuranceType) throws URISyntaxException {
        log.debug("REST request to save InsuranceType : {}", insuranceType);
        if (insuranceType.getId() != null) {
            throw new BadRequestAlertException("A new insuranceType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InsuranceType result = insuranceTypeService.save(insuranceType);
        return ResponseEntity.created(new URI("/api/insurance-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /insurance-types} : Updates an existing insuranceType.
     *
     * @param insuranceType the insuranceType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated insuranceType,
     * or with status {@code 400 (Bad Request)} if the insuranceType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the insuranceType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/insurance-types")
    public ResponseEntity<InsuranceType> updateInsuranceType(@RequestBody InsuranceType insuranceType) throws URISyntaxException {
        log.debug("REST request to update InsuranceType : {}", insuranceType);
        if (insuranceType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InsuranceType result = insuranceTypeService.save(insuranceType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, insuranceType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /insurance-types} : get all the insuranceTypes.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of insuranceTypes in body.
     */
    @GetMapping("/insurance-types")
    public List<InsuranceType> getAllInsuranceTypes() {
        log.debug("REST request to get all InsuranceTypes");
        return insuranceTypeService.findAll();
    }

    /**
     * {@code GET  /insurance-types/:id} : get the "id" insuranceType.
     *
     * @param id the id of the insuranceType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the insuranceType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/insurance-types/{id}")
    public ResponseEntity<InsuranceType> getInsuranceType(@PathVariable Long id) {
        log.debug("REST request to get InsuranceType : {}", id);
        Optional<InsuranceType> insuranceType = insuranceTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(insuranceType);
    }

    /**
     * {@code DELETE  /insurance-types/:id} : delete the "id" insuranceType.
     *
     * @param id the id of the insuranceType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/insurance-types/{id}")
    public ResponseEntity<Void> deleteInsuranceType(@PathVariable Long id) {
        log.debug("REST request to delete InsuranceType : {}", id);
        insuranceTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/insurance-types?query=:query} : search for the insuranceType corresponding
     * to the query.
     *
     * @param query the query of the insuranceType search.
     * @return the result of the search.
     */
    @GetMapping("/_search/insurance-types")
    public List<InsuranceType> searchInsuranceTypes(@RequestParam String query) {
        log.debug("REST request to search InsuranceTypes for query {}", query);
        return insuranceTypeService.search(query);
    }
}
