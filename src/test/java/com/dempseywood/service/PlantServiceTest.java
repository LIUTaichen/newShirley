package com.dempseywood.service;

import com.dempseywood.FleetManagementApp;
import com.dempseywood.domain.Location;
import com.dempseywood.domain.Plant;
import com.dempseywood.repository.LocationRepository;
import com.dempseywood.service.util.DistanceUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FleetManagementApp.class)
@Transactional
public class PlantServiceTest {

    private final Logger log = LoggerFactory.getLogger(PlantServiceTest.class);


    private static final Double hqlat = -36.91514;
    private static final Double hqLng = 174.8088572;



    private static final Double stadiumLat = -36.917322;
    private static final Double stadiumLng = 174.811258;
    private Location hq;
    private Location stadium;
    private Plant plantAtHq;
    private Plant plantAtStatiusm;

    @Autowired
    private PlantService plantService;

    @Autowired
    private LocationRepository locationRepository;

    @Before
    public void setup(){
        hq = new Location();
        hq.setLatitude(hqlat);
        hq.setLongitude(hqLng);
        locationRepository.save(hq);

        plantAtHq = new Plant();
        plantAtHq.setLocation(hq);
        plantService.save(plantAtHq);

        stadium = new Location();
        stadium.setLatitude(stadiumLat);
        stadium.setLongitude(stadiumLng);
        locationRepository.save(stadium);
        plantAtStatiusm = new Plant();
        plantAtStatiusm.setLocation(stadium);
        plantService.save(plantAtStatiusm);

    }


    @Test
    public void assertThatDistanceCanBeCalculated(){
        log.info(DistanceUtil.getDistance(hqlat,hqLng, stadiumLat, stadiumLng) +" ");
    }


    @Test
    @Transactional
    public void assetThatHqPlantIsReturnedWhenRequestingPlantsWithin100MeterOfHQ(){
        List<Plant> returnedPlants = plantService.findByLocation(hqlat,hqLng,100D);
        assertThat(returnedPlants).contains(plantAtHq);
    }

    @Test
    @Transactional
    public void assetThatPlantAtStadiumIsNotReturnedWhenRequestingPlantsWithin100MeterOfHQ(){
        List<Plant> returnedPlants = plantService.findByLocation(hqlat,hqLng,100D);
        assertThat(returnedPlants).doesNotContain(plantAtStatiusm);
    }

    @Test
    @Transactional
    public void assetThatBothPlantsAreReturnedWhenRequestingPlantsWithin1000MeterOfHQ(){
        List<Plant> returnedPlants = plantService.findByLocation(hqlat,hqLng,1000D);
        assertThat(returnedPlants).contains(plantAtHq).contains(plantAtStatiusm);
    }
}
