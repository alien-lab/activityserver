package com.alienlab.activityserver.web.wechat.bean;

import org.springframework.stereotype.Component;

@Component
public class JSApiTicket {
	private String errcode;
	private String errmsg;
	private String ticket;
	private String expires_in;
	private long ticketTime;
	public String getErrcode() {
		return errcode;
	}
	public void setErrcode(String errcode) {
		this.errcode = errcode;
	}
	public String getErrmsg() {
		return errmsg;
	}
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	public String getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(String expires_in) {
		this.expires_in = expires_in;
	}
	public long getTicketTime() {
		return ticketTime;
	}
	public void setTicketTime(long ticketTime) {
		this.ticketTime = ticketTime;
	}
}
