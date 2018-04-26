package com.dempseywood.web.rest;

import com.dempseywood.FleetManagementApp;

import com.dempseywood.domain.OffHireRequest;
import com.dempseywood.repository.OffHireRequestRepository;
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
 * Test class for the OffHireRequestResource REST controller.
 *
 * @see OffHireRequestResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FleetManagementApp.class)
public class OffHireRequestResourceIntTest {

    @Autowired
    private OffHireRequestRepository offHireRequestRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOffHireRequestMockMvc;

    private OffHireRequest offHireRequest;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OffHireRequestResource offHireRequestResource = new OffHireRequestResource(offHireRequestRepository);
        this.restOffHireRequestMockMvc = MockMvcBuilders.standaloneSetup(offHireRequestResource)
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
    public static OffHireRequest createEntity(EntityManager em) {
        OffHireRequest offHireRequest = new OffHireRequest();
        return offHireRequest;
    }

    @Before
    public void initTest() {
        offHireRequest = createEntity(em);
    }

    @Test
    @Transactional
    public void createOffHireRequest() throws Exception {
        int databaseSizeBeforeCreate = offHireRequestRepository.findAll().size();

        // Create the OffHireRequest
        restOffHireRequestMockMvc.perform(post("/api/off-hire-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(offHireRequest)))
            .andExpect(status().isCreated());

        // Validate the OffHireRequest in the database
        List<OffHireRequest> offHireRequestList = offHireRequestRepository.findAll();
        assertThat(offHireRequestList).hasSize(databaseSizeBeforeCreate + 1);
        OffHireRequest testOffHireRequest = offHireRequestList.get(offHireRequestList.size() - 1);
    }

    @Test
    @Transactional
    public void createOffHireRequestWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = offHireRequestRepository.findAll().size();

        // Create the OffHireRequest with an existing ID
        offHireRequest.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOffHireRequestMockMvc.perform(post("/api/off-hire-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(offHireRequest)))
            .andExpect(status().isBadRequest());

        // Validate the OffHireRequest in the database
        List<OffHireRequest> offHireRequestList = offHireRequestRepository.findAll();
        assertThat(offHireRequestList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllOffHireRequests() throws Exception {
        // Initialize the database
        offHireRequestRepository.saveAndFlush(offHireRequest);

        // Get all the offHireRequestList
        restOffHireRequestMockMvc.perform(get("/api/off-hire-requests?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(offHireRequest.getId().intValue())));
    }

    @Test
    @Transactional
    public void getOffHireRequest() throws Exception {
        // Initialize the database
        offHireRequestRepository.saveAndFlush(offHireRequest);

        // Get the offHireRequest
        restOffHireRequestMockMvc.perform(get("/api/off-hire-requests/{id}", offHireRequest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(offHireRequest.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingOffHireRequest() throws Exception {
        // Get the offHireRequest
        restOffHireRequestMockMvc.perform(get("/api/off-hire-requests/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOffHireRequest() throws Exception {
        // Initialize the database
        offHireRequestRepository.saveAndFlush(offHireRequest);
        int databaseSizeBeforeUpdate = offHireRequestRepository.findAll().size();

        // Update the offHireRequest
        OffHireRequest updatedOffHireRequest = offHireRequestRepository.findOne(offHireRequest.getId());
        // Disconnect from session so that the updates on updatedOffHireRequest are not directly saved in db
        em.detach(updatedOffHireRequest);

        restOffHireRequestMockMvc.perform(put("/api/off-hire-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOffHireRequest)))
            .andExpect(status().isOk());

        // Validate the OffHireRequest in the database
        List<OffHireRequest> offHireRequestList = offHireRequestRepository.findAll();
        assertThat(offHireRequestList).hasSize(databaseSizeBeforeUpdate);
        OffHireRequest testOffHireRequest = offHireRequestList.get(offHireRequestList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingOffHireRequest() throws Exception {
        int databaseSizeBeforeUpdate = offHireRequestRepository.findAll().size();

        // Create the OffHireRequest

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOffHireRequestMockMvc.perform(put("/api/off-hire-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(offHireRequest)))
            .andExpect(status().isCreated());

        // Validate the OffHireRequest in the database
        List<OffHireRequest> offHireRequestList = offHireRequestRepository.findAll();
        assertThat(offHireRequestList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOffHireRequest() throws Exception {
        // Initialize the database
        offHireRequestRepository.saveAndFlush(offHireRequest);
        int databaseSizeBeforeDelete = offHireRequestRepository.findAll().size();

        // Get the offHireRequest
        restOffHireRequestMockMvc.perform(delete("/api/off-hire-requests/{id}", offHireRequest.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<OffHireRequest> offHireRequestList = offHireRequestRepository.findAll();
        assertThat(offHireRequestList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OffHireRequest.class);
        OffHireRequest offHireRequest1 = new OffHireRequest();
        offHireRequest1.setId(1L);
        OffHireRequest offHireRequest2 = new OffHireRequest();
        offHireRequest2.setId(offHireRequest1.getId());
        assertThat(offHireRequest1).isEqualTo(offHireRequest2);
        offHireRequest2.setId(2L);
        assertThat(offHireRequest1).isNotEqualTo(offHireRequest2);
        offHireRequest1.setId(null);
        assertThat(offHireRequest1).isNotEqualTo(offHireRequest2);
    }
}
