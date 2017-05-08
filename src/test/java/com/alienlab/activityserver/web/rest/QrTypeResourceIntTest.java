package com.alienlab.activityserver.web.rest;

import com.alienlab.activityserver.ActivityserverApp;

import com.alienlab.activityserver.domain.QrType;
import com.alienlab.activityserver.repository.QrTypeRepository;
import com.alienlab.activityserver.service.QrTypeService;

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
 * Test class for the QrTypeResource REST controller.
 *
 * @see QrTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ActivityserverApp.class)
public class QrTypeResourceIntTest {

    private static final String DEFAULT_QR_TYPE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_QR_TYPE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_QR_TYPE_TABLE = "AAAAAAAAAA";
    private static final String UPDATED_QR_TYPE_TABLE = "BBBBBBBBBB";

    private static final String DEFAULT_QR_TYPE_IDFIELD = "AAAAAAAAAA";
    private static final String UPDATED_QR_TYPE_IDFIELD = "BBBBBBBBBB";

    private static final String DEFAULT_QR_TYPE_NAMEFIELD = "AAAAAAAAAA";
    private static final String UPDATED_QR_TYPE_NAMEFIELD = "BBBBBBBBBB";

    private static final String DEFAULT_QR_TYPE_REPTYPE = "AAAAAAAAAA";
    private static final String UPDATED_QR_TYPE_REPTYPE = "BBBBBBBBBB";

    private static final String DEFAULT_QR_TYPE_URL = "AAAAAAAAAA";
    private static final String UPDATED_QR_TYPE_URL = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_QR_TYPE_CTTIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_QR_TYPE_CTTIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_QR_TYPE_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_QR_TYPE_STATUS = "BBBBBBBBBB";

    @Inject
    private QrTypeRepository qrTypeRepository;

    @Inject
    private QrTypeService qrTypeService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restQrTypeMockMvc;

