package com.dempseywood.repository;

import com.dempseywood.domain.PrestartCheck;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PrestartCheck entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrestartCheckRepository extends JpaRepository<PrestartCheck, Long>, JpaSpecificationExecutor<PrestartCheck> {

}
