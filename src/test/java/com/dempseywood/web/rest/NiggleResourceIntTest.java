package com.dempseywood.web.rest;

import com.dempseywood.FleetManagementApp;

import com.dempseywood.domain.Niggle;
import com.dempseywood.domain.PurchaseOrder;
import com.dempseywood.domain.Plant;
import com.dempseywood.domain.MaintenanceContractor;
import com.dempseywood.repository.NiggleRepository;
import com.dempseywood.service.NiggleService;
import com.dempseywood.web.rest.errors.ExceptionTranslator;
import com.dempseywood.service.dto.NiggleCriteria;
import com.dempseywood.service.NiggleQueryService;

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

    private static final String DEFAULT_INVOICE_NO = "AAAAAAAAAA";
    private static final String UPDATED_INVOICE_NO = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_OPENED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_OPENED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATE_CLOSED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_CLOSED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private NiggleRepository niggleRepository;

    @Autowired
    private NiggleService niggleService;

    @Autowired
    private NiggleQueryService niggleQueryService;

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
        final NiggleResource niggleResource = new NiggleResource(niggleService, niggleQueryService);
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
            .invoiceNo(DEFAULT_INVOICE_NO)
            .dateOpened(DEFAULT_DATE_OPENED)
            .dateClosed(DEFAULT_DATE_CLOSED);
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
        assertThat(testNiggle.getInvoiceNo()).isEqualTo(DEFAULT_INVOICE_NO);
        assertThat(testNiggle.getDateOpened()).isEqualTo(DEFAULT_DATE_OPENED);
        assertThat(testNiggle.getDateClosed()).isEqualTo(DEFAULT_DATE_CLOSED);
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
            .andExpect(jsonPath("$.[*].invoiceNo").value(hasItem(DEFAULT_INVOICE_NO.toString())))
            .andExpect(jsonPath("$.[*].dateOpened").value(hasItem(DEFAULT_DATE_OPENED.toString())))
            .andExpect(jsonPath("$.[*].dateClosed").value(hasItem(DEFAULT_DATE_CLOSED.toString())));
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
            .andExpect(jsonPath("$.invoiceNo").value(DEFAULT_INVOICE_NO.toString()))
            .andExpect(jsonPath("$.dateOpened").value(DEFAULT_DATE_OPENED.toString()))
            .andExpect(jsonPath("$.dateClosed").value(DEFAULT_DATE_CLOSED.toString()));
    }

    @Test
    @Transactional
    public void getAllNigglesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        niggleRepository.saveAndFlush(niggle);

        // Get all the niggleList where description equals to DEFAULT_DESCRIPTION
        defaultNiggleShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the niggleList where description equals to UPDATED_DESCRIPTION
        defaultNiggleShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllNigglesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        niggleRepository.saveAndFlush(niggle);

        // Get all the niggleList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultNiggleShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the niggleList where description equals to UPDATED_DESCRIPTION
        defaultNiggleShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllNigglesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        niggleRepository.saveAndFlush(niggle);

        // Get all the niggleList where description is not null
        defaultNiggleShouldBeFound("description.specified=true");

        // Get all the niggleList where description is null
        defaultNiggleShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    public void getAllNigglesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        niggleRepository.saveAndFlush(niggle);

        // Get all the niggleList where status equals to DEFAULT_STATUS
        defaultNiggleShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the niggleList where status equals to UPDATED_STATUS
        defaultNiggleShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllNigglesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        niggleRepository.saveAndFlush(niggle);

        // Get all the niggleList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultNiggleShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the niggleList where status equals to UPDATED_STATUS
        defaultNiggleShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllNigglesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        niggleRepository.saveAndFlush(niggle);

        // Get all the niggleList where status is not null
        defaultNiggleShouldBeFound("status.specified=true");

        // Get all the niggleList where status is null
        defaultNiggleShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllNigglesByNoteIsEqualToSomething() throws Exception {
        // Initialize the database
        niggleRepository.saveAndFlush(niggle);

        // Get all the niggleList where note equals to DEFAULT_NOTE
        defaultNiggleShouldBeFound("note.equals=" + DEFAULT_NOTE);

        // Get all the niggleList where note equals to UPDATED_NOTE
        defaultNiggleShouldNotBeFound("note.equals=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllNigglesByNoteIsInShouldWork() throws Exception {
        // Initialize the database
        niggleRepository.saveAndFlush(niggle);

        // Get all the niggleList where note in DEFAULT_NOTE or UPDATED_NOTE
        defaultNiggleShouldBeFound("note.in=" + DEFAULT_NOTE + "," + UPDATED_NOTE);

        // Get all the niggleList where note equals to UPDATED_NOTE
        defaultNiggleShouldNotBeFound("note.in=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllNigglesByNoteIsNullOrNotNull() throws Exception {
        // Initialize the database
        niggleRepository.saveAndFlush(niggle);

        // Get all the niggleList where note is not null
        defaultNiggleShouldBeFound("note.specified=true");

        // Get all the niggleList where note is null
        defaultNiggleShouldNotBeFound("note.specified=false");
    }

    @Test
    @Transactional
    public void getAllNigglesByPriorityIsEqualToSomething() throws Exception {
        // Initialize the database
        niggleRepository.saveAndFlush(niggle);

        // Get all the niggleList where priority equals to DEFAULT_PRIORITY
        defaultNiggleShouldBeFound("priority.equals=" + DEFAULT_PRIORITY);

        // Get all the niggleList where priority equals to UPDATED_PRIORITY
        defaultNiggleShouldNotBeFound("priority.equals=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    public void getAllNigglesByPriorityIsInShouldWork() throws Exception {
        // Initialize the database
        niggleRepository.saveAndFlush(niggle);

        // Get all the niggleList where priority in DEFAULT_PRIORITY or UPDATED_PRIORITY
        defaultNiggleShouldBeFound("priority.in=" + DEFAULT_PRIORITY + "," + UPDATED_PRIORITY);

        // Get all the niggleList where priority equals to UPDATED_PRIORITY
        defaultNiggleShouldNotBeFound("priority.in=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    public void getAllNigglesByPriorityIsNullOrNotNull() throws Exception {
        // Initialize the database
        niggleRepository.saveAndFlush(niggle);

        // Get all the niggleList where priority is not null
        defaultNiggleShouldBeFound("priority.specified=true");

        // Get all the niggleList where priority is null
        defaultNiggleShouldNotBeFound("priority.specified=false");
    }

    @Test
    @Transactional
    public void getAllNigglesByQuattraReferenceIsEqualToSomething() throws Exception {
        // Initialize the database
        niggleRepository.saveAndFlush(niggle);

        // Get all the niggleList where quattraReference equals to DEFAULT_QUATTRA_REFERENCE
        defaultNiggleShouldBeFound("quattraReference.equals=" + DEFAULT_QUATTRA_REFERENCE);

        // Get all the niggleList where quattraReference equals to UPDATED_QUATTRA_REFERENCE
        defaultNiggleShouldNotBeFound("quattraReference.equals=" + UPDATED_QUATTRA_REFERENCE);
    }

    @Test
    @Transactional
    public void getAllNigglesByQuattraReferenceIsInShouldWork() throws Exception {
        // Initialize the database
        niggleRepository.saveAndFlush(niggle);

        // Get all the niggleList where quattraReference in DEFAULT_QUATTRA_REFERENCE or UPDATED_QUATTRA_REFERENCE
        defaultNiggleShouldBeFound("quattraReference.in=" + DEFAULT_QUATTRA_REFERENCE + "," + UPDATED_QUATTRA_REFERENCE);

        // Get all the niggleList where quattraReference equals to UPDATED_QUATTRA_REFERENCE
        defaultNiggleShouldNotBeFound("quattraReference.in=" + UPDATED_QUATTRA_REFERENCE);
    }

    @Test
    @Transactional
    public void getAllNigglesByQuattraReferenceIsNullOrNotNull() throws Exception {
        // Initialize the database
        niggleRepository.saveAndFlush(niggle);

        // Get all the niggleList where quattraReference is not null
        defaultNiggleShouldBeFound("quattraReference.specified=true");

        // Get all the niggleList where quattraReference is null
        defaultNiggleShouldNotBeFound("quattraReference.specified=false");
    }

    @Test
    @Transactional
    public void getAllNigglesByQuattraCommentsIsEqualToSomething() throws Exception {
        // Initialize the database
        niggleRepository.saveAndFlush(niggle);

        // Get all the niggleList where quattraComments equals to DEFAULT_QUATTRA_COMMENTS
        defaultNiggleShouldBeFound("quattraComments.equals=" + DEFAULT_QUATTRA_COMMENTS);

        // Get all the niggleList where quattraComments equals to UPDATED_QUATTRA_COMMENTS
        defaultNiggleShouldNotBeFound("quattraComments.equals=" + UPDATED_QUATTRA_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllNigglesByQuattraCommentsIsInShouldWork() throws Exception {
        // Initialize the database
        niggleRepository.saveAndFlush(niggle);

        // Get all the niggleList where quattraComments in DEFAULT_QUATTRA_COMMENTS or UPDATED_QUATTRA_COMMENTS
        defaultNiggleShouldBeFound("quattraComments.in=" + DEFAULT_QUATTRA_COMMENTS + "," + UPDATED_QUATTRA_COMMENTS);

        // Get all the niggleList where quattraComments equals to UPDATED_QUATTRA_COMMENTS
        defaultNiggleShouldNotBeFound("quattraComments.in=" + UPDATED_QUATTRA_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllNigglesByQuattraCommentsIsNullOrNotNull() throws Exception {
        // Initialize the database
        niggleRepository.saveAndFlush(niggle);

        // Get all the niggleList where quattraComments is not null
        defaultNiggleShouldBeFound("quattraComments.specified=true");

        // Get all the niggleList where quattraComments is null
        defaultNiggleShouldNotBeFound("quattraComments.specified=false");
    }

    @Test
    @Transactional
    public void getAllNigglesByInvoiceNoIsEqualToSomething() throws Exception {
        // Initialize the database
        niggleRepository.saveAndFlush(niggle);

        // Get all the niggleList where invoiceNo equals to DEFAULT_INVOICE_NO
        defaultNiggleShouldBeFound("invoiceNo.equals=" + DEFAULT_INVOICE_NO);

        // Get all the niggleList where invoiceNo equals to UPDATED_INVOICE_NO
        defaultNiggleShouldNotBeFound("invoiceNo.equals=" + UPDATED_INVOICE_NO);
    }

    @Test
    @Transactional
    public void getAllNigglesByInvoiceNoIsInShouldWork() throws Exception {
        // Initialize the database
        niggleRepository.saveAndFlush(niggle);

        // Get all the niggleList where invoiceNo in DEFAULT_INVOICE_NO or UPDATED_INVOICE_NO
        defaultNiggleShouldBeFound("invoiceNo.in=" + DEFAULT_INVOICE_NO + "," + UPDATED_INVOICE_NO);

        // Get all the niggleList where invoiceNo equals to UPDATED_INVOICE_NO
        defaultNiggleShouldNotBeFound("invoiceNo.in=" + UPDATED_INVOICE_NO);
    }

    @Test
    @Transactional
    public void getAllNigglesByInvoiceNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        niggleRepository.saveAndFlush(niggle);

        // Get all the niggleList where invoiceNo is not null
        defaultNiggleShouldBeFound("invoiceNo.specified=true");

        // Get all the niggleList where invoiceNo is null
        defaultNiggleShouldNotBeFound("invoiceNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllNigglesByDateOpenedIsEqualToSomething() throws Exception {
        // Initialize the database
        niggleRepository.saveAndFlush(niggle);

        // Get all the niggleList where dateOpened equals to DEFAULT_DATE_OPENED
        defaultNiggleShouldBeFound("dateOpened.equals=" + DEFAULT_DATE_OPENED);

        // Get all the niggleList where dateOpened equals to UPDATED_DATE_OPENED
        defaultNiggleShouldNotBeFound("dateOpened.equals=" + UPDATED_DATE_OPENED);
    }

    @Test
    @Transactional
    public void getAllNigglesByDateOpenedIsInShouldWork() throws Exception {
        // Initialize the database
        niggleRepository.saveAndFlush(niggle);

        // Get all the niggleList where dateOpened in DEFAULT_DATE_OPENED or UPDATED_DATE_OPENED
        defaultNiggleShouldBeFound("dateOpened.in=" + DEFAULT_DATE_OPENED + "," + UPDATED_DATE_OPENED);

        // Get all the niggleList where dateOpened equals to UPDATED_DATE_OPENED
        defaultNiggleShouldNotBeFound("dateOpened.in=" + UPDATED_DATE_OPENED);
    }

    @Test
    @Transactional
    public void getAllNigglesByDateOpenedIsNullOrNotNull() throws Exception {
        // Initialize the database
        niggleRepository.saveAndFlush(niggle);

        // Get all the niggleList where dateOpened is not null
        defaultNiggleShouldBeFound("dateOpened.specified=true");

        // Get all the niggleList where dateOpened is null
        defaultNiggleShouldNotBeFound("dateOpened.specified=false");
    }

    @Test
    @Transactional
    public void getAllNigglesByDateClosedIsEqualToSomething() throws Exception {
        // Initialize the database
        niggleRepository.saveAndFlush(niggle);

        // Get all the niggleList where dateClosed equals to DEFAULT_DATE_CLOSED
        defaultNiggleShouldBeFound("dateClosed.equals=" + DEFAULT_DATE_CLOSED);

        // Get all the niggleList where dateClosed equals to UPDATED_DATE_CLOSED
        defaultNiggleShouldNotBeFound("dateClosed.equals=" + UPDATED_DATE_CLOSED);
    }

    @Test
    @Transactional
    public void getAllNigglesByDateClosedIsInShouldWork() throws Exception {
        // Initialize the database
        niggleRepository.saveAndFlush(niggle);

        // Get all the niggleList where dateClosed in DEFAULT_DATE_CLOSED or UPDATED_DATE_CLOSED
        defaultNiggleShouldBeFound("dateClosed.in=" + DEFAULT_DATE_CLOSED + "," + UPDATED_DATE_CLOSED);

        // Get all the niggleList where dateClosed equals to UPDATED_DATE_CLOSED
        defaultNiggleShouldNotBeFound("dateClosed.in=" + UPDATED_DATE_CLOSED);
    }

    @Test
    @Transactional
    public void getAllNigglesByDateClosedIsNullOrNotNull() throws Exception {
        // Initialize the database
        niggleRepository.saveAndFlush(niggle);

        // Get all the niggleList where dateClosed is not null
        defaultNiggleShouldBeFound("dateClosed.specified=true");

        // Get all the niggleList where dateClosed is null
        defaultNiggleShouldNotBeFound("dateClosed.specified=false");
    }

    @Test
    @Transactional
    public void getAllNigglesByPurchaseOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        PurchaseOrder purchaseOrder = PurchaseOrderResourceIntTest.createEntity(em);
        em.persist(purchaseOrder);
        em.flush();
        niggle.setPurchaseOrder(purchaseOrder);
        niggleRepository.saveAndFlush(niggle);
        Long purchaseOrderId = purchaseOrder.getId();

        // Get all the niggleList where purchaseOrder equals to purchaseOrderId
        defaultNiggleShouldBeFound("purchaseOrderId.equals=" + purchaseOrderId);

        // Get all the niggleList where purchaseOrder equals to purchaseOrderId + 1
        defaultNiggleShouldNotBeFound("purchaseOrderId.equals=" + (purchaseOrderId + 1));
    }


    @Test
    @Transactional
    public void getAllNigglesByPlantIsEqualToSomething() throws Exception {
        // Initialize the database
        Plant plant = PlantResourceIntTest.createEntity(em);
        em.persist(plant);
        em.flush();
        niggle.setPlant(plant);
        niggleRepository.saveAndFlush(niggle);
        Long plantId = plant.getId();

        // Get all the niggleList where plant equals to plantId
        defaultNiggleShouldBeFound("plantId.equals=" + plantId);

        // Get all the niggleList where plant equals to plantId + 1
        defaultNiggleShouldNotBeFound("plantId.equals=" + (plantId + 1));
    }


    @Test
    @Transactional
    public void getAllNigglesByAssignedContractorIsEqualToSomething() throws Exception {
        // Initialize the database
        MaintenanceContractor assignedContractor = MaintenanceContractorResourceIntTest.createEntity(em);
        em.persist(assignedContractor);
        em.flush();
        niggle.setAssignedContractor(assignedContractor);
        niggleRepository.saveAndFlush(niggle);
        Long assignedContractorId = assignedContractor.getId();

        // Get all the niggleList where assignedContractor equals to assignedContractorId
        defaultNiggleShouldBeFound("assignedContractorId.equals=" + assignedContractorId);

        // Get all the niggleList where assignedContractor equals to assignedContractorId + 1
        defaultNiggleShouldNotBeFound("assignedContractorId.equals=" + (assignedContractorId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultNiggleShouldBeFound(String filter) throws Exception {
        restNiggleMockMvc.perform(get("/api/niggles?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(niggle.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY.toString())))
            .andExpect(jsonPath("$.[*].quattraReference").value(hasItem(DEFAULT_QUATTRA_REFERENCE.toString())))
            .andExpect(jsonPath("$.[*].quattraComments").value(hasItem(DEFAULT_QUATTRA_COMMENTS.toString())))
            .andExpect(jsonPath("$.[*].invoiceNo").value(hasItem(DEFAULT_INVOICE_NO.toString())))
            .andExpect(jsonPath("$.[*].dateOpened").value(hasItem(DEFAULT_DATE_OPENED.toString())))
            .andExpect(jsonPath("$.[*].dateClosed").value(hasItem(DEFAULT_DATE_CLOSED.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultNiggleShouldNotBeFound(String filter) throws Exception {
        restNiggleMockMvc.perform(get("/api/niggles?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
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
        niggleService.save(niggle);

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
            .invoiceNo(UPDATED_INVOICE_NO)
            .dateOpened(UPDATED_DATE_OPENED)
            .dateClosed(UPDATED_DATE_CLOSED);

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
        assertThat(testNiggle.getInvoiceNo()).isEqualTo(UPDATED_INVOICE_NO);
        assertThat(testNiggle.getDateOpened()).isEqualTo(UPDATED_DATE_OPENED);
        assertThat(testNiggle.getDateClosed()).isEqualTo(UPDATED_DATE_CLOSED);
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
        niggleService.save(niggle);

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