    private QrType qrType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        QrTypeResource qrTypeResource = new QrTypeResource();
        ReflectionTestUtils.setField(qrTypeResource, "qrTypeService", qrTypeService);
        this.restQrTypeMockMvc = MockMvcBuilders.standaloneSetup(qrTypeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QrType createEntity() {
        QrType qrType = new QrType()
                .qrTypeName(DEFAULT_QR_TYPE_NAME)
                .qrTypeTable(DEFAULT_QR_TYPE_TABLE)
                .qrTypeIdfield(DEFAULT_QR_TYPE_IDFIELD)
                .qrTypeNamefield(DEFAULT_QR_TYPE_NAMEFIELD)
                .qrTypeReptype(DEFAULT_QR_TYPE_REPTYPE)
                .qrTypeUrl(DEFAULT_QR_TYPE_URL)
                .qrTypeCttime(DEFAULT_QR_TYPE_CTTIME)
                .qrTypeStatus(DEFAULT_QR_TYPE_STATUS);
        return qrType;
    }

    @Before
    public void initTest() {
        qrTypeRepository.deleteAll();
        qrType = createEntity();
    }

    @Test
    public void createQrType() throws Exception {
        int databaseSizeBeforeCreate = qrTypeRepository.findAll().size();

        // Create the QrType

        restQrTypeMockMvc.perform(post("/api/qr-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(qrType)))
            .andExpect(status().isCreated());

        // Validate the QrType in the database
        List<QrType> qrTypeList = qrTypeRepository.findAll();
        assertThat(qrTypeList).hasSize(databaseSizeBeforeCreate + 1);
        QrType testQrType = qrTypeList.get(qrTypeList.size() - 1);
        assertThat(testQrType.getQrTypeName()).isEqualTo(DEFAULT_QR_TYPE_NAME);
        assertThat(testQrType.getQrTypeTable()).isEqualTo(DEFAULT_QR_TYPE_TABLE);
        assertThat(testQrType.getQrTypeIdfield()).isEqualTo(DEFAULT_QR_TYPE_IDFIELD);
        assertThat(testQrType.getQrTypeNamefield()).isEqualTo(DEFAULT_QR_TYPE_NAMEFIELD);
        assertThat(testQrType.getQrTypeReptype()).isEqualTo(DEFAULT_QR_TYPE_REPTYPE);
        assertThat(testQrType.getQrTypeUrl()).isEqualTo(DEFAULT_QR_TYPE_URL);
        assertThat(testQrType.getQrTypeCttime()).isEqualTo(DEFAULT_QR_TYPE_CTTIME);
        assertThat(testQrType.getQrTypeStatus()).isEqualTo(DEFAULT_QR_TYPE_STATUS);
    }

    @Test
    public void createQrTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = qrTypeRepository.findAll().size();

        // Create the QrType with an existing ID
        QrType existingQrType = new QrType();
        existingQrType.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restQrTypeMockMvc.perform(post("/api/qr-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingQrType)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<QrType> qrTypeList = qrTypeRepository.findAll();
        assertThat(qrTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllQrTypes() throws Exception {
        // Initialize the database
        qrTypeRepository.save(qrType);

        // Get all the qrTypeList
        restQrTypeMockMvc.perform(get("/api/qr-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(qrType.getId())))
            .andExpect(jsonPath("$.[*].qrTypeName").value(hasItem(DEFAULT_QR_TYPE_NAME.toString())))
            .andExpect(jsonPath("$.[*].qrTypeTable").value(hasItem(DEFAULT_QR_TYPE_TABLE.toString())))
            .andExpect(jsonPath("$.[*].qrTypeIdfield").value(hasItem(DEFAULT_QR_TYPE_IDFIELD.toString())))
            .andExpect(jsonPath("$.[*].qrTypeNamefield").value(hasItem(DEFAULT_QR_TYPE_NAMEFIELD.toString())))
            .andExpect(jsonPath("$.[*].qrTypeReptype").value(hasItem(DEFAULT_QR_TYPE_REPTYPE.toString())))
            .andExpect(jsonPath("$.[*].qrTypeUrl").value(hasItem(DEFAULT_QR_TYPE_URL.toString())))
            .andExpect(jsonPath("$.[*].qrTypeCttime").value(hasItem(sameInstant(DEFAULT_QR_TYPE_CTTIME))))
            .andExpect(jsonPath("$.[*].qrTypeStatus").value(hasItem(DEFAULT_QR_TYPE_STATUS.toString())));
    }

    @Test
    public void getQrType() throws Exception {
        // Initialize the database
        qrTypeRepository.save(qrType);

        // Get the qrType
        restQrTypeMockMvc.perform(get("/api/qr-types/{id}", qrType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(qrType.getId()))
            .andExpect(jsonPath("$.qrTypeName").value(DEFAULT_QR_TYPE_NAME.toString()))
            .andExpect(jsonPath("$.qrTypeTable").value(DEFAULT_QR_TYPE_TABLE.toString()))
            .andExpect(jsonPath("$.qrTypeIdfield").value(DEFAULT_QR_TYPE_IDFIELD.toString()))
            .andExpect(jsonPath("$.qrTypeNamefield").value(DEFAULT_QR_TYPE_NAMEFIELD.toString()))
            .andExpect(jsonPath("$.qrTypeReptype").value(DEFAULT_QR_TYPE_REPTYPE.toString()))
            .andExpect(jsonPath("$.qrTypeUrl").value(DEFAULT_QR_TYPE_URL.toString()))
            .andExpect(jsonPath("$.qrTypeCttime").value(sameInstant(DEFAULT_QR_TYPE_CTTIME)))
            .andExpect(jsonPath("$.qrTypeStatus").value(DEFAULT_QR_TYPE_STATUS.toString()));
    }

    @Test
    public void getNonExistingQrType() throws Exception {
        // Get the qrType
        restQrTypeMockMvc.perform(get("/api/qr-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateQrType() throws Exception {
        // Initialize the database
        qrTypeService.save(qrType);

        int databaseSizeBeforeUpdate = qrTypeRepository.findAll().size();

        // Update the qrType
        QrType updatedQrType = qrTypeRepository.findOne(qrType.getId());
        updatedQrType
                .qrTypeName(UPDATED_QR_TYPE_NAME)
                .qrTypeTable(UPDATED_QR_TYPE_TABLE)
                .qrTypeIdfield(UPDATED_QR_TYPE_IDFIELD)
                .qrTypeNamefield(UPDATED_QR_TYPE_NAMEFIELD)
                .qrTypeReptype(UPDATED_QR_TYPE_REPTYPE)
                .qrTypeUrl(UPDATED_QR_TYPE_URL)
                .qrTypeCttime(UPDATED_QR_TYPE_CTTIME)
                .qrTypeStatus(UPDATED_QR_TYPE_STATUS);

        restQrTypeMockMvc.perform(put("/api/qr-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedQrType)))
            .andExpect(status().isOk());

        // Validate the QrType in the database
        List<QrType> qrTypeList = qrTypeRepository.findAll();
        assertThat(qrTypeList).hasSize(databaseSizeBeforeUpdate);
        QrType testQrType = qrTypeList.get(qrTypeList.size() - 1);
        assertThat(testQrType.getQrTypeName()).isEqualTo(UPDATED_QR_TYPE_NAME);
        assertThat(testQrType.getQrTypeTable()).isEqualTo(UPDATED_QR_TYPE_TABLE);
        assertThat(testQrType.getQrTypeIdfield()).isEqualTo(UPDATED_QR_TYPE_IDFIELD);
        assertThat(testQrType.getQrTypeNamefield()).isEqualTo(UPDATED_QR_TYPE_NAMEFIELD);
        assertThat(testQrType.getQrTypeReptype()).isEqualTo(UPDATED_QR_TYPE_REPTYPE);
        assertThat(testQrType.getQrTypeUrl()).isEqualTo(UPDATED_QR_TYPE_URL);
        assertThat(testQrType.getQrTypeCttime()).isEqualTo(UPDATED_QR_TYPE_CTTIME);
        assertThat(testQrType.getQrTypeStatus()).isEqualTo(UPDATED_QR_TYPE_STATUS);
    }

    @Test
    public void updateNonExistingQrType() throws Exception {
        int databaseSizeBeforeUpdate = qrTypeRepository.findAll().size();

        // Create the QrType

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restQrTypeMockMvc.perform(put("/api/qr-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(qrType)))
            .andExpect(status().isCreated());

        // Validate the QrType in the database
        List<QrType> qrTypeList = qrTypeRepository.findAll();
        assertThat(qrTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteQrType() throws Exception {
        // Initialize the database
        qrTypeService.save(qrType);

        int databaseSizeBeforeDelete = qrTypeRepository.findAll().size();

        // Get the qrType
        restQrTypeMockMvc.perform(delete("/api/qr-types/{id}", qrType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<QrType> qrTypeList = qrTypeRepository.findAll();
        assertThat(qrTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
