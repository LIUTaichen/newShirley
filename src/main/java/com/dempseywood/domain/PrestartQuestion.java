package com.dempseywood.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A PrestartQuestion.
 */
@Entity
@Table(name = "prestart_question")
public class PrestartQuestion extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_body")
    private String body;

    @Column(name = "is_lock_out_required")
    private Boolean isLockOutRequired;

    @OneToMany(mappedBy = "prestartQuestion")
    @JsonIgnore
    private Set<PrestartQuestionOption> options = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public PrestartQuestion body(String body) {
        this.body = body;
        return this;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Boolean isIsLockOutRequired() {
        return isLockOutRequired;
    }

    public PrestartQuestion isLockOutRequired(Boolean isLockOutRequired) {
        this.isLockOutRequired = isLockOutRequired;
        return this;
    }

    public void setIsLockOutRequired(Boolean isLockOutRequired) {
        this.isLockOutRequired = isLockOutRequired;
    }

    public Set<PrestartQuestionOption> getOptions() {
        return options;
    }

    public PrestartQuestion options(Set<PrestartQuestionOption> prestartQuestionOptions) {
        this.options = prestartQuestionOptions;
        return this;
    }

    public PrestartQuestion addOptions(PrestartQuestionOption prestartQuestionOption) {
        this.options.add(prestartQuestionOption);
        prestartQuestionOption.setPrestartQuestion(this);
        return this;
    }

    public PrestartQuestion removeOptions(PrestartQuestionOption prestartQuestionOption) {
        this.options.remove(prestartQuestionOption);
        prestartQuestionOption.setPrestartQuestion(null);
        return this;
    }

    public void setOptions(Set<PrestartQuestionOption> prestartQuestionOptions) {
        this.options = prestartQuestionOptions;
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
        PrestartQuestion prestartQuestion = (PrestartQuestion) o;
        if (prestartQuestion.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), prestartQuestion.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PrestartQuestion{" +
            "id=" + getId() +
            ", body='" + getBody() + "'" +
            ", isLockOutRequired='" + isIsLockOutRequired() + "'" +
            "}";
    }
}
