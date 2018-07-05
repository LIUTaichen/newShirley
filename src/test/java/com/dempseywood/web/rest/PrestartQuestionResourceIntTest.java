package com.dempseywood.web.rest;

import com.dempseywood.FleetManagementApp;

import com.dempseywood.domain.PrestartQuestion;
import com.dempseywood.repository.PrestartQuestionRepository;
import com.dempseywood.service.PrestartQuestionService;
import com.dempseywood.web.rest.errors.ExceptionTranslator;
import com.dempseywood.service.dto.PrestartQuestionCriteria;
import com.dempseywood.service.PrestartQuestionQueryService;

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
 * Test class for the PrestartQuestionResource REST controller.
 *
 * @see PrestartQuestionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FleetManagementApp.class)
public class PrestartQuestionResourceIntTest {

    private static final String DEFAULT_BODY = "AAAAAAAAAA";
    private static final String UPDATED_BODY = "BBBBBBBBBB";

    @Autowired
    private PrestartQuestionRepository prestartQuestionRepository;

    @Autowired
    private PrestartQuestionService prestartQuestionService;

    @Autowired
    private PrestartQuestionQueryService prestartQuestionQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPrestartQuestionMockMvc;

    private PrestartQuestion prestartQuestion;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PrestartQuestionResource prestartQuestionResource = new PrestartQuestionResource(prestartQuestionService, prestartQuestionQueryService);
        this.restPrestartQuestionMockMvc = MockMvcBuilders.standaloneSetup(prestartQuestionResource)
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
    public static PrestartQuestion createEntity(EntityManager em) {
        PrestartQuestion prestartQuestion = new PrestartQuestion()
            .body(DEFAULT_BODY);
        return prestartQuestion;
    }

    @Before
    public void initTest() {
        prestartQuestion = createEntity(em);
    }

