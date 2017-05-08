package com.alienlab.activityserver.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A QrType.
 */

@Document(collection = "qr_type")
public class QrType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("qr_type_name")
    private String qrTypeName;

    @Field("qr_type_table")
    private String qrTypeTable;

    @Field("qr_type_idfield")
    private String qrTypeIdfield;

    @Field("qr_type_namefield")
    private String qrTypeNamefield;

    @Field("qr_type_reptype")
    private String qrTypeReptype;

    @Field("qr_type_url")
    private String qrTypeUrl;

    @Field("qr_type_cttime")
    private ZonedDateTime qrTypeCttime;

    @Field("qr_type_status")
    private String qrTypeStatus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQrTypeName() {
        return qrTypeName;
    }

    public QrType qrTypeName(String qrTypeName) {
        this.qrTypeName = qrTypeName;
        return this;
    }

    public void setQrTypeName(String qrTypeName) {
        this.qrTypeName = qrTypeName;
    }

    public String getQrTypeTable() {
        return qrTypeTable;
    }

    public QrType qrTypeTable(String qrTypeTable) {
        this.qrTypeTable = qrTypeTable;
        return this;
    }

    public void setQrTypeTable(String qrTypeTable) {
        this.qrTypeTable = qrTypeTable;
    }

    public String getQrTypeIdfield() {
        return qrTypeIdfield;
    }

    public QrType qrTypeIdfield(String qrTypeIdfield) {
        this.qrTypeIdfield = qrTypeIdfield;
        return this;
    }

    public void setQrTypeIdfield(String qrTypeIdfield) {
        this.qrTypeIdfield = qrTypeIdfield;
    }

    public String getQrTypeNamefield() {
        return qrTypeNamefield;
    }

    public QrType qrTypeNamefield(String qrTypeNamefield) {
        this.qrTypeNamefield = qrTypeNamefield;
        return this;
    }

    public void setQrTypeNamefield(String qrTypeNamefield) {
        this.qrTypeNamefield = qrTypeNamefield;
    }

    public String getQrTypeReptype() {
        return qrTypeReptype;
    }

    public QrType qrTypeReptype(String qrTypeReptype) {
        this.qrTypeReptype = qrTypeReptype;
        return this;
    }

    public void setQrTypeReptype(String qrTypeReptype) {
        this.qrTypeReptype = qrTypeReptype;
    }

    public String getQrTypeUrl() {
        return qrTypeUrl;
    }

    public QrType qrTypeUrl(String qrTypeUrl) {
        this.qrTypeUrl = qrTypeUrl;
        return this;
    }

    public void setQrTypeUrl(String qrTypeUrl) {
        this.qrTypeUrl = qrTypeUrl;
    }

    public ZonedDateTime getQrTypeCttime() {
        return qrTypeCttime;
    }

    public QrType qrTypeCttime(ZonedDateTime qrTypeCttime) {
        this.qrTypeCttime = qrTypeCttime;
        return this;
    }

    public void setQrTypeCttime(ZonedDateTime qrTypeCttime) {
        this.qrTypeCttime = qrTypeCttime;
    }

    public String getQrTypeStatus() {
        return qrTypeStatus;
    }

    public QrType qrTypeStatus(String qrTypeStatus) {
        this.qrTypeStatus = qrTypeStatus;
        return this;
    }

    public void setQrTypeStatus(String qrTypeStatus) {
        this.qrTypeStatus = qrTypeStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        QrType qrType = (QrType) o;
        if (qrType.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, qrType.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "QrType{" +
            "id=" + id +
            ", qrTypeName='" + qrTypeName + "'" +
            ", qrTypeTable='" + qrTypeTable + "'" +
            ", qrTypeIdfield='" + qrTypeIdfield + "'" +
            ", qrTypeNamefield='" + qrTypeNamefield + "'" +
            ", qrTypeReptype='" + qrTypeReptype + "'" +
            ", qrTypeUrl='" + qrTypeUrl + "'" +
            ", qrTypeCttime='" + qrTypeCttime + "'" +
            ", qrTypeStatus='" + qrTypeStatus + "'" +
            '}';
    }
}
