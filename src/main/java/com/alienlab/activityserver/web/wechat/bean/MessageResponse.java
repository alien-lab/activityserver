package com.alienlab.activityserver.web.wechat.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.io.Serializable;

/**
 * Created by 橘 on 2016/12/23.
 */
@XmlSeeAlso({
        TextMessageResponse.class,
        NewsMessageResponse.class
})
@XmlAccessorType(XmlAccessType.NONE)
public abstract  class MessageResponse implements Serializable {
    @XmlElement
    private String ToUserName;
    @XmlElement
    private String FromUserName;
    @XmlElement
    private Long CreateTime;
    @XmlElement
    private String MsgType;

    public String getToUserName() {
        return ToUserName;
    }

    public void setToUserName(String toUserName) {
        ToUserName = toUserName;
    }

    public String getFromUserName() {
        return FromUserName;
    }

    public void setFromUserName(String fromUserName) {
        FromUserName = fromUserName;
    }

    public Long getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(Long createTime) {
        CreateTime = createTime;
    }

    public String getMsgType() {
        return MsgType;
    }

    public void setMsgType(String msgType) {
        MsgType = msgType;
    }
}
