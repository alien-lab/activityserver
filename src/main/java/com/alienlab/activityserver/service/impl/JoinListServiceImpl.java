package com.alienlab.activityserver.service.impl;

import com.alienlab.activityserver.service.JoinListService;
import com.alienlab.activityserver.domain.JoinList;
import com.alienlab.activityserver.repository.JoinListRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing JoinList.
 */
@Service
public class JoinListServiceImpl implements JoinListService{

    private final Logger log = LoggerFactory.getLogger(JoinListServiceImpl.class);
    
    @Inject
    private JoinListRepository joinListRepository;

    /**
     * Save a joinList.
     *
     * @param joinList the entity to save
     * @return the persisted entity
     */
    public JoinList save(JoinList joinList) {
        log.debug("Request to save JoinList : {}", joinList);
        JoinList result = joinListRepository.save(joinList);
        return result;
    }

    /**
     *  Get all the joinLists.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    public Page<JoinList> findAll(Pageable pageable) {
        log.debug("Request to get all JoinLists");
        Page<JoinList> result = joinListRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one joinList by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    public JoinList findOne(String id) {
        log.debug("Request to get JoinList : {}", id);
        JoinList joinList = joinListRepository.findOne(id);
        return joinList;
    }

    /**
     *  Delete the  joinList by id.
     *
     *  @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete JoinList : {}", id);
        joinListRepository.delete(id);
    }
}
