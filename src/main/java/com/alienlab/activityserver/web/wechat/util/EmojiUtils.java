package com.alienlab.activityserver.web.wechat.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhuliang on 2017/4/6.
 */
public class EmojiUtils {
    public static String filter(String str){
        if(str == null || str.length() == 0){
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for(int i=0;i<str.length();i++){
            int ch = str.charAt(i);
            int min = Integer.parseInt("E001", 16);
            int max = Integer.parseInt("E537", 16);
            if(ch >= min && ch <= max){
                sb.append("");
            }else{
                sb.append((char)ch);
            }
        }
        return sb.toString();
    }

    /**
     * 过滤昵称特殊表情
     */
    public static String filterName(String name) {
        if(name==null){
            return null;

        }
        if("".equals(name.trim())){
            return "";
        }

        Pattern patter = Pattern.compile("[a-zA-Z0-9\u4e00-\u9fa5]");
        Matcher match = patter.matcher(name);

        StringBuffer buffer = new StringBuffer();

        while (match.find()) {
            buffer.append(match.group());
        }

        return buffer.toString();
    }

}
