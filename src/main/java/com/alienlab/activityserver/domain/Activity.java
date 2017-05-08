package com.alienlab.activityserver.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Activity.
 */

@Document(collection = "activity")
public class Activity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("act_name")
    private String actName;

    @NotNull
    @Field("act_date")
    private ZonedDateTime actDate;

    @Field("act_playercount")
    private Integer actPlayercount;

    @Field("act_price_1")
    private Float actPrice1;

    @Field("act_price_2")
    private Float actPrice2;

    @Field("act_desc")
    private String actDesc;

    @Field("act_contact")
    private String actContact;

    @Field("act_contact_phone")
    private String actContactPhone;

    @Field("act_status")
    private String actStatus;

    @Field("act_image")
    private String actImage;

    @NotNull
    @Field("act_flag")
    private String actFlag;

    @Field("act_qrkey")
    private String actQrkey;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getActName() {
        return actName;
    }

    public Activity actName(String actName) {
        this.actName = actName;
        return this;
    }

    public void setActName(String actName) {
        this.actName = actName;
    }

    public ZonedDateTime getActDate() {
        return actDate;
    }

    public Activity actDate(ZonedDateTime actDate) {
        this.actDate = actDate;
        return this;
    }

    public void setActDate(ZonedDateTime actDate) {
        this.actDate = actDate;
    }

    public Integer getActPlayercount() {
        return actPlayercount;
    }

    public Activity actPlayercount(Integer actPlayercount) {
        this.actPlayercount = actPlayercount;
        return this;
    }

    public void setActPlayercount(Integer actPlayercount) {
        this.actPlayercount = actPlayercount;
    }

    public Float getActPrice1() {
        return actPrice1;
    }

    public Activity actPrice1(Float actPrice1) {
        this.actPrice1 = actPrice1;
        return this;
    }

    public void setActPrice1(Float actPrice1) {
        this.actPrice1 = actPrice1;
    }

    public Float getActPrice2() {
        return actPrice2;
    }

    public Activity actPrice2(Float actPrice2) {
        this.actPrice2 = actPrice2;
        return this;
    }

    public void setActPrice2(Float actPrice2) {
        this.actPrice2 = actPrice2;
    }

    public String getActDesc() {
        return actDesc;
    }

    public Activity actDesc(String actDesc) {
        this.actDesc = actDesc;
        return this;
    }

    public void setActDesc(String actDesc) {
        this.actDesc = actDesc;
    }

    public String getActContact() {
        return actContact;
    }

    public Activity actContact(String actContact) {
        this.actContact = actContact;
        return this;
    }

    public void setActContact(String actContact) {
        this.actContact = actContact;
    }

    public String getActContactPhone() {
        return actContactPhone;
    }

    public Activity actContactPhone(String actContactPhone) {
        this.actContactPhone = actContactPhone;
        return this;
    }

    public void setActContactPhone(String actContactPhone) {
        this.actContactPhone = actContactPhone;
    }

    public String getActStatus() {
        return actStatus;
    }

    public Activity actStatus(String actStatus) {
        this.actStatus = actStatus;
        return this;
    }

    public void setActStatus(String actStatus) {
        this.actStatus = actStatus;
    }

    public String getActImage() {
        return actImage;
    }

    public Activity actImage(String actImage) {
        this.actImage = actImage;
        return this;
    }

    public void setActImage(String actImage) {
        this.actImage = actImage;
    }

    public String getActFlag() {
        return actFlag;
    }

    public Activity actFlag(String actFlag) {
        this.actFlag = actFlag;
        return this;
    }

    public void setActFlag(String actFlag) {
        this.actFlag = actFlag;
    }

    public String getActQrkey() {
        return actQrkey;
    }

    public Activity actQrkey(String actQrkey) {
        this.actQrkey = actQrkey;
        return this;
    }

    public void setActQrkey(String actQrkey) {
        this.actQrkey = actQrkey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Activity activity = (Activity) o;
        if (activity.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, activity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Activity{" +
            "id=" + id +
            ", actName='" + actName + "'" +
            ", actDate='" + actDate + "'" +
            ", actPlayercount='" + actPlayercount + "'" +
            ", actPrice1='" + actPrice1 + "'" +
            ", actPrice2='" + actPrice2 + "'" +
            ", actDesc='" + actDesc + "'" +
            ", actContact='" + actContact + "'" +
            ", actContactPhone='" + actContactPhone + "'" +
            ", actStatus='" + actStatus + "'" +
            ", actImage='" + actImage + "'" +
            ", actFlag='" + actFlag + "'" +
            ", actQrkey='" + actQrkey + "'" +
            '}';
    }
}
