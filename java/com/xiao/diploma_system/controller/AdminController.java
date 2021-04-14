package com.xiao.diploma_system.controller;
import com.xiao.diploma_system.entity.Apply;
import com.xiao.diploma_system.entity.Response;
import com.xiao.diploma_system.entity.Revoke;
import com.xiao.diploma_system.entity.Student;
import com.xiao.diploma_system.services.StudentServices;
import org.hamcrest.core.Is;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import com.xiao.diploma_system.entity.*;
import com.xiao.diploma_system.services.AdminServices;
import com.xiao.diploma_system.services.StudentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
@Controller
public class AdminController {
    @Autowired
    private StudentServices studentServices;
    @Autowired
    private AdminServices adminServices;
    private boolean Login;

    public AdminController() {
        this.Login = false;
    }

    @GetMapping("/admin")                                                                       //检查管理员是否登陆
    public String index(HttpServletRequest request, Model model) throws Exception {             //springboot内置类
        if (!Login)
            return "/admin/login";
        List<Student> AllStudent=studentServices.getallStudent();                               //定义参数
        List<Student> PassStudent=studentServices.getallPassStudent();
        List<Apply> applies=adminServices.getAllApply();
        model.addAttribute("AllStudent", AllStudent);                              //往类中加入属性
        model.addAttribute("PassStudent", PassStudent);
        model.addAttribute("studentAccount", studentServices.getStudentAccount());
        model.addAttribute("diplomaAccount", PassStudent.size());
        model.addAttribute("applyAccount",adminServices.getApplyAccount());
        model.addAttribute("bltAcount",adminServices.getBltAccount());
        model.addAttribute("applies",applies);
        return "/admin/adminIndex";
    }

    @GetMapping("/admin/login")
    public String adminadmin(HttpServletRequest request, Model model) throws Exception {
        Login=false;
        return "/admin/login";
    }

    @GetMapping("/admin/SignOut")
    public String SignOut(HttpServletRequest request, Model model) throws Exception {
        Login=false;
        return index(request,model);
    }

    @PostMapping("/admin/login")                                                                  //接口定位
    @ResponseBody
    public Object adminLogin(@RequestBody Admin admin) throws Exception {
        System.out.println(admin);
        if (adminServices.adminLogin(admin.getId(),admin.getPassword())){
            Login=true;
//            System.out.println(new Response<String>(1,"login success",null).getcode());
            return new Response<String>(1,"login success",null).getcode();
            //原返回的是对象，通过Object返回需要的code状态值
//            return new Response<String>(1,"login success",null);
        }
//        System.out.println(status);
        System.out.println(admin.getId());
        System.out.println(admin.getPassword());
//        System.out.println(new Response<String>(1, "login failed",null));
//        return new Response<String>(0, "login failed",null);
        return new Response<String>(0,"login success",null).getcode();
    }

    @PostMapping("/admin/upndate")
    public Response adminUpdate(@RequestBody Student student) throws Exception {
        String addr=studentServices.getAdressByID(student.getId());
        if (studentServices.updateStudentInfo(addr,student)){
            return new Response<String>(1, "update success", null);
         }
        return new Response<String>(1, "update failed", null);
    }

    @GetMapping("/admin/search")
    public String search(HttpServletRequest request, Model model) throws Exception {
        model.addAttribute("applyAccount",adminServices.getApplyAccount());
        List<Apply> applies=adminServices.getAllApply();
        model.addAttribute("applies",applies);
        return "admin/search";
    }

    @GetMapping("/admin/search2")
    public String search2(HttpServletRequest request, Model model) throws Exception {
        model.addAttribute("applyAccount",adminServices.getApplyAccount());
        List<Apply> applies=adminServices.getAllApply();
        model.addAttribute("applies",applies);
        return "admin/search2";
    }

    @GetMapping("/admin/addBlackList")
    public String addBlackList(HttpServletRequest request, Model model) throws Exception {
        model.addAttribute("applyAccount",adminServices.getApplyAccount());
        List<Apply> applies=adminServices.getAllApply();
        model.addAttribute("applies",applies);
        model.addAttribute("bltAccount",adminServices.getBltAccount());
        return "/admin/addBlackList";
    }
    @PostMapping("/admin/addblt")
    public Response addblt(@RequestBody BlackList blackList) throws Exception {
        String address=studentServices.getAdressByID(blackList.getId());
        if (adminServices.addBlackList(blackList,address)){
            return new Response<String>(1, "add success", null);
        }
        return new Response<String>(1, "add failed", null);
    }

    @GetMapping("/admin/listBlackList")
    public String listBlackList(HttpServletRequest request, Model model) throws Exception {
        model.addAttribute("applyAccount",adminServices.getApplyAccount());
        List<Apply> applies=adminServices.getAllApply();
        model.addAttribute("applies",applies);
        List<BlackList> blackLists=adminServices.getAllBlackList();
        model.addAttribute("blackLists",blackLists);
        return "/admin/listBlackList";
    }

    @GetMapping("/admin/getBltByIndex/{index}")
    @ResponseBody
    public BlackList getBltByIndex(@PathVariable("index") int index) throws Exception {
        BlackList blackList=adminServices.getBlackListInfoByIndex(index);
        if (blackList == null)
            return null;
        return blackList;
    }
    @PostMapping("/admin/deleteblt")
    public Response deleteblt(@RequestBody BlackList blackList) throws Exception {
        if (adminServices.revokeBlackList(blackList.getIndex())){
            return new Response<String>(1, "delete success", null);
        }
        return new Response<String>(1, "delete failed", null);
    }

    @GetMapping("/admin/comfirmEdu")
    public String comfirmEdu(HttpServletRequest request, Model model) throws Exception {
        model.addAttribute("applyAccount",adminServices.getApplyAccount());
        List<Apply> applies=adminServices.getAllApply();
        model.addAttribute("applies",applies);
        return "/admin/comfirmEdu";
    }
    @GetMapping("/admin/getApply/{address}")
    @ResponseBody
    public Apply getBltByIndex(@PathVariable("address") String address) throws Exception {
        Apply apply=studentServices.getApplyInfoByAddr(address);
        if (apply == null)
            return null;
        apply.setAddress(address);
        return apply;
    }
    @PostMapping("/admin/comfirmEdu")
    public Response comfirmEduaaa(@RequestBody Apply apply) throws Exception {
        if (adminServices.comfirmEdu(apply.getAddress())){
            return new Response<String>(1, "comfirmEdu success", null);
        }
        return new Response<String>(1, "comfirmEdu failed", null);
    }
}
