package com.dempseywood.service;

import com.dempseywood.config.ApplicationProperties;
import com.dempseywood.service.dto.LoginDTO;
import com.dempseywood.web.rest.NiggleResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;

@Service
public class BlackhawkSessionIdService {

    private final Logger log = LoggerFactory.getLogger(BlackhawkSessionIdService.class);

    private ApplicationProperties props;

    private String sessionId;
    private String loginUrl;

    public BlackhawkSessionIdService(ApplicationProperties props) {
        this.props = props;
    }

    @PostConstruct
    public void init(){
        this.loginUrl = props.getBlackhawkApi().getUrl().getRoot() + props.getBlackhawkApi().getUrl().getLogin();
        log.debug(props.getBlackhawkApi().getUsername());
        log.debug(props.getBlackhawkApi().getPassword());
        log.debug(props.getBlackhawkApi().getUrl().getRoot());
        log.debug(props.getBlackhawkApi().getUrl().getLogin());
        renewSessionId();
    }

    public void renewSessionId() {
        LoginDTO loginDTO = constructLogin();
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<LoginDTO> request = new HttpEntity<>(loginDTO);
        sessionId = restTemplate.postForObject(loginUrl, request, String.class).replace("\"","");
        log.debug("retrieved sessionID: " + sessionId);
    }

    private LoginDTO constructLogin(){
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername(props.getBlackhawkApi().getUsername());
        loginDTO.setPassword(props.getBlackhawkApi().getPassword());
        loginDTO.setValid(Boolean.TRUE);
        loginDTO.setApplicationId(props.getBlackhawkApi().getApplicationId());
        loginDTO.setRequestTemporaryToken(Boolean.FALSE);
        loginDTO.setCompanyId(props.getBlackhawkApi().getCompanyId());
        return loginDTO;
    }

    public String getSessionId() {
        return sessionId;
    }

    public Boolean isSessionIdValid(){
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(loginUrl)
            .queryParam("sessionId", sessionId);
        String uriString = builder.toUriString();
        HttpHeaders headers = new HttpHeaders();
        headers.set("token", sessionId);
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        LoginDTO login = restTemplate.exchange(uriString, HttpMethod.GET, entity, LoginDTO.class).getBody();
        if(login == null){
            return false;
        }else{
            return login.getValid();
        }

    }
}
