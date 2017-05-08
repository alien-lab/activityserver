package com.alienlab.activityserver.web.wechat.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alienlab.activityserver.domain.WechatMessageLog;
import com.alienlab.activityserver.domain.WechatUser;
import com.alienlab.activityserver.service.WechatMessageLogService;
import com.alienlab.activityserver.service.WechatUserService;
import com.alienlab.activityserver.web.wechat.bean.AccessToken;
import com.alienlab.activityserver.web.wechat.bean.JSApiTicket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Map;

/**
 * Created by 橘 on 2016/12/26.
 */
@Component
public class WechatUtil {
    private static final Logger logger = LoggerFactory.getLogger("WechatUtil");
    // 获取access_token的接口地址（GET） 限200（次/天）
    public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    //JSAPI请求URL
    public final static String jsapi_ticket_url="https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";

    public final static String menu_url="https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

    public final static String user_info="https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

    public final static String cus_msg="https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";

    public final static String access_toekn_url="https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";

    public final static String user_info_openid="https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN ";

    public final static String get_user_info_openid="https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN ";

    public final static  String qr_code_ticket_url="https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=TOKEN";

    public final static String template_msg_url="https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";

    public final static String  get_media_url = "https://api.weixin.qq.com/cgi-bin/material/get_material?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";

    public final static String get_all_media = "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=ACCESS_TOKEN";

    public final static String get_btn_info = "https://api.weixin.qq.com/cgi-bin/get_current_selfmenu_info?access_token=ACCESS_TOKEN";
    @Autowired
    SignUtil sign;
    @Value("${wechat.appid}")
    private String wxappid;
    @Value("${wechat.secret}")
    private String wxappsecret;

    @Value("${wechat.host.basepath}")
    private String wechathost;
    public Map<String, String> getJsapiSignature(String url){
        logger.info("获得微信js-SDK加密signature:"+url);
        JSApiTicket jt=getJSApiTicket(wxappid,wxappsecret);
        if(jt==null){
            return null;
        }
        String jsapi_ticket=jt.getTicket();
        logger.info("获得jsapi_ticket："+jsapi_ticket);
        Map map=sign.sign(jsapi_ticket, url);
        map.put("appid", wxappid);
        return map;
    }

    public  JSONObject  get_access_token(String code){
         String url = access_toekn_url.replaceAll("APPID",wxappid).replaceAll("SECRET",wxappsecret).replaceAll("CODE",code);
         JSONObject jsonObject = HttpsInvoker.httpRequest(url,"GET","");
        return jsonObject;
    }


    public  JSONObject  get_media_info(String media_id){
        JSONObject jo = new JSONObject();
        jo.put("media_id",media_id);
        AccessToken at=getAccessToken(wxappid,wxappsecret);
        if(at==null){
            return null;
        }
        String posturl=get_media_url.replace("ACCESS_TOKEN",at.getToken()).replace("MEDIA_ID",media_id);
        JSONObject jsonObject = HttpsInvoker.httpRequest(posturl,"POST",jo.toJSONString());
        return jsonObject;
    }

    public  JSONObject  get_user_info_openid(String openid){
        JSONObject jo = new JSONObject();
        jo.put("openid",openid);
        AccessToken at=getAccessToken(wxappid,wxappsecret);
        if(at==null){
            return null;
        }
        String posturl=get_user_info_openid.replace("ACCESS_TOKEN",at.getToken()).replace("OPENID",openid);
        JSONObject jsonObject = HttpsInvoker.httpRequest(posturl,"POST",jo.toJSONString());
        return jsonObject;
    }

    public  JSONObject  get_btn_info(){
        AccessToken at=getAccessToken(wxappid,wxappsecret);
        if(at==null){
            return null;
        }
        String posturl=get_btn_info.replace("ACCESS_TOKEN",at.getToken());
        JSONObject jsonObject = HttpsInvoker.httpRequest(posturl,"GET","");
        return jsonObject;
    }


    public  JSONObject  get_all_media(String type,String offset,String count){
        JSONObject jo = new JSONObject();
        jo.put("type",type);
        jo.put("offset",offset);
        jo.put("count",count);
        AccessToken at=getAccessToken(wxappid,wxappsecret);
        if(at==null){
            return null;
        }
        String posturl=get_all_media.replace("ACCESS_TOKEN",at.getToken()).replace("TYPE",type).replace("OFFSET",offset).replace("COUNT",count);
        JSONObject jsonObject = HttpsInvoker.httpRequest(posturl,"POST",jo.toJSONString());
        return jsonObject;
    }

