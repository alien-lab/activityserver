package com.alienlab.activityserver.web.rest;

import com.alienlab.activityserver.ActivityserverApp;

import com.alienlab.activityserver.domain.Activity;
import com.alienlab.activityserver.repository.ActivityRepository;
import com.alienlab.activityserver.service.ActivityService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.inject.Inject;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.alienlab.activityserver.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ActivityResource REST controller.
 *
 * @see ActivityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ActivityserverApp.class)
public class ActivityResourceIntTest {

    private static final String DEFAULT_ACT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ACT_NAME = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_ACT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ACT_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Integer DEFAULT_ACT_PLAYERCOUNT = 1;
    private static final Integer UPDATED_ACT_PLAYERCOUNT = 2;

    private static final Float DEFAULT_ACT_PRICE_1 = 1F;
    private static final Float UPDATED_ACT_PRICE_1 = 2F;

    private static final Float DEFAULT_ACT_PRICE_2 = 1F;
    private static final Float UPDATED_ACT_PRICE_2 = 2F;

    private static final String DEFAULT_ACT_DESC = "AAAAAAAAAA";
    private static final String UPDATED_ACT_DESC = "BBBBBBBBBB";

    private static final String DEFAULT_ACT_CONTACT = "AAAAAAAAAA";
    private static final String UPDATED_ACT_CONTACT = "BBBBBBBBBB";

    private static final String DEFAULT_ACT_CONTACT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_ACT_CONTACT_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_ACT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_ACT_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_ACT_IMAGE = "AAAAAAAAAA";
    private static final String UPDATED_ACT_IMAGE = "BBBBBBBBBB";

    private static final String DEFAULT_ACT_FLAG = "AAAAAAAAAA";
    private static final String UPDATED_ACT_FLAG = "BBBBBBBBBB";

    private static final String DEFAULT_ACT_QRKEY = "AAAAAAAAAA";
    private static final String UPDATED_ACT_QRKEY = "BBBBBBBBBB";

    @Inject
    private ActivityRepository activityRepository;

    @Inject
    private ActivityService activityService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restActivityMockMvc;

