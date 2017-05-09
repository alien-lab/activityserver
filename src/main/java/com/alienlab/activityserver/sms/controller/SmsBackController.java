package com.alienlab.activityserver.sms.controller;

import com.alienlab.activityserver.sms.service.SmsCode;
import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * Created by 橘 on 2017/5/8.
 */
@RestController
public class SmsBackController {
    private static Logger logger = Logger.getLogger(SmsBackController.class);
    @PostMapping(value="/smsback")
    public ResponseEntity smsCallback(HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        String rand_code=request.getParameter("rand_code");
        String identifier=request.getParameter("identifier");
        //将验证码存储到Map中。
        SmsCode.codePool.put(identifier, rand_code);
        logger.info("验证码为："+rand_code+">>>标识为："+identifier);
        return ResponseEntity.ok().body("验证码为："+rand_code+">>>标识为："+identifier);
    }
}
