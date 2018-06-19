package com.dempseywood.service;

import com.dempseywood.domain.Niggle;
import com.dempseywood.repository.NiggleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing Niggle.
 */
@Service
@Transactional
public class NiggleService {

    private final Logger log = LoggerFactory.getLogger(NiggleService.class);

    private final NiggleRepository niggleRepository;

    public NiggleService(NiggleRepository niggleRepository) {
        this.niggleRepository = niggleRepository;
    }

    /**
     * Save a niggle.
     *
     * @param niggle the entity to save
     * @return the persisted entity
     */
    public Niggle save(Niggle niggle) {
        log.debug("Request to save Niggle : {}", niggle);
        return niggleRepository.save(niggle);
    }

    /**
     * Get all the niggles.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Niggle> findAll() {
        log.debug("Request to get all Niggles");
        return niggleRepository.findAll();
    }

    /**
     * Get one niggle by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Niggle findOne(Long id) {
        log.debug("Request to get Niggle : {}", id);
        return niggleRepository.findOne(id);
    }

    /**
     * Delete the niggle by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Niggle : {}", id);
        niggleRepository.delete(id);
    }
}
