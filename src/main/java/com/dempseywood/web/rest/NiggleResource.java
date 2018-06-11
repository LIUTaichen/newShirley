package com.dempseywood.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.dempseywood.domain.Niggle;
import com.dempseywood.service.NiggleService;
import com.dempseywood.web.rest.errors.BadRequestAlertException;
import com.dempseywood.web.rest.util.HeaderUtil;
import com.dempseywood.service.dto.NiggleCriteria;
import com.dempseywood.service.NiggleQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Niggle.
 */
@RestController
@RequestMapping("/api")
public class NiggleResource {

    private final Logger log = LoggerFactory.getLogger(NiggleResource.class);

    private static final String ENTITY_NAME = "niggle";

    private final NiggleService niggleService;

    private final NiggleQueryService niggleQueryService;

    public NiggleResource(NiggleService niggleService, NiggleQueryService niggleQueryService) {
        this.niggleService = niggleService;
        this.niggleQueryService = niggleQueryService;
    }

    /**
     * POST  /niggles : Create a new niggle.
     *
     * @param niggle the niggle to create
     * @return the ResponseEntity with status 201 (Created) and with body the new niggle, or with status 400 (Bad Request) if the niggle has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/niggles")
    @Timed
    @Secured({"ROLE_ADMIN", "ROLE_USER", "ROLE_DW"})
    public ResponseEntity<Niggle> createNiggle(@RequestBody Niggle niggle) throws URISyntaxException {
        log.debug("REST request to save Niggle : {}", niggle);
        if (niggle.getId() != null) {
            throw new BadRequestAlertException("A new niggle cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Niggle result = niggleService.save(niggle);
        return ResponseEntity.created(new URI("/api/niggles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /niggles : Updates an existing niggle.
     *
     * @param niggle the niggle to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated niggle,
     * or with status 400 (Bad Request) if the niggle is not valid,
     * or with status 500 (Internal Server Error) if the niggle couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/niggles")
    @Timed
    public ResponseEntity<Niggle> updateNiggle(@RequestBody Niggle niggle) throws URISyntaxException {
        log.debug("REST request to update Niggle : {}", niggle);
        if (niggle.getId() == null) {
            return createNiggle(niggle);
        }
        Niggle result = niggleService.save(niggle);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, niggle.getId().toString()))
            .body(result);
    }

    /**
     * GET  /niggles : get all the niggles.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of niggles in body
     */
    @GetMapping("/niggles")
    @Timed
    @Secured({"ROLE_ADMIN", "ROLE_USER", "ROLE_DW" , "ROLE_DW_READ_ONLY" , "ROLE_QUATTRA"})
    public ResponseEntity<List<Niggle>> getAllNiggles(NiggleCriteria criteria) {
        log.debug("REST request to get Niggles by criteria: {}", criteria);
        List<Niggle> entityList = niggleQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * GET  /niggles/:id : get the "id" niggle.
     *
     * @param id the id of the niggle to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the niggle, or with status 404 (Not Found)
     */
    @GetMapping("/niggles/{id}")
    @Timed
    @Secured({"ROLE_ADMIN", "ROLE_USER", "ROLE_DW", "ROLE_DW_READ_ONLY", "ROLE_QUATTRA"})
    public ResponseEntity<Niggle> getNiggle(@PathVariable Long id) {
        log.debug("REST request to get Niggle : {}", id);
        Niggle niggle = niggleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(niggle));
    }

    /**
     * DELETE  /niggles/:id : delete the "id" niggle.
     *
     * @param id the id of the niggle to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/niggles/{id}")
    @Timed
    @Secured({"ROLE_ADMIN", "ROLE_USER", "ROLE_DW"})
    public ResponseEntity<Void> deleteNiggle(@PathVariable Long id) {
        log.debug("REST request to delete Niggle : {}", id);
        niggleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
