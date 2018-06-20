package com.dempseywood.web.rest;

import com.dempseywood.FleetManagementApp;

import com.dempseywood.domain.NiggleSnapshot;
import com.dempseywood.repository.NiggleSnapshotRepository;
import com.dempseywood.service.NiggleSnapshotService;
import com.dempseywood.web.rest.errors.ExceptionTranslator;
import com.dempseywood.service.dto.NiggleSnapshotCriteria;
import com.dempseywood.service.NiggleSnapshotQueryService;

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
 * Test class for the NiggleSnapshotResource REST controller.
 *
 * @see NiggleSnapshotResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FleetManagementApp.class)
public class NiggleSnapshotResourceIntTest {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Status DEFAULT_STATUS = Status.SUBMITTED;
    private static final Status UPDATED_STATUS = Status.OPEN;

    private static final Priority DEFAULT_PRIORITY = Priority.LOW;
    private static final Priority UPDATED_PRIORITY = Priority.MEDIUM;

    private static final Integer DEFAULT_COUNT = 1;
    private static final Integer UPDATED_COUNT = 2;

    private static final Integer DEFAULT_AGE_OF_OLDEST = 1;
    private static final Integer UPDATED_AGE_OF_OLDEST = 2;

    @Autowired
    private NiggleSnapshotRepository niggleSnapshotRepository;

    @Autowired
    private NiggleSnapshotService niggleSnapshotService;

    @Autowired
    private NiggleSnapshotQueryService niggleSnapshotQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restNiggleSnapshotMockMvc;

    private NiggleSnapshot niggleSnapshot;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NiggleSnapshotResource niggleSnapshotResource = new NiggleSnapshotResource(niggleSnapshotService, niggleSnapshotQueryService);
        this.restNiggleSnapshotMockMvc = MockMvcBuilders.standaloneSetup(niggleSnapshotResource)
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
    public static NiggleSnapshot createEntity(EntityManager em) {
        NiggleSnapshot niggleSnapshot = new NiggleSnapshot()
            .date(DEFAULT_DATE)
            .status(DEFAULT_STATUS)
            .priority(DEFAULT_PRIORITY)
            .count(DEFAULT_COUNT)
            .ageOfOldest(DEFAULT_AGE_OF_OLDEST);
        return niggleSnapshot;
    }

    @Before
    public void initTest() {
        niggleSnapshot = createEntity(em);
    }

