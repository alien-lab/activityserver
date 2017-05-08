package com.alienlab.activityserver.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Objects;

/**
 * A WechatUser.
 */

@Document(collection = "wechat_user")
public class WechatUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("wechat_unionid")
    private String wechatUnionid;

    @Field("wechat_nickname")
    private String wechatNickname;

    @Field("wechat_image")
    private String wechatImage;

    @Field("wechat_area")
    private String wechatArea;

    @Field("user_type")
    private String userType;

    @Field("wechat_openid")
    private String wechatOpenid;

    @Field("wechat_qrkey")
    private String wechatQrkey;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWechatUnionid() {
        return wechatUnionid;
    }

    public WechatUser wechatUnionid(String wechatUnionid) {
        this.wechatUnionid = wechatUnionid;
        return this;
    }

    public void setWechatUnionid(String wechatUnionid) {
        this.wechatUnionid = wechatUnionid;
    }

    public String getWechatNickname() {
        return wechatNickname;
    }

    public WechatUser wechatNickname(String wechatNickname) {
        this.wechatNickname = wechatNickname;
        return this;
    }

    public void setWechatNickname(String wechatNickname) {
        this.wechatNickname = wechatNickname;
    }

    public String getWechatImage() {
        return wechatImage;
    }

    public WechatUser wechatImage(String wechatImage) {
        this.wechatImage = wechatImage;
        return this;
    }

    public void setWechatImage(String wechatImage) {
        this.wechatImage = wechatImage;
    }

    public String getWechatArea() {
        return wechatArea;
    }

    public WechatUser wechatArea(String wechatArea) {
        this.wechatArea = wechatArea;
        return this;
    }

    public void setWechatArea(String wechatArea) {
        this.wechatArea = wechatArea;
    }

    public String getUserType() {
        return userType;
    }

    public WechatUser userType(String userType) {
        this.userType = userType;
        return this;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getWechatOpenid() {
        return wechatOpenid;
    }

    public WechatUser wechatOpenid(String wechatOpenid) {
        this.wechatOpenid = wechatOpenid;
        return this;
    }

    public void setWechatOpenid(String wechatOpenid) {
        this.wechatOpenid = wechatOpenid;
    }

    public String getWechatQrkey() {
        return wechatQrkey;
    }

    public WechatUser wechatQrkey(String wechatQrkey) {
        this.wechatQrkey = wechatQrkey;
        return this;
    }

    public void setWechatQrkey(String wechatQrkey) {
        this.wechatQrkey = wechatQrkey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WechatUser wechatUser = (WechatUser) o;
        if (wechatUser.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, wechatUser.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "WechatUser{" +
            "id=" + id +
            ", wechatUnionid='" + wechatUnionid + "'" +
            ", wechatNickname='" + wechatNickname + "'" +
            ", wechatImage='" + wechatImage + "'" +
            ", wechatArea='" + wechatArea + "'" +
            ", userType='" + userType + "'" +
            ", wechatOpenid='" + wechatOpenid + "'" +
            ", wechatQrkey='" + wechatQrkey + "'" +
            '}';
    }
}
