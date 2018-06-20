package com.dempseywood.web.rest;

import com.dempseywood.FleetManagementApp;

import com.dempseywood.domain.WeeklyNiggleSnapshot;
import com.dempseywood.repository.WeeklyNiggleSnapshotRepository;
import com.dempseywood.service.WeeklyNiggleSnapshotService;
import com.dempseywood.web.rest.errors.ExceptionTranslator;
import com.dempseywood.service.dto.WeeklyNiggleSnapshotCriteria;
import com.dempseywood.service.WeeklyNiggleSnapshotQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.dempseywood.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dempseywood.domain.enumeration.Status;
import com.dempseywood.domain.enumeration.Priority;

/**
 * Test class for the WeeklyNiggleSnapshotResource REST controller.
 *
 * @see WeeklyNiggleSnapshotResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FleetManagementApp.class)
public class WeeklyNiggleSnapshotResourceIntTest {

    private static final LocalDate DEFAULT_WEEK_ENDING_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_WEEK_ENDING_ON = LocalDate.now(ZoneId.systemDefault());

    private static final Status DEFAULT_STATUS = Status.SUBMITTED;
    private static final Status UPDATED_STATUS = Status.OPEN;

    private static final Priority DEFAULT_PRIORITY = Priority.LOW;
    private static final Priority UPDATED_PRIORITY = Priority.MEDIUM;

    private static final Integer DEFAULT_COUNT = 1;
    private static final Integer UPDATED_COUNT = 2;

    private static final Integer DEFAULT_AGE_OF_OLDEST = 1;
    private static final Integer UPDATED_AGE_OF_OLDEST = 2;

    @Autowired
    private WeeklyNiggleSnapshotRepository weeklyNiggleSnapshotRepository;

    @Autowired
    private WeeklyNiggleSnapshotService weeklyNiggleSnapshotService;

    @Autowired
    private WeeklyNiggleSnapshotQueryService weeklyNiggleSnapshotQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restWeeklyNiggleSnapshotMockMvc;

    private WeeklyNiggleSnapshot weeklyNiggleSnapshot;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WeeklyNiggleSnapshotResource weeklyNiggleSnapshotResource = new WeeklyNiggleSnapshotResource(weeklyNiggleSnapshotService, weeklyNiggleSnapshotQueryService);
        this.restWeeklyNiggleSnapshotMockMvc = MockMvcBuilders.standaloneSetup(weeklyNiggleSnapshotResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WeeklyNiggleSnapshot createEntity(EntityManager em) {
        WeeklyNiggleSnapshot weeklyNiggleSnapshot = new WeeklyNiggleSnapshot()
            .weekEndingOn(DEFAULT_WEEK_ENDING_ON)
            .status(DEFAULT_STATUS)
            .priority(DEFAULT_PRIORITY)
            .count(DEFAULT_COUNT)
            .ageOfOldest(DEFAULT_AGE_OF_OLDEST);
        return weeklyNiggleSnapshot;
    }

    @Before
    public void initTest() {
        weeklyNiggleSnapshot = createEntity(em);
    }

    @Test
    @Transactional
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void createWeeklyNiggleSnapshot() throws Exception {
        int databaseSizeBeforeCreate = weeklyNiggleSnapshotRepository.findAll().size();

        // Create the WeeklyNiggleSnapshot
        restWeeklyNiggleSnapshotMockMvc.perform(post("/api/weekly-niggle-snapshots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(weeklyNiggleSnapshot)))
            .andExpect(status().isCreated());

        // Validate the WeeklyNiggleSnapshot in the database
        List<WeeklyNiggleSnapshot> weeklyNiggleSnapshotList = weeklyNiggleSnapshotRepository.findAll();
        assertThat(weeklyNiggleSnapshotList).hasSize(databaseSizeBeforeCreate + 1);
        WeeklyNiggleSnapshot testWeeklyNiggleSnapshot = weeklyNiggleSnapshotList.get(weeklyNiggleSnapshotList.size() - 1);
        assertThat(testWeeklyNiggleSnapshot.getWeekEndingOn()).isEqualTo(DEFAULT_WEEK_ENDING_ON);
        assertThat(testWeeklyNiggleSnapshot.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testWeeklyNiggleSnapshot.getPriority()).isEqualTo(DEFAULT_PRIORITY);
        assertThat(testWeeklyNiggleSnapshot.getCount()).isEqualTo(DEFAULT_COUNT);
        assertThat(testWeeklyNiggleSnapshot.getAgeOfOldest()).isEqualTo(DEFAULT_AGE_OF_OLDEST);
    }

    @Test
    @Transactional
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void createWeeklyNiggleSnapshotWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = weeklyNiggleSnapshotRepository.findAll().size();

        // Create the WeeklyNiggleSnapshot with an existing ID
        weeklyNiggleSnapshot.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWeeklyNiggleSnapshotMockMvc.perform(post("/api/weekly-niggle-snapshots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(weeklyNiggleSnapshot)))
            .andExpect(status().isBadRequest());

        // Validate the WeeklyNiggleSnapshot in the database
        List<WeeklyNiggleSnapshot> weeklyNiggleSnapshotList = weeklyNiggleSnapshotRepository.findAll();
        assertThat(weeklyNiggleSnapshotList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})

    public void getAllWeeklyNiggleSnapshots() throws Exception {
        // Initialize the database
        weeklyNiggleSnapshotRepository.saveAndFlush(weeklyNiggleSnapshot);

        // Get all the weeklyNiggleSnapshotList
        restWeeklyNiggleSnapshotMockMvc.perform(get("/api/weekly-niggle-snapshots?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(weeklyNiggleSnapshot.getId().intValue())))
            .andExpect(jsonPath("$.[*].weekEndingOn").value(hasItem(DEFAULT_WEEK_ENDING_ON.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY.toString())))
            .andExpect(jsonPath("$.[*].count").value(hasItem(DEFAULT_COUNT)))
            .andExpect(jsonPath("$.[*].ageOfOldest").value(hasItem(DEFAULT_AGE_OF_OLDEST)));
    }

    @Test
    @Transactional
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void getWeeklyNiggleSnapshot() throws Exception {
        // Initialize the database
        weeklyNiggleSnapshotRepository.saveAndFlush(weeklyNiggleSnapshot);

        // Get the weeklyNiggleSnapshot
        restWeeklyNiggleSnapshotMockMvc.perform(get("/api/weekly-niggle-snapshots/{id}", weeklyNiggleSnapshot.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(weeklyNiggleSnapshot.getId().intValue()))
            .andExpect(jsonPath("$.weekEndingOn").value(DEFAULT_WEEK_ENDING_ON.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.priority").value(DEFAULT_PRIORITY.toString()))
            .andExpect(jsonPath("$.count").value(DEFAULT_COUNT))
            .andExpect(jsonPath("$.ageOfOldest").value(DEFAULT_AGE_OF_OLDEST));
    }

    @Test
    @Transactional
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void getAllWeeklyNiggleSnapshotsByWeekEndingOnIsEqualToSomething() throws Exception {
        // Initialize the database
        weeklyNiggleSnapshotRepository.saveAndFlush(weeklyNiggleSnapshot);

        // Get all the weeklyNiggleSnapshotList where weekEndingOn equals to DEFAULT_WEEK_ENDING_ON
        defaultWeeklyNiggleSnapshotShouldBeFound("weekEndingOn.equals=" + DEFAULT_WEEK_ENDING_ON);

        // Get all the weeklyNiggleSnapshotList where weekEndingOn equals to UPDATED_WEEK_ENDING_ON
        defaultWeeklyNiggleSnapshotShouldNotBeFound("weekEndingOn.equals=" + UPDATED_WEEK_ENDING_ON);
    }

    @Test
    @Transactional
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void getAllWeeklyNiggleSnapshotsByWeekEndingOnIsInShouldWork() throws Exception {
        // Initialize the database
        weeklyNiggleSnapshotRepository.saveAndFlush(weeklyNiggleSnapshot);

        // Get all the weeklyNiggleSnapshotList where weekEndingOn in DEFAULT_WEEK_ENDING_ON or UPDATED_WEEK_ENDING_ON
        defaultWeeklyNiggleSnapshotShouldBeFound("weekEndingOn.in=" + DEFAULT_WEEK_ENDING_ON + "," + UPDATED_WEEK_ENDING_ON);

        // Get all the weeklyNiggleSnapshotList where weekEndingOn equals to UPDATED_WEEK_ENDING_ON
        defaultWeeklyNiggleSnapshotShouldNotBeFound("weekEndingOn.in=" + UPDATED_WEEK_ENDING_ON);
    }

    @Test
    @Transactional
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void getAllWeeklyNiggleSnapshotsByWeekEndingOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        weeklyNiggleSnapshotRepository.saveAndFlush(weeklyNiggleSnapshot);

        // Get all the weeklyNiggleSnapshotList where weekEndingOn is not null
        defaultWeeklyNiggleSnapshotShouldBeFound("weekEndingOn.specified=true");

        // Get all the weeklyNiggleSnapshotList where weekEndingOn is null
        defaultWeeklyNiggleSnapshotShouldNotBeFound("weekEndingOn.specified=false");
    }

    @Test
    @Transactional
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void getAllWeeklyNiggleSnapshotsByWeekEndingOnIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        weeklyNiggleSnapshotRepository.saveAndFlush(weeklyNiggleSnapshot);

        // Get all the weeklyNiggleSnapshotList where weekEndingOn greater than or equals to DEFAULT_WEEK_ENDING_ON
        defaultWeeklyNiggleSnapshotShouldBeFound("weekEndingOn.greaterOrEqualThan=" + DEFAULT_WEEK_ENDING_ON);

        // Get all the weeklyNiggleSnapshotList where weekEndingOn greater than or equals to UPDATED_WEEK_ENDING_ON
        defaultWeeklyNiggleSnapshotShouldNotBeFound("weekEndingOn.greaterOrEqualThan=" + UPDATED_WEEK_ENDING_ON);
    }

    @Test
    @Transactional
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void getAllWeeklyNiggleSnapshotsByWeekEndingOnIsLessThanSomething() throws Exception {
        // Initialize the database
        weeklyNiggleSnapshotRepository.saveAndFlush(weeklyNiggleSnapshot);

        // Get all the weeklyNiggleSnapshotList where weekEndingOn less than or equals to DEFAULT_WEEK_ENDING_ON
        defaultWeeklyNiggleSnapshotShouldNotBeFound("weekEndingOn.lessThan=" + DEFAULT_WEEK_ENDING_ON);

        // Get all the weeklyNiggleSnapshotList where weekEndingOn less than or equals to UPDATED_WEEK_ENDING_ON
        defaultWeeklyNiggleSnapshotShouldBeFound("weekEndingOn.lessThan=" + UPDATED_WEEK_ENDING_ON);
    }


    @Test
    @Transactional
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void getAllWeeklyNiggleSnapshotsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        weeklyNiggleSnapshotRepository.saveAndFlush(weeklyNiggleSnapshot);

        // Get all the weeklyNiggleSnapshotList where status equals to DEFAULT_STATUS
        defaultWeeklyNiggleSnapshotShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the weeklyNiggleSnapshotList where status equals to UPDATED_STATUS
        defaultWeeklyNiggleSnapshotShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void getAllWeeklyNiggleSnapshotsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        weeklyNiggleSnapshotRepository.saveAndFlush(weeklyNiggleSnapshot);

        // Get all the weeklyNiggleSnapshotList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultWeeklyNiggleSnapshotShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the weeklyNiggleSnapshotList where status equals to UPDATED_STATUS
        defaultWeeklyNiggleSnapshotShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void getAllWeeklyNiggleSnapshotsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        weeklyNiggleSnapshotRepository.saveAndFlush(weeklyNiggleSnapshot);

        // Get all the weeklyNiggleSnapshotList where status is not null
        defaultWeeklyNiggleSnapshotShouldBeFound("status.specified=true");

        // Get all the weeklyNiggleSnapshotList where status is null
        defaultWeeklyNiggleSnapshotShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void getAllWeeklyNiggleSnapshotsByPriorityIsEqualToSomething() throws Exception {
        // Initialize the database
        weeklyNiggleSnapshotRepository.saveAndFlush(weeklyNiggleSnapshot);

        // Get all the weeklyNiggleSnapshotList where priority equals to DEFAULT_PRIORITY
        defaultWeeklyNiggleSnapshotShouldBeFound("priority.equals=" + DEFAULT_PRIORITY);

        // Get all the weeklyNiggleSnapshotList where priority equals to UPDATED_PRIORITY
        defaultWeeklyNiggleSnapshotShouldNotBeFound("priority.equals=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void getAllWeeklyNiggleSnapshotsByPriorityIsInShouldWork() throws Exception {
        // Initialize the database
        weeklyNiggleSnapshotRepository.saveAndFlush(weeklyNiggleSnapshot);

        // Get all the weeklyNiggleSnapshotList where priority in DEFAULT_PRIORITY or UPDATED_PRIORITY
        defaultWeeklyNiggleSnapshotShouldBeFound("priority.in=" + DEFAULT_PRIORITY + "," + UPDATED_PRIORITY);

        // Get all the weeklyNiggleSnapshotList where priority equals to UPDATED_PRIORITY
        defaultWeeklyNiggleSnapshotShouldNotBeFound("priority.in=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void getAllWeeklyNiggleSnapshotsByPriorityIsNullOrNotNull() throws Exception {
        // Initialize the database
        weeklyNiggleSnapshotRepository.saveAndFlush(weeklyNiggleSnapshot);

        // Get all the weeklyNiggleSnapshotList where priority is not null
        defaultWeeklyNiggleSnapshotShouldBeFound("priority.specified=true");

        // Get all the weeklyNiggleSnapshotList where priority is null
        defaultWeeklyNiggleSnapshotShouldNotBeFound("priority.specified=false");
    }

    @Test
    @Transactional
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void getAllWeeklyNiggleSnapshotsByCountIsEqualToSomething() throws Exception {
        // Initialize the database
        weeklyNiggleSnapshotRepository.saveAndFlush(weeklyNiggleSnapshot);

        // Get all the weeklyNiggleSnapshotList where count equals to DEFAULT_COUNT
        defaultWeeklyNiggleSnapshotShouldBeFound("count.equals=" + DEFAULT_COUNT);

        // Get all the weeklyNiggleSnapshotList where count equals to UPDATED_COUNT
        defaultWeeklyNiggleSnapshotShouldNotBeFound("count.equals=" + UPDATED_COUNT);
    }

    @Test
    @Transactional
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void getAllWeeklyNiggleSnapshotsByCountIsInShouldWork() throws Exception {
        // Initialize the database
        weeklyNiggleSnapshotRepository.saveAndFlush(weeklyNiggleSnapshot);

        // Get all the weeklyNiggleSnapshotList where count in DEFAULT_COUNT or UPDATED_COUNT
        defaultWeeklyNiggleSnapshotShouldBeFound("count.in=" + DEFAULT_COUNT + "," + UPDATED_COUNT);

        // Get all the weeklyNiggleSnapshotList where count equals to UPDATED_COUNT
        defaultWeeklyNiggleSnapshotShouldNotBeFound("count.in=" + UPDATED_COUNT);
    }

    @Test
    @Transactional
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void getAllWeeklyNiggleSnapshotsByCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        weeklyNiggleSnapshotRepository.saveAndFlush(weeklyNiggleSnapshot);

        // Get all the weeklyNiggleSnapshotList where count is not null
        defaultWeeklyNiggleSnapshotShouldBeFound("count.specified=true");

        // Get all the weeklyNiggleSnapshotList where count is null
        defaultWeeklyNiggleSnapshotShouldNotBeFound("count.specified=false");
    }

    @Test
    @Transactional
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void getAllWeeklyNiggleSnapshotsByCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        weeklyNiggleSnapshotRepository.saveAndFlush(weeklyNiggleSnapshot);

        // Get all the weeklyNiggleSnapshotList where count greater than or equals to DEFAULT_COUNT
        defaultWeeklyNiggleSnapshotShouldBeFound("count.greaterOrEqualThan=" + DEFAULT_COUNT);

        // Get all the weeklyNiggleSnapshotList where count greater than or equals to UPDATED_COUNT
        defaultWeeklyNiggleSnapshotShouldNotBeFound("count.greaterOrEqualThan=" + UPDATED_COUNT);
    }

    @Test
    @Transactional
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void getAllWeeklyNiggleSnapshotsByCountIsLessThanSomething() throws Exception {
        // Initialize the database
        weeklyNiggleSnapshotRepository.saveAndFlush(weeklyNiggleSnapshot);

        // Get all the weeklyNiggleSnapshotList where count less than or equals to DEFAULT_COUNT
        defaultWeeklyNiggleSnapshotShouldNotBeFound("count.lessThan=" + DEFAULT_COUNT);

        // Get all the weeklyNiggleSnapshotList where count less than or equals to UPDATED_COUNT
        defaultWeeklyNiggleSnapshotShouldBeFound("count.lessThan=" + UPDATED_COUNT);
    }


    @Test
    @Transactional
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void getAllWeeklyNiggleSnapshotsByAgeOfOldestIsEqualToSomething() throws Exception {
        // Initialize the database
        weeklyNiggleSnapshotRepository.saveAndFlush(weeklyNiggleSnapshot);

        // Get all the weeklyNiggleSnapshotList where ageOfOldest equals to DEFAULT_AGE_OF_OLDEST
        defaultWeeklyNiggleSnapshotShouldBeFound("ageOfOldest.equals=" + DEFAULT_AGE_OF_OLDEST);

        // Get all the weeklyNiggleSnapshotList where ageOfOldest equals to UPDATED_AGE_OF_OLDEST
        defaultWeeklyNiggleSnapshotShouldNotBeFound("ageOfOldest.equals=" + UPDATED_AGE_OF_OLDEST);
    }

    @Test
    @Transactional
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void getAllWeeklyNiggleSnapshotsByAgeOfOldestIsInShouldWork() throws Exception {
        // Initialize the database
        weeklyNiggleSnapshotRepository.saveAndFlush(weeklyNiggleSnapshot);

        // Get all the weeklyNiggleSnapshotList where ageOfOldest in DEFAULT_AGE_OF_OLDEST or UPDATED_AGE_OF_OLDEST
        defaultWeeklyNiggleSnapshotShouldBeFound("ageOfOldest.in=" + DEFAULT_AGE_OF_OLDEST + "," + UPDATED_AGE_OF_OLDEST);

        // Get all the weeklyNiggleSnapshotList where ageOfOldest equals to UPDATED_AGE_OF_OLDEST
        defaultWeeklyNiggleSnapshotShouldNotBeFound("ageOfOldest.in=" + UPDATED_AGE_OF_OLDEST);
    }

    @Test
    @Transactional
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void getAllWeeklyNiggleSnapshotsByAgeOfOldestIsNullOrNotNull() throws Exception {
        // Initialize the database
        weeklyNiggleSnapshotRepository.saveAndFlush(weeklyNiggleSnapshot);

        // Get all the weeklyNiggleSnapshotList where ageOfOldest is not null
        defaultWeeklyNiggleSnapshotShouldBeFound("ageOfOldest.specified=true");

        // Get all the weeklyNiggleSnapshotList where ageOfOldest is null
        defaultWeeklyNiggleSnapshotShouldNotBeFound("ageOfOldest.specified=false");
    }

    @Test
    @Transactional
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void getAllWeeklyNiggleSnapshotsByAgeOfOldestIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        weeklyNiggleSnapshotRepository.saveAndFlush(weeklyNiggleSnapshot);

        // Get all the weeklyNiggleSnapshotList where ageOfOldest greater than or equals to DEFAULT_AGE_OF_OLDEST
        defaultWeeklyNiggleSnapshotShouldBeFound("ageOfOldest.greaterOrEqualThan=" + DEFAULT_AGE_OF_OLDEST);

        // Get all the weeklyNiggleSnapshotList where ageOfOldest greater than or equals to UPDATED_AGE_OF_OLDEST
        defaultWeeklyNiggleSnapshotShouldNotBeFound("ageOfOldest.greaterOrEqualThan=" + UPDATED_AGE_OF_OLDEST);
    }

    @Test
    @Transactional
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void getAllWeeklyNiggleSnapshotsByAgeOfOldestIsLessThanSomething() throws Exception {
        // Initialize the database
        weeklyNiggleSnapshotRepository.saveAndFlush(weeklyNiggleSnapshot);

        // Get all the weeklyNiggleSnapshotList where ageOfOldest less than or equals to DEFAULT_AGE_OF_OLDEST
        defaultWeeklyNiggleSnapshotShouldNotBeFound("ageOfOldest.lessThan=" + DEFAULT_AGE_OF_OLDEST);

        // Get all the weeklyNiggleSnapshotList where ageOfOldest less than or equals to UPDATED_AGE_OF_OLDEST
        defaultWeeklyNiggleSnapshotShouldBeFound("ageOfOldest.lessThan=" + UPDATED_AGE_OF_OLDEST);
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultWeeklyNiggleSnapshotShouldBeFound(String filter) throws Exception {
        restWeeklyNiggleSnapshotMockMvc.perform(get("/api/weekly-niggle-snapshots?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(weeklyNiggleSnapshot.getId().intValue())))
            .andExpect(jsonPath("$.[*].weekEndingOn").value(hasItem(DEFAULT_WEEK_ENDING_ON.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY.toString())))
            .andExpect(jsonPath("$.[*].count").value(hasItem(DEFAULT_COUNT)))
            .andExpect(jsonPath("$.[*].ageOfOldest").value(hasItem(DEFAULT_AGE_OF_OLDEST)));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultWeeklyNiggleSnapshotShouldNotBeFound(String filter) throws Exception {
        restWeeklyNiggleSnapshotMockMvc.perform(get("/api/weekly-niggle-snapshots?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void getNonExistingWeeklyNiggleSnapshot() throws Exception {
        // Get the weeklyNiggleSnapshot
        restWeeklyNiggleSnapshotMockMvc.perform(get("/api/weekly-niggle-snapshots/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void updateWeeklyNiggleSnapshot() throws Exception {
        // Initialize the database
        weeklyNiggleSnapshotService.save(weeklyNiggleSnapshot);

        int databaseSizeBeforeUpdate = weeklyNiggleSnapshotRepository.findAll().size();

        // Update the weeklyNiggleSnapshot
        WeeklyNiggleSnapshot updatedWeeklyNiggleSnapshot = weeklyNiggleSnapshotRepository.findOne(weeklyNiggleSnapshot.getId());
        // Disconnect from session so that the updates on updatedWeeklyNiggleSnapshot are not directly saved in db
        em.detach(updatedWeeklyNiggleSnapshot);
        updatedWeeklyNiggleSnapshot
            .weekEndingOn(UPDATED_WEEK_ENDING_ON)
            .status(UPDATED_STATUS)
            .priority(UPDATED_PRIORITY)
            .count(UPDATED_COUNT)
            .ageOfOldest(UPDATED_AGE_OF_OLDEST);

        restWeeklyNiggleSnapshotMockMvc.perform(put("/api/weekly-niggle-snapshots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedWeeklyNiggleSnapshot)))
            .andExpect(status().isOk());

        // Validate the WeeklyNiggleSnapshot in the database
        List<WeeklyNiggleSnapshot> weeklyNiggleSnapshotList = weeklyNiggleSnapshotRepository.findAll();
        assertThat(weeklyNiggleSnapshotList).hasSize(databaseSizeBeforeUpdate);
        WeeklyNiggleSnapshot testWeeklyNiggleSnapshot = weeklyNiggleSnapshotList.get(weeklyNiggleSnapshotList.size() - 1);
        assertThat(testWeeklyNiggleSnapshot.getWeekEndingOn()).isEqualTo(UPDATED_WEEK_ENDING_ON);
        assertThat(testWeeklyNiggleSnapshot.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testWeeklyNiggleSnapshot.getPriority()).isEqualTo(UPDATED_PRIORITY);
        assertThat(testWeeklyNiggleSnapshot.getCount()).isEqualTo(UPDATED_COUNT);
        assertThat(testWeeklyNiggleSnapshot.getAgeOfOldest()).isEqualTo(UPDATED_AGE_OF_OLDEST);
    }

    @Test
    @Transactional
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void updateNonExistingWeeklyNiggleSnapshot() throws Exception {
        int databaseSizeBeforeUpdate = weeklyNiggleSnapshotRepository.findAll().size();

        // Create the WeeklyNiggleSnapshot

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restWeeklyNiggleSnapshotMockMvc.perform(put("/api/weekly-niggle-snapshots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(weeklyNiggleSnapshot)))
            .andExpect(status().isCreated());

        // Validate the WeeklyNiggleSnapshot in the database
        List<WeeklyNiggleSnapshot> weeklyNiggleSnapshotList = weeklyNiggleSnapshotRepository.findAll();
        assertThat(weeklyNiggleSnapshotList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void deleteWeeklyNiggleSnapshot() throws Exception {
        // Initialize the database
        weeklyNiggleSnapshotService.save(weeklyNiggleSnapshot);

        int databaseSizeBeforeDelete = weeklyNiggleSnapshotRepository.findAll().size();

        // Get the weeklyNiggleSnapshot
        restWeeklyNiggleSnapshotMockMvc.perform(delete("/api/weekly-niggle-snapshots/{id}", weeklyNiggleSnapshot.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<WeeklyNiggleSnapshot> weeklyNiggleSnapshotList = weeklyNiggleSnapshotRepository.findAll();
        assertThat(weeklyNiggleSnapshotList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WeeklyNiggleSnapshot.class);
        WeeklyNiggleSnapshot weeklyNiggleSnapshot1 = new WeeklyNiggleSnapshot();
        weeklyNiggleSnapshot1.setId(1L);
        WeeklyNiggleSnapshot weeklyNiggleSnapshot2 = new WeeklyNiggleSnapshot();
        weeklyNiggleSnapshot2.setId(weeklyNiggleSnapshot1.getId());
        assertThat(weeklyNiggleSnapshot1).isEqualTo(weeklyNiggleSnapshot2);
        weeklyNiggleSnapshot2.setId(2L);
        assertThat(weeklyNiggleSnapshot1).isNotEqualTo(weeklyNiggleSnapshot2);
        weeklyNiggleSnapshot1.setId(null);
        assertThat(weeklyNiggleSnapshot1).isNotEqualTo(weeklyNiggleSnapshot2);
    }
}
