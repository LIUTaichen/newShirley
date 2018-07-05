package com.dempseywood.service;

import com.dempseywood.domain.PrestartQuestionOption;
import com.dempseywood.repository.PrestartQuestionOptionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing PrestartQuestionOption.
 */
@Service
@Transactional
public class PrestartQuestionOptionService {

    private final Logger log = LoggerFactory.getLogger(PrestartQuestionOptionService.class);

    private final PrestartQuestionOptionRepository prestartQuestionOptionRepository;

    public PrestartQuestionOptionService(PrestartQuestionOptionRepository prestartQuestionOptionRepository) {
        this.prestartQuestionOptionRepository = prestartQuestionOptionRepository;
    }

    /**
     * Save a prestartQuestionOption.
     *
     * @param prestartQuestionOption the entity to save
     * @return the persisted entity
     */
    public PrestartQuestionOption save(PrestartQuestionOption prestartQuestionOption) {
        log.debug("Request to save PrestartQuestionOption : {}", prestartQuestionOption);
        return prestartQuestionOptionRepository.save(prestartQuestionOption);
    }

    /**
     * Get all the prestartQuestionOptions.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<PrestartQuestionOption> findAll() {
        log.debug("Request to get all PrestartQuestionOptions");
        return prestartQuestionOptionRepository.findAll();
    }

    /**
     * Get one prestartQuestionOption by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public PrestartQuestionOption findOne(Long id) {
        log.debug("Request to get PrestartQuestionOption : {}", id);
        return prestartQuestionOptionRepository.findOne(id);
    }

    /**
     * Delete the prestartQuestionOption by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PrestartQuestionOption : {}", id);
        prestartQuestionOptionRepository.delete(id);
    }
}
