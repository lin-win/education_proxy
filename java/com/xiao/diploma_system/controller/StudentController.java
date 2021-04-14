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
//import sun.jvm.hotspot.oops.ObjectHeap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class StudentController {
    @Autowired
    private StudentServices studentServices;
    private Student user;

    public StudentController() {
        this.user = new Student();
    }

    @GetMapping("/")
    public String index(HttpServletRequest request, Model model) throws Exception {
        List<Student> AllStudent = studentServices.getallStudent();
        List<Student> PassStudent = studentServices.getallPassStudent();
        model.addAttribute("AllStudent", AllStudent);
        model.addAttribute("PassStudent", PassStudent);
        System.out.println(user);
        model.addAttribute("User", user);
        model.addAttribute("StudentAccount", studentServices.getStudentAccount());
        model.addAttribute("DiplomaAccount", PassStudent.size());
        String login = "Login in";
        if (IsLogin())
            login = "Sign out";
        model.addAttribute("Login", login);
        return "student/index";
    }

    @GetMapping("/student/login")
    public String Login(HttpServletRequest request, Model model) throws Exception {
        return "student/login";
    }

    @PostMapping("/student/login")
    @ResponseBody
    public Object userLogin(@RequestBody Student student) throws Exception {
        System.out.println(student);
        if (studentServices.studentLogin(student.getId(), student.getPassword())) {
            user = studentServices.getStudentInfoByAddress(studentServices.getAdressByID(student.getId()));
//            login = true;
        return new Response<String>(1, "login success", null).getcode();
        }
        System.out.println(student.getId());
        System.out.println(student.getPassword());
        return new Response<String>(0, "login failed", null).getcode();
    }

    @GetMapping("/student/signOut")
    public String SignOut(HttpServletRequest request, Model model) throws Exception {
        user = new Student();
        return index(request, model);
    }




    @GetMapping("/student/personal")
    public String personal(HttpServletRequest request, Model model) throws Exception {
        if (IsLogin()) {
            String login = "Sign out";
            model.addAttribute("Login", login);
            model.addAttribute("User", user);
            return "student/personal";
        }
        return Login(request, model);
    }

    @PostMapping("/student/personal")
    public Response updateUser(@RequestBody Student student) throws Exception {
        System.out.println(student);
        String addr = studentServices.getAdressByID(student.getId());
        if (studentServices.updateStudentInfo(addr, student)) {
            Student student1 = studentServices.getStudentInfoByAddress(addr);
            user = student1;
            return new Response<String>(1, "update success", null);
        }
        return new Response<String>(1, "update failed", null);
    }

    @GetMapping("/student/apply")
    public String apply(HttpServletRequest request, Model model) throws Exception {
        if (IsLogin()) {
            model.addAttribute("User", user);
            String login = "Sign out";
            model.addAttribute("Login", login);
            String addr = studentServices.getAdressByID(user.getId());
            String exsitEdu="false";
            if(studentServices.exsitEdu(addr))
            {
                exsitEdu="true";
            }
            model.addAttribute("exsitEdu", exsitEdu);
            Apply apply=studentServices.getApplyInfoByAddr(addr);
            if (apply==null)
                return "student/apply1";
            model.addAttribute("apply", apply);
            return "student/apply2";
        }
        return Login(request, model);
    }

    @PostMapping("/student/apply")
    public Response userApply(@RequestBody Apply apply) throws Exception {
        String addr = studentServices.getAdressByID(user.getId());
        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
        apply.setTime(dateFormat.format(date));
        String mess = studentServices.applyDiploma(addr, apply);
        if (mess.equals("ok")) {
            return new Response<String>(1, mess, null);
        } else
            return new Response<String>(1, mess, null);
    }

    @GetMapping("/student/message")
    public String message(HttpServletRequest request, Model model) throws Exception {
        return "/student/message";
    }

    @GetMapping("/student/revoke")
    public String revoke(HttpServletRequest request, Model model) throws Exception {
        if (IsLogin()) {
            model.addAttribute("User", user);
            String login = "Sign out";
            model.addAttribute("Login", login);
            String addr = studentServices.getAdressByID(user.getId());
            String exsitEdu="false";
            if(!studentServices.exsitEdu(addr))
            {
                model.addAttribute("message", "并不存在学历，请先申请！");
                return message(request,model);
            }
            return "student/revoke";
        }
        return Login(request, model);
    }

    @PostMapping("/student/revoke")
    public Response userApply(@RequestBody Revoke revoke) throws Exception {
        String addr=studentServices.getAdressByID(user.getId());
        if (studentServices.revokeEdu(revoke).equals("ok")){
            user=studentServices.getStudentInfoByAddress(addr);
            return new Response<String>(1, "revoke ok", null);
        } else
            return new Response<String>(1, "revoke failed", null);
    }

    @PostMapping("/student/revokeStu")
    public Response revokeStu(@RequestBody Student student) throws Exception {
        String addr=studentServices.getAdressByID(user.getId());
        if (studentServices.revokeStu(addr,student.getId(),student.getPassword())){
            user=new Student();
            return new Response<String>(1, "revoke student ok", null);
        } else
            return new Response<String>(1, "revoke student failed", null);
    }
    @GetMapping("/student/regist")
    public String regist(HttpServletRequest request, Model model) throws Exception {
        int id = studentServices.getStudentAccount();
        model.addAttribute("ID", id);
        return "student/register";
    }

    @PostMapping("/student/regist")
    @ResponseBody
    public Object userRegist(@RequestBody Student student) throws Exception {
        System.out.println(student);
        int id = student.getId();
        student.setTime("null");
        student.setImage("../dist/img/tourist.jpg");
        student.setExsitEdu(false);
        student.setAddress("null");
        student.setEduType("null");
        student.setEduHash("null");
        student.setMajor("null");
        student.setSchool("null");
        if (studentServices.registerStudent(student)) {
            String addr = studentServices.getAdressByID(id);
            user = studentServices.getStudentInfoByAddress(addr);
            return new Response<String>(1, "regist success", null).getcode();
        }
        return new Response<String>(0, "regist failed", null).getcode();
    }

    @GetMapping("/student/history")
    public String history(HttpServletRequest request, Model model) throws Exception {
        if (IsLogin()) {
            model.addAttribute("User", user);
            String login = "Sign out";
            model.addAttribute("Login", login);
            String addr=studentServices.getAdressByID(user.getId());
            Revoke revoke=studentServices.getRevokeInfoByAddr(addr);
            List<Revoke>rev=new ArrayList<Revoke>();
            if(revoke!= null)
                rev.add(revoke);
            model.addAttribute("revokes", rev);
            return "student/history";
        }
        return Login(request, model);
    }

    @GetMapping("/student/search")
    public String search(HttpServletRequest request, Model model) throws Exception {
        model.addAttribute("User", user);
        String login = "Login in";
        if (IsLogin())
            login = "Sign out";
        model.addAttribute("Login", login);
        return "student/search";
    }

    @GetMapping("/student/search2")
    public String search2(HttpServletRequest request, Model model) throws Exception {
        model.addAttribute("User", user);
        String login = "Login in";
        if (IsLogin())
            login = "Sign out";
        model.addAttribute("Login", login);
        return "student/search2";
    }

    @GetMapping("/student/getStudentByID/{id}")
    @ResponseBody
    public Student getStudentByID(@PathVariable("id") int StuID) throws Exception {
        String addr = studentServices.getAdressByID(StuID);
        Student student = studentServices.getStudentInfoByAddress(addr);
        System.out.println(student);
        if (student == null)
            return null;
        return student;
    }

    @GetMapping("/student/getStudentByEduHash/{eduHash}")
    @ResponseBody
    public Student getStudentByEduHash(@PathVariable("eduHash") String eduHash) throws Exception {
        Student student = studentServices.getStudentInfoByAddress(eduHash);
        System.out.println(student);
        if (student == null)
            return null;
        return student;
    }

    //此处强调下，用于保存网站上的图片至本地并且存文件名到对应用户
    @ResponseBody
    @RequestMapping("/student/upload")
    public Map<String, String> uploadaaa(MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws Exception {
        //String path = request.getSession().getServletContext().getRealPath("upload");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式  HH:mm:ss
        String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
        String path = "/diploma_system/src/main/resources/static/dist/img/";
        UUID uuid = UUID.randomUUID();
        String originalFilename = file.getOriginalFilename();
        // String fileName = uuid.toString() + originalFilename;
        String extendName = originalFilename.substring(originalFilename.lastIndexOf("."), originalFilename.length());
        String fileName = uuid.toString() + extendName;
        File dir = new File(path, fileName);
        File filepath = new File(path);
        if (!filepath.exists()) {
            filepath.mkdirs();
        }
        file.transferTo(dir);
        Map<String, String> map = new HashMap<>();
        String addr = studentServices.getAdressByID(user.getId());
        fileName = "../dist/img/" + fileName;
        if (studentServices.setImage(addr, fileName)) {
            user = studentServices.getStudentInfoByAddress(addr);
            System.out.println(user);
            map.put("filePath", path);
            map.put("fileName", fileName);
        } else {
            map.put("filePath", "error");
            map.put("fileName", "error");
        }
        return map;

    }

    public boolean IsLogin() {
        if (user.getName().equals("未登录")) {
            if (user.getImage().equals("../dist/img/tourist.jpg"))
                return false;
        }
        return true;
    }
}

