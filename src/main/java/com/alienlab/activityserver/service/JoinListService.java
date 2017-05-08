package com.alienlab.activityserver.service;

import com.alienlab.activityserver.domain.JoinList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing JoinList.
 */
public interface JoinListService {

    /**
     * Save a joinList.
     *
     * @param joinList the entity to save
     * @return the persisted entity
     */
    JoinList save(JoinList joinList);

    /**
     *  Get all the joinLists.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<JoinList> findAll(Pageable pageable);

    /**
     *  Get the "id" joinList.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    JoinList findOne(String id);

    /**
     *  Delete the "id" joinList.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
