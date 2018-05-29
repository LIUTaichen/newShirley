package com.dempseywood.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.dempseywood.domain.Plant;
import com.dempseywood.service.PlantService;
import com.dempseywood.web.rest.errors.BadRequestAlertException;
import com.dempseywood.web.rest.util.HeaderUtil;
import com.dempseywood.service.dto.PlantCriteria;
import com.dempseywood.service.PlantQueryService;
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
 * REST controller for managing Plant.
 */
@RestController
@RequestMapping("/api")
public class PlantResource {

    private final Logger log = LoggerFactory.getLogger(PlantResource.class);

    private static final String ENTITY_NAME = "plant";

    private final PlantService plantService;

    private final PlantQueryService plantQueryService;

    public PlantResource(PlantService plantService, PlantQueryService plantQueryService) {
        this.plantService = plantService;
        this.plantQueryService = plantQueryService;
    }

    /**
     * POST  /plants : Create a new plant.
     *
     * @param plant the plant to create
     * @return the ResponseEntity with status 201 (Created) and with body the new plant, or with status 400 (Bad Request) if the plant has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/plants")
    @Timed
    public ResponseEntity<Plant> createPlant(@RequestBody Plant plant) throws URISyntaxException {
        log.debug("REST request to save Plant : {}", plant);
        if (plant.getId() != null) {
            throw new BadRequestAlertException("A new plant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Plant result = plantService.save(plant);
        return ResponseEntity.created(new URI("/api/plants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /plants : Updates an existing plant.
     *
     * @param plant the plant to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated plant,
     * or with status 400 (Bad Request) if the plant is not valid,
     * or with status 500 (Internal Server Error) if the plant couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/plants")
    @Timed
    public ResponseEntity<Plant> updatePlant(@RequestBody Plant plant) throws URISyntaxException {
        log.debug("REST request to update Plant : {}", plant);
        if (plant.getId() == null) {
            return createPlant(plant);
        }
        Plant result = plantService.save(plant);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, plant.getId().toString()))
            .body(result);
    }

    /**
     * GET  /plants : get all the plants.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of plants in body
     */
    @GetMapping("/plants")
    @Timed
    public ResponseEntity<List<Plant>> getAllPlants(PlantCriteria criteria) {
        log.debug("REST request to get Plants by criteria: {}", criteria);
        List<Plant> entityList = plantQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * GET  /plants/:id : get the "id" plant.
     *
     * @param id the id of the plant to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the plant, or with status 404 (Not Found)
     */
    @GetMapping("/plants/{id}")
    @Timed
    public ResponseEntity<Plant> getPlant(@PathVariable Long id) {
        log.debug("REST request to get Plant : {}", id);
        Plant plant = plantService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(plant));
    }

    /**
     * DELETE  /plants/:id : delete the "id" plant.
     *
     * @param id the id of the plant to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/plants/{id}")
    @Timed
    public ResponseEntity<Void> deletePlant(@PathVariable Long id) {
        log.debug("REST request to delete Plant : {}", id);
        plantService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
