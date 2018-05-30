package com.dempseywood.service;

import com.dempseywood.FleetManagementApp;
import com.dempseywood.service.dto.GeofenceDTO;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.WKTReader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FleetManagementApp.class)
@Transactional
public class GeofenceServiceTest {

    @Autowired
    private GeofenceService geofenceService;

    @Test
    public void assertThatGeofencesAreRetrieved() throws Exception {
        GeofenceDTO[] geofences = geofenceService.getGeofencesFromBlackhawk();
        assertThat(geofences).isNotEmpty();
        assertThat(geofences[0].getWellKnownText()).isNotBlank().isNotNull();
        WKTReader reader = new WKTReader();

        Geometry polygon = reader.read(geofences[0].getWellKnownText());
        assertThat(polygon.getCoordinates()).isNotEmpty().isNotNull();
    }

    @Test
    public void assertThatAucklandRegionIsExcludedWhenParsingGeofences() throws Exception {
        GeofenceDTO[] rawGeofences = geofenceService.getGeofencesFromBlackhawk();
        List<GeofenceDTO> geofences = geofenceService.parseGeometry(rawGeofences);
        List<GeofenceDTO> filtered = geofences.stream().filter(geofenceDTO -> {
            if(geofenceDTO.getName().equals("Auckland Region")){
                return true;
            }else{
                return false;
            }
        }).collect(Collectors.toList());
        assertThat(filtered).isEmpty();
    }

    @Test
    public void assertThatAGeofenceCanBeFoundForCoordinatesOfHeadQuater() throws Exception {
        GeofenceDTO geofence = geofenceService.getContainingGeofence(-36.9161428,174.8097221);
        assertThat(geofence).isNotNull();
    }


}
