package com.dempseywood.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.dempseywood.domain.enumeration.Status;

import com.dempseywood.domain.enumeration.Priority;

/**
 * A NiggleSnapshot.
 */
@Entity
@Table(name = "niggle_snapshot")
public class NiggleSnapshot extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_date")
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    private Priority priority;

    @Column(name = "count")
    private Integer count;

    @Column(name = "age_of_oldest")
    private Integer ageOfOldest;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public NiggleSnapshot date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Status getStatus() {
        return status;
    }

    public NiggleSnapshot status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Priority getPriority() {
        return priority;
    }

    public NiggleSnapshot priority(Priority priority) {
        this.priority = priority;
        return this;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Integer getCount() {
        return count;
    }

    public NiggleSnapshot count(Integer count) {
        this.count = count;
        return this;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getAgeOfOldest() {
        return ageOfOldest;
    }

    public NiggleSnapshot ageOfOldest(Integer ageOfOldest) {
        this.ageOfOldest = ageOfOldest;
        return this;
    }

    public void setAgeOfOldest(Integer ageOfOldest) {
        this.ageOfOldest = ageOfOldest;
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
        NiggleSnapshot niggleSnapshot = (NiggleSnapshot) o;
        if (niggleSnapshot.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), niggleSnapshot.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NiggleSnapshot{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", priority='" + getPriority() + "'" +
            ", count=" + getCount() +
            ", ageOfOldest=" + getAgeOfOldest() +
            "}";
    }
}