    private Activity activity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ActivityResource activityResource = new ActivityResource();
        ReflectionTestUtils.setField(activityResource, "activityService", activityService);
        this.restActivityMockMvc = MockMvcBuilders.standaloneSetup(activityResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Activity createEntity() {
        Activity activity = new Activity()
                .actName(DEFAULT_ACT_NAME)
                .actDate(DEFAULT_ACT_DATE)
                .actPlayercount(DEFAULT_ACT_PLAYERCOUNT)
                .actPrice1(DEFAULT_ACT_PRICE_1)
                .actPrice2(DEFAULT_ACT_PRICE_2)
                .actDesc(DEFAULT_ACT_DESC)
                .actContact(DEFAULT_ACT_CONTACT)
                .actContactPhone(DEFAULT_ACT_CONTACT_PHONE)
                .actStatus(DEFAULT_ACT_STATUS)
                .actImage(DEFAULT_ACT_IMAGE)
                .actFlag(DEFAULT_ACT_FLAG)
                .actQrkey(DEFAULT_ACT_QRKEY);
        return activity;
    }

    @Before
    public void initTest() {
        activityRepository.deleteAll();
        activity = createEntity();
    }

    @Test
    public void createActivity() throws Exception {
        int databaseSizeBeforeCreate = activityRepository.findAll().size();

        // Create the Activity

        restActivityMockMvc.perform(post("/api/activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(activity)))
            .andExpect(status().isCreated());

        // Validate the Activity in the database
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeCreate + 1);
        Activity testActivity = activityList.get(activityList.size() - 1);
        assertThat(testActivity.getActName()).isEqualTo(DEFAULT_ACT_NAME);
        assertThat(testActivity.getActDate()).isEqualTo(DEFAULT_ACT_DATE);
        assertThat(testActivity.getActPlayercount()).isEqualTo(DEFAULT_ACT_PLAYERCOUNT);
        assertThat(testActivity.getActPrice1()).isEqualTo(DEFAULT_ACT_PRICE_1);
        assertThat(testActivity.getActPrice2()).isEqualTo(DEFAULT_ACT_PRICE_2);
        assertThat(testActivity.getActDesc()).isEqualTo(DEFAULT_ACT_DESC);
        assertThat(testActivity.getActContact()).isEqualTo(DEFAULT_ACT_CONTACT);
        assertThat(testActivity.getActContactPhone()).isEqualTo(DEFAULT_ACT_CONTACT_PHONE);
        assertThat(testActivity.getActStatus()).isEqualTo(DEFAULT_ACT_STATUS);
        assertThat(testActivity.getActImage()).isEqualTo(DEFAULT_ACT_IMAGE);
        assertThat(testActivity.getActFlag()).isEqualTo(DEFAULT_ACT_FLAG);
        assertThat(testActivity.getActQrkey()).isEqualTo(DEFAULT_ACT_QRKEY);
    }

    @Test
    public void createActivityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = activityRepository.findAll().size();

        // Create the Activity with an existing ID
        Activity existingActivity = new Activity();
        existingActivity.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restActivityMockMvc.perform(post("/api/activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingActivity)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkActNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = activityRepository.findAll().size();
        // set the field null
        activity.setActName(null);

        // Create the Activity, which fails.

        restActivityMockMvc.perform(post("/api/activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(activity)))
            .andExpect(status().isBadRequest());

        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkActDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = activityRepository.findAll().size();
        // set the field null
        activity.setActDate(null);

        // Create the Activity, which fails.

        restActivityMockMvc.perform(post("/api/activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(activity)))
            .andExpect(status().isBadRequest());

        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkActFlagIsRequired() throws Exception {
        int databaseSizeBeforeTest = activityRepository.findAll().size();
        // set the field null
        activity.setActFlag(null);

        // Create the Activity, which fails.

        restActivityMockMvc.perform(post("/api/activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(activity)))
            .andExpect(status().isBadRequest());

        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllActivities() throws Exception {
        // Initialize the database
        activityRepository.save(activity);

        // Get all the activityList
        restActivityMockMvc.perform(get("/api/activities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(activity.getId())))
            .andExpect(jsonPath("$.[*].actName").value(hasItem(DEFAULT_ACT_NAME.toString())))
            .andExpect(jsonPath("$.[*].actDate").value(hasItem(sameInstant(DEFAULT_ACT_DATE))))
            .andExpect(jsonPath("$.[*].actPlayercount").value(hasItem(DEFAULT_ACT_PLAYERCOUNT)))
            .andExpect(jsonPath("$.[*].actPrice1").value(hasItem(DEFAULT_ACT_PRICE_1.doubleValue())))
            .andExpect(jsonPath("$.[*].actPrice2").value(hasItem(DEFAULT_ACT_PRICE_2.doubleValue())))
            .andExpect(jsonPath("$.[*].actDesc").value(hasItem(DEFAULT_ACT_DESC.toString())))
            .andExpect(jsonPath("$.[*].actContact").value(hasItem(DEFAULT_ACT_CONTACT.toString())))
            .andExpect(jsonPath("$.[*].actContactPhone").value(hasItem(DEFAULT_ACT_CONTACT_PHONE.toString())))
            .andExpect(jsonPath("$.[*].actStatus").value(hasItem(DEFAULT_ACT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].actImage").value(hasItem(DEFAULT_ACT_IMAGE.toString())))
            .andExpect(jsonPath("$.[*].actFlag").value(hasItem(DEFAULT_ACT_FLAG.toString())))
            .andExpect(jsonPath("$.[*].actQrkey").value(hasItem(DEFAULT_ACT_QRKEY.toString())));
    }

    @Test
    public void getActivity() throws Exception {
        // Initialize the database
        activityRepository.save(activity);

        // Get the activity
        restActivityMockMvc.perform(get("/api/activities/{id}", activity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(activity.getId()))
            .andExpect(jsonPath("$.actName").value(DEFAULT_ACT_NAME.toString()))
            .andExpect(jsonPath("$.actDate").value(sameInstant(DEFAULT_ACT_DATE)))
            .andExpect(jsonPath("$.actPlayercount").value(DEFAULT_ACT_PLAYERCOUNT))
            .andExpect(jsonPath("$.actPrice1").value(DEFAULT_ACT_PRICE_1.doubleValue()))
            .andExpect(jsonPath("$.actPrice2").value(DEFAULT_ACT_PRICE_2.doubleValue()))
            .andExpect(jsonPath("$.actDesc").value(DEFAULT_ACT_DESC.toString()))
            .andExpect(jsonPath("$.actContact").value(DEFAULT_ACT_CONTACT.toString()))
            .andExpect(jsonPath("$.actContactPhone").value(DEFAULT_ACT_CONTACT_PHONE.toString()))
            .andExpect(jsonPath("$.actStatus").value(DEFAULT_ACT_STATUS.toString()))
            .andExpect(jsonPath("$.actImage").value(DEFAULT_ACT_IMAGE.toString()))
            .andExpect(jsonPath("$.actFlag").value(DEFAULT_ACT_FLAG.toString()))
            .andExpect(jsonPath("$.actQrkey").value(DEFAULT_ACT_QRKEY.toString()));
    }

    @Test
    public void getNonExistingActivity() throws Exception {
        // Get the activity
        restActivityMockMvc.perform(get("/api/activities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateActivity() throws Exception {
        // Initialize the database
        activityService.save(activity);

        int databaseSizeBeforeUpdate = activityRepository.findAll().size();

        // Update the activity
        Activity updatedActivity = activityRepository.findOne(activity.getId());
        updatedActivity
                .actName(UPDATED_ACT_NAME)
                .actDate(UPDATED_ACT_DATE)
                .actPlayercount(UPDATED_ACT_PLAYERCOUNT)
                .actPrice1(UPDATED_ACT_PRICE_1)
                .actPrice2(UPDATED_ACT_PRICE_2)
                .actDesc(UPDATED_ACT_DESC)
                .actContact(UPDATED_ACT_CONTACT)
                .actContactPhone(UPDATED_ACT_CONTACT_PHONE)
                .actStatus(UPDATED_ACT_STATUS)
                .actImage(UPDATED_ACT_IMAGE)
                .actFlag(UPDATED_ACT_FLAG)
                .actQrkey(UPDATED_ACT_QRKEY);

        restActivityMockMvc.perform(put("/api/activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedActivity)))
            .andExpect(status().isOk());

        // Validate the Activity in the database
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeUpdate);
        Activity testActivity = activityList.get(activityList.size() - 1);
        assertThat(testActivity.getActName()).isEqualTo(UPDATED_ACT_NAME);
        assertThat(testActivity.getActDate()).isEqualTo(UPDATED_ACT_DATE);
        assertThat(testActivity.getActPlayercount()).isEqualTo(UPDATED_ACT_PLAYERCOUNT);
        assertThat(testActivity.getActPrice1()).isEqualTo(UPDATED_ACT_PRICE_1);
        assertThat(testActivity.getActPrice2()).isEqualTo(UPDATED_ACT_PRICE_2);
        assertThat(testActivity.getActDesc()).isEqualTo(UPDATED_ACT_DESC);
        assertThat(testActivity.getActContact()).isEqualTo(UPDATED_ACT_CONTACT);
        assertThat(testActivity.getActContactPhone()).isEqualTo(UPDATED_ACT_CONTACT_PHONE);
        assertThat(testActivity.getActStatus()).isEqualTo(UPDATED_ACT_STATUS);
        assertThat(testActivity.getActImage()).isEqualTo(UPDATED_ACT_IMAGE);
        assertThat(testActivity.getActFlag()).isEqualTo(UPDATED_ACT_FLAG);
        assertThat(testActivity.getActQrkey()).isEqualTo(UPDATED_ACT_QRKEY);
    }

    @Test
    public void updateNonExistingActivity() throws Exception {
        int databaseSizeBeforeUpdate = activityRepository.findAll().size();

        // Create the Activity

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restActivityMockMvc.perform(put("/api/activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(activity)))
            .andExpect(status().isCreated());

        // Validate the Activity in the database
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteActivity() throws Exception {
        // Initialize the database
        activityService.save(activity);

        int databaseSizeBeforeDelete = activityRepository.findAll().size();

        // Get the activity
        restActivityMockMvc.perform(delete("/api/activities/{id}", activity.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
