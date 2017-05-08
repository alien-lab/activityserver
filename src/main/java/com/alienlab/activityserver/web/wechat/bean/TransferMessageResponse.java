package com.alienlab.activityserver.web.wechat.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by 橘 on 2017/3/29.
 */
@XmlRootElement(name="xml")
@XmlAccessorType(XmlAccessType.NONE)
public class TransferMessageResponse  extends MessageResponse  {
    public TransferMessageResponse(){
        super();
    }
}
