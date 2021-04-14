package com.xiao.diploma_system.entity;

public class Revoke {
    String address;//账户地址
    String sid  ;//学号
    String name  ;//姓名
    String reason  ;//原因

    public Revoke() {
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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }



    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    @Override
    public String toString() {
        return "Revoke{" +
                "address='" + address + '\'' +
                ", sid='" + sid + '\'' +
                ", name='" + name + '\'' +
                ", reason='" + reason + '\'' +
                '}';
    }

    public Revoke(String address, String sid, String name, String reason) {
        this.address = address;
        this.sid = sid;
        this.name = name;
        this.reason = reason;
    }
}
