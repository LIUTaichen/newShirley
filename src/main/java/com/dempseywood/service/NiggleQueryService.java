package com.dempseywood.service;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.dempseywood.domain.Niggle;
import com.dempseywood.domain.*; // for static metamodels
import com.dempseywood.repository.NiggleRepository;
import com.dempseywood.service.dto.NiggleCriteria;

import com.dempseywood.domain.enumeration.Status;
import com.dempseywood.domain.enumeration.Priority;

/**
 * Service for executing complex queries for Niggle entities in the database.
 * The main input is a {@link NiggleCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Niggle} or a {@link Page} of {@link Niggle} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class NiggleQueryService extends QueryService<Niggle> {

    private final Logger log = LoggerFactory.getLogger(NiggleQueryService.class);


    private final NiggleRepository niggleRepository;

    public NiggleQueryService(NiggleRepository niggleRepository) {
        this.niggleRepository = niggleRepository;
    }

    /**
     * Return a {@link List} of {@link Niggle} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Niggle> findByCriteria(NiggleCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Niggle> specification = createSpecification(criteria);
        return niggleRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Niggle} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Niggle> findByCriteria(NiggleCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Niggle> specification = createSpecification(criteria);
        return niggleRepository.findAll(specification, page);
    }

    /**
     * Function to convert NiggleCriteria to a {@link Specifications}
     */
    private Specifications<Niggle> createSpecification(NiggleCriteria criteria) {
        Specifications<Niggle> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Niggle_.id));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Niggle_.description));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), Niggle_.status));
            }
            if (criteria.getNote() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNote(), Niggle_.note));
            }
            if (criteria.getPriority() != null) {
                specification = specification.and(buildSpecification(criteria.getPriority(), Niggle_.priority));
            }
            if (criteria.getQuattraReference() != null) {
                specification = specification.and(buildStringSpecification(criteria.getQuattraReference(), Niggle_.quattraReference));
            }
            if (criteria.getQuattraComments() != null) {
                specification = specification.and(buildStringSpecification(criteria.getQuattraComments(), Niggle_.quattraComments));
            }
            if (criteria.getInvoiceNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInvoiceNo(), Niggle_.invoiceNo));
            }
            if (criteria.getDateOpened() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateOpened(), Niggle_.dateOpened));
            }
            if (criteria.getDateClosed() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateClosed(), Niggle_.dateClosed));
            }
            if (criteria.getPurchaseOrderId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getPurchaseOrderId(), Niggle_.purchaseOrder, PurchaseOrder_.id));
            }
            if (criteria.getPlantId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getPlantId(), Niggle_.plant, Plant_.id));
            }
            if (criteria.getAssignedContractorId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getAssignedContractorId(), Niggle_.assignedContractor, MaintenanceContractor_.id));
            }
        }
        return specification;
    }

}
