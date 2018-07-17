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
 * Criteria class for the PrestartQuestionOption entity. This class is used in PrestartQuestionOptionResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /prestart-question-options?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PrestartQuestionOptionCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter body;

    private BooleanFilter isNormal;

    private BooleanFilter isActive;

    private LongFilter prestartQuestionId;

    public PrestartQuestionOptionCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getBody() {
        return body;
    }

    public void setBody(StringFilter body) {
        this.body = body;
    }

    public BooleanFilter getIsNormal() {
        return isNormal;
    }

    public void setIsNormal(BooleanFilter isNormal) {
        this.isNormal = isNormal;
    }

    public BooleanFilter getIsActive() {
        return isActive;
    }

    public void setIsActive(BooleanFilter isActive) {
        this.isActive = isActive;
    }

    public LongFilter getPrestartQuestionId() {
        return prestartQuestionId;
    }

    public void setPrestartQuestionId(LongFilter prestartQuestionId) {
        this.prestartQuestionId = prestartQuestionId;
    }

    @Override
    public String toString() {
        return "PrestartQuestionOptionCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (body != null ? "body=" + body + ", " : "") +
                (isNormal != null ? "isNormal=" + isNormal + ", " : "") +
                (isActive != null ? "isActive=" + isActive + ", " : "") +
                (prestartQuestionId != null ? "prestartQuestionId=" + prestartQuestionId + ", " : "") +
            "}";
    }

}
