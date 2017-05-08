package com.alienlab.activityserver.web.wechat.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by 橘 on 2016/12/23.
 */
@XmlRootElement(name="xml")
@XmlAccessorType(XmlAccessType.NONE)
public class TextMessageResponse extends MessageResponse {
    public TextMessageResponse(String content){
        super();
        this.Content=content;
    }
    public TextMessageResponse(){
        super();
    }
    @XmlElement
    private String Content;
    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

}
