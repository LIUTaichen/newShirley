package com.dempseywood.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import com.dempseywood.domain.enumeration.Event;

import com.dempseywood.domain.enumeration.RecipientType;

/**
 * A EmailSubscription.
 */
@Entity
@Table(name = "email_subscription")
public class EmailSubscription extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "event")
    private Event event;

    @Column(name = "email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "recipient_type")
    private RecipientType recipientType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Event getEvent() {
        return event;
    }

    public EmailSubscription event(Event event) {
        this.event = event;
        return this;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getEmail() {
        return email;
    }

    public EmailSubscription email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public RecipientType getRecipientType() {
        return recipientType;
    }

    public EmailSubscription recipientType(RecipientType recipientType) {
        this.recipientType = recipientType;
        return this;
    }

    public void setRecipientType(RecipientType recipientType) {
        this.recipientType = recipientType;
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
        EmailSubscription emailSubscription = (EmailSubscription) o;
        if (emailSubscription.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), emailSubscription.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EmailSubscription{" +
            "id=" + getId() +
            ", event='" + getEvent() + "'" +
            ", email='" + getEmail() + "'" +
            ", recipientType='" + getRecipientType() + "'" +
            "}";
    }
}
