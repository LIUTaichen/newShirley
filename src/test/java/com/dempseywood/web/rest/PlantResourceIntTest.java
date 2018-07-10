package com.dempseywood.web.rest;

import com.dempseywood.FleetManagementApp;

import com.dempseywood.domain.Plant;
import com.dempseywood.domain.Location;
import com.dempseywood.domain.PlantLog;
import com.dempseywood.domain.Category;
import com.dempseywood.domain.Company;
import com.dempseywood.domain.MaintenanceContractor;
import com.dempseywood.domain.Project;
import com.dempseywood.repository.PlantRepository;
import com.dempseywood.service.PlantService;
import com.dempseywood.web.rest.errors.ExceptionTranslator;
import com.dempseywood.service.dto.PlantCriteria;
import com.dempseywood.service.PlantQueryService;

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

    private static final Integer DEFAULT_METER_READING = 1;
    private static final Integer UPDATED_METER_READING = 2;

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

    @Autowired
    private PlantRepository plantRepository;

    @Autowired
    private PlantService plantService;

    @Autowired
    private PlantQueryService plantQueryService;

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
        final PlantResource plantResource = new PlantResource(plantService, plantQueryService);
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
            .meterReading(DEFAULT_METER_READING)
            .maintenanceDueAt(DEFAULT_MAINTENANCE_DUE_AT)
            .meterUnit(DEFAULT_METER_UNIT)
            .certificateDueDate(DEFAULT_CERTIFICATE_DUE_DATE)
            .rucDueAtKm(DEFAULT_RUC_DUE_AT_KM)
            .hubboReading(DEFAULT_HUBBO_READING)
            .loadCapacity(DEFAULT_LOAD_CAPACITY)
            .hourlyRate(DEFAULT_HOURLY_RATE)
            .registrationDueDate(DEFAULT_REGISTRATION_DUE_DATE)
            .hireStatus(DEFAULT_HIRE_STATUS)
            .gpsDeviceSerial(DEFAULT_GPS_DEVICE_SERIAL);
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
        assertThat(testPlant.getMeterReading()).isEqualTo(DEFAULT_METER_READING);
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
            .andExpect(jsonPath("$.[*].meterReading").value(hasItem(DEFAULT_METER_READING)))
            .andExpect(jsonPath("$.[*].maintenanceDueAt").value(hasItem(DEFAULT_MAINTENANCE_DUE_AT)))
            .andExpect(jsonPath("$.[*].meterUnit").value(hasItem(DEFAULT_METER_UNIT.toString())))
            .andExpect(jsonPath("$.[*].certificateDueDate").value(hasItem(DEFAULT_CERTIFICATE_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].rucDueAtKm").value(hasItem(DEFAULT_RUC_DUE_AT_KM)))
            .andExpect(jsonPath("$.[*].hubboReading").value(hasItem(DEFAULT_HUBBO_READING)))
            .andExpect(jsonPath("$.[*].loadCapacity").value(hasItem(DEFAULT_LOAD_CAPACITY)))
            .andExpect(jsonPath("$.[*].hourlyRate").value(hasItem(DEFAULT_HOURLY_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].registrationDueDate").value(hasItem(DEFAULT_REGISTRATION_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].hireStatus").value(hasItem(DEFAULT_HIRE_STATUS.toString())))
            .andExpect(jsonPath("$.[*].gpsDeviceSerial").value(hasItem(DEFAULT_GPS_DEVICE_SERIAL.toString())));
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
            .andExpect(jsonPath("$.meterReading").value(DEFAULT_METER_READING))
            .andExpect(jsonPath("$.maintenanceDueAt").value(DEFAULT_MAINTENANCE_DUE_AT))
            .andExpect(jsonPath("$.meterUnit").value(DEFAULT_METER_UNIT.toString()))
            .andExpect(jsonPath("$.certificateDueDate").value(DEFAULT_CERTIFICATE_DUE_DATE.toString()))
            .andExpect(jsonPath("$.rucDueAtKm").value(DEFAULT_RUC_DUE_AT_KM))
            .andExpect(jsonPath("$.hubboReading").value(DEFAULT_HUBBO_READING))
            .andExpect(jsonPath("$.loadCapacity").value(DEFAULT_LOAD_CAPACITY))
            .andExpect(jsonPath("$.hourlyRate").value(DEFAULT_HOURLY_RATE.doubleValue()))
            .andExpect(jsonPath("$.registrationDueDate").value(DEFAULT_REGISTRATION_DUE_DATE.toString()))
            .andExpect(jsonPath("$.hireStatus").value(DEFAULT_HIRE_STATUS.toString()))
            .andExpect(jsonPath("$.gpsDeviceSerial").value(DEFAULT_GPS_DEVICE_SERIAL.toString()));
    }

    @Test
    @Transactional
    public void getAllPlantsByFleetIdIsEqualToSomething() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        // Get all the plantList where fleetId equals to DEFAULT_FLEET_ID
        defaultPlantShouldBeFound("fleetId.equals=" + DEFAULT_FLEET_ID);

        // Get all the plantList where fleetId equals to UPDATED_FLEET_ID
        defaultPlantShouldNotBeFound("fleetId.equals=" + UPDATED_FLEET_ID);
    }

    @Test
    @Transactional
    public void getAllPlantsByFleetIdIsInShouldWork() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        // Get all the plantList where fleetId in DEFAULT_FLEET_ID or UPDATED_FLEET_ID
        defaultPlantShouldBeFound("fleetId.in=" + DEFAULT_FLEET_ID + "," + UPDATED_FLEET_ID);

        // Get all the plantList where fleetId equals to UPDATED_FLEET_ID
        defaultPlantShouldNotBeFound("fleetId.in=" + UPDATED_FLEET_ID);
    }

    @Test
    @Transactional
    public void getAllPlantsByFleetIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        // Get all the plantList where fleetId is not null
        defaultPlantShouldBeFound("fleetId.specified=true");

        // Get all the plantList where fleetId is null
        defaultPlantShouldNotBeFound("fleetId.specified=false");
    }

    @Test
    @Transactional
    public void getAllPlantsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        // Get all the plantList where name equals to DEFAULT_NAME
        defaultPlantShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the plantList where name equals to UPDATED_NAME
        defaultPlantShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPlantsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        // Get all the plantList where name in DEFAULT_NAME or UPDATED_NAME
        defaultPlantShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the plantList where name equals to UPDATED_NAME
        defaultPlantShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPlantsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        // Get all the plantList where name is not null
        defaultPlantShouldBeFound("name.specified=true");

        // Get all the plantList where name is null
        defaultPlantShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllPlantsByNotesIsEqualToSomething() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        // Get all the plantList where notes equals to DEFAULT_NOTES
        defaultPlantShouldBeFound("notes.equals=" + DEFAULT_NOTES);

        // Get all the plantList where notes equals to UPDATED_NOTES
        defaultPlantShouldNotBeFound("notes.equals=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    public void getAllPlantsByNotesIsInShouldWork() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        // Get all the plantList where notes in DEFAULT_NOTES or UPDATED_NOTES
        defaultPlantShouldBeFound("notes.in=" + DEFAULT_NOTES + "," + UPDATED_NOTES);

        // Get all the plantList where notes equals to UPDATED_NOTES
        defaultPlantShouldNotBeFound("notes.in=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    public void getAllPlantsByNotesIsNullOrNotNull() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        // Get all the plantList where notes is not null
        defaultPlantShouldBeFound("notes.specified=true");

        // Get all the plantList where notes is null
        defaultPlantShouldNotBeFound("notes.specified=false");
    }

    @Test
    @Transactional
    public void getAllPlantsByPurchaseDateIsEqualToSomething() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        // Get all the plantList where purchaseDate equals to DEFAULT_PURCHASE_DATE
        defaultPlantShouldBeFound("purchaseDate.equals=" + DEFAULT_PURCHASE_DATE);

        // Get all the plantList where purchaseDate equals to UPDATED_PURCHASE_DATE
        defaultPlantShouldNotBeFound("purchaseDate.equals=" + UPDATED_PURCHASE_DATE);
    }

    @Test
    @Transactional
    public void getAllPlantsByPurchaseDateIsInShouldWork() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        // Get all the plantList where purchaseDate in DEFAULT_PURCHASE_DATE or UPDATED_PURCHASE_DATE
        defaultPlantShouldBeFound("purchaseDate.in=" + DEFAULT_PURCHASE_DATE + "," + UPDATED_PURCHASE_DATE);

        // Get all the plantList where purchaseDate equals to UPDATED_PURCHASE_DATE
        defaultPlantShouldNotBeFound("purchaseDate.in=" + UPDATED_PURCHASE_DATE);
    }

    @Test
    @Transactional
    public void getAllPlantsByPurchaseDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        // Get all the plantList where purchaseDate is not null
        defaultPlantShouldBeFound("purchaseDate.specified=true");

        // Get all the plantList where purchaseDate is null
        defaultPlantShouldNotBeFound("purchaseDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllPlantsByIsActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        // Get all the plantList where isActive equals to DEFAULT_IS_ACTIVE
        defaultPlantShouldBeFound("isActive.equals=" + DEFAULT_IS_ACTIVE);

        // Get all the plantList where isActive equals to UPDATED_IS_ACTIVE
        defaultPlantShouldNotBeFound("isActive.equals=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllPlantsByIsActiveIsInShouldWork() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        // Get all the plantList where isActive in DEFAULT_IS_ACTIVE or UPDATED_IS_ACTIVE
        defaultPlantShouldBeFound("isActive.in=" + DEFAULT_IS_ACTIVE + "," + UPDATED_IS_ACTIVE);

        // Get all the plantList where isActive equals to UPDATED_IS_ACTIVE
        defaultPlantShouldNotBeFound("isActive.in=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllPlantsByIsActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        // Get all the plantList where isActive is not null
        defaultPlantShouldBeFound("isActive.specified=true");

        // Get all the plantList where isActive is null
        defaultPlantShouldNotBeFound("isActive.specified=false");
    }

    @Test
    @Transactional
    public void getAllPlantsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        // Get all the plantList where description equals to DEFAULT_DESCRIPTION
        defaultPlantShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the plantList where description equals to UPDATED_DESCRIPTION
        defaultPlantShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllPlantsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        // Get all the plantList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultPlantShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the plantList where description equals to UPDATED_DESCRIPTION
        defaultPlantShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllPlantsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        // Get all the plantList where description is not null
        defaultPlantShouldBeFound("description.specified=true");

        // Get all the plantList where description is null
        defaultPlantShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    public void getAllPlantsByVinIsEqualToSomething() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        // Get all the plantList where vin equals to DEFAULT_VIN
        defaultPlantShouldBeFound("vin.equals=" + DEFAULT_VIN);

        // Get all the plantList where vin equals to UPDATED_VIN
        defaultPlantShouldNotBeFound("vin.equals=" + UPDATED_VIN);
    }

    @Test
    @Transactional
    public void getAllPlantsByVinIsInShouldWork() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        // Get all the plantList where vin in DEFAULT_VIN or UPDATED_VIN
        defaultPlantShouldBeFound("vin.in=" + DEFAULT_VIN + "," + UPDATED_VIN);

        // Get all the plantList where vin equals to UPDATED_VIN
        defaultPlantShouldNotBeFound("vin.in=" + UPDATED_VIN);
    }

    @Test
    @Transactional
    public void getAllPlantsByVinIsNullOrNotNull() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        // Get all the plantList where vin is not null
        defaultPlantShouldBeFound("vin.specified=true");

        // Get all the plantList where vin is null
        defaultPlantShouldNotBeFound("vin.specified=false");
    }

    @Test
    @Transactional
    public void getAllPlantsByRegoIsEqualToSomething() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        // Get all the plantList where rego equals to DEFAULT_REGO
        defaultPlantShouldBeFound("rego.equals=" + DEFAULT_REGO);

        // Get all the plantList where rego equals to UPDATED_REGO
        defaultPlantShouldNotBeFound("rego.equals=" + UPDATED_REGO);
    }

    @Test
    @Transactional
    public void getAllPlantsByRegoIsInShouldWork() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        // Get all the plantList where rego in DEFAULT_REGO or UPDATED_REGO
        defaultPlantShouldBeFound("rego.in=" + DEFAULT_REGO + "," + UPDATED_REGO);

        // Get all the plantList where rego equals to UPDATED_REGO
        defaultPlantShouldNotBeFound("rego.in=" + UPDATED_REGO);
    }

    @Test
    @Transactional
    public void getAllPlantsByRegoIsNullOrNotNull() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        // Get all the plantList where rego is not null
        defaultPlantShouldBeFound("rego.specified=true");

        // Get all the plantList where rego is null
        defaultPlantShouldNotBeFound("rego.specified=false");
    }

    @Test
    @Transactional
    public void getAllPlantsByDateOfManufactureIsEqualToSomething() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        // Get all the plantList where dateOfManufacture equals to DEFAULT_DATE_OF_MANUFACTURE
        defaultPlantShouldBeFound("dateOfManufacture.equals=" + DEFAULT_DATE_OF_MANUFACTURE);

        // Get all the plantList where dateOfManufacture equals to UPDATED_DATE_OF_MANUFACTURE
        defaultPlantShouldNotBeFound("dateOfManufacture.equals=" + UPDATED_DATE_OF_MANUFACTURE);
    }

    @Test
    @Transactional
    public void getAllPlantsByDateOfManufactureIsInShouldWork() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        // Get all the plantList where dateOfManufacture in DEFAULT_DATE_OF_MANUFACTURE or UPDATED_DATE_OF_MANUFACTURE
        defaultPlantShouldBeFound("dateOfManufacture.in=" + DEFAULT_DATE_OF_MANUFACTURE + "," + UPDATED_DATE_OF_MANUFACTURE);

        // Get all the plantList where dateOfManufacture equals to UPDATED_DATE_OF_MANUFACTURE
        defaultPlantShouldNotBeFound("dateOfManufacture.in=" + UPDATED_DATE_OF_MANUFACTURE);
    }

    @Test
    @Transactional
    public void getAllPlantsByDateOfManufactureIsNullOrNotNull() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        // Get all the plantList where dateOfManufacture is not null
        defaultPlantShouldBeFound("dateOfManufacture.specified=true");

        // Get all the plantList where dateOfManufacture is null
        defaultPlantShouldNotBeFound("dateOfManufacture.specified=false");
    }

    @Test
    @Transactional
    public void getAllPlantsByTankSizeIsEqualToSomething() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        // Get all the plantList where tankSize equals to DEFAULT_TANK_SIZE
        defaultPlantShouldBeFound("tankSize.equals=" + DEFAULT_TANK_SIZE);

        // Get all the plantList where tankSize equals to UPDATED_TANK_SIZE
        defaultPlantShouldNotBeFound("tankSize.equals=" + UPDATED_TANK_SIZE);
    }

    @Test
    @Transactional
    public void getAllPlantsByTankSizeIsInShouldWork() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        // Get all the plantList where tankSize in DEFAULT_TANK_SIZE or UPDATED_TANK_SIZE
        defaultPlantShouldBeFound("tankSize.in=" + DEFAULT_TANK_SIZE + "," + UPDATED_TANK_SIZE);

        // Get all the plantList where tankSize equals to UPDATED_TANK_SIZE
        defaultPlantShouldNotBeFound("tankSize.in=" + UPDATED_TANK_SIZE);
    }

    @Test
    @Transactional
    public void getAllPlantsByTankSizeIsNullOrNotNull() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        // Get all the plantList where tankSize is not null
        defaultPlantShouldBeFound("tankSize.specified=true");

        // Get all the plantList where tankSize is null
        defaultPlantShouldNotBeFound("tankSize.specified=false");
    }

    @Test
    @Transactional
    public void getAllPlantsByTankSizeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        // Get all the plantList where tankSize greater than or equals to DEFAULT_TANK_SIZE
        defaultPlantShouldBeFound("tankSize.greaterOrEqualThan=" + DEFAULT_TANK_SIZE);

        // Get all the plantList where tankSize greater than or equals to UPDATED_TANK_SIZE
        defaultPlantShouldNotBeFound("tankSize.greaterOrEqualThan=" + UPDATED_TANK_SIZE);
    }

    @Test
    @Transactional
    public void getAllPlantsByTankSizeIsLessThanSomething() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        // Get all the plantList where tankSize less than or equals to DEFAULT_TANK_SIZE
        defaultPlantShouldNotBeFound("tankSize.lessThan=" + DEFAULT_TANK_SIZE);

        // Get all the plantList where tankSize less than or equals to UPDATED_TANK_SIZE
        defaultPlantShouldBeFound("tankSize.lessThan=" + UPDATED_TANK_SIZE);
    }


    @Test
    @Transactional
    public void getAllPlantsByMeterReadingIsEqualToSomething() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        // Get all the plantList where meterReading equals to DEFAULT_METER_READING
        defaultPlantShouldBeFound("meterReading.equals=" + DEFAULT_METER_READING);

        // Get all the plantList where meterReading equals to UPDATED_METER_READING
        defaultPlantShouldNotBeFound("meterReading.equals=" + UPDATED_METER_READING);
    }

    @Test
    @Transactional
    public void getAllPlantsByMeterReadingIsInShouldWork() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        // Get all the plantList where meterReading in DEFAULT_METER_READING or UPDATED_METER_READING
        defaultPlantShouldBeFound("meterReading.in=" + DEFAULT_METER_READING + "," + UPDATED_METER_READING);

        // Get all the plantList where meterReading equals to UPDATED_METER_READING
        defaultPlantShouldNotBeFound("meterReading.in=" + UPDATED_METER_READING);
    }

    @Test
    @Transactional
    public void getAllPlantsByMeterReadingIsNullOrNotNull() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        // Get all the plantList where meterReading is not null
        defaultPlantShouldBeFound("meterReading.specified=true");

        // Get all the plantList where meterReading is null
        defaultPlantShouldNotBeFound("meterReading.specified=false");
    }

    @Test
    @Transactional
    public void getAllPlantsByMeterReadingIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        // Get all the plantList where meterReading greater than or equals to DEFAULT_METER_READING
        defaultPlantShouldBeFound("meterReading.greaterOrEqualThan=" + DEFAULT_METER_READING);

        // Get all the plantList where meterReading greater than or equals to UPDATED_METER_READING
        defaultPlantShouldNotBeFound("meterReading.greaterOrEqualThan=" + UPDATED_METER_READING);
    }

    @Test
    @Transactional
    public void getAllPlantsByMeterReadingIsLessThanSomething() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        // Get all the plantList where meterReading less than or equals to DEFAULT_METER_READING
        defaultPlantShouldNotBeFound("meterReading.lessThan=" + DEFAULT_METER_READING);

        // Get all the plantList where meterReading less than or equals to UPDATED_METER_READING
        defaultPlantShouldBeFound("meterReading.lessThan=" + UPDATED_METER_READING);
    }


    @Test
    @Transactional
    public void getAllPlantsByMaintenanceDueAtIsEqualToSomething() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        // Get all the plantList where maintenanceDueAt equals to DEFAULT_MAINTENANCE_DUE_AT
        defaultPlantShouldBeFound("maintenanceDueAt.equals=" + DEFAULT_MAINTENANCE_DUE_AT);

        // Get all the plantList where maintenanceDueAt equals to UPDATED_MAINTENANCE_DUE_AT
        defaultPlantShouldNotBeFound("maintenanceDueAt.equals=" + UPDATED_MAINTENANCE_DUE_AT);
    }

    @Test
    @Transactional
    public void getAllPlantsByMaintenanceDueAtIsInShouldWork() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        // Get all the plantList where maintenanceDueAt in DEFAULT_MAINTENANCE_DUE_AT or UPDATED_MAINTENANCE_DUE_AT
        defaultPlantShouldBeFound("maintenanceDueAt.in=" + DEFAULT_MAINTENANCE_DUE_AT + "," + UPDATED_MAINTENANCE_DUE_AT);

        // Get all the plantList where maintenanceDueAt equals to UPDATED_MAINTENANCE_DUE_AT
        defaultPlantShouldNotBeFound("maintenanceDueAt.in=" + UPDATED_MAINTENANCE_DUE_AT);
    }

    @Test
    @Transactional
    public void getAllPlantsByMaintenanceDueAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        // Get all the plantList where maintenanceDueAt is not null
        defaultPlantShouldBeFound("maintenanceDueAt.specified=true");

        // Get all the plantList where maintenanceDueAt is null
        defaultPlantShouldNotBeFound("maintenanceDueAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllPlantsByMaintenanceDueAtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        // Get all the plantList where maintenanceDueAt greater than or equals to DEFAULT_MAINTENANCE_DUE_AT
        defaultPlantShouldBeFound("maintenanceDueAt.greaterOrEqualThan=" + DEFAULT_MAINTENANCE_DUE_AT);

        // Get all the plantList where maintenanceDueAt greater than or equals to UPDATED_MAINTENANCE_DUE_AT
        defaultPlantShouldNotBeFound("maintenanceDueAt.greaterOrEqualThan=" + UPDATED_MAINTENANCE_DUE_AT);
    }

    @Test
    @Transactional
    public void getAllPlantsByMaintenanceDueAtIsLessThanSomething() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        // Get all the plantList where maintenanceDueAt less than or equals to DEFAULT_MAINTENANCE_DUE_AT
        defaultPlantShouldNotBeFound("maintenanceDueAt.lessThan=" + DEFAULT_MAINTENANCE_DUE_AT);

        // Get all the plantList where maintenanceDueAt less than or equals to UPDATED_MAINTENANCE_DUE_AT
        defaultPlantShouldBeFound("maintenanceDueAt.lessThan=" + UPDATED_MAINTENANCE_DUE_AT);
    }


    @Test
    @Transactional
    public void getAllPlantsByMeterUnitIsEqualToSomething() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        // Get all the plantList where meterUnit equals to DEFAULT_METER_UNIT
        defaultPlantShouldBeFound("meterUnit.equals=" + DEFAULT_METER_UNIT);

        // Get all the plantList where meterUnit equals to UPDATED_METER_UNIT
        defaultPlantShouldNotBeFound("meterUnit.equals=" + UPDATED_METER_UNIT);
    }

    @Test
    @Transactional
    public void getAllPlantsByMeterUnitIsInShouldWork() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        // Get all the plantList where meterUnit in DEFAULT_METER_UNIT or UPDATED_METER_UNIT
        defaultPlantShouldBeFound("meterUnit.in=" + DEFAULT_METER_UNIT + "," + UPDATED_METER_UNIT);

        // Get all the plantList where meterUnit equals to UPDATED_METER_UNIT
        defaultPlantShouldNotBeFound("meterUnit.in=" + UPDATED_METER_UNIT);
    }

    @Test
    @Transactional
    public void getAllPlantsByMeterUnitIsNullOrNotNull() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        // Get all the plantList where meterUnit is not null
        defaultPlantShouldBeFound("meterUnit.specified=true");

        // Get all the plantList where meterUnit is null
        defaultPlantShouldNotBeFound("meterUnit.specified=false");
    }

    @Test
    @Transactional
    public void getAllPlantsByCertificateDueDateIsEqualToSomething() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        // Get all the plantList where certificateDueDate equals to DEFAULT_CERTIFICATE_DUE_DATE
        defaultPlantShouldBeFound("certificateDueDate.equals=" + DEFAULT_CERTIFICATE_DUE_DATE);

        // Get all the plantList where certificateDueDate equals to UPDATED_CERTIFICATE_DUE_DATE
        defaultPlantShouldNotBeFound("certificateDueDate.equals=" + UPDATED_CERTIFICATE_DUE_DATE);
    }

    @Test
    @Transactional
    public void getAllPlantsByCertificateDueDateIsInShouldWork() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        // Get all the plantList where certificateDueDate in DEFAULT_CERTIFICATE_DUE_DATE or UPDATED_CERTIFICATE_DUE_DATE
        defaultPlantShouldBeFound("certificateDueDate.in=" + DEFAULT_CERTIFICATE_DUE_DATE + "," + UPDATED_CERTIFICATE_DUE_DATE);

        // Get all the plantList where certificateDueDate equals to UPDATED_CERTIFICATE_DUE_DATE
        defaultPlantShouldNotBeFound("certificateDueDate.in=" + UPDATED_CERTIFICATE_DUE_DATE);
    }

    @Test
    @Transactional
    public void getAllPlantsByCertificateDueDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        // Get all the plantList where certificateDueDate is not null
        defaultPlantShouldBeFound("certificateDueDate.specified=true");

        // Get all the plantList where certificateDueDate is null
        defaultPlantShouldNotBeFound("certificateDueDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllPlantsByRucDueAtKmIsEqualToSomething() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        // Get all the plantList where rucDueAtKm equals to DEFAULT_RUC_DUE_AT_KM
        defaultPlantShouldBeFound("rucDueAtKm.equals=" + DEFAULT_RUC_DUE_AT_KM);

        // Get all the plantList where rucDueAtKm equals to UPDATED_RUC_DUE_AT_KM
        defaultPlantShouldNotBeFound("rucDueAtKm.equals=" + UPDATED_RUC_DUE_AT_KM);
    }

    @Test
    @Transactional
    public void getAllPlantsByRucDueAtKmIsInShouldWork() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        // Get all the plantList where rucDueAtKm in DEFAULT_RUC_DUE_AT_KM or UPDATED_RUC_DUE_AT_KM
        defaultPlantShouldBeFound("rucDueAtKm.in=" + DEFAULT_RUC_DUE_AT_KM + "," + UPDATED_RUC_DUE_AT_KM);

        // Get all the plantList where rucDueAtKm equals to UPDATED_RUC_DUE_AT_KM
        defaultPlantShouldNotBeFound("rucDueAtKm.in=" + UPDATED_RUC_DUE_AT_KM);
    }

    @Test
    @Transactional
    public void getAllPlantsByRucDueAtKmIsNullOrNotNull() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        // Get all the plantList where rucDueAtKm is not null
        defaultPlantShouldBeFound("rucDueAtKm.specified=true");

        // Get all the plantList where rucDueAtKm is null
        defaultPlantShouldNotBeFound("rucDueAtKm.specified=false");
    }

    @Test
    @Transactional
    public void getAllPlantsByRucDueAtKmIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        // Get all the plantList where rucDueAtKm greater than or equals to DEFAULT_RUC_DUE_AT_KM
        defaultPlantShouldBeFound("rucDueAtKm.greaterOrEqualThan=" + DEFAULT_RUC_DUE_AT_KM);

        // Get all the plantList where rucDueAtKm greater than or equals to UPDATED_RUC_DUE_AT_KM
        defaultPlantShouldNotBeFound("rucDueAtKm.greaterOrEqualThan=" + UPDATED_RUC_DUE_AT_KM);
    }

    @Test
    @Transactional
    public void getAllPlantsByRucDueAtKmIsLessThanSomething() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        // Get all the plantList where rucDueAtKm less than or equals to DEFAULT_RUC_DUE_AT_KM
        defaultPlantShouldNotBeFound("rucDueAtKm.lessThan=" + DEFAULT_RUC_DUE_AT_KM);

        // Get all the plantList where rucDueAtKm less than or equals to UPDATED_RUC_DUE_AT_KM
        defaultPlantShouldBeFound("rucDueAtKm.lessThan=" + UPDATED_RUC_DUE_AT_KM);
    }


    @Test
    @Transactional
    public void getAllPlantsByHubboReadingIsEqualToSomething() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        // Get all the plantList where hubboReading equals to DEFAULT_HUBBO_READING
        defaultPlantShouldBeFound("hubboReading.equals=" + DEFAULT_HUBBO_READING);

        // Get all the plantList where hubboReading equals to UPDATED_HUBBO_READING
        defaultPlantShouldNotBeFound("hubboReading.equals=" + UPDATED_HUBBO_READING);
    }

    @Test
    @Transactional
    public void getAllPlantsByHubboReadingIsInShouldWork() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        // Get all the plantList where hubboReading in DEFAULT_HUBBO_READING or UPDATED_HUBBO_READING
        defaultPlantShouldBeFound("hubboReading.in=" + DEFAULT_HUBBO_READING + "," + UPDATED_HUBBO_READING);

        // Get all the plantList where hubboReading equals to UPDATED_HUBBO_READING
        defaultPlantShouldNotBeFound("hubboReading.in=" + UPDATED_HUBBO_READING);
    }

    @Test
    @Transactional
    public void getAllPlantsByHubboReadingIsNullOrNotNull() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        // Get all the plantList where hubboReading is not null
        defaultPlantShouldBeFound("hubboReading.specified=true");

        // Get all the plantList where hubboReading is null
        defaultPlantShouldNotBeFound("hubboReading.specified=false");
    }

    @Test
    @Transactional
    public void getAllPlantsByHubboReadingIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        // Get all the plantList where hubboReading greater than or equals to DEFAULT_HUBBO_READING
        defaultPlantShouldBeFound("hubboReading.greaterOrEqualThan=" + DEFAULT_HUBBO_READING);

        // Get all the plantList where hubboReading greater than or equals to UPDATED_HUBBO_READING
        defaultPlantShouldNotBeFound("hubboReading.greaterOrEqualThan=" + UPDATED_HUBBO_READING);
    }

    @Test
    @Transactional
    public void getAllPlantsByHubboReadingIsLessThanSomething() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        // Get all the plantList where hubboReading less than or equals to DEFAULT_HUBBO_READING
        defaultPlantShouldNotBeFound("hubboReading.lessThan=" + DEFAULT_HUBBO_READING);

        // Get all the plantList where hubboReading less than or equals to UPDATED_HUBBO_READING
        defaultPlantShouldBeFound("hubboReading.lessThan=" + UPDATED_HUBBO_READING);
    }


    @Test
    @Transactional
    public void getAllPlantsByLoadCapacityIsEqualToSomething() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        // Get all the plantList where loadCapacity equals to DEFAULT_LOAD_CAPACITY
        defaultPlantShouldBeFound("loadCapacity.equals=" + DEFAULT_LOAD_CAPACITY);

        // Get all the plantList where loadCapacity equals to UPDATED_LOAD_CAPACITY
        defaultPlantShouldNotBeFound("loadCapacity.equals=" + UPDATED_LOAD_CAPACITY);
    }

    @Test
    @Transactional
    public void getAllPlantsByLoadCapacityIsInShouldWork() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        // Get all the plantList where loadCapacity in DEFAULT_LOAD_CAPACITY or UPDATED_LOAD_CAPACITY
        defaultPlantShouldBeFound("loadCapacity.in=" + DEFAULT_LOAD_CAPACITY + "," + UPDATED_LOAD_CAPACITY);

        // Get all the plantList where loadCapacity equals to UPDATED_LOAD_CAPACITY
        defaultPlantShouldNotBeFound("loadCapacity.in=" + UPDATED_LOAD_CAPACITY);
    }

    @Test
    @Transactional
    public void getAllPlantsByLoadCapacityIsNullOrNotNull() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        // Get all the plantList where loadCapacity is not null
        defaultPlantShouldBeFound("loadCapacity.specified=true");

        // Get all the plantList where loadCapacity is null
        defaultPlantShouldNotBeFound("loadCapacity.specified=false");
    }

    @Test
    @Transactional
    public void getAllPlantsByLoadCapacityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        // Get all the plantList where loadCapacity greater than or equals to DEFAULT_LOAD_CAPACITY
        defaultPlantShouldBeFound("loadCapacity.greaterOrEqualThan=" + DEFAULT_LOAD_CAPACITY);

        // Get all the plantList where loadCapacity greater than or equals to UPDATED_LOAD_CAPACITY
        defaultPlantShouldNotBeFound("loadCapacity.greaterOrEqualThan=" + UPDATED_LOAD_CAPACITY);
    }

    @Test
    @Transactional
    public void getAllPlantsByLoadCapacityIsLessThanSomething() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        // Get all the plantList where loadCapacity less than or equals to DEFAULT_LOAD_CAPACITY
        defaultPlantShouldNotBeFound("loadCapacity.lessThan=" + DEFAULT_LOAD_CAPACITY);

        // Get all the plantList where loadCapacity less than or equals to UPDATED_LOAD_CAPACITY
        defaultPlantShouldBeFound("loadCapacity.lessThan=" + UPDATED_LOAD_CAPACITY);
    }


    @Test
    @Transactional
    public void getAllPlantsByHourlyRateIsEqualToSomething() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        // Get all the plantList where hourlyRate equals to DEFAULT_HOURLY_RATE
        defaultPlantShouldBeFound("hourlyRate.equals=" + DEFAULT_HOURLY_RATE);

        // Get all the plantList where hourlyRate equals to UPDATED_HOURLY_RATE
        defaultPlantShouldNotBeFound("hourlyRate.equals=" + UPDATED_HOURLY_RATE);
    }

    @Test
    @Transactional
    public void getAllPlantsByHourlyRateIsInShouldWork() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        // Get all the plantList where hourlyRate in DEFAULT_HOURLY_RATE or UPDATED_HOURLY_RATE
        defaultPlantShouldBeFound("hourlyRate.in=" + DEFAULT_HOURLY_RATE + "," + UPDATED_HOURLY_RATE);

        // Get all the plantList where hourlyRate equals to UPDATED_HOURLY_RATE
        defaultPlantShouldNotBeFound("hourlyRate.in=" + UPDATED_HOURLY_RATE);
    }

    @Test
    @Transactional
    public void getAllPlantsByHourlyRateIsNullOrNotNull() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        // Get all the plantList where hourlyRate is not null
        defaultPlantShouldBeFound("hourlyRate.specified=true");

        // Get all the plantList where hourlyRate is null
        defaultPlantShouldNotBeFound("hourlyRate.specified=false");
    }

    @Test
    @Transactional
    public void getAllPlantsByRegistrationDueDateIsEqualToSomething() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        // Get all the plantList where registrationDueDate equals to DEFAULT_REGISTRATION_DUE_DATE
        defaultPlantShouldBeFound("registrationDueDate.equals=" + DEFAULT_REGISTRATION_DUE_DATE);

        // Get all the plantList where registrationDueDate equals to UPDATED_REGISTRATION_DUE_DATE
        defaultPlantShouldNotBeFound("registrationDueDate.equals=" + UPDATED_REGISTRATION_DUE_DATE);
    }

    @Test
    @Transactional
    public void getAllPlantsByRegistrationDueDateIsInShouldWork() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        // Get all the plantList where registrationDueDate in DEFAULT_REGISTRATION_DUE_DATE or UPDATED_REGISTRATION_DUE_DATE
        defaultPlantShouldBeFound("registrationDueDate.in=" + DEFAULT_REGISTRATION_DUE_DATE + "," + UPDATED_REGISTRATION_DUE_DATE);

        // Get all the plantList where registrationDueDate equals to UPDATED_REGISTRATION_DUE_DATE
        defaultPlantShouldNotBeFound("registrationDueDate.in=" + UPDATED_REGISTRATION_DUE_DATE);
    }

    @Test
    @Transactional
    public void getAllPlantsByRegistrationDueDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        // Get all the plantList where registrationDueDate is not null
        defaultPlantShouldBeFound("registrationDueDate.specified=true");

        // Get all the plantList where registrationDueDate is null
        defaultPlantShouldNotBeFound("registrationDueDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllPlantsByHireStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        // Get all the plantList where hireStatus equals to DEFAULT_HIRE_STATUS
        defaultPlantShouldBeFound("hireStatus.equals=" + DEFAULT_HIRE_STATUS);

        // Get all the plantList where hireStatus equals to UPDATED_HIRE_STATUS
        defaultPlantShouldNotBeFound("hireStatus.equals=" + UPDATED_HIRE_STATUS);
    }

    @Test
    @Transactional
    public void getAllPlantsByHireStatusIsInShouldWork() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        // Get all the plantList where hireStatus in DEFAULT_HIRE_STATUS or UPDATED_HIRE_STATUS
        defaultPlantShouldBeFound("hireStatus.in=" + DEFAULT_HIRE_STATUS + "," + UPDATED_HIRE_STATUS);

        // Get all the plantList where hireStatus equals to UPDATED_HIRE_STATUS
        defaultPlantShouldNotBeFound("hireStatus.in=" + UPDATED_HIRE_STATUS);
    }

    @Test
    @Transactional
    public void getAllPlantsByHireStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        // Get all the plantList where hireStatus is not null
        defaultPlantShouldBeFound("hireStatus.specified=true");

        // Get all the plantList where hireStatus is null
        defaultPlantShouldNotBeFound("hireStatus.specified=false");
    }

    @Test
    @Transactional
    public void getAllPlantsByGpsDeviceSerialIsEqualToSomething() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        // Get all the plantList where gpsDeviceSerial equals to DEFAULT_GPS_DEVICE_SERIAL
        defaultPlantShouldBeFound("gpsDeviceSerial.equals=" + DEFAULT_GPS_DEVICE_SERIAL);

        // Get all the plantList where gpsDeviceSerial equals to UPDATED_GPS_DEVICE_SERIAL
        defaultPlantShouldNotBeFound("gpsDeviceSerial.equals=" + UPDATED_GPS_DEVICE_SERIAL);
    }

    @Test
    @Transactional
    public void getAllPlantsByGpsDeviceSerialIsInShouldWork() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        // Get all the plantList where gpsDeviceSerial in DEFAULT_GPS_DEVICE_SERIAL or UPDATED_GPS_DEVICE_SERIAL
        defaultPlantShouldBeFound("gpsDeviceSerial.in=" + DEFAULT_GPS_DEVICE_SERIAL + "," + UPDATED_GPS_DEVICE_SERIAL);

        // Get all the plantList where gpsDeviceSerial equals to UPDATED_GPS_DEVICE_SERIAL
        defaultPlantShouldNotBeFound("gpsDeviceSerial.in=" + UPDATED_GPS_DEVICE_SERIAL);
    }

    @Test
    @Transactional
    public void getAllPlantsByGpsDeviceSerialIsNullOrNotNull() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        // Get all the plantList where gpsDeviceSerial is not null
        defaultPlantShouldBeFound("gpsDeviceSerial.specified=true");

        // Get all the plantList where gpsDeviceSerial is null
        defaultPlantShouldNotBeFound("gpsDeviceSerial.specified=false");
    }

    @Test
    @Transactional
    public void getAllPlantsByLocationIsEqualToSomething() throws Exception {
        // Initialize the database
        Location location = LocationResourceIntTest.createEntity(em);
        em.persist(location);
        em.flush();
        plant.setLocation(location);
        plantRepository.saveAndFlush(plant);
        Long locationId = location.getId();

        // Get all the plantList where location equals to locationId
        defaultPlantShouldBeFound("locationId.equals=" + locationId);

        // Get all the plantList where location equals to locationId + 1
        defaultPlantShouldNotBeFound("locationId.equals=" + (locationId + 1));
    }


    @Test
    @Transactional
    public void getAllPlantsByLastLogIsEqualToSomething() throws Exception {
        // Initialize the database
        PlantLog lastLog = PlantLogResourceIntTest.createEntity(em);
        em.persist(lastLog);
        em.flush();
        plant.setLastLog(lastLog);
        plantRepository.saveAndFlush(plant);
        Long lastLogId = lastLog.getId();

        // Get all the plantList where lastLog equals to lastLogId
        defaultPlantShouldBeFound("lastLogId.equals=" + lastLogId);

        // Get all the plantList where lastLog equals to lastLogId + 1
        defaultPlantShouldNotBeFound("lastLogId.equals=" + (lastLogId + 1));
    }


    @Test
    @Transactional
    public void getAllPlantsByCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        Category category = CategoryResourceIntTest.createEntity(em);
        em.persist(category);
        em.flush();
        plant.setCategory(category);
        plantRepository.saveAndFlush(plant);
        Long categoryId = category.getId();

        // Get all the plantList where category equals to categoryId
        defaultPlantShouldBeFound("categoryId.equals=" + categoryId);

        // Get all the plantList where category equals to categoryId + 1
        defaultPlantShouldNotBeFound("categoryId.equals=" + (categoryId + 1));
    }


    @Test
    @Transactional
    public void getAllPlantsByOwnerIsEqualToSomething() throws Exception {
        // Initialize the database
        Company owner = CompanyResourceIntTest.createEntity(em);
        em.persist(owner);
        em.flush();
        plant.setOwner(owner);
        plantRepository.saveAndFlush(plant);
        Long ownerId = owner.getId();

        // Get all the plantList where owner equals to ownerId
        defaultPlantShouldBeFound("ownerId.equals=" + ownerId);

        // Get all the plantList where owner equals to ownerId + 1
        defaultPlantShouldNotBeFound("ownerId.equals=" + (ownerId + 1));
    }


    @Test
    @Transactional
    public void getAllPlantsByAssignedContractorIsEqualToSomething() throws Exception {
        // Initialize the database
        MaintenanceContractor assignedContractor = MaintenanceContractorResourceIntTest.createEntity(em);
        em.persist(assignedContractor);
        em.flush();
        plant.setAssignedContractor(assignedContractor);
        plantRepository.saveAndFlush(plant);
        Long assignedContractorId = assignedContractor.getId();

        // Get all the plantList where assignedContractor equals to assignedContractorId
        defaultPlantShouldBeFound("assignedContractorId.equals=" + assignedContractorId);

        // Get all the plantList where assignedContractor equals to assignedContractorId + 1
        defaultPlantShouldNotBeFound("assignedContractorId.equals=" + (assignedContractorId + 1));
    }


    @Test
    @Transactional
    public void getAllPlantsByProjectIsEqualToSomething() throws Exception {
        // Initialize the database
        Project project = ProjectResourceIntTest.createEntity(em);
        em.persist(project);
        em.flush();
        plant.setProject(project);
        plantRepository.saveAndFlush(plant);
        Long projectId = project.getId();

        // Get all the plantList where project equals to projectId
        defaultPlantShouldBeFound("projectId.equals=" + projectId);

        // Get all the plantList where project equals to projectId + 1
        defaultPlantShouldNotBeFound("projectId.equals=" + (projectId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultPlantShouldBeFound(String filter) throws Exception {
        restPlantMockMvc.perform(get("/api/plants?sort=id,desc&" + filter))
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
            .andExpect(jsonPath("$.[*].meterReading").value(hasItem(DEFAULT_METER_READING)))
            .andExpect(jsonPath("$.[*].maintenanceDueAt").value(hasItem(DEFAULT_MAINTENANCE_DUE_AT)))
            .andExpect(jsonPath("$.[*].meterUnit").value(hasItem(DEFAULT_METER_UNIT.toString())))
            .andExpect(jsonPath("$.[*].certificateDueDate").value(hasItem(DEFAULT_CERTIFICATE_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].rucDueAtKm").value(hasItem(DEFAULT_RUC_DUE_AT_KM)))
            .andExpect(jsonPath("$.[*].hubboReading").value(hasItem(DEFAULT_HUBBO_READING)))
            .andExpect(jsonPath("$.[*].loadCapacity").value(hasItem(DEFAULT_LOAD_CAPACITY)))
            .andExpect(jsonPath("$.[*].hourlyRate").value(hasItem(DEFAULT_HOURLY_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].registrationDueDate").value(hasItem(DEFAULT_REGISTRATION_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].hireStatus").value(hasItem(DEFAULT_HIRE_STATUS.toString())))
            .andExpect(jsonPath("$.[*].gpsDeviceSerial").value(hasItem(DEFAULT_GPS_DEVICE_SERIAL.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultPlantShouldNotBeFound(String filter) throws Exception {
        restPlantMockMvc.perform(get("/api/plants?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
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
        plantService.save(plant);

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
            .meterReading(UPDATED_METER_READING)
            .maintenanceDueAt(UPDATED_MAINTENANCE_DUE_AT)
            .meterUnit(UPDATED_METER_UNIT)
            .certificateDueDate(UPDATED_CERTIFICATE_DUE_DATE)
            .rucDueAtKm(UPDATED_RUC_DUE_AT_KM)
            .hubboReading(UPDATED_HUBBO_READING)
            .loadCapacity(UPDATED_LOAD_CAPACITY)
            .hourlyRate(UPDATED_HOURLY_RATE)
            .registrationDueDate(UPDATED_REGISTRATION_DUE_DATE)
            .hireStatus(UPDATED_HIRE_STATUS)
            .gpsDeviceSerial(UPDATED_GPS_DEVICE_SERIAL);

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
        assertThat(testPlant.getMeterReading()).isEqualTo(UPDATED_METER_READING);
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
        plantService.save(plant);

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
