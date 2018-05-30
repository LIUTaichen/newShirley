package com.dempseywood.service;

import com.dempseywood.FleetManagementApp;
import com.dempseywood.domain.Location;
import com.dempseywood.domain.Plant;
import com.dempseywood.repository.LocationRepository;
import com.dempseywood.service.dto.DeviceDTO;
import com.dempseywood.service.dto.PointDTO;
import com.dempseywood.service.dto.VehicleDTO;
import com.dempseywood.service.dto.VehicleInformationDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FleetManagementApp.class)
@Transactional
public class LocationUpdateServiceTest {
    @Autowired
    private LocationUpdateService locationUpdateService;
    @Autowired
    private LocationRepository locationRepository;

    @Test
    @Transactional
    public void assertThatVehiclesAreRetreived() {
        VehicleDTO[] vehicles = locationUpdateService.getVehicles();
        assertThat(vehicles.length > 1).isTrue();
    }

    @Test
    @Transactional
    public void assertThatPlantsAreRetreived() {
        List<Plant> plants = locationUpdateService.getPlants();
        assertThat(plants).isNotNull();
    }

    @Test
    @Transactional
    public void assertThatDecimalSigfoxSerialIsConvertedToHex() {
        VehicleDTO[] vehicles = new VehicleDTO[1];
        VehicleDTO vehicle = new VehicleDTO();
        vehicle.setVehicleInformationDTO(new VehicleInformationDTO());
        vehicle.getVehicleInformationDTO().setDeviceDTO(new DeviceDTO());
        vehicle.getVehicleInformationDTO().getDeviceDTO().setSerialNumber("2903086");
        vehicles[0] = vehicle;
        Map<String, VehicleDTO> map = locationUpdateService.generateVehiclesMap(vehicles);
        assertThat(map.containsKey("2C4C2E")).isTrue();
    }

    @Test
    @Transactional
    public void assertThatG5SerialIsNotConverted() {
        VehicleDTO[] vehicles = new VehicleDTO[1];
        VehicleDTO vehicle = new VehicleDTO();
        vehicle.setVehicleInformationDTO(new VehicleInformationDTO());
        vehicle.getVehicleInformationDTO().setDeviceDTO(new DeviceDTO());
        vehicle.getVehicleInformationDTO().getDeviceDTO().setSerialNumber("526168");
        vehicles[0] = vehicle;
        Map<String, VehicleDTO> map = locationUpdateService.generateVehiclesMap(vehicles);
        assertThat(map.containsKey("526168")).isTrue();
    }

    @Test
    @Transactional
    public void assertThatWhenFresherLocationDataIsRetrievedPlantLocationIsUpdated() {
        VehicleDTO[] vehicles = new VehicleDTO[1];
        VehicleDTO vehicle = new VehicleDTO();
        vehicle.setVehicleInformationDTO(new VehicleInformationDTO());
        vehicle.getVehicleInformationDTO().setDeviceDTO(new DeviceDTO());
        vehicle.getVehicleInformationDTO().getDeviceDTO().setSerialNumber("526168");
        vehicle.setAddress("new location");
        vehicle.setLastValidGpsTime(Instant.now());
        vehicle.setPoint(new PointDTO());
        vehicles[0] = vehicle;

        Plant plant = new Plant();
        plant.setGpsDeviceSerial("526168");
        Location location = new Location();
        location.setAddress("stale location");
        location.setTimestamp(Instant.now().minus(1, ChronoUnit.DAYS));
        location = locationRepository.save(location);
        plant.setLocation(location);
        List<Plant> plantList = new ArrayList<>();
        plantList.add(plant);
        Map<String, VehicleDTO> map = locationUpdateService.generateVehiclesMap(vehicles);
        Plant updatedPlant = locationUpdateService.generatePlantsToBeUpdated(map , plantList).iterator().next();
        assertThat(updatedPlant.getLocation().getTimestamp().equals(vehicle.getLastValidGpsTime())).isTrue();
        assertThat(updatedPlant.getLocation().getAddress().equals(vehicle.getAddress())).isTrue();
    }

    @Test
    @Transactional
    public void assertThatWhenPlantLocationUpdateTimeIsNullItIsUpdated() {
        VehicleDTO[] vehicles = new VehicleDTO[1];
        VehicleDTO vehicle = new VehicleDTO();
        vehicle.setVehicleInformationDTO(new VehicleInformationDTO());
        vehicle.getVehicleInformationDTO().setDeviceDTO(new DeviceDTO());
        vehicle.getVehicleInformationDTO().getDeviceDTO().setSerialNumber("526168");
        vehicle.setAddress("new location");
        vehicle.setLastValidGpsTime(Instant.now());
        vehicle.setPoint(new PointDTO());
        vehicles[0] = vehicle;

        Plant plant = new Plant();
        plant.setGpsDeviceSerial("526168");
        Location location = new Location();
        location.setAddress("stale location");
        location.setTimestamp(null);
        location = locationRepository.save(location);
        plant.setLocation(location);
        List<Plant> plantList = new ArrayList<>();
        plantList.add(plant);
        Map<String, VehicleDTO> map = locationUpdateService.generateVehiclesMap(vehicles);
        Plant updatedPlant = locationUpdateService.generatePlantsToBeUpdated(map , plantList).iterator().next();
        assertThat(updatedPlant.getLocation().getTimestamp().equals(vehicle.getLastValidGpsTime())).isTrue();
        assertThat(updatedPlant.getLocation().getAddress().equals(vehicle.getAddress())).isTrue();
    }


    @Test
    @Transactional
    public void assertThatWhenStaleLocationDataIsRetrievedPlantIsNotUpdated() {
        VehicleDTO[] vehicles = new VehicleDTO[1];
        VehicleDTO vehicle = new VehicleDTO();
        vehicle.setVehicleInformationDTO(new VehicleInformationDTO());
        vehicle.getVehicleInformationDTO().setDeviceDTO(new DeviceDTO());
        vehicle.getVehicleInformationDTO().getDeviceDTO().setSerialNumber("526168");
        vehicle.setAddress("stale location");
        vehicle.setLastValidGpsTime(Instant.now().minus(1, ChronoUnit.DAYS));
        vehicles[0] = vehicle;

        Plant plant = new Plant();
        plant.setGpsDeviceSerial("526168");
        Location location = new Location();
        location.setAddress("fresher location");
        location.setTimestamp(Instant.now());
        location = locationRepository.save(location);
        plant.setLocation(location);
        List<Plant> plantList = new ArrayList<>();
        plantList.add(plant);
        Map<String, VehicleDTO> map = locationUpdateService.generateVehiclesMap(vehicles);
        assertThat(locationUpdateService.generatePlantsToBeUpdated(map , plantList)).isEmpty();
    }


}
