package com.dempseywood.web.rest;

import com.dempseywood.FleetManagementApp;

import com.dempseywood.domain.PrestartQuestionOption;
import com.dempseywood.domain.PrestartQuestion;
import com.dempseywood.repository.PrestartQuestionOptionRepository;
import com.dempseywood.service.PrestartQuestionOptionService;
import com.dempseywood.web.rest.errors.ExceptionTranslator;
import com.dempseywood.service.dto.PrestartQuestionOptionCriteria;
import com.dempseywood.service.PrestartQuestionOptionQueryService;

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
 * Test class for the PrestartQuestionOptionResource REST controller.
 *
 * @see PrestartQuestionOptionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FleetManagementApp.class)
public class PrestartQuestionOptionResourceIntTest {

    private static final String DEFAULT_BODY = "AAAAAAAAAA";
    private static final String UPDATED_BODY = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_NORMAL = false;
    private static final Boolean UPDATED_IS_NORMAL = true;

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    @Autowired
    private PrestartQuestionOptionRepository prestartQuestionOptionRepository;

    @Autowired
    private PrestartQuestionOptionService prestartQuestionOptionService;

    @Autowired
    private PrestartQuestionOptionQueryService prestartQuestionOptionQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPrestartQuestionOptionMockMvc;

    private PrestartQuestionOption prestartQuestionOption;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PrestartQuestionOptionResource prestartQuestionOptionResource = new PrestartQuestionOptionResource(prestartQuestionOptionService, prestartQuestionOptionQueryService);
        this.restPrestartQuestionOptionMockMvc = MockMvcBuilders.standaloneSetup(prestartQuestionOptionResource)
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
    public static PrestartQuestionOption createEntity(EntityManager em) {
        PrestartQuestionOption prestartQuestionOption = new PrestartQuestionOption()
            .body(DEFAULT_BODY)
            .isNormal(DEFAULT_IS_NORMAL)
            .isActive(DEFAULT_IS_ACTIVE);
        return prestartQuestionOption;
    }

    @Before
    public void initTest() {
        prestartQuestionOption = createEntity(em);
    }

