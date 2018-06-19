package com.dempseywood.web.rest;

import com.dempseywood.FleetManagementApp;

import com.dempseywood.domain.MaintenanceContractor;
import com.dempseywood.repository.MaintenanceContractorRepository;
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
import java.util.List;

import static com.dempseywood.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MaintenanceContractorResource REST controller.
 *
 * @see MaintenanceContractorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FleetManagementApp.class)
public class MaintenanceContractorResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private MaintenanceContractorRepository maintenanceContractorRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMaintenanceContractorMockMvc;

    private MaintenanceContractor maintenanceContractor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MaintenanceContractorResource maintenanceContractorResource = new MaintenanceContractorResource(maintenanceContractorRepository);
        this.restMaintenanceContractorMockMvc = MockMvcBuilders.standaloneSetup(maintenanceContractorResource)
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
    public static MaintenanceContractor createEntity(EntityManager em) {
        MaintenanceContractor maintenanceContractor = new MaintenanceContractor()
            .name(DEFAULT_NAME);
        return maintenanceContractor;
    }

    @Before
    public void initTest() {
        maintenanceContractor = createEntity(em);
    }

    @Test
    @Transactional
    public void createMaintenanceContractor() throws Exception {
        int databaseSizeBeforeCreate = maintenanceContractorRepository.findAll().size();

        // Create the MaintenanceContractor
        restMaintenanceContractorMockMvc.perform(post("/api/maintenance-contractors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(maintenanceContractor)))
            .andExpect(status().isCreated());

        // Validate the MaintenanceContractor in the database
        List<MaintenanceContractor> maintenanceContractorList = maintenanceContractorRepository.findAll();
        assertThat(maintenanceContractorList).hasSize(databaseSizeBeforeCreate + 1);
        MaintenanceContractor testMaintenanceContractor = maintenanceContractorList.get(maintenanceContractorList.size() - 1);
        assertThat(testMaintenanceContractor.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createMaintenanceContractorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = maintenanceContractorRepository.findAll().size();

        // Create the MaintenanceContractor with an existing ID
        maintenanceContractor.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMaintenanceContractorMockMvc.perform(post("/api/maintenance-contractors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(maintenanceContractor)))
            .andExpect(status().isBadRequest());

        // Validate the MaintenanceContractor in the database
        List<MaintenanceContractor> maintenanceContractorList = maintenanceContractorRepository.findAll();
        assertThat(maintenanceContractorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMaintenanceContractors() throws Exception {
        // Initialize the database
        maintenanceContractorRepository.saveAndFlush(maintenanceContractor);

        // Get all the maintenanceContractorList
        restMaintenanceContractorMockMvc.perform(get("/api/maintenance-contractors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(maintenanceContractor.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getMaintenanceContractor() throws Exception {
        // Initialize the database
        maintenanceContractorRepository.saveAndFlush(maintenanceContractor);

        // Get the maintenanceContractor
        restMaintenanceContractorMockMvc.perform(get("/api/maintenance-contractors/{id}", maintenanceContractor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(maintenanceContractor.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMaintenanceContractor() throws Exception {
        // Get the maintenanceContractor
        restMaintenanceContractorMockMvc.perform(get("/api/maintenance-contractors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMaintenanceContractor() throws Exception {
        // Initialize the database
        maintenanceContractorRepository.saveAndFlush(maintenanceContractor);
        int databaseSizeBeforeUpdate = maintenanceContractorRepository.findAll().size();

        // Update the maintenanceContractor
        MaintenanceContractor updatedMaintenanceContractor = maintenanceContractorRepository.findOne(maintenanceContractor.getId());
        // Disconnect from session so that the updates on updatedMaintenanceContractor are not directly saved in db
        em.detach(updatedMaintenanceContractor);
        updatedMaintenanceContractor
            .name(UPDATED_NAME);

        restMaintenanceContractorMockMvc.perform(put("/api/maintenance-contractors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMaintenanceContractor)))
            .andExpect(status().isOk());

        // Validate the MaintenanceContractor in the database
        List<MaintenanceContractor> maintenanceContractorList = maintenanceContractorRepository.findAll();
        assertThat(maintenanceContractorList).hasSize(databaseSizeBeforeUpdate);
        MaintenanceContractor testMaintenanceContractor = maintenanceContractorList.get(maintenanceContractorList.size() - 1);
        assertThat(testMaintenanceContractor.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingMaintenanceContractor() throws Exception {
        int databaseSizeBeforeUpdate = maintenanceContractorRepository.findAll().size();

        // Create the MaintenanceContractor

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMaintenanceContractorMockMvc.perform(put("/api/maintenance-contractors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(maintenanceContractor)))
            .andExpect(status().isCreated());

        // Validate the MaintenanceContractor in the database
        List<MaintenanceContractor> maintenanceContractorList = maintenanceContractorRepository.findAll();
        assertThat(maintenanceContractorList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMaintenanceContractor() throws Exception {
        // Initialize the database
        maintenanceContractorRepository.saveAndFlush(maintenanceContractor);
        int databaseSizeBeforeDelete = maintenanceContractorRepository.findAll().size();

        // Get the maintenanceContractor
        restMaintenanceContractorMockMvc.perform(delete("/api/maintenance-contractors/{id}", maintenanceContractor.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MaintenanceContractor> maintenanceContractorList = maintenanceContractorRepository.findAll();
        assertThat(maintenanceContractorList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MaintenanceContractor.class);
        MaintenanceContractor maintenanceContractor1 = new MaintenanceContractor();
        maintenanceContractor1.setId(1L);
        MaintenanceContractor maintenanceContractor2 = new MaintenanceContractor();
        maintenanceContractor2.setId(maintenanceContractor1.getId());
        assertThat(maintenanceContractor1).isEqualTo(maintenanceContractor2);
        maintenanceContractor2.setId(2L);
        assertThat(maintenanceContractor1).isNotEqualTo(maintenanceContractor2);
        maintenanceContractor1.setId(null);
        assertThat(maintenanceContractor1).isNotEqualTo(maintenanceContractor2);
    }
}
