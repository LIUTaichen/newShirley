package com.dempseywood.service;

import com.dempseywood.domain.Niggle;
import com.dempseywood.domain.PurchaseOrder;
import com.dempseywood.domain.enumeration.Status;
import com.dempseywood.repository.NiggleRepository;
import com.dempseywood.repository.PurchaseOrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

/**
 * Service Implementation for managing Niggle.
 */
@Service
@Transactional
public class NiggleService {

    private final Logger log = LoggerFactory.getLogger(NiggleService.class);

    private final NiggleRepository niggleRepository;

    private final PurchaseOrderRepository purchaseOrderRepository;



    public NiggleService(NiggleRepository niggleRepository, PurchaseOrderRepository purchaseOrderRepository) {
        this.niggleRepository = niggleRepository;
        this.purchaseOrderRepository = purchaseOrderRepository;
    }

    /**
     * Save a niggle.
     *
     * @param niggle the entity to save
     * @return the persisted entity
     */
    public Niggle save(Niggle niggle) {
        log.debug("Request to save Niggle : {}", niggle);
        if(Status.OPEN == niggle.getStatus() && niggle.getDateOpened() == null){
            log.debug("Niggle is being opened. Creating purchase order and setting timestamp for dateOpened");
            PurchaseOrder newPurchaseOrder = purchaseOrderRepository.save(new PurchaseOrder());
            niggle.setPurchaseOrder(newPurchaseOrder);
            niggle.setDateOpened(Instant.now());
        }

        if(Status.CLOSED == niggle.getStatus() && niggle.getDateClosed() == null){
            log.debug("Niggle is being closed. Setting timestamp for dateClosed");
            niggle.setDateClosed(Instant.now());
        }
        return niggleRepository.save(niggle);
    }

    /**
     * Get all the niggles.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Niggle> findAll() {
        log.debug("Request to get all Niggles");
        return niggleRepository.findAll();
    }

    /**
     * Get one niggle by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Niggle findOne(Long id) {
        log.debug("Request to get Niggle : {}", id);
        return niggleRepository.findOne(id);
    }

    /**
     * Delete the niggle by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Niggle : {}", id);
        niggleRepository.delete(id);
    }
}
