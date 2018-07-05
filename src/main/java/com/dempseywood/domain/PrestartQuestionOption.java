package com.dempseywood.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A PrestartQuestionOption.
 */
@Entity
@Table(name = "prestart_question_option")
public class PrestartQuestionOption extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_body")
    private String body;

    @Column(name = "is_normal")
    private Boolean isNormal;

    @Column(name = "is_active")
    private Boolean isActive;

    @ManyToOne
    private PrestartQuestion question;

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

    public PrestartQuestionOption body(String body) {
        this.body = body;
        return this;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Boolean isIsNormal() {
        return isNormal;
    }

    public PrestartQuestionOption isNormal(Boolean isNormal) {
        this.isNormal = isNormal;
        return this;
    }

    public void setIsNormal(Boolean isNormal) {
        this.isNormal = isNormal;
    }

    public Boolean isIsActive() {
        return isActive;
    }

    public PrestartQuestionOption isActive(Boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public PrestartQuestion getQuestion() {
        return question;
    }

    public PrestartQuestionOption question(PrestartQuestion prestartQuestion) {
        this.question = prestartQuestion;
        return this;
    }

    public void setQuestion(PrestartQuestion prestartQuestion) {
        this.question = prestartQuestion;
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
        PrestartQuestionOption prestartQuestionOption = (PrestartQuestionOption) o;
        if (prestartQuestionOption.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), prestartQuestionOption.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PrestartQuestionOption{" +
            "id=" + getId() +
            ", body='" + getBody() + "'" +
            ", isNormal='" + isIsNormal() + "'" +
            ", isActive='" + isIsActive() + "'" +
            "}";
    }
}
