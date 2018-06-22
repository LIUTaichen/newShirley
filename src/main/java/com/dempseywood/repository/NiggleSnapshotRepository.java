package com.dempseywood.repository;

import com.dempseywood.domain.NiggleSnapshot;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the NiggleSnapshot entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NiggleSnapshotRepository extends JpaRepository<NiggleSnapshot, Long>, JpaSpecificationExecutor<NiggleSnapshot> {

}
