package com.dempseywood.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.dempseywood.domain.MaintenanceContractor;

import com.dempseywood.repository.MaintenanceContractorRepository;
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
 * REST controller for managing MaintenanceContractor.
 */
@RestController
@RequestMapping("/api")
public class MaintenanceContractorResource {

    private final Logger log = LoggerFactory.getLogger(MaintenanceContractorResource.class);

    private static final String ENTITY_NAME = "maintenanceContractor";

    private final MaintenanceContractorRepository maintenanceContractorRepository;

    public MaintenanceContractorResource(MaintenanceContractorRepository maintenanceContractorRepository) {
        this.maintenanceContractorRepository = maintenanceContractorRepository;
    }

    /**
     * POST  /maintenance-contractors : Create a new maintenanceContractor.
     *
     * @param maintenanceContractor the maintenanceContractor to create
     * @return the ResponseEntity with status 201 (Created) and with body the new maintenanceContractor, or with status 400 (Bad Request) if the maintenanceContractor has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/maintenance-contractors")
    @Timed
    public ResponseEntity<MaintenanceContractor> createMaintenanceContractor(@RequestBody MaintenanceContractor maintenanceContractor) throws URISyntaxException {
        log.debug("REST request to save MaintenanceContractor : {}", maintenanceContractor);
        if (maintenanceContractor.getId() != null) {
            throw new BadRequestAlertException("A new maintenanceContractor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MaintenanceContractor result = maintenanceContractorRepository.save(maintenanceContractor);
        return ResponseEntity.created(new URI("/api/maintenance-contractors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /maintenance-contractors : Updates an existing maintenanceContractor.
     *
     * @param maintenanceContractor the maintenanceContractor to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated maintenanceContractor,
     * or with status 400 (Bad Request) if the maintenanceContractor is not valid,
     * or with status 500 (Internal Server Error) if the maintenanceContractor couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/maintenance-contractors")
    @Timed
    public ResponseEntity<MaintenanceContractor> updateMaintenanceContractor(@RequestBody MaintenanceContractor maintenanceContractor) throws URISyntaxException {
        log.debug("REST request to update MaintenanceContractor : {}", maintenanceContractor);
        if (maintenanceContractor.getId() == null) {
            return createMaintenanceContractor(maintenanceContractor);
        }
        MaintenanceContractor result = maintenanceContractorRepository.save(maintenanceContractor);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, maintenanceContractor.getId().toString()))
            .body(result);
    }

    /**
     * GET  /maintenance-contractors : get all the maintenanceContractors.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of maintenanceContractors in body
     */
    @GetMapping("/maintenance-contractors")
    @Timed
    public List<MaintenanceContractor> getAllMaintenanceContractors() {
        log.debug("REST request to get all MaintenanceContractors");
        return maintenanceContractorRepository.findAll();
        }

    /**
     * GET  /maintenance-contractors/:id : get the "id" maintenanceContractor.
     *
     * @param id the id of the maintenanceContractor to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the maintenanceContractor, or with status 404 (Not Found)
     */
    @GetMapping("/maintenance-contractors/{id}")
    @Timed
    public ResponseEntity<MaintenanceContractor> getMaintenanceContractor(@PathVariable Long id) {
        log.debug("REST request to get MaintenanceContractor : {}", id);
        MaintenanceContractor maintenanceContractor = maintenanceContractorRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(maintenanceContractor));
    }

    /**
     * DELETE  /maintenance-contractors/:id : delete the "id" maintenanceContractor.
     *
     * @param id the id of the maintenanceContractor to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/maintenance-contractors/{id}")
    @Timed
    public ResponseEntity<Void> deleteMaintenanceContractor(@PathVariable Long id) {
        log.debug("REST request to delete MaintenanceContractor : {}", id);
        maintenanceContractorRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
