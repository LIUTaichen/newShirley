package com.dempseywood.web.rest;

import com.dempseywood.FleetManagementApp;

import com.dempseywood.domain.EmailSubscription;
import com.dempseywood.repository.EmailSubscriptionRepository;
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

import com.dempseywood.domain.enumeration.Event;
import com.dempseywood.domain.enumeration.RecipientType;
/**
 * Test class for the EmailSubscriptionResource REST controller.
 *
 * @see EmailSubscriptionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FleetManagementApp.class)
public class EmailSubscriptionResourceIntTest {

    private static final Event DEFAULT_EVENT = Event.ON_HOLD;
    private static final Event UPDATED_EVENT = Event.HIGH_PRIORITY;

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final RecipientType DEFAULT_RECIPIENT_TYPE = RecipientType.TO;
    private static final RecipientType UPDATED_RECIPIENT_TYPE = RecipientType.CC;

    @Autowired
    private EmailSubscriptionRepository emailSubscriptionRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEmailSubscriptionMockMvc;

    private EmailSubscription emailSubscription;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EmailSubscriptionResource emailSubscriptionResource = new EmailSubscriptionResource(emailSubscriptionRepository);
        this.restEmailSubscriptionMockMvc = MockMvcBuilders.standaloneSetup(emailSubscriptionResource)
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
    public static EmailSubscription createEntity(EntityManager em) {
        EmailSubscription emailSubscription = new EmailSubscription()
            .event(DEFAULT_EVENT)
            .email(DEFAULT_EMAIL)
            .recipientType(DEFAULT_RECIPIENT_TYPE);
        return emailSubscription;
    }

    @Before
    public void initTest() {
        emailSubscription = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmailSubscription() throws Exception {
        int databaseSizeBeforeCreate = emailSubscriptionRepository.findAll().size();

        // Create the EmailSubscription
        restEmailSubscriptionMockMvc.perform(post("/api/email-subscriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emailSubscription)))
            .andExpect(status().isCreated());

        // Validate the EmailSubscription in the database
        List<EmailSubscription> emailSubscriptionList = emailSubscriptionRepository.findAll();
        assertThat(emailSubscriptionList).hasSize(databaseSizeBeforeCreate + 1);
        EmailSubscription testEmailSubscription = emailSubscriptionList.get(emailSubscriptionList.size() - 1);
        assertThat(testEmailSubscription.getEvent()).isEqualTo(DEFAULT_EVENT);
        assertThat(testEmailSubscription.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testEmailSubscription.getRecipientType()).isEqualTo(DEFAULT_RECIPIENT_TYPE);
    }

    @Test
    @Transactional
    public void createEmailSubscriptionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = emailSubscriptionRepository.findAll().size();

        // Create the EmailSubscription with an existing ID
        emailSubscription.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmailSubscriptionMockMvc.perform(post("/api/email-subscriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emailSubscription)))
            .andExpect(status().isBadRequest());

        // Validate the EmailSubscription in the database
        List<EmailSubscription> emailSubscriptionList = emailSubscriptionRepository.findAll();
        assertThat(emailSubscriptionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEmailSubscriptions() throws Exception {
        // Initialize the database
        emailSubscriptionRepository.saveAndFlush(emailSubscription);

        // Get all the emailSubscriptionList
        restEmailSubscriptionMockMvc.perform(get("/api/email-subscriptions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emailSubscription.getId().intValue())))
            .andExpect(jsonPath("$.[*].event").value(hasItem(DEFAULT_EVENT.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].recipientType").value(hasItem(DEFAULT_RECIPIENT_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getEmailSubscription() throws Exception {
        // Initialize the database
        emailSubscriptionRepository.saveAndFlush(emailSubscription);

        // Get the emailSubscription
        restEmailSubscriptionMockMvc.perform(get("/api/email-subscriptions/{id}", emailSubscription.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(emailSubscription.getId().intValue()))
            .andExpect(jsonPath("$.event").value(DEFAULT_EVENT.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.recipientType").value(DEFAULT_RECIPIENT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEmailSubscription() throws Exception {
        // Get the emailSubscription
        restEmailSubscriptionMockMvc.perform(get("/api/email-subscriptions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmailSubscription() throws Exception {
        // Initialize the database
        emailSubscriptionRepository.saveAndFlush(emailSubscription);
        int databaseSizeBeforeUpdate = emailSubscriptionRepository.findAll().size();

        // Update the emailSubscription
        EmailSubscription updatedEmailSubscription = emailSubscriptionRepository.findOne(emailSubscription.getId());
        // Disconnect from session so that the updates on updatedEmailSubscription are not directly saved in db
        em.detach(updatedEmailSubscription);
        updatedEmailSubscription
            .event(UPDATED_EVENT)
            .email(UPDATED_EMAIL)
            .recipientType(UPDATED_RECIPIENT_TYPE);

        restEmailSubscriptionMockMvc.perform(put("/api/email-subscriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEmailSubscription)))
            .andExpect(status().isOk());

        // Validate the EmailSubscription in the database
        List<EmailSubscription> emailSubscriptionList = emailSubscriptionRepository.findAll();
        assertThat(emailSubscriptionList).hasSize(databaseSizeBeforeUpdate);
        EmailSubscription testEmailSubscription = emailSubscriptionList.get(emailSubscriptionList.size() - 1);
        assertThat(testEmailSubscription.getEvent()).isEqualTo(UPDATED_EVENT);
        assertThat(testEmailSubscription.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEmailSubscription.getRecipientType()).isEqualTo(UPDATED_RECIPIENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingEmailSubscription() throws Exception {
        int databaseSizeBeforeUpdate = emailSubscriptionRepository.findAll().size();

        // Create the EmailSubscription

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEmailSubscriptionMockMvc.perform(put("/api/email-subscriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emailSubscription)))
            .andExpect(status().isCreated());

        // Validate the EmailSubscription in the database
        List<EmailSubscription> emailSubscriptionList = emailSubscriptionRepository.findAll();
        assertThat(emailSubscriptionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEmailSubscription() throws Exception {
        // Initialize the database
        emailSubscriptionRepository.saveAndFlush(emailSubscription);
        int databaseSizeBeforeDelete = emailSubscriptionRepository.findAll().size();

        // Get the emailSubscription
        restEmailSubscriptionMockMvc.perform(delete("/api/email-subscriptions/{id}", emailSubscription.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EmailSubscription> emailSubscriptionList = emailSubscriptionRepository.findAll();
        assertThat(emailSubscriptionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmailSubscription.class);
        EmailSubscription emailSubscription1 = new EmailSubscription();
        emailSubscription1.setId(1L);
        EmailSubscription emailSubscription2 = new EmailSubscription();
        emailSubscription2.setId(emailSubscription1.getId());
        assertThat(emailSubscription1).isEqualTo(emailSubscription2);
        emailSubscription2.setId(2L);
        assertThat(emailSubscription1).isNotEqualTo(emailSubscription2);
        emailSubscription1.setId(null);
        assertThat(emailSubscription1).isNotEqualTo(emailSubscription2);
    }
}
