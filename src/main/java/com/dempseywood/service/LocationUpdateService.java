package com.dempseywood.service;

import com.dempseywood.config.ApplicationProperties;
import com.dempseywood.domain.Location;
import com.dempseywood.domain.Plant;
import com.dempseywood.repository.LocationRepository;
import com.dempseywood.repository.PlantRepository;
import com.dempseywood.service.dto.GeofenceDTO;
import com.dempseywood.service.dto.VehicleDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LocationUpdateService {

    private BlackhawkSessionIdService blackhawkSessionIdService;
    private ApplicationProperties props;
    private PlantRepository plantRepository;
    private PlantService plantService;
    private GeofenceService geofenceService;
    private final Logger log = LoggerFactory.getLogger(LocationUpdateService.class);

    public LocationUpdateService(BlackhawkSessionIdService blackhawkSessionIdService,
                                 ApplicationProperties props,
                                 PlantRepository plantRepository,
                                 PlantService plantService,
                                 GeofenceService geofenceService) {
        this.blackhawkSessionIdService = blackhawkSessionIdService;
        this.props = props;
        this.plantRepository = plantRepository;
        this.plantService = plantService;
        this.geofenceService = geofenceService;
    }


    public VehicleDTO[] getVehicles(){
        RestTemplate restTemplate = new RestTemplate();
        String sessionId = blackhawkSessionIdService.getSessionId();
        HttpHeaders headers = new HttpHeaders();
        headers.set("token", sessionId);
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        String url = props.getBlackhawkApi().getUrl().getRoot() + props.getBlackhawkApi().getUrl().getVehicle();
        VehicleDTO[] vehicles = restTemplate.exchange(url, HttpMethod.GET, entity, VehicleDTO[].class).getBody();
        return vehicles;
    }

    public List<Plant> getPlants(){
       return this.plantRepository.findByGpsDeviceSerialIsNotNull();
    }

    public List<Plant> generatePlantsToBeUpdated(Map<String, VehicleDTO> vehiclesMap, List<Plant> plants){
        List<Plant> plantsToUpdate = new ArrayList<>();
        for(Plant plant: plants){
            if(!vehiclesMap.containsKey(plant.getGpsDeviceSerial())){
                continue;
            }else{
                VehicleDTO vehicle = vehiclesMap.get(plant.getGpsDeviceSerial());
                if(plant.getLocation() == null){
                    Location location = new Location();
                    plant.setLocation(location);
                }
                if(plant.getLocation().getTimestamp() == null
                     || vehicle.getLastValidGpsTime().isAfter(plant.getLocation().getTimestamp())){
                    plant.getLocation().setTimestamp(vehicle.getLastValidGpsTime());
                    plant.getLocation().setAddress(vehicle.getAddress());
                    Double latitude = vehicle.getPoint().getLatitude();
                    Double longitude = vehicle.getPoint().getLongitude();
                    plant.getLocation().setLatitude(latitude);
                    plant.getLocation().setLongitude(longitude);
                    GeofenceDTO geofence = this.geofenceService.getContainingGeofence(latitude, longitude);
                    if(geofence != null){
                        String newAddress;
                        if(plant.getLocation().getAddress().isEmpty()){
                            newAddress = "Site: "  +geofence.getDescription();
                        }else{
                            newAddress = plant.getLocation().getAddress() +", Site: "  +geofence.getDescription();
                        }
                        plant.getLocation().setAddress(newAddress);
                    }
                    plantsToUpdate.add(plant);
                }
            }
        }
        return plantsToUpdate;
    }

    public Map<String, VehicleDTO> generateVehiclesMap(VehicleDTO[] vehicles){
        Map<String, VehicleDTO> map = new HashMap<String, VehicleDTO>();

        for(VehicleDTO vehicle : vehicles){
            String deviceSerial = vehicle.getVehicleInformationDTO().getDeviceDTO().getSerialNumber();
            if(deviceSerial.startsWith("2")){
                try{
                    deviceSerial =  Integer.toHexString(Integer.valueOf(deviceSerial)).toUpperCase();
                }catch(Exception e){
                    log.error("exception when converting decimal sigfox device serial: " + deviceSerial + " to hex", e);
                    continue;
                }
            }
            map.put(deviceSerial,vehicle);
        }
        return map;
    }


    public void updatePlantLocation(){
        log.info("starting to update plant location");
        VehicleDTO[] vehicles = getVehicles();
        List<Plant> plants = getPlants();
        Map<String, VehicleDTO> map = generateVehiclesMap(vehicles);
        List<Plant> plantsToUpDate = generatePlantsToBeUpdated(map,plants);
        log.info("number of plants to be updated: " + plantsToUpDate.size() );
        plantsToUpDate.forEach(plant-> {
            plantService.savePlantLocation(plant);
        });
    }
}
