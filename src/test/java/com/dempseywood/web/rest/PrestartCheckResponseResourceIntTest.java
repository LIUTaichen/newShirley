package com.dempseywood.web.rest;

import com.dempseywood.FleetManagementApp;

import com.dempseywood.domain.PrestartCheckResponse;
import com.dempseywood.domain.PrestartCheck;
import com.dempseywood.domain.PrestartQuestion;
import com.dempseywood.domain.PrestartQuestionOption;
import com.dempseywood.repository.PrestartCheckResponseRepository;
import com.dempseywood.service.PrestartCheckResponseService;
import com.dempseywood.web.rest.errors.ExceptionTranslator;
import com.dempseywood.service.dto.PrestartCheckResponseCriteria;
import com.dempseywood.service.PrestartCheckResponseQueryService;

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
 * Test class for the PrestartCheckResponseResource REST controller.
 *
 * @see PrestartCheckResponseResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FleetManagementApp.class)
public class PrestartCheckResponseResourceIntTest {

    @Autowired
    private PrestartCheckResponseRepository prestartCheckResponseRepository;

    @Autowired
    private PrestartCheckResponseService prestartCheckResponseService;

    @Autowired
    private PrestartCheckResponseQueryService prestartCheckResponseQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPrestartCheckResponseMockMvc;

    private PrestartCheckResponse prestartCheckResponse;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PrestartCheckResponseResource prestartCheckResponseResource = new PrestartCheckResponseResource(prestartCheckResponseService, prestartCheckResponseQueryService);
        this.restPrestartCheckResponseMockMvc = MockMvcBuilders.standaloneSetup(prestartCheckResponseResource)
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
    public static PrestartCheckResponse createEntity(EntityManager em) {
        PrestartCheckResponse prestartCheckResponse = new PrestartCheckResponse();
        return prestartCheckResponse;
    }

    @Before
    public void initTest() {
        prestartCheckResponse = createEntity(em);
    }

