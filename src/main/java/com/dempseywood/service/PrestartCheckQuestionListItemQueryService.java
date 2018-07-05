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

import com.dempseywood.domain.PrestartCheckQuestionListItem;
import com.dempseywood.domain.*; // for static metamodels
import com.dempseywood.repository.PrestartCheckQuestionListItemRepository;
import com.dempseywood.service.dto.PrestartCheckQuestionListItemCriteria;


/**
 * Service for executing complex queries for PrestartCheckQuestionListItem entities in the database.
 * The main input is a {@link PrestartCheckQuestionListItemCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PrestartCheckQuestionListItem} or a {@link Page} of {@link PrestartCheckQuestionListItem} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PrestartCheckQuestionListItemQueryService extends QueryService<PrestartCheckQuestionListItem> {

    private final Logger log = LoggerFactory.getLogger(PrestartCheckQuestionListItemQueryService.class);


    private final PrestartCheckQuestionListItemRepository prestartCheckQuestionListItemRepository;

    public PrestartCheckQuestionListItemQueryService(PrestartCheckQuestionListItemRepository prestartCheckQuestionListItemRepository) {
        this.prestartCheckQuestionListItemRepository = prestartCheckQuestionListItemRepository;
    }

    /**
     * Return a {@link List} of {@link PrestartCheckQuestionListItem} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PrestartCheckQuestionListItem> findByCriteria(PrestartCheckQuestionListItemCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<PrestartCheckQuestionListItem> specification = createSpecification(criteria);
        return prestartCheckQuestionListItemRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link PrestartCheckQuestionListItem} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PrestartCheckQuestionListItem> findByCriteria(PrestartCheckQuestionListItemCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<PrestartCheckQuestionListItem> specification = createSpecification(criteria);
        return prestartCheckQuestionListItemRepository.findAll(specification, page);
    }

    /**
     * Function to convert PrestartCheckQuestionListItemCriteria to a {@link Specifications}
     */
    private Specifications<PrestartCheckQuestionListItem> createSpecification(PrestartCheckQuestionListItemCriteria criteria) {
        Specifications<PrestartCheckQuestionListItem> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), PrestartCheckQuestionListItem_.id));
            }
            if (criteria.getOrder() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOrder(), PrestartCheckQuestionListItem_.order));
            }
            if (criteria.getPrestartCheckConfigId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getPrestartCheckConfigId(), PrestartCheckQuestionListItem_.prestartCheckConfig, PrestartCheckConfig_.id));
            }
            if (criteria.getQuestionId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getQuestionId(), PrestartCheckQuestionListItem_.question, PrestartQuestion_.id));
            }
        }
        return specification;
    }

}
