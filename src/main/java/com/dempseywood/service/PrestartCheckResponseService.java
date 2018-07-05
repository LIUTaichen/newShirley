package com.dempseywood.service;

import com.dempseywood.domain.PrestartCheckResponse;
import com.dempseywood.repository.PrestartCheckResponseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing PrestartCheckResponse.
 */
@Service
@Transactional
public class PrestartCheckResponseService {

    private final Logger log = LoggerFactory.getLogger(PrestartCheckResponseService.class);

    private final PrestartCheckResponseRepository prestartCheckResponseRepository;

    public PrestartCheckResponseService(PrestartCheckResponseRepository prestartCheckResponseRepository) {
        this.prestartCheckResponseRepository = prestartCheckResponseRepository;
    }

    /**
     * Save a prestartCheckResponse.
     *
     * @param prestartCheckResponse the entity to save
     * @return the persisted entity
     */
    public PrestartCheckResponse save(PrestartCheckResponse prestartCheckResponse) {
        log.debug("Request to save PrestartCheckResponse : {}", prestartCheckResponse);
        return prestartCheckResponseRepository.save(prestartCheckResponse);
    }

    /**
     * Get all the prestartCheckResponses.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<PrestartCheckResponse> findAll() {
        log.debug("Request to get all PrestartCheckResponses");
        return prestartCheckResponseRepository.findAll();
    }

    /**
     * Get one prestartCheckResponse by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public PrestartCheckResponse findOne(Long id) {
        log.debug("Request to get PrestartCheckResponse : {}", id);
        return prestartCheckResponseRepository.findOne(id);
    }

    /**
     * Delete the prestartCheckResponse by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PrestartCheckResponse : {}", id);
        prestartCheckResponseRepository.delete(id);
    }
}
