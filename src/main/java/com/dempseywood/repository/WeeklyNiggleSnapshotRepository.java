package com.dempseywood.repository;

import com.dempseywood.domain.WeeklyNiggleSnapshot;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the WeeklyNiggleSnapshot entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WeeklyNiggleSnapshotRepository extends JpaRepository<WeeklyNiggleSnapshot, Long>, JpaSpecificationExecutor<WeeklyNiggleSnapshot> {

}