    @Test
    @Transactional
    public void createPrestartQuestionOption() throws Exception {
        int databaseSizeBeforeCreate = prestartQuestionOptionRepository.findAll().size();

        // Create the PrestartQuestionOption
        restPrestartQuestionOptionMockMvc.perform(post("/api/prestart-question-options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prestartQuestionOption)))
            .andExpect(status().isCreated());

        // Validate the PrestartQuestionOption in the database
        List<PrestartQuestionOption> prestartQuestionOptionList = prestartQuestionOptionRepository.findAll();
        assertThat(prestartQuestionOptionList).hasSize(databaseSizeBeforeCreate + 1);
        PrestartQuestionOption testPrestartQuestionOption = prestartQuestionOptionList.get(prestartQuestionOptionList.size() - 1);
        assertThat(testPrestartQuestionOption.getBody()).isEqualTo(DEFAULT_BODY);
        assertThat(testPrestartQuestionOption.isIsNormal()).isEqualTo(DEFAULT_IS_NORMAL);
        assertThat(testPrestartQuestionOption.isIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void createPrestartQuestionOptionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = prestartQuestionOptionRepository.findAll().size();

        // Create the PrestartQuestionOption with an existing ID
        prestartQuestionOption.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrestartQuestionOptionMockMvc.perform(post("/api/prestart-question-options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prestartQuestionOption)))
            .andExpect(status().isBadRequest());

        // Validate the PrestartQuestionOption in the database
        List<PrestartQuestionOption> prestartQuestionOptionList = prestartQuestionOptionRepository.findAll();
        assertThat(prestartQuestionOptionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPrestartQuestionOptions() throws Exception {
        // Initialize the database
        prestartQuestionOptionRepository.saveAndFlush(prestartQuestionOption);

        // Get all the prestartQuestionOptionList
        restPrestartQuestionOptionMockMvc.perform(get("/api/prestart-question-options?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prestartQuestionOption.getId().intValue())))
            .andExpect(jsonPath("$.[*].body").value(hasItem(DEFAULT_BODY.toString())))
            .andExpect(jsonPath("$.[*].isNormal").value(hasItem(DEFAULT_IS_NORMAL.booleanValue())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void getPrestartQuestionOption() throws Exception {
        // Initialize the database
        prestartQuestionOptionRepository.saveAndFlush(prestartQuestionOption);

        // Get the prestartQuestionOption
        restPrestartQuestionOptionMockMvc.perform(get("/api/prestart-question-options/{id}", prestartQuestionOption.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(prestartQuestionOption.getId().intValue()))
            .andExpect(jsonPath("$.body").value(DEFAULT_BODY.toString()))
            .andExpect(jsonPath("$.isNormal").value(DEFAULT_IS_NORMAL.booleanValue()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getAllPrestartQuestionOptionsByBodyIsEqualToSomething() throws Exception {
        // Initialize the database
        prestartQuestionOptionRepository.saveAndFlush(prestartQuestionOption);

        // Get all the prestartQuestionOptionList where body equals to DEFAULT_BODY
        defaultPrestartQuestionOptionShouldBeFound("body.equals=" + DEFAULT_BODY);

        // Get all the prestartQuestionOptionList where body equals to UPDATED_BODY
        defaultPrestartQuestionOptionShouldNotBeFound("body.equals=" + UPDATED_BODY);
    }

    @Test
    @Transactional
    public void getAllPrestartQuestionOptionsByBodyIsInShouldWork() throws Exception {
        // Initialize the database
        prestartQuestionOptionRepository.saveAndFlush(prestartQuestionOption);

        // Get all the prestartQuestionOptionList where body in DEFAULT_BODY or UPDATED_BODY
        defaultPrestartQuestionOptionShouldBeFound("body.in=" + DEFAULT_BODY + "," + UPDATED_BODY);

        // Get all the prestartQuestionOptionList where body equals to UPDATED_BODY
        defaultPrestartQuestionOptionShouldNotBeFound("body.in=" + UPDATED_BODY);
    }

    @Test
    @Transactional
    public void getAllPrestartQuestionOptionsByBodyIsNullOrNotNull() throws Exception {
        // Initialize the database
        prestartQuestionOptionRepository.saveAndFlush(prestartQuestionOption);

        // Get all the prestartQuestionOptionList where body is not null
        defaultPrestartQuestionOptionShouldBeFound("body.specified=true");

        // Get all the prestartQuestionOptionList where body is null
        defaultPrestartQuestionOptionShouldNotBeFound("body.specified=false");
    }

    @Test
    @Transactional
    public void getAllPrestartQuestionOptionsByIsNormalIsEqualToSomething() throws Exception {
        // Initialize the database
        prestartQuestionOptionRepository.saveAndFlush(prestartQuestionOption);

        // Get all the prestartQuestionOptionList where isNormal equals to DEFAULT_IS_NORMAL
        defaultPrestartQuestionOptionShouldBeFound("isNormal.equals=" + DEFAULT_IS_NORMAL);

        // Get all the prestartQuestionOptionList where isNormal equals to UPDATED_IS_NORMAL
        defaultPrestartQuestionOptionShouldNotBeFound("isNormal.equals=" + UPDATED_IS_NORMAL);
    }

    @Test
    @Transactional
    public void getAllPrestartQuestionOptionsByIsNormalIsInShouldWork() throws Exception {
        // Initialize the database
        prestartQuestionOptionRepository.saveAndFlush(prestartQuestionOption);

        // Get all the prestartQuestionOptionList where isNormal in DEFAULT_IS_NORMAL or UPDATED_IS_NORMAL
        defaultPrestartQuestionOptionShouldBeFound("isNormal.in=" + DEFAULT_IS_NORMAL + "," + UPDATED_IS_NORMAL);

        // Get all the prestartQuestionOptionList where isNormal equals to UPDATED_IS_NORMAL
        defaultPrestartQuestionOptionShouldNotBeFound("isNormal.in=" + UPDATED_IS_NORMAL);
    }

    @Test
    @Transactional
    public void getAllPrestartQuestionOptionsByIsNormalIsNullOrNotNull() throws Exception {
        // Initialize the database
        prestartQuestionOptionRepository.saveAndFlush(prestartQuestionOption);

        // Get all the prestartQuestionOptionList where isNormal is not null
        defaultPrestartQuestionOptionShouldBeFound("isNormal.specified=true");

        // Get all the prestartQuestionOptionList where isNormal is null
        defaultPrestartQuestionOptionShouldNotBeFound("isNormal.specified=false");
    }

    @Test
    @Transactional
    public void getAllPrestartQuestionOptionsByIsActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        prestartQuestionOptionRepository.saveAndFlush(prestartQuestionOption);

        // Get all the prestartQuestionOptionList where isActive equals to DEFAULT_IS_ACTIVE
        defaultPrestartQuestionOptionShouldBeFound("isActive.equals=" + DEFAULT_IS_ACTIVE);

        // Get all the prestartQuestionOptionList where isActive equals to UPDATED_IS_ACTIVE
        defaultPrestartQuestionOptionShouldNotBeFound("isActive.equals=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllPrestartQuestionOptionsByIsActiveIsInShouldWork() throws Exception {
        // Initialize the database
        prestartQuestionOptionRepository.saveAndFlush(prestartQuestionOption);

        // Get all the prestartQuestionOptionList where isActive in DEFAULT_IS_ACTIVE or UPDATED_IS_ACTIVE
        defaultPrestartQuestionOptionShouldBeFound("isActive.in=" + DEFAULT_IS_ACTIVE + "," + UPDATED_IS_ACTIVE);

        // Get all the prestartQuestionOptionList where isActive equals to UPDATED_IS_ACTIVE
        defaultPrestartQuestionOptionShouldNotBeFound("isActive.in=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllPrestartQuestionOptionsByIsActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        prestartQuestionOptionRepository.saveAndFlush(prestartQuestionOption);

        // Get all the prestartQuestionOptionList where isActive is not null
        defaultPrestartQuestionOptionShouldBeFound("isActive.specified=true");

        // Get all the prestartQuestionOptionList where isActive is null
        defaultPrestartQuestionOptionShouldNotBeFound("isActive.specified=false");
    }

    @Test
    @Transactional
    public void getAllPrestartQuestionOptionsByQuestionIsEqualToSomething() throws Exception {
        // Initialize the database
        PrestartQuestion question = PrestartQuestionResourceIntTest.createEntity(em);
        em.persist(question);
        em.flush();
        prestartQuestionOption.setQuestion(question);
        prestartQuestionOptionRepository.saveAndFlush(prestartQuestionOption);
        Long questionId = question.getId();

        // Get all the prestartQuestionOptionList where question equals to questionId
        defaultPrestartQuestionOptionShouldBeFound("questionId.equals=" + questionId);

        // Get all the prestartQuestionOptionList where question equals to questionId + 1
        defaultPrestartQuestionOptionShouldNotBeFound("questionId.equals=" + (questionId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultPrestartQuestionOptionShouldBeFound(String filter) throws Exception {
        restPrestartQuestionOptionMockMvc.perform(get("/api/prestart-question-options?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prestartQuestionOption.getId().intValue())))
            .andExpect(jsonPath("$.[*].body").value(hasItem(DEFAULT_BODY.toString())))
            .andExpect(jsonPath("$.[*].isNormal").value(hasItem(DEFAULT_IS_NORMAL.booleanValue())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultPrestartQuestionOptionShouldNotBeFound(String filter) throws Exception {
        restPrestartQuestionOptionMockMvc.perform(get("/api/prestart-question-options?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingPrestartQuestionOption() throws Exception {
        // Get the prestartQuestionOption
        restPrestartQuestionOptionMockMvc.perform(get("/api/prestart-question-options/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePrestartQuestionOption() throws Exception {
        // Initialize the database
        prestartQuestionOptionService.save(prestartQuestionOption);

        int databaseSizeBeforeUpdate = prestartQuestionOptionRepository.findAll().size();

        // Update the prestartQuestionOption
        PrestartQuestionOption updatedPrestartQuestionOption = prestartQuestionOptionRepository.findOne(prestartQuestionOption.getId());
        // Disconnect from session so that the updates on updatedPrestartQuestionOption are not directly saved in db
        em.detach(updatedPrestartQuestionOption);
        updatedPrestartQuestionOption
            .body(UPDATED_BODY)
            .isNormal(UPDATED_IS_NORMAL)
            .isActive(UPDATED_IS_ACTIVE);

        restPrestartQuestionOptionMockMvc.perform(put("/api/prestart-question-options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPrestartQuestionOption)))
            .andExpect(status().isOk());

        // Validate the PrestartQuestionOption in the database
        List<PrestartQuestionOption> prestartQuestionOptionList = prestartQuestionOptionRepository.findAll();
        assertThat(prestartQuestionOptionList).hasSize(databaseSizeBeforeUpdate);
        PrestartQuestionOption testPrestartQuestionOption = prestartQuestionOptionList.get(prestartQuestionOptionList.size() - 1);
        assertThat(testPrestartQuestionOption.getBody()).isEqualTo(UPDATED_BODY);
        assertThat(testPrestartQuestionOption.isIsNormal()).isEqualTo(UPDATED_IS_NORMAL);
        assertThat(testPrestartQuestionOption.isIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingPrestartQuestionOption() throws Exception {
        int databaseSizeBeforeUpdate = prestartQuestionOptionRepository.findAll().size();

        // Create the PrestartQuestionOption

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPrestartQuestionOptionMockMvc.perform(put("/api/prestart-question-options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prestartQuestionOption)))
            .andExpect(status().isCreated());

        // Validate the PrestartQuestionOption in the database
        List<PrestartQuestionOption> prestartQuestionOptionList = prestartQuestionOptionRepository.findAll();
        assertThat(prestartQuestionOptionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePrestartQuestionOption() throws Exception {
        // Initialize the database
        prestartQuestionOptionService.save(prestartQuestionOption);

        int databaseSizeBeforeDelete = prestartQuestionOptionRepository.findAll().size();

        // Get the prestartQuestionOption
        restPrestartQuestionOptionMockMvc.perform(delete("/api/prestart-question-options/{id}", prestartQuestionOption.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PrestartQuestionOption> prestartQuestionOptionList = prestartQuestionOptionRepository.findAll();
        assertThat(prestartQuestionOptionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrestartQuestionOption.class);
        PrestartQuestionOption prestartQuestionOption1 = new PrestartQuestionOption();
        prestartQuestionOption1.setId(1L);
        PrestartQuestionOption prestartQuestionOption2 = new PrestartQuestionOption();
        prestartQuestionOption2.setId(prestartQuestionOption1.getId());
        assertThat(prestartQuestionOption1).isEqualTo(prestartQuestionOption2);
        prestartQuestionOption2.setId(2L);
        assertThat(prestartQuestionOption1).isNotEqualTo(prestartQuestionOption2);
        prestartQuestionOption1.setId(null);
        assertThat(prestartQuestionOption1).isNotEqualTo(prestartQuestionOption2);
    }
}
