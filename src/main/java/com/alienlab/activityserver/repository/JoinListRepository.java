package com.alienlab.activityserver.repository;

import com.alienlab.activityserver.domain.JoinList;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the JoinList entity.
 */
@SuppressWarnings("unused")
public interface JoinListRepository extends MongoRepository<JoinList,String> {

}
