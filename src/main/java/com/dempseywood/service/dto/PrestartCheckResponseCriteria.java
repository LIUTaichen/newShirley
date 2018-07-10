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
 * Criteria class for the PrestartCheckResponse entity. This class is used in PrestartCheckResponseResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /prestart-check-responses?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PrestartCheckResponseCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private LongFilter prestartCheckId;

    private LongFilter questionId;

    private LongFilter responseId;

    public PrestartCheckResponseCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getPrestartCheckId() {
        return prestartCheckId;
    }

    public void setPrestartCheckId(LongFilter prestartCheckId) {
        this.prestartCheckId = prestartCheckId;
    }

    public LongFilter getQuestionId() {
        return questionId;
    }

    public void setQuestionId(LongFilter questionId) {
        this.questionId = questionId;
    }

    public LongFilter getResponseId() {
        return responseId;
    }

    public void setResponseId(LongFilter responseId) {
        this.responseId = responseId;
    }

    @Override
    public String toString() {
        return "PrestartCheckResponseCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (prestartCheckId != null ? "prestartCheckId=" + prestartCheckId + ", " : "") +
                (questionId != null ? "questionId=" + questionId + ", " : "") +
                (responseId != null ? "responseId=" + responseId + ", " : "") +
            "}";
    }

}
