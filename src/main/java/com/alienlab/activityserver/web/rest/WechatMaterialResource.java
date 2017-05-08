package com.alienlab.activityserver.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.alienlab.activityserver.domain.WechatMaterial;
import com.alienlab.activityserver.service.WechatMaterialService;
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
 * REST controller for managing WechatMaterial.
 */
@RestController
@RequestMapping("/api")
public class WechatMaterialResource {

    private final Logger log = LoggerFactory.getLogger(WechatMaterialResource.class);
        
    @Inject
    private WechatMaterialService wechatMaterialService;

    /**
     * POST  /wechat-materials : Create a new wechatMaterial.
     *
     * @param wechatMaterial the wechatMaterial to create
     * @return the ResponseEntity with status 201 (Created) and with body the new wechatMaterial, or with status 400 (Bad Request) if the wechatMaterial has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/wechat-materials")
    @Timed
    public ResponseEntity<WechatMaterial> createWechatMaterial(@RequestBody WechatMaterial wechatMaterial) throws URISyntaxException {
        log.debug("REST request to save WechatMaterial : {}", wechatMaterial);
        if (wechatMaterial.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("wechatMaterial", "idexists", "A new wechatMaterial cannot already have an ID")).body(null);
        }
        WechatMaterial result = wechatMaterialService.save(wechatMaterial);
        return ResponseEntity.created(new URI("/api/wechat-materials/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("wechatMaterial", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /wechat-materials : Updates an existing wechatMaterial.
     *
     * @param wechatMaterial the wechatMaterial to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated wechatMaterial,
     * or with status 400 (Bad Request) if the wechatMaterial is not valid,
     * or with status 500 (Internal Server Error) if the wechatMaterial couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/wechat-materials")
    @Timed
    public ResponseEntity<WechatMaterial> updateWechatMaterial(@RequestBody WechatMaterial wechatMaterial) throws URISyntaxException {
        log.debug("REST request to update WechatMaterial : {}", wechatMaterial);
        if (wechatMaterial.getId() == null) {
            return createWechatMaterial(wechatMaterial);
        }
        WechatMaterial result = wechatMaterialService.save(wechatMaterial);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("wechatMaterial", wechatMaterial.getId().toString()))
            .body(result);
    }

    /**
     * GET  /wechat-materials : get all the wechatMaterials.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of wechatMaterials in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/wechat-materials")
    @Timed
    public ResponseEntity<List<WechatMaterial>> getAllWechatMaterials(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of WechatMaterials");
        Page<WechatMaterial> page = wechatMaterialService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/wechat-materials");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /wechat-materials/:id : get the "id" wechatMaterial.
     *
     * @param id the id of the wechatMaterial to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the wechatMaterial, or with status 404 (Not Found)
     */
    @GetMapping("/wechat-materials/{id}")
    @Timed
    public ResponseEntity<WechatMaterial> getWechatMaterial(@PathVariable String id) {
        log.debug("REST request to get WechatMaterial : {}", id);
        WechatMaterial wechatMaterial = wechatMaterialService.findOne(id);
        return Optional.ofNullable(wechatMaterial)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /wechat-materials/:id : delete the "id" wechatMaterial.
     *
     * @param id the id of the wechatMaterial to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/wechat-materials/{id}")
    @Timed
    public ResponseEntity<Void> deleteWechatMaterial(@PathVariable String id) {
        log.debug("REST request to delete WechatMaterial : {}", id);
        wechatMaterialService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("wechatMaterial", id.toString())).build();
    }

}
