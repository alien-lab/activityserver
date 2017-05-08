package com.alienlab.activityserver.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.alienlab.activityserver.domain.WechatMessageLog;
import com.alienlab.activityserver.service.WechatMessageLogService;
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
import java.util.Optional;

/**
 * REST controller for managing WechatMessageLog.
 */
@RestController
@RequestMapping("/api")
public class WechatMessageLogResource {

    private final Logger log = LoggerFactory.getLogger(WechatMessageLogResource.class);
        
    @Inject
    private WechatMessageLogService wechatMessageLogService;

    /**
     * POST  /wechat-message-logs : Create a new wechatMessageLog.
     *
     * @param wechatMessageLog the wechatMessageLog to create
     * @return the ResponseEntity with status 201 (Created) and with body the new wechatMessageLog, or with status 400 (Bad Request) if the wechatMessageLog has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/wechat-message-logs")
    @Timed
    public ResponseEntity<WechatMessageLog> createWechatMessageLog(@RequestBody WechatMessageLog wechatMessageLog) throws URISyntaxException {
        log.debug("REST request to save WechatMessageLog : {}", wechatMessageLog);
        if (wechatMessageLog.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("wechatMessageLog", "idexists", "A new wechatMessageLog cannot already have an ID")).body(null);
        }
        WechatMessageLog result = wechatMessageLogService.save(wechatMessageLog);
        return ResponseEntity.created(new URI("/api/wechat-message-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("wechatMessageLog", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /wechat-message-logs : Updates an existing wechatMessageLog.
     *
     * @param wechatMessageLog the wechatMessageLog to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated wechatMessageLog,
     * or with status 400 (Bad Request) if the wechatMessageLog is not valid,
     * or with status 500 (Internal Server Error) if the wechatMessageLog couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/wechat-message-logs")
    @Timed
    public ResponseEntity<WechatMessageLog> updateWechatMessageLog(@RequestBody WechatMessageLog wechatMessageLog) throws URISyntaxException {
        log.debug("REST request to update WechatMessageLog : {}", wechatMessageLog);
        if (wechatMessageLog.getId() == null) {
            return createWechatMessageLog(wechatMessageLog);
        }
        WechatMessageLog result = wechatMessageLogService.save(wechatMessageLog);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("wechatMessageLog", wechatMessageLog.getId().toString()))
            .body(result);
    }

    /**
     * GET  /wechat-message-logs : get all the wechatMessageLogs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of wechatMessageLogs in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/wechat-message-logs")
    @Timed
    public ResponseEntity<List<WechatMessageLog>> getAllWechatMessageLogs(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of WechatMessageLogs");
        Page<WechatMessageLog> page = wechatMessageLogService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/wechat-message-logs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /wechat-message-logs/:id : get the "id" wechatMessageLog.
     *
     * @param id the id of the wechatMessageLog to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the wechatMessageLog, or with status 404 (Not Found)
     */
    @GetMapping("/wechat-message-logs/{id}")
    @Timed
    public ResponseEntity<WechatMessageLog> getWechatMessageLog(@PathVariable String id) {
        log.debug("REST request to get WechatMessageLog : {}", id);
        WechatMessageLog wechatMessageLog = wechatMessageLogService.findOne(id);
        return Optional.ofNullable(wechatMessageLog)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /wechat-message-logs/:id : delete the "id" wechatMessageLog.
     *
     * @param id the id of the wechatMessageLog to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/wechat-message-logs/{id}")
    @Timed
    public ResponseEntity<Void> deleteWechatMessageLog(@PathVariable String id) {
        log.debug("REST request to delete WechatMessageLog : {}", id);
        wechatMessageLogService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("wechatMessageLog", id.toString())).build();
    }

}
