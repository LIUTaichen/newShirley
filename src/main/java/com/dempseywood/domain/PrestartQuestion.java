package com.dempseywood.domain;


import javax.persistence.*;

import java.io.Serializable;
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
            "}";
    }
}
