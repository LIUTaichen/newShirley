package com.dempseywood.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A PrestartCheck.
 */
@Entity
@Table(name = "prestart_check")
public class PrestartCheck extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "signature")
    private byte[] signature;

    @Column(name = "signature_content_type")
    private String signatureContentType;

    @ManyToOne
    private Project project;

    @ManyToOne
    private Plant plant;

    @ManyToOne
    private Location location;

    @ManyToOne
    private People operator;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getSignature() {
        return signature;
    }

    public PrestartCheck signature(byte[] signature) {
        this.signature = signature;
        return this;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }

    public String getSignatureContentType() {
        return signatureContentType;
    }

    public PrestartCheck signatureContentType(String signatureContentType) {
        this.signatureContentType = signatureContentType;
        return this;
    }

    public void setSignatureContentType(String signatureContentType) {
        this.signatureContentType = signatureContentType;
    }

    public Project getProject() {
        return project;
    }

    public PrestartCheck project(Project project) {
        this.project = project;
        return this;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Plant getPlant() {
        return plant;
    }

    public PrestartCheck plant(Plant plant) {
        this.plant = plant;
        return this;
    }

    public void setPlant(Plant plant) {
        this.plant = plant;
    }

    public Location getLocation() {
        return location;
    }

    public PrestartCheck location(Location location) {
        this.location = location;
        return this;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public People getOperator() {
        return operator;
    }

    public PrestartCheck operator(People people) {
        this.operator = people;
        return this;
    }

    public void setOperator(People people) {
        this.operator = people;
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
        PrestartCheck prestartCheck = (PrestartCheck) o;
        if (prestartCheck.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), prestartCheck.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PrestartCheck{" +
            "id=" + getId() +
            ", signature='" + getSignature() + "'" +
            ", signatureContentType='" + getSignatureContentType() + "'" +
            "}";
    }
}
