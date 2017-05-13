package com.alienlab.activityserver.service;

import com.alienlab.activityserver.domain.Activity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Activity.
 */
public interface ActivityService {

    /**
     * Save a activity.
     *
     * @param activity the entity to save
     * @return the persisted entity
     */
    Activity save(Activity activity);

    /**
     *  Get all the activities.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Activity> findAll(Pageable pageable);

    /**
     *  Get the "id" activity.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Activity findOne(String id);

    /**
     *  Delete the "id" activity.
     *
     *  @param id the id of the entity
     */
    void delete(String id);

    Activity findByFlag(String flag);
}
