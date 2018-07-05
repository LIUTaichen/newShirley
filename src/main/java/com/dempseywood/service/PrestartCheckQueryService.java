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

import com.dempseywood.domain.PrestartCheck;
import com.dempseywood.domain.*; // for static metamodels
import com.dempseywood.repository.PrestartCheckRepository;
import com.dempseywood.service.dto.PrestartCheckCriteria;


/**
 * Service for executing complex queries for PrestartCheck entities in the database.
 * The main input is a {@link PrestartCheckCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PrestartCheck} or a {@link Page} of {@link PrestartCheck} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PrestartCheckQueryService extends QueryService<PrestartCheck> {

    private final Logger log = LoggerFactory.getLogger(PrestartCheckQueryService.class);


    private final PrestartCheckRepository prestartCheckRepository;

    public PrestartCheckQueryService(PrestartCheckRepository prestartCheckRepository) {
        this.prestartCheckRepository = prestartCheckRepository;
    }

    /**
     * Return a {@link List} of {@link PrestartCheck} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PrestartCheck> findByCriteria(PrestartCheckCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<PrestartCheck> specification = createSpecification(criteria);
        return prestartCheckRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link PrestartCheck} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PrestartCheck> findByCriteria(PrestartCheckCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<PrestartCheck> specification = createSpecification(criteria);
        return prestartCheckRepository.findAll(specification, page);
    }

    /**
     * Function to convert PrestartCheckCriteria to a {@link Specifications}
     */
    private Specifications<PrestartCheck> createSpecification(PrestartCheckCriteria criteria) {
        Specifications<PrestartCheck> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), PrestartCheck_.id));
            }
            if (criteria.getProjectId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getProjectId(), PrestartCheck_.project, Project_.id));
            }
            if (criteria.getPlantId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getPlantId(), PrestartCheck_.plant, Plant_.id));
            }
            if (criteria.getLocationId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getLocationId(), PrestartCheck_.location, Location_.id));
            }
            if (criteria.getOperatorId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getOperatorId(), PrestartCheck_.operator, People_.id));
            }
        }
        return specification;
    }

}
