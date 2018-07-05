package com.dempseywood.service;

import com.dempseywood.domain.PrestartCheckQuestionListItem;
import com.dempseywood.repository.PrestartCheckQuestionListItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing PrestartCheckQuestionListItem.
 */
@Service
@Transactional
public class PrestartCheckQuestionListItemService {

    private final Logger log = LoggerFactory.getLogger(PrestartCheckQuestionListItemService.class);

    private final PrestartCheckQuestionListItemRepository prestartCheckQuestionListItemRepository;

    public PrestartCheckQuestionListItemService(PrestartCheckQuestionListItemRepository prestartCheckQuestionListItemRepository) {
        this.prestartCheckQuestionListItemRepository = prestartCheckQuestionListItemRepository;
    }

    /**
     * Save a prestartCheckQuestionListItem.
     *
     * @param prestartCheckQuestionListItem the entity to save
     * @return the persisted entity
     */
    public PrestartCheckQuestionListItem save(PrestartCheckQuestionListItem prestartCheckQuestionListItem) {
        log.debug("Request to save PrestartCheckQuestionListItem : {}", prestartCheckQuestionListItem);
        return prestartCheckQuestionListItemRepository.save(prestartCheckQuestionListItem);
    }

    /**
     * Get all the prestartCheckQuestionListItems.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<PrestartCheckQuestionListItem> findAll() {
        log.debug("Request to get all PrestartCheckQuestionListItems");
        return prestartCheckQuestionListItemRepository.findAll();
    }

    /**
     * Get one prestartCheckQuestionListItem by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public PrestartCheckQuestionListItem findOne(Long id) {
        log.debug("Request to get PrestartCheckQuestionListItem : {}", id);
        return prestartCheckQuestionListItemRepository.findOne(id);
    }

    /**
     * Delete the prestartCheckQuestionListItem by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PrestartCheckQuestionListItem : {}", id);
        prestartCheckQuestionListItemRepository.delete(id);
    }
}
