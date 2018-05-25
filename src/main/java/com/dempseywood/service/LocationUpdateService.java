package com.dempseywood.service;

import com.dempseywood.config.ApplicationProperties;
import com.dempseywood.service.dto.LoginDTO;
import com.dempseywood.service.dto.VehicleDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class LocationUpdateService {

    private BlackhawkSessionIdService blackhawkSessionIdService;
    private ApplicationProperties props;

    public LocationUpdateService(BlackhawkSessionIdService blackhawkSessionIdService,
                                 ApplicationProperties props) {
        this.blackhawkSessionIdService = blackhawkSessionIdService;
        this.props = props;
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
}
