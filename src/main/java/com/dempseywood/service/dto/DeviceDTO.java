package com.dempseywood.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeviceDTO {

    @JsonProperty("SerialNumber")
    private String serialNumber;

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
}
