package com.dempseywood.repository;

import com.dempseywood.domain.PrestartCheckQuestionListItem;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PrestartCheckQuestionListItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrestartCheckQuestionListItemRepository extends JpaRepository<PrestartCheckQuestionListItem, Long>, JpaSpecificationExecutor<PrestartCheckQuestionListItem> {

}
