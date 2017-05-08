package com.alienlab.activityserver.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A WechatMessageLog.
 */

@Document(collection = "wechat_message_log")
public class WechatMessageLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("message_time")
    private ZonedDateTime messageTime;

    @Field("message_body")
    private String messageBody;

    @Field("message_status")
    private String messageStatus;

    @DBRef
    private WechatUser wechatUser;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ZonedDateTime getMessageTime() {
        return messageTime;
    }

    public WechatMessageLog messageTime(ZonedDateTime messageTime) {
        this.messageTime = messageTime;
        return this;
    }

    public void setMessageTime(ZonedDateTime messageTime) {
        this.messageTime = messageTime;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public WechatMessageLog messageBody(String messageBody) {
        this.messageBody = messageBody;
        return this;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public String getMessageStatus() {
        return messageStatus;
    }

    public WechatMessageLog messageStatus(String messageStatus) {
        this.messageStatus = messageStatus;
        return this;
    }

    public void setMessageStatus(String messageStatus) {
        this.messageStatus = messageStatus;
    }

    public WechatUser getWechatUser() {
        return wechatUser;
    }

    public WechatMessageLog wechatUser(WechatUser wechatUser) {
        this.wechatUser = wechatUser;
        return this;
    }

    public void setWechatUser(WechatUser wechatUser) {
        this.wechatUser = wechatUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WechatMessageLog wechatMessageLog = (WechatMessageLog) o;
        if (wechatMessageLog.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, wechatMessageLog.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "WechatMessageLog{" +
            "id=" + id +
            ", messageTime='" + messageTime + "'" +
            ", messageBody='" + messageBody + "'" +
            ", messageStatus='" + messageStatus + "'" +
            ", wechatUser='" + wechatUser + "'" +
            '}';
    }
}
