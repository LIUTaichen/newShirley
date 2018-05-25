package com.dempseywood.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;


public class VehicleInformationDTO {
    @JsonProperty("Device")
    private DeviceDTO deviceDTO;

    public DeviceDTO getDeviceDTO() {
        return deviceDTO;
    }

    public void setDeviceDTO(DeviceDTO deviceDTO) {
        this.deviceDTO = deviceDTO;
    }
}
