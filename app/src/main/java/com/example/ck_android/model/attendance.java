package com.example.ck_android.model;

import java.io.Serializable;

public class attendance implements Serializable {
    String ngay;
    String id;
    String trangthai;

    public String getIdsv() {
        return idsv;
    }

    public void setIdsv(String idsv) {
        this.idsv = idsv;
    }

    String idsv;

    public String getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }

    public attendance(String ngay, String idsv, String trangthai) {
        this.ngay = ngay;
        this.idsv = idsv;
        this.trangthai = trangthai;
    }

    public attendance(){}

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
