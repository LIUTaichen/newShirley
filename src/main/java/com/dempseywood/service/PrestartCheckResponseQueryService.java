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

import com.dempseywood.domain.PrestartCheckResponse;
import com.dempseywood.domain.*; // for static metamodels
import com.dempseywood.repository.PrestartCheckResponseRepository;
import com.dempseywood.service.dto.PrestartCheckResponseCriteria;


/**
 * Service for executing complex queries for PrestartCheckResponse entities in the database.
 * The main input is a {@link PrestartCheckResponseCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PrestartCheckResponse} or a {@link Page} of {@link PrestartCheckResponse} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PrestartCheckResponseQueryService extends QueryService<PrestartCheckResponse> {

    private final Logger log = LoggerFactory.getLogger(PrestartCheckResponseQueryService.class);


    private final PrestartCheckResponseRepository prestartCheckResponseRepository;

    public PrestartCheckResponseQueryService(PrestartCheckResponseRepository prestartCheckResponseRepository) {
        this.prestartCheckResponseRepository = prestartCheckResponseRepository;
    }

    /**
     * Return a {@link List} of {@link PrestartCheckResponse} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PrestartCheckResponse> findByCriteria(PrestartCheckResponseCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<PrestartCheckResponse> specification = createSpecification(criteria);
        return prestartCheckResponseRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link PrestartCheckResponse} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PrestartCheckResponse> findByCriteria(PrestartCheckResponseCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<PrestartCheckResponse> specification = createSpecification(criteria);
        return prestartCheckResponseRepository.findAll(specification, page);
    }

    /**
     * Function to convert PrestartCheckResponseCriteria to a {@link Specifications}
     */
    private Specifications<PrestartCheckResponse> createSpecification(PrestartCheckResponseCriteria criteria) {
        Specifications<PrestartCheckResponse> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), PrestartCheckResponse_.id));
            }
            if (criteria.getQuestionId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getQuestionId(), PrestartCheckResponse_.question, PrestartQuestion_.id));
            }
            if (criteria.getResponseId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getResponseId(), PrestartCheckResponse_.response, PrestartQuestionOption_.id));
            }
            if (criteria.getPrestartCheckId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getPrestartCheckId(), PrestartCheckResponse_.prestartCheck, PrestartCheck_.id));
            }
        }
        return specification;
    }

}
