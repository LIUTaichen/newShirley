package com.dempseywood.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.dempseywood.domain.PrestartQuestionOption;
import com.dempseywood.service.PrestartQuestionOptionService;
import com.dempseywood.web.rest.errors.BadRequestAlertException;
import com.dempseywood.web.rest.util.HeaderUtil;
import com.dempseywood.service.dto.PrestartQuestionOptionCriteria;
import com.dempseywood.service.PrestartQuestionOptionQueryService;
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
 * REST controller for managing PrestartQuestionOption.
 */
@RestController
@RequestMapping("/api")
public class PrestartQuestionOptionResource {

    private final Logger log = LoggerFactory.getLogger(PrestartQuestionOptionResource.class);

    private static final String ENTITY_NAME = "prestartQuestionOption";

    private final PrestartQuestionOptionService prestartQuestionOptionService;

    private final PrestartQuestionOptionQueryService prestartQuestionOptionQueryService;

    public PrestartQuestionOptionResource(PrestartQuestionOptionService prestartQuestionOptionService, PrestartQuestionOptionQueryService prestartQuestionOptionQueryService) {
        this.prestartQuestionOptionService = prestartQuestionOptionService;
        this.prestartQuestionOptionQueryService = prestartQuestionOptionQueryService;
    }

    /**
     * POST  /prestart-question-options : Create a new prestartQuestionOption.
     *
     * @param prestartQuestionOption the prestartQuestionOption to create
     * @return the ResponseEntity with status 201 (Created) and with body the new prestartQuestionOption, or with status 400 (Bad Request) if the prestartQuestionOption has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/prestart-question-options")
    @Timed
    public ResponseEntity<PrestartQuestionOption> createPrestartQuestionOption(@RequestBody PrestartQuestionOption prestartQuestionOption) throws URISyntaxException {
        log.debug("REST request to save PrestartQuestionOption : {}", prestartQuestionOption);
        if (prestartQuestionOption.getId() != null) {
            throw new BadRequestAlertException("A new prestartQuestionOption cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PrestartQuestionOption result = prestartQuestionOptionService.save(prestartQuestionOption);
        return ResponseEntity.created(new URI("/api/prestart-question-options/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /prestart-question-options : Updates an existing prestartQuestionOption.
     *
     * @param prestartQuestionOption the prestartQuestionOption to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated prestartQuestionOption,
     * or with status 400 (Bad Request) if the prestartQuestionOption is not valid,
     * or with status 500 (Internal Server Error) if the prestartQuestionOption couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/prestart-question-options")
    @Timed
    public ResponseEntity<PrestartQuestionOption> updatePrestartQuestionOption(@RequestBody PrestartQuestionOption prestartQuestionOption) throws URISyntaxException {
        log.debug("REST request to update PrestartQuestionOption : {}", prestartQuestionOption);
        if (prestartQuestionOption.getId() == null) {
            return createPrestartQuestionOption(prestartQuestionOption);
        }
        PrestartQuestionOption result = prestartQuestionOptionService.save(prestartQuestionOption);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, prestartQuestionOption.getId().toString()))
            .body(result);
    }

    /**
     * GET  /prestart-question-options : get all the prestartQuestionOptions.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of prestartQuestionOptions in body
     */
    @GetMapping("/prestart-question-options")
    @Timed
    public ResponseEntity<List<PrestartQuestionOption>> getAllPrestartQuestionOptions(PrestartQuestionOptionCriteria criteria) {
        log.debug("REST request to get PrestartQuestionOptions by criteria: {}", criteria);
        List<PrestartQuestionOption> entityList = prestartQuestionOptionQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * GET  /prestart-question-options/:id : get the "id" prestartQuestionOption.
     *
     * @param id the id of the prestartQuestionOption to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the prestartQuestionOption, or with status 404 (Not Found)
     */
    @GetMapping("/prestart-question-options/{id}")
    @Timed
    public ResponseEntity<PrestartQuestionOption> getPrestartQuestionOption(@PathVariable Long id) {
        log.debug("REST request to get PrestartQuestionOption : {}", id);
        PrestartQuestionOption prestartQuestionOption = prestartQuestionOptionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(prestartQuestionOption));
    }

    /**
     * DELETE  /prestart-question-options/:id : delete the "id" prestartQuestionOption.
     *
     * @param id the id of the prestartQuestionOption to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/prestart-question-options/{id}")
    @Timed
    public ResponseEntity<Void> deletePrestartQuestionOption(@PathVariable Long id) {
        log.debug("REST request to delete PrestartQuestionOption : {}", id);
        prestartQuestionOptionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
