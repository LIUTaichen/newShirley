package com.dempseywood.service;

import com.dempseywood.config.ApplicationProperties;
import com.dempseywood.service.dto.LoginDTO;
import com.dempseywood.web.rest.NiggleResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

@Service
public class BlackhawkSessionIdService {

    private final Logger log = LoggerFactory.getLogger(BlackhawkSessionIdService.class);

    private ApplicationProperties props;

    private String sessionId;

    public BlackhawkSessionIdService(ApplicationProperties props) {
        this.props = props;
    }

    @PostConstruct
    public void init(){

        log.debug(props.getBlackhawkApi().getUsername());
        log.debug(props.getBlackhawkApi().getPassword());
        log.debug(props.getBlackhawkApi().getUrl().getRoot());
        log.debug(props.getBlackhawkApi().getUrl().getLogin());
        LoginDTO loginDTO = constructLogin();
        RestTemplate restTemplate = new RestTemplate();
        String loginUrl = props.getBlackhawkApi().getUrl().getRoot()+props.getBlackhawkApi().getUrl().getLogin();
        HttpEntity<LoginDTO> request = new HttpEntity<>(loginDTO);
        sessionId = restTemplate.postForObject(loginUrl, request, String.class);
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
}
