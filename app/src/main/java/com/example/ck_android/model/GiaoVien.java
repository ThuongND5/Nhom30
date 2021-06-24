package com.example.ck_android.model;

import java.io.Serializable;

public class GiaoVien implements Serializable{
    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public String getTpass() {
        return tpass;
    }

    public void setTpass(String tpass) {
        this.tpass = tpass;
    }

    String tname;
    String tid;
    String subject;
    String classes;
    String tpass;

    public GiaoVien( String tid,String tname, String subject, String classes, String tpass) {
        this.tid = tid;
        this.tname = tname;
        this.subject = subject;
        this.classes = classes;
        this.tpass = tpass;
    }
    public GiaoVien(){
    }
    @Override
    public String toString() {
        return "GiaoVien{" +
                "id='" + tid  + '\'' +
                ", Mssv='" + tname + '\'' +
                ", HoTen='" + classes + '\'' +
                ", Email='" + subject + '\'' +
                ", SoDienThoai='" + tpass + '\'' +
                '}';
    }

}
