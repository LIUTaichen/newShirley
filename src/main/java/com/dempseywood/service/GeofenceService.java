package com.dempseywood.service;

import com.dempseywood.config.ApplicationProperties;
import com.dempseywood.repository.PlantRepository;
import com.dempseywood.service.dto.GeofenceDTO;
import com.vividsolutions.jts.geom.*;
import com.vividsolutions.jts.io.WKTReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class GeofenceService {

    private BlackhawkSessionIdService blackhawkSessionIdService;
    private ApplicationProperties props;
    private PlantRepository plantRepository;
    private PlantService plantService;
    private final Logger log = LoggerFactory.getLogger(GeofenceService.class);
    private WKTReader reader = new WKTReader();
    private GeometryFactory geometryFactory = new GeometryFactory();

    private List<GeofenceDTO> geofences;


    public GeofenceService(BlackhawkSessionIdService blackhawkSessionIdService, ApplicationProperties props, PlantRepository plantRepository, PlantService plantService) {
        this.blackhawkSessionIdService = blackhawkSessionIdService;
        this.props = props;
        this.plantRepository = plantRepository;
        this.plantService = plantService;
    }



    @Scheduled(fixedRate = 1000 * 60 * 10)
    @PostConstruct
    public void refreshGeofences(){
        GeofenceDTO[] rawFences = this.getGeofencesFromBlackhawk();
        List<GeofenceDTO> parsedGeofences = this.parseGeometry(rawFences);
        this.geofences = parsedGeofences;
    }


     public GeofenceDTO[] getGeofencesFromBlackhawk(){
        RestTemplate restTemplate = new RestTemplate();
        String sessionId = blackhawkSessionIdService.getSessionId();
        HttpHeaders headers = new HttpHeaders();
        headers.set("token", sessionId);
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        String url = props.getBlackhawkApi().getUrl().getRoot() + props.getBlackhawkApi().getUrl().getGeofence();
         GeofenceDTO[] geofences = restTemplate.exchange(url, HttpMethod.GET, entity, GeofenceDTO[].class).getBody();
        return geofences;
    }

      public List<GeofenceDTO> parseGeometry(GeofenceDTO[] geofenceDTOs){
          List<GeofenceDTO> geofences = new ArrayList<>();
         for(GeofenceDTO dto: geofenceDTOs){
             if(dto.getWellKnownText().isEmpty() || dto.getWellKnownText().equals("Null")){
                 continue;
             }
             try {
                 Geometry polygon = reader.read(dto.getWellKnownText());
                 dto.setGeometry(polygon);
                 geofences.add(dto);
             }catch (Exception e){
                 log.debug(dto.toString());
                 log.error("error when parsing geofences", e);
             }
         }
         return geofences;
      }

      public GeofenceDTO getContainingGeofence(double lat, double lng){
          if(this.geofences ==null || this.geofences.isEmpty()){
              return null;
          }
          Point point = geometryFactory.createPoint(new Coordinate(lng, lat));
          if(!point.isValid()){
              return null;
          }
          for(GeofenceDTO geofence : geofences){
              if(geofence.getGeometry().contains(point)){
                return geofence;
              }
          }
          return null;
      }

}
