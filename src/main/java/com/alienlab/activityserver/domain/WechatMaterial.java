package com.alienlab.activityserver.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A WechatMaterial.
 */

@Document(collection = "wechat_material")
public class WechatMaterial implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("btn_id")
    private String btnId;

    @Field("media_id")
    private String mediaId;

    @Field("craete_time")
    private ZonedDateTime craeteTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBtnId() {
        return btnId;
    }

    public WechatMaterial btnId(String btnId) {
        this.btnId = btnId;
        return this;
    }

    public void setBtnId(String btnId) {
        this.btnId = btnId;
    }

    public String getMediaId() {
        return mediaId;
    }

    public WechatMaterial mediaId(String mediaId) {
        this.mediaId = mediaId;
        return this;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public ZonedDateTime getCraeteTime() {
        return craeteTime;
    }

    public WechatMaterial craeteTime(ZonedDateTime craeteTime) {
        this.craeteTime = craeteTime;
        return this;
    }

    public void setCraeteTime(ZonedDateTime craeteTime) {
        this.craeteTime = craeteTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WechatMaterial wechatMaterial = (WechatMaterial) o;
        if (wechatMaterial.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, wechatMaterial.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "WechatMaterial{" +
            "id=" + id +
            ", btnId='" + btnId + "'" +
            ", mediaId='" + mediaId + "'" +
            ", craeteTime='" + craeteTime + "'" +
            '}';
    }
}
