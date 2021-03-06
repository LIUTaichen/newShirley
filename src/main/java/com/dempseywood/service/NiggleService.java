package com.dempseywood.service;

import com.dempseywood.domain.Niggle;
import com.dempseywood.domain.PurchaseOrder;
import com.dempseywood.domain.User;
import com.dempseywood.domain.enumeration.Priority;
import com.dempseywood.domain.enumeration.Status;
import com.dempseywood.repository.NiggleRepository;
import com.dempseywood.repository.PurchaseOrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public static final String DEMPSEY_WOOD_PURCHASE_ORDER_PREFIX = "DW";
    public static final String QUATTRA_NAME = "Quattra";
    private final Logger log = LoggerFactory.getLogger(NiggleService.class);

    private final NiggleRepository niggleRepository;

    private final PurchaseOrderRepository purchaseOrderRepository;

    private final MailService mailService;

    public NiggleService(NiggleRepository niggleRepository, PurchaseOrderRepository purchaseOrderRepository,
                         MailService mailService) {
        this.niggleRepository = niggleRepository;
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.mailService = mailService;
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
            newPurchaseOrder.setOrderNumber(DEMPSEY_WOOD_PURCHASE_ORDER_PREFIX +newPurchaseOrder.getId());
            niggle.setPurchaseOrder(newPurchaseOrder);
            niggle.setDateOpened(Instant.now());
            if(Priority.HIGH.equals(niggle.getPriority()) && QUATTRA_NAME.equals(niggle.getAssignedContractor().getName())){
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                authentication.getName();
                String username = authentication.getName();
                mailService.sendHighPriorityNotificationMail( niggle, username);
            }
        }

        if(Status.CLOSED == niggle.getStatus() && niggle.getDateClosed() == null){
            log.debug("Niggle is being closed. Setting timestamp for dateClosed");
            niggle.setDateClosed(Instant.now());
        }

        if(Status.COMPLETED == niggle.getStatus() && niggle.getDateCompleted() == null){
            log.debug("Niggle is being completed. Setting timestamp for dateCompleted");
            niggle.setDateCompleted(Instant.now());
        }

        if(Status.ON_HOLD == niggle.getStatus()){
            Niggle dbNiggle = niggleRepository.findOne(niggle.getId());
            if(Status.ON_HOLD != dbNiggle.getStatus()){
                log.debug("Niggle is being put on hold. Sending notification email");
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                authentication.getName();
                String username = authentication.getName();
                mailService.sendOnHoldNotificationMail( niggle, username);
            }
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
