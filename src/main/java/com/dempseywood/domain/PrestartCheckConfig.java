package com.dempseywood.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A PrestartCheckConfig.
 */
@Entity
@Table(name = "prestart_check_config")
public class PrestartCheckConfig extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "prestartCheckConfig")
    @JsonIgnore
    private Set<PrestartCheckQuestionListItem> questionlists = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<PrestartCheckQuestionListItem> getQuestionlists() {
        return questionlists;
    }

    public PrestartCheckConfig questionlists(Set<PrestartCheckQuestionListItem> prestartCheckQuestionListItems) {
        this.questionlists = prestartCheckQuestionListItems;
        return this;
    }

    public PrestartCheckConfig addQuestionlist(PrestartCheckQuestionListItem prestartCheckQuestionListItem) {
        this.questionlists.add(prestartCheckQuestionListItem);
        prestartCheckQuestionListItem.setPrestartCheckConfig(this);
        return this;
    }

    public PrestartCheckConfig removeQuestionlist(PrestartCheckQuestionListItem prestartCheckQuestionListItem) {
        this.questionlists.remove(prestartCheckQuestionListItem);
        prestartCheckQuestionListItem.setPrestartCheckConfig(null);
        return this;
    }

    public void setQuestionlists(Set<PrestartCheckQuestionListItem> prestartCheckQuestionListItems) {
        this.questionlists = prestartCheckQuestionListItems;
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
        PrestartCheckConfig prestartCheckConfig = (PrestartCheckConfig) o;
        if (prestartCheckConfig.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), prestartCheckConfig.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PrestartCheckConfig{" +
            "id=" + getId() +
            "}";
    }
}
