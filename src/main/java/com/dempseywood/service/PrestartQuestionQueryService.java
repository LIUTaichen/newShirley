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

import com.dempseywood.domain.PrestartQuestion;
import com.dempseywood.domain.*; // for static metamodels
import com.dempseywood.repository.PrestartQuestionRepository;
import com.dempseywood.service.dto.PrestartQuestionCriteria;


/**
 * Service for executing complex queries for PrestartQuestion entities in the database.
 * The main input is a {@link PrestartQuestionCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PrestartQuestion} or a {@link Page} of {@link PrestartQuestion} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PrestartQuestionQueryService extends QueryService<PrestartQuestion> {

    private final Logger log = LoggerFactory.getLogger(PrestartQuestionQueryService.class);


    private final PrestartQuestionRepository prestartQuestionRepository;

    public PrestartQuestionQueryService(PrestartQuestionRepository prestartQuestionRepository) {
        this.prestartQuestionRepository = prestartQuestionRepository;
    }

    /**
     * Return a {@link List} of {@link PrestartQuestion} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PrestartQuestion> findByCriteria(PrestartQuestionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<PrestartQuestion> specification = createSpecification(criteria);
        return prestartQuestionRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link PrestartQuestion} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PrestartQuestion> findByCriteria(PrestartQuestionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<PrestartQuestion> specification = createSpecification(criteria);
        return prestartQuestionRepository.findAll(specification, page);
    }

    /**
     * Function to convert PrestartQuestionCriteria to a {@link Specifications}
     */
    private Specifications<PrestartQuestion> createSpecification(PrestartQuestionCriteria criteria) {
        Specifications<PrestartQuestion> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), PrestartQuestion_.id));
            }
            if (criteria.getBody() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBody(), PrestartQuestion_.body));
            }
        }
        return specification;
    }

}
