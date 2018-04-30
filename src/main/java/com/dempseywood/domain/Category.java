package com.dempseywood.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Category.
 */
@Entity
@Table(name = "category")
public class Category extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "category")
    private String category;

    @Column(name = "description")
    private String description;

    @Column(name = "jhi_type")
    private String type;

    @Column(name = "track_usage")
    private Boolean trackUsage;

    @Column(name = "daily_rate")
    private Double dailyRate;

    @Column(name = "load_capacity")
    private Integer loadCapacity;

    @Column(name = "hourly_rate")
    private Double hourlyRate;

    @Column(name = "is_earch_moving_plant")
    private Boolean isEarchMovingPlant;

    @Column(name = "is_tracked_for_internal_billing")
    private Boolean isTrackedForInternalBilling;

    @ManyToOne
    private Competency competency;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public Category category(String category) {
        this.category = category;
        return this;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public Category description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public Category type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean isTrackUsage() {
        return trackUsage;
    }

    public Category trackUsage(Boolean trackUsage) {
        this.trackUsage = trackUsage;
        return this;
    }

    public void setTrackUsage(Boolean trackUsage) {
        this.trackUsage = trackUsage;
    }

    public Double getDailyRate() {
        return dailyRate;
    }

    public Category dailyRate(Double dailyRate) {
        this.dailyRate = dailyRate;
        return this;
    }

    public void setDailyRate(Double dailyRate) {
        this.dailyRate = dailyRate;
    }

    public Integer getLoadCapacity() {
        return loadCapacity;
    }

    public Category loadCapacity(Integer loadCapacity) {
        this.loadCapacity = loadCapacity;
        return this;
    }

    public void setLoadCapacity(Integer loadCapacity) {
        this.loadCapacity = loadCapacity;
    }

    public Double getHourlyRate() {
        return hourlyRate;
    }

    public Category hourlyRate(Double hourlyRate) {
        this.hourlyRate = hourlyRate;
        return this;
    }

    public void setHourlyRate(Double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public Boolean isIsEarchMovingPlant() {
        return isEarchMovingPlant;
    }

    public Category isEarchMovingPlant(Boolean isEarchMovingPlant) {
        this.isEarchMovingPlant = isEarchMovingPlant;
        return this;
    }

    public void setIsEarchMovingPlant(Boolean isEarchMovingPlant) {
        this.isEarchMovingPlant = isEarchMovingPlant;
    }

    public Boolean isIsTrackedForInternalBilling() {
        return isTrackedForInternalBilling;
    }

    public Category isTrackedForInternalBilling(Boolean isTrackedForInternalBilling) {
        this.isTrackedForInternalBilling = isTrackedForInternalBilling;
        return this;
    }

    public void setIsTrackedForInternalBilling(Boolean isTrackedForInternalBilling) {
        this.isTrackedForInternalBilling = isTrackedForInternalBilling;
    }

    public Competency getCompetency() {
        return competency;
    }

    public Category competency(Competency competency) {
        this.competency = competency;
        return this;
    }

    public void setCompetency(Competency competency) {
        this.competency = competency;
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
        Category category = (Category) o;
        if (category.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), category.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Category{" +
            "id=" + getId() +
            ", category='" + getCategory() + "'" +
            ", description='" + getDescription() + "'" +
            ", type='" + getType() + "'" +
            ", trackUsage='" + isTrackUsage() + "'" +
            ", dailyRate=" + getDailyRate() +
            ", loadCapacity=" + getLoadCapacity() +
            ", hourlyRate=" + getHourlyRate() +
            ", isEarchMovingPlant='" + isIsEarchMovingPlant() + "'" +
            ", isTrackedForInternalBilling='" + isIsTrackedForInternalBilling() + "'" +
            "}";
    }
}
