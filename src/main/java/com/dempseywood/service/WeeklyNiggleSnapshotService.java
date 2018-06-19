package com.dempseywood.service;

import com.dempseywood.domain.WeeklyNiggleSnapshot;
import com.dempseywood.repository.WeeklyNiggleSnapshotRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing WeeklyNiggleSnapshot.
 */
@Service
@Transactional
public class WeeklyNiggleSnapshotService {

    private final Logger log = LoggerFactory.getLogger(WeeklyNiggleSnapshotService.class);

    private final WeeklyNiggleSnapshotRepository weeklyNiggleSnapshotRepository;

    public WeeklyNiggleSnapshotService(WeeklyNiggleSnapshotRepository weeklyNiggleSnapshotRepository) {
        this.weeklyNiggleSnapshotRepository = weeklyNiggleSnapshotRepository;
    }

    /**
     * Save a weeklyNiggleSnapshot.
     *
     * @param weeklyNiggleSnapshot the entity to save
     * @return the persisted entity
     */
    public WeeklyNiggleSnapshot save(WeeklyNiggleSnapshot weeklyNiggleSnapshot) {
        log.debug("Request to save WeeklyNiggleSnapshot : {}", weeklyNiggleSnapshot);
        return weeklyNiggleSnapshotRepository.save(weeklyNiggleSnapshot);
    }

    /**
     * Get all the weeklyNiggleSnapshots.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<WeeklyNiggleSnapshot> findAll() {
        log.debug("Request to get all WeeklyNiggleSnapshots");
        return weeklyNiggleSnapshotRepository.findAll();
    }

    /**
     * Get one weeklyNiggleSnapshot by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public WeeklyNiggleSnapshot findOne(Long id) {
        log.debug("Request to get WeeklyNiggleSnapshot : {}", id);
        return weeklyNiggleSnapshotRepository.findOne(id);
    }

    /**
     * Delete the weeklyNiggleSnapshot by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete WeeklyNiggleSnapshot : {}", id);
        weeklyNiggleSnapshotRepository.delete(id);
    }
}
