package com.xiao.diploma_system.entity;

public class Apply {
    String school  ;//学校
    String major  ;//专业
    String time  ;//时间
    String eduType  ;//教育层次
    String eduHash  ;//学历编号
    String address;
    Boolean isUsed;

    public Apply() {
        this.school="";
        this.major="";
        this.time="";
        this.eduHash="";
        this.eduType="";
    }

    @Override
    public String toString() {
        return "Apply{" +
                "school='" + school + '\'' +
                ", major='" + major + '\'' +
                ", time='" + time + '\'' +
                ", eduType='" + eduType + '\'' +
                ", eduHash='" + eduHash + '\'' +
                ", isUsed=" + isUsed +
                '}';
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getEduType() {
        return eduType;
    }

    public void setEduType(String eduType) {
        this.eduType = eduType;
    }

    public String getEduHash() {
        return eduHash;
    }

    public void setEduHash(String eduHash) {
        this.eduHash = eduHash;
    }

    public Boolean getUsed() {
        return isUsed;
    }

    public void setUsed(Boolean used) {
        isUsed = used;
    }

    public Apply(String school, String major, String time, String eduType, String eduHash, Boolean isUsed) {
        this.school = school;
        this.major = major;
        this.time = time;
        this.eduType = eduType;
        this.eduHash = eduHash;
        this.isUsed = isUsed;
    }
}
