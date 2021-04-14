package com.xiao.diploma_system.dao;

import com.xiao.diploma_system.entity.Apply;
import com.xiao.diploma_system.entity.Revoke;
import com.xiao.diploma_system.entity.Student;
import org.springframework.stereotype.Repository;
import org.web3j.crypto.CipherException;

import java.io.IOException;
import java.math.BigInteger;

public interface StudentDao {
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
     * 通过index获取学生的标识ID
     * @param index
     * @return
     * @throws Exception
     */
    int getIDByIndex(int index) throws Exception;

    /**
     * 通过账户地址获取学生的撤销信息
     * @param address
     * @return
     * @throws Exception
     */
    Revoke getRevokeInfoByAddr(String address) throws Exception;

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
     * 登陆
     * @param id
     * @param password
     * @return
     * @throws Exception
     */
    boolean studentLogin(int id,String password) throws Exception;

    /**
     * 判断是否存在该账户
     * @param address
     * @return
     * @throws Exception
     */
    boolean ifExsit(String address) throws Exception;

    /**
     * 通过账户地址判断是否存有学历
     * @param address
     * @return
     * @throws Exception
     */
    boolean exsitEdu(String address) throws Exception;

    /**
     * 更改学生状态
     * @param address
     * @param state
     * @param index
     * @return
     * @throws Exception
     */
    boolean setState(String address,int state, int index) throws Exception;

    /**
     * 更改学生头像
     * @param address
     * @param state
     * @param index
     * @return
     * @throws Exception
     */
    boolean setImage(String address,String _image) throws Exception;

    /**
     * 更新学生信息
     * @param address
     * @param student
     * @return
     * @throws Exception
     */
    boolean updateStudentInfo(String address,Student student) throws Exception;

    /**
     * 通过账户地址获取学生的学历信息
     * @param student
     * @param address
     * @return
     * @throws Exception
     */
    Student queryDiploma(Student student,String address) throws Exception;

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


}
