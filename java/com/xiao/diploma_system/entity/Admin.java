package com.xiao.diploma_system.entity;

public class Admin {
    String id ;//ID
    String password ;//密码

    @Override
    public String toString() {
        return "admin{" +
                "id='" + id + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Admin(String id, String password) {
        this.id = id;
        this.password = password;
    }
}
