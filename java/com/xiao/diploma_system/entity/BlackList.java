package com.xiao.diploma_system.entity;

public class BlackList {
    int index;
    String address;//账户地址
    String sid  ;//学号
    String name  ;//姓名
    String school  ;//学校
    String reason ;//原因

    public BlackList(String sid, String name, String school, String reason, boolean isUsed) {
        this.sid = sid;
        this.name = name;
        this.school = school;
        this.reason = reason;
        this.isUsed = isUsed;
    }


    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    boolean isUsed;

    public BlackList() {
    }



    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }

    @Override
    public String toString() {
        return "BlackList{" +
                "address='" + address + '\'' +
                ", sid='" + sid + '\'' +
                ", name='" + name + '\'' +
                ", school='" + school + '\'' +
                ", reason='" + reason + '\'' +
                ", isUsed=" + isUsed +
                '}';
    }
}
