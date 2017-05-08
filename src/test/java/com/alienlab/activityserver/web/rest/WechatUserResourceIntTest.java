package com.alienlab.activityserver.web.rest;

import com.alienlab.activityserver.ActivityserverApp;

import com.alienlab.activityserver.domain.WechatUser;
import com.alienlab.activityserver.repository.WechatUserRepository;
import com.alienlab.activityserver.service.WechatUserService;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the WechatUserResource REST controller.
 *
 * @see WechatUserResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ActivityserverApp.class)
public class WechatUserResourceIntTest {

    private static final String DEFAULT_WECHAT_UNIONID = "AAAAAAAAAA";
    private static final String UPDATED_WECHAT_UNIONID = "BBBBBBBBBB";

    private static final String DEFAULT_WECHAT_NICKNAME = "AAAAAAAAAA";
    private static final String UPDATED_WECHAT_NICKNAME = "BBBBBBBBBB";

    private static final String DEFAULT_WECHAT_IMAGE = "AAAAAAAAAA";
    private static final String UPDATED_WECHAT_IMAGE = "BBBBBBBBBB";

    private static final String DEFAULT_WECHAT_AREA = "AAAAAAAAAA";
    private static final String UPDATED_WECHAT_AREA = "BBBBBBBBBB";

    private static final String DEFAULT_USER_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_USER_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_WECHAT_OPENID = "AAAAAAAAAA";
    private static final String UPDATED_WECHAT_OPENID = "BBBBBBBBBB";

    private static final String DEFAULT_WECHAT_QRKEY = "AAAAAAAAAA";
    private static final String UPDATED_WECHAT_QRKEY = "BBBBBBBBBB";

    @Inject
    private WechatUserRepository wechatUserRepository;

    @Inject
    private WechatUserService wechatUserService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restWechatUserMockMvc;

