package com.dempseywood.web.rest;

import com.dempseywood.FleetManagementApp;

import com.dempseywood.domain.PrestartCheckConfig;
import com.dempseywood.domain.PrestartCheckQuestionListItem;
import com.dempseywood.repository.PrestartCheckConfigRepository;
import com.dempseywood.service.PrestartCheckConfigService;
import com.dempseywood.web.rest.errors.ExceptionTranslator;
import com.dempseywood.service.dto.PrestartCheckConfigCriteria;
import com.dempseywood.service.PrestartCheckConfigQueryService;

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
 * Test class for the PrestartCheckConfigResource REST controller.
 *
 * @see PrestartCheckConfigResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FleetManagementApp.class)
public class PrestartCheckConfigResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private PrestartCheckConfigRepository prestartCheckConfigRepository;

    @Autowired
    private PrestartCheckConfigService prestartCheckConfigService;

    @Autowired
    private PrestartCheckConfigQueryService prestartCheckConfigQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPrestartCheckConfigMockMvc;

    private PrestartCheckConfig prestartCheckConfig;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PrestartCheckConfigResource prestartCheckConfigResource = new PrestartCheckConfigResource(prestartCheckConfigService, prestartCheckConfigQueryService);
        this.restPrestartCheckConfigMockMvc = MockMvcBuilders.standaloneSetup(prestartCheckConfigResource)
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
    public static PrestartCheckConfig createEntity(EntityManager em) {
        PrestartCheckConfig prestartCheckConfig = new PrestartCheckConfig()
            .name(DEFAULT_NAME);
        return prestartCheckConfig;
    }

    @Before
    public void initTest() {
        prestartCheckConfig = createEntity(em);
    }

    @Test
    @Transactional
    public void createPrestartCheckConfig() throws Exception {
        int databaseSizeBeforeCreate = prestartCheckConfigRepository.findAll().size();

        // Create the PrestartCheckConfig
        restPrestartCheckConfigMockMvc.perform(post("/api/prestart-check-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prestartCheckConfig)))
            .andExpect(status().isCreated());

        // Validate the PrestartCheckConfig in the database
        List<PrestartCheckConfig> prestartCheckConfigList = prestartCheckConfigRepository.findAll();
        assertThat(prestartCheckConfigList).hasSize(databaseSizeBeforeCreate + 1);
        PrestartCheckConfig testPrestartCheckConfig = prestartCheckConfigList.get(prestartCheckConfigList.size() - 1);
        assertThat(testPrestartCheckConfig.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createPrestartCheckConfigWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = prestartCheckConfigRepository.findAll().size();

        // Create the PrestartCheckConfig with an existing ID
        prestartCheckConfig.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrestartCheckConfigMockMvc.perform(post("/api/prestart-check-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prestartCheckConfig)))
            .andExpect(status().isBadRequest());

        // Validate the PrestartCheckConfig in the database
        List<PrestartCheckConfig> prestartCheckConfigList = prestartCheckConfigRepository.findAll();
        assertThat(prestartCheckConfigList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPrestartCheckConfigs() throws Exception {
        // Initialize the database
        prestartCheckConfigRepository.saveAndFlush(prestartCheckConfig);

        // Get all the prestartCheckConfigList
        restPrestartCheckConfigMockMvc.perform(get("/api/prestart-check-configs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prestartCheckConfig.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getPrestartCheckConfig() throws Exception {
        // Initialize the database
        prestartCheckConfigRepository.saveAndFlush(prestartCheckConfig);

        // Get the prestartCheckConfig
        restPrestartCheckConfigMockMvc.perform(get("/api/prestart-check-configs/{id}", prestartCheckConfig.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(prestartCheckConfig.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getAllPrestartCheckConfigsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        prestartCheckConfigRepository.saveAndFlush(prestartCheckConfig);

        // Get all the prestartCheckConfigList where name equals to DEFAULT_NAME
        defaultPrestartCheckConfigShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the prestartCheckConfigList where name equals to UPDATED_NAME
        defaultPrestartCheckConfigShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPrestartCheckConfigsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        prestartCheckConfigRepository.saveAndFlush(prestartCheckConfig);

        // Get all the prestartCheckConfigList where name in DEFAULT_NAME or UPDATED_NAME
        defaultPrestartCheckConfigShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the prestartCheckConfigList where name equals to UPDATED_NAME
        defaultPrestartCheckConfigShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPrestartCheckConfigsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        prestartCheckConfigRepository.saveAndFlush(prestartCheckConfig);

        // Get all the prestartCheckConfigList where name is not null
        defaultPrestartCheckConfigShouldBeFound("name.specified=true");

        // Get all the prestartCheckConfigList where name is null
        defaultPrestartCheckConfigShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllPrestartCheckConfigsByQuestionlistIsEqualToSomething() throws Exception {
        // Initialize the database
        PrestartCheckQuestionListItem questionlist = PrestartCheckQuestionListItemResourceIntTest.createEntity(em);
        em.persist(questionlist);
        em.flush();
        prestartCheckConfig.addQuestionlist(questionlist);
        prestartCheckConfigRepository.saveAndFlush(prestartCheckConfig);
        Long questionlistId = questionlist.getId();

        // Get all the prestartCheckConfigList where questionlist equals to questionlistId
        defaultPrestartCheckConfigShouldBeFound("questionlistId.equals=" + questionlistId);

        // Get all the prestartCheckConfigList where questionlist equals to questionlistId + 1
        defaultPrestartCheckConfigShouldNotBeFound("questionlistId.equals=" + (questionlistId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultPrestartCheckConfigShouldBeFound(String filter) throws Exception {
        restPrestartCheckConfigMockMvc.perform(get("/api/prestart-check-configs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prestartCheckConfig.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultPrestartCheckConfigShouldNotBeFound(String filter) throws Exception {
        restPrestartCheckConfigMockMvc.perform(get("/api/prestart-check-configs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingPrestartCheckConfig() throws Exception {
        // Get the prestartCheckConfig
        restPrestartCheckConfigMockMvc.perform(get("/api/prestart-check-configs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePrestartCheckConfig() throws Exception {
        // Initialize the database
        prestartCheckConfigService.save(prestartCheckConfig);

        int databaseSizeBeforeUpdate = prestartCheckConfigRepository.findAll().size();

        // Update the prestartCheckConfig
        PrestartCheckConfig updatedPrestartCheckConfig = prestartCheckConfigRepository.findOne(prestartCheckConfig.getId());
        // Disconnect from session so that the updates on updatedPrestartCheckConfig are not directly saved in db
        em.detach(updatedPrestartCheckConfig);
        updatedPrestartCheckConfig
            .name(UPDATED_NAME);

        restPrestartCheckConfigMockMvc.perform(put("/api/prestart-check-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPrestartCheckConfig)))
            .andExpect(status().isOk());

        // Validate the PrestartCheckConfig in the database
        List<PrestartCheckConfig> prestartCheckConfigList = prestartCheckConfigRepository.findAll();
        assertThat(prestartCheckConfigList).hasSize(databaseSizeBeforeUpdate);
        PrestartCheckConfig testPrestartCheckConfig = prestartCheckConfigList.get(prestartCheckConfigList.size() - 1);
        assertThat(testPrestartCheckConfig.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingPrestartCheckConfig() throws Exception {
        int databaseSizeBeforeUpdate = prestartCheckConfigRepository.findAll().size();

        // Create the PrestartCheckConfig

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPrestartCheckConfigMockMvc.perform(put("/api/prestart-check-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prestartCheckConfig)))
            .andExpect(status().isCreated());

        // Validate the PrestartCheckConfig in the database
        List<PrestartCheckConfig> prestartCheckConfigList = prestartCheckConfigRepository.findAll();
        assertThat(prestartCheckConfigList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePrestartCheckConfig() throws Exception {
        // Initialize the database
        prestartCheckConfigService.save(prestartCheckConfig);

        int databaseSizeBeforeDelete = prestartCheckConfigRepository.findAll().size();

        // Get the prestartCheckConfig
        restPrestartCheckConfigMockMvc.perform(delete("/api/prestart-check-configs/{id}", prestartCheckConfig.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PrestartCheckConfig> prestartCheckConfigList = prestartCheckConfigRepository.findAll();
        assertThat(prestartCheckConfigList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrestartCheckConfig.class);
        PrestartCheckConfig prestartCheckConfig1 = new PrestartCheckConfig();
        prestartCheckConfig1.setId(1L);
        PrestartCheckConfig prestartCheckConfig2 = new PrestartCheckConfig();
        prestartCheckConfig2.setId(prestartCheckConfig1.getId());
        assertThat(prestartCheckConfig1).isEqualTo(prestartCheckConfig2);
        prestartCheckConfig2.setId(2L);
        assertThat(prestartCheckConfig1).isNotEqualTo(prestartCheckConfig2);
        prestartCheckConfig1.setId(null);
        assertThat(prestartCheckConfig1).isNotEqualTo(prestartCheckConfig2);
    }
}
