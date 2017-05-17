package com.alienlab.activityserver.repository;

import com.alienlab.activityserver.domain.JoinList;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Spring Data MongoDB repository for the JoinList entity.
 */
@SuppressWarnings("unused")
public interface JoinListRepository extends MongoRepository<JoinList,String> {
    JoinList findJoinListByJoinOpenidAndOrderNo(String openid,String orderNo);
    List<JoinList> findJoinListsByJoinOpenid(String openid);
}
