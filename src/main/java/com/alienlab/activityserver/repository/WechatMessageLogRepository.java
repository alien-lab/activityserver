package com.alienlab.activityserver.repository;

import com.alienlab.activityserver.domain.WechatMessageLog;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the WechatMessageLog entity.
 */
@SuppressWarnings("unused")
public interface WechatMessageLogRepository extends MongoRepository<WechatMessageLog,String> {

}