    public  JSONObject get_user_info(String access_toekn,String openid){
        String url = user_info_openid.replaceAll("ACCESS_TOKEN",access_toekn).replaceAll("OPENID",openid);
        JSONObject jsonObject = HttpsInvoker.httpRequest(url,"GET","");
        return jsonObject;
    }

    public JSONObject get_qr_code_ticket(String scene_id){
        String access_toen = WechatUtil.getAccessToken(wxappid,wxappsecret).getToken();
        String url = qr_code_ticket_url.replaceAll("TOKEN",access_toen);
        String jsonMsg="{\"action_name\": \"QR_LIMIT_STR_SCENE\", \"action_info\": {\"scene\": {\"scene_str\": \"%s\"}}}";
        JSONObject json = HttpsInvoker.httpRequest(url,"POST",String.format(jsonMsg,scene_id));
        return json;
    }

    private static JSApiTicket jsticket=null;
    public static JSApiTicket getJSApiTicket(String appid,String secret){
        logger.info("获取微信jsticket");
        if(jsticket== null){
            logger.info("系统中jsticket不存在！");
            jsticket=getJsApiTicket(appid,secret);
        }else{
            Calendar c=Calendar.getInstance();
            long now=c.getTimeInMillis();
            if(now-jsticket.getTicketTime()>=7000*1000){
                logger.info("系统中jsticket已超时！gettoken时间："+jsticket.getTicketTime()+",当前时间:"+now);
                jsticket=getJsApiTicket(appid,secret);
            }else{
                logger.info("系统中jsticket未过期可使用");
            }
        }
        return jsticket;
    }

    private static JSApiTicket getJsApiTicket(String appid,String secret){
        logger.info("微信服务号获取新JSApiTicket");
        HttpsInvoker invoker=new HttpsInvoker();
        JSApiTicket jt=null;
        AccessToken at=getAccessToken(appid,secret);
        if(at==null){
            return jt;
        }
        String requestUrl = jsapi_ticket_url.replace("ACCESS_TOKEN", at.getToken());
        JSONObject jsonObject = HttpsInvoker.httpRequest(requestUrl, "GET", null);
        // 如果请求成功
        if (null != jsonObject) {
            try {
                jt = new JSApiTicket();
                jt.setErrcode(jsonObject.getString("errcode"));
                jt.setErrmsg(jsonObject.getString("errmsg"));
                jt.setExpires_in(jsonObject.getString("expires_in"));
                jt.setTicket(jsonObject.getString("ticket"));
                Calendar c=Calendar.getInstance();
                jt.setTicketTime(c.getTimeInMillis());
            } catch (JSONException e) {
                jt = null;
                // 获取token失败
                logger.error("获取JSApiTicket失败 errcode:"+jsonObject.getString("errcode")+"errmsg:"+jsonObject.getString("errmsg"));
            }
        }
        return jt;
    }

    private static AccessToken accessToken = null;
    public static AccessToken getAccessToken(String appid, String appsecret) {
        logger.info("获取微信AccessToken");
        WechatUtil wu=new WechatUtil();
        if(accessToken== null){
            logger.info("系统中token不存在！");
            accessToken=wu.getToken(appid,appsecret);
        }else{
            Calendar c=Calendar.getInstance();
            long now=c.getTimeInMillis();
            if(now-accessToken.getTokenTime()>=7000*1000){
                logger.info("系统中token已超时！gettoken时间："+accessToken.getTokenTime()+",当前时间:"+now);
                accessToken=wu.getToken(appid,appsecret);
            }else{
                logger.info("系统中token未过期可使用");
            }
        }
        return accessToken;
    }

