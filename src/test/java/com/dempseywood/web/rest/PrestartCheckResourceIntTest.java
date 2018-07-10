package com.dempseywood.web.rest;

import com.dempseywood.FleetManagementApp;

import com.dempseywood.domain.PrestartCheck;
import com.dempseywood.domain.PlantLog;
import com.dempseywood.domain.Project;
import com.dempseywood.domain.Plant;
import com.dempseywood.domain.Location;
import com.dempseywood.domain.People;
import com.dempseywood.repository.PrestartCheckRepository;
import com.dempseywood.service.PrestartCheckService;
import com.dempseywood.web.rest.errors.ExceptionTranslator;
import com.dempseywood.service.dto.PrestartCheckCriteria;
import com.dempseywood.service.PrestartCheckQueryService;

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
import java.util.List;

import static com.dempseywood.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PrestartCheckResource REST controller.
 *
 * @see PrestartCheckResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FleetManagementApp.class)
public class PrestartCheckResourceIntTest {

    private static final byte[] DEFAULT_SIGNATURE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_SIGNATURE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_SIGNATURE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_SIGNATURE_CONTENT_TYPE = "image/png";

    @Autowired
    private PrestartCheckRepository prestartCheckRepository;

    @Autowired
    private PrestartCheckService prestartCheckService;

    @Autowired
    private PrestartCheckQueryService prestartCheckQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPrestartCheckMockMvc;

    private PrestartCheck prestartCheck;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PrestartCheckResource prestartCheckResource = new PrestartCheckResource(prestartCheckService, prestartCheckQueryService);
        this.restPrestartCheckMockMvc = MockMvcBuilders.standaloneSetup(prestartCheckResource)
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
    public static PrestartCheck createEntity(EntityManager em) {
        PrestartCheck prestartCheck = new PrestartCheck()
            .signature(DEFAULT_SIGNATURE)
            .signatureContentType(DEFAULT_SIGNATURE_CONTENT_TYPE);
        return prestartCheck;
    }

    @Before
    public void initTest() {
        prestartCheck = createEntity(em);
    }

