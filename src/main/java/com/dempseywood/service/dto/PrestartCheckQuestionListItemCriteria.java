package com.dempseywood.service.dto;

import java.io.Serializable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;






/**
 * Criteria class for the PrestartCheckQuestionListItem entity. This class is used in PrestartCheckQuestionListItemResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /prestart-check-question-list-items?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PrestartCheckQuestionListItemCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private IntegerFilter order;

    private LongFilter prestartCheckConfigId;

    private LongFilter questionId;

    public PrestartCheckQuestionListItemCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getOrder() {
        return order;
    }

    public void setOrder(IntegerFilter order) {
        this.order = order;
    }

    public LongFilter getPrestartCheckConfigId() {
        return prestartCheckConfigId;
    }

    public void setPrestartCheckConfigId(LongFilter prestartCheckConfigId) {
        this.prestartCheckConfigId = prestartCheckConfigId;
    }

    public LongFilter getQuestionId() {
        return questionId;
    }

    public void setQuestionId(LongFilter questionId) {
        this.questionId = questionId;
    }

    @Override
    public String toString() {
        return "PrestartCheckQuestionListItemCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (order != null ? "order=" + order + ", " : "") +
                (prestartCheckConfigId != null ? "prestartCheckConfigId=" + prestartCheckConfigId + ", " : "") +
                (questionId != null ? "questionId=" + questionId + ", " : "") +
            "}";
    }

}
