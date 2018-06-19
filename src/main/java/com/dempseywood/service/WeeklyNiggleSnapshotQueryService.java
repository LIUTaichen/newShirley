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

import com.dempseywood.domain.WeeklyNiggleSnapshot;
import com.dempseywood.domain.*; // for static metamodels
import com.dempseywood.repository.WeeklyNiggleSnapshotRepository;
import com.dempseywood.service.dto.WeeklyNiggleSnapshotCriteria;

import com.dempseywood.domain.enumeration.Status;
import com.dempseywood.domain.enumeration.Priority;

/**
 * Service for executing complex queries for WeeklyNiggleSnapshot entities in the database.
 * The main input is a {@link WeeklyNiggleSnapshotCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link WeeklyNiggleSnapshot} or a {@link Page} of {@link WeeklyNiggleSnapshot} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class WeeklyNiggleSnapshotQueryService extends QueryService<WeeklyNiggleSnapshot> {

    private final Logger log = LoggerFactory.getLogger(WeeklyNiggleSnapshotQueryService.class);


    private final WeeklyNiggleSnapshotRepository weeklyNiggleSnapshotRepository;

    public WeeklyNiggleSnapshotQueryService(WeeklyNiggleSnapshotRepository weeklyNiggleSnapshotRepository) {
        this.weeklyNiggleSnapshotRepository = weeklyNiggleSnapshotRepository;
    }

    /**
     * Return a {@link List} of {@link WeeklyNiggleSnapshot} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<WeeklyNiggleSnapshot> findByCriteria(WeeklyNiggleSnapshotCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<WeeklyNiggleSnapshot> specification = createSpecification(criteria);
        return weeklyNiggleSnapshotRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link WeeklyNiggleSnapshot} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<WeeklyNiggleSnapshot> findByCriteria(WeeklyNiggleSnapshotCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<WeeklyNiggleSnapshot> specification = createSpecification(criteria);
        return weeklyNiggleSnapshotRepository.findAll(specification, page);
    }

    /**
     * Function to convert WeeklyNiggleSnapshotCriteria to a {@link Specifications}
     */
    private Specifications<WeeklyNiggleSnapshot> createSpecification(WeeklyNiggleSnapshotCriteria criteria) {
        Specifications<WeeklyNiggleSnapshot> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), WeeklyNiggleSnapshot_.id));
            }
            if (criteria.getWeekEndingOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWeekEndingOn(), WeeklyNiggleSnapshot_.weekEndingOn));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), WeeklyNiggleSnapshot_.status));
            }
            if (criteria.getPriority() != null) {
                specification = specification.and(buildSpecification(criteria.getPriority(), WeeklyNiggleSnapshot_.priority));
            }
            if (criteria.getCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCount(), WeeklyNiggleSnapshot_.count));
            }
            if (criteria.getAgeOfOldest() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAgeOfOldest(), WeeklyNiggleSnapshot_.ageOfOldest));
            }
        }
        return specification;
    }

}
