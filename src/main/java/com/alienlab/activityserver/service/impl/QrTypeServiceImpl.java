package com.alienlab.activityserver.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alienlab.activityserver.service.QrTypeService;
import com.alienlab.activityserver.domain.QrType;
import com.alienlab.activityserver.repository.QrTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

/**
 * Service Implementation for managing QrType.
 */
@Service
public class QrTypeServiceImpl implements QrTypeService{

    private final Logger log = LoggerFactory.getLogger(QrTypeServiceImpl.class);

    @Autowired
    MongoTemplate mongoTemplate;

    @Inject
    private QrTypeRepository qrTypeRepository;

    /**
     * Save a qrType.
     *
     * @param qrType the entity to save
     * @return the persisted entity
     */
    public QrType save(QrType qrType) {
        log.debug("Request to save QrType : {}", qrType);
        QrType result = qrTypeRepository.save(qrType);
        return result;
    }

    /**
     *  Get all the qrTypes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    public Page<QrType> findAll(Pageable pageable) {
        log.debug("Request to get all QrTypes");
        Page<QrType> result = qrTypeRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one qrType by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    public QrType findOne(String id) {
        log.debug("Request to get QrType : {}", id);
        QrType qrType = qrTypeRepository.findOne(id);
        return qrType;
    }

    /**
     *  Delete the  qrType by id.
     *
     *  @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete QrType : {}", id);
        qrTypeRepository.delete(id);
    }

    public JSONObject getqrcodepar(String key){
        JSONObject jo = new JSONObject();
        String keys[] = key.split("and");
        if(keys.length<2){
            jo.put("error","key解析出错");
            return jo;
        }
        QrType qrType = qrTypeRepository.findOne(keys[0]);
        jo.put("qrType",qrType);
        if(qrType!=null){
            String table_name = qrType.getQrTypeTable();
            String major_key = qrType.getQrTypeIdfield();
            Map<String,Object> main_data = getIdInfo(table_name,major_key,keys[1]);
            jo.put("data",main_data);
            return jo;
        }else{
            jo.put("error","二维码类型获取出错");
            return jo;
        }
    }

    @Override
    public Map<String, Object> getIdInfo(String table_name, String id, String value) {
        return null;
    }
}
