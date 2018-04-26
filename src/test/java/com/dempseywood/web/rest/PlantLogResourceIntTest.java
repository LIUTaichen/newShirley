package com.dempseywood.web.rest;

import com.dempseywood.FleetManagementApp;

import com.dempseywood.domain.PlantLog;
import com.dempseywood.repository.PlantLogRepository;
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

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.dempseywood.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PlantLogResource REST controller.
 *
 * @see PlantLogResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FleetManagementApp.class)
public class PlantLogResourceIntTest {

    private static final Integer DEFAULT_METER_READING = 1;
    private static final Integer UPDATED_METER_READING = 2;

    private static final Integer DEFAULT_HUBBO_READING = 1;
    private static final Integer UPDATED_HUBBO_READING = 2;

    private static final Integer DEFAULT_SERVICE_DUE_AT = 1;
    private static final Integer UPDATED_SERVICE_DUE_AT = 2;

    private static final Integer DEFAULT_RUC_DUE_AT = 1;
    private static final Integer UPDATED_RUC_DUE_AT = 2;

    private static final Instant DEFAULT_WOF_DUE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_WOF_DUE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_COF_DUE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_COF_DUE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_SERVICE_DUE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SERVICE_DUE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private PlantLogRepository plantLogRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPlantLogMockMvc;

    private PlantLog plantLog;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PlantLogResource plantLogResource = new PlantLogResource(plantLogRepository);
        this.restPlantLogMockMvc = MockMvcBuilders.standaloneSetup(plantLogResource)
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
    public static PlantLog createEntity(EntityManager em) {
        PlantLog plantLog = new PlantLog()
            .meterReading(DEFAULT_METER_READING)
            .hubboReading(DEFAULT_HUBBO_READING)
            .serviceDueAt(DEFAULT_SERVICE_DUE_AT)
            .rucDueAt(DEFAULT_RUC_DUE_AT)
            .wofDueDate(DEFAULT_WOF_DUE_DATE)
            .cofDueDate(DEFAULT_COF_DUE_DATE)
            .serviceDueDate(DEFAULT_SERVICE_DUE_DATE);
        return plantLog;
    }

