package com.xiao.diploma_system.dao;

import com.xiao.diploma_system.entity.BlackList;

public interface AdminDao {
    /**
     * 登陆
     * * 接口名称参数 id
     *              password
     * @return
     * @throws Exception
     */
    boolean adminLogin(String id,String password) throws Exception;

    /**
     * 确认学历
     * 接口名称参数 address
     * @return
     * @throws Exception
     */
    boolean comfirmEdu(String address) throws Exception;

    /**
     * 添加黑名单
     * 接口名称参数 blackList
     * 接口名称参数 address
     * @return
     * @throws Exception
     */
    boolean addBlackList(BlackList blackList,String address) throws Exception;

    /**
     * 撤销黑名单
     * 接口名称参数 index
     * @return
     * @throws Exception
     */
    boolean revokeBlackList(int index) throws Exception;

    /**
     * 通过index获取黑名单
     * 接口名称参数 index
     * @return
     * @throws Exception
     */
    BlackList getBlackListInfoByIndex(int index) throws Exception;

    /**
     * 获取黑名单总数
     * 接口名称参数
     * @return
     * @throws Exception
     */
    int getBltAccount() throws Exception;

    /**
     * 获取黑名单总数
     * 接口名称参数
     * @return
     * @throws Exception
     */
    int getAppAccount() throws Exception;


}