    public AccessToken getToken(String appid,String appsecret){
        logger.info("微信服务号获取新token");
        AccessToken at=null;
        String requestUrl = access_token_url.replace("APPID", appid).replace("APPSECRET", appsecret);
        HttpsInvoker invoker=new HttpsInvoker();
        JSONObject jsonObject = HttpsInvoker.httpRequest(requestUrl, "GET", null);
        // 如果请求成功
        if (null != jsonObject) {
            try {
                at = new AccessToken();
                at.setToken(jsonObject.getString("access_token"));
                at.setExpiresIn(jsonObject.getIntValue("expires_in"));
                Calendar c=Calendar.getInstance();
                at.setTokenTime(c.getTimeInMillis());
            } catch (JSONException e) {
                at = null;
                // 获取token失败
                logger.error("获取token失败 errcode:"+jsonObject.getString("errcode")+"errmsg:"+jsonObject.getString("errmsg"));
            }
        }
        return at;
    }



    public JSONObject getMenu(){
        JSONObject menu=new JSONObject();
        JSONArray buttons=new JSONArray();//定义一个按钮组
        WechatUtil wechatUtil=new WechatUtil();
        //第一组按钮
        JSONObject button1=new JSONObject();
        button1.put("name","SUMEC");//第一个一级菜单
        JSONArray btn1_sub=new JSONArray();//第一个一级菜单的子菜单
        JSONObject btn1_sub_btn4=new JSONObject();
        btn1_sub_btn4.put("type","click");
        btn1_sub_btn4.put("name","关于我们");
        btn1_sub_btn4.put("key","M11");
        btn1_sub.add(btn1_sub_btn4);
        JSONObject btn1_sub_btn2=new JSONObject();
        btn1_sub_btn2.put("type","click");
        btn1_sub_btn2.put("name","行业动态");
        btn1_sub_btn2.put("key","M12");
        btn1_sub.add(btn1_sub_btn2);
        JSONObject btn1_sub_btn3=new JSONObject();
        btn1_sub_btn3.put("type","click");
        btn1_sub_btn3.put("name","金融资讯");
        btn1_sub_btn3.put("key","M13");
        btn1_sub.add(btn1_sub_btn3);
        JSONObject btn1_sub_btn1=new JSONObject();
        btn1_sub_btn1.put("type","click");
        btn1_sub_btn1.put("name","打折转售");
        btn1_sub_btn1.put("key","M14");
        btn1_sub.add(btn1_sub_btn1);



        button1.put("sub_button",btn1_sub);
        buttons.add(button1);

        //第二组按钮
      JSONObject button2=new JSONObject();
       button2.put("name","我要");//第一个一级菜单
       JSONArray btn2_sub=new JSONArray();//第一个一级菜单的子菜单
       JSONObject btn2_sub_btn1=new JSONObject();

   /*     JSONObject btn2_sub_btn2=new JSONObject();
        btn2_sub_btn2.put("type","scancode_push");
        btn2_sub_btn2.put("name","扫一扫");
        btn2_sub_btn2.put("key","btn22");
        btn2_sub.add(btn2_sub_btn2);*/

        JSONObject btn2_sub_btna=new JSONObject();
        btn2_sub_btna.put("type","view");
        btn2_sub_btna.put("name","融资服务");
        btn2_sub_btna.put("url",wechatUtil.getPageAuthUrl("http://test.sumec.com/wechatpage/#/enterpriseRegistration?reqire=融资服务","0"));
        btn2_sub.add(btn2_sub_btna);
        JSONObject btn2_sub_btnb=new JSONObject();
        btn2_sub_btnb.put("type","view");
        btn2_sub_btnb.put("name","贸易代理");
        btn2_sub_btnb.put("url",wechatUtil.getPageAuthUrl("http://test.sumec.com/wechatpage/#/enterpriseRegistration?reqire=贸易代理","0"));
        btn2_sub.add(btn2_sub_btnb);



        JSONObject btn2_sub_btn2=new JSONObject();
        btn2_sub_btn2.put("type","view");
        btn2_sub_btn2.put("name","招标免税");
        btn2_sub_btn2.put("url",wechatUtil.getPageAuthUrl("http://test.sumec.com/wechatpage/#/enterpriseRegistration?reqire=招标免税","0"));
        btn2_sub.add(btn2_sub_btn2);


        JSONObject btn2_sub_btn3=new JSONObject();
        btn2_sub_btn3.put("type","view");
        btn2_sub_btn3.put("name","报关运输");
        btn2_sub_btn3.put("url",wechatUtil.getPageAuthUrl("http://test.sumec.com/wechatpage/#/enterpriseRegistration?reqire=报关运输","0"));
        btn2_sub.add(btn2_sub_btn3);
        JSONObject btn2_sub_btn4=new JSONObject();
        btn2_sub_btn4.put("type","view");
        btn2_sub_btn4.put("name","进口设备");
        btn2_sub_btn4.put("url",wechatUtil.getPageAuthUrl("http://test.sumec.com/wechatpage/#/enterpriseRegistration?reqire=进口设备","0"));
        btn2_sub.add(btn2_sub_btn4);

       /* btn2_sub_btn1.put("type","view");
        btn2_sub_btn1.put("name","我的执行合同");
        btn2_sub_btn1.put("url",wechatUtil.getPageAuthUrl("http://msrv.sumec.com/wechatpage/#/parHomePage","0"));
        btn2_sub.add(btn2_sub_btn1);
*/
    /*   JSONObject btn2_sub_btn3=new JSONObject();
       btn2_sub_btn3.put("type","click");
       btn2_sub_btn3.put("name","量子留聲機");
       btn2_sub_btn3.put("key","btn23");
       btn2_sub.add(btn2_sub_btn3);*/
       button2.put("sub_button",btn2_sub);
       buttons.add(button2);

        //第三组按钮
        JSONObject button3=new JSONObject();
        button3.put("name","我的");//第一个一级菜单
        JSONArray btn3_sub=new JSONArray();//第一个一级菜单的子菜单
        JSONObject btn3_sub_btn1=new JSONObject();
        btn3_sub_btn1.put("type","view");
        btn3_sub_btn1.put("name","执行合同");
        btn3_sub_btn1.put("url",wechatUtil.getPageAuthUrl("http://test.sumec.com/wechatpage/#/judgeContractPage","0"));
        btn3_sub.add(btn3_sub_btn1);
        JSONObject btn3_sub_btn2=new JSONObject();
        btn3_sub_btn2.put("type","view");
        btn3_sub_btn2.put("name","二维码");
        btn3_sub_btn2.put("url",wechatUtil.getPageAuthUrl("http://test.sumec.com/wechatpage/#/judgeHomePage","0"));
        btn3_sub.add(btn3_sub_btn2);
      /*  JSONObject btn3_sub_btn3=new JSONObject();
        btn3_sub_btn3.put("type","click");
        btn3_sub_btn3.put("name","联系我们");
        btn3_sub_btn3.put("key","btn33");
        btn3_sub.add(btn3_sub_btn3);*/
        button3.put("sub_button",btn3_sub);
        buttons.add(button3);

        menu.put("button",buttons);
        return menu;
    }

