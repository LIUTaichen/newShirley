package com.dempseywood.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.dempseywood.domain.PrestartCheck;
import com.dempseywood.service.PrestartCheckService;
import com.dempseywood.web.rest.errors.BadRequestAlertException;
import com.dempseywood.web.rest.util.HeaderUtil;
import com.dempseywood.service.dto.PrestartCheckCriteria;
import com.dempseywood.service.PrestartCheckQueryService;
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
 * REST controller for managing PrestartCheck.
 */
@RestController
@RequestMapping("/api")
public class PrestartCheckResource {

    private final Logger log = LoggerFactory.getLogger(PrestartCheckResource.class);

    private static final String ENTITY_NAME = "prestartCheck";

    private final PrestartCheckService prestartCheckService;

    private final PrestartCheckQueryService prestartCheckQueryService;

    public PrestartCheckResource(PrestartCheckService prestartCheckService, PrestartCheckQueryService prestartCheckQueryService) {
        this.prestartCheckService = prestartCheckService;
        this.prestartCheckQueryService = prestartCheckQueryService;
    }

    /**
     * POST  /prestart-checks : Create a new prestartCheck.
     *
     * @param prestartCheck the prestartCheck to create
     * @return the ResponseEntity with status 201 (Created) and with body the new prestartCheck, or with status 400 (Bad Request) if the prestartCheck has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/prestart-checks")
    @Timed
    public ResponseEntity<PrestartCheck> createPrestartCheck(@RequestBody PrestartCheck prestartCheck) throws URISyntaxException {
        log.debug("REST request to save PrestartCheck : {}", prestartCheck);
        if (prestartCheck.getId() != null) {
            throw new BadRequestAlertException("A new prestartCheck cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PrestartCheck result = prestartCheckService.save(prestartCheck);
        return ResponseEntity.created(new URI("/api/prestart-checks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /prestart-checks : Updates an existing prestartCheck.
     *
     * @param prestartCheck the prestartCheck to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated prestartCheck,
     * or with status 400 (Bad Request) if the prestartCheck is not valid,
     * or with status 500 (Internal Server Error) if the prestartCheck couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/prestart-checks")
    @Timed
    public ResponseEntity<PrestartCheck> updatePrestartCheck(@RequestBody PrestartCheck prestartCheck) throws URISyntaxException {
        log.debug("REST request to update PrestartCheck : {}", prestartCheck);
        if (prestartCheck.getId() == null) {
            return createPrestartCheck(prestartCheck);
        }
        PrestartCheck result = prestartCheckService.save(prestartCheck);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, prestartCheck.getId().toString()))
            .body(result);
    }

    /**
     * GET  /prestart-checks : get all the prestartChecks.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of prestartChecks in body
     */
    @GetMapping("/prestart-checks")
    @Timed
    public ResponseEntity<List<PrestartCheck>> getAllPrestartChecks(PrestartCheckCriteria criteria) {
        log.debug("REST request to get PrestartChecks by criteria: {}", criteria);
        List<PrestartCheck> entityList = prestartCheckQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * GET  /prestart-checks/:id : get the "id" prestartCheck.
     *
     * @param id the id of the prestartCheck to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the prestartCheck, or with status 404 (Not Found)
     */
    @GetMapping("/prestart-checks/{id}")
    @Timed
    public ResponseEntity<PrestartCheck> getPrestartCheck(@PathVariable Long id) {
        log.debug("REST request to get PrestartCheck : {}", id);
        PrestartCheck prestartCheck = prestartCheckService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(prestartCheck));
    }

    /**
     * DELETE  /prestart-checks/:id : delete the "id" prestartCheck.
     *
     * @param id the id of the prestartCheck to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/prestart-checks/{id}")
    @Timed
    public ResponseEntity<Void> deletePrestartCheck(@PathVariable Long id) {
        log.debug("REST request to delete PrestartCheck : {}", id);
        prestartCheckService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
