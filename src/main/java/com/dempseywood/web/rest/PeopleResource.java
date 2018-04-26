package com.dempseywood.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.dempseywood.domain.People;

import com.dempseywood.repository.PeopleRepository;
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
 * REST controller for managing People.
 */
@RestController
@RequestMapping("/api")
public class PeopleResource {

    private final Logger log = LoggerFactory.getLogger(PeopleResource.class);

    private static final String ENTITY_NAME = "people";

    private final PeopleRepository peopleRepository;

    public PeopleResource(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    /**
     * POST  /people : Create a new people.
     *
     * @param people the people to create
     * @return the ResponseEntity with status 201 (Created) and with body the new people, or with status 400 (Bad Request) if the people has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/people")
    @Timed
    public ResponseEntity<People> createPeople(@RequestBody People people) throws URISyntaxException {
        log.debug("REST request to save People : {}", people);
        if (people.getId() != null) {
            throw new BadRequestAlertException("A new people cannot already have an ID", ENTITY_NAME, "idexists");
        }
        People result = peopleRepository.save(people);
        return ResponseEntity.created(new URI("/api/people/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /people : Updates an existing people.
     *
     * @param people the people to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated people,
     * or with status 400 (Bad Request) if the people is not valid,
     * or with status 500 (Internal Server Error) if the people couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/people")
    @Timed
    public ResponseEntity<People> updatePeople(@RequestBody People people) throws URISyntaxException {
        log.debug("REST request to update People : {}", people);
        if (people.getId() == null) {
            return createPeople(people);
        }
        People result = peopleRepository.save(people);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, people.getId().toString()))
            .body(result);
    }

    /**
     * GET  /people : get all the people.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of people in body
     */
    @GetMapping("/people")
    @Timed
    public List<People> getAllPeople() {
        log.debug("REST request to get all People");
        return peopleRepository.findAll();
        }

    /**
     * GET  /people/:id : get the "id" people.
     *
     * @param id the id of the people to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the people, or with status 404 (Not Found)
     */
    @GetMapping("/people/{id}")
    @Timed
    public ResponseEntity<People> getPeople(@PathVariable Long id) {
        log.debug("REST request to get People : {}", id);
        People people = peopleRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(people));
    }

    /**
     * DELETE  /people/:id : delete the "id" people.
     *
     * @param id the id of the people to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/people/{id}")
    @Timed
    public ResponseEntity<Void> deletePeople(@PathVariable Long id) {
        log.debug("REST request to delete People : {}", id);
        peopleRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
