package com.alienlab.activityserver.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A JoinList.
 */

@Document(collection = "join_list")
public class JoinList implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("join_name")
    private String joinName;

    @Field("join_time")
    private ZonedDateTime joinTime;

    @Field("join_openid")
    private String joinOpenid;

    @Field("join_phone")
    private String joinPhone;

    @Field("join_nick")
    private String joinNick;

    @Field("join_icon")
    private String joinIcon;

    @Field("join_status")
    private String joinStatus;

    @Field("join_price_1")
    private Float joinPrice1;

    @Field("join_price_2")
    private Float joinPrice2;

    @Field("join_entercode")
    private String joinEntercode;

    @DBRef
    private Activity activity;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJoinName() {
        return joinName;
    }

    public JoinList joinName(String joinName) {
        this.joinName = joinName;
        return this;
    }

    public void setJoinName(String joinName) {
        this.joinName = joinName;
    }

    public ZonedDateTime getJoinTime() {
        return joinTime;
    }

    public JoinList joinTime(ZonedDateTime joinTime) {
        this.joinTime = joinTime;
        return this;
    }

    public void setJoinTime(ZonedDateTime joinTime) {
        this.joinTime = joinTime;
    }

    public String getJoinOpenid() {
        return joinOpenid;
    }

    public JoinList joinOpenid(String joinOpenid) {
        this.joinOpenid = joinOpenid;
        return this;
    }

    public void setJoinOpenid(String joinOpenid) {
        this.joinOpenid = joinOpenid;
    }

    public String getJoinPhone() {
        return joinPhone;
    }

    public JoinList joinPhone(String joinPhone) {
        this.joinPhone = joinPhone;
        return this;
    }

    public void setJoinPhone(String joinPhone) {
        this.joinPhone = joinPhone;
    }

    public String getJoinNick() {
        return joinNick;
    }

    public JoinList joinNick(String joinNick) {
        this.joinNick = joinNick;
        return this;
    }

    public void setJoinNick(String joinNick) {
        this.joinNick = joinNick;
    }

    public String getJoinIcon() {
        return joinIcon;
    }

    public JoinList joinIcon(String joinIcon) {
        this.joinIcon = joinIcon;
        return this;
    }

    public void setJoinIcon(String joinIcon) {
        this.joinIcon = joinIcon;
    }

    public String getJoinStatus() {
        return joinStatus;
    }

    public JoinList joinStatus(String joinStatus) {
        this.joinStatus = joinStatus;
        return this;
    }

    public void setJoinStatus(String joinStatus) {
        this.joinStatus = joinStatus;
    }

    public Float getJoinPrice1() {
        return joinPrice1;
    }

    public JoinList joinPrice1(Float joinPrice1) {
        this.joinPrice1 = joinPrice1;
        return this;
    }

    public void setJoinPrice1(Float joinPrice1) {
        this.joinPrice1 = joinPrice1;
    }

    public Float getJoinPrice2() {
        return joinPrice2;
    }

    public JoinList joinPrice2(Float joinPrice2) {
        this.joinPrice2 = joinPrice2;
        return this;
    }

    public void setJoinPrice2(Float joinPrice2) {
        this.joinPrice2 = joinPrice2;
    }

    public String getJoinEntercode() {
        return joinEntercode;
    }

    public JoinList joinEntercode(String joinEntercode) {
        this.joinEntercode = joinEntercode;
        return this;
    }

    public void setJoinEntercode(String joinEntercode) {
        this.joinEntercode = joinEntercode;
    }

    public Activity getActivity() {
        return activity;
    }

    public JoinList activity(Activity activity) {
        this.activity = activity;
        return this;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JoinList joinList = (JoinList) o;
        if (joinList.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, joinList.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "JoinList{" +
            "id=" + id +
            ", joinName='" + joinName + "'" +
            ", joinTime='" + joinTime + "'" +
            ", joinOpenid='" + joinOpenid + "'" +
            ", joinPhone='" + joinPhone + "'" +
            ", joinNick='" + joinNick + "'" +
            ", joinIcon='" + joinIcon + "'" +
            ", joinStatus='" + joinStatus + "'" +
            ", joinPrice1='" + joinPrice1 + "'" +
            ", joinPrice2='" + joinPrice2 + "'" +
            ", joinEntercode='" + joinEntercode + "'" +
            ", activity='" + activity + "'" +
            '}';
    }
}
