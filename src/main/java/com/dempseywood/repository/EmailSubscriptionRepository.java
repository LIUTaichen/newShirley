package com.dempseywood.repository;

import com.dempseywood.domain.EmailSubscription;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the EmailSubscription entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmailSubscriptionRepository extends JpaRepository<EmailSubscription, Long> {

}
