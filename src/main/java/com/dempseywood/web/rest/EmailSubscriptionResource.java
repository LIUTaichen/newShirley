package com.dempseywood.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.dempseywood.domain.EmailSubscription;

import com.dempseywood.repository.EmailSubscriptionRepository;
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
 * REST controller for managing EmailSubscription.
 */
@RestController
@RequestMapping("/api")
public class EmailSubscriptionResource {

    private final Logger log = LoggerFactory.getLogger(EmailSubscriptionResource.class);

    private static final String ENTITY_NAME = "emailSubscription";

    private final EmailSubscriptionRepository emailSubscriptionRepository;

    public EmailSubscriptionResource(EmailSubscriptionRepository emailSubscriptionRepository) {
        this.emailSubscriptionRepository = emailSubscriptionRepository;
    }

    /**
     * POST  /email-subscriptions : Create a new emailSubscription.
     *
     * @param emailSubscription the emailSubscription to create
     * @return the ResponseEntity with status 201 (Created) and with body the new emailSubscription, or with status 400 (Bad Request) if the emailSubscription has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/email-subscriptions")
    @Timed
    public ResponseEntity<EmailSubscription> createEmailSubscription(@RequestBody EmailSubscription emailSubscription) throws URISyntaxException {
        log.debug("REST request to save EmailSubscription : {}", emailSubscription);
        if (emailSubscription.getId() != null) {
            throw new BadRequestAlertException("A new emailSubscription cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmailSubscription result = emailSubscriptionRepository.save(emailSubscription);
        return ResponseEntity.created(new URI("/api/email-subscriptions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /email-subscriptions : Updates an existing emailSubscription.
     *
     * @param emailSubscription the emailSubscription to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated emailSubscription,
     * or with status 400 (Bad Request) if the emailSubscription is not valid,
     * or with status 500 (Internal Server Error) if the emailSubscription couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/email-subscriptions")
    @Timed
    public ResponseEntity<EmailSubscription> updateEmailSubscription(@RequestBody EmailSubscription emailSubscription) throws URISyntaxException {
        log.debug("REST request to update EmailSubscription : {}", emailSubscription);
        if (emailSubscription.getId() == null) {
            return createEmailSubscription(emailSubscription);
        }
        EmailSubscription result = emailSubscriptionRepository.save(emailSubscription);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, emailSubscription.getId().toString()))
            .body(result);
    }

    /**
     * GET  /email-subscriptions : get all the emailSubscriptions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of emailSubscriptions in body
     */
    @GetMapping("/email-subscriptions")
    @Timed
    public List<EmailSubscription> getAllEmailSubscriptions() {
        log.debug("REST request to get all EmailSubscriptions");
        return emailSubscriptionRepository.findAll();
        }

    /**
     * GET  /email-subscriptions/:id : get the "id" emailSubscription.
     *
     * @param id the id of the emailSubscription to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the emailSubscription, or with status 404 (Not Found)
     */
    @GetMapping("/email-subscriptions/{id}")
    @Timed
    public ResponseEntity<EmailSubscription> getEmailSubscription(@PathVariable Long id) {
        log.debug("REST request to get EmailSubscription : {}", id);
        EmailSubscription emailSubscription = emailSubscriptionRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(emailSubscription));
    }

    /**
     * DELETE  /email-subscriptions/:id : delete the "id" emailSubscription.
     *
     * @param id the id of the emailSubscription to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/email-subscriptions/{id}")
    @Timed
    public ResponseEntity<Void> deleteEmailSubscription(@PathVariable Long id) {
        log.debug("REST request to delete EmailSubscription : {}", id);
        emailSubscriptionRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
