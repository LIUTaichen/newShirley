package com.dempseywood.service;

import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Profile("prod")
public class JobScheduler {

    private final LocationUpdateService locationUpdateService;

    public JobScheduler(LocationUpdateService locationUpdateService) {
        this.locationUpdateService = locationUpdateService;
    }

    @Scheduled(fixedRate = 1000 * 60 * 2)
    private void updateLocation(){
        locationUpdateService.updatePlantLocation();
    }
}
