package com.alienlab.activityserver.repository;

import com.alienlab.activityserver.domain.WechatMaterial;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the WechatMaterial entity.
 */
@SuppressWarnings("unused")
public interface WechatMaterialRepository extends MongoRepository<WechatMaterial,String> {

}
