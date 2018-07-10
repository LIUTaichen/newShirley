package com.dempseywood.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A PrestartCheckResponse.
 */
@Entity
@Table(name = "prestart_check_response")
public class PrestartCheckResponse extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private PrestartCheck prestartCheck;

    @ManyToOne
    private PrestartQuestion question;

    @ManyToOne
    private PrestartQuestionOption response;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PrestartCheck getPrestartCheck() {
        return prestartCheck;
    }

    public PrestartCheckResponse prestartCheck(PrestartCheck prestartCheck) {
        this.prestartCheck = prestartCheck;
        return this;
    }

    public void setPrestartCheck(PrestartCheck prestartCheck) {
        this.prestartCheck = prestartCheck;
    }

    public PrestartQuestion getQuestion() {
        return question;
    }

    public PrestartCheckResponse question(PrestartQuestion prestartQuestion) {
        this.question = prestartQuestion;
        return this;
    }

    public void setQuestion(PrestartQuestion prestartQuestion) {
        this.question = prestartQuestion;
    }

    public PrestartQuestionOption getResponse() {
        return response;
    }

    public PrestartCheckResponse response(PrestartQuestionOption prestartQuestionOption) {
        this.response = prestartQuestionOption;
        return this;
    }

    public void setResponse(PrestartQuestionOption prestartQuestionOption) {
        this.response = prestartQuestionOption;
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
        PrestartCheckResponse prestartCheckResponse = (PrestartCheckResponse) o;
        if (prestartCheckResponse.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), prestartCheckResponse.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PrestartCheckResponse{" +
            "id=" + getId() +
            "}";
    }
}
