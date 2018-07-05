package com.dempseywood.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.dempseywood.domain.PrestartCheckConfig;
import com.dempseywood.service.PrestartCheckConfigService;
import com.dempseywood.web.rest.errors.BadRequestAlertException;
import com.dempseywood.web.rest.util.HeaderUtil;
import com.dempseywood.service.dto.PrestartCheckConfigCriteria;
import com.dempseywood.service.PrestartCheckConfigQueryService;
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
 * REST controller for managing PrestartCheckConfig.
 */
@RestController
@RequestMapping("/api")
public class PrestartCheckConfigResource {

    private final Logger log = LoggerFactory.getLogger(PrestartCheckConfigResource.class);

    private static final String ENTITY_NAME = "prestartCheckConfig";

    private final PrestartCheckConfigService prestartCheckConfigService;

    private final PrestartCheckConfigQueryService prestartCheckConfigQueryService;

    public PrestartCheckConfigResource(PrestartCheckConfigService prestartCheckConfigService, PrestartCheckConfigQueryService prestartCheckConfigQueryService) {
        this.prestartCheckConfigService = prestartCheckConfigService;
        this.prestartCheckConfigQueryService = prestartCheckConfigQueryService;
    }

    /**
     * POST  /prestart-check-configs : Create a new prestartCheckConfig.
     *
     * @param prestartCheckConfig the prestartCheckConfig to create
     * @return the ResponseEntity with status 201 (Created) and with body the new prestartCheckConfig, or with status 400 (Bad Request) if the prestartCheckConfig has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/prestart-check-configs")
    @Timed
    public ResponseEntity<PrestartCheckConfig> createPrestartCheckConfig(@RequestBody PrestartCheckConfig prestartCheckConfig) throws URISyntaxException {
        log.debug("REST request to save PrestartCheckConfig : {}", prestartCheckConfig);
        if (prestartCheckConfig.getId() != null) {
            throw new BadRequestAlertException("A new prestartCheckConfig cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PrestartCheckConfig result = prestartCheckConfigService.save(prestartCheckConfig);
        return ResponseEntity.created(new URI("/api/prestart-check-configs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /prestart-check-configs : Updates an existing prestartCheckConfig.
     *
     * @param prestartCheckConfig the prestartCheckConfig to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated prestartCheckConfig,
     * or with status 400 (Bad Request) if the prestartCheckConfig is not valid,
     * or with status 500 (Internal Server Error) if the prestartCheckConfig couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/prestart-check-configs")
    @Timed
    public ResponseEntity<PrestartCheckConfig> updatePrestartCheckConfig(@RequestBody PrestartCheckConfig prestartCheckConfig) throws URISyntaxException {
        log.debug("REST request to update PrestartCheckConfig : {}", prestartCheckConfig);
        if (prestartCheckConfig.getId() == null) {
            return createPrestartCheckConfig(prestartCheckConfig);
        }
        PrestartCheckConfig result = prestartCheckConfigService.save(prestartCheckConfig);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, prestartCheckConfig.getId().toString()))
            .body(result);
    }

    /**
     * GET  /prestart-check-configs : get all the prestartCheckConfigs.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of prestartCheckConfigs in body
     */
    @GetMapping("/prestart-check-configs")
    @Timed
    public ResponseEntity<List<PrestartCheckConfig>> getAllPrestartCheckConfigs(PrestartCheckConfigCriteria criteria) {
        log.debug("REST request to get PrestartCheckConfigs by criteria: {}", criteria);
        List<PrestartCheckConfig> entityList = prestartCheckConfigQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * GET  /prestart-check-configs/:id : get the "id" prestartCheckConfig.
     *
     * @param id the id of the prestartCheckConfig to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the prestartCheckConfig, or with status 404 (Not Found)
     */
    @GetMapping("/prestart-check-configs/{id}")
    @Timed
    public ResponseEntity<PrestartCheckConfig> getPrestartCheckConfig(@PathVariable Long id) {
        log.debug("REST request to get PrestartCheckConfig : {}", id);
        PrestartCheckConfig prestartCheckConfig = prestartCheckConfigService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(prestartCheckConfig));
    }

    /**
     * DELETE  /prestart-check-configs/:id : delete the "id" prestartCheckConfig.
     *
     * @param id the id of the prestartCheckConfig to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/prestart-check-configs/{id}")
    @Timed
    public ResponseEntity<Void> deletePrestartCheckConfig(@PathVariable Long id) {
        log.debug("REST request to delete PrestartCheckConfig : {}", id);
        prestartCheckConfigService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
