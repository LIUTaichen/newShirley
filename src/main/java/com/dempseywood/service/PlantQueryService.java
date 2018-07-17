package com.dempseywood.service;


import java.util.List;
import java.util.stream.Collectors;

import com.dempseywood.repository.LocationRepository;
import com.dempseywood.service.util.DistanceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.dempseywood.domain.Plant;
import com.dempseywood.domain.*; // for static metamodels
import com.dempseywood.repository.PlantRepository;
import com.dempseywood.service.dto.PlantCriteria;

import com.dempseywood.domain.enumeration.MeterUnit;
import com.dempseywood.domain.enumeration.HireStatus;

/**
 * Service for executing complex queries for Plant entities in the database.
 * The main input is a {@link PlantCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Plant} or a {@link Page} of {@link Plant} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PlantQueryService extends QueryService<Plant> {

    private final Logger log = LoggerFactory.getLogger(PlantQueryService.class);


    private final PlantRepository plantRepository;

    private final LocationRepository locationRepository;

    public PlantQueryService(PlantRepository plantRepository,LocationRepository locationRepository ) {
        this.plantRepository = plantRepository;
        this.locationRepository = locationRepository;
    }

    /**
     * Return a {@link List} of {@link Plant} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Plant> findByCriteria(PlantCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Plant> specification = createSpecification(criteria);
        return plantRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Plant} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Plant> findByCriteria(PlantCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Plant> specification = createSpecification(criteria);
        return plantRepository.findAll(specification, page);
    }

    /**
     * Function to convert PlantCriteria to a {@link Specifications}
     */
    private Specifications<Plant> createSpecification(PlantCriteria criteria) {
        Specifications<Plant> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Plant_.id));
            }
            if (criteria.getFleetId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFleetId(), Plant_.fleetId));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Plant_.name));
            }
            if (criteria.getNotes() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNotes(), Plant_.notes));
            }
            if (criteria.getPurchaseDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPurchaseDate(), Plant_.purchaseDate));
            }
            if (criteria.getIsActive() != null) {
                specification = specification.and(buildSpecification(criteria.getIsActive(), Plant_.isActive));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Plant_.description));
            }
            if (criteria.getVin() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVin(), Plant_.vin));
            }
            if (criteria.getRego() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRego(), Plant_.rego));
            }
            if (criteria.getDateOfManufacture() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateOfManufacture(), Plant_.dateOfManufacture));
            }
            if (criteria.getTankSize() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTankSize(), Plant_.tankSize));
            }
            if (criteria.getMeterReading() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMeterReading(), Plant_.meterReading));
            }
            if (criteria.getMaintenanceDueAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMaintenanceDueAt(), Plant_.maintenanceDueAt));
            }
            if (criteria.getMeterUnit() != null) {
                specification = specification.and(buildSpecification(criteria.getMeterUnit(), Plant_.meterUnit));
            }
            if (criteria.getCertificateDueDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCertificateDueDate(), Plant_.certificateDueDate));
            }
            if (criteria.getRucDueAtKm() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRucDueAtKm(), Plant_.rucDueAtKm));
            }
            if (criteria.getHubboReading() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHubboReading(), Plant_.hubboReading));
            }
            if (criteria.getLoadCapacity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLoadCapacity(), Plant_.loadCapacity));
            }
            if (criteria.getHourlyRate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHourlyRate(), Plant_.hourlyRate));
            }
            if (criteria.getRegistrationDueDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRegistrationDueDate(), Plant_.registrationDueDate));
            }
            if (criteria.getHireStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getHireStatus(), Plant_.hireStatus));
            }
            if (criteria.getGpsDeviceSerial() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGpsDeviceSerial(), Plant_.gpsDeviceSerial));
            }
            if (criteria.getLocationId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getLocationId(), Plant_.location, Location_.id));
            }
            if (criteria.getLastLogId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getLastLogId(), Plant_.lastLog, PlantLog_.id));
            }
            if (criteria.getCategoryId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCategoryId(), Plant_.category, Category_.id));
            }
            if (criteria.getOwnerId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getOwnerId(), Plant_.owner, Company_.id));
            }
            if (criteria.getAssignedContractorId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getAssignedContractorId(), Plant_.assignedContractor, MaintenanceContractor_.id));
            }
            if (criteria.getProjectId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getProjectId(), Plant_.project, Project_.id));
            }
        }
        return specification;
    }

}
