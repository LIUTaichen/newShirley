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
 * Criteria class for the PrestartCheckConfig entity. This class is used in PrestartCheckConfigResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /prestart-check-configs?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PrestartCheckConfigCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private LongFilter questionlistId;

    public PrestartCheckConfigCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getQuestionlistId() {
        return questionlistId;
    }

    public void setQuestionlistId(LongFilter questionlistId) {
        this.questionlistId = questionlistId;
    }

    @Override
    public String toString() {
        return "PrestartCheckConfigCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (questionlistId != null ? "questionlistId=" + questionlistId + ", " : "") +
            "}";
    }

}
