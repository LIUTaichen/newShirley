package com.dempseywood.web.rest;

import com.dempseywood.FleetManagementApp;

import com.dempseywood.domain.Niggle;
import com.dempseywood.repository.NiggleRepository;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.dempseywood.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dempseywood.domain.enumeration.Status;
import com.dempseywood.domain.enumeration.Priority;
/**
 * Test class for the NiggleResource REST controller.
 *
 * @see NiggleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FleetManagementApp.class)
public class NiggleResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Status DEFAULT_STATUS = Status.SUBMITTED;
    private static final Status UPDATED_STATUS = Status.OPEN;

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    private static final Priority DEFAULT_PRIORITY = Priority.LOW;
    private static final Priority UPDATED_PRIORITY = Priority.MEDIUM;

    private static final String DEFAULT_QUATTRA_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_QUATTRA_REFERENCE = "BBBBBBBBBB";

    private static final String DEFAULT_QUATTRA_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_QUATTRA_COMMENTS = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_OPENED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_OPENED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATE_CLOSED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_CLOSED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_INVOICE_NO = "AAAAAAAAAA";
    private static final String UPDATED_INVOICE_NO = "BBBBBBBBBB";

    @Autowired
    private NiggleRepository niggleRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restNiggleMockMvc;

    private Niggle niggle;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NiggleResource niggleResource = new NiggleResource(niggleRepository);
        this.restNiggleMockMvc = MockMvcBuilders.standaloneSetup(niggleResource)
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
    public static Niggle createEntity(EntityManager em) {
        Niggle niggle = new Niggle()
            .description(DEFAULT_DESCRIPTION)
            .status(DEFAULT_STATUS)
            .note(DEFAULT_NOTE)
            .priority(DEFAULT_PRIORITY)
            .quattraReference(DEFAULT_QUATTRA_REFERENCE)
            .quattraComments(DEFAULT_QUATTRA_COMMENTS)
            .dateOpened(DEFAULT_DATE_OPENED)
            .dateClosed(DEFAULT_DATE_CLOSED)
            .invoiceNo(DEFAULT_INVOICE_NO);
        return niggle;
    }

    @Before
    public void initTest() {
        niggle = createEntity(em);
    }

    @Test
    @Transactional
    public void createNiggle() throws Exception {
        int databaseSizeBeforeCreate = niggleRepository.findAll().size();

        // Create the Niggle
        restNiggleMockMvc.perform(post("/api/niggles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(niggle)))
            .andExpect(status().isCreated());

        // Validate the Niggle in the database
        List<Niggle> niggleList = niggleRepository.findAll();
        assertThat(niggleList).hasSize(databaseSizeBeforeCreate + 1);
        Niggle testNiggle = niggleList.get(niggleList.size() - 1);
        assertThat(testNiggle.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testNiggle.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testNiggle.getNote()).isEqualTo(DEFAULT_NOTE);
        assertThat(testNiggle.getPriority()).isEqualTo(DEFAULT_PRIORITY);
        assertThat(testNiggle.getQuattraReference()).isEqualTo(DEFAULT_QUATTRA_REFERENCE);
        assertThat(testNiggle.getQuattraComments()).isEqualTo(DEFAULT_QUATTRA_COMMENTS);
        assertThat(testNiggle.getDateOpened()).isEqualTo(DEFAULT_DATE_OPENED);
        assertThat(testNiggle.getDateClosed()).isEqualTo(DEFAULT_DATE_CLOSED);
        assertThat(testNiggle.getInvoiceNo()).isEqualTo(DEFAULT_INVOICE_NO);
    }

    @Test
    @Transactional
    public void createNiggleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = niggleRepository.findAll().size();

        // Create the Niggle with an existing ID
        niggle.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNiggleMockMvc.perform(post("/api/niggles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(niggle)))
            .andExpect(status().isBadRequest());

        // Validate the Niggle in the database
        List<Niggle> niggleList = niggleRepository.findAll();
        assertThat(niggleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllNiggles() throws Exception {
        // Initialize the database
        niggleRepository.saveAndFlush(niggle);

        // Get all the niggleList
        restNiggleMockMvc.perform(get("/api/niggles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(niggle.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY.toString())))
            .andExpect(jsonPath("$.[*].quattraReference").value(hasItem(DEFAULT_QUATTRA_REFERENCE.toString())))
            .andExpect(jsonPath("$.[*].quattraComments").value(hasItem(DEFAULT_QUATTRA_COMMENTS.toString())))
            .andExpect(jsonPath("$.[*].dateOpened").value(hasItem(DEFAULT_DATE_OPENED.toString())))
            .andExpect(jsonPath("$.[*].dateClosed").value(hasItem(DEFAULT_DATE_CLOSED.toString())))
            .andExpect(jsonPath("$.[*].invoiceNo").value(hasItem(DEFAULT_INVOICE_NO.toString())));
    }

    @Test
    @Transactional
    public void getNiggle() throws Exception {
        // Initialize the database
        niggleRepository.saveAndFlush(niggle);

        // Get the niggle
        restNiggleMockMvc.perform(get("/api/niggles/{id}", niggle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(niggle.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE.toString()))
            .andExpect(jsonPath("$.priority").value(DEFAULT_PRIORITY.toString()))
            .andExpect(jsonPath("$.quattraReference").value(DEFAULT_QUATTRA_REFERENCE.toString()))
            .andExpect(jsonPath("$.quattraComments").value(DEFAULT_QUATTRA_COMMENTS.toString()))
            .andExpect(jsonPath("$.dateOpened").value(DEFAULT_DATE_OPENED.toString()))
            .andExpect(jsonPath("$.dateClosed").value(DEFAULT_DATE_CLOSED.toString()))
            .andExpect(jsonPath("$.invoiceNo").value(DEFAULT_INVOICE_NO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingNiggle() throws Exception {
        // Get the niggle
        restNiggleMockMvc.perform(get("/api/niggles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNiggle() throws Exception {
        // Initialize the database
        niggleRepository.saveAndFlush(niggle);
        int databaseSizeBeforeUpdate = niggleRepository.findAll().size();

        // Update the niggle
        Niggle updatedNiggle = niggleRepository.findOne(niggle.getId());
        // Disconnect from session so that the updates on updatedNiggle are not directly saved in db
        em.detach(updatedNiggle);
        updatedNiggle
            .description(UPDATED_DESCRIPTION)
            .status(UPDATED_STATUS)
            .note(UPDATED_NOTE)
            .priority(UPDATED_PRIORITY)
            .quattraReference(UPDATED_QUATTRA_REFERENCE)
            .quattraComments(UPDATED_QUATTRA_COMMENTS)
            .dateOpened(UPDATED_DATE_OPENED)
            .dateClosed(UPDATED_DATE_CLOSED)
            .invoiceNo(UPDATED_INVOICE_NO);

        restNiggleMockMvc.perform(put("/api/niggles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNiggle)))
            .andExpect(status().isOk());

        // Validate the Niggle in the database
        List<Niggle> niggleList = niggleRepository.findAll();
        assertThat(niggleList).hasSize(databaseSizeBeforeUpdate);
        Niggle testNiggle = niggleList.get(niggleList.size() - 1);
        assertThat(testNiggle.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testNiggle.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testNiggle.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testNiggle.getPriority()).isEqualTo(UPDATED_PRIORITY);
        assertThat(testNiggle.getQuattraReference()).isEqualTo(UPDATED_QUATTRA_REFERENCE);
        assertThat(testNiggle.getQuattraComments()).isEqualTo(UPDATED_QUATTRA_COMMENTS);
        assertThat(testNiggle.getDateOpened()).isEqualTo(UPDATED_DATE_OPENED);
        assertThat(testNiggle.getDateClosed()).isEqualTo(UPDATED_DATE_CLOSED);
        assertThat(testNiggle.getInvoiceNo()).isEqualTo(UPDATED_INVOICE_NO);
    }

    @Test
    @Transactional
    public void updateNonExistingNiggle() throws Exception {
        int databaseSizeBeforeUpdate = niggleRepository.findAll().size();

        // Create the Niggle

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restNiggleMockMvc.perform(put("/api/niggles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(niggle)))
            .andExpect(status().isCreated());

        // Validate the Niggle in the database
        List<Niggle> niggleList = niggleRepository.findAll();
        assertThat(niggleList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteNiggle() throws Exception {
        // Initialize the database
        niggleRepository.saveAndFlush(niggle);
        int databaseSizeBeforeDelete = niggleRepository.findAll().size();

        // Get the niggle
        restNiggleMockMvc.perform(delete("/api/niggles/{id}", niggle.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Niggle> niggleList = niggleRepository.findAll();
        assertThat(niggleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Niggle.class);
        Niggle niggle1 = new Niggle();
        niggle1.setId(1L);
        Niggle niggle2 = new Niggle();
        niggle2.setId(niggle1.getId());
        assertThat(niggle1).isEqualTo(niggle2);
        niggle2.setId(2L);
        assertThat(niggle1).isNotEqualTo(niggle2);
        niggle1.setId(null);
        assertThat(niggle1).isNotEqualTo(niggle2);
    }
}
