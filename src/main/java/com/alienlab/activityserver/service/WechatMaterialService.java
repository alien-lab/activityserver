package com.alienlab.activityserver.service;

import com.alienlab.activityserver.domain.WechatMaterial;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing WechatMaterial.
 */
public interface WechatMaterialService {

    /**
     * Save a wechatMaterial.
     *
     * @param wechatMaterial the entity to save
     * @return the persisted entity
     */
    WechatMaterial save(WechatMaterial wechatMaterial);

    /**
     *  Get all the wechatMaterials.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<WechatMaterial> findAll(Pageable pageable);

    /**
     *  Get the "id" wechatMaterial.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    WechatMaterial findOne(String id);

    /**
     *  Delete the "id" wechatMaterial.
     *
     *  @param id the id of the entity
     */
    void delete(String id);

    List<WechatMaterial> findMaterialByBtnId(String btnId);
}
