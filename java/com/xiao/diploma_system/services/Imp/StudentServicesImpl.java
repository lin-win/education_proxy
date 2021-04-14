package com.xiao.diploma_system.services.Imp;

import com.xiao.diploma_system.dao.StudentDao;
import com.xiao.diploma_system.entity.Apply;
import com.xiao.diploma_system.entity.Revoke;
import com.xiao.diploma_system.entity.Student;
import com.xiao.diploma_system.services.StudentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class StudentServicesImpl implements StudentServices {
    @Autowired
    StudentDao studentDao;

    @Override
    public boolean studentLogin(int id, String password) throws Exception {
        return studentDao.studentLogin(id,password);
    }

    @Override
    public boolean registerStudent(Student student) throws Exception {
        return studentDao.registerStudent(student);
    }

    @Override
    public int getStudentAccount() throws Exception {
        return studentDao.getStudentAccount();
    }

    @Override
    public Apply getApplyInfoByAddr(String address) throws Exception {
        return studentDao.getApplyInfoByAddr(address);
    }

    @Override
    public String getAdressByID(int id) throws Exception {
        return studentDao.getAdressByID(id);
    }

    @Override
    public Student getStudentInfoByAddress(String address) throws Exception {
        Student student=new Student();
        student=studentDao.getStudentInfoByAddress(address);
        student=studentDao.queryDiploma(student,address);
        student.setAddress(address);
        student.setExsitEdu(exsitEdu(address));
        return student;
    }

    @Override
    public boolean exsitEdu(String address) throws Exception {
        return studentDao.exsitEdu(address);
    }

    @Override
    public boolean setImage(String address,String _image) throws Exception {
        return studentDao.setImage(address,_image);
    }

    @Override
    public String revokeEdu(Revoke revoke) throws Exception {
        return studentDao.revokeEdu(revoke);
    }

    @Override
    public boolean revokeStu(String address, int id, String password) throws Exception {
        return studentDao.revokeStu(address,id,password);
    }

    @Override
    public String applyDiploma(String address, Apply apply) throws Exception {
        return studentDao.applyDiploma(address,apply);
    }

    @Override
    public boolean updateStudentInfo(String address, Student student) throws Exception {
        return studentDao.updateStudentInfo(address,student);
    }

    @Override
    public Revoke getRevokeInfoByAddr(String address) throws Exception {
        return studentDao.getRevokeInfoByAddr(address);
    }

    @Override
    public boolean ifExsit(String address) throws Exception {
        return studentDao.ifExsit(address);
    }

    @Override
    public List<Student> getallStudent() throws Exception {
        List<String>stu_addr=getallStudentAddress();
        List<Student>stu=new ArrayList<Student>();
        int account=getStudentAccount();
        int i;
        for(i=0;i<account;i++){
            stu.add(getStudentInfoByAddress(stu_addr.get(i)));
        }
        return stu;
    }

    @Override
    public List<Student> getallPassStudent() throws Exception {
        List<Student> PassStu = new ArrayList<Student>();
        List<Student> stu = getallStudent();
        for (Student s : stu) {
            if (s.getExsitEdu())
                PassStu.add(s);
        }
        return PassStu;
    }
    @Override
    public List<String> getallStudentAddress() throws Exception {
        int account=getStudentAccount();
        List<String>stu_addr=new ArrayList<String>();
        int []id=new int[account];
        int i;
        for(i=0;i<account;i++){
           id[i]= studentDao.getIDByIndex(i);
        }
        for(i=0;i<account;i++){
            stu_addr.add(studentDao.getAdressByID(id[i]));
        }
        return stu_addr;
    }



}