    @Test
    @Transactional
    public void createNiggleSnapshot() throws Exception {
        int databaseSizeBeforeCreate = niggleSnapshotRepository.findAll().size();

        // Create the NiggleSnapshot
        restNiggleSnapshotMockMvc.perform(post("/api/niggle-snapshots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(niggleSnapshot)))
            .andExpect(status().isCreated());

        // Validate the NiggleSnapshot in the database
        List<NiggleSnapshot> niggleSnapshotList = niggleSnapshotRepository.findAll();
        assertThat(niggleSnapshotList).hasSize(databaseSizeBeforeCreate + 1);
        NiggleSnapshot testNiggleSnapshot = niggleSnapshotList.get(niggleSnapshotList.size() - 1);
        assertThat(testNiggleSnapshot.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testNiggleSnapshot.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testNiggleSnapshot.getPriority()).isEqualTo(DEFAULT_PRIORITY);
        assertThat(testNiggleSnapshot.getCount()).isEqualTo(DEFAULT_COUNT);
        assertThat(testNiggleSnapshot.getAgeOfOldest()).isEqualTo(DEFAULT_AGE_OF_OLDEST);
    }

    @Test
    @Transactional
    public void createNiggleSnapshotWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = niggleSnapshotRepository.findAll().size();

        // Create the NiggleSnapshot with an existing ID
        niggleSnapshot.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNiggleSnapshotMockMvc.perform(post("/api/niggle-snapshots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(niggleSnapshot)))
            .andExpect(status().isBadRequest());

        // Validate the NiggleSnapshot in the database
        List<NiggleSnapshot> niggleSnapshotList = niggleSnapshotRepository.findAll();
        assertThat(niggleSnapshotList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllNiggleSnapshots() throws Exception {
        // Initialize the database
        niggleSnapshotRepository.saveAndFlush(niggleSnapshot);

        // Get all the niggleSnapshotList
        restNiggleSnapshotMockMvc.perform(get("/api/niggle-snapshots?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(niggleSnapshot.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY.toString())))
            .andExpect(jsonPath("$.[*].count").value(hasItem(DEFAULT_COUNT)))
            .andExpect(jsonPath("$.[*].ageOfOldest").value(hasItem(DEFAULT_AGE_OF_OLDEST)));
    }

    @Test
    @Transactional
    public void getNiggleSnapshot() throws Exception {
        // Initialize the database
        niggleSnapshotRepository.saveAndFlush(niggleSnapshot);

        // Get the niggleSnapshot
        restNiggleSnapshotMockMvc.perform(get("/api/niggle-snapshots/{id}", niggleSnapshot.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(niggleSnapshot.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.priority").value(DEFAULT_PRIORITY.toString()))
            .andExpect(jsonPath("$.count").value(DEFAULT_COUNT))
            .andExpect(jsonPath("$.ageOfOldest").value(DEFAULT_AGE_OF_OLDEST));
    }

    @Test
    @Transactional
    public void getAllNiggleSnapshotsByDateIsEqualToSomething() throws Exception {
        // Initialize the database
        niggleSnapshotRepository.saveAndFlush(niggleSnapshot);

        // Get all the niggleSnapshotList where date equals to DEFAULT_DATE
        defaultNiggleSnapshotShouldBeFound("date.equals=" + DEFAULT_DATE);

        // Get all the niggleSnapshotList where date equals to UPDATED_DATE
        defaultNiggleSnapshotShouldNotBeFound("date.equals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllNiggleSnapshotsByDateIsInShouldWork() throws Exception {
        // Initialize the database
        niggleSnapshotRepository.saveAndFlush(niggleSnapshot);

        // Get all the niggleSnapshotList where date in DEFAULT_DATE or UPDATED_DATE
        defaultNiggleSnapshotShouldBeFound("date.in=" + DEFAULT_DATE + "," + UPDATED_DATE);

        // Get all the niggleSnapshotList where date equals to UPDATED_DATE
        defaultNiggleSnapshotShouldNotBeFound("date.in=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllNiggleSnapshotsByDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        niggleSnapshotRepository.saveAndFlush(niggleSnapshot);

        // Get all the niggleSnapshotList where date is not null
        defaultNiggleSnapshotShouldBeFound("date.specified=true");

        // Get all the niggleSnapshotList where date is null
        defaultNiggleSnapshotShouldNotBeFound("date.specified=false");
    }

    @Test
    @Transactional
    public void getAllNiggleSnapshotsByDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        niggleSnapshotRepository.saveAndFlush(niggleSnapshot);

        // Get all the niggleSnapshotList where date greater than or equals to DEFAULT_DATE
        defaultNiggleSnapshotShouldBeFound("date.greaterOrEqualThan=" + DEFAULT_DATE);

        // Get all the niggleSnapshotList where date greater than or equals to UPDATED_DATE
        defaultNiggleSnapshotShouldNotBeFound("date.greaterOrEqualThan=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllNiggleSnapshotsByDateIsLessThanSomething() throws Exception {
        // Initialize the database
        niggleSnapshotRepository.saveAndFlush(niggleSnapshot);

        // Get all the niggleSnapshotList where date less than or equals to DEFAULT_DATE
        defaultNiggleSnapshotShouldNotBeFound("date.lessThan=" + DEFAULT_DATE);

        // Get all the niggleSnapshotList where date less than or equals to UPDATED_DATE
        defaultNiggleSnapshotShouldBeFound("date.lessThan=" + UPDATED_DATE);
    }


    @Test
    @Transactional
    public void getAllNiggleSnapshotsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        niggleSnapshotRepository.saveAndFlush(niggleSnapshot);

        // Get all the niggleSnapshotList where status equals to DEFAULT_STATUS
        defaultNiggleSnapshotShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the niggleSnapshotList where status equals to UPDATED_STATUS
        defaultNiggleSnapshotShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllNiggleSnapshotsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        niggleSnapshotRepository.saveAndFlush(niggleSnapshot);

        // Get all the niggleSnapshotList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultNiggleSnapshotShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the niggleSnapshotList where status equals to UPDATED_STATUS
        defaultNiggleSnapshotShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllNiggleSnapshotsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        niggleSnapshotRepository.saveAndFlush(niggleSnapshot);

        // Get all the niggleSnapshotList where status is not null
        defaultNiggleSnapshotShouldBeFound("status.specified=true");

        // Get all the niggleSnapshotList where status is null
        defaultNiggleSnapshotShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllNiggleSnapshotsByPriorityIsEqualToSomething() throws Exception {
        // Initialize the database
        niggleSnapshotRepository.saveAndFlush(niggleSnapshot);

        // Get all the niggleSnapshotList where priority equals to DEFAULT_PRIORITY
        defaultNiggleSnapshotShouldBeFound("priority.equals=" + DEFAULT_PRIORITY);

        // Get all the niggleSnapshotList where priority equals to UPDATED_PRIORITY
        defaultNiggleSnapshotShouldNotBeFound("priority.equals=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    public void getAllNiggleSnapshotsByPriorityIsInShouldWork() throws Exception {
        // Initialize the database
        niggleSnapshotRepository.saveAndFlush(niggleSnapshot);

        // Get all the niggleSnapshotList where priority in DEFAULT_PRIORITY or UPDATED_PRIORITY
        defaultNiggleSnapshotShouldBeFound("priority.in=" + DEFAULT_PRIORITY + "," + UPDATED_PRIORITY);

        // Get all the niggleSnapshotList where priority equals to UPDATED_PRIORITY
        defaultNiggleSnapshotShouldNotBeFound("priority.in=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    public void getAllNiggleSnapshotsByPriorityIsNullOrNotNull() throws Exception {
        // Initialize the database
        niggleSnapshotRepository.saveAndFlush(niggleSnapshot);

        // Get all the niggleSnapshotList where priority is not null
        defaultNiggleSnapshotShouldBeFound("priority.specified=true");

        // Get all the niggleSnapshotList where priority is null
        defaultNiggleSnapshotShouldNotBeFound("priority.specified=false");
    }

    @Test
    @Transactional
    public void getAllNiggleSnapshotsByCountIsEqualToSomething() throws Exception {
        // Initialize the database
        niggleSnapshotRepository.saveAndFlush(niggleSnapshot);

        // Get all the niggleSnapshotList where count equals to DEFAULT_COUNT
        defaultNiggleSnapshotShouldBeFound("count.equals=" + DEFAULT_COUNT);

        // Get all the niggleSnapshotList where count equals to UPDATED_COUNT
        defaultNiggleSnapshotShouldNotBeFound("count.equals=" + UPDATED_COUNT);
    }

    @Test
    @Transactional
    public void getAllNiggleSnapshotsByCountIsInShouldWork() throws Exception {
        // Initialize the database
        niggleSnapshotRepository.saveAndFlush(niggleSnapshot);

        // Get all the niggleSnapshotList where count in DEFAULT_COUNT or UPDATED_COUNT
        defaultNiggleSnapshotShouldBeFound("count.in=" + DEFAULT_COUNT + "," + UPDATED_COUNT);

        // Get all the niggleSnapshotList where count equals to UPDATED_COUNT
        defaultNiggleSnapshotShouldNotBeFound("count.in=" + UPDATED_COUNT);
    }

    @Test
    @Transactional
    public void getAllNiggleSnapshotsByCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        niggleSnapshotRepository.saveAndFlush(niggleSnapshot);

        // Get all the niggleSnapshotList where count is not null
        defaultNiggleSnapshotShouldBeFound("count.specified=true");

        // Get all the niggleSnapshotList where count is null
        defaultNiggleSnapshotShouldNotBeFound("count.specified=false");
    }

    @Test
    @Transactional
    public void getAllNiggleSnapshotsByCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        niggleSnapshotRepository.saveAndFlush(niggleSnapshot);

        // Get all the niggleSnapshotList where count greater than or equals to DEFAULT_COUNT
        defaultNiggleSnapshotShouldBeFound("count.greaterOrEqualThan=" + DEFAULT_COUNT);

        // Get all the niggleSnapshotList where count greater than or equals to UPDATED_COUNT
        defaultNiggleSnapshotShouldNotBeFound("count.greaterOrEqualThan=" + UPDATED_COUNT);
    }

    @Test
    @Transactional
    public void getAllNiggleSnapshotsByCountIsLessThanSomething() throws Exception {
        // Initialize the database
        niggleSnapshotRepository.saveAndFlush(niggleSnapshot);

        // Get all the niggleSnapshotList where count less than or equals to DEFAULT_COUNT
        defaultNiggleSnapshotShouldNotBeFound("count.lessThan=" + DEFAULT_COUNT);

        // Get all the niggleSnapshotList where count less than or equals to UPDATED_COUNT
        defaultNiggleSnapshotShouldBeFound("count.lessThan=" + UPDATED_COUNT);
    }


    @Test
    @Transactional
    public void getAllNiggleSnapshotsByAgeOfOldestIsEqualToSomething() throws Exception {
        // Initialize the database
        niggleSnapshotRepository.saveAndFlush(niggleSnapshot);

        // Get all the niggleSnapshotList where ageOfOldest equals to DEFAULT_AGE_OF_OLDEST
        defaultNiggleSnapshotShouldBeFound("ageOfOldest.equals=" + DEFAULT_AGE_OF_OLDEST);

        // Get all the niggleSnapshotList where ageOfOldest equals to UPDATED_AGE_OF_OLDEST
        defaultNiggleSnapshotShouldNotBeFound("ageOfOldest.equals=" + UPDATED_AGE_OF_OLDEST);
    }

    @Test
    @Transactional
    public void getAllNiggleSnapshotsByAgeOfOldestIsInShouldWork() throws Exception {
        // Initialize the database
        niggleSnapshotRepository.saveAndFlush(niggleSnapshot);

        // Get all the niggleSnapshotList where ageOfOldest in DEFAULT_AGE_OF_OLDEST or UPDATED_AGE_OF_OLDEST
        defaultNiggleSnapshotShouldBeFound("ageOfOldest.in=" + DEFAULT_AGE_OF_OLDEST + "," + UPDATED_AGE_OF_OLDEST);

        // Get all the niggleSnapshotList where ageOfOldest equals to UPDATED_AGE_OF_OLDEST
        defaultNiggleSnapshotShouldNotBeFound("ageOfOldest.in=" + UPDATED_AGE_OF_OLDEST);
    }

    @Test
    @Transactional
    public void getAllNiggleSnapshotsByAgeOfOldestIsNullOrNotNull() throws Exception {
        // Initialize the database
        niggleSnapshotRepository.saveAndFlush(niggleSnapshot);

        // Get all the niggleSnapshotList where ageOfOldest is not null
        defaultNiggleSnapshotShouldBeFound("ageOfOldest.specified=true");

        // Get all the niggleSnapshotList where ageOfOldest is null
        defaultNiggleSnapshotShouldNotBeFound("ageOfOldest.specified=false");
    }

    @Test
    @Transactional
    public void getAllNiggleSnapshotsByAgeOfOldestIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        niggleSnapshotRepository.saveAndFlush(niggleSnapshot);

        // Get all the niggleSnapshotList where ageOfOldest greater than or equals to DEFAULT_AGE_OF_OLDEST
        defaultNiggleSnapshotShouldBeFound("ageOfOldest.greaterOrEqualThan=" + DEFAULT_AGE_OF_OLDEST);

        // Get all the niggleSnapshotList where ageOfOldest greater than or equals to UPDATED_AGE_OF_OLDEST
        defaultNiggleSnapshotShouldNotBeFound("ageOfOldest.greaterOrEqualThan=" + UPDATED_AGE_OF_OLDEST);
    }

    @Test
    @Transactional
    public void getAllNiggleSnapshotsByAgeOfOldestIsLessThanSomething() throws Exception {
        // Initialize the database
        niggleSnapshotRepository.saveAndFlush(niggleSnapshot);

        // Get all the niggleSnapshotList where ageOfOldest less than or equals to DEFAULT_AGE_OF_OLDEST
        defaultNiggleSnapshotShouldNotBeFound("ageOfOldest.lessThan=" + DEFAULT_AGE_OF_OLDEST);

        // Get all the niggleSnapshotList where ageOfOldest less than or equals to UPDATED_AGE_OF_OLDEST
        defaultNiggleSnapshotShouldBeFound("ageOfOldest.lessThan=" + UPDATED_AGE_OF_OLDEST);
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultNiggleSnapshotShouldBeFound(String filter) throws Exception {
        restNiggleSnapshotMockMvc.perform(get("/api/niggle-snapshots?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(niggleSnapshot.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY.toString())))
            .andExpect(jsonPath("$.[*].count").value(hasItem(DEFAULT_COUNT)))
            .andExpect(jsonPath("$.[*].ageOfOldest").value(hasItem(DEFAULT_AGE_OF_OLDEST)));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultNiggleSnapshotShouldNotBeFound(String filter) throws Exception {
        restNiggleSnapshotMockMvc.perform(get("/api/niggle-snapshots?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingNiggleSnapshot() throws Exception {
        // Get the niggleSnapshot
        restNiggleSnapshotMockMvc.perform(get("/api/niggle-snapshots/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNiggleSnapshot() throws Exception {
        // Initialize the database
        niggleSnapshotService.save(niggleSnapshot);

        int databaseSizeBeforeUpdate = niggleSnapshotRepository.findAll().size();

        // Update the niggleSnapshot
        NiggleSnapshot updatedNiggleSnapshot = niggleSnapshotRepository.findOne(niggleSnapshot.getId());
        // Disconnect from session so that the updates on updatedNiggleSnapshot are not directly saved in db
        em.detach(updatedNiggleSnapshot);
        updatedNiggleSnapshot
            .date(UPDATED_DATE)
            .status(UPDATED_STATUS)
            .priority(UPDATED_PRIORITY)
            .count(UPDATED_COUNT)
            .ageOfOldest(UPDATED_AGE_OF_OLDEST);

        restNiggleSnapshotMockMvc.perform(put("/api/niggle-snapshots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNiggleSnapshot)))
            .andExpect(status().isOk());

        // Validate the NiggleSnapshot in the database
        List<NiggleSnapshot> niggleSnapshotList = niggleSnapshotRepository.findAll();
        assertThat(niggleSnapshotList).hasSize(databaseSizeBeforeUpdate);
        NiggleSnapshot testNiggleSnapshot = niggleSnapshotList.get(niggleSnapshotList.size() - 1);
        assertThat(testNiggleSnapshot.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testNiggleSnapshot.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testNiggleSnapshot.getPriority()).isEqualTo(UPDATED_PRIORITY);
        assertThat(testNiggleSnapshot.getCount()).isEqualTo(UPDATED_COUNT);
        assertThat(testNiggleSnapshot.getAgeOfOldest()).isEqualTo(UPDATED_AGE_OF_OLDEST);
    }

    @Test
    @Transactional
    public void updateNonExistingNiggleSnapshot() throws Exception {
        int databaseSizeBeforeUpdate = niggleSnapshotRepository.findAll().size();

        // Create the NiggleSnapshot

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restNiggleSnapshotMockMvc.perform(put("/api/niggle-snapshots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(niggleSnapshot)))
            .andExpect(status().isCreated());

        // Validate the NiggleSnapshot in the database
        List<NiggleSnapshot> niggleSnapshotList = niggleSnapshotRepository.findAll();
        assertThat(niggleSnapshotList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteNiggleSnapshot() throws Exception {
        // Initialize the database
        niggleSnapshotService.save(niggleSnapshot);

        int databaseSizeBeforeDelete = niggleSnapshotRepository.findAll().size();

        // Get the niggleSnapshot
        restNiggleSnapshotMockMvc.perform(delete("/api/niggle-snapshots/{id}", niggleSnapshot.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<NiggleSnapshot> niggleSnapshotList = niggleSnapshotRepository.findAll();
        assertThat(niggleSnapshotList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NiggleSnapshot.class);
        NiggleSnapshot niggleSnapshot1 = new NiggleSnapshot();
        niggleSnapshot1.setId(1L);
        NiggleSnapshot niggleSnapshot2 = new NiggleSnapshot();
        niggleSnapshot2.setId(niggleSnapshot1.getId());
        assertThat(niggleSnapshot1).isEqualTo(niggleSnapshot2);
        niggleSnapshot2.setId(2L);
        assertThat(niggleSnapshot1).isNotEqualTo(niggleSnapshot2);
        niggleSnapshot1.setId(null);
        assertThat(niggleSnapshot1).isNotEqualTo(niggleSnapshot2);
    }
}