    @Test
    @Transactional
    public void createPrestartCheck() throws Exception {
        int databaseSizeBeforeCreate = prestartCheckRepository.findAll().size();

        // Create the PrestartCheck
        restPrestartCheckMockMvc.perform(post("/api/prestart-checks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prestartCheck)))
            .andExpect(status().isCreated());

        // Validate the PrestartCheck in the database
        List<PrestartCheck> prestartCheckList = prestartCheckRepository.findAll();
        assertThat(prestartCheckList).hasSize(databaseSizeBeforeCreate + 1);
        PrestartCheck testPrestartCheck = prestartCheckList.get(prestartCheckList.size() - 1);
        assertThat(testPrestartCheck.getSignature()).isEqualTo(DEFAULT_SIGNATURE);
        assertThat(testPrestartCheck.getSignatureContentType()).isEqualTo(DEFAULT_SIGNATURE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createPrestartCheckWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = prestartCheckRepository.findAll().size();

        // Create the PrestartCheck with an existing ID
        prestartCheck.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrestartCheckMockMvc.perform(post("/api/prestart-checks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prestartCheck)))
            .andExpect(status().isBadRequest());

        // Validate the PrestartCheck in the database
        List<PrestartCheck> prestartCheckList = prestartCheckRepository.findAll();
        assertThat(prestartCheckList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPrestartChecks() throws Exception {
        // Initialize the database
        prestartCheckRepository.saveAndFlush(prestartCheck);

        // Get all the prestartCheckList
        restPrestartCheckMockMvc.perform(get("/api/prestart-checks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prestartCheck.getId().intValue())))
            .andExpect(jsonPath("$.[*].signatureContentType").value(hasItem(DEFAULT_SIGNATURE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].signature").value(hasItem(Base64Utils.encodeToString(DEFAULT_SIGNATURE))));
    }

    @Test
    @Transactional
    public void getPrestartCheck() throws Exception {
        // Initialize the database
        prestartCheckRepository.saveAndFlush(prestartCheck);

        // Get the prestartCheck
        restPrestartCheckMockMvc.perform(get("/api/prestart-checks/{id}", prestartCheck.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(prestartCheck.getId().intValue()))
            .andExpect(jsonPath("$.signatureContentType").value(DEFAULT_SIGNATURE_CONTENT_TYPE))
            .andExpect(jsonPath("$.signature").value(Base64Utils.encodeToString(DEFAULT_SIGNATURE)));
    }

    @Test
    @Transactional
    public void getAllPrestartChecksByPlantLogIsEqualToSomething() throws Exception {
        // Initialize the database
        PlantLog plantLog = PlantLogResourceIntTest.createEntity(em);
        em.persist(plantLog);
        em.flush();
        prestartCheck.setPlantLog(plantLog);
        prestartCheckRepository.saveAndFlush(prestartCheck);
        Long plantLogId = plantLog.getId();

        // Get all the prestartCheckList where plantLog equals to plantLogId
        defaultPrestartCheckShouldBeFound("plantLogId.equals=" + plantLogId);

        // Get all the prestartCheckList where plantLog equals to plantLogId + 1
        defaultPrestartCheckShouldNotBeFound("plantLogId.equals=" + (plantLogId + 1));
    }


    @Test
    @Transactional
    public void getAllPrestartChecksByProjectIsEqualToSomething() throws Exception {
        // Initialize the database
        Project project = ProjectResourceIntTest.createEntity(em);
        em.persist(project);
        em.flush();
        prestartCheck.setProject(project);
        prestartCheckRepository.saveAndFlush(prestartCheck);
        Long projectId = project.getId();

        // Get all the prestartCheckList where project equals to projectId
        defaultPrestartCheckShouldBeFound("projectId.equals=" + projectId);

        // Get all the prestartCheckList where project equals to projectId + 1
        defaultPrestartCheckShouldNotBeFound("projectId.equals=" + (projectId + 1));
    }


    @Test
    @Transactional
    public void getAllPrestartChecksByPlantIsEqualToSomething() throws Exception {
        // Initialize the database
        Plant plant = PlantResourceIntTest.createEntity(em);
        em.persist(plant);
        em.flush();
        prestartCheck.setPlant(plant);
        prestartCheckRepository.saveAndFlush(prestartCheck);
        Long plantId = plant.getId();

        // Get all the prestartCheckList where plant equals to plantId
        defaultPrestartCheckShouldBeFound("plantId.equals=" + plantId);

        // Get all the prestartCheckList where plant equals to plantId + 1
        defaultPrestartCheckShouldNotBeFound("plantId.equals=" + (plantId + 1));
    }


    @Test
    @Transactional
    public void getAllPrestartChecksByLocationIsEqualToSomething() throws Exception {
        // Initialize the database
        Location location = LocationResourceIntTest.createEntity(em);
        em.persist(location);
        em.flush();
        prestartCheck.setLocation(location);
        prestartCheckRepository.saveAndFlush(prestartCheck);
        Long locationId = location.getId();

        // Get all the prestartCheckList where location equals to locationId
        defaultPrestartCheckShouldBeFound("locationId.equals=" + locationId);

        // Get all the prestartCheckList where location equals to locationId + 1
        defaultPrestartCheckShouldNotBeFound("locationId.equals=" + (locationId + 1));
    }


    @Test
    @Transactional
    public void getAllPrestartChecksByOperatorIsEqualToSomething() throws Exception {
        // Initialize the database
        People operator = PeopleResourceIntTest.createEntity(em);
        em.persist(operator);
        em.flush();
        prestartCheck.setOperator(operator);
        prestartCheckRepository.saveAndFlush(prestartCheck);
        Long operatorId = operator.getId();

        // Get all the prestartCheckList where operator equals to operatorId
        defaultPrestartCheckShouldBeFound("operatorId.equals=" + operatorId);

        // Get all the prestartCheckList where operator equals to operatorId + 1
        defaultPrestartCheckShouldNotBeFound("operatorId.equals=" + (operatorId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultPrestartCheckShouldBeFound(String filter) throws Exception {
        restPrestartCheckMockMvc.perform(get("/api/prestart-checks?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prestartCheck.getId().intValue())))
            .andExpect(jsonPath("$.[*].signatureContentType").value(hasItem(DEFAULT_SIGNATURE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].signature").value(hasItem(Base64Utils.encodeToString(DEFAULT_SIGNATURE))));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultPrestartCheckShouldNotBeFound(String filter) throws Exception {
        restPrestartCheckMockMvc.perform(get("/api/prestart-checks?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingPrestartCheck() throws Exception {
        // Get the prestartCheck
        restPrestartCheckMockMvc.perform(get("/api/prestart-checks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePrestartCheck() throws Exception {
        // Initialize the database
        prestartCheckService.save(prestartCheck);

        int databaseSizeBeforeUpdate = prestartCheckRepository.findAll().size();

        // Update the prestartCheck
        PrestartCheck updatedPrestartCheck = prestartCheckRepository.findOne(prestartCheck.getId());
        // Disconnect from session so that the updates on updatedPrestartCheck are not directly saved in db
        em.detach(updatedPrestartCheck);
        updatedPrestartCheck
            .signature(UPDATED_SIGNATURE)
            .signatureContentType(UPDATED_SIGNATURE_CONTENT_TYPE);

        restPrestartCheckMockMvc.perform(put("/api/prestart-checks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPrestartCheck)))
            .andExpect(status().isOk());

        // Validate the PrestartCheck in the database
        List<PrestartCheck> prestartCheckList = prestartCheckRepository.findAll();
        assertThat(prestartCheckList).hasSize(databaseSizeBeforeUpdate);
        PrestartCheck testPrestartCheck = prestartCheckList.get(prestartCheckList.size() - 1);
        assertThat(testPrestartCheck.getSignature()).isEqualTo(UPDATED_SIGNATURE);
        assertThat(testPrestartCheck.getSignatureContentType()).isEqualTo(UPDATED_SIGNATURE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingPrestartCheck() throws Exception {
        int databaseSizeBeforeUpdate = prestartCheckRepository.findAll().size();

        // Create the PrestartCheck

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPrestartCheckMockMvc.perform(put("/api/prestart-checks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prestartCheck)))
            .andExpect(status().isCreated());

        // Validate the PrestartCheck in the database
        List<PrestartCheck> prestartCheckList = prestartCheckRepository.findAll();
        assertThat(prestartCheckList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePrestartCheck() throws Exception {
        // Initialize the database
        prestartCheckService.save(prestartCheck);

        int databaseSizeBeforeDelete = prestartCheckRepository.findAll().size();

        // Get the prestartCheck
        restPrestartCheckMockMvc.perform(delete("/api/prestart-checks/{id}", prestartCheck.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PrestartCheck> prestartCheckList = prestartCheckRepository.findAll();
        assertThat(prestartCheckList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrestartCheck.class);
        PrestartCheck prestartCheck1 = new PrestartCheck();
        prestartCheck1.setId(1L);
        PrestartCheck prestartCheck2 = new PrestartCheck();
        prestartCheck2.setId(prestartCheck1.getId());
        assertThat(prestartCheck1).isEqualTo(prestartCheck2);
        prestartCheck2.setId(2L);
        assertThat(prestartCheck1).isNotEqualTo(prestartCheck2);
        prestartCheck1.setId(null);
        assertThat(prestartCheck1).isNotEqualTo(prestartCheck2);
    }
}
