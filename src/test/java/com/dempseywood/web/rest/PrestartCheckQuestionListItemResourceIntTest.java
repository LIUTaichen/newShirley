package com.dempseywood.web.rest;

import com.dempseywood.FleetManagementApp;

import com.dempseywood.domain.PrestartCheckQuestionListItem;
import com.dempseywood.domain.PrestartCheckConfig;
import com.dempseywood.domain.PrestartQuestion;
import com.dempseywood.repository.PrestartCheckQuestionListItemRepository;
import com.dempseywood.service.PrestartCheckQuestionListItemService;
import com.dempseywood.web.rest.errors.ExceptionTranslator;
import com.dempseywood.service.dto.PrestartCheckQuestionListItemCriteria;
import com.dempseywood.service.PrestartCheckQuestionListItemQueryService;

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
 * Test class for the PrestartCheckQuestionListItemResource REST controller.
 *
 * @see PrestartCheckQuestionListItemResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FleetManagementApp.class)
public class PrestartCheckQuestionListItemResourceIntTest {

    private static final Integer DEFAULT_ORDER = 1;
    private static final Integer UPDATED_ORDER = 2;

    @Autowired
    private PrestartCheckQuestionListItemRepository prestartCheckQuestionListItemRepository;

    @Autowired
    private PrestartCheckQuestionListItemService prestartCheckQuestionListItemService;

    @Autowired
    private PrestartCheckQuestionListItemQueryService prestartCheckQuestionListItemQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPrestartCheckQuestionListItemMockMvc;

    private PrestartCheckQuestionListItem prestartCheckQuestionListItem;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PrestartCheckQuestionListItemResource prestartCheckQuestionListItemResource = new PrestartCheckQuestionListItemResource(prestartCheckQuestionListItemService, prestartCheckQuestionListItemQueryService);
        this.restPrestartCheckQuestionListItemMockMvc = MockMvcBuilders.standaloneSetup(prestartCheckQuestionListItemResource)
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
    public static PrestartCheckQuestionListItem createEntity(EntityManager em) {
        PrestartCheckQuestionListItem prestartCheckQuestionListItem = new PrestartCheckQuestionListItem()
            .order(DEFAULT_ORDER);
        return prestartCheckQuestionListItem;
    }

