package com.dempseywood.web.rest;

import com.dempseywood.FleetManagementApp;

import com.dempseywood.domain.Competency;
import com.dempseywood.repository.CompetencyRepository;
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
 * Test class for the CompetencyResource REST controller.
 *
 * @see CompetencyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FleetManagementApp.class)
public class CompetencyResourceIntTest {

    private static final String DEFAULT_COMPETENCY = "AAAAAAAAAA";
    private static final String UPDATED_COMPETENCY = "BBBBBBBBBB";

    private static final String DEFAULT_GROUPING = "AAAAAAAAAA";
    private static final String UPDATED_GROUPING = "BBBBBBBBBB";

    private static final Integer DEFAULT_SORT_ORDER = 1;
    private static final Integer UPDATED_SORT_ORDER = 2;

    @Autowired
    private CompetencyRepository competencyRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCompetencyMockMvc;

    private Competency competency;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CompetencyResource competencyResource = new CompetencyResource(competencyRepository);
        this.restCompetencyMockMvc = MockMvcBuilders.standaloneSetup(competencyResource)
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
    public static Competency createEntity(EntityManager em) {
        Competency competency = new Competency()
            .competency(DEFAULT_COMPETENCY)
            .grouping(DEFAULT_GROUPING)
            .sortOrder(DEFAULT_SORT_ORDER);
        return competency;
    }

    @Before
    public void initTest() {
        competency = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompetency() throws Exception {
        int databaseSizeBeforeCreate = competencyRepository.findAll().size();

        // Create the Competency
        restCompetencyMockMvc.perform(post("/api/competencies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(competency)))
            .andExpect(status().isCreated());

        // Validate the Competency in the database
        List<Competency> competencyList = competencyRepository.findAll();
        assertThat(competencyList).hasSize(databaseSizeBeforeCreate + 1);
        Competency testCompetency = competencyList.get(competencyList.size() - 1);
        assertThat(testCompetency.getCompetency()).isEqualTo(DEFAULT_COMPETENCY);
        assertThat(testCompetency.getGrouping()).isEqualTo(DEFAULT_GROUPING);
        assertThat(testCompetency.getSortOrder()).isEqualTo(DEFAULT_SORT_ORDER);
    }

    @Test
    @Transactional
    public void createCompetencyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = competencyRepository.findAll().size();

        // Create the Competency with an existing ID
        competency.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompetencyMockMvc.perform(post("/api/competencies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(competency)))
            .andExpect(status().isBadRequest());

        // Validate the Competency in the database
        List<Competency> competencyList = competencyRepository.findAll();
        assertThat(competencyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCompetencies() throws Exception {
        // Initialize the database
        competencyRepository.saveAndFlush(competency);

        // Get all the competencyList
        restCompetencyMockMvc.perform(get("/api/competencies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(competency.getId().intValue())))
            .andExpect(jsonPath("$.[*].competency").value(hasItem(DEFAULT_COMPETENCY.toString())))
            .andExpect(jsonPath("$.[*].grouping").value(hasItem(DEFAULT_GROUPING.toString())))
            .andExpect(jsonPath("$.[*].sortOrder").value(hasItem(DEFAULT_SORT_ORDER)));
    }

    @Test
    @Transactional
    public void getCompetency() throws Exception {
        // Initialize the database
        competencyRepository.saveAndFlush(competency);

        // Get the competency
        restCompetencyMockMvc.perform(get("/api/competencies/{id}", competency.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(competency.getId().intValue()))
            .andExpect(jsonPath("$.competency").value(DEFAULT_COMPETENCY.toString()))
            .andExpect(jsonPath("$.grouping").value(DEFAULT_GROUPING.toString()))
            .andExpect(jsonPath("$.sortOrder").value(DEFAULT_SORT_ORDER));
    }

    @Test
    @Transactional
    public void getNonExistingCompetency() throws Exception {
        // Get the competency
        restCompetencyMockMvc.perform(get("/api/competencies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompetency() throws Exception {
        // Initialize the database
        competencyRepository.saveAndFlush(competency);
        int databaseSizeBeforeUpdate = competencyRepository.findAll().size();

        // Update the competency
        Competency updatedCompetency = competencyRepository.findOne(competency.getId());
        // Disconnect from session so that the updates on updatedCompetency are not directly saved in db
        em.detach(updatedCompetency);
        updatedCompetency
            .competency(UPDATED_COMPETENCY)
            .grouping(UPDATED_GROUPING)
            .sortOrder(UPDATED_SORT_ORDER);

        restCompetencyMockMvc.perform(put("/api/competencies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCompetency)))
            .andExpect(status().isOk());

        // Validate the Competency in the database
        List<Competency> competencyList = competencyRepository.findAll();
        assertThat(competencyList).hasSize(databaseSizeBeforeUpdate);
        Competency testCompetency = competencyList.get(competencyList.size() - 1);
        assertThat(testCompetency.getCompetency()).isEqualTo(UPDATED_COMPETENCY);
        assertThat(testCompetency.getGrouping()).isEqualTo(UPDATED_GROUPING);
        assertThat(testCompetency.getSortOrder()).isEqualTo(UPDATED_SORT_ORDER);
    }

    @Test
    @Transactional
    public void updateNonExistingCompetency() throws Exception {
        int databaseSizeBeforeUpdate = competencyRepository.findAll().size();

        // Create the Competency

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCompetencyMockMvc.perform(put("/api/competencies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(competency)))
            .andExpect(status().isCreated());

        // Validate the Competency in the database
        List<Competency> competencyList = competencyRepository.findAll();
        assertThat(competencyList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCompetency() throws Exception {
        // Initialize the database
        competencyRepository.saveAndFlush(competency);
        int databaseSizeBeforeDelete = competencyRepository.findAll().size();

        // Get the competency
        restCompetencyMockMvc.perform(delete("/api/competencies/{id}", competency.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Competency> competencyList = competencyRepository.findAll();
        assertThat(competencyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Competency.class);
        Competency competency1 = new Competency();
        competency1.setId(1L);
        Competency competency2 = new Competency();
        competency2.setId(competency1.getId());
        assertThat(competency1).isEqualTo(competency2);
        competency2.setId(2L);
        assertThat(competency1).isNotEqualTo(competency2);
        competency1.setId(null);
        assertThat(competency1).isNotEqualTo(competency2);
    }
}
