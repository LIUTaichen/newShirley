package com.dempseywood.repository;

import com.dempseywood.domain.Competency;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Competency entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompetencyRepository extends JpaRepository<Competency, Long> {

}
