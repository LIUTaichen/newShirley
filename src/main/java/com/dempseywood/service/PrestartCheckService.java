package com.dempseywood.service;

import com.dempseywood.domain.PrestartCheck;
import com.dempseywood.repository.PrestartCheckRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing PrestartCheck.
 */
@Service
@Transactional
public class PrestartCheckService {

    private final Logger log = LoggerFactory.getLogger(PrestartCheckService.class);

    private final PrestartCheckRepository prestartCheckRepository;

    public PrestartCheckService(PrestartCheckRepository prestartCheckRepository) {
        this.prestartCheckRepository = prestartCheckRepository;
    }

    /**
     * Save a prestartCheck.
     *
     * @param prestartCheck the entity to save
     * @return the persisted entity
     */
    public PrestartCheck save(PrestartCheck prestartCheck) {
        log.debug("Request to save PrestartCheck : {}", prestartCheck);
        return prestartCheckRepository.save(prestartCheck);
    }

    /**
     * Get all the prestartChecks.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<PrestartCheck> findAll() {
        log.debug("Request to get all PrestartChecks");
        return prestartCheckRepository.findAll();
    }

    /**
     * Get one prestartCheck by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public PrestartCheck findOne(Long id) {
        log.debug("Request to get PrestartCheck : {}", id);
        return prestartCheckRepository.findOne(id);
    }

    /**
     * Delete the prestartCheck by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PrestartCheck : {}", id);
        prestartCheckRepository.delete(id);
    }
}