    @Test
    @Transactional
    public void createPrestartQuestion() throws Exception {
        int databaseSizeBeforeCreate = prestartQuestionRepository.findAll().size();

        // Create the PrestartQuestion
        restPrestartQuestionMockMvc.perform(post("/api/prestart-questions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prestartQuestion)))
            .andExpect(status().isCreated());

        // Validate the PrestartQuestion in the database
        List<PrestartQuestion> prestartQuestionList = prestartQuestionRepository.findAll();
        assertThat(prestartQuestionList).hasSize(databaseSizeBeforeCreate + 1);
        PrestartQuestion testPrestartQuestion = prestartQuestionList.get(prestartQuestionList.size() - 1);
        assertThat(testPrestartQuestion.getBody()).isEqualTo(DEFAULT_BODY);
    }

    @Test
    @Transactional
    public void createPrestartQuestionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = prestartQuestionRepository.findAll().size();

        // Create the PrestartQuestion with an existing ID
        prestartQuestion.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrestartQuestionMockMvc.perform(post("/api/prestart-questions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prestartQuestion)))
            .andExpect(status().isBadRequest());

        // Validate the PrestartQuestion in the database
        List<PrestartQuestion> prestartQuestionList = prestartQuestionRepository.findAll();
        assertThat(prestartQuestionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPrestartQuestions() throws Exception {
        // Initialize the database
        prestartQuestionRepository.saveAndFlush(prestartQuestion);

        // Get all the prestartQuestionList
        restPrestartQuestionMockMvc.perform(get("/api/prestart-questions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prestartQuestion.getId().intValue())))
            .andExpect(jsonPath("$.[*].body").value(hasItem(DEFAULT_BODY.toString())));
    }

    @Test
    @Transactional
    public void getPrestartQuestion() throws Exception {
        // Initialize the database
        prestartQuestionRepository.saveAndFlush(prestartQuestion);

        // Get the prestartQuestion
        restPrestartQuestionMockMvc.perform(get("/api/prestart-questions/{id}", prestartQuestion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(prestartQuestion.getId().intValue()))
            .andExpect(jsonPath("$.body").value(DEFAULT_BODY.toString()));
    }

    @Test
    @Transactional
    public void getAllPrestartQuestionsByBodyIsEqualToSomething() throws Exception {
        // Initialize the database
        prestartQuestionRepository.saveAndFlush(prestartQuestion);

        // Get all the prestartQuestionList where body equals to DEFAULT_BODY
        defaultPrestartQuestionShouldBeFound("body.equals=" + DEFAULT_BODY);

        // Get all the prestartQuestionList where body equals to UPDATED_BODY
        defaultPrestartQuestionShouldNotBeFound("body.equals=" + UPDATED_BODY);
    }

    @Test
    @Transactional
    public void getAllPrestartQuestionsByBodyIsInShouldWork() throws Exception {
        // Initialize the database
        prestartQuestionRepository.saveAndFlush(prestartQuestion);

        // Get all the prestartQuestionList where body in DEFAULT_BODY or UPDATED_BODY
        defaultPrestartQuestionShouldBeFound("body.in=" + DEFAULT_BODY + "," + UPDATED_BODY);

        // Get all the prestartQuestionList where body equals to UPDATED_BODY
        defaultPrestartQuestionShouldNotBeFound("body.in=" + UPDATED_BODY);
    }

    @Test
    @Transactional
    public void getAllPrestartQuestionsByBodyIsNullOrNotNull() throws Exception {
        // Initialize the database
        prestartQuestionRepository.saveAndFlush(prestartQuestion);

        // Get all the prestartQuestionList where body is not null
        defaultPrestartQuestionShouldBeFound("body.specified=true");

        // Get all the prestartQuestionList where body is null
        defaultPrestartQuestionShouldNotBeFound("body.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultPrestartQuestionShouldBeFound(String filter) throws Exception {
        restPrestartQuestionMockMvc.perform(get("/api/prestart-questions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prestartQuestion.getId().intValue())))
            .andExpect(jsonPath("$.[*].body").value(hasItem(DEFAULT_BODY.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultPrestartQuestionShouldNotBeFound(String filter) throws Exception {
        restPrestartQuestionMockMvc.perform(get("/api/prestart-questions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingPrestartQuestion() throws Exception {
        // Get the prestartQuestion
        restPrestartQuestionMockMvc.perform(get("/api/prestart-questions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePrestartQuestion() throws Exception {
        // Initialize the database
        prestartQuestionService.save(prestartQuestion);

        int databaseSizeBeforeUpdate = prestartQuestionRepository.findAll().size();

        // Update the prestartQuestion
        PrestartQuestion updatedPrestartQuestion = prestartQuestionRepository.findOne(prestartQuestion.getId());
        // Disconnect from session so that the updates on updatedPrestartQuestion are not directly saved in db
        em.detach(updatedPrestartQuestion);
        updatedPrestartQuestion
            .body(UPDATED_BODY);

        restPrestartQuestionMockMvc.perform(put("/api/prestart-questions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPrestartQuestion)))
            .andExpect(status().isOk());

        // Validate the PrestartQuestion in the database
        List<PrestartQuestion> prestartQuestionList = prestartQuestionRepository.findAll();
        assertThat(prestartQuestionList).hasSize(databaseSizeBeforeUpdate);
        PrestartQuestion testPrestartQuestion = prestartQuestionList.get(prestartQuestionList.size() - 1);
        assertThat(testPrestartQuestion.getBody()).isEqualTo(UPDATED_BODY);
    }

    @Test
    @Transactional
    public void updateNonExistingPrestartQuestion() throws Exception {
        int databaseSizeBeforeUpdate = prestartQuestionRepository.findAll().size();

        // Create the PrestartQuestion

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPrestartQuestionMockMvc.perform(put("/api/prestart-questions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prestartQuestion)))
            .andExpect(status().isCreated());

        // Validate the PrestartQuestion in the database
        List<PrestartQuestion> prestartQuestionList = prestartQuestionRepository.findAll();
        assertThat(prestartQuestionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePrestartQuestion() throws Exception {
        // Initialize the database
        prestartQuestionService.save(prestartQuestion);

        int databaseSizeBeforeDelete = prestartQuestionRepository.findAll().size();

        // Get the prestartQuestion
        restPrestartQuestionMockMvc.perform(delete("/api/prestart-questions/{id}", prestartQuestion.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PrestartQuestion> prestartQuestionList = prestartQuestionRepository.findAll();
        assertThat(prestartQuestionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrestartQuestion.class);
        PrestartQuestion prestartQuestion1 = new PrestartQuestion();
        prestartQuestion1.setId(1L);
        PrestartQuestion prestartQuestion2 = new PrestartQuestion();
        prestartQuestion2.setId(prestartQuestion1.getId());
        assertThat(prestartQuestion1).isEqualTo(prestartQuestion2);
        prestartQuestion2.setId(2L);
        assertThat(prestartQuestion1).isNotEqualTo(prestartQuestion2);
        prestartQuestion1.setId(null);
        assertThat(prestartQuestion1).isNotEqualTo(prestartQuestion2);
    }
}
