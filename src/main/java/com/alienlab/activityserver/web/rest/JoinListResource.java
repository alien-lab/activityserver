package com.alienlab.activityserver.web.rest;

import com.alibaba.fastjson.JSONObject;
import com.alienlab.activityserver.domain.Activity;
import com.alienlab.activityserver.service.ActivityService;
import com.alienlab.activityserver.sms.service.SmsService;
import com.alienlab.activityserver.web.wechat.service.WechatService;
import com.alienlab.activityserver.web.wechat.util.PayCommonUtil;
import com.codahale.metrics.annotation.Timed;
import com.alienlab.activityserver.domain.JoinList;
import com.alienlab.activityserver.service.JoinListService;
import com.alienlab.activityserver.web.rest.util.HeaderUtil;
import com.alienlab.activityserver.web.rest.util.PaginationUtil;

import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.*;

/**
 * REST controller for managing JoinList.
 */
@RestController
@RequestMapping("/api")
public class JoinListResource {

    private final Logger log = LoggerFactory.getLogger(JoinListResource.class);

    @Inject
    private JoinListService joinListService;

    /**
     * POST  /join-lists : Create a new joinList.
     *
     * @param joinList the joinList to create
     * @return the ResponseEntity with status 201 (Created) and with body the new joinList, or with status 400 (Bad Request) if the joinList has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/join-lists")
    @Timed
    public ResponseEntity<JoinList> createJoinList(@Valid @RequestBody JoinList joinList) throws URISyntaxException {
        log.debug("REST request to save JoinList : {}", joinList);
        if (joinList.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("joinList", "idexists", "A new joinList cannot already have an ID")).body(null);
        }
        JoinList result = joinListService.save(joinList);
        return ResponseEntity.created(new URI("/api/join-lists/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("joinList", result.getId().toString()))
            .body(result);
    }

    @Autowired
    SmsService smsService;
    @Autowired
    ActivityService activityService;
    @Autowired
    WechatService wechatService;

    @PostMapping("/join-lists/json")
    @Timed
    public ResponseEntity createJoinListJson(@RequestParam String joinJson, HttpServletRequest request) throws URISyntaxException {
        log.debug("REST request to save JoinList : {}", joinJson);
        JSONObject join=JSONObject.parseObject(joinJson);
        String smsid=join.getString("smsid");
        String phonecode=join.getString("phonecode");
        boolean codeflag=smsService.validateCode(smsid,phonecode);
        if(!codeflag){ //短信验证码错误
            ExecResult er=new ExecResult(false,"短信验证码错误");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
        JoinList joinList=new JoinList();
        String actFlag=join.getString("actFlag");
        joinList.setActivity(actFlag);
        joinList.setJoinForm(joinJson);
        joinList.setJoinIcon(join.getString("icon"));
        joinList.setJoinName(join.getString("parentName"));
        joinList.setJoinNick(join.getString("nickname"));
        joinList.setJoinOpenid(join.getString("openid"));
        joinList.setJoinPhone(join.getString("phone"));
        joinList.setJoinStatus("审核中");
        joinList.setJoinTime(ZonedDateTime.now());
        Activity act=activityService.findByFlag(actFlag);
        if(act==null){
            ExecResult er=new ExecResult(false,"您报名的活动不存在。");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
        String orderno= UUID.randomUUID().toString().replaceAll("-","");

        //微信下单支付
        Map<String,String> orderResult=wechatService.makeOrder(act.getActName(),orderno,act.getActPrice1().intValue(),request.getRemoteAddr(),joinList.getJoinOpenid());

        if(orderResult==null){
            ExecResult er=new ExecResult(false,"调用微信支付失败。");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
        String orderflag=orderResult.get("return_code");
        String resultcode=orderResult.get("result_code");
        if(orderflag.equalsIgnoreCase("SUCCESS")){
            if(resultcode.equalsIgnoreCase("SUCCESS")){//订单创建成功
                //保存joinlist
                joinList.setOrderNo(orderno);
                joinList=joinListService.save(joinList);
                JSONObject result=new JSONObject();
                result.put("joinList",joinList);
                result.put("orderInfo",wechatService.getPayParam(orderResult));
                return ResponseEntity.ok().body(result);
            }else{ //如果下单出现错误，返回错误信息到页面
                ExecResult er=new ExecResult(false,orderResult.get("err_code_des"));
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
            }
        }else{ //如果获取订单错误，返回错误信息到页面
            ExecResult er=new ExecResult(false,orderResult.get("return_msg"));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }



    /**
     * PUT  /join-lists : Updates an existing joinList.
     *
     * @param joinList the joinList to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated joinList,
     * or with status 400 (Bad Request) if the joinList is not valid,
     * or with status 500 (Internal Server Error) if the joinList couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/join-lists")
    @Timed
    public ResponseEntity<JoinList> updateJoinList(@Valid @RequestBody JoinList joinList) throws URISyntaxException {
        log.debug("REST request to update JoinList : {}", joinList);
        if (joinList.getId() == null) {
            return createJoinList(joinList);
        }
        JoinList result = joinListService.save(joinList);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("joinList", joinList.getId().toString()))
            .body(result);
    }

    /**
     * GET  /join-lists : get all the joinLists.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of joinLists in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/join-lists")
    @Timed
    public ResponseEntity<List<JoinList>> getAllJoinLists(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of JoinLists");
        Page<JoinList> page = joinListService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/join-lists");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /join-lists/:id : get the "id" joinList.
     *
     * @param id the id of the joinList to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the joinList, or with status 404 (Not Found)
     */
    @GetMapping("/join-lists/{id}")
    @Timed
    public ResponseEntity<JoinList> getJoinList(@PathVariable String id) {
        log.debug("REST request to get JoinList : {}", id);
        JoinList joinList = joinListService.findOne(id);
        return Optional.ofNullable(joinList)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /join-lists/:id : delete the "id" joinList.
     *
     * @param id the id of the joinList to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/join-lists/{id}")
    @Timed
    public ResponseEntity<Void> deleteJoinList(@PathVariable String id) {
        log.debug("REST request to delete JoinList : {}", id);
        joinListService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("joinList", id.toString())).build();
    }

}
