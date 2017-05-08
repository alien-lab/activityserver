package com.alienlab.activityserver.web.wechat.controller;

import com.alienlab.activityserver.web.wechat.util.PayCommonUtil;
import com.alienlab.activityserver.web.wechat.util.XMLUtil;
import org.apache.log4j.Logger;
import org.jdom2.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 橘 on 2017/5/8.
 */
@RestController
public class PayController {
    private static Logger logger = Logger.getLogger(PayController.class);
    @Autowired
    PayCommonUtil payCommonUtil;
    @PostMapping(value="/wxpay")
    public ResponseEntity payCallback(HttpServletRequest request) throws IOException {
        InputStream inStream = request.getInputStream();
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        outSteam.close();
        inStream.close();
        String result  = new String(outSteam.toByteArray(),"utf-8");//获取微信调用我们notify_url的返回信息
        logger.info("收到付款成功通知>>>"+result);
        Map<String, String> map=new HashMap();
        try {
            map = XMLUtil.doXMLParse(result);
        } catch (JDOMException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        for(Object keyValue : map.keySet()){
            logger.info("通知字段："+keyValue+"="+map.get(keyValue));
        }
        if (map.get("return_code").toString().equalsIgnoreCase("SUCCESS")) {
            String resultcode=map.get("result_code");
            String orderno=map.get("out_trade_no");
            if(resultcode.equalsIgnoreCase("SUCCESS")){ //支付成功
                //此处订单支付成功逻辑
            }else{ //支付失败
                String message=map.get("err_code")+":"+map.get("err_code_des");
                //此处订单支付失败逻辑
            }
        }else{
            logger.error("获取微信支付通知接口失败");
        }
        //TODO 对数据库的操作
        return ResponseEntity.ok().body(payCommonUtil.setXML("SUCCESS", ""));   //告诉微信服务器，我收到信息了，不要在调用回调action了
    }
}
