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

import com.dempseywood.domain.PrestartQuestionOption;
import com.dempseywood.domain.*; // for static metamodels
import com.dempseywood.repository.PrestartQuestionOptionRepository;
import com.dempseywood.service.dto.PrestartQuestionOptionCriteria;


/**
 * Service for executing complex queries for PrestartQuestionOption entities in the database.
 * The main input is a {@link PrestartQuestionOptionCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PrestartQuestionOption} or a {@link Page} of {@link PrestartQuestionOption} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PrestartQuestionOptionQueryService extends QueryService<PrestartQuestionOption> {

    private final Logger log = LoggerFactory.getLogger(PrestartQuestionOptionQueryService.class);


    private final PrestartQuestionOptionRepository prestartQuestionOptionRepository;

    public PrestartQuestionOptionQueryService(PrestartQuestionOptionRepository prestartQuestionOptionRepository) {
        this.prestartQuestionOptionRepository = prestartQuestionOptionRepository;
    }

    /**
     * Return a {@link List} of {@link PrestartQuestionOption} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PrestartQuestionOption> findByCriteria(PrestartQuestionOptionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<PrestartQuestionOption> specification = createSpecification(criteria);
        return prestartQuestionOptionRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link PrestartQuestionOption} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PrestartQuestionOption> findByCriteria(PrestartQuestionOptionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<PrestartQuestionOption> specification = createSpecification(criteria);
        return prestartQuestionOptionRepository.findAll(specification, page);
    }

    /**
     * Function to convert PrestartQuestionOptionCriteria to a {@link Specifications}
     */
    private Specifications<PrestartQuestionOption> createSpecification(PrestartQuestionOptionCriteria criteria) {
        Specifications<PrestartQuestionOption> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), PrestartQuestionOption_.id));
            }
            if (criteria.getBody() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBody(), PrestartQuestionOption_.body));
            }
            if (criteria.getIsNormal() != null) {
                specification = specification.and(buildSpecification(criteria.getIsNormal(), PrestartQuestionOption_.isNormal));
            }
            if (criteria.getIsActive() != null) {
                specification = specification.and(buildSpecification(criteria.getIsActive(), PrestartQuestionOption_.isActive));
            }
            if (criteria.getQuestionId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getQuestionId(), PrestartQuestionOption_.question, PrestartQuestion_.id));
            }
        }
        return specification;
    }

}
