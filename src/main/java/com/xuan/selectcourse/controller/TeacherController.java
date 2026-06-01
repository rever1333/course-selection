package com.xuan.selectcourse.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuan.selectcourse.pojo.Teacher;
import com.xuan.selectcourse.pojo.User;
import com.xuan.selectcourse.service.TeacherService;
import com.xuan.selectcourse.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/teacher")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private UserService userService;
    //分pages查询所有Teacher Information
    @RequestMapping("/all")
    public ModelAndView showPage(@RequestParam(defaultValue = "1") int page,@RequestParam(defaultValue = "5") int size){
        Page<Teacher> teacherPage=teacherService.findPage(page, size);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("teacherPage",teacherPage);
        modelAndView.addObject("condition","");
        modelAndView.setViewName("teacher_all");
        return modelAndView;
    }

    //模糊查询Teacher Information
    @RequestMapping("/findByLike")
    public ModelAndView showPageByLike(@RequestParam(defaultValue = "1") int page,@RequestParam(defaultValue = "5") int size,String condition) {
        Page<Teacher> teacherPage = teacherService.findPageByLike(page, size,condition);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("teacherPage", teacherPage);
        modelAndView.addObject("condition", condition);
        modelAndView.setViewName("teacher_all");
        return modelAndView;
    }
    //AdminAdd TeacherAccount
    @RequestMapping("/add")
    @ResponseBody  //只Backjson数据，不进行跳转
    public String addTeacher(Teacher teacher){
        User user =userService.findUserByUsername(teacher.getUsername());
        if (user==null){
            User user1 = new User();
            user1.setUsername(teacher.getUsername());
            user1.setStatus(0);
            Integer uid = userService.addUser(user1);
            userService.addUserRole(uid,2);
            teacherService.addTeacher(teacher);
            return "Added successfully";
        }else {
            return "Add failed";
        }



    }

    //AdminDeleteTeacherAccount
    @RequestMapping("/delete")
    @ResponseBody
    public String deleteTeacher(Integer teacherId){
        //根据id查询出Teacher
        Teacher teacher = teacherService.findTeacherById(teacherId);

        //根据TeacheridDeleteTeacher Information
        teacherService.deleteTeacher(teacher.getId());

        //根据TeacherEmployee IDDelete用户中的该Account信息
        Integer uid = userService.delete(teacher.getUsername());
        userService.deleteUserRole(uid);
        return "Delete成功";
    }

    //Admin跳转到EditTeacher Information
    @RequestMapping("/updateById")
    public ModelAndView updateById(Integer id){
        Teacher teacher = teacherService.findTeacherById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("teacher",teacher);
        modelAndView.setViewName("teacher_update");
        return modelAndView;
    }

    //AdminEditTeacher Information
    @RequestMapping("/update")
    @ResponseBody
    public String updateTeacher(Teacher teacher){
        teacherService.updateTeacher(teacher);
        return "Edit成功";
    }


}
