package com.alienlab.activityserver.web.rest;

import com.alienlab.activityserver.ActivityserverApp;

import com.alienlab.activityserver.domain.WechatMaterial;
import com.alienlab.activityserver.repository.WechatMaterialRepository;
import com.alienlab.activityserver.service.WechatMaterialService;

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
 * Test class for the WechatMaterialResource REST controller.
 *
 * @see WechatMaterialResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ActivityserverApp.class)
public class WechatMaterialResourceIntTest {

    private static final String DEFAULT_BTN_ID = "AAAAAAAAAA";
    private static final String UPDATED_BTN_ID = "BBBBBBBBBB";

    private static final String DEFAULT_MEDIA_ID = "AAAAAAAAAA";
    private static final String UPDATED_MEDIA_ID = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CRAETE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CRAETE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Inject
    private WechatMaterialRepository wechatMaterialRepository;

    @Inject
    private WechatMaterialService wechatMaterialService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restWechatMaterialMockMvc;

    private WechatMaterial wechatMaterial;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        WechatMaterialResource wechatMaterialResource = new WechatMaterialResource();
        ReflectionTestUtils.setField(wechatMaterialResource, "wechatMaterialService", wechatMaterialService);
        this.restWechatMaterialMockMvc = MockMvcBuilders.standaloneSetup(wechatMaterialResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WechatMaterial createEntity() {
        WechatMaterial wechatMaterial = new WechatMaterial()
                .btnId(DEFAULT_BTN_ID)
                .mediaId(DEFAULT_MEDIA_ID)
                .craeteTime(DEFAULT_CRAETE_TIME);
        return wechatMaterial;
    }

    @Before
    public void initTest() {
        wechatMaterialRepository.deleteAll();
        wechatMaterial = createEntity();
    }

    @Test
    public void createWechatMaterial() throws Exception {
        int databaseSizeBeforeCreate = wechatMaterialRepository.findAll().size();

        // Create the WechatMaterial

        restWechatMaterialMockMvc.perform(post("/api/wechat-materials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wechatMaterial)))
            .andExpect(status().isCreated());

        // Validate the WechatMaterial in the database
        List<WechatMaterial> wechatMaterialList = wechatMaterialRepository.findAll();
        assertThat(wechatMaterialList).hasSize(databaseSizeBeforeCreate + 1);
        WechatMaterial testWechatMaterial = wechatMaterialList.get(wechatMaterialList.size() - 1);
        assertThat(testWechatMaterial.getBtnId()).isEqualTo(DEFAULT_BTN_ID);
        assertThat(testWechatMaterial.getMediaId()).isEqualTo(DEFAULT_MEDIA_ID);
        assertThat(testWechatMaterial.getCraeteTime()).isEqualTo(DEFAULT_CRAETE_TIME);
    }

    @Test
    public void createWechatMaterialWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = wechatMaterialRepository.findAll().size();

        // Create the WechatMaterial with an existing ID
        WechatMaterial existingWechatMaterial = new WechatMaterial();
        existingWechatMaterial.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restWechatMaterialMockMvc.perform(post("/api/wechat-materials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingWechatMaterial)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<WechatMaterial> wechatMaterialList = wechatMaterialRepository.findAll();
        assertThat(wechatMaterialList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllWechatMaterials() throws Exception {
        // Initialize the database
        wechatMaterialRepository.save(wechatMaterial);

        // Get all the wechatMaterialList
        restWechatMaterialMockMvc.perform(get("/api/wechat-materials?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(wechatMaterial.getId())))
            .andExpect(jsonPath("$.[*].btnId").value(hasItem(DEFAULT_BTN_ID.toString())))
            .andExpect(jsonPath("$.[*].mediaId").value(hasItem(DEFAULT_MEDIA_ID.toString())))
            .andExpect(jsonPath("$.[*].craeteTime").value(hasItem(sameInstant(DEFAULT_CRAETE_TIME))));
    }

    @Test
    public void getWechatMaterial() throws Exception {
        // Initialize the database
        wechatMaterialRepository.save(wechatMaterial);

        // Get the wechatMaterial
        restWechatMaterialMockMvc.perform(get("/api/wechat-materials/{id}", wechatMaterial.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(wechatMaterial.getId()))
            .andExpect(jsonPath("$.btnId").value(DEFAULT_BTN_ID.toString()))
            .andExpect(jsonPath("$.mediaId").value(DEFAULT_MEDIA_ID.toString()))
            .andExpect(jsonPath("$.craeteTime").value(sameInstant(DEFAULT_CRAETE_TIME)));
    }

    @Test
    public void getNonExistingWechatMaterial() throws Exception {
        // Get the wechatMaterial
        restWechatMaterialMockMvc.perform(get("/api/wechat-materials/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateWechatMaterial() throws Exception {
        // Initialize the database
        wechatMaterialService.save(wechatMaterial);

        int databaseSizeBeforeUpdate = wechatMaterialRepository.findAll().size();

        // Update the wechatMaterial
        WechatMaterial updatedWechatMaterial = wechatMaterialRepository.findOne(wechatMaterial.getId());
        updatedWechatMaterial
                .btnId(UPDATED_BTN_ID)
                .mediaId(UPDATED_MEDIA_ID)
                .craeteTime(UPDATED_CRAETE_TIME);

        restWechatMaterialMockMvc.perform(put("/api/wechat-materials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedWechatMaterial)))
            .andExpect(status().isOk());

        // Validate the WechatMaterial in the database
        List<WechatMaterial> wechatMaterialList = wechatMaterialRepository.findAll();
        assertThat(wechatMaterialList).hasSize(databaseSizeBeforeUpdate);
        WechatMaterial testWechatMaterial = wechatMaterialList.get(wechatMaterialList.size() - 1);
        assertThat(testWechatMaterial.getBtnId()).isEqualTo(UPDATED_BTN_ID);
        assertThat(testWechatMaterial.getMediaId()).isEqualTo(UPDATED_MEDIA_ID);
        assertThat(testWechatMaterial.getCraeteTime()).isEqualTo(UPDATED_CRAETE_TIME);
    }

    @Test
    public void updateNonExistingWechatMaterial() throws Exception {
        int databaseSizeBeforeUpdate = wechatMaterialRepository.findAll().size();

        // Create the WechatMaterial

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restWechatMaterialMockMvc.perform(put("/api/wechat-materials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wechatMaterial)))
            .andExpect(status().isCreated());

        // Validate the WechatMaterial in the database
        List<WechatMaterial> wechatMaterialList = wechatMaterialRepository.findAll();
        assertThat(wechatMaterialList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteWechatMaterial() throws Exception {
        // Initialize the database
        wechatMaterialService.save(wechatMaterial);

        int databaseSizeBeforeDelete = wechatMaterialRepository.findAll().size();

        // Get the wechatMaterial
        restWechatMaterialMockMvc.perform(delete("/api/wechat-materials/{id}", wechatMaterial.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<WechatMaterial> wechatMaterialList = wechatMaterialRepository.findAll();
        assertThat(wechatMaterialList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
