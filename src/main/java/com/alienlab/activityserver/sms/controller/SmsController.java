package com.alienlab.activityserver.sms.controller;

import com.alibaba.fastjson.JSONObject;
import com.alienlab.activityserver.sms.service.SmsService;
import com.alienlab.activityserver.web.rest.ExecResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by 橘 on 2017/5/9.
 */
@RestController
@RequestMapping(value="/api")
public class SmsController {
    @Autowired
    SmsService smsService;
    @PostMapping(value="/sendsms")
    public ResponseEntity sendSMS(@RequestParam String phone){
        try{
            String result=smsService.sendSms(phone);
            JSONObject jresult=JSONObject.parseObject(result);
            return ResponseEntity.ok().body(jresult);
        }catch(Exception e){
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }
}

