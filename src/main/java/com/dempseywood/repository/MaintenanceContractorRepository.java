package com.dempseywood.repository;

import com.dempseywood.domain.MaintenanceContractor;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the MaintenanceContractor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MaintenanceContractorRepository extends JpaRepository<MaintenanceContractor, Long> {

}
