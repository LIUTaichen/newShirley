package com.dempseywood.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A PlantLog.
 */
@Entity
@Table(name = "plant_log")
public class PlantLog extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "meter_reading")
    private Integer meterReading;

    @Column(name = "hubbo_reading")
    private Integer hubboReading;

    @Column(name = "service_due_at")
    private Integer serviceDueAt;

    @Column(name = "ruc_due_at")
    private Integer rucDueAt;

    @Column(name = "wof_due_date")
    private Instant wofDueDate;

    @Column(name = "cof_due_date")
    private Instant cofDueDate;

    @Column(name = "service_due_date")
    private Instant serviceDueDate;

    @ManyToOne
    private Plant plant;

    @ManyToOne
    private People operator;

    @ManyToOne
    private Project site;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMeterReading() {
        return meterReading;
    }

    public PlantLog meterReading(Integer meterReading) {
        this.meterReading = meterReading;
        return this;
    }

    public void setMeterReading(Integer meterReading) {
        this.meterReading = meterReading;
    }

    public Integer getHubboReading() {
        return hubboReading;
    }

    public PlantLog hubboReading(Integer hubboReading) {
        this.hubboReading = hubboReading;
        return this;
    }

    public void setHubboReading(Integer hubboReading) {
        this.hubboReading = hubboReading;
    }

    public Integer getServiceDueAt() {
        return serviceDueAt;
    }

    public PlantLog serviceDueAt(Integer serviceDueAt) {
        this.serviceDueAt = serviceDueAt;
        return this;
    }

    public void setServiceDueAt(Integer serviceDueAt) {
        this.serviceDueAt = serviceDueAt;
    }

    public Integer getRucDueAt() {
        return rucDueAt;
    }

    public PlantLog rucDueAt(Integer rucDueAt) {
        this.rucDueAt = rucDueAt;
        return this;
    }

    public void setRucDueAt(Integer rucDueAt) {
        this.rucDueAt = rucDueAt;
    }

    public Instant getWofDueDate() {
        return wofDueDate;
    }

    public PlantLog wofDueDate(Instant wofDueDate) {
        this.wofDueDate = wofDueDate;
        return this;
    }

    public void setWofDueDate(Instant wofDueDate) {
        this.wofDueDate = wofDueDate;
    }

    public Instant getCofDueDate() {
        return cofDueDate;
    }

    public PlantLog cofDueDate(Instant cofDueDate) {
        this.cofDueDate = cofDueDate;
        return this;
    }

    public void setCofDueDate(Instant cofDueDate) {
        this.cofDueDate = cofDueDate;
    }

    public Instant getServiceDueDate() {
        return serviceDueDate;
    }

    public PlantLog serviceDueDate(Instant serviceDueDate) {
        this.serviceDueDate = serviceDueDate;
        return this;
    }

    public void setServiceDueDate(Instant serviceDueDate) {
        this.serviceDueDate = serviceDueDate;
    }

    public Plant getPlant() {
        return plant;
    }

    public PlantLog plant(Plant plant) {
        this.plant = plant;
        return this;
    }

    public void setPlant(Plant plant) {
        this.plant = plant;
    }

    public People getOperator() {
        return operator;
    }

    public PlantLog operator(People people) {
        this.operator = people;
        return this;
    }

    public void setOperator(People people) {
        this.operator = people;
    }

    public Project getSite() {
        return site;
    }

    public PlantLog site(Project project) {
        this.site = project;
        return this;
    }

    public void setSite(Project project) {
        this.site = project;
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
        PlantLog plantLog = (PlantLog) o;
        if (plantLog.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), plantLog.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PlantLog{" +
            "id=" + getId() +
            ", meterReading=" + getMeterReading() +
            ", hubboReading=" + getHubboReading() +
            ", serviceDueAt=" + getServiceDueAt() +
            ", rucDueAt=" + getRucDueAt() +
            ", wofDueDate='" + getWofDueDate() + "'" +
            ", cofDueDate='" + getCofDueDate() + "'" +
            ", serviceDueDate='" + getServiceDueDate() + "'" +
            "}";
    }
}
