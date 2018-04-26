package com.dempseywood.repository;

import com.dempseywood.domain.PlantLog;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PlantLog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlantLogRepository extends JpaRepository<PlantLog, Long> {

}
