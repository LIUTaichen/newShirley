package com.dempseywood.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.dempseywood.domain.PrestartCheckResponse;
import com.dempseywood.service.PrestartCheckResponseService;
import com.dempseywood.web.rest.errors.BadRequestAlertException;
import com.dempseywood.web.rest.util.HeaderUtil;
import com.dempseywood.service.dto.PrestartCheckResponseCriteria;
import com.dempseywood.service.PrestartCheckResponseQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing PrestartCheckResponse.
 */
@RestController
@RequestMapping("/api")
public class PrestartCheckResponseResource {

    private final Logger log = LoggerFactory.getLogger(PrestartCheckResponseResource.class);

    private static final String ENTITY_NAME = "prestartCheckResponse";

    private final PrestartCheckResponseService prestartCheckResponseService;

    private final PrestartCheckResponseQueryService prestartCheckResponseQueryService;

    public PrestartCheckResponseResource(PrestartCheckResponseService prestartCheckResponseService, PrestartCheckResponseQueryService prestartCheckResponseQueryService) {
        this.prestartCheckResponseService = prestartCheckResponseService;
        this.prestartCheckResponseQueryService = prestartCheckResponseQueryService;
    }

    /**
     * POST  /prestart-check-responses : Create a new prestartCheckResponse.
     *
     * @param prestartCheckResponse the prestartCheckResponse to create
     * @return the ResponseEntity with status 201 (Created) and with body the new prestartCheckResponse, or with status 400 (Bad Request) if the prestartCheckResponse has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/prestart-check-responses")
    @Timed
    public ResponseEntity<PrestartCheckResponse> createPrestartCheckResponse(@RequestBody PrestartCheckResponse prestartCheckResponse) throws URISyntaxException {
        log.debug("REST request to save PrestartCheckResponse : {}", prestartCheckResponse);
        if (prestartCheckResponse.getId() != null) {
            throw new BadRequestAlertException("A new prestartCheckResponse cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PrestartCheckResponse result = prestartCheckResponseService.save(prestartCheckResponse);
        return ResponseEntity.created(new URI("/api/prestart-check-responses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /prestart-check-responses : Updates an existing prestartCheckResponse.
     *
     * @param prestartCheckResponse the prestartCheckResponse to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated prestartCheckResponse,
     * or with status 400 (Bad Request) if the prestartCheckResponse is not valid,
     * or with status 500 (Internal Server Error) if the prestartCheckResponse couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/prestart-check-responses")
    @Timed
    public ResponseEntity<PrestartCheckResponse> updatePrestartCheckResponse(@RequestBody PrestartCheckResponse prestartCheckResponse) throws URISyntaxException {
        log.debug("REST request to update PrestartCheckResponse : {}", prestartCheckResponse);
        if (prestartCheckResponse.getId() == null) {
            return createPrestartCheckResponse(prestartCheckResponse);
        }
        PrestartCheckResponse result = prestartCheckResponseService.save(prestartCheckResponse);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, prestartCheckResponse.getId().toString()))
            .body(result);
    }

    /**
     * GET  /prestart-check-responses : get all the prestartCheckResponses.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of prestartCheckResponses in body
     */
    @GetMapping("/prestart-check-responses")
    @Timed
    public ResponseEntity<List<PrestartCheckResponse>> getAllPrestartCheckResponses(PrestartCheckResponseCriteria criteria) {
        log.debug("REST request to get PrestartCheckResponses by criteria: {}", criteria);
        List<PrestartCheckResponse> entityList = prestartCheckResponseQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * GET  /prestart-check-responses/:id : get the "id" prestartCheckResponse.
     *
     * @param id the id of the prestartCheckResponse to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the prestartCheckResponse, or with status 404 (Not Found)
     */
    @GetMapping("/prestart-check-responses/{id}")
    @Timed
    public ResponseEntity<PrestartCheckResponse> getPrestartCheckResponse(@PathVariable Long id) {
        log.debug("REST request to get PrestartCheckResponse : {}", id);
        PrestartCheckResponse prestartCheckResponse = prestartCheckResponseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(prestartCheckResponse));
    }

    /**
     * DELETE  /prestart-check-responses/:id : delete the "id" prestartCheckResponse.
     *
     * @param id the id of the prestartCheckResponse to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/prestart-check-responses/{id}")
    @Timed
    public ResponseEntity<Void> deletePrestartCheckResponse(@PathVariable Long id) {
        log.debug("REST request to delete PrestartCheckResponse : {}", id);
        prestartCheckResponseService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
