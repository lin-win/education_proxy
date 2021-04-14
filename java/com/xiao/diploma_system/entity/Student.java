package com.xiao.diploma_system.entity;

public class Student {
    int id  ;//用户ID
    String address;//账户地址
    String sid  ;//学号
    String name  ;//姓名
    String password ; //密码
    String school  ;//学校
    String major  ;//专业
    String eduHash  ;//学历编号
    String time  ;//时间
    int state  ;//状态
    int index  ;//索引号
    String eduType  ;//教育层次
    String image   ;//头像
    Boolean ExsitEdu  ;//该地址是否被使用

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", sid='" + sid + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", school='" + school + '\'' +
                ", major='" + major + '\'' +
                ", eduHash='" + eduHash + '\'' +
                ", time='" + time + '\'' +
                ", state=" + state +
                ", index=" + index +
                ", eduType='" + eduType + '\'' +
                ", image='" + image + '\'' +
                ", ExsitEdu=" + ExsitEdu +
                '}';
    }

    public Student(int id, String address, String sid, String name, String password, String school, String major, String eduHash, String time, int state, int index, String eduType, String image, Boolean exsitEdu) {
        this.id = id;
        this.address = address;
        this.sid = sid;
        this.name = name;
        this.password = password;
        this.school = school;
        this.major = major;
        this.eduHash = eduHash;
        this.time = time;
        this.state = state;
        this.index = index;
        this.eduType = eduType;
        this.image = image;
        ExsitEdu = exsitEdu;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Boolean getExsitEdu() {
        return ExsitEdu;
    }

    public void setExsitEdu(Boolean exsitEdu) {
        ExsitEdu = exsitEdu;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public Student() {
        this.name="未登录";
        this.image="../dist/img/tourist.jpg";
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getEduHash() {
        return eduHash;
    }

    public void setEduHash(String eduHash) {
        this.eduHash = eduHash;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getEduType() {
        return eduType;
    }

    public void setEduType(String eduType) {
        this.eduType = eduType;
    }




}
