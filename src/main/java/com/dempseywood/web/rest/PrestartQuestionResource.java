package com.dempseywood.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.dempseywood.domain.PrestartQuestion;
import com.dempseywood.service.PrestartQuestionService;
import com.dempseywood.web.rest.errors.BadRequestAlertException;
import com.dempseywood.web.rest.util.HeaderUtil;
import com.dempseywood.service.dto.PrestartQuestionCriteria;
import com.dempseywood.service.PrestartQuestionQueryService;
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
 * REST controller for managing PrestartQuestion.
 */
@RestController
@RequestMapping("/api")
public class PrestartQuestionResource {

    private final Logger log = LoggerFactory.getLogger(PrestartQuestionResource.class);

    private static final String ENTITY_NAME = "prestartQuestion";

    private final PrestartQuestionService prestartQuestionService;

    private final PrestartQuestionQueryService prestartQuestionQueryService;

    public PrestartQuestionResource(PrestartQuestionService prestartQuestionService, PrestartQuestionQueryService prestartQuestionQueryService) {
        this.prestartQuestionService = prestartQuestionService;
        this.prestartQuestionQueryService = prestartQuestionQueryService;
    }

    /**
     * POST  /prestart-questions : Create a new prestartQuestion.
     *
     * @param prestartQuestion the prestartQuestion to create
     * @return the ResponseEntity with status 201 (Created) and with body the new prestartQuestion, or with status 400 (Bad Request) if the prestartQuestion has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/prestart-questions")
    @Timed
    public ResponseEntity<PrestartQuestion> createPrestartQuestion(@RequestBody PrestartQuestion prestartQuestion) throws URISyntaxException {
        log.debug("REST request to save PrestartQuestion : {}", prestartQuestion);
        if (prestartQuestion.getId() != null) {
            throw new BadRequestAlertException("A new prestartQuestion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PrestartQuestion result = prestartQuestionService.save(prestartQuestion);
        return ResponseEntity.created(new URI("/api/prestart-questions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /prestart-questions : Updates an existing prestartQuestion.
     *
     * @param prestartQuestion the prestartQuestion to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated prestartQuestion,
     * or with status 400 (Bad Request) if the prestartQuestion is not valid,
     * or with status 500 (Internal Server Error) if the prestartQuestion couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/prestart-questions")
    @Timed
    public ResponseEntity<PrestartQuestion> updatePrestartQuestion(@RequestBody PrestartQuestion prestartQuestion) throws URISyntaxException {
        log.debug("REST request to update PrestartQuestion : {}", prestartQuestion);
        if (prestartQuestion.getId() == null) {
            return createPrestartQuestion(prestartQuestion);
        }
        PrestartQuestion result = prestartQuestionService.save(prestartQuestion);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, prestartQuestion.getId().toString()))
            .body(result);
    }

    /**
     * GET  /prestart-questions : get all the prestartQuestions.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of prestartQuestions in body
     */
    @GetMapping("/prestart-questions")
    @Timed
    public ResponseEntity<List<PrestartQuestion>> getAllPrestartQuestions(PrestartQuestionCriteria criteria) {
        log.debug("REST request to get PrestartQuestions by criteria: {}", criteria);
        List<PrestartQuestion> entityList = prestartQuestionQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * GET  /prestart-questions/:id : get the "id" prestartQuestion.
     *
     * @param id the id of the prestartQuestion to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the prestartQuestion, or with status 404 (Not Found)
     */
    @GetMapping("/prestart-questions/{id}")
    @Timed
    public ResponseEntity<PrestartQuestion> getPrestartQuestion(@PathVariable Long id) {
        log.debug("REST request to get PrestartQuestion : {}", id);
        PrestartQuestion prestartQuestion = prestartQuestionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(prestartQuestion));
    }

    /**
     * DELETE  /prestart-questions/:id : delete the "id" prestartQuestion.
     *
     * @param id the id of the prestartQuestion to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/prestart-questions/{id}")
    @Timed
    public ResponseEntity<Void> deletePrestartQuestion(@PathVariable Long id) {
        log.debug("REST request to delete PrestartQuestion : {}", id);
        prestartQuestionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
