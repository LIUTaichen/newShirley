package com.dempseywood.repository;

import com.dempseywood.domain.Niggle;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Niggle entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NiggleRepository extends JpaRepository<Niggle, Long>, JpaSpecificationExecutor<Niggle> {

}