    @Test
    @Transactional
    public void createPrestartCheckResponse() throws Exception {
        int databaseSizeBeforeCreate = prestartCheckResponseRepository.findAll().size();

        // Create the PrestartCheckResponse
        restPrestartCheckResponseMockMvc.perform(post("/api/prestart-check-responses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prestartCheckResponse)))
            .andExpect(status().isCreated());

        // Validate the PrestartCheckResponse in the database
        List<PrestartCheckResponse> prestartCheckResponseList = prestartCheckResponseRepository.findAll();
        assertThat(prestartCheckResponseList).hasSize(databaseSizeBeforeCreate + 1);
        PrestartCheckResponse testPrestartCheckResponse = prestartCheckResponseList.get(prestartCheckResponseList.size() - 1);
    }

    @Test
    @Transactional
    public void createPrestartCheckResponseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = prestartCheckResponseRepository.findAll().size();

        // Create the PrestartCheckResponse with an existing ID
        prestartCheckResponse.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrestartCheckResponseMockMvc.perform(post("/api/prestart-check-responses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prestartCheckResponse)))
            .andExpect(status().isBadRequest());

        // Validate the PrestartCheckResponse in the database
        List<PrestartCheckResponse> prestartCheckResponseList = prestartCheckResponseRepository.findAll();
        assertThat(prestartCheckResponseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPrestartCheckResponses() throws Exception {
        // Initialize the database
        prestartCheckResponseRepository.saveAndFlush(prestartCheckResponse);

        // Get all the prestartCheckResponseList
        restPrestartCheckResponseMockMvc.perform(get("/api/prestart-check-responses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prestartCheckResponse.getId().intValue())));
    }

    @Test
    @Transactional
    public void getPrestartCheckResponse() throws Exception {
        // Initialize the database
        prestartCheckResponseRepository.saveAndFlush(prestartCheckResponse);

        // Get the prestartCheckResponse
        restPrestartCheckResponseMockMvc.perform(get("/api/prestart-check-responses/{id}", prestartCheckResponse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(prestartCheckResponse.getId().intValue()));
    }

    @Test
    @Transactional
    public void getAllPrestartCheckResponsesByPrestartCheckIsEqualToSomething() throws Exception {
        // Initialize the database
        PrestartCheck prestartCheck = PrestartCheckResourceIntTest.createEntity(em);
        em.persist(prestartCheck);
        em.flush();
        prestartCheckResponse.setPrestartCheck(prestartCheck);
        prestartCheckResponseRepository.saveAndFlush(prestartCheckResponse);
        Long prestartCheckId = prestartCheck.getId();

        // Get all the prestartCheckResponseList where prestartCheck equals to prestartCheckId
        defaultPrestartCheckResponseShouldBeFound("prestartCheckId.equals=" + prestartCheckId);

        // Get all the prestartCheckResponseList where prestartCheck equals to prestartCheckId + 1
        defaultPrestartCheckResponseShouldNotBeFound("prestartCheckId.equals=" + (prestartCheckId + 1));
    }


    @Test
    @Transactional
    public void getAllPrestartCheckResponsesByQuestionIsEqualToSomething() throws Exception {
        // Initialize the database
        PrestartQuestion question = PrestartQuestionResourceIntTest.createEntity(em);
        em.persist(question);
        em.flush();
        prestartCheckResponse.setQuestion(question);
        prestartCheckResponseRepository.saveAndFlush(prestartCheckResponse);
        Long questionId = question.getId();

        // Get all the prestartCheckResponseList where question equals to questionId
        defaultPrestartCheckResponseShouldBeFound("questionId.equals=" + questionId);

        // Get all the prestartCheckResponseList where question equals to questionId + 1
        defaultPrestartCheckResponseShouldNotBeFound("questionId.equals=" + (questionId + 1));
    }


    @Test
    @Transactional
    public void getAllPrestartCheckResponsesByResponseIsEqualToSomething() throws Exception {
        // Initialize the database
        PrestartQuestionOption response = PrestartQuestionOptionResourceIntTest.createEntity(em);
        em.persist(response);
        em.flush();
        prestartCheckResponse.setResponse(response);
        prestartCheckResponseRepository.saveAndFlush(prestartCheckResponse);
        Long responseId = response.getId();

        // Get all the prestartCheckResponseList where response equals to responseId
        defaultPrestartCheckResponseShouldBeFound("responseId.equals=" + responseId);

        // Get all the prestartCheckResponseList where response equals to responseId + 1
        defaultPrestartCheckResponseShouldNotBeFound("responseId.equals=" + (responseId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultPrestartCheckResponseShouldBeFound(String filter) throws Exception {
        restPrestartCheckResponseMockMvc.perform(get("/api/prestart-check-responses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prestartCheckResponse.getId().intValue())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultPrestartCheckResponseShouldNotBeFound(String filter) throws Exception {
        restPrestartCheckResponseMockMvc.perform(get("/api/prestart-check-responses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingPrestartCheckResponse() throws Exception {
        // Get the prestartCheckResponse
        restPrestartCheckResponseMockMvc.perform(get("/api/prestart-check-responses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePrestartCheckResponse() throws Exception {
        // Initialize the database
        prestartCheckResponseService.save(prestartCheckResponse);

        int databaseSizeBeforeUpdate = prestartCheckResponseRepository.findAll().size();

        // Update the prestartCheckResponse
        PrestartCheckResponse updatedPrestartCheckResponse = prestartCheckResponseRepository.findOne(prestartCheckResponse.getId());
        // Disconnect from session so that the updates on updatedPrestartCheckResponse are not directly saved in db
        em.detach(updatedPrestartCheckResponse);

        restPrestartCheckResponseMockMvc.perform(put("/api/prestart-check-responses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPrestartCheckResponse)))
            .andExpect(status().isOk());

        // Validate the PrestartCheckResponse in the database
        List<PrestartCheckResponse> prestartCheckResponseList = prestartCheckResponseRepository.findAll();
        assertThat(prestartCheckResponseList).hasSize(databaseSizeBeforeUpdate);
        PrestartCheckResponse testPrestartCheckResponse = prestartCheckResponseList.get(prestartCheckResponseList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingPrestartCheckResponse() throws Exception {
        int databaseSizeBeforeUpdate = prestartCheckResponseRepository.findAll().size();

        // Create the PrestartCheckResponse

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPrestartCheckResponseMockMvc.perform(put("/api/prestart-check-responses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prestartCheckResponse)))
            .andExpect(status().isCreated());

        // Validate the PrestartCheckResponse in the database
        List<PrestartCheckResponse> prestartCheckResponseList = prestartCheckResponseRepository.findAll();
        assertThat(prestartCheckResponseList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePrestartCheckResponse() throws Exception {
        // Initialize the database
        prestartCheckResponseService.save(prestartCheckResponse);

        int databaseSizeBeforeDelete = prestartCheckResponseRepository.findAll().size();

        // Get the prestartCheckResponse
        restPrestartCheckResponseMockMvc.perform(delete("/api/prestart-check-responses/{id}", prestartCheckResponse.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PrestartCheckResponse> prestartCheckResponseList = prestartCheckResponseRepository.findAll();
        assertThat(prestartCheckResponseList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrestartCheckResponse.class);
        PrestartCheckResponse prestartCheckResponse1 = new PrestartCheckResponse();
        prestartCheckResponse1.setId(1L);
        PrestartCheckResponse prestartCheckResponse2 = new PrestartCheckResponse();
        prestartCheckResponse2.setId(prestartCheckResponse1.getId());
        assertThat(prestartCheckResponse1).isEqualTo(prestartCheckResponse2);
        prestartCheckResponse2.setId(2L);
        assertThat(prestartCheckResponse1).isNotEqualTo(prestartCheckResponse2);
        prestartCheckResponse1.setId(null);
        assertThat(prestartCheckResponse1).isNotEqualTo(prestartCheckResponse2);
    }
}
