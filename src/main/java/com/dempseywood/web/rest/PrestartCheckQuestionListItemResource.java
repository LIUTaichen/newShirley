package com.dempseywood.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.dempseywood.domain.PrestartCheckQuestionListItem;
import com.dempseywood.service.PrestartCheckQuestionListItemService;
import com.dempseywood.web.rest.errors.BadRequestAlertException;
import com.dempseywood.web.rest.util.HeaderUtil;
import com.dempseywood.service.dto.PrestartCheckQuestionListItemCriteria;
import com.dempseywood.service.PrestartCheckQuestionListItemQueryService;
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
 * REST controller for managing PrestartCheckQuestionListItem.
 */
@RestController
@RequestMapping("/api")
public class PrestartCheckQuestionListItemResource {

    private final Logger log = LoggerFactory.getLogger(PrestartCheckQuestionListItemResource.class);

    private static final String ENTITY_NAME = "prestartCheckQuestionListItem";

    private final PrestartCheckQuestionListItemService prestartCheckQuestionListItemService;

    private final PrestartCheckQuestionListItemQueryService prestartCheckQuestionListItemQueryService;

    public PrestartCheckQuestionListItemResource(PrestartCheckQuestionListItemService prestartCheckQuestionListItemService, PrestartCheckQuestionListItemQueryService prestartCheckQuestionListItemQueryService) {
        this.prestartCheckQuestionListItemService = prestartCheckQuestionListItemService;
        this.prestartCheckQuestionListItemQueryService = prestartCheckQuestionListItemQueryService;
    }

    /**
     * POST  /prestart-check-question-list-items : Create a new prestartCheckQuestionListItem.
     *
     * @param prestartCheckQuestionListItem the prestartCheckQuestionListItem to create
     * @return the ResponseEntity with status 201 (Created) and with body the new prestartCheckQuestionListItem, or with status 400 (Bad Request) if the prestartCheckQuestionListItem has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/prestart-check-question-list-items")
    @Timed
    public ResponseEntity<PrestartCheckQuestionListItem> createPrestartCheckQuestionListItem(@RequestBody PrestartCheckQuestionListItem prestartCheckQuestionListItem) throws URISyntaxException {
        log.debug("REST request to save PrestartCheckQuestionListItem : {}", prestartCheckQuestionListItem);
        if (prestartCheckQuestionListItem.getId() != null) {
            throw new BadRequestAlertException("A new prestartCheckQuestionListItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PrestartCheckQuestionListItem result = prestartCheckQuestionListItemService.save(prestartCheckQuestionListItem);
        return ResponseEntity.created(new URI("/api/prestart-check-question-list-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /prestart-check-question-list-items : Updates an existing prestartCheckQuestionListItem.
     *
     * @param prestartCheckQuestionListItem the prestartCheckQuestionListItem to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated prestartCheckQuestionListItem,
     * or with status 400 (Bad Request) if the prestartCheckQuestionListItem is not valid,
     * or with status 500 (Internal Server Error) if the prestartCheckQuestionListItem couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/prestart-check-question-list-items")
    @Timed
    public ResponseEntity<PrestartCheckQuestionListItem> updatePrestartCheckQuestionListItem(@RequestBody PrestartCheckQuestionListItem prestartCheckQuestionListItem) throws URISyntaxException {
        log.debug("REST request to update PrestartCheckQuestionListItem : {}", prestartCheckQuestionListItem);
        if (prestartCheckQuestionListItem.getId() == null) {
            return createPrestartCheckQuestionListItem(prestartCheckQuestionListItem);
        }
        PrestartCheckQuestionListItem result = prestartCheckQuestionListItemService.save(prestartCheckQuestionListItem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, prestartCheckQuestionListItem.getId().toString()))
            .body(result);
    }

    /**
     * GET  /prestart-check-question-list-items : get all the prestartCheckQuestionListItems.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of prestartCheckQuestionListItems in body
     */
    @GetMapping("/prestart-check-question-list-items")
    @Timed
    public ResponseEntity<List<PrestartCheckQuestionListItem>> getAllPrestartCheckQuestionListItems(PrestartCheckQuestionListItemCriteria criteria) {
        log.debug("REST request to get PrestartCheckQuestionListItems by criteria: {}", criteria);
        List<PrestartCheckQuestionListItem> entityList = prestartCheckQuestionListItemQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * GET  /prestart-check-question-list-items/:id : get the "id" prestartCheckQuestionListItem.
     *
     * @param id the id of the prestartCheckQuestionListItem to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the prestartCheckQuestionListItem, or with status 404 (Not Found)
     */
    @GetMapping("/prestart-check-question-list-items/{id}")
    @Timed
    public ResponseEntity<PrestartCheckQuestionListItem> getPrestartCheckQuestionListItem(@PathVariable Long id) {
        log.debug("REST request to get PrestartCheckQuestionListItem : {}", id);
        PrestartCheckQuestionListItem prestartCheckQuestionListItem = prestartCheckQuestionListItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(prestartCheckQuestionListItem));
    }

    /**
     * DELETE  /prestart-check-question-list-items/:id : delete the "id" prestartCheckQuestionListItem.
     *
     * @param id the id of the prestartCheckQuestionListItem to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/prestart-check-question-list-items/{id}")
    @Timed
    public ResponseEntity<Void> deletePrestartCheckQuestionListItem(@PathVariable Long id) {
        log.debug("REST request to delete PrestartCheckQuestionListItem : {}", id);
        prestartCheckQuestionListItemService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
