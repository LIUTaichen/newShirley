package com.dempseywood.service.dto;

import java.io.Serializable;
import com.dempseywood.domain.enumeration.Status;
import com.dempseywood.domain.enumeration.Priority;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;


import io.github.jhipster.service.filter.LocalDateFilter;



/**
 * Criteria class for the WeeklyNiggleSnapshot entity. This class is used in WeeklyNiggleSnapshotResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /weekly-niggle-snapshots?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class WeeklyNiggleSnapshotCriteria implements Serializable {
    /**
     * Class for filtering Status
     */
    public static class StatusFilter extends Filter<Status> {
    }

    /**
     * Class for filtering Priority
     */
    public static class PriorityFilter extends Filter<Priority> {
    }

    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private LocalDateFilter weekEndingOn;

    private StatusFilter status;

    private PriorityFilter priority;

    private IntegerFilter count;

    private IntegerFilter ageOfOldest;

    public WeeklyNiggleSnapshotCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LocalDateFilter getWeekEndingOn() {
        return weekEndingOn;
    }

    public void setWeekEndingOn(LocalDateFilter weekEndingOn) {
        this.weekEndingOn = weekEndingOn;
    }

    public StatusFilter getStatus() {
        return status;
    }

    public void setStatus(StatusFilter status) {
        this.status = status;
    }

    public PriorityFilter getPriority() {
        return priority;
    }

    public void setPriority(PriorityFilter priority) {
        this.priority = priority;
    }

    public IntegerFilter getCount() {
        return count;
    }

    public void setCount(IntegerFilter count) {
        this.count = count;
    }

    public IntegerFilter getAgeOfOldest() {
        return ageOfOldest;
    }

    public void setAgeOfOldest(IntegerFilter ageOfOldest) {
        this.ageOfOldest = ageOfOldest;
    }

    @Override
    public String toString() {
        return "WeeklyNiggleSnapshotCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (weekEndingOn != null ? "weekEndingOn=" + weekEndingOn + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (priority != null ? "priority=" + priority + ", " : "") +
                (count != null ? "count=" + count + ", " : "") +
                (ageOfOldest != null ? "ageOfOldest=" + ageOfOldest + ", " : "") +
            "}";
    }

}
