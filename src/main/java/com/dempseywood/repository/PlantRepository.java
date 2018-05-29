package com.dempseywood.repository;

import com.dempseywood.domain.Plant;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the Plant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlantRepository extends JpaRepository<Plant, Long>, JpaSpecificationExecutor<Plant> {

    List<Plant> findByGpsDeviceSerialIsNotNull();

}
