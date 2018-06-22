package com.dempseywood.service;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.dempseywood.domain.NiggleSnapshot;
import com.dempseywood.domain.*; // for static metamodels
import com.dempseywood.repository.NiggleSnapshotRepository;
import com.dempseywood.service.dto.NiggleSnapshotCriteria;

import com.dempseywood.domain.enumeration.Status;
import com.dempseywood.domain.enumeration.Priority;

/**
 * Service for executing complex queries for NiggleSnapshot entities in the database.
 * The main input is a {@link NiggleSnapshotCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link NiggleSnapshot} or a {@link Page} of {@link NiggleSnapshot} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class NiggleSnapshotQueryService extends QueryService<NiggleSnapshot> {

    private final Logger log = LoggerFactory.getLogger(NiggleSnapshotQueryService.class);


    private final NiggleSnapshotRepository niggleSnapshotRepository;

    public NiggleSnapshotQueryService(NiggleSnapshotRepository niggleSnapshotRepository) {
        this.niggleSnapshotRepository = niggleSnapshotRepository;
    }

    /**
     * Return a {@link List} of {@link NiggleSnapshot} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<NiggleSnapshot> findByCriteria(NiggleSnapshotCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<NiggleSnapshot> specification = createSpecification(criteria);
        return niggleSnapshotRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link NiggleSnapshot} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<NiggleSnapshot> findByCriteria(NiggleSnapshotCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<NiggleSnapshot> specification = createSpecification(criteria);
        return niggleSnapshotRepository.findAll(specification, page);
    }

    /**
     * Function to convert NiggleSnapshotCriteria to a {@link Specifications}
     */
    private Specifications<NiggleSnapshot> createSpecification(NiggleSnapshotCriteria criteria) {
        Specifications<NiggleSnapshot> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), NiggleSnapshot_.id));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate(), NiggleSnapshot_.date));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), NiggleSnapshot_.status));
            }
            if (criteria.getPriority() != null) {
                specification = specification.and(buildSpecification(criteria.getPriority(), NiggleSnapshot_.priority));
            }
            if (criteria.getCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCount(), NiggleSnapshot_.count));
            }
            if (criteria.getAgeOfOldest() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAgeOfOldest(), NiggleSnapshot_.ageOfOldest));
            }
        }
        return specification;
    }

}
