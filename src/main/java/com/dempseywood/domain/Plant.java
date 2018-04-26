package com.dempseywood.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import com.dempseywood.domain.enumeration.MeterUnit;

import com.dempseywood.domain.enumeration.HireStatus;

/**
 * A Plant.
 */
@Entity
@Table(name = "plant")
public class Plant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "fleet_id")
    private String fleetId;

    @Column(name = "name")
    private String name;

    @Column(name = "notes")
    private String notes;

    @Column(name = "purchase_date")
    private Instant purchaseDate;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "description")
    private String description;

    @Column(name = "vin")
    private String vin;

    @Column(name = "rego")
    private String rego;

    @Column(name = "date_of_manufacture")
    private Instant dateOfManufacture;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @Column(name = "tank_size")
    private Integer tankSize;

    @Column(name = "maintenance_due_at")
    private Integer maintenanceDueAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "meter_unit")
    private MeterUnit meterUnit;

    @Column(name = "certificate_due_date")
    private Instant certificateDueDate;

    @Column(name = "ruc_due_at_km")
    private Integer rucDueAtKm;

    @Column(name = "hubbo_reading")
    private Integer hubboReading;

    @Column(name = "load_capacity")
    private Integer loadCapacity;

    @Column(name = "hourly_rate")
    private Double hourlyRate;

    @Column(name = "registration_due_date")
    private Instant registrationDueDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "hire_status")
    private HireStatus hireStatus;

    @Column(name = "gps_device_serial")
    private String gpsDeviceSerial;

    @Column(name = "location")
    private String location;

    @Column(name = "last_location_update_time")
    private Instant lastLocationUpdateTime;

    @ManyToOne
    private Category category;

    @ManyToOne
    private Company owner;

    @ManyToOne
    private MaintenanceContractor assignedContractor;

    @ManyToOne
    private Project project;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFleetId() {
        return fleetId;
    }

    public Plant fleetId(String fleetId) {
        this.fleetId = fleetId;
        return this;
    }

    public void setFleetId(String fleetId) {
        this.fleetId = fleetId;
    }

    public String getName() {
        return name;
    }

    public Plant name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotes() {
        return notes;
    }

    public Plant notes(String notes) {
        this.notes = notes;
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Instant getPurchaseDate() {
        return purchaseDate;
    }

    public Plant purchaseDate(Instant purchaseDate) {
        this.purchaseDate = purchaseDate;
        return this;
    }

    public void setPurchaseDate(Instant purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Boolean isIsActive() {
        return isActive;
    }

    public Plant isActive(Boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getDescription() {
        return description;
    }

    public Plant description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVin() {
        return vin;
    }

    public Plant vin(String vin) {
        this.vin = vin;
        return this;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getRego() {
        return rego;
    }

    public Plant rego(String rego) {
        this.rego = rego;
        return this;
    }

    public void setRego(String rego) {
        this.rego = rego;
    }

    public Instant getDateOfManufacture() {
        return dateOfManufacture;
    }

    public Plant dateOfManufacture(Instant dateOfManufacture) {
        this.dateOfManufacture = dateOfManufacture;
        return this;
    }

    public void setDateOfManufacture(Instant dateOfManufacture) {
        this.dateOfManufacture = dateOfManufacture;
    }

    public byte[] getImage() {
        return image;
    }

    public Plant image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public Plant imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public Integer getTankSize() {
        return tankSize;
    }

    public Plant tankSize(Integer tankSize) {
        this.tankSize = tankSize;
        return this;
    }

    public void setTankSize(Integer tankSize) {
        this.tankSize = tankSize;
    }

    public Integer getMaintenanceDueAt() {
        return maintenanceDueAt;
    }

    public Plant maintenanceDueAt(Integer maintenanceDueAt) {
        this.maintenanceDueAt = maintenanceDueAt;
        return this;
    }

    public void setMaintenanceDueAt(Integer maintenanceDueAt) {
        this.maintenanceDueAt = maintenanceDueAt;
    }

    public MeterUnit getMeterUnit() {
        return meterUnit;
    }

    public Plant meterUnit(MeterUnit meterUnit) {
        this.meterUnit = meterUnit;
        return this;
    }

    public void setMeterUnit(MeterUnit meterUnit) {
        this.meterUnit = meterUnit;
    }

    public Instant getCertificateDueDate() {
        return certificateDueDate;
    }

    public Plant certificateDueDate(Instant certificateDueDate) {
        this.certificateDueDate = certificateDueDate;
        return this;
    }

    public void setCertificateDueDate(Instant certificateDueDate) {
        this.certificateDueDate = certificateDueDate;
    }

    public Integer getRucDueAtKm() {
        return rucDueAtKm;
    }

    public Plant rucDueAtKm(Integer rucDueAtKm) {
        this.rucDueAtKm = rucDueAtKm;
        return this;
    }

    public void setRucDueAtKm(Integer rucDueAtKm) {
        this.rucDueAtKm = rucDueAtKm;
    }

    public Integer getHubboReading() {
        return hubboReading;
    }

    public Plant hubboReading(Integer hubboReading) {
        this.hubboReading = hubboReading;
        return this;
    }

    public void setHubboReading(Integer hubboReading) {
        this.hubboReading = hubboReading;
    }

    public Integer getLoadCapacity() {
        return loadCapacity;
    }

    public Plant loadCapacity(Integer loadCapacity) {
        this.loadCapacity = loadCapacity;
        return this;
    }

    public void setLoadCapacity(Integer loadCapacity) {
        this.loadCapacity = loadCapacity;
    }

    public Double getHourlyRate() {
        return hourlyRate;
    }

    public Plant hourlyRate(Double hourlyRate) {
        this.hourlyRate = hourlyRate;
        return this;
    }

    public void setHourlyRate(Double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public Instant getRegistrationDueDate() {
        return registrationDueDate;
    }

    public Plant registrationDueDate(Instant registrationDueDate) {
        this.registrationDueDate = registrationDueDate;
        return this;
    }

    public void setRegistrationDueDate(Instant registrationDueDate) {
        this.registrationDueDate = registrationDueDate;
    }

    public HireStatus getHireStatus() {
        return hireStatus;
    }

    public Plant hireStatus(HireStatus hireStatus) {
        this.hireStatus = hireStatus;
        return this;
    }

    public void setHireStatus(HireStatus hireStatus) {
        this.hireStatus = hireStatus;
    }

    public String getGpsDeviceSerial() {
        return gpsDeviceSerial;
    }

    public Plant gpsDeviceSerial(String gpsDeviceSerial) {
        this.gpsDeviceSerial = gpsDeviceSerial;
        return this;
    }

    public void setGpsDeviceSerial(String gpsDeviceSerial) {
        this.gpsDeviceSerial = gpsDeviceSerial;
    }

    public String getLocation() {
        return location;
    }

    public Plant location(String location) {
        this.location = location;
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Instant getLastLocationUpdateTime() {
        return lastLocationUpdateTime;
    }

    public Plant lastLocationUpdateTime(Instant lastLocationUpdateTime) {
        this.lastLocationUpdateTime = lastLocationUpdateTime;
        return this;
    }

    public void setLastLocationUpdateTime(Instant lastLocationUpdateTime) {
        this.lastLocationUpdateTime = lastLocationUpdateTime;
    }

    public Category getCategory() {
        return category;
    }

    public Plant category(Category category) {
        this.category = category;
        return this;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Company getOwner() {
        return owner;
    }

    public Plant owner(Company company) {
        this.owner = company;
        return this;
    }

    public void setOwner(Company company) {
        this.owner = company;
    }

    public MaintenanceContractor getAssignedContractor() {
        return assignedContractor;
    }

    public Plant assignedContractor(MaintenanceContractor maintenanceContractor) {
        this.assignedContractor = maintenanceContractor;
        return this;
    }

    public void setAssignedContractor(MaintenanceContractor maintenanceContractor) {
        this.assignedContractor = maintenanceContractor;
    }

    public Project getProject() {
        return project;
    }

    public Plant project(Project project) {
        this.project = project;
        return this;
    }

    public void setProject(Project project) {
        this.project = project;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Plant plant = (Plant) o;
        if (plant.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), plant.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Plant{" +
            "id=" + getId() +
            ", fleetId='" + getFleetId() + "'" +
            ", name='" + getName() + "'" +
            ", notes='" + getNotes() + "'" +
            ", purchaseDate='" + getPurchaseDate() + "'" +
            ", isActive='" + isIsActive() + "'" +
            ", description='" + getDescription() + "'" +
            ", vin='" + getVin() + "'" +
            ", rego='" + getRego() + "'" +
            ", dateOfManufacture='" + getDateOfManufacture() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            ", tankSize=" + getTankSize() +
            ", maintenanceDueAt=" + getMaintenanceDueAt() +
            ", meterUnit='" + getMeterUnit() + "'" +
            ", certificateDueDate='" + getCertificateDueDate() + "'" +
            ", rucDueAtKm=" + getRucDueAtKm() +
            ", hubboReading=" + getHubboReading() +
            ", loadCapacity=" + getLoadCapacity() +
            ", hourlyRate=" + getHourlyRate() +
            ", registrationDueDate='" + getRegistrationDueDate() + "'" +
            ", hireStatus='" + getHireStatus() + "'" +
            ", gpsDeviceSerial='" + getGpsDeviceSerial() + "'" +
            ", location='" + getLocation() + "'" +
            ", lastLocationUpdateTime='" + getLastLocationUpdateTime() + "'" +
            "}";
    }
}
