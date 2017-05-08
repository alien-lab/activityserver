package com.alienlab.activityserver.web.wechat.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alienlab.activityserver.domain.WechatMaterial;
import com.alienlab.activityserver.domain.WechatUser;
import com.alienlab.activityserver.service.WechatMaterialService;
import com.alienlab.activityserver.service.WechatUserService;
import com.alienlab.activityserver.web.rest.ExecResult;
import com.alienlab.activityserver.web.wechat.service.WechatService;
import com.alienlab.activityserver.web.wechat.util.EmojiUtils;
import com.alienlab.activityserver.web.wechat.util.SignUtil;
import com.alienlab.activityserver.web.wechat.util.WechatUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2017-01-18.
 */
@RestController
@RequestMapping("/api")
public class WeChatController {
    @Autowired
    SignUtil signUtil;
    @Autowired
    WechatUtil wechatUtil;


    @Autowired
    WechatService wechatService;

    private static Logger logger = Logger.getLogger(WeChatController.class);



    @RequestMapping(value="/jsapi",method = RequestMethod.POST)
    public Map<String,String> getJsApiTicket(@RequestParam("url") String url){
        return wechatService.getJsApiTicket(url);
    }

    @RequestMapping(value="/getmediainfotest",method = RequestMethod.POST)
    public JSONObject getmediainfotest(@RequestParam("media_id") String media_id){
        return wechatService.getmediainfotest(media_id);
    }

    @RequestMapping(value="/getmediainfo",method = RequestMethod.POST)
    public JSONObject getmediainfo(@RequestParam("btn_id") String btn_id){
        return wechatService.getmediainfo(btn_id);
    }


    @RequestMapping(value="/createMenu",method = RequestMethod.POST)
    public ResponseEntity getallmedia(@RequestParam("menu") String menu){
        ExecResult result = wechatService.getallmedia(menu);
        if(result.getResult()>0){
            return ResponseEntity.ok().body( result) ;
        }else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body( result) ;
        }
    }

    @RequestMapping(value="/getallmedia",method = RequestMethod.POST)
    public JSONObject getallmedia(@RequestParam("type") String type, @RequestParam("offset") String offset, @RequestParam("count") String count){
        return wechatService.getallmedia(type,offset,count);
    }


    @RequestMapping(value = "/getbtninfo" , method = RequestMethod.GET)
    public JSONObject getbtninfo(){
        return wechatService.getbtninfo();
    }

    @RequestMapping(value="/getuserinfo",method = RequestMethod.GET)
    public JSONObject getUserInfo(@RequestParam("code") String code){
        return wechatService.getUserInfo(code);
    }


    @RequestMapping(value = "/getqrcode",method = RequestMethod.GET)
    public JSONObject getQRCode(@RequestParam("scene_id") String scene_id ){
        return wechatService.getQRCode(scene_id);
    }

    @RequestMapping(value="/qrresponse",method = RequestMethod.POST)
    public JSONObject responseQrEvent(@RequestParam("qrid") String qrid){
        return wechatService.responseQrEvent(qrid);
    }



}
