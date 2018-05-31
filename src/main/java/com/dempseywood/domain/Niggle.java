package com.dempseywood.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import com.dempseywood.domain.enumeration.Status;

import com.dempseywood.domain.enumeration.Priority;
import org.hibernate.annotations.Type;

/**
 * A Niggle.
 */
@Entity
@Table(name = "niggle")
public class Niggle extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description", length = 65535)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "note", length = 65535)
    private String note;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    private Priority priority;

    @Column(name = "quattra_reference")
    private String quattraReference;

    @Column(name = "quattra_comments", length = 65535)
    private String quattraComments;

    @Column(name = "invoice_no")
    private String invoiceNo;

    @Column(name = "date_opened")
    private Instant dateOpened;

    @Column(name = "date_closed")
    private Instant dateClosed;

    @OneToOne
    @JoinColumn(unique = true)
    private PurchaseOrder purchaseOrder;

    @ManyToOne
    private Plant plant;

    @ManyToOne
    private MaintenanceContractor assignedContractor;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public Niggle description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public Niggle status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public Niggle note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Priority getPriority() {
        return priority;
    }

    public Niggle priority(Priority priority) {
        this.priority = priority;
        return this;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public String getQuattraReference() {
        return quattraReference;
    }

    public Niggle quattraReference(String quattraReference) {
        this.quattraReference = quattraReference;
        return this;
    }

    public void setQuattraReference(String quattraReference) {
        this.quattraReference = quattraReference;
    }

    public String getQuattraComments() {
        return quattraComments;
    }

    public Niggle quattraComments(String quattraComments) {
        this.quattraComments = quattraComments;
        return this;
    }

    public void setQuattraComments(String quattraComments) {
        this.quattraComments = quattraComments;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public Niggle invoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
        return this;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public Instant getDateOpened() {
        return dateOpened;
    }

    public Niggle dateOpened(Instant dateOpened) {
        this.dateOpened = dateOpened;
        return this;
    }

    public void setDateOpened(Instant dateOpened) {
        this.dateOpened = dateOpened;
    }

    public Instant getDateClosed() {
        return dateClosed;
    }

    public Niggle dateClosed(Instant dateClosed) {
        this.dateClosed = dateClosed;
        return this;
    }

    public void setDateClosed(Instant dateClosed) {
        this.dateClosed = dateClosed;
    }

    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    public Niggle purchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
        return this;
    }

    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public Plant getPlant() {
        return plant;
    }

    public Niggle plant(Plant plant) {
        this.plant = plant;
        return this;
    }

    public void setPlant(Plant plant) {
        this.plant = plant;
    }

    public MaintenanceContractor getAssignedContractor() {
        return assignedContractor;
    }

    public Niggle assignedContractor(MaintenanceContractor maintenanceContractor) {
        this.assignedContractor = maintenanceContractor;
        return this;
    }

    public void setAssignedContractor(MaintenanceContractor maintenanceContractor) {
        this.assignedContractor = maintenanceContractor;
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
        Niggle niggle = (Niggle) o;
        if (niggle.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), niggle.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Niggle{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", status='" + getStatus() + "'" +
            ", note='" + getNote() + "'" +
            ", priority='" + getPriority() + "'" +
            ", quattraReference='" + getQuattraReference() + "'" +
            ", quattraComments='" + getQuattraComments() + "'" +
            ", invoiceNo='" + getInvoiceNo() + "'" +
            ", dateOpened='" + getDateOpened() + "'" +
            ", dateClosed='" + getDateClosed() + "'" +
            "}";
    }
}
