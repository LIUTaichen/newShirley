package com.dempseywood.web.rest;

import com.dempseywood.FleetManagementApp;

import com.dempseywood.domain.Plant;
import com.dempseywood.repository.PlantRepository;
import com.dempseywood.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.dempseywood.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dempseywood.domain.enumeration.MeterUnit;
import com.dempseywood.domain.enumeration.HireStatus;
/**
 * Test class for the PlantResource REST controller.
 *
 * @see PlantResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FleetManagementApp.class)
public class PlantResourceIntTest {

    private static final String DEFAULT_FLEET_ID = "AAAAAAAAAA";
    private static final String UPDATED_FLEET_ID = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final Instant DEFAULT_PURCHASE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PURCHASE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_VIN = "AAAAAAAAAA";
    private static final String UPDATED_VIN = "BBBBBBBBBB";

    private static final String DEFAULT_REGO = "AAAAAAAAAA";
    private static final String UPDATED_REGO = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_OF_MANUFACTURE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_OF_MANUFACTURE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    private static final Integer DEFAULT_TANK_SIZE = 1;
    private static final Integer UPDATED_TANK_SIZE = 2;

    private static final Integer DEFAULT_MAINTENANCE_DUE_AT = 1;
    private static final Integer UPDATED_MAINTENANCE_DUE_AT = 2;

    private static final MeterUnit DEFAULT_METER_UNIT = MeterUnit.KM;
    private static final MeterUnit UPDATED_METER_UNIT = MeterUnit.HOUR;

    private static final Instant DEFAULT_CERTIFICATE_DUE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CERTIFICATE_DUE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_RUC_DUE_AT_KM = 1;
    private static final Integer UPDATED_RUC_DUE_AT_KM = 2;

    private static final Integer DEFAULT_HUBBO_READING = 1;
    private static final Integer UPDATED_HUBBO_READING = 2;

    private static final Integer DEFAULT_LOAD_CAPACITY = 1;
    private static final Integer UPDATED_LOAD_CAPACITY = 2;

    private static final Double DEFAULT_HOURLY_RATE = 1D;
    private static final Double UPDATED_HOURLY_RATE = 2D;

    private static final Instant DEFAULT_REGISTRATION_DUE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_REGISTRATION_DUE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final HireStatus DEFAULT_HIRE_STATUS = HireStatus.ONHIRE;
    private static final HireStatus UPDATED_HIRE_STATUS = HireStatus.OFFHIRE;

    private static final String DEFAULT_GPS_DEVICE_SERIAL = "AAAAAAAAAA";
    private static final String UPDATED_GPS_DEVICE_SERIAL = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_LOCATION_UPDATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_LOCATION_UPDATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private PlantRepository plantRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPlantMockMvc;

    private Plant plant;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PlantResource plantResource = new PlantResource(plantRepository);
        this.restPlantMockMvc = MockMvcBuilders.standaloneSetup(plantResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Plant createEntity(EntityManager em) {
        Plant plant = new Plant()
            .fleetId(DEFAULT_FLEET_ID)
            .name(DEFAULT_NAME)
            .notes(DEFAULT_NOTES)
            .purchaseDate(DEFAULT_PURCHASE_DATE)
            .isActive(DEFAULT_IS_ACTIVE)
            .description(DEFAULT_DESCRIPTION)
            .vin(DEFAULT_VIN)
            .rego(DEFAULT_REGO)
            .dateOfManufacture(DEFAULT_DATE_OF_MANUFACTURE)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE)
            .tankSize(DEFAULT_TANK_SIZE)
            .maintenanceDueAt(DEFAULT_MAINTENANCE_DUE_AT)
            .meterUnit(DEFAULT_METER_UNIT)
            .certificateDueDate(DEFAULT_CERTIFICATE_DUE_DATE)
            .rucDueAtKm(DEFAULT_RUC_DUE_AT_KM)
            .hubboReading(DEFAULT_HUBBO_READING)
            .loadCapacity(DEFAULT_LOAD_CAPACITY)
            .hourlyRate(DEFAULT_HOURLY_RATE)
            .registrationDueDate(DEFAULT_REGISTRATION_DUE_DATE)
            .hireStatus(DEFAULT_HIRE_STATUS)
            .gpsDeviceSerial(DEFAULT_GPS_DEVICE_SERIAL)
            .location(DEFAULT_LOCATION)
            .lastLocationUpdateTime(DEFAULT_LAST_LOCATION_UPDATE_TIME);
        return plant;
    }

    @Before
    public void initTest() {
        plant = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlant() throws Exception {
        int databaseSizeBeforeCreate = plantRepository.findAll().size();

        // Create the Plant
        restPlantMockMvc.perform(post("/api/plants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plant)))
            .andExpect(status().isCreated());

        // Validate the Plant in the database
        List<Plant> plantList = plantRepository.findAll();
        assertThat(plantList).hasSize(databaseSizeBeforeCreate + 1);
        Plant testPlant = plantList.get(plantList.size() - 1);
        assertThat(testPlant.getFleetId()).isEqualTo(DEFAULT_FLEET_ID);
        assertThat(testPlant.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPlant.getNotes()).isEqualTo(DEFAULT_NOTES);
        assertThat(testPlant.getPurchaseDate()).isEqualTo(DEFAULT_PURCHASE_DATE);
        assertThat(testPlant.isIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testPlant.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPlant.getVin()).isEqualTo(DEFAULT_VIN);
        assertThat(testPlant.getRego()).isEqualTo(DEFAULT_REGO);
        assertThat(testPlant.getDateOfManufacture()).isEqualTo(DEFAULT_DATE_OF_MANUFACTURE);
        assertThat(testPlant.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testPlant.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
        assertThat(testPlant.getTankSize()).isEqualTo(DEFAULT_TANK_SIZE);
        assertThat(testPlant.getMaintenanceDueAt()).isEqualTo(DEFAULT_MAINTENANCE_DUE_AT);
        assertThat(testPlant.getMeterUnit()).isEqualTo(DEFAULT_METER_UNIT);
        assertThat(testPlant.getCertificateDueDate()).isEqualTo(DEFAULT_CERTIFICATE_DUE_DATE);
        assertThat(testPlant.getRucDueAtKm()).isEqualTo(DEFAULT_RUC_DUE_AT_KM);
        assertThat(testPlant.getHubboReading()).isEqualTo(DEFAULT_HUBBO_READING);
        assertThat(testPlant.getLoadCapacity()).isEqualTo(DEFAULT_LOAD_CAPACITY);
        assertThat(testPlant.getHourlyRate()).isEqualTo(DEFAULT_HOURLY_RATE);
        assertThat(testPlant.getRegistrationDueDate()).isEqualTo(DEFAULT_REGISTRATION_DUE_DATE);
        assertThat(testPlant.getHireStatus()).isEqualTo(DEFAULT_HIRE_STATUS);
        assertThat(testPlant.getGpsDeviceSerial()).isEqualTo(DEFAULT_GPS_DEVICE_SERIAL);
        assertThat(testPlant.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testPlant.getLastLocationUpdateTime()).isEqualTo(DEFAULT_LAST_LOCATION_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void createPlantWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = plantRepository.findAll().size();

        // Create the Plant with an existing ID
        plant.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlantMockMvc.perform(post("/api/plants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plant)))
            .andExpect(status().isBadRequest());

        // Validate the Plant in the database
        List<Plant> plantList = plantRepository.findAll();
        assertThat(plantList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPlants() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        // Get all the plantList
        restPlantMockMvc.perform(get("/api/plants?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(plant.getId().intValue())))
            .andExpect(jsonPath("$.[*].fleetId").value(hasItem(DEFAULT_FLEET_ID.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES.toString())))
            .andExpect(jsonPath("$.[*].purchaseDate").value(hasItem(DEFAULT_PURCHASE_DATE.toString())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].vin").value(hasItem(DEFAULT_VIN.toString())))
            .andExpect(jsonPath("$.[*].rego").value(hasItem(DEFAULT_REGO.toString())))
            .andExpect(jsonPath("$.[*].dateOfManufacture").value(hasItem(DEFAULT_DATE_OF_MANUFACTURE.toString())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].tankSize").value(hasItem(DEFAULT_TANK_SIZE)))
            .andExpect(jsonPath("$.[*].maintenanceDueAt").value(hasItem(DEFAULT_MAINTENANCE_DUE_AT)))
            .andExpect(jsonPath("$.[*].meterUnit").value(hasItem(DEFAULT_METER_UNIT.toString())))
            .andExpect(jsonPath("$.[*].certificateDueDate").value(hasItem(DEFAULT_CERTIFICATE_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].rucDueAtKm").value(hasItem(DEFAULT_RUC_DUE_AT_KM)))
            .andExpect(jsonPath("$.[*].hubboReading").value(hasItem(DEFAULT_HUBBO_READING)))
            .andExpect(jsonPath("$.[*].loadCapacity").value(hasItem(DEFAULT_LOAD_CAPACITY)))
            .andExpect(jsonPath("$.[*].hourlyRate").value(hasItem(DEFAULT_HOURLY_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].registrationDueDate").value(hasItem(DEFAULT_REGISTRATION_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].hireStatus").value(hasItem(DEFAULT_HIRE_STATUS.toString())))
            .andExpect(jsonPath("$.[*].gpsDeviceSerial").value(hasItem(DEFAULT_GPS_DEVICE_SERIAL.toString())))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION.toString())))
            .andExpect(jsonPath("$.[*].lastLocationUpdateTime").value(hasItem(DEFAULT_LAST_LOCATION_UPDATE_TIME.toString())));
    }

    @Test
    @Transactional
    public void getPlant() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        // Get the plant
        restPlantMockMvc.perform(get("/api/plants/{id}", plant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(plant.getId().intValue()))
            .andExpect(jsonPath("$.fleetId").value(DEFAULT_FLEET_ID.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES.toString()))
            .andExpect(jsonPath("$.purchaseDate").value(DEFAULT_PURCHASE_DATE.toString()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.vin").value(DEFAULT_VIN.toString()))
            .andExpect(jsonPath("$.rego").value(DEFAULT_REGO.toString()))
            .andExpect(jsonPath("$.dateOfManufacture").value(DEFAULT_DATE_OF_MANUFACTURE.toString()))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.tankSize").value(DEFAULT_TANK_SIZE))
            .andExpect(jsonPath("$.maintenanceDueAt").value(DEFAULT_MAINTENANCE_DUE_AT))
            .andExpect(jsonPath("$.meterUnit").value(DEFAULT_METER_UNIT.toString()))
            .andExpect(jsonPath("$.certificateDueDate").value(DEFAULT_CERTIFICATE_DUE_DATE.toString()))
            .andExpect(jsonPath("$.rucDueAtKm").value(DEFAULT_RUC_DUE_AT_KM))
            .andExpect(jsonPath("$.hubboReading").value(DEFAULT_HUBBO_READING))
            .andExpect(jsonPath("$.loadCapacity").value(DEFAULT_LOAD_CAPACITY))
            .andExpect(jsonPath("$.hourlyRate").value(DEFAULT_HOURLY_RATE.doubleValue()))
            .andExpect(jsonPath("$.registrationDueDate").value(DEFAULT_REGISTRATION_DUE_DATE.toString()))
            .andExpect(jsonPath("$.hireStatus").value(DEFAULT_HIRE_STATUS.toString()))
            .andExpect(jsonPath("$.gpsDeviceSerial").value(DEFAULT_GPS_DEVICE_SERIAL.toString()))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION.toString()))
            .andExpect(jsonPath("$.lastLocationUpdateTime").value(DEFAULT_LAST_LOCATION_UPDATE_TIME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPlant() throws Exception {
        // Get the plant
        restPlantMockMvc.perform(get("/api/plants/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlant() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);
        int databaseSizeBeforeUpdate = plantRepository.findAll().size();

        // Update the plant
        Plant updatedPlant = plantRepository.findOne(plant.getId());
        // Disconnect from session so that the updates on updatedPlant are not directly saved in db
        em.detach(updatedPlant);
        updatedPlant
            .fleetId(UPDATED_FLEET_ID)
            .name(UPDATED_NAME)
            .notes(UPDATED_NOTES)
            .purchaseDate(UPDATED_PURCHASE_DATE)
            .isActive(UPDATED_IS_ACTIVE)
            .description(UPDATED_DESCRIPTION)
            .vin(UPDATED_VIN)
            .rego(UPDATED_REGO)
            .dateOfManufacture(UPDATED_DATE_OF_MANUFACTURE)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .tankSize(UPDATED_TANK_SIZE)
            .maintenanceDueAt(UPDATED_MAINTENANCE_DUE_AT)
            .meterUnit(UPDATED_METER_UNIT)
            .certificateDueDate(UPDATED_CERTIFICATE_DUE_DATE)
            .rucDueAtKm(UPDATED_RUC_DUE_AT_KM)
            .hubboReading(UPDATED_HUBBO_READING)
            .loadCapacity(UPDATED_LOAD_CAPACITY)
            .hourlyRate(UPDATED_HOURLY_RATE)
            .registrationDueDate(UPDATED_REGISTRATION_DUE_DATE)
            .hireStatus(UPDATED_HIRE_STATUS)
            .gpsDeviceSerial(UPDATED_GPS_DEVICE_SERIAL)
            .location(UPDATED_LOCATION)
            .lastLocationUpdateTime(UPDATED_LAST_LOCATION_UPDATE_TIME);

        restPlantMockMvc.perform(put("/api/plants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPlant)))
            .andExpect(status().isOk());

        // Validate the Plant in the database
        List<Plant> plantList = plantRepository.findAll();
        assertThat(plantList).hasSize(databaseSizeBeforeUpdate);
        Plant testPlant = plantList.get(plantList.size() - 1);
        assertThat(testPlant.getFleetId()).isEqualTo(UPDATED_FLEET_ID);
        assertThat(testPlant.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPlant.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testPlant.getPurchaseDate()).isEqualTo(UPDATED_PURCHASE_DATE);
        assertThat(testPlant.isIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testPlant.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPlant.getVin()).isEqualTo(UPDATED_VIN);
        assertThat(testPlant.getRego()).isEqualTo(UPDATED_REGO);
        assertThat(testPlant.getDateOfManufacture()).isEqualTo(UPDATED_DATE_OF_MANUFACTURE);
        assertThat(testPlant.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testPlant.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
        assertThat(testPlant.getTankSize()).isEqualTo(UPDATED_TANK_SIZE);
        assertThat(testPlant.getMaintenanceDueAt()).isEqualTo(UPDATED_MAINTENANCE_DUE_AT);
        assertThat(testPlant.getMeterUnit()).isEqualTo(UPDATED_METER_UNIT);
        assertThat(testPlant.getCertificateDueDate()).isEqualTo(UPDATED_CERTIFICATE_DUE_DATE);
        assertThat(testPlant.getRucDueAtKm()).isEqualTo(UPDATED_RUC_DUE_AT_KM);
        assertThat(testPlant.getHubboReading()).isEqualTo(UPDATED_HUBBO_READING);
        assertThat(testPlant.getLoadCapacity()).isEqualTo(UPDATED_LOAD_CAPACITY);
        assertThat(testPlant.getHourlyRate()).isEqualTo(UPDATED_HOURLY_RATE);
        assertThat(testPlant.getRegistrationDueDate()).isEqualTo(UPDATED_REGISTRATION_DUE_DATE);
        assertThat(testPlant.getHireStatus()).isEqualTo(UPDATED_HIRE_STATUS);
        assertThat(testPlant.getGpsDeviceSerial()).isEqualTo(UPDATED_GPS_DEVICE_SERIAL);
        assertThat(testPlant.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testPlant.getLastLocationUpdateTime()).isEqualTo(UPDATED_LAST_LOCATION_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingPlant() throws Exception {
        int databaseSizeBeforeUpdate = plantRepository.findAll().size();

        // Create the Plant

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPlantMockMvc.perform(put("/api/plants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plant)))
            .andExpect(status().isCreated());

        // Validate the Plant in the database
        List<Plant> plantList = plantRepository.findAll();
        assertThat(plantList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePlant() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);
        int databaseSizeBeforeDelete = plantRepository.findAll().size();

        // Get the plant
        restPlantMockMvc.perform(delete("/api/plants/{id}", plant.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Plant> plantList = plantRepository.findAll();
        assertThat(plantList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Plant.class);
        Plant plant1 = new Plant();
        plant1.setId(1L);
        Plant plant2 = new Plant();
        plant2.setId(plant1.getId());
        assertThat(plant1).isEqualTo(plant2);
        plant2.setId(2L);
        assertThat(plant1).isNotEqualTo(plant2);
        plant1.setId(null);
        assertThat(plant1).isNotEqualTo(plant2);
    }
}
