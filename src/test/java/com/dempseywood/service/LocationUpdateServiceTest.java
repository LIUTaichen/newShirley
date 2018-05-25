package com.dempseywood.service;

import com.dempseywood.FleetManagementApp;
import com.dempseywood.service.dto.VehicleDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FleetManagementApp.class)
@Transactional
public class LocationUpdateServiceTest {
    @Autowired
    private LocationUpdateService locationUpdateService;

    @Test
    @Transactional
    public void assertThatVehiclesAreRetreived() {
        VehicleDTO[] vehicles = locationUpdateService.getVehicles();
        assertThat(vehicles.length > 1).isTrue();
    }

}
