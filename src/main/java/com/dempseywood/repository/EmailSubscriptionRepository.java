package com.dempseywood.repository;

import com.dempseywood.domain.EmailSubscription;
import com.dempseywood.domain.enumeration.Event;
import com.dempseywood.domain.enumeration.RecipientType;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the EmailSubscription entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmailSubscriptionRepository extends JpaRepository<EmailSubscription, Long> {
    List<EmailSubscription> findAllByEvent(Event event);
    List<EmailSubscription> findByEventAndRecipientType(Event event, RecipientType type);
}