    public boolean createMenuByUrl(JSONObject menu){
        AccessToken at=getAccessToken(wxappid,wxappsecret);
        String token=at.getToken();
        String url=menu_url.replaceAll("ACCESS_TOKEN",token);
        JSONObject jsonObject = HttpsInvoker.httpRequest(url, "POST", menu.toJSONString());
        logger.debug("menu create:"+jsonObject.toJSONString());
        if (null != jsonObject) {
            if (0 != jsonObject.getIntValue("errcode")) {
                int result = jsonObject.getIntValue("errcode");
                logger.error("创建菜单失败 errcode:"+jsonObject.getIntValue("errcode")+",errmsg:"+jsonObject.getString("errmsg"));
            }else{
                logger.debug("菜单创建成功！");
            }
        }
        return true;
    }

    public boolean createMenu(){
        JSONObject menu=getMenu();//获得菜单
        AccessToken at=getAccessToken(wxappid,wxappsecret);
        String token=at.getToken();
        String url=menu_url.replaceAll("ACCESS_TOKEN",token);
        JSONObject jsonObject = HttpsInvoker.httpRequest(url, "POST", menu.toJSONString());
        logger.debug("menu create:"+jsonObject.toJSONString());
        if (null != jsonObject) {
            if (0 != jsonObject.getIntValue("errcode")) {
                int result = jsonObject.getIntValue("errcode");
                logger.error("创建菜单失败 errcode:"+jsonObject.getIntValue("errcode")+",errmsg:"+jsonObject.getString("errmsg"));
            }else{
                logger.debug("菜单创建成功！");
            }
        }
        return true;
    }

