package com.alienlab.activityserver.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alienlab.activityserver.domain.Activity;
import com.alienlab.activityserver.domain.JoinList;
import com.alienlab.activityserver.service.ActivityService;
import com.alienlab.activityserver.service.WechatMessageService;
import com.alienlab.activityserver.web.wechat.util.WechatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.format.DateTimeFormatter;

/**
 * Created by 橘 on 2017/5/14.
 */
@Service
public class WechatMessageServiceImpl implements WechatMessageService {
    @Autowired
    ActivityService activityService;
    @Autowired
    WechatUtil wechatUtil;
    @Value("${wechat.host.basepath}")
    private String wechathost;

    @Override
    public JSONObject sendJoinApplication(JoinList joinList) {
        JSONObject param=new JSONObject();
        JSONObject first=new JSONObject();
        first.put("value","尊敬的"+joinList.getJoinNick()+"您好，感谢您的参与。我们已收到您的报名申请。");
        first.put("color","#000000");
        param.put("first",first);

        JSONObject param1=new JSONObject();
        Activity act=activityService.findByFlag(joinList.getActivity());
        param1.put("value",act.getActName());
        param1.put("color","#173177");
        param.put("keyword1",param1);


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        JSONObject param2=new JSONObject();
        param2.put("value",joinList.getJoinTime().format(formatter));
        param2.put("color","#000000");
        param.put("keyword2",param2);

        JSONObject param3=new JSONObject();
        param3.put("value",joinList.getJoinName());
        param3.put("color","#173177");
        param.put("keyword3",param3);

        JSONObject remark=new JSONObject();
        remark.put("value","我们将尽快对您的信息进行审核，审核通过后，将通过服务号消息通知您。请关注此微信号后续通知，谢谢。");
        remark.put("color","#000000");
        param.put("remark",remark);

        String url= wechatUtil.getPageAuthUrl(wechathost+"#!/regist","0");

        JSONObject result= wechatUtil.sendTemplateMsg(joinList.getJoinOpenid(),url,"OWFuKm6YnptT94fIv2VrG6wQtL6O-rITuZo2sRrkuxI",param);
        return result;
    }

    @Override
    public JSONObject sendCheckJoin(JoinList joinList) {
        return null;
    }

    @Override
    public JSONObject sendJoinConfirm(JoinList joinList) {
        return null;
    }
}
