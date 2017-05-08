package com.alienlab.activityserver.web.rest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class ExecResult {
	private int result=0;
	private String message="";
	private JSON data=null;
	private int errorCode = 0 ;//错误异常
	private String errormsg = "";
	public ExecResult(){

	}
	public ExecResult(boolean result, String message){
		this.result=result?1:0;
		this.message=message;
		if(!result){
			this.errormsg=message;
		}
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String reason) {
		this.message = reason;
	}
	public int getResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result?1:0;
	}
	public JSON getData() {
		return data;
	}
	public void setData(JSON data) {
		this.data = data;
	}
	public String toString(){
		return JSONObject.toJSONString(this);
	}
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	/**
	 * @return the errormsg
	 */
	public String getErrormsg() {
		return errormsg;
	}
	/**
	 * @param errormsg the errormsg to set
	 */
	public void setErrormsg(String errormsg) {
		this.errormsg = errormsg;
	}





}
