package com.dempseywood.repository;

import com.dempseywood.domain.PrestartCheckResponse;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PrestartCheckResponse entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrestartCheckResponseRepository extends JpaRepository<PrestartCheckResponse, Long>, JpaSpecificationExecutor<PrestartCheckResponse> {

}
