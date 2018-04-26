package com.dempseywood.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.dempseywood.domain.PlantLog;

import com.dempseywood.repository.PlantLogRepository;
import com.dempseywood.web.rest.errors.BadRequestAlertException;
import com.dempseywood.web.rest.util.HeaderUtil;
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
 * REST controller for managing PlantLog.
 */
@RestController
@RequestMapping("/api")
public class PlantLogResource {

    private final Logger log = LoggerFactory.getLogger(PlantLogResource.class);

    private static final String ENTITY_NAME = "plantLog";

    private final PlantLogRepository plantLogRepository;

    public PlantLogResource(PlantLogRepository plantLogRepository) {
        this.plantLogRepository = plantLogRepository;
    }

    /**
     * POST  /plant-logs : Create a new plantLog.
     *
     * @param plantLog the plantLog to create
     * @return the ResponseEntity with status 201 (Created) and with body the new plantLog, or with status 400 (Bad Request) if the plantLog has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/plant-logs")
    @Timed
    public ResponseEntity<PlantLog> createPlantLog(@RequestBody PlantLog plantLog) throws URISyntaxException {
        log.debug("REST request to save PlantLog : {}", plantLog);
        if (plantLog.getId() != null) {
            throw new BadRequestAlertException("A new plantLog cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PlantLog result = plantLogRepository.save(plantLog);
        return ResponseEntity.created(new URI("/api/plant-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /plant-logs : Updates an existing plantLog.
     *
     * @param plantLog the plantLog to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated plantLog,
     * or with status 400 (Bad Request) if the plantLog is not valid,
     * or with status 500 (Internal Server Error) if the plantLog couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/plant-logs")
    @Timed
    public ResponseEntity<PlantLog> updatePlantLog(@RequestBody PlantLog plantLog) throws URISyntaxException {
        log.debug("REST request to update PlantLog : {}", plantLog);
        if (plantLog.getId() == null) {
            return createPlantLog(plantLog);
        }
        PlantLog result = plantLogRepository.save(plantLog);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, plantLog.getId().toString()))
            .body(result);
    }

    /**
     * GET  /plant-logs : get all the plantLogs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of plantLogs in body
     */
    @GetMapping("/plant-logs")
    @Timed
    public List<PlantLog> getAllPlantLogs() {
        log.debug("REST request to get all PlantLogs");
        return plantLogRepository.findAll();
        }

    /**
     * GET  /plant-logs/:id : get the "id" plantLog.
     *
     * @param id the id of the plantLog to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the plantLog, or with status 404 (Not Found)
     */
    @GetMapping("/plant-logs/{id}")
    @Timed
    public ResponseEntity<PlantLog> getPlantLog(@PathVariable Long id) {
        log.debug("REST request to get PlantLog : {}", id);
        PlantLog plantLog = plantLogRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(plantLog));
    }

    /**
     * DELETE  /plant-logs/:id : delete the "id" plantLog.
     *
     * @param id the id of the plantLog to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/plant-logs/{id}")
    @Timed
    public ResponseEntity<Void> deletePlantLog(@PathVariable Long id) {
        log.debug("REST request to delete PlantLog : {}", id);
        plantLogRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
