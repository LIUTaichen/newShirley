package com.dempseywood.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Competency.
 */
@Entity
@Table(name = "competency")
public class Competency implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "competency")
    private String competency;

    @Column(name = "grouping")
    private String grouping;

    @Column(name = "sort_order")
    private Integer sortOrder;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompetency() {
        return competency;
    }

    public Competency competency(String competency) {
        this.competency = competency;
        return this;
    }

    public void setCompetency(String competency) {
        this.competency = competency;
    }

    public String getGrouping() {
        return grouping;
    }

    public Competency grouping(String grouping) {
        this.grouping = grouping;
        return this;
    }

    public void setGrouping(String grouping) {
        this.grouping = grouping;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public Competency sortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
        return this;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
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
        Competency competency = (Competency) o;
        if (competency.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), competency.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Competency{" +
            "id=" + getId() +
            ", competency='" + getCompetency() + "'" +
            ", grouping='" + getGrouping() + "'" +
            ", sortOrder=" + getSortOrder() +
            "}";
    }
}
