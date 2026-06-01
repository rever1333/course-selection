package com.xuan.selectcourse.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xuan.selectcourse.bean.StudentVO;
import com.xuan.selectcourse.pojo.Class;
import com.xuan.selectcourse.pojo.Student;
import com.xuan.selectcourse.pojo.User;
import com.xuan.selectcourse.service.ClassService;
import com.xuan.selectcourse.service.StudentService;
import com.xuan.selectcourse.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private UserService userService;
    @Autowired
    private ClassService classService;
    //多表分pages查询Student所有信息
    @RequestMapping("/all")
    public ModelAndView showPage(@RequestParam(defaultValue = "1") int page,@RequestParam(defaultValue = "5") int size){
        IPage<StudentVO> studentVOIPage = studentService.showPage(page, size);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("studentVOIPage",studentVOIPage);
        modelAndView.addObject("condition","");
        modelAndView.setViewName("student_all");
        return modelAndView;
    }

    //模糊查询Student Information
    @RequestMapping("/findByLike")
    public ModelAndView showPageByLike(@RequestParam(defaultValue = "1") int page,@RequestParam(defaultValue = "5") int size, String condition) {

        IPage<StudentVO> studentVOIPage = studentService.findPageByLike(page, size, condition);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("studentVOIPage", studentVOIPage);
        modelAndView.addObject("condition", condition);
        modelAndView.setViewName("student_all");
        return modelAndView;
    }

    //AdminAdd StudentAccount
    @RequestMapping("/add")
    @ResponseBody  //只Backjson数据，不进行跳转
    public String addStudent(Student student,String className){
        User userByUsername = userService.findUserByUsername(student.getUsername());
        if (userByUsername==null){
            Class aClass =classService.findByClassName(className);
            student.setClass_id(aClass.getId());
            User user = new User();
            user.setUsername(student.getUsername());
            user.setStatus(1);
            Integer uid = userService.addUser(user);
            userService.addUserRole(uid,3);
            studentService.addStudent(student);
            return "Added successfully";
        }else {
            return "Add failed";
        }

    }

    //AdminDeleteStudentAccount
    @RequestMapping("/delete")
    @ResponseBody
    public String deleteStudent(Integer studentId){
        //根据id查询出Student
        Student student = studentService.findStudentById(studentId);
        //根据StudentidDeleteStudent Information
        studentService.deleteStudent(student.getId());
        //根据StudentStudent IDDelete用户中的该Account信息
        Integer uid = userService.delete(student.getUsername());
        userService.deleteUserRole(uid);
        return "Delete成功";
    }

    //查询Class并跳转到EditStudent Information
    @RequestMapping("/updateById")
    public ModelAndView findClassAndUpdateStudent(Integer id){
        List<Class> classList =classService.findClass();
        Student student=studentService.findStudentById(id);
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject("classList",classList);
        modelAndView.addObject("student",student);
        modelAndView.setViewName("student_update");
        return modelAndView;

    }

    //AdminEditStudent Information
    @RequestMapping("/update")
    @ResponseBody
    public String updateStudent (Student student,String className){
        Class aClass=classService.findByClassName(className);
        student.setClass_id(aClass.getId());
        studentService.updateStudent(student);
        return "Edit成功";
    }

}
