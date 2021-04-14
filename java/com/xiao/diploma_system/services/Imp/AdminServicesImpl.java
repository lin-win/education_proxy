package com.xiao.diploma_system.services.Imp;

import com.xiao.diploma_system.dao.AdminDao;
import com.xiao.diploma_system.dao.StudentDao;
import com.xiao.diploma_system.entity.Apply;
import com.xiao.diploma_system.entity.BlackList;
import com.xiao.diploma_system.entity.Student;
import com.xiao.diploma_system.services.AdminServices;
import com.xiao.diploma_system.services.StudentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class AdminServicesImpl implements AdminServices {
    @Autowired
    AdminDao adminDao;
    @Autowired
    StudentServices studentServices;
    @Override
    public boolean adminLogin(String id, String password) throws Exception {
        return adminDao.adminLogin(id,password);
    }

    @Override
    public boolean comfirmEdu(String address) throws Exception {
        return adminDao.comfirmEdu(address);
    }

    @Override
    public boolean addBlackList(BlackList blackList, String address) throws Exception {
        return adminDao.addBlackList(blackList,address);
    }

    @Override
    public boolean revokeBlackList(int index) throws Exception {
        return adminDao.revokeBlackList(index);
    }

    @Override
    public BlackList getBlackListInfoByIndex(int index) throws Exception {
        return adminDao.getBlackListInfoByIndex(index);
    }

    @Override
    public int getBltAccount() throws Exception {
        return adminDao.getBltAccount();
    }

    @Override
    public int getApplyAccount() throws Exception {
        return adminDao.getAppAccount();
    }

    @Override
    public List<BlackList> getAllBlackList() throws Exception {
        int account=getBltAccount();
        List<BlackList>blt=new ArrayList<BlackList>();
        int i;
        for(i=0;i<account;i++){
            blt.add(getBlackListInfoByIndex(i));
        }
        return blt;
    }

    @Override
    public List<Apply> getAllApply() throws Exception {
        List<String>addr=studentServices.getallStudentAddress();
        List<Apply>app=new ArrayList<Apply>();
        Apply apply=new Apply();
        for(int i=0;i<addr.size();i++){
            apply=studentServices.getApplyInfoByAddr(addr.get(i));
            if (apply!=null){
                apply.setAddress(addr.get(i));
                app.add(apply);
            }
        }
        return app;
    }


}
