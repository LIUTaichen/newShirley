package com.dempseywood.service;

import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Profile("prod")
public class JobScheduler {

    private final LocationUpdateService locationUpdateService;
    private final WeeklySnapshotService weeklySnapshotService;

    public JobScheduler(LocationUpdateService locationUpdateService, WeeklySnapshotService weeklySnapshotService) {
        this.locationUpdateService = locationUpdateService;
        this.weeklySnapshotService = weeklySnapshotService;
    }

    @Scheduled(fixedRate = 1000 * 60 * 2)
    private void updateLocation(){
        locationUpdateService.updatePlantLocation();
    }

    @Scheduled(cron="0 0 1 * * *", zone="NZ")
    private void takeSnapshot(){
        weeklySnapshotService.takeSnapshot();
    }
}
