package com.dempseywood.service;

import com.dempseywood.config.ApplicationProperties;
import com.dempseywood.domain.Plant;
import com.dempseywood.repository.PlantRepository;
import com.dempseywood.service.dto.VehicleDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
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
    private final Logger log = LoggerFactory.getLogger(LocationUpdateService.class);

    public LocationUpdateService(BlackhawkSessionIdService blackhawkSessionIdService,
                                 ApplicationProperties props,
                                 PlantRepository plantRepository) {
        this.blackhawkSessionIdService = blackhawkSessionIdService;
        this.props = props;
        this.plantRepository = plantRepository;
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
                if(vehicle.getLastValidGpsTime().isAfter(plant.getLastLocationUpdateTime())){
                    plant.setLastLocationUpdateTime(vehicle.getLastValidGpsTime());
                    plant.setLocation(vehicle.getAddress());
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
}
