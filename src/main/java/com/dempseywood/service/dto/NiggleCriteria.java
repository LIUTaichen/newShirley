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

import io.github.jhipster.service.filter.InstantFilter;




/**
 * Criteria class for the Niggle entity. This class is used in NiggleResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /niggles?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class NiggleCriteria implements Serializable {
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

    private StringFilter description;

    private StatusFilter status;

    private StringFilter note;

    private PriorityFilter priority;

    private StringFilter quattraReference;

    private StringFilter quattraComments;

    private StringFilter invoiceNo;

    private InstantFilter dateOpened;

    private InstantFilter dateClosed;

    private LongFilter purchaseOrderId;

    private LongFilter plantId;

    private LongFilter assignedContractorId;

    public NiggleCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public StatusFilter getStatus() {
        return status;
    }

    public void setStatus(StatusFilter status) {
        this.status = status;
    }

    public StringFilter getNote() {
        return note;
    }

    public void setNote(StringFilter note) {
        this.note = note;
    }

    public PriorityFilter getPriority() {
        return priority;
    }

    public void setPriority(PriorityFilter priority) {
        this.priority = priority;
    }

    public StringFilter getQuattraReference() {
        return quattraReference;
    }

    public void setQuattraReference(StringFilter quattraReference) {
        this.quattraReference = quattraReference;
    }

    public StringFilter getQuattraComments() {
        return quattraComments;
    }

    public void setQuattraComments(StringFilter quattraComments) {
        this.quattraComments = quattraComments;
    }

    public StringFilter getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(StringFilter invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public InstantFilter getDateOpened() {
        return dateOpened;
    }

    public void setDateOpened(InstantFilter dateOpened) {
        this.dateOpened = dateOpened;
    }

    public InstantFilter getDateClosed() {
        return dateClosed;
    }

    public void setDateClosed(InstantFilter dateClosed) {
        this.dateClosed = dateClosed;
    }

    public LongFilter getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(LongFilter purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }

    public LongFilter getPlantId() {
        return plantId;
    }

    public void setPlantId(LongFilter plantId) {
        this.plantId = plantId;
    }

    public LongFilter getAssignedContractorId() {
        return assignedContractorId;
    }

    public void setAssignedContractorId(LongFilter assignedContractorId) {
        this.assignedContractorId = assignedContractorId;
    }

    @Override
    public String toString() {
        return "NiggleCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (note != null ? "note=" + note + ", " : "") +
                (priority != null ? "priority=" + priority + ", " : "") +
                (quattraReference != null ? "quattraReference=" + quattraReference + ", " : "") +
                (quattraComments != null ? "quattraComments=" + quattraComments + ", " : "") +
                (invoiceNo != null ? "invoiceNo=" + invoiceNo + ", " : "") +
                (dateOpened != null ? "dateOpened=" + dateOpened + ", " : "") +
                (dateClosed != null ? "dateClosed=" + dateClosed + ", " : "") +
                (purchaseOrderId != null ? "purchaseOrderId=" + purchaseOrderId + ", " : "") +
                (plantId != null ? "plantId=" + plantId + ", " : "") +
                (assignedContractorId != null ? "assignedContractorId=" + assignedContractorId + ", " : "") +
            "}";
    }

}
