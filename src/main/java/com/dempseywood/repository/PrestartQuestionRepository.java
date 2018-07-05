package com.dempseywood.repository;

import com.dempseywood.domain.PrestartQuestion;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PrestartQuestion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrestartQuestionRepository extends JpaRepository<PrestartQuestion, Long>, JpaSpecificationExecutor<PrestartQuestion> {

}
