package com.alienlab.activityserver.repository;

import com.alienlab.activityserver.domain.WechatUser;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the WechatUser entity.
 */
@SuppressWarnings("unused")
public interface WechatUserRepository extends MongoRepository<WechatUser,String> {

}
