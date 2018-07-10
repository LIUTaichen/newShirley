package com.dempseywood.service.dto;

import java.io.Serializable;
import com.dempseywood.domain.enumeration.MeterUnit;
import com.dempseywood.domain.enumeration.HireStatus;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

import io.github.jhipster.service.filter.InstantFilter;




/**
 * Criteria class for the Plant entity. This class is used in PlantResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /plants?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PlantCriteria implements Serializable {
    /**
     * Class for filtering MeterUnit
     */
    public static class MeterUnitFilter extends Filter<MeterUnit> {
    }

    /**
     * Class for filtering HireStatus
     */
    public static class HireStatusFilter extends Filter<HireStatus> {
    }

    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter fleetId;

    private StringFilter name;

    private StringFilter notes;

    private InstantFilter purchaseDate;

    private BooleanFilter isActive;

    private StringFilter description;

    private StringFilter vin;

    private StringFilter rego;

    private InstantFilter dateOfManufacture;

    private IntegerFilter tankSize;

    private IntegerFilter meterReading;

    private IntegerFilter maintenanceDueAt;

    private MeterUnitFilter meterUnit;

    private InstantFilter certificateDueDate;

    private IntegerFilter rucDueAtKm;

    private IntegerFilter hubboReading;

    private IntegerFilter loadCapacity;

    private DoubleFilter hourlyRate;

    private InstantFilter registrationDueDate;

    private HireStatusFilter hireStatus;

    private StringFilter gpsDeviceSerial;

    private LongFilter locationId;

    private LongFilter lastLogId;

    private LongFilter categoryId;

    private LongFilter ownerId;

    private LongFilter assignedContractorId;

    private LongFilter projectId;

    public PlantCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getFleetId() {
        return fleetId;
    }

    public void setFleetId(StringFilter fleetId) {
        this.fleetId = fleetId;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getNotes() {
        return notes;
    }

    public void setNotes(StringFilter notes) {
        this.notes = notes;
    }

    public InstantFilter getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(InstantFilter purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public BooleanFilter getIsActive() {
        return isActive;
    }

    public void setIsActive(BooleanFilter isActive) {
        this.isActive = isActive;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public StringFilter getVin() {
        return vin;
    }

    public void setVin(StringFilter vin) {
        this.vin = vin;
    }

    public StringFilter getRego() {
        return rego;
    }

    public void setRego(StringFilter rego) {
        this.rego = rego;
    }

    public InstantFilter getDateOfManufacture() {
        return dateOfManufacture;
    }

    public void setDateOfManufacture(InstantFilter dateOfManufacture) {
        this.dateOfManufacture = dateOfManufacture;
    }

    public IntegerFilter getTankSize() {
        return tankSize;
    }

    public void setTankSize(IntegerFilter tankSize) {
        this.tankSize = tankSize;
    }

    public IntegerFilter getMeterReading() {
        return meterReading;
    }

    public void setMeterReading(IntegerFilter meterReading) {
        this.meterReading = meterReading;
    }

    public IntegerFilter getMaintenanceDueAt() {
        return maintenanceDueAt;
    }

    public void setMaintenanceDueAt(IntegerFilter maintenanceDueAt) {
        this.maintenanceDueAt = maintenanceDueAt;
    }

    public MeterUnitFilter getMeterUnit() {
        return meterUnit;
    }

    public void setMeterUnit(MeterUnitFilter meterUnit) {
        this.meterUnit = meterUnit;
    }

    public InstantFilter getCertificateDueDate() {
        return certificateDueDate;
    }

    public void setCertificateDueDate(InstantFilter certificateDueDate) {
        this.certificateDueDate = certificateDueDate;
    }

    public IntegerFilter getRucDueAtKm() {
        return rucDueAtKm;
    }

    public void setRucDueAtKm(IntegerFilter rucDueAtKm) {
        this.rucDueAtKm = rucDueAtKm;
    }

    public IntegerFilter getHubboReading() {
        return hubboReading;
    }

    public void setHubboReading(IntegerFilter hubboReading) {
        this.hubboReading = hubboReading;
    }

    public IntegerFilter getLoadCapacity() {
        return loadCapacity;
    }

    public void setLoadCapacity(IntegerFilter loadCapacity) {
        this.loadCapacity = loadCapacity;
    }

    public DoubleFilter getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(DoubleFilter hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public InstantFilter getRegistrationDueDate() {
        return registrationDueDate;
    }

    public void setRegistrationDueDate(InstantFilter registrationDueDate) {
        this.registrationDueDate = registrationDueDate;
    }

    public HireStatusFilter getHireStatus() {
        return hireStatus;
    }

    public void setHireStatus(HireStatusFilter hireStatus) {
        this.hireStatus = hireStatus;
    }

    public StringFilter getGpsDeviceSerial() {
        return gpsDeviceSerial;
    }

    public void setGpsDeviceSerial(StringFilter gpsDeviceSerial) {
        this.gpsDeviceSerial = gpsDeviceSerial;
    }

    public LongFilter getLocationId() {
        return locationId;
    }

    public void setLocationId(LongFilter locationId) {
        this.locationId = locationId;
    }

    public LongFilter getLastLogId() {
        return lastLogId;
    }

    public void setLastLogId(LongFilter lastLogId) {
        this.lastLogId = lastLogId;
    }

    public LongFilter getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(LongFilter categoryId) {
        this.categoryId = categoryId;
    }

    public LongFilter getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(LongFilter ownerId) {
        this.ownerId = ownerId;
    }

    public LongFilter getAssignedContractorId() {
        return assignedContractorId;
    }

    public void setAssignedContractorId(LongFilter assignedContractorId) {
        this.assignedContractorId = assignedContractorId;
    }

    public LongFilter getProjectId() {
        return projectId;
    }

    public void setProjectId(LongFilter projectId) {
        this.projectId = projectId;
    }

    @Override
    public String toString() {
        return "PlantCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (fleetId != null ? "fleetId=" + fleetId + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (notes != null ? "notes=" + notes + ", " : "") +
                (purchaseDate != null ? "purchaseDate=" + purchaseDate + ", " : "") +
                (isActive != null ? "isActive=" + isActive + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (vin != null ? "vin=" + vin + ", " : "") +
                (rego != null ? "rego=" + rego + ", " : "") +
                (dateOfManufacture != null ? "dateOfManufacture=" + dateOfManufacture + ", " : "") +
                (tankSize != null ? "tankSize=" + tankSize + ", " : "") +
                (meterReading != null ? "meterReading=" + meterReading + ", " : "") +
                (maintenanceDueAt != null ? "maintenanceDueAt=" + maintenanceDueAt + ", " : "") +
                (meterUnit != null ? "meterUnit=" + meterUnit + ", " : "") +
                (certificateDueDate != null ? "certificateDueDate=" + certificateDueDate + ", " : "") +
                (rucDueAtKm != null ? "rucDueAtKm=" + rucDueAtKm + ", " : "") +
                (hubboReading != null ? "hubboReading=" + hubboReading + ", " : "") +
                (loadCapacity != null ? "loadCapacity=" + loadCapacity + ", " : "") +
                (hourlyRate != null ? "hourlyRate=" + hourlyRate + ", " : "") +
                (registrationDueDate != null ? "registrationDueDate=" + registrationDueDate + ", " : "") +
                (hireStatus != null ? "hireStatus=" + hireStatus + ", " : "") +
                (gpsDeviceSerial != null ? "gpsDeviceSerial=" + gpsDeviceSerial + ", " : "") +
                (locationId != null ? "locationId=" + locationId + ", " : "") +
                (lastLogId != null ? "lastLogId=" + lastLogId + ", " : "") +
                (categoryId != null ? "categoryId=" + categoryId + ", " : "") +
                (ownerId != null ? "ownerId=" + ownerId + ", " : "") +
                (assignedContractorId != null ? "assignedContractorId=" + assignedContractorId + ", " : "") +
                (projectId != null ? "projectId=" + projectId + ", " : "") +
            "}";
    }

}
