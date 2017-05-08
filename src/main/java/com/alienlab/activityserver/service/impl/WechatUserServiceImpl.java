package com.alienlab.activityserver.service.impl;

import com.alienlab.activityserver.service.WechatUserService;
import com.alienlab.activityserver.domain.WechatUser;
import com.alienlab.activityserver.repository.WechatUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing WechatUser.
 */
@Service
public class WechatUserServiceImpl implements WechatUserService{

    private final Logger log = LoggerFactory.getLogger(WechatUserServiceImpl.class);

    @Inject
    private WechatUserRepository wechatUserRepository;

    /**
     * Save a wechatUser.
     *
     * @param wechatUser the entity to save
     * @return the persisted entity
     */
    public WechatUser save(WechatUser wechatUser) {
        log.debug("Request to save WechatUser : {}", wechatUser);
        WechatUser result = wechatUserRepository.save(wechatUser);
        return result;
    }

    /**
     *  Get all the wechatUsers.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    public Page<WechatUser> findAll(Pageable pageable) {
        log.debug("Request to get all WechatUsers");
        Page<WechatUser> result = wechatUserRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one wechatUser by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    public WechatUser findOne(String id) {
        log.debug("Request to get WechatUser : {}", id);
        WechatUser wechatUser = wechatUserRepository.findOne(id);
        return wechatUser;
    }

    /**
     *  Delete the  wechatUser by id.
     *
     *  @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete WechatUser : {}", id);
        wechatUserRepository.delete(id);
    }

    @Override
    public WechatUser findUserByOpenid(String openid) {
        return null;
    }
}
