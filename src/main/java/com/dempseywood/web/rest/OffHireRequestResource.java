package com.dempseywood.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.dempseywood.domain.OffHireRequest;

import com.dempseywood.repository.OffHireRequestRepository;
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
 * REST controller for managing OffHireRequest.
 */
@RestController
@RequestMapping("/api")
public class OffHireRequestResource {

    private final Logger log = LoggerFactory.getLogger(OffHireRequestResource.class);

    private static final String ENTITY_NAME = "offHireRequest";

    private final OffHireRequestRepository offHireRequestRepository;

    public OffHireRequestResource(OffHireRequestRepository offHireRequestRepository) {
        this.offHireRequestRepository = offHireRequestRepository;
    }

    /**
     * POST  /off-hire-requests : Create a new offHireRequest.
     *
     * @param offHireRequest the offHireRequest to create
     * @return the ResponseEntity with status 201 (Created) and with body the new offHireRequest, or with status 400 (Bad Request) if the offHireRequest has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/off-hire-requests")
    @Timed
    public ResponseEntity<OffHireRequest> createOffHireRequest(@RequestBody OffHireRequest offHireRequest) throws URISyntaxException {
        log.debug("REST request to save OffHireRequest : {}", offHireRequest);
        if (offHireRequest.getId() != null) {
            throw new BadRequestAlertException("A new offHireRequest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OffHireRequest result = offHireRequestRepository.save(offHireRequest);
        return ResponseEntity.created(new URI("/api/off-hire-requests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /off-hire-requests : Updates an existing offHireRequest.
     *
     * @param offHireRequest the offHireRequest to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated offHireRequest,
     * or with status 400 (Bad Request) if the offHireRequest is not valid,
     * or with status 500 (Internal Server Error) if the offHireRequest couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/off-hire-requests")
    @Timed
    public ResponseEntity<OffHireRequest> updateOffHireRequest(@RequestBody OffHireRequest offHireRequest) throws URISyntaxException {
        log.debug("REST request to update OffHireRequest : {}", offHireRequest);
        if (offHireRequest.getId() == null) {
            return createOffHireRequest(offHireRequest);
        }
        OffHireRequest result = offHireRequestRepository.save(offHireRequest);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, offHireRequest.getId().toString()))
            .body(result);
    }

    /**
     * GET  /off-hire-requests : get all the offHireRequests.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of offHireRequests in body
     */
    @GetMapping("/off-hire-requests")
    @Timed
    public List<OffHireRequest> getAllOffHireRequests() {
        log.debug("REST request to get all OffHireRequests");
        return offHireRequestRepository.findAll();
        }

    /**
     * GET  /off-hire-requests/:id : get the "id" offHireRequest.
     *
     * @param id the id of the offHireRequest to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the offHireRequest, or with status 404 (Not Found)
     */
    @GetMapping("/off-hire-requests/{id}")
    @Timed
    public ResponseEntity<OffHireRequest> getOffHireRequest(@PathVariable Long id) {
        log.debug("REST request to get OffHireRequest : {}", id);
        OffHireRequest offHireRequest = offHireRequestRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(offHireRequest));
    }

    /**
     * DELETE  /off-hire-requests/:id : delete the "id" offHireRequest.
     *
     * @param id the id of the offHireRequest to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/off-hire-requests/{id}")
    @Timed
    public ResponseEntity<Void> deleteOffHireRequest(@PathVariable Long id) {
        log.debug("REST request to delete OffHireRequest : {}", id);
        offHireRequestRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
