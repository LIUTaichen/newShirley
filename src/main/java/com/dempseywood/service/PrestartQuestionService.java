package com.dempseywood.service;

import com.dempseywood.domain.PrestartQuestion;
import com.dempseywood.repository.PrestartQuestionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing PrestartQuestion.
 */
@Service
@Transactional
public class PrestartQuestionService {

    private final Logger log = LoggerFactory.getLogger(PrestartQuestionService.class);

    private final PrestartQuestionRepository prestartQuestionRepository;

    public PrestartQuestionService(PrestartQuestionRepository prestartQuestionRepository) {
        this.prestartQuestionRepository = prestartQuestionRepository;
    }

    /**
     * Save a prestartQuestion.
     *
     * @param prestartQuestion the entity to save
     * @return the persisted entity
     */
    public PrestartQuestion save(PrestartQuestion prestartQuestion) {
        log.debug("Request to save PrestartQuestion : {}", prestartQuestion);
        return prestartQuestionRepository.save(prestartQuestion);
    }

    /**
     * Get all the prestartQuestions.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<PrestartQuestion> findAll() {
        log.debug("Request to get all PrestartQuestions");
        return prestartQuestionRepository.findAll();
    }

    /**
     * Get one prestartQuestion by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public PrestartQuestion findOne(Long id) {
        log.debug("Request to get PrestartQuestion : {}", id);
        return prestartQuestionRepository.findOne(id);
    }

    /**
     * Delete the prestartQuestion by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PrestartQuestion : {}", id);
        prestartQuestionRepository.delete(id);
    }
}