    public JSONObject getUserInfo(String openid){
        AccessToken at=getAccessToken(wxappid,wxappsecret);
        String url=user_info.replaceAll("ACCESS_TOKEN",at.getToken()).replaceAll("OPENID",openid);
        JSONObject jsonObject = HttpsInvoker.httpRequest(url, "GET", null);
        if (null != jsonObject) {
            if (0 != jsonObject.getIntValue("errcode")) {
                int result = jsonObject.getIntValue("errcode");
                logger.error("获取用户信息失败 errcode:"+jsonObject.getIntValue("errcode")+",errmsg:"+jsonObject.getString("errmsg"));
            }else{
                logger.debug("获取用户信息成功："+jsonObject.toJSONString());
            }
        }
        return jsonObject;
    }


    public JSONObject sendTextMsg(String openid,String text){
        AccessToken at=getAccessToken(wxappid,wxappsecret);
        //AccessToken at=getAccessToken("wxb85df50f1f75e679","c0e8667b1b3265d53d5708b0cdbab42b");
        String url=cus_msg.replaceAll("ACCESS_TOKEN",at.getToken());
        JSONObject msg=new JSONObject();
        msg.put("touser",openid);
        msg.put("msgtype","text");
        JSONObject content=new JSONObject();
        content.put("content",text);
        msg.put("text",content);
        JSONObject jsonObject = HttpsInvoker.httpRequest(url, "POST", msg.toJSONString());
        if (null != jsonObject) {
            if (0 != jsonObject.getIntValue("errcode")) {
                int result = jsonObject.getIntValue("errcode");
                logger.error("发送用户信息失败 errcode:"+jsonObject.getIntValue("errcode")+",errmsg:"+jsonObject.getString("errmsg"));
            }else{
                logger.debug("发送用户信息成功："+jsonObject.toJSONString());
            }
        }

        return jsonObject;
    }


    @Autowired
    WechatMessageLogService wechatMessageLogService;
    @Autowired
    WechatUserService wechatUserService;
    /**
     * 发送模板消息
     * @param openid
     * @param url
     * @param tid
     * @param post
     * @return
     */
    public JSONObject sendTemplateMsg(String openid,String url,String tid,JSONObject post ){
        AccessToken at=getAccessToken(wxappid,wxappsecret);
        if(at==null){
            return null;
        }
        String posturl=template_msg_url.replace("ACCESS_TOKEN",at.getToken());
        JSONObject param=new JSONObject();
        param.put("touser",openid);
        param.put("template_id",tid);
        param.put("url",url);
        param.put("data",post);
        JSONObject jsonObject = HttpsInvoker.httpRequest(posturl, "POST", param.toJSONString());
        if (null != jsonObject) {
            if (0 != jsonObject.getIntValue("errcode")) {
                int result = jsonObject.getIntValue("errcode");
                logger.error("发送用户信息失败 errcode:"+jsonObject.getIntValue("errcode")+",errmsg:"+jsonObject.getString("errmsg"));
            }else{
                WechatMessageLog log=new WechatMessageLog();
                log.setMessageBody(post.toJSONString());
                WechatUser user=wechatUserService.findUserByOpenid(openid);
                log.setWechatUser(user);
                log.setMessageTime(ZonedDateTime.now());
                log.setId(jsonObject.getString("msgid"));
                wechatMessageLogService.save(log);
                logger.debug("发送用户信息成功："+jsonObject.toJSONString());
            }
        }
        return jsonObject;
    }

    /**
     * 获取微信页面认证URL
     * @param url
     * @return
     */
    public String getPageAuthUrl(String url,String state){
        String baseurl="https://open.weixin.qq.com/connect/oauth2/authorize?appid=$(APPID)&redirect_uri=$(URL)&response_type=code&scope=snsapi_userinfo&state=$(STATE)";
        //if(wxappid==null)wxappid="wxf617bb39539b5f2e";
        baseurl=baseurl.replace("$(APPID)", wxappid);
        if(state==null)state="null";
        baseurl=baseurl.replace("$(STATE)", state);
        try {
            url= URLEncoder.encode(url,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        baseurl=baseurl.replace("$(URL)",url);
        return baseurl;
    }
/*  public static void main(String [] args){
        WechatUtil w=new WechatUtil();
        w.createMenu();
        System.out.println("创建成功");

   }*/
}
