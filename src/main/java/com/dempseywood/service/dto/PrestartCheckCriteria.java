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
 * Criteria class for the PrestartCheck entity. This class is used in PrestartCheckResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /prestart-checks?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PrestartCheckCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private LongFilter plantLogId;

    private LongFilter projectId;

    private LongFilter plantId;

    private LongFilter locationId;

    private LongFilter operatorId;

    public PrestartCheckCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getPlantLogId() {
        return plantLogId;
    }

    public void setPlantLogId(LongFilter plantLogId) {
        this.plantLogId = plantLogId;
    }

    public LongFilter getProjectId() {
        return projectId;
    }

    public void setProjectId(LongFilter projectId) {
        this.projectId = projectId;
    }

    public LongFilter getPlantId() {
        return plantId;
    }

    public void setPlantId(LongFilter plantId) {
        this.plantId = plantId;
    }

    public LongFilter getLocationId() {
        return locationId;
    }

    public void setLocationId(LongFilter locationId) {
        this.locationId = locationId;
    }

    public LongFilter getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(LongFilter operatorId) {
        this.operatorId = operatorId;
    }

    @Override
    public String toString() {
        return "PrestartCheckCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (plantLogId != null ? "plantLogId=" + plantLogId + ", " : "") +
                (projectId != null ? "projectId=" + projectId + ", " : "") +
                (plantId != null ? "plantId=" + plantId + ", " : "") +
                (locationId != null ? "locationId=" + locationId + ", " : "") +
                (operatorId != null ? "operatorId=" + operatorId + ", " : "") +
            "}";
    }

}
