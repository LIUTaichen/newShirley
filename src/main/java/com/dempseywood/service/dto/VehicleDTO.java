package com.dempseywood.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public class VehicleDTO {
    @JsonProperty("Name")
    private String name;
    @JsonProperty("Id")
    private Long id;
    @JsonProperty("CurrentAddress")
    private String address;
    @JsonProperty("Point")
    private PointDTO point;
    @JsonProperty("VehicleInformation")
    private VehicleInformationDTO vehicleInformationDTO;
    @JsonProperty("LastValidGpsTime")
    private Instant lastValidGpsTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public PointDTO getPoint() {
        return point;
    }

    public void setPoint(PointDTO point) {
        this.point = point;
    }

    public VehicleInformationDTO getVehicleInformationDTO() {
        return vehicleInformationDTO;
    }

    public void setVehicleInformationDTO(VehicleInformationDTO vehicleInformationDTO) {
        this.vehicleInformationDTO = vehicleInformationDTO;
    }

    public Instant getLastValidGpsTime() {
        return lastValidGpsTime;
    }

    public void setLastValidGpsTime(Instant lastValidGpsTime) {
        this.lastValidGpsTime = lastValidGpsTime;
    }
}
