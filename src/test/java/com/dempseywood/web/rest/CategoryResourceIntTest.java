package com.dempseywood.web.rest;

import com.dempseywood.FleetManagementApp;

import com.dempseywood.domain.Category;
import com.dempseywood.repository.CategoryRepository;
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

import com.dempseywood.domain.enumeration.MaintenanceGroup;
/**
 * Test class for the CategoryResource REST controller.
 *
 * @see CategoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FleetManagementApp.class)
public class CategoryResourceIntTest {

    private static final String DEFAULT_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_TRACK_USAGE = false;
    private static final Boolean UPDATED_TRACK_USAGE = true;

    private static final Double DEFAULT_DAILY_RATE = 1D;
    private static final Double UPDATED_DAILY_RATE = 2D;

    private static final Integer DEFAULT_LOAD_CAPACITY = 1;
    private static final Integer UPDATED_LOAD_CAPACITY = 2;

    private static final Double DEFAULT_HOURLY_RATE = 1D;
    private static final Double UPDATED_HOURLY_RATE = 2D;

    private static final Boolean DEFAULT_IS_EARCH_MOVING_PLANT = false;
    private static final Boolean UPDATED_IS_EARCH_MOVING_PLANT = true;

    private static final Boolean DEFAULT_IS_TRACKED_FOR_INTERNAL_BILLING = false;
    private static final Boolean UPDATED_IS_TRACKED_FOR_INTERNAL_BILLING = true;

    private static final MaintenanceGroup DEFAULT_MAINTENANCE_GROUP = MaintenanceGroup.YELLOW_FLEET;
    private static final MaintenanceGroup UPDATED_MAINTENANCE_GROUP = MaintenanceGroup.WHITE_FLEET;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCategoryMockMvc;

    private Category category;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CategoryResource categoryResource = new CategoryResource(categoryRepository);
        this.restCategoryMockMvc = MockMvcBuilders.standaloneSetup(categoryResource)
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
    public static Category createEntity(EntityManager em) {
        Category category = new Category()
            .category(DEFAULT_CATEGORY)
            .description(DEFAULT_DESCRIPTION)
            .type(DEFAULT_TYPE)
            .trackUsage(DEFAULT_TRACK_USAGE)
            .dailyRate(DEFAULT_DAILY_RATE)
            .loadCapacity(DEFAULT_LOAD_CAPACITY)
            .hourlyRate(DEFAULT_HOURLY_RATE)
            .isEarchMovingPlant(DEFAULT_IS_EARCH_MOVING_PLANT)
            .isTrackedForInternalBilling(DEFAULT_IS_TRACKED_FOR_INTERNAL_BILLING)
            .maintenanceGroup(DEFAULT_MAINTENANCE_GROUP);
        return category;
    }

    @Before
    public void initTest() {
        category = createEntity(em);
    }

    @Test
    @Transactional
    public void createCategory() throws Exception {
        int databaseSizeBeforeCreate = categoryRepository.findAll().size();

        // Create the Category
        restCategoryMockMvc.perform(post("/api/categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(category)))
            .andExpect(status().isCreated());

        // Validate the Category in the database
        List<Category> categoryList = categoryRepository.findAll();
        assertThat(categoryList).hasSize(databaseSizeBeforeCreate + 1);
        Category testCategory = categoryList.get(categoryList.size() - 1);
        assertThat(testCategory.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testCategory.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCategory.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testCategory.isTrackUsage()).isEqualTo(DEFAULT_TRACK_USAGE);
        assertThat(testCategory.getDailyRate()).isEqualTo(DEFAULT_DAILY_RATE);
        assertThat(testCategory.getLoadCapacity()).isEqualTo(DEFAULT_LOAD_CAPACITY);
        assertThat(testCategory.getHourlyRate()).isEqualTo(DEFAULT_HOURLY_RATE);
        assertThat(testCategory.isIsEarchMovingPlant()).isEqualTo(DEFAULT_IS_EARCH_MOVING_PLANT);
        assertThat(testCategory.isIsTrackedForInternalBilling()).isEqualTo(DEFAULT_IS_TRACKED_FOR_INTERNAL_BILLING);
        assertThat(testCategory.getMaintenanceGroup()).isEqualTo(DEFAULT_MAINTENANCE_GROUP);
    }

    @Test
    @Transactional
    public void createCategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = categoryRepository.findAll().size();

        // Create the Category with an existing ID
        category.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCategoryMockMvc.perform(post("/api/categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(category)))
            .andExpect(status().isBadRequest());

        // Validate the Category in the database
        List<Category> categoryList = categoryRepository.findAll();
        assertThat(categoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCategories() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList
        restCategoryMockMvc.perform(get("/api/categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(category.getId().intValue())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].trackUsage").value(hasItem(DEFAULT_TRACK_USAGE.booleanValue())))
            .andExpect(jsonPath("$.[*].dailyRate").value(hasItem(DEFAULT_DAILY_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].loadCapacity").value(hasItem(DEFAULT_LOAD_CAPACITY)))
            .andExpect(jsonPath("$.[*].hourlyRate").value(hasItem(DEFAULT_HOURLY_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].isEarchMovingPlant").value(hasItem(DEFAULT_IS_EARCH_MOVING_PLANT.booleanValue())))
            .andExpect(jsonPath("$.[*].isTrackedForInternalBilling").value(hasItem(DEFAULT_IS_TRACKED_FOR_INTERNAL_BILLING.booleanValue())))
            .andExpect(jsonPath("$.[*].maintenanceGroup").value(hasItem(DEFAULT_MAINTENANCE_GROUP.toString())));
    }

    @Test
    @Transactional
    public void getCategory() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get the category
        restCategoryMockMvc.perform(get("/api/categories/{id}", category.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(category.getId().intValue()))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.trackUsage").value(DEFAULT_TRACK_USAGE.booleanValue()))
            .andExpect(jsonPath("$.dailyRate").value(DEFAULT_DAILY_RATE.doubleValue()))
            .andExpect(jsonPath("$.loadCapacity").value(DEFAULT_LOAD_CAPACITY))
            .andExpect(jsonPath("$.hourlyRate").value(DEFAULT_HOURLY_RATE.doubleValue()))
            .andExpect(jsonPath("$.isEarchMovingPlant").value(DEFAULT_IS_EARCH_MOVING_PLANT.booleanValue()))
            .andExpect(jsonPath("$.isTrackedForInternalBilling").value(DEFAULT_IS_TRACKED_FOR_INTERNAL_BILLING.booleanValue()))
            .andExpect(jsonPath("$.maintenanceGroup").value(DEFAULT_MAINTENANCE_GROUP.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCategory() throws Exception {
        // Get the category
        restCategoryMockMvc.perform(get("/api/categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCategory() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);
        int databaseSizeBeforeUpdate = categoryRepository.findAll().size();

        // Update the category
        Category updatedCategory = categoryRepository.findOne(category.getId());
        // Disconnect from session so that the updates on updatedCategory are not directly saved in db
        em.detach(updatedCategory);
        updatedCategory
            .category(UPDATED_CATEGORY)
            .description(UPDATED_DESCRIPTION)
            .type(UPDATED_TYPE)
            .trackUsage(UPDATED_TRACK_USAGE)
            .dailyRate(UPDATED_DAILY_RATE)
            .loadCapacity(UPDATED_LOAD_CAPACITY)
            .hourlyRate(UPDATED_HOURLY_RATE)
            .isEarchMovingPlant(UPDATED_IS_EARCH_MOVING_PLANT)
            .isTrackedForInternalBilling(UPDATED_IS_TRACKED_FOR_INTERNAL_BILLING)
            .maintenanceGroup(UPDATED_MAINTENANCE_GROUP);

        restCategoryMockMvc.perform(put("/api/categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCategory)))
            .andExpect(status().isOk());

        // Validate the Category in the database
        List<Category> categoryList = categoryRepository.findAll();
        assertThat(categoryList).hasSize(databaseSizeBeforeUpdate);
        Category testCategory = categoryList.get(categoryList.size() - 1);
        assertThat(testCategory.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testCategory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCategory.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testCategory.isTrackUsage()).isEqualTo(UPDATED_TRACK_USAGE);
        assertThat(testCategory.getDailyRate()).isEqualTo(UPDATED_DAILY_RATE);
        assertThat(testCategory.getLoadCapacity()).isEqualTo(UPDATED_LOAD_CAPACITY);
        assertThat(testCategory.getHourlyRate()).isEqualTo(UPDATED_HOURLY_RATE);
        assertThat(testCategory.isIsEarchMovingPlant()).isEqualTo(UPDATED_IS_EARCH_MOVING_PLANT);
        assertThat(testCategory.isIsTrackedForInternalBilling()).isEqualTo(UPDATED_IS_TRACKED_FOR_INTERNAL_BILLING);
        assertThat(testCategory.getMaintenanceGroup()).isEqualTo(UPDATED_MAINTENANCE_GROUP);
    }

    @Test
    @Transactional
    public void updateNonExistingCategory() throws Exception {
        int databaseSizeBeforeUpdate = categoryRepository.findAll().size();

        // Create the Category

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCategoryMockMvc.perform(put("/api/categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(category)))
            .andExpect(status().isCreated());

        // Validate the Category in the database
        List<Category> categoryList = categoryRepository.findAll();
        assertThat(categoryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCategory() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);
        int databaseSizeBeforeDelete = categoryRepository.findAll().size();

        // Get the category
        restCategoryMockMvc.perform(delete("/api/categories/{id}", category.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Category> categoryList = categoryRepository.findAll();
        assertThat(categoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Category.class);
        Category category1 = new Category();
        category1.setId(1L);
        Category category2 = new Category();
        category2.setId(category1.getId());
        assertThat(category1).isEqualTo(category2);
        category2.setId(2L);
        assertThat(category1).isNotEqualTo(category2);
        category1.setId(null);
        assertThat(category1).isNotEqualTo(category2);
    }
}
