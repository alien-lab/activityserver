package com.alienlab.activityserver.web.rest;

import com.alienlab.activityserver.ActivityserverApp;

import com.alienlab.activityserver.domain.JoinList;
import com.alienlab.activityserver.repository.JoinListRepository;
import com.alienlab.activityserver.service.JoinListService;

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
 * Test class for the JoinListResource REST controller.
 *
 * @see JoinListResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ActivityserverApp.class)
public class JoinListResourceIntTest {

    private static final String DEFAULT_JOIN_NAME = "AAAAAAAAAA";
    private static final String UPDATED_JOIN_NAME = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_JOIN_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_JOIN_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_JOIN_OPENID = "AAAAAAAAAA";
    private static final String UPDATED_JOIN_OPENID = "BBBBBBBBBB";

    private static final String DEFAULT_JOIN_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_JOIN_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_JOIN_NICK = "AAAAAAAAAA";
    private static final String UPDATED_JOIN_NICK = "BBBBBBBBBB";

    private static final String DEFAULT_JOIN_ICON = "AAAAAAAAAA";
    private static final String UPDATED_JOIN_ICON = "BBBBBBBBBB";

    private static final String DEFAULT_JOIN_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_JOIN_STATUS = "BBBBBBBBBB";

    private static final Float DEFAULT_JOIN_PRICE_1 = 1F;
    private static final Float UPDATED_JOIN_PRICE_1 = 2F;

    private static final Float DEFAULT_JOIN_PRICE_2 = 1F;
    private static final Float UPDATED_JOIN_PRICE_2 = 2F;

    private static final String DEFAULT_JOIN_ENTERCODE = "AAAAAAAAAA";
    private static final String UPDATED_JOIN_ENTERCODE = "BBBBBBBBBB";

    private static final String DEFAULT_ACTIVITY = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVITY = "BBBBBBBBBB";

    private static final String DEFAULT_JOIN_FORM = "AAAAAAAAAA";
    private static final String UPDATED_JOIN_FORM = "BBBBBBBBBB";

    private static final String DEFAULT_ORDER_NO = "AAAAAAAAAA";
    private static final String UPDATED_ORDER_NO = "BBBBBBBBBB";

    @Inject
    private JoinListRepository joinListRepository;

    @Inject
    private JoinListService joinListService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restJoinListMockMvc;

