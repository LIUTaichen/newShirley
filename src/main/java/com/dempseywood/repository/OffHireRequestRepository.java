package com.dempseywood.repository;

import com.dempseywood.domain.OffHireRequest;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the OffHireRequest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OffHireRequestRepository extends JpaRepository<OffHireRequest, Long> {

}
