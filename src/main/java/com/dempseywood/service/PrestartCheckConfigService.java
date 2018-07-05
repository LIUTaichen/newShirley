package com.dempseywood.service;

import com.dempseywood.domain.PrestartCheckConfig;
import com.dempseywood.repository.PrestartCheckConfigRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing PrestartCheckConfig.
 */
@Service
@Transactional
public class PrestartCheckConfigService {

    private final Logger log = LoggerFactory.getLogger(PrestartCheckConfigService.class);

    private final PrestartCheckConfigRepository prestartCheckConfigRepository;

    public PrestartCheckConfigService(PrestartCheckConfigRepository prestartCheckConfigRepository) {
        this.prestartCheckConfigRepository = prestartCheckConfigRepository;
    }

    /**
     * Save a prestartCheckConfig.
     *
     * @param prestartCheckConfig the entity to save
     * @return the persisted entity
     */
    public PrestartCheckConfig save(PrestartCheckConfig prestartCheckConfig) {
        log.debug("Request to save PrestartCheckConfig : {}", prestartCheckConfig);
        return prestartCheckConfigRepository.save(prestartCheckConfig);
    }

    /**
     * Get all the prestartCheckConfigs.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<PrestartCheckConfig> findAll() {
        log.debug("Request to get all PrestartCheckConfigs");
        return prestartCheckConfigRepository.findAll();
    }

    /**
     * Get one prestartCheckConfig by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public PrestartCheckConfig findOne(Long id) {
        log.debug("Request to get PrestartCheckConfig : {}", id);
        return prestartCheckConfigRepository.findOne(id);
    }

    /**
     * Delete the prestartCheckConfig by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PrestartCheckConfig : {}", id);
        prestartCheckConfigRepository.delete(id);
    }
}
