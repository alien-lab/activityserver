package com.alienlab.activityserver.service;

import com.alibaba.fastjson.JSONObject;
import com.alienlab.activityserver.domain.JoinList;

/**
 * Created by 橘 on 2017/5/14.
 */
public interface WechatMessageService {
    JSONObject sendJoinApplication(JoinList joinList);
    JSONObject sendCheckJoin(JoinList joinList);
    JSONObject sendJoinConfirm(JoinList joinList);
}
