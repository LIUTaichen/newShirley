package com.dempseywood.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginDTO {
    @JsonProperty("IsValid")
    private Boolean isValid;
    @JsonProperty("Username")
    private String username;
    @JsonProperty("Password")
    private String password;
    @JsonProperty("ApplicationId")
    private String applicationId;
    @JsonProperty("RequestTemporaryToken")
    private Boolean requestTemporaryToken;
    @JsonProperty("CompanyId")
    private Long companyId;

    public Boolean getValid() {
        return isValid;
    }

    public void setValid(Boolean valid) {
        isValid = valid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public Boolean getRequestTemporaryToken() {
        return requestTemporaryToken;
    }

    public void setRequestTemporaryToken(Boolean requestTemporaryToken) {
        this.requestTemporaryToken = requestTemporaryToken;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
}