    private JoinList joinList;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        JoinListResource joinListResource = new JoinListResource();
        ReflectionTestUtils.setField(joinListResource, "joinListService", joinListService);
        this.restJoinListMockMvc = MockMvcBuilders.standaloneSetup(joinListResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JoinList createEntity() {
        JoinList joinList = new JoinList()
                .joinName(DEFAULT_JOIN_NAME)
                .joinTime(DEFAULT_JOIN_TIME)
                .joinOpenid(DEFAULT_JOIN_OPENID)
                .joinPhone(DEFAULT_JOIN_PHONE)
                .joinNick(DEFAULT_JOIN_NICK)
                .joinIcon(DEFAULT_JOIN_ICON)
                .joinStatus(DEFAULT_JOIN_STATUS)
                .joinPrice1(DEFAULT_JOIN_PRICE_1)
                .joinPrice2(DEFAULT_JOIN_PRICE_2)
                .joinEntercode(DEFAULT_JOIN_ENTERCODE)
                .activity(DEFAULT_ACTIVITY)
                .joinForm(DEFAULT_JOIN_FORM)
                .orderNo(DEFAULT_ORDER_NO);
        return joinList;
    }

    @Before
    public void initTest() {
        joinListRepository.deleteAll();
        joinList = createEntity();
    }

    @Test
    public void createJoinList() throws Exception {
        int databaseSizeBeforeCreate = joinListRepository.findAll().size();

        // Create the JoinList

        restJoinListMockMvc.perform(post("/api/join-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(joinList)))
            .andExpect(status().isCreated());

        // Validate the JoinList in the database
        List<JoinList> joinListList = joinListRepository.findAll();
        assertThat(joinListList).hasSize(databaseSizeBeforeCreate + 1);
        JoinList testJoinList = joinListList.get(joinListList.size() - 1);
        assertThat(testJoinList.getJoinName()).isEqualTo(DEFAULT_JOIN_NAME);
        assertThat(testJoinList.getJoinTime()).isEqualTo(DEFAULT_JOIN_TIME);
        assertThat(testJoinList.getJoinOpenid()).isEqualTo(DEFAULT_JOIN_OPENID);
        assertThat(testJoinList.getJoinPhone()).isEqualTo(DEFAULT_JOIN_PHONE);
        assertThat(testJoinList.getJoinNick()).isEqualTo(DEFAULT_JOIN_NICK);
        assertThat(testJoinList.getJoinIcon()).isEqualTo(DEFAULT_JOIN_ICON);
        assertThat(testJoinList.getJoinStatus()).isEqualTo(DEFAULT_JOIN_STATUS);
        assertThat(testJoinList.getJoinPrice1()).isEqualTo(DEFAULT_JOIN_PRICE_1);
        assertThat(testJoinList.getJoinPrice2()).isEqualTo(DEFAULT_JOIN_PRICE_2);
        assertThat(testJoinList.getJoinEntercode()).isEqualTo(DEFAULT_JOIN_ENTERCODE);
        assertThat(testJoinList.getActivity()).isEqualTo(DEFAULT_ACTIVITY);
        assertThat(testJoinList.getJoinForm()).isEqualTo(DEFAULT_JOIN_FORM);
        assertThat(testJoinList.getOrderNo()).isEqualTo(DEFAULT_ORDER_NO);
    }

    @Test
    public void createJoinListWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = joinListRepository.findAll().size();

        // Create the JoinList with an existing ID
        JoinList existingJoinList = new JoinList();
        existingJoinList.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restJoinListMockMvc.perform(post("/api/join-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingJoinList)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<JoinList> joinListList = joinListRepository.findAll();
        assertThat(joinListList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkJoinNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = joinListRepository.findAll().size();
        // set the field null
        joinList.setJoinName(null);

        // Create the JoinList, which fails.

        restJoinListMockMvc.perform(post("/api/join-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(joinList)))
            .andExpect(status().isBadRequest());

        List<JoinList> joinListList = joinListRepository.findAll();
        assertThat(joinListList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllJoinLists() throws Exception {
        // Initialize the database
        joinListRepository.save(joinList);

        // Get all the joinListList
        restJoinListMockMvc.perform(get("/api/join-lists?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(joinList.getId())))
            .andExpect(jsonPath("$.[*].joinName").value(hasItem(DEFAULT_JOIN_NAME.toString())))
            .andExpect(jsonPath("$.[*].joinTime").value(hasItem(sameInstant(DEFAULT_JOIN_TIME))))
            .andExpect(jsonPath("$.[*].joinOpenid").value(hasItem(DEFAULT_JOIN_OPENID.toString())))
            .andExpect(jsonPath("$.[*].joinPhone").value(hasItem(DEFAULT_JOIN_PHONE.toString())))
            .andExpect(jsonPath("$.[*].joinNick").value(hasItem(DEFAULT_JOIN_NICK.toString())))
            .andExpect(jsonPath("$.[*].joinIcon").value(hasItem(DEFAULT_JOIN_ICON.toString())))
            .andExpect(jsonPath("$.[*].joinStatus").value(hasItem(DEFAULT_JOIN_STATUS.toString())))
            .andExpect(jsonPath("$.[*].joinPrice1").value(hasItem(DEFAULT_JOIN_PRICE_1.doubleValue())))
            .andExpect(jsonPath("$.[*].joinPrice2").value(hasItem(DEFAULT_JOIN_PRICE_2.doubleValue())))
            .andExpect(jsonPath("$.[*].joinEntercode").value(hasItem(DEFAULT_JOIN_ENTERCODE.toString())))
            .andExpect(jsonPath("$.[*].activity").value(hasItem(DEFAULT_ACTIVITY.toString())))
            .andExpect(jsonPath("$.[*].joinForm").value(hasItem(DEFAULT_JOIN_FORM.toString())))
            .andExpect(jsonPath("$.[*].orderNo").value(hasItem(DEFAULT_ORDER_NO.toString())));
    }

    @Test
    public void getJoinList() throws Exception {
        // Initialize the database
        joinListRepository.save(joinList);

        // Get the joinList
        restJoinListMockMvc.perform(get("/api/join-lists/{id}", joinList.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(joinList.getId()))
            .andExpect(jsonPath("$.joinName").value(DEFAULT_JOIN_NAME.toString()))
            .andExpect(jsonPath("$.joinTime").value(sameInstant(DEFAULT_JOIN_TIME)))
            .andExpect(jsonPath("$.joinOpenid").value(DEFAULT_JOIN_OPENID.toString()))
            .andExpect(jsonPath("$.joinPhone").value(DEFAULT_JOIN_PHONE.toString()))
            .andExpect(jsonPath("$.joinNick").value(DEFAULT_JOIN_NICK.toString()))
            .andExpect(jsonPath("$.joinIcon").value(DEFAULT_JOIN_ICON.toString()))
            .andExpect(jsonPath("$.joinStatus").value(DEFAULT_JOIN_STATUS.toString()))
            .andExpect(jsonPath("$.joinPrice1").value(DEFAULT_JOIN_PRICE_1.doubleValue()))
            .andExpect(jsonPath("$.joinPrice2").value(DEFAULT_JOIN_PRICE_2.doubleValue()))
            .andExpect(jsonPath("$.joinEntercode").value(DEFAULT_JOIN_ENTERCODE.toString()))
            .andExpect(jsonPath("$.activity").value(DEFAULT_ACTIVITY.toString()))
            .andExpect(jsonPath("$.joinForm").value(DEFAULT_JOIN_FORM.toString()))
            .andExpect(jsonPath("$.orderNo").value(DEFAULT_ORDER_NO.toString()));
    }

    @Test
    public void getNonExistingJoinList() throws Exception {
        // Get the joinList
        restJoinListMockMvc.perform(get("/api/join-lists/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateJoinList() throws Exception {
        // Initialize the database
        joinListService.save(joinList);

        int databaseSizeBeforeUpdate = joinListRepository.findAll().size();

        // Update the joinList
        JoinList updatedJoinList = joinListRepository.findOne(joinList.getId());
        updatedJoinList
                .joinName(UPDATED_JOIN_NAME)
                .joinTime(UPDATED_JOIN_TIME)
                .joinOpenid(UPDATED_JOIN_OPENID)
                .joinPhone(UPDATED_JOIN_PHONE)
                .joinNick(UPDATED_JOIN_NICK)
                .joinIcon(UPDATED_JOIN_ICON)
                .joinStatus(UPDATED_JOIN_STATUS)
                .joinPrice1(UPDATED_JOIN_PRICE_1)
                .joinPrice2(UPDATED_JOIN_PRICE_2)
                .joinEntercode(UPDATED_JOIN_ENTERCODE)
                .activity(UPDATED_ACTIVITY)
                .joinForm(UPDATED_JOIN_FORM)
                .orderNo(UPDATED_ORDER_NO);

        restJoinListMockMvc.perform(put("/api/join-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedJoinList)))
            .andExpect(status().isOk());

        // Validate the JoinList in the database
        List<JoinList> joinListList = joinListRepository.findAll();
        assertThat(joinListList).hasSize(databaseSizeBeforeUpdate);
        JoinList testJoinList = joinListList.get(joinListList.size() - 1);
        assertThat(testJoinList.getJoinName()).isEqualTo(UPDATED_JOIN_NAME);
        assertThat(testJoinList.getJoinTime()).isEqualTo(UPDATED_JOIN_TIME);
        assertThat(testJoinList.getJoinOpenid()).isEqualTo(UPDATED_JOIN_OPENID);
        assertThat(testJoinList.getJoinPhone()).isEqualTo(UPDATED_JOIN_PHONE);
        assertThat(testJoinList.getJoinNick()).isEqualTo(UPDATED_JOIN_NICK);
        assertThat(testJoinList.getJoinIcon()).isEqualTo(UPDATED_JOIN_ICON);
        assertThat(testJoinList.getJoinStatus()).isEqualTo(UPDATED_JOIN_STATUS);
        assertThat(testJoinList.getJoinPrice1()).isEqualTo(UPDATED_JOIN_PRICE_1);
        assertThat(testJoinList.getJoinPrice2()).isEqualTo(UPDATED_JOIN_PRICE_2);
        assertThat(testJoinList.getJoinEntercode()).isEqualTo(UPDATED_JOIN_ENTERCODE);
        assertThat(testJoinList.getActivity()).isEqualTo(UPDATED_ACTIVITY);
        assertThat(testJoinList.getJoinForm()).isEqualTo(UPDATED_JOIN_FORM);
        assertThat(testJoinList.getOrderNo()).isEqualTo(UPDATED_ORDER_NO);
    }

    @Test
    public void updateNonExistingJoinList() throws Exception {
        int databaseSizeBeforeUpdate = joinListRepository.findAll().size();

        // Create the JoinList

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restJoinListMockMvc.perform(put("/api/join-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(joinList)))
            .andExpect(status().isCreated());

        // Validate the JoinList in the database
        List<JoinList> joinListList = joinListRepository.findAll();
        assertThat(joinListList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteJoinList() throws Exception {
        // Initialize the database
        joinListService.save(joinList);

        int databaseSizeBeforeDelete = joinListRepository.findAll().size();

        // Get the joinList
        restJoinListMockMvc.perform(delete("/api/join-lists/{id}", joinList.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<JoinList> joinListList = joinListRepository.findAll();
        assertThat(joinListList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
