package com.dempseywood.service;

import com.dempseywood.domain.Location;
import com.dempseywood.domain.Plant;
import com.dempseywood.repository.LocationRepository;
import com.dempseywood.repository.PlantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing Plant.
 */
@Service
@Transactional
public class PlantService {

    private final Logger log = LoggerFactory.getLogger(PlantService.class);

    private final PlantRepository plantRepository;

    private final LocationRepository locationRepository;

    public PlantService(PlantRepository plantRepository,LocationRepository locationRepository ) {
        this.plantRepository = plantRepository;
        this.locationRepository = locationRepository;
    }

    /**
     * Save a plant.
     *
     * @param plant the entity to save
     * @return the persisted entity
     */
    public Plant save(Plant plant) {
        log.debug("Request to save Plant : {}", plant);
        return plantRepository.save(plant);
    }

    /**
     * Get all the plants.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Plant> findAll() {
        log.debug("Request to get all Plants");
        return plantRepository.findAll();
    }

    /**
     * Get one plant by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Plant findOne(Long id) {
        log.debug("Request to get Plant : {}", id);
        return plantRepository.findOne(id);
    }

    /**
     * Delete the plant by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Plant : {}", id);
        plantRepository.delete(id);
    }

    public void savePlantLocation(Plant plant ){
        if(plant.getLocation() == null){
            return;
        }
        Location location = locationRepository.save(plant.getLocation());
        if(plant.getLocation().getId() == null){
            plant.setLocation(location);
            plantRepository.save(plant);
        }
    }

}
