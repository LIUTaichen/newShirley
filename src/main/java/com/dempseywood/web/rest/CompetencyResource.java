package com.dempseywood.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.dempseywood.domain.Competency;

import com.dempseywood.repository.CompetencyRepository;
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
 * REST controller for managing Competency.
 */
@RestController
@RequestMapping("/api")
public class CompetencyResource {

    private final Logger log = LoggerFactory.getLogger(CompetencyResource.class);

    private static final String ENTITY_NAME = "competency";

    private final CompetencyRepository competencyRepository;

    public CompetencyResource(CompetencyRepository competencyRepository) {
        this.competencyRepository = competencyRepository;
    }

    /**
     * POST  /competencies : Create a new competency.
     *
     * @param competency the competency to create
     * @return the ResponseEntity with status 201 (Created) and with body the new competency, or with status 400 (Bad Request) if the competency has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/competencies")
    @Timed
    public ResponseEntity<Competency> createCompetency(@RequestBody Competency competency) throws URISyntaxException {
        log.debug("REST request to save Competency : {}", competency);
        if (competency.getId() != null) {
            throw new BadRequestAlertException("A new competency cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Competency result = competencyRepository.save(competency);
        return ResponseEntity.created(new URI("/api/competencies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /competencies : Updates an existing competency.
     *
     * @param competency the competency to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated competency,
     * or with status 400 (Bad Request) if the competency is not valid,
     * or with status 500 (Internal Server Error) if the competency couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/competencies")
    @Timed
    public ResponseEntity<Competency> updateCompetency(@RequestBody Competency competency) throws URISyntaxException {
        log.debug("REST request to update Competency : {}", competency);
        if (competency.getId() == null) {
            return createCompetency(competency);
        }
        Competency result = competencyRepository.save(competency);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, competency.getId().toString()))
            .body(result);
    }

    /**
     * GET  /competencies : get all the competencies.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of competencies in body
     */
    @GetMapping("/competencies")
    @Timed
    public List<Competency> getAllCompetencies() {
        log.debug("REST request to get all Competencies");
        return competencyRepository.findAll();
        }

    /**
     * GET  /competencies/:id : get the "id" competency.
     *
     * @param id the id of the competency to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the competency, or with status 404 (Not Found)
     */
    @GetMapping("/competencies/{id}")
    @Timed
    public ResponseEntity<Competency> getCompetency(@PathVariable Long id) {
        log.debug("REST request to get Competency : {}", id);
        Competency competency = competencyRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(competency));
    }

    /**
     * DELETE  /competencies/:id : delete the "id" competency.
     *
     * @param id the id of the competency to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/competencies/{id}")
    @Timed
    public ResponseEntity<Void> deleteCompetency(@PathVariable Long id) {
        log.debug("REST request to delete Competency : {}", id);
        competencyRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
