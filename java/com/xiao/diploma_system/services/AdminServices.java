package com.xiao.diploma_system.services;

import com.xiao.diploma_system.entity.Apply;
import com.xiao.diploma_system.entity.BlackList;

import java.util.List;

public interface AdminServices {
    /**
     * 登陆
     * @param id
     * @param password
     * @return
     * @throws Exception
     */
    boolean adminLogin(String id,String password) throws Exception;

    /**
     * 确认学历
     * @param address
     * @return
     * @throws Exception
     */
    boolean comfirmEdu(String address) throws Exception;

    /**
     * 添加黑名单
     * @param blackList
     * @param address
     * @return
     * @throws Exception
     */
    boolean addBlackList(BlackList blackList, String address) throws Exception;

    /**
     * 撤销黑名单
     * @param index
     * @return
     * @throws Exception
     */
    boolean revokeBlackList(int index) throws Exception;

    /**
     * 通过index获取黑名单
     * @param index
     * @return
     * @throws Exception
     */
    BlackList getBlackListInfoByIndex(int index) throws Exception;

    /**
     * 获取黑名单总数
     * @return
     * @throws Exception
     */
    int getBltAccount() throws Exception;

    /**
     * 获取申请总数
     * @return
     * @throws Exception
     */
    int getApplyAccount() throws Exception;

    /**
     * 获取所有黑名单
     * @return
     * @throws Exception
     */
    List<BlackList> getAllBlackList()throws Exception;

    /**
     * 获取所有学历申请
     * @return
     * @throws Exception
     */
    List<Apply> getAllApply()throws Exception;


}
