package com.dempseywood.repository;

import com.dempseywood.domain.PrestartCheckConfig;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PrestartCheckConfig entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrestartCheckConfigRepository extends JpaRepository<PrestartCheckConfig, Long>, JpaSpecificationExecutor<PrestartCheckConfig> {

}
