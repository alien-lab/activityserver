package com.alienlab.activityserver.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.alienlab.activityserver.domain.WechatUser;
import com.alienlab.activityserver.service.WechatUserService;
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
 * REST controller for managing WechatUser.
 */
@RestController
@RequestMapping("/api")
public class WechatUserResource {

    private final Logger log = LoggerFactory.getLogger(WechatUserResource.class);
        
    @Inject
    private WechatUserService wechatUserService;

    /**
     * POST  /wechat-users : Create a new wechatUser.
     *
     * @param wechatUser the wechatUser to create
     * @return the ResponseEntity with status 201 (Created) and with body the new wechatUser, or with status 400 (Bad Request) if the wechatUser has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/wechat-users")
    @Timed
    public ResponseEntity<WechatUser> createWechatUser(@RequestBody WechatUser wechatUser) throws URISyntaxException {
        log.debug("REST request to save WechatUser : {}", wechatUser);
        if (wechatUser.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("wechatUser", "idexists", "A new wechatUser cannot already have an ID")).body(null);
        }
        WechatUser result = wechatUserService.save(wechatUser);
        return ResponseEntity.created(new URI("/api/wechat-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("wechatUser", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /wechat-users : Updates an existing wechatUser.
     *
     * @param wechatUser the wechatUser to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated wechatUser,
     * or with status 400 (Bad Request) if the wechatUser is not valid,
     * or with status 500 (Internal Server Error) if the wechatUser couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/wechat-users")
    @Timed
    public ResponseEntity<WechatUser> updateWechatUser(@RequestBody WechatUser wechatUser) throws URISyntaxException {
        log.debug("REST request to update WechatUser : {}", wechatUser);
        if (wechatUser.getId() == null) {
            return createWechatUser(wechatUser);
        }
        WechatUser result = wechatUserService.save(wechatUser);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("wechatUser", wechatUser.getId().toString()))
            .body(result);
    }

    /**
     * GET  /wechat-users : get all the wechatUsers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of wechatUsers in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/wechat-users")
    @Timed
    public ResponseEntity<List<WechatUser>> getAllWechatUsers(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of WechatUsers");
        Page<WechatUser> page = wechatUserService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/wechat-users");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /wechat-users/:id : get the "id" wechatUser.
     *
     * @param id the id of the wechatUser to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the wechatUser, or with status 404 (Not Found)
     */
    @GetMapping("/wechat-users/{id}")
    @Timed
    public ResponseEntity<WechatUser> getWechatUser(@PathVariable String id) {
        log.debug("REST request to get WechatUser : {}", id);
        WechatUser wechatUser = wechatUserService.findOne(id);
        return Optional.ofNullable(wechatUser)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /wechat-users/:id : delete the "id" wechatUser.
     *
     * @param id the id of the wechatUser to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/wechat-users/{id}")
    @Timed
    public ResponseEntity<Void> deleteWechatUser(@PathVariable String id) {
        log.debug("REST request to delete WechatUser : {}", id);
        wechatUserService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("wechatUser", id.toString())).build();
    }

}
