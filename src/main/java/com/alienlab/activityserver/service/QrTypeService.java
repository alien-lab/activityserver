package com.alienlab.activityserver.service;

import com.alibaba.fastjson.JSONObject;
import com.alienlab.activityserver.domain.QrType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;

/**
 * Service Interface for managing QrType.
 */
public interface QrTypeService {

    /**
     * Save a qrType.
     *
     * @param qrType the entity to save
     * @return the persisted entity
     */
    QrType save(QrType qrType);

    /**
     *  Get all the qrTypes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<QrType> findAll(Pageable pageable);

    /**
     *  Get the "id" qrType.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    QrType findOne(String id);

    /**
     *  Delete the "id" qrType.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
    JSONObject getqrcodepar(String key);
    Map<String,Object> getIdInfo(String table_name, String id, String value);
}
