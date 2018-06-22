package com.dempseywood.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.dempseywood.domain.NiggleSnapshot;
import com.dempseywood.service.NiggleSnapshotService;
import com.dempseywood.web.rest.errors.BadRequestAlertException;
import com.dempseywood.web.rest.util.HeaderUtil;
import com.dempseywood.service.dto.NiggleSnapshotCriteria;
import com.dempseywood.service.NiggleSnapshotQueryService;
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
 * REST controller for managing NiggleSnapshot.
 */
@RestController
@RequestMapping("/api")
public class NiggleSnapshotResource {

    private final Logger log = LoggerFactory.getLogger(NiggleSnapshotResource.class);

    private static final String ENTITY_NAME = "niggleSnapshot";

    private final NiggleSnapshotService niggleSnapshotService;

    private final NiggleSnapshotQueryService niggleSnapshotQueryService;

    public NiggleSnapshotResource(NiggleSnapshotService niggleSnapshotService, NiggleSnapshotQueryService niggleSnapshotQueryService) {
        this.niggleSnapshotService = niggleSnapshotService;
        this.niggleSnapshotQueryService = niggleSnapshotQueryService;
    }

    /**
     * POST  /niggle-snapshots : Create a new niggleSnapshot.
     *
     * @param niggleSnapshot the niggleSnapshot to create
     * @return the ResponseEntity with status 201 (Created) and with body the new niggleSnapshot, or with status 400 (Bad Request) if the niggleSnapshot has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/niggle-snapshots")
    @Timed
    public ResponseEntity<NiggleSnapshot> createNiggleSnapshot(@RequestBody NiggleSnapshot niggleSnapshot) throws URISyntaxException {
        log.debug("REST request to save NiggleSnapshot : {}", niggleSnapshot);
        if (niggleSnapshot.getId() != null) {
            throw new BadRequestAlertException("A new niggleSnapshot cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NiggleSnapshot result = niggleSnapshotService.save(niggleSnapshot);
        return ResponseEntity.created(new URI("/api/niggle-snapshots/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /niggle-snapshots : Updates an existing niggleSnapshot.
     *
     * @param niggleSnapshot the niggleSnapshot to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated niggleSnapshot,
     * or with status 400 (Bad Request) if the niggleSnapshot is not valid,
     * or with status 500 (Internal Server Error) if the niggleSnapshot couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/niggle-snapshots")
    @Timed
    public ResponseEntity<NiggleSnapshot> updateNiggleSnapshot(@RequestBody NiggleSnapshot niggleSnapshot) throws URISyntaxException {
        log.debug("REST request to update NiggleSnapshot : {}", niggleSnapshot);
        if (niggleSnapshot.getId() == null) {
            return createNiggleSnapshot(niggleSnapshot);
        }
        NiggleSnapshot result = niggleSnapshotService.save(niggleSnapshot);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, niggleSnapshot.getId().toString()))
            .body(result);
    }

    /**
     * GET  /niggle-snapshots : get all the niggleSnapshots.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of niggleSnapshots in body
     */
    @GetMapping("/niggle-snapshots")
    @Timed
    public ResponseEntity<List<NiggleSnapshot>> getAllNiggleSnapshots(NiggleSnapshotCriteria criteria) {
        log.debug("REST request to get NiggleSnapshots by criteria: {}", criteria);
        List<NiggleSnapshot> entityList = niggleSnapshotQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * GET  /niggle-snapshots/:id : get the "id" niggleSnapshot.
     *
     * @param id the id of the niggleSnapshot to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the niggleSnapshot, or with status 404 (Not Found)
     */
    @GetMapping("/niggle-snapshots/{id}")
    @Timed
    public ResponseEntity<NiggleSnapshot> getNiggleSnapshot(@PathVariable Long id) {
        log.debug("REST request to get NiggleSnapshot : {}", id);
        NiggleSnapshot niggleSnapshot = niggleSnapshotService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(niggleSnapshot));
    }

    /**
     * DELETE  /niggle-snapshots/:id : delete the "id" niggleSnapshot.
     *
     * @param id the id of the niggleSnapshot to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/niggle-snapshots/{id}")
    @Timed
    public ResponseEntity<Void> deleteNiggleSnapshot(@PathVariable Long id) {
        log.debug("REST request to delete NiggleSnapshot : {}", id);
        niggleSnapshotService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
