package com.dempseywood.service;

import com.dempseywood.domain.NiggleSnapshot;
import com.dempseywood.repository.NiggleSnapshotRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing NiggleSnapshot.
 */
@Service
@Transactional
public class NiggleSnapshotService {

    private final Logger log = LoggerFactory.getLogger(NiggleSnapshotService.class);

    private final NiggleSnapshotRepository niggleSnapshotRepository;

    public NiggleSnapshotService(NiggleSnapshotRepository niggleSnapshotRepository) {
        this.niggleSnapshotRepository = niggleSnapshotRepository;
    }

    /**
     * Save a niggleSnapshot.
     *
     * @param niggleSnapshot the entity to save
     * @return the persisted entity
     */
    public NiggleSnapshot save(NiggleSnapshot niggleSnapshot) {
        log.debug("Request to save NiggleSnapshot : {}", niggleSnapshot);
        return niggleSnapshotRepository.save(niggleSnapshot);
    }

    /**
     * Get all the niggleSnapshots.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<NiggleSnapshot> findAll() {
        log.debug("Request to get all NiggleSnapshots");
        return niggleSnapshotRepository.findAll();
    }

    /**
     * Get one niggleSnapshot by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public NiggleSnapshot findOne(Long id) {
        log.debug("Request to get NiggleSnapshot : {}", id);
        return niggleSnapshotRepository.findOne(id);
    }

    /**
     * Delete the niggleSnapshot by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete NiggleSnapshot : {}", id);
        niggleSnapshotRepository.delete(id);
    }
}
