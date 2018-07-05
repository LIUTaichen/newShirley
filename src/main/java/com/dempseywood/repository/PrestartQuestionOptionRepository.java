package com.dempseywood.repository;

import com.dempseywood.domain.PrestartQuestionOption;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PrestartQuestionOption entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrestartQuestionOptionRepository extends JpaRepository<PrestartQuestionOption, Long>, JpaSpecificationExecutor<PrestartQuestionOption> {

}
