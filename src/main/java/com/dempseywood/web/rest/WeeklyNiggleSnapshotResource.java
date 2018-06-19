package com.dempseywood.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.dempseywood.domain.WeeklyNiggleSnapshot;
import com.dempseywood.service.WeeklyNiggleSnapshotService;
import com.dempseywood.web.rest.errors.BadRequestAlertException;
import com.dempseywood.web.rest.util.HeaderUtil;
import com.dempseywood.service.dto.WeeklyNiggleSnapshotCriteria;
import com.dempseywood.service.WeeklyNiggleSnapshotQueryService;
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
 * REST controller for managing WeeklyNiggleSnapshot.
 */
@RestController
@RequestMapping("/api")
public class WeeklyNiggleSnapshotResource {

    private final Logger log = LoggerFactory.getLogger(WeeklyNiggleSnapshotResource.class);

    private static final String ENTITY_NAME = "weeklyNiggleSnapshot";

    private final WeeklyNiggleSnapshotService weeklyNiggleSnapshotService;

    private final WeeklyNiggleSnapshotQueryService weeklyNiggleSnapshotQueryService;

    public WeeklyNiggleSnapshotResource(WeeklyNiggleSnapshotService weeklyNiggleSnapshotService, WeeklyNiggleSnapshotQueryService weeklyNiggleSnapshotQueryService) {
        this.weeklyNiggleSnapshotService = weeklyNiggleSnapshotService;
        this.weeklyNiggleSnapshotQueryService = weeklyNiggleSnapshotQueryService;
    }

    /**
     * POST  /weekly-niggle-snapshots : Create a new weeklyNiggleSnapshot.
     *
     * @param weeklyNiggleSnapshot the weeklyNiggleSnapshot to create
     * @return the ResponseEntity with status 201 (Created) and with body the new weeklyNiggleSnapshot, or with status 400 (Bad Request) if the weeklyNiggleSnapshot has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/weekly-niggle-snapshots")
    @Timed
    public ResponseEntity<WeeklyNiggleSnapshot> createWeeklyNiggleSnapshot(@RequestBody WeeklyNiggleSnapshot weeklyNiggleSnapshot) throws URISyntaxException {
        log.debug("REST request to save WeeklyNiggleSnapshot : {}", weeklyNiggleSnapshot);
        if (weeklyNiggleSnapshot.getId() != null) {
            throw new BadRequestAlertException("A new weeklyNiggleSnapshot cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WeeklyNiggleSnapshot result = weeklyNiggleSnapshotService.save(weeklyNiggleSnapshot);
        return ResponseEntity.created(new URI("/api/weekly-niggle-snapshots/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /weekly-niggle-snapshots : Updates an existing weeklyNiggleSnapshot.
     *
     * @param weeklyNiggleSnapshot the weeklyNiggleSnapshot to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated weeklyNiggleSnapshot,
     * or with status 400 (Bad Request) if the weeklyNiggleSnapshot is not valid,
     * or with status 500 (Internal Server Error) if the weeklyNiggleSnapshot couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/weekly-niggle-snapshots")
    @Timed
    public ResponseEntity<WeeklyNiggleSnapshot> updateWeeklyNiggleSnapshot(@RequestBody WeeklyNiggleSnapshot weeklyNiggleSnapshot) throws URISyntaxException {
        log.debug("REST request to update WeeklyNiggleSnapshot : {}", weeklyNiggleSnapshot);
        if (weeklyNiggleSnapshot.getId() == null) {
            return createWeeklyNiggleSnapshot(weeklyNiggleSnapshot);
        }
        WeeklyNiggleSnapshot result = weeklyNiggleSnapshotService.save(weeklyNiggleSnapshot);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, weeklyNiggleSnapshot.getId().toString()))
            .body(result);
    }

    /**
     * GET  /weekly-niggle-snapshots : get all the weeklyNiggleSnapshots.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of weeklyNiggleSnapshots in body
     */
    @GetMapping("/weekly-niggle-snapshots")
    @Timed
    public ResponseEntity<List<WeeklyNiggleSnapshot>> getAllWeeklyNiggleSnapshots(WeeklyNiggleSnapshotCriteria criteria) {
        log.debug("REST request to get WeeklyNiggleSnapshots by criteria: {}", criteria);
        List<WeeklyNiggleSnapshot> entityList = weeklyNiggleSnapshotQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * GET  /weekly-niggle-snapshots/:id : get the "id" weeklyNiggleSnapshot.
     *
     * @param id the id of the weeklyNiggleSnapshot to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the weeklyNiggleSnapshot, or with status 404 (Not Found)
     */
    @GetMapping("/weekly-niggle-snapshots/{id}")
    @Timed
    public ResponseEntity<WeeklyNiggleSnapshot> getWeeklyNiggleSnapshot(@PathVariable Long id) {
        log.debug("REST request to get WeeklyNiggleSnapshot : {}", id);
        WeeklyNiggleSnapshot weeklyNiggleSnapshot = weeklyNiggleSnapshotService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(weeklyNiggleSnapshot));
    }

    /**
     * DELETE  /weekly-niggle-snapshots/:id : delete the "id" weeklyNiggleSnapshot.
     *
     * @param id the id of the weeklyNiggleSnapshot to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/weekly-niggle-snapshots/{id}")
    @Timed
    public ResponseEntity<Void> deleteWeeklyNiggleSnapshot(@PathVariable Long id) {
        log.debug("REST request to delete WeeklyNiggleSnapshot : {}", id);
        weeklyNiggleSnapshotService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
