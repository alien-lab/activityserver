package com.alienlab.activityserver.service.impl;

import com.alienlab.activityserver.service.ActivityService;
import com.alienlab.activityserver.domain.Activity;
import com.alienlab.activityserver.repository.ActivityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Activity.
 */
@Service
public class ActivityServiceImpl implements ActivityService{

    private final Logger log = LoggerFactory.getLogger(ActivityServiceImpl.class);

    @Inject
    private ActivityRepository activityRepository;

    /**
     * Save a activity.
     *
     * @param activity the entity to save
     * @return the persisted entity
     */
    public Activity save(Activity activity) {
        log.debug("Request to save Activity : {}", activity);
        Activity result = activityRepository.save(activity);
        return result;
    }

    /**
     *  Get all the activities.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    public Page<Activity> findAll(Pageable pageable) {
        log.debug("Request to get all Activities");
        Page<Activity> result = activityRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one activity by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    public Activity findOne(String id) {
        log.debug("Request to get Activity : {}", id);
        Activity activity = activityRepository.findOne(id);
        return activity;
    }

    /**
     *  Delete the  activity by id.
     *
     *  @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete Activity : {}", id);
        activityRepository.delete(id);
    }

    @Override
    public Activity findByFlag(String flag) {
        return activityRepository.findActivityByActFlag(flag);
    }
}
