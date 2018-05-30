package com.dempseywood.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vividsolutions.jts.geom.Geometry;

public class GeofenceDTO {
    @JsonProperty("Id")
    private String id;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("Description")
    private String description;
    @JsonProperty("WellKnownText")
    private String wellKnownText;

    private Geometry geometry;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWellKnownText() {
        return wellKnownText;
    }

    public void setWellKnownText(String wellKnownText) {
        this.wellKnownText = wellKnownText;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    @Override
    public String toString() {
        return "GeofenceDTO{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", wellKnownText='" + wellKnownText + '\'' +
            ", geometry=" + geometry +
            '}';
    }
}
