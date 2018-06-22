package com.dempseywood.repository;

import com.dempseywood.domain.Niggle;
import com.dempseywood.domain.enumeration.Priority;
import com.dempseywood.domain.enumeration.Status;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the Niggle entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NiggleRepository extends JpaRepository<Niggle, Long>, JpaSpecificationExecutor<Niggle> {

    List<Niggle> findByStatusAndPriority(Status status, Priority priority);

}
