package com.zelix.yikondi.web.rest;

import com.zelix.yikondi.domain.FamilyMember;
import com.zelix.yikondi.service.FamilyMemberService;
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
 * REST controller for managing {@link com.zelix.yikondi.domain.FamilyMember}.
 */
@RestController
@RequestMapping("/api")
public class FamilyMemberResource {

    private final Logger log = LoggerFactory.getLogger(FamilyMemberResource.class);

    private static final String ENTITY_NAME = "familyMember";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FamilyMemberService familyMemberService;

    public FamilyMemberResource(FamilyMemberService familyMemberService) {
        this.familyMemberService = familyMemberService;
    }

    /**
     * {@code POST  /family-members} : Create a new familyMember.
     *
     * @param familyMember the familyMember to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new familyMember, or with status {@code 400 (Bad Request)} if the familyMember has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/family-members")
    public ResponseEntity<FamilyMember> createFamilyMember(@RequestBody FamilyMember familyMember) throws URISyntaxException {
        log.debug("REST request to save FamilyMember : {}", familyMember);
        if (familyMember.getId() != null) {
            throw new BadRequestAlertException("A new familyMember cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FamilyMember result = familyMemberService.save(familyMember);
        return ResponseEntity.created(new URI("/api/family-members/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /family-members} : Updates an existing familyMember.
     *
     * @param familyMember the familyMember to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated familyMember,
     * or with status {@code 400 (Bad Request)} if the familyMember is not valid,
     * or with status {@code 500 (Internal Server Error)} if the familyMember couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/family-members")
    public ResponseEntity<FamilyMember> updateFamilyMember(@RequestBody FamilyMember familyMember) throws URISyntaxException {
        log.debug("REST request to update FamilyMember : {}", familyMember);
        if (familyMember.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FamilyMember result = familyMemberService.save(familyMember);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, familyMember.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /family-members} : get all the familyMembers.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of familyMembers in body.
     */
    @GetMapping("/family-members")
    public List<FamilyMember> getAllFamilyMembers() {
        log.debug("REST request to get all FamilyMembers");
        return familyMemberService.findAll();
    }

    /**
     * {@code GET  /family-members/:id} : get the "id" familyMember.
     *
     * @param id the id of the familyMember to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the familyMember, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/family-members/{id}")
    public ResponseEntity<FamilyMember> getFamilyMember(@PathVariable Long id) {
        log.debug("REST request to get FamilyMember : {}", id);
        Optional<FamilyMember> familyMember = familyMemberService.findOne(id);
        return ResponseUtil.wrapOrNotFound(familyMember);
    }

    /**
     * {@code DELETE  /family-members/:id} : delete the "id" familyMember.
     *
     * @param id the id of the familyMember to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/family-members/{id}")
    public ResponseEntity<Void> deleteFamilyMember(@PathVariable Long id) {
        log.debug("REST request to delete FamilyMember : {}", id);
        familyMemberService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/family-members?query=:query} : search for the familyMember corresponding
     * to the query.
     *
     * @param query the query of the familyMember search.
     * @return the result of the search.
     */
    @GetMapping("/_search/family-members")
    public List<FamilyMember> searchFamilyMembers(@RequestParam String query) {
        log.debug("REST request to search FamilyMembers for query {}", query);
        return familyMemberService.search(query);
    }
}
