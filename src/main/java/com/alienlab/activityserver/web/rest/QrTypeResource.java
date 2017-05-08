package com.alienlab.activityserver.web.rest;

import com.alibaba.fastjson.JSONObject;
import com.codahale.metrics.annotation.Timed;
import com.alienlab.activityserver.domain.QrType;
import com.alienlab.activityserver.service.QrTypeService;
import com.alienlab.activityserver.web.rest.util.HeaderUtil;
import com.alienlab.activityserver.web.rest.util.PaginationUtil;

import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * REST controller for managing QrType.
 */
@RestController
@RequestMapping("/api")
public class QrTypeResource {

    private final Logger log = LoggerFactory.getLogger(QrTypeResource.class);

    @Inject
    private QrTypeService qrTypeService;

    /**
     * POST  /qr-types : Create a new qrType.
     *
     * @param qrType the qrType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new qrType, or with status 400 (Bad Request) if the qrType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/qr-types")
    @Timed
    public ResponseEntity<QrType> createQrType(@RequestBody QrType qrType) throws URISyntaxException {
        log.debug("REST request to save QrType : {}", qrType);
        if (qrType.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("qrType", "idexists", "A new qrType cannot already have an ID")).body(null);
        }
        QrType result = qrTypeService.save(qrType);
        return ResponseEntity.created(new URI("/api/qr-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("qrType", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /qr-types : Updates an existing qrType.
     *
     * @param qrType the qrType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated qrType,
     * or with status 400 (Bad Request) if the qrType is not valid,
     * or with status 500 (Internal Server Error) if the qrType couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/qr-types")
    @Timed
    public ResponseEntity<QrType> updateQrType(@RequestBody QrType qrType) throws URISyntaxException {
        log.debug("REST request to update QrType : {}", qrType);
        if (qrType.getId() == null) {
            return createQrType(qrType);
        }
        QrType result = qrTypeService.save(qrType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("qrType", qrType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /qr-types : get all the qrTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of qrTypes in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/qr-types")
    @Timed
    public ResponseEntity<List<QrType>> getAllQrTypes(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of QrTypes");
        Page<QrType> page = qrTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/qr-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /qr-types/:id : get the "id" qrType.
     *
     * @param id the id of the qrType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the qrType, or with status 404 (Not Found)
     */
    @GetMapping("/qr-types/{id}")
    @Timed
    public ResponseEntity<QrType> getQrType(@PathVariable String id) {
        log.debug("REST request to get QrType : {}", id);
        QrType qrType = qrTypeService.findOne(id);
        return Optional.ofNullable(qrType)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /qr-types/:id : delete the "id" qrType.
     *
     * @param id the id of the qrType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/qr-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteQrType(@PathVariable String id) {
        log.debug("REST request to delete QrType : {}", id);
        qrTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("qrType", id.toString())).build();
    }

    @PostMapping("/qr-type-wechat")
    @Timed
    public JSONObject getqrcodepar(@RequestParam String key){
        return qrTypeService.getqrcodepar(key);
    }

}
