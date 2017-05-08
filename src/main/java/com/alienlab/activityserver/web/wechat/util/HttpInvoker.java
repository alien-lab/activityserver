package com.alienlab.activityserver.web.wechat.util;


import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * HTTP请求�?
 * @author LiHong
 */
public class HttpInvoker {
	private static Logger logger = Logger.getLogger(HttpInvoker.class);
	/**
	 * GET请求
	 * @param getUrl
	 * @throws IOException
	 * @return 提取HTTP响应报文包体，以字符串形式返�?
	 */
	public static String httpGet(String getUrl) throws IOException {
		URL getURL = new URL(getUrl);
		logger.info("发�?�http get请求�?"+getUrl);
		HttpURLConnection connection = (HttpURLConnection) getURL.openConnection();
		connection.connect();
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		StringBuilder sbStr = new StringBuilder();
		String line;
		while ((line = bufferedReader.readLine()) != null) {
			sbStr.append(line);
		}
		bufferedReader.close();
		connection.disconnect();
		String result=new String(sbStr.toString().getBytes(),"utf-8");
		logger.info("请求完成，响应内容："+result);
		return result;
	}
	public static JSONObject httpJsonGet(String getUrl) throws IOException {
		String rst=httpGet(getUrl);
		JSONObject json=JSONObject.parseObject(rst);
		return json;
	}
	/**
	 * POST请求
	 * @param postUrl
	 * @param postHeaders
	 * @param postEntity
	 * @throws IOException
	 * @return 提取HTTP响应报文包体，以字符串形式返�?
	 */
	public static String httpPost(String postUrl,Map<String, String> postHeaders, String postEntity) throws IOException {
		URL postURL = new URL(postUrl);
		logger.info("发�?�http post请求�?"+postUrl);
		logger.info("Post内容�?"+postEntity);
		HttpURLConnection httpURLConnection = (HttpURLConnection) postURL.openConnection();
		httpURLConnection.setDoOutput(true);
		httpURLConnection.setDoInput(true);
		httpURLConnection.setRequestMethod("POST");
		httpURLConnection.setUseCaches(false);
		httpURLConnection.setInstanceFollowRedirects(true);
		httpURLConnection.setRequestProperty(" Content-Type ", " application/x-www-form-urlencoded ");
		if(postHeaders != null) {
			for(String pKey : postHeaders.keySet()) {
				httpURLConnection.setRequestProperty(pKey, postHeaders.get(pKey));
			}
		}
		if(postEntity != null) {
			DataOutputStream out = new DataOutputStream(httpURLConnection.getOutputStream());
			out.writeBytes(postEntity);
			out.flush();
			out.close(); // flush and close
		}
		//connection.connect();
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
		StringBuilder sbStr = new StringBuilder();
		String line;
		while ((line = bufferedReader.readLine()) != null) {
			sbStr.append(line);
		}
		bufferedReader.close();
		httpURLConnection.disconnect();
		String result=new String(sbStr.toString().getBytes(),"utf-8");
		logger.info("请求完成，响应内容："+result);
		return result;
	}

	public static JSONObject httpJsonPost(String postUrl,Map<String, String> postHeaders, String postEntity) throws IOException {
		String rst=httpPost(postUrl,postHeaders,postEntity);
		JSONObject json=JSONObject.parseObject(rst);
		return json;
	}

//	public static void main(String [] args){
//		String jo="";
//		try {
//			jo = HttpInvoker.httpPost("http://210.28.101.76/do?invoke=AppAction@Login",
//					null,
//					"loginname=1402753102&loginpwd=442312502");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println(jo);
//	}
}