    @Before
    public void initTest() {
        prestartCheckQuestionListItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createPrestartCheckQuestionListItem() throws Exception {
        int databaseSizeBeforeCreate = prestartCheckQuestionListItemRepository.findAll().size();

        // Create the PrestartCheckQuestionListItem
        restPrestartCheckQuestionListItemMockMvc.perform(post("/api/prestart-check-question-list-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prestartCheckQuestionListItem)))
            .andExpect(status().isCreated());

        // Validate the PrestartCheckQuestionListItem in the database
        List<PrestartCheckQuestionListItem> prestartCheckQuestionListItemList = prestartCheckQuestionListItemRepository.findAll();
        assertThat(prestartCheckQuestionListItemList).hasSize(databaseSizeBeforeCreate + 1);
        PrestartCheckQuestionListItem testPrestartCheckQuestionListItem = prestartCheckQuestionListItemList.get(prestartCheckQuestionListItemList.size() - 1);
        assertThat(testPrestartCheckQuestionListItem.getOrder()).isEqualTo(DEFAULT_ORDER);
    }

    @Test
    @Transactional
    public void createPrestartCheckQuestionListItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = prestartCheckQuestionListItemRepository.findAll().size();

        // Create the PrestartCheckQuestionListItem with an existing ID
        prestartCheckQuestionListItem.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrestartCheckQuestionListItemMockMvc.perform(post("/api/prestart-check-question-list-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prestartCheckQuestionListItem)))
            .andExpect(status().isBadRequest());

        // Validate the PrestartCheckQuestionListItem in the database
        List<PrestartCheckQuestionListItem> prestartCheckQuestionListItemList = prestartCheckQuestionListItemRepository.findAll();
        assertThat(prestartCheckQuestionListItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPrestartCheckQuestionListItems() throws Exception {
        // Initialize the database
        prestartCheckQuestionListItemRepository.saveAndFlush(prestartCheckQuestionListItem);

        // Get all the prestartCheckQuestionListItemList
        restPrestartCheckQuestionListItemMockMvc.perform(get("/api/prestart-check-question-list-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prestartCheckQuestionListItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].order").value(hasItem(DEFAULT_ORDER)));
    }

    @Test
    @Transactional
    public void getPrestartCheckQuestionListItem() throws Exception {
        // Initialize the database
        prestartCheckQuestionListItemRepository.saveAndFlush(prestartCheckQuestionListItem);

        // Get the prestartCheckQuestionListItem
        restPrestartCheckQuestionListItemMockMvc.perform(get("/api/prestart-check-question-list-items/{id}", prestartCheckQuestionListItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(prestartCheckQuestionListItem.getId().intValue()))
            .andExpect(jsonPath("$.order").value(DEFAULT_ORDER));
    }

    @Test
    @Transactional
    public void getAllPrestartCheckQuestionListItemsByOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        prestartCheckQuestionListItemRepository.saveAndFlush(prestartCheckQuestionListItem);

        // Get all the prestartCheckQuestionListItemList where order equals to DEFAULT_ORDER
        defaultPrestartCheckQuestionListItemShouldBeFound("order.equals=" + DEFAULT_ORDER);

        // Get all the prestartCheckQuestionListItemList where order equals to UPDATED_ORDER
        defaultPrestartCheckQuestionListItemShouldNotBeFound("order.equals=" + UPDATED_ORDER);
    }

    @Test
    @Transactional
    public void getAllPrestartCheckQuestionListItemsByOrderIsInShouldWork() throws Exception {
        // Initialize the database
        prestartCheckQuestionListItemRepository.saveAndFlush(prestartCheckQuestionListItem);

        // Get all the prestartCheckQuestionListItemList where order in DEFAULT_ORDER or UPDATED_ORDER
        defaultPrestartCheckQuestionListItemShouldBeFound("order.in=" + DEFAULT_ORDER + "," + UPDATED_ORDER);

        // Get all the prestartCheckQuestionListItemList where order equals to UPDATED_ORDER
        defaultPrestartCheckQuestionListItemShouldNotBeFound("order.in=" + UPDATED_ORDER);
    }

    @Test
    @Transactional
    public void getAllPrestartCheckQuestionListItemsByOrderIsNullOrNotNull() throws Exception {
        // Initialize the database
        prestartCheckQuestionListItemRepository.saveAndFlush(prestartCheckQuestionListItem);

        // Get all the prestartCheckQuestionListItemList where order is not null
        defaultPrestartCheckQuestionListItemShouldBeFound("order.specified=true");

        // Get all the prestartCheckQuestionListItemList where order is null
        defaultPrestartCheckQuestionListItemShouldNotBeFound("order.specified=false");
    }

    @Test
    @Transactional
    public void getAllPrestartCheckQuestionListItemsByOrderIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        prestartCheckQuestionListItemRepository.saveAndFlush(prestartCheckQuestionListItem);

        // Get all the prestartCheckQuestionListItemList where order greater than or equals to DEFAULT_ORDER
        defaultPrestartCheckQuestionListItemShouldBeFound("order.greaterOrEqualThan=" + DEFAULT_ORDER);

        // Get all the prestartCheckQuestionListItemList where order greater than or equals to UPDATED_ORDER
        defaultPrestartCheckQuestionListItemShouldNotBeFound("order.greaterOrEqualThan=" + UPDATED_ORDER);
    }

    @Test
    @Transactional
    public void getAllPrestartCheckQuestionListItemsByOrderIsLessThanSomething() throws Exception {
        // Initialize the database
        prestartCheckQuestionListItemRepository.saveAndFlush(prestartCheckQuestionListItem);

        // Get all the prestartCheckQuestionListItemList where order less than or equals to DEFAULT_ORDER
        defaultPrestartCheckQuestionListItemShouldNotBeFound("order.lessThan=" + DEFAULT_ORDER);

        // Get all the prestartCheckQuestionListItemList where order less than or equals to UPDATED_ORDER
        defaultPrestartCheckQuestionListItemShouldBeFound("order.lessThan=" + UPDATED_ORDER);
    }


    @Test
    @Transactional
    public void getAllPrestartCheckQuestionListItemsByPrestartCheckConfigIsEqualToSomething() throws Exception {
        // Initialize the database
        PrestartCheckConfig prestartCheckConfig = PrestartCheckConfigResourceIntTest.createEntity(em);
        em.persist(prestartCheckConfig);
        em.flush();
        prestartCheckQuestionListItem.setPrestartCheckConfig(prestartCheckConfig);
        prestartCheckQuestionListItemRepository.saveAndFlush(prestartCheckQuestionListItem);
        Long prestartCheckConfigId = prestartCheckConfig.getId();

        // Get all the prestartCheckQuestionListItemList where prestartCheckConfig equals to prestartCheckConfigId
        defaultPrestartCheckQuestionListItemShouldBeFound("prestartCheckConfigId.equals=" + prestartCheckConfigId);

        // Get all the prestartCheckQuestionListItemList where prestartCheckConfig equals to prestartCheckConfigId + 1
        defaultPrestartCheckQuestionListItemShouldNotBeFound("prestartCheckConfigId.equals=" + (prestartCheckConfigId + 1));
    }


    @Test
    @Transactional
    public void getAllPrestartCheckQuestionListItemsByQuestionIsEqualToSomething() throws Exception {
        // Initialize the database
        PrestartQuestion question = PrestartQuestionResourceIntTest.createEntity(em);
        em.persist(question);
        em.flush();
        prestartCheckQuestionListItem.setQuestion(question);
        prestartCheckQuestionListItemRepository.saveAndFlush(prestartCheckQuestionListItem);
        Long questionId = question.getId();

        // Get all the prestartCheckQuestionListItemList where question equals to questionId
        defaultPrestartCheckQuestionListItemShouldBeFound("questionId.equals=" + questionId);

        // Get all the prestartCheckQuestionListItemList where question equals to questionId + 1
        defaultPrestartCheckQuestionListItemShouldNotBeFound("questionId.equals=" + (questionId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultPrestartCheckQuestionListItemShouldBeFound(String filter) throws Exception {
        restPrestartCheckQuestionListItemMockMvc.perform(get("/api/prestart-check-question-list-items?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prestartCheckQuestionListItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].order").value(hasItem(DEFAULT_ORDER)));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultPrestartCheckQuestionListItemShouldNotBeFound(String filter) throws Exception {
        restPrestartCheckQuestionListItemMockMvc.perform(get("/api/prestart-check-question-list-items?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingPrestartCheckQuestionListItem() throws Exception {
        // Get the prestartCheckQuestionListItem
        restPrestartCheckQuestionListItemMockMvc.perform(get("/api/prestart-check-question-list-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePrestartCheckQuestionListItem() throws Exception {
        // Initialize the database
        prestartCheckQuestionListItemService.save(prestartCheckQuestionListItem);

        int databaseSizeBeforeUpdate = prestartCheckQuestionListItemRepository.findAll().size();

        // Update the prestartCheckQuestionListItem
        PrestartCheckQuestionListItem updatedPrestartCheckQuestionListItem = prestartCheckQuestionListItemRepository.findOne(prestartCheckQuestionListItem.getId());
        // Disconnect from session so that the updates on updatedPrestartCheckQuestionListItem are not directly saved in db
        em.detach(updatedPrestartCheckQuestionListItem);
        updatedPrestartCheckQuestionListItem
            .order(UPDATED_ORDER);

        restPrestartCheckQuestionListItemMockMvc.perform(put("/api/prestart-check-question-list-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPrestartCheckQuestionListItem)))
            .andExpect(status().isOk());

        // Validate the PrestartCheckQuestionListItem in the database
        List<PrestartCheckQuestionListItem> prestartCheckQuestionListItemList = prestartCheckQuestionListItemRepository.findAll();
        assertThat(prestartCheckQuestionListItemList).hasSize(databaseSizeBeforeUpdate);
        PrestartCheckQuestionListItem testPrestartCheckQuestionListItem = prestartCheckQuestionListItemList.get(prestartCheckQuestionListItemList.size() - 1);
        assertThat(testPrestartCheckQuestionListItem.getOrder()).isEqualTo(UPDATED_ORDER);
    }

    @Test
    @Transactional
    public void updateNonExistingPrestartCheckQuestionListItem() throws Exception {
        int databaseSizeBeforeUpdate = prestartCheckQuestionListItemRepository.findAll().size();

        // Create the PrestartCheckQuestionListItem

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPrestartCheckQuestionListItemMockMvc.perform(put("/api/prestart-check-question-list-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prestartCheckQuestionListItem)))
            .andExpect(status().isCreated());

        // Validate the PrestartCheckQuestionListItem in the database
        List<PrestartCheckQuestionListItem> prestartCheckQuestionListItemList = prestartCheckQuestionListItemRepository.findAll();
        assertThat(prestartCheckQuestionListItemList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePrestartCheckQuestionListItem() throws Exception {
        // Initialize the database
        prestartCheckQuestionListItemService.save(prestartCheckQuestionListItem);

        int databaseSizeBeforeDelete = prestartCheckQuestionListItemRepository.findAll().size();

        // Get the prestartCheckQuestionListItem
        restPrestartCheckQuestionListItemMockMvc.perform(delete("/api/prestart-check-question-list-items/{id}", prestartCheckQuestionListItem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PrestartCheckQuestionListItem> prestartCheckQuestionListItemList = prestartCheckQuestionListItemRepository.findAll();
        assertThat(prestartCheckQuestionListItemList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrestartCheckQuestionListItem.class);
        PrestartCheckQuestionListItem prestartCheckQuestionListItem1 = new PrestartCheckQuestionListItem();
        prestartCheckQuestionListItem1.setId(1L);
        PrestartCheckQuestionListItem prestartCheckQuestionListItem2 = new PrestartCheckQuestionListItem();
        prestartCheckQuestionListItem2.setId(prestartCheckQuestionListItem1.getId());
        assertThat(prestartCheckQuestionListItem1).isEqualTo(prestartCheckQuestionListItem2);
        prestartCheckQuestionListItem2.setId(2L);
        assertThat(prestartCheckQuestionListItem1).isNotEqualTo(prestartCheckQuestionListItem2);
        prestartCheckQuestionListItem1.setId(null);
        assertThat(prestartCheckQuestionListItem1).isNotEqualTo(prestartCheckQuestionListItem2);
    }
}
