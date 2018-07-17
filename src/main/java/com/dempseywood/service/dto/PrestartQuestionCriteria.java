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
 * Criteria class for the PrestartQuestion entity. This class is used in PrestartQuestionResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /prestart-questions?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PrestartQuestionCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter body;

    private BooleanFilter isLockOutRequired;

    private LongFilter optionsId;

    public PrestartQuestionCriteria() {
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

    public BooleanFilter getIsLockOutRequired() {
        return isLockOutRequired;
    }

    public void setIsLockOutRequired(BooleanFilter isLockOutRequired) {
        this.isLockOutRequired = isLockOutRequired;
    }

    public LongFilter getOptionsId() {
        return optionsId;
    }

    public void setOptionsId(LongFilter optionsId) {
        this.optionsId = optionsId;
    }

    @Override
    public String toString() {
        return "PrestartQuestionCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (body != null ? "body=" + body + ", " : "") +
                (isLockOutRequired != null ? "isLockOutRequired=" + isLockOutRequired + ", " : "") +
                (optionsId != null ? "optionsId=" + optionsId + ", " : "") +
            "}";
    }

}