    private WechatUser wechatUser;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        WechatUserResource wechatUserResource = new WechatUserResource();
        ReflectionTestUtils.setField(wechatUserResource, "wechatUserService", wechatUserService);
        this.restWechatUserMockMvc = MockMvcBuilders.standaloneSetup(wechatUserResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WechatUser createEntity() {
        WechatUser wechatUser = new WechatUser()
                .wechatUnionid(DEFAULT_WECHAT_UNIONID)
                .wechatNickname(DEFAULT_WECHAT_NICKNAME)
                .wechatImage(DEFAULT_WECHAT_IMAGE)
                .wechatArea(DEFAULT_WECHAT_AREA)
                .userType(DEFAULT_USER_TYPE)
                .wechatOpenid(DEFAULT_WECHAT_OPENID)
                .wechatQrkey(DEFAULT_WECHAT_QRKEY);
        return wechatUser;
    }

    @Before
    public void initTest() {
        wechatUserRepository.deleteAll();
        wechatUser = createEntity();
    }

    @Test
    public void createWechatUser() throws Exception {
        int databaseSizeBeforeCreate = wechatUserRepository.findAll().size();

        // Create the WechatUser

        restWechatUserMockMvc.perform(post("/api/wechat-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wechatUser)))
            .andExpect(status().isCreated());

        // Validate the WechatUser in the database
        List<WechatUser> wechatUserList = wechatUserRepository.findAll();
        assertThat(wechatUserList).hasSize(databaseSizeBeforeCreate + 1);
        WechatUser testWechatUser = wechatUserList.get(wechatUserList.size() - 1);
        assertThat(testWechatUser.getWechatUnionid()).isEqualTo(DEFAULT_WECHAT_UNIONID);
        assertThat(testWechatUser.getWechatNickname()).isEqualTo(DEFAULT_WECHAT_NICKNAME);
        assertThat(testWechatUser.getWechatImage()).isEqualTo(DEFAULT_WECHAT_IMAGE);
        assertThat(testWechatUser.getWechatArea()).isEqualTo(DEFAULT_WECHAT_AREA);
        assertThat(testWechatUser.getUserType()).isEqualTo(DEFAULT_USER_TYPE);
        assertThat(testWechatUser.getWechatOpenid()).isEqualTo(DEFAULT_WECHAT_OPENID);
        assertThat(testWechatUser.getWechatQrkey()).isEqualTo(DEFAULT_WECHAT_QRKEY);
    }

    @Test
    public void createWechatUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = wechatUserRepository.findAll().size();

        // Create the WechatUser with an existing ID
        WechatUser existingWechatUser = new WechatUser();
        existingWechatUser.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restWechatUserMockMvc.perform(post("/api/wechat-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingWechatUser)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<WechatUser> wechatUserList = wechatUserRepository.findAll();
        assertThat(wechatUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllWechatUsers() throws Exception {
        // Initialize the database
        wechatUserRepository.save(wechatUser);

        // Get all the wechatUserList
        restWechatUserMockMvc.perform(get("/api/wechat-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(wechatUser.getId())))
            .andExpect(jsonPath("$.[*].wechatUnionid").value(hasItem(DEFAULT_WECHAT_UNIONID.toString())))
            .andExpect(jsonPath("$.[*].wechatNickname").value(hasItem(DEFAULT_WECHAT_NICKNAME.toString())))
            .andExpect(jsonPath("$.[*].wechatImage").value(hasItem(DEFAULT_WECHAT_IMAGE.toString())))
            .andExpect(jsonPath("$.[*].wechatArea").value(hasItem(DEFAULT_WECHAT_AREA.toString())))
            .andExpect(jsonPath("$.[*].userType").value(hasItem(DEFAULT_USER_TYPE.toString())))
            .andExpect(jsonPath("$.[*].wechatOpenid").value(hasItem(DEFAULT_WECHAT_OPENID.toString())))
            .andExpect(jsonPath("$.[*].wechatQrkey").value(hasItem(DEFAULT_WECHAT_QRKEY.toString())));
    }

    @Test
    public void getWechatUser() throws Exception {
        // Initialize the database
        wechatUserRepository.save(wechatUser);

        // Get the wechatUser
        restWechatUserMockMvc.perform(get("/api/wechat-users/{id}", wechatUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(wechatUser.getId()))
            .andExpect(jsonPath("$.wechatUnionid").value(DEFAULT_WECHAT_UNIONID.toString()))
            .andExpect(jsonPath("$.wechatNickname").value(DEFAULT_WECHAT_NICKNAME.toString()))
            .andExpect(jsonPath("$.wechatImage").value(DEFAULT_WECHAT_IMAGE.toString()))
            .andExpect(jsonPath("$.wechatArea").value(DEFAULT_WECHAT_AREA.toString()))
            .andExpect(jsonPath("$.userType").value(DEFAULT_USER_TYPE.toString()))
            .andExpect(jsonPath("$.wechatOpenid").value(DEFAULT_WECHAT_OPENID.toString()))
            .andExpect(jsonPath("$.wechatQrkey").value(DEFAULT_WECHAT_QRKEY.toString()));
    }

    @Test
    public void getNonExistingWechatUser() throws Exception {
        // Get the wechatUser
        restWechatUserMockMvc.perform(get("/api/wechat-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateWechatUser() throws Exception {
        // Initialize the database
        wechatUserService.save(wechatUser);

        int databaseSizeBeforeUpdate = wechatUserRepository.findAll().size();

        // Update the wechatUser
        WechatUser updatedWechatUser = wechatUserRepository.findOne(wechatUser.getId());
        updatedWechatUser
                .wechatUnionid(UPDATED_WECHAT_UNIONID)
                .wechatNickname(UPDATED_WECHAT_NICKNAME)
                .wechatImage(UPDATED_WECHAT_IMAGE)
                .wechatArea(UPDATED_WECHAT_AREA)
                .userType(UPDATED_USER_TYPE)
                .wechatOpenid(UPDATED_WECHAT_OPENID)
                .wechatQrkey(UPDATED_WECHAT_QRKEY);

        restWechatUserMockMvc.perform(put("/api/wechat-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedWechatUser)))
            .andExpect(status().isOk());

        // Validate the WechatUser in the database
        List<WechatUser> wechatUserList = wechatUserRepository.findAll();
        assertThat(wechatUserList).hasSize(databaseSizeBeforeUpdate);
        WechatUser testWechatUser = wechatUserList.get(wechatUserList.size() - 1);
        assertThat(testWechatUser.getWechatUnionid()).isEqualTo(UPDATED_WECHAT_UNIONID);
        assertThat(testWechatUser.getWechatNickname()).isEqualTo(UPDATED_WECHAT_NICKNAME);
        assertThat(testWechatUser.getWechatImage()).isEqualTo(UPDATED_WECHAT_IMAGE);
        assertThat(testWechatUser.getWechatArea()).isEqualTo(UPDATED_WECHAT_AREA);
        assertThat(testWechatUser.getUserType()).isEqualTo(UPDATED_USER_TYPE);
        assertThat(testWechatUser.getWechatOpenid()).isEqualTo(UPDATED_WECHAT_OPENID);
        assertThat(testWechatUser.getWechatQrkey()).isEqualTo(UPDATED_WECHAT_QRKEY);
    }

    @Test
    public void updateNonExistingWechatUser() throws Exception {
        int databaseSizeBeforeUpdate = wechatUserRepository.findAll().size();

        // Create the WechatUser

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restWechatUserMockMvc.perform(put("/api/wechat-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wechatUser)))
            .andExpect(status().isCreated());

        // Validate the WechatUser in the database
        List<WechatUser> wechatUserList = wechatUserRepository.findAll();
        assertThat(wechatUserList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteWechatUser() throws Exception {
        // Initialize the database
        wechatUserService.save(wechatUser);

        int databaseSizeBeforeDelete = wechatUserRepository.findAll().size();

        // Get the wechatUser
        restWechatUserMockMvc.perform(delete("/api/wechat-users/{id}", wechatUser.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<WechatUser> wechatUserList = wechatUserRepository.findAll();
        assertThat(wechatUserList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