    @Before
    public void initTest() {
        plantLog = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlantLog() throws Exception {
        int databaseSizeBeforeCreate = plantLogRepository.findAll().size();

        // Create the PlantLog
        restPlantLogMockMvc.perform(post("/api/plant-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plantLog)))
            .andExpect(status().isCreated());

        // Validate the PlantLog in the database
        List<PlantLog> plantLogList = plantLogRepository.findAll();
        assertThat(plantLogList).hasSize(databaseSizeBeforeCreate + 1);
        PlantLog testPlantLog = plantLogList.get(plantLogList.size() - 1);
        assertThat(testPlantLog.getMeterReading()).isEqualTo(DEFAULT_METER_READING);
        assertThat(testPlantLog.getHubboReading()).isEqualTo(DEFAULT_HUBBO_READING);
        assertThat(testPlantLog.getServiceDueAt()).isEqualTo(DEFAULT_SERVICE_DUE_AT);
        assertThat(testPlantLog.getRucDueAt()).isEqualTo(DEFAULT_RUC_DUE_AT);
        assertThat(testPlantLog.getWofDueDate()).isEqualTo(DEFAULT_WOF_DUE_DATE);
        assertThat(testPlantLog.getCofDueDate()).isEqualTo(DEFAULT_COF_DUE_DATE);
        assertThat(testPlantLog.getServiceDueDate()).isEqualTo(DEFAULT_SERVICE_DUE_DATE);
    }

    @Test
    @Transactional
    public void createPlantLogWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = plantLogRepository.findAll().size();

        // Create the PlantLog with an existing ID
        plantLog.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlantLogMockMvc.perform(post("/api/plant-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plantLog)))
            .andExpect(status().isBadRequest());

        // Validate the PlantLog in the database
        List<PlantLog> plantLogList = plantLogRepository.findAll();
        assertThat(plantLogList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPlantLogs() throws Exception {
        // Initialize the database
        plantLogRepository.saveAndFlush(plantLog);

        // Get all the plantLogList
        restPlantLogMockMvc.perform(get("/api/plant-logs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(plantLog.getId().intValue())))
            .andExpect(jsonPath("$.[*].meterReading").value(hasItem(DEFAULT_METER_READING)))
            .andExpect(jsonPath("$.[*].hubboReading").value(hasItem(DEFAULT_HUBBO_READING)))
            .andExpect(jsonPath("$.[*].serviceDueAt").value(hasItem(DEFAULT_SERVICE_DUE_AT)))
            .andExpect(jsonPath("$.[*].rucDueAt").value(hasItem(DEFAULT_RUC_DUE_AT)))
            .andExpect(jsonPath("$.[*].wofDueDate").value(hasItem(DEFAULT_WOF_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].cofDueDate").value(hasItem(DEFAULT_COF_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].serviceDueDate").value(hasItem(DEFAULT_SERVICE_DUE_DATE.toString())));
    }

    @Test
    @Transactional
    public void getPlantLog() throws Exception {
        // Initialize the database
        plantLogRepository.saveAndFlush(plantLog);

        // Get the plantLog
        restPlantLogMockMvc.perform(get("/api/plant-logs/{id}", plantLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(plantLog.getId().intValue()))
            .andExpect(jsonPath("$.meterReading").value(DEFAULT_METER_READING))
            .andExpect(jsonPath("$.hubboReading").value(DEFAULT_HUBBO_READING))
            .andExpect(jsonPath("$.serviceDueAt").value(DEFAULT_SERVICE_DUE_AT))
            .andExpect(jsonPath("$.rucDueAt").value(DEFAULT_RUC_DUE_AT))
            .andExpect(jsonPath("$.wofDueDate").value(DEFAULT_WOF_DUE_DATE.toString()))
            .andExpect(jsonPath("$.cofDueDate").value(DEFAULT_COF_DUE_DATE.toString()))
            .andExpect(jsonPath("$.serviceDueDate").value(DEFAULT_SERVICE_DUE_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPlantLog() throws Exception {
        // Get the plantLog
        restPlantLogMockMvc.perform(get("/api/plant-logs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlantLog() throws Exception {
        // Initialize the database
        plantLogRepository.saveAndFlush(plantLog);
        int databaseSizeBeforeUpdate = plantLogRepository.findAll().size();

        // Update the plantLog
        PlantLog updatedPlantLog = plantLogRepository.findOne(plantLog.getId());
        // Disconnect from session so that the updates on updatedPlantLog are not directly saved in db
        em.detach(updatedPlantLog);
        updatedPlantLog
            .meterReading(UPDATED_METER_READING)
            .hubboReading(UPDATED_HUBBO_READING)
            .serviceDueAt(UPDATED_SERVICE_DUE_AT)
            .rucDueAt(UPDATED_RUC_DUE_AT)
            .wofDueDate(UPDATED_WOF_DUE_DATE)
            .cofDueDate(UPDATED_COF_DUE_DATE)
            .serviceDueDate(UPDATED_SERVICE_DUE_DATE);

        restPlantLogMockMvc.perform(put("/api/plant-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPlantLog)))
            .andExpect(status().isOk());

        // Validate the PlantLog in the database
        List<PlantLog> plantLogList = plantLogRepository.findAll();
        assertThat(plantLogList).hasSize(databaseSizeBeforeUpdate);
        PlantLog testPlantLog = plantLogList.get(plantLogList.size() - 1);
        assertThat(testPlantLog.getMeterReading()).isEqualTo(UPDATED_METER_READING);
        assertThat(testPlantLog.getHubboReading()).isEqualTo(UPDATED_HUBBO_READING);
        assertThat(testPlantLog.getServiceDueAt()).isEqualTo(UPDATED_SERVICE_DUE_AT);
        assertThat(testPlantLog.getRucDueAt()).isEqualTo(UPDATED_RUC_DUE_AT);
        assertThat(testPlantLog.getWofDueDate()).isEqualTo(UPDATED_WOF_DUE_DATE);
        assertThat(testPlantLog.getCofDueDate()).isEqualTo(UPDATED_COF_DUE_DATE);
        assertThat(testPlantLog.getServiceDueDate()).isEqualTo(UPDATED_SERVICE_DUE_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingPlantLog() throws Exception {
        int databaseSizeBeforeUpdate = plantLogRepository.findAll().size();

        // Create the PlantLog

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPlantLogMockMvc.perform(put("/api/plant-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plantLog)))
            .andExpect(status().isCreated());

        // Validate the PlantLog in the database
        List<PlantLog> plantLogList = plantLogRepository.findAll();
        assertThat(plantLogList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePlantLog() throws Exception {
        // Initialize the database
        plantLogRepository.saveAndFlush(plantLog);
        int databaseSizeBeforeDelete = plantLogRepository.findAll().size();

        // Get the plantLog
        restPlantLogMockMvc.perform(delete("/api/plant-logs/{id}", plantLog.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PlantLog> plantLogList = plantLogRepository.findAll();
        assertThat(plantLogList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlantLog.class);
        PlantLog plantLog1 = new PlantLog();
        plantLog1.setId(1L);
        PlantLog plantLog2 = new PlantLog();
        plantLog2.setId(plantLog1.getId());
        assertThat(plantLog1).isEqualTo(plantLog2);
        plantLog2.setId(2L);
        assertThat(plantLog1).isNotEqualTo(plantLog2);
        plantLog1.setId(null);
        assertThat(plantLog1).isNotEqualTo(plantLog2);
    }
}
