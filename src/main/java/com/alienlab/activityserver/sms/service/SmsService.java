package com.alienlab.activityserver.sms.service;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alienlab.activityserver.web.wechat.bean.AccessToken;
import com.alienlab.activityserver.web.wechat.controller.PayController;
import com.alienlab.activityserver.web.wechat.util.HttpInvoker;
import com.alienlab.activityserver.web.wechat.util.HttpsInvoker;
import open189.sign.ParamsSign;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TreeMap;

/**
 * Created by 橘 on 2017/5/8.
 */
@Service
public class SmsService {
    private static Logger logger = Logger.getLogger(SmsService.class);

    @Value("${sms.appid}")
    private String smsappid;

    @Value("${sms.appsecret}")
    private String smssecret;

    @Value("${sms.callback}")
    private String smscallback;

    private String access_token="";
    /**
     * 调用发送短信接口
     * @param phone 接收短信的手机号码
     * @return
     * @throws IOException
     */
    public String sendSms(String phone) throws IOException, IOException {
        logger.info("正在向手机："+phone+" 发送验证码");
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timestamp = dateFormat.format(date);
        System.err.println(timestamp);
        TreeMap<String, String> paramsMap = new TreeMap<String, String>();
        paramsMap.put("app_id", smsappid);
        AccessToken token=getAccessToken();
        if(token!=null){
            paramsMap.put("access_token", token.getToken());
            access_token=token.getToken();
        }
        paramsMap.put("timestamp", timestamp);

        String getUrl = "http://api.189.cn/v2/dm/randcode/token?app_id=" + smsappid
            + "&access_token=" + access_token + "&timestamp="+ URLEncoder.encode(timestamp,"UTF-8") + "&sign="+ ParamsSign.value(paramsMap, smssecret);
        String resJson = HttpInvoker.httpGet(getUrl);
        System.err.println(resJson);
        JSONObject json = JSONObject.parseObject(resJson);
        System.out.println(json.get("token"));

        TreeMap<String, String> paramsMap1 = new TreeMap<String, String>();
        paramsMap1.put("app_id", smsappid);
        paramsMap1.put("access_token", access_token);
        paramsMap1.put("timestamp", timestamp);
        paramsMap1.put("token", json.get("token").toString());
        paramsMap1.put("url", smscallback);
        paramsMap1.put("phone", phone);
        paramsMap1.put("exp_time", "10");

        String postUrl = "http://api.189.cn/v2/dm/randcode/send?app_id="
            + smsappid + "&access_token=" + access_token + "&timestamp=" + URLEncoder.encode(timestamp,"UTF-8") + "";
        String postEntity = "token=" + json.get("token")
            + "&phone=" + phone
            + "&url=" + smscallback
            + "&exp_time=" + "10"
            + "&sign="+ParamsSign.value(paramsMap1, smssecret);
        System.out.println(postEntity);
        String resJson1 = HttpInvoker.httpPost(postUrl,null,postEntity);
        return resJson1;
    }

    public boolean validateCode(String id,String code){
        logger.info("验证短信码："+id+",提交的验证码为："+code);
        if(SmsCode.codePool.containsKey(id)){
            String value=SmsCode.codePool.get(id);
            logger.info("找到id为"+id+"的验证码记录，验证码为："+value);
            if(value.equals(code)){
                logger.info("找到id为"+id+"的验证码记录，验证码为："+value+",验证成功。");
                SmsCode.codePool.remove(id);
                return true;
            }else{
                logger.error("找到id为"+id+"的验证码记录，验证码为："+value+",输入验证码为："+code+",验证失败。");
                //codePool.remove(id);
                return false;
            }
        }else{
            logger.error("没有找到id为"+id+"的验证码记录。");
            return false;
        }
    }

    private AccessToken getAccessToken(){
        AccessToken accessToken = null;
        String appid=smsappid;
        String appsecret=smssecret;
        String requestUrl = "https://oauth.api.189.cn/emp/oauth2/v3/access_token";
        StringBuffer postContent=new StringBuffer();
        postContent.append("grant_type=client_credentials&");
        postContent.append("app_id="+appid+"&");
        postContent.append("app_secret="+appsecret);
        HttpsInvoker invoker=new HttpsInvoker();
        JSONObject jsonObject = HttpsInvoker.httpRequest(requestUrl, "POST", postContent.toString());
        // 如果请求成功
        if (null != jsonObject) {
            try {
                String res_code=jsonObject.getString("res_code");
                if(res_code.equals("0")){
                    accessToken = new AccessToken();
                    accessToken.setToken(jsonObject.getString("access_token"));
                    accessToken.setExpiresIn(jsonObject.getIntValue("expires_in"));
                }else{
                    accessToken = null;
                    // 获取token失败
                    logger.error("获取token失败 errcode:"+jsonObject.getString("res_code")+"errmsg:"+jsonObject.getString("res_message"));
                }
            } catch (JSONException e) {
                accessToken = null;
                // 获取token失败
                logger.error("获取token失败 errcode:"+jsonObject.getString("res_code")+"errmsg:"+jsonObject.getString("res_message"));
            }
        }
        System.out.println(accessToken.getToken());
        return accessToken;
    }
}
