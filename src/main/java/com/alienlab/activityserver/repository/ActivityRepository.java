package com.alienlab.activityserver.repository;

import com.alienlab.activityserver.domain.Activity;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Activity entity.
 */
@SuppressWarnings("unused")
public interface ActivityRepository extends MongoRepository<Activity,String> {
    Activity findActivityByActFlag(String flag);
}
