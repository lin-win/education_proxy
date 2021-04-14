package com.xiao.diploma_system.services;

import com.xiao.diploma_system.entity.Apply;
import com.xiao.diploma_system.entity.Revoke;
import com.xiao.diploma_system.entity.Student;

import java.util.List;

public interface StudentServices {
    /**
     * 登陆
     * @param id
     * @param password
     * @return
     * @throws Exception
     */
    boolean studentLogin(int id,String password) throws Exception;


    /**
     * 注册学生
     * @param student
     * @return
     * @throws Exception
     */
    boolean registerStudent(Student student) throws Exception;

    /**
     * 获取已经注册的学生数
     * @return
     * @throws Exception
     */
    int getStudentAccount() throws Exception;

    /**
     * 通过账户地址获取学生的申请学历信息
     * @param address
     * @return
     * @throws Exception
     */
    Apply getApplyInfoByAddr(String address) throws Exception;

    /**
     * 通过ID获取学生的账户地址
     * @param id
     * @return
     * @throws Exception
     */
    String getAdressByID(int id) throws Exception;

    /**
     * 通过账户地址获取学生信息
     * @param address
     * @return
     * @throws Exception
     */
    Student getStudentInfoByAddress(String address) throws Exception;


    /**
     * 通过账户地址判断是否存有学历
     * @param address
     * @return
     * @throws Exception
     */
    boolean exsitEdu(String address) throws Exception;

    /**
     * 更改头像
     * @param address
     * @return
     * @throws Exception
     */
    boolean setImage(String address,String _image) throws Exception;

    /**
     * 注销学历
     * @param revoke
     * @return
     * @throws Exception
     */
    String revokeEdu(Revoke revoke) throws Exception;

    /**
     * 注销学生
     * @param address
     * @param id
     * @param password
     * @return
     * @throws Exception
     */
    boolean revokeStu(String address,int id,String password) throws Exception;

    /**
     * 申请学历
     * @param address
     * @param apply
     * @return
     * @throws Exception
     */
    String applyDiploma(String address,Apply apply) throws Exception;

    /**
     * 更新学生信息
     * @param address
     * @param student
     * @return
     * @throws Exception
     */
    boolean updateStudentInfo(String address,Student student) throws Exception;

    /**
     * 通过账户地址获取学生的撤销信息
     * @param address
     * @return
     * @throws Exception
     */
    Revoke getRevokeInfoByAddr(String address) throws Exception;

    /**
     * 判断是否存在该账户
     * @param address
     * @return
     * @throws Exception
     */
    boolean ifExsit(String address) throws Exception;

    /**
     * 获取所有学生信息
     * @return
     * @throws Exception
     */
    List<Student> getallStudent() throws Exception;

    /**
     * 获取所有学生信息
     * @return
     * @throws Exception
     */
    List<Student> getallPassStudent() throws Exception;

    /**
     * 获取所有学生地址
     * @return
     * @throws Exception
     */
    public List<String> getallStudentAddress() throws Exception;
}
