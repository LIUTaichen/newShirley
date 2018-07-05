package com.dempseywood.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A PrestartCheckQuestionListItem.
 */
@Entity
@Table(name = "question_list_item")
public class PrestartCheckQuestionListItem extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_order")
    private Integer order;

    @ManyToOne
    private PrestartCheckConfig prestartCheckConfig;

    @ManyToOne
    private PrestartQuestion question;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOrder() {
        return order;
    }

    public PrestartCheckQuestionListItem order(Integer order) {
        this.order = order;
        return this;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public PrestartCheckConfig getPrestartCheckConfig() {
        return prestartCheckConfig;
    }

    public PrestartCheckQuestionListItem prestartCheckConfig(PrestartCheckConfig prestartCheckConfig) {
        this.prestartCheckConfig = prestartCheckConfig;
        return this;
    }

    public void setPrestartCheckConfig(PrestartCheckConfig prestartCheckConfig) {
        this.prestartCheckConfig = prestartCheckConfig;
    }

    public PrestartQuestion getQuestion() {
        return question;
    }

    public PrestartCheckQuestionListItem question(PrestartQuestion prestartQuestion) {
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
        PrestartCheckQuestionListItem prestartCheckQuestionListItem = (PrestartCheckQuestionListItem) o;
        if (prestartCheckQuestionListItem.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), prestartCheckQuestionListItem.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PrestartCheckQuestionListItem{" +
            "id=" + getId() +
            ", order=" + getOrder() +
            "}";
    }
}
