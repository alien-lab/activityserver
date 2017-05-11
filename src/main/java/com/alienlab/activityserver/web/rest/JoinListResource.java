package com.alienlab.activityserver.web.rest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.codahale.metrics.annotation.Timed;
import com.alienlab.activityserver.domain.JoinList;
import com.alienlab.activityserver.service.JoinListService;
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
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing JoinList.
 */
@RestController
@RequestMapping("/api")
public class JoinListResource {

    private final Logger log = LoggerFactory.getLogger(JoinListResource.class);

    @Inject
    private JoinListService joinListService;

    /**
     * POST  /join-lists : Create a new joinList.
     *
     * @param joinList the joinList to create
     * @return the ResponseEntity with status 201 (Created) and with body the new joinList, or with status 400 (Bad Request) if the joinList has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/join-lists")
    @Timed
    public ResponseEntity<JoinList> createJoinList(@Valid @RequestBody JoinList joinList) throws URISyntaxException {
        log.debug("REST request to save JoinList : {}", joinList);
        if (joinList.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("joinList", "idexists", "A new joinList cannot already have an ID")).body(null);
        }
        JoinList result = joinListService.save(joinList);
        return ResponseEntity.created(new URI("/api/join-lists/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("joinList", result.getId().toString()))
            .body(result);
    }

    @PostMapping("/join-lists/json")
    @Timed
    public ResponseEntity createJoinListJson(@RequestParam String joinJson) throws URISyntaxException {
        log.debug("REST request to save JoinList : {}", joinJson);
        try {
            joinJson=URLDecoder.decode(joinJson,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        JSONObject join=JSONObject.parseObject(joinJson);
        return ResponseEntity.ok()
            .body(join);
    }

    /**
     * PUT  /join-lists : Updates an existing joinList.
     *
     * @param joinList the joinList to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated joinList,
     * or with status 400 (Bad Request) if the joinList is not valid,
     * or with status 500 (Internal Server Error) if the joinList couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/join-lists")
    @Timed
    public ResponseEntity<JoinList> updateJoinList(@Valid @RequestBody JoinList joinList) throws URISyntaxException {
        log.debug("REST request to update JoinList : {}", joinList);
        if (joinList.getId() == null) {
            return createJoinList(joinList);
        }
        JoinList result = joinListService.save(joinList);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("joinList", joinList.getId().toString()))
            .body(result);
    }

    /**
     * GET  /join-lists : get all the joinLists.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of joinLists in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/join-lists")
    @Timed
    public ResponseEntity<List<JoinList>> getAllJoinLists(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of JoinLists");
        Page<JoinList> page = joinListService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/join-lists");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /join-lists/:id : get the "id" joinList.
     *
     * @param id the id of the joinList to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the joinList, or with status 404 (Not Found)
     */
    @GetMapping("/join-lists/{id}")
    @Timed
    public ResponseEntity<JoinList> getJoinList(@PathVariable String id) {
        log.debug("REST request to get JoinList : {}", id);
        JoinList joinList = joinListService.findOne(id);
        return Optional.ofNullable(joinList)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /join-lists/:id : delete the "id" joinList.
     *
     * @param id the id of the joinList to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/join-lists/{id}")
    @Timed
    public ResponseEntity<Void> deleteJoinList(@PathVariable String id) {
        log.debug("REST request to delete JoinList : {}", id);
        joinListService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("joinList", id.toString())).build();
    }

}
