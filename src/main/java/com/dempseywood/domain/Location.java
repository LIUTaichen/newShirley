package com.dempseywood.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A Location.
 */
@Entity
@Table(name = "location")
public class Location implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "address")
    private String address;

    @Column(name = "bearing")
    private Double bearing;

    @Column(name = "direction")
    private String direction;

    @Column(name = "speed")
    private Double speed;

    @Column(name = "jhi_timestamp")
    private Instant timestamp;

    @Column(name = "provider")
    private String provider;

    @ManyToOne
    private Project project;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Location latitude(Double latitude) {
        this.latitude = latitude;
        return this;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Location longitude(Double longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public Location address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getBearing() {
        return bearing;
    }

    public Location bearing(Double bearing) {
        this.bearing = bearing;
        return this;
    }

    public void setBearing(Double bearing) {
        this.bearing = bearing;
    }

    public String getDirection() {
        return direction;
    }

    public Location direction(String direction) {
        this.direction = direction;
        return this;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public Double getSpeed() {
        return speed;
    }

    public Location speed(Double speed) {
        this.speed = speed;
        return this;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public Location timestamp(Instant timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public String getProvider() {
        return provider;
    }

    public Location provider(String provider) {
        this.provider = provider;
        return this;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public Project getProject() {
        return project;
    }

    public Location project(Project project) {
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
        Location location = (Location) o;
        if (location.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), location.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Location{" +
            "id=" + getId() +
            ", latitude=" + getLatitude() +
            ", longitude=" + getLongitude() +
            ", address='" + getAddress() + "'" +
            ", bearing=" + getBearing() +
            ", direction='" + getDirection() + "'" +
            ", speed=" + getSpeed() +
            ", timestamp='" + getTimestamp() + "'" +
            ", provider='" + getProvider() + "'" +
            "}";
    }
}
