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

import com.dempseywood.domain.PrestartCheckConfig;
import com.dempseywood.domain.*; // for static metamodels
import com.dempseywood.repository.PrestartCheckConfigRepository;
import com.dempseywood.service.dto.PrestartCheckConfigCriteria;


/**
 * Service for executing complex queries for PrestartCheckConfig entities in the database.
 * The main input is a {@link PrestartCheckConfigCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PrestartCheckConfig} or a {@link Page} of {@link PrestartCheckConfig} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PrestartCheckConfigQueryService extends QueryService<PrestartCheckConfig> {

    private final Logger log = LoggerFactory.getLogger(PrestartCheckConfigQueryService.class);


    private final PrestartCheckConfigRepository prestartCheckConfigRepository;

    public PrestartCheckConfigQueryService(PrestartCheckConfigRepository prestartCheckConfigRepository) {
        this.prestartCheckConfigRepository = prestartCheckConfigRepository;
    }

    /**
     * Return a {@link List} of {@link PrestartCheckConfig} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PrestartCheckConfig> findByCriteria(PrestartCheckConfigCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<PrestartCheckConfig> specification = createSpecification(criteria);
        return prestartCheckConfigRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link PrestartCheckConfig} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PrestartCheckConfig> findByCriteria(PrestartCheckConfigCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<PrestartCheckConfig> specification = createSpecification(criteria);
        return prestartCheckConfigRepository.findAll(specification, page);
    }

    /**
     * Function to convert PrestartCheckConfigCriteria to a {@link Specifications}
     */
    private Specifications<PrestartCheckConfig> createSpecification(PrestartCheckConfigCriteria criteria) {
        Specifications<PrestartCheckConfig> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), PrestartCheckConfig_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), PrestartCheckConfig_.name));
            }
            if (criteria.getQuestionlistId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getQuestionlistId(), PrestartCheckConfig_.questionlists, PrestartCheckQuestionListItem_.id));
            }
        }
        return specification;
    }

}
