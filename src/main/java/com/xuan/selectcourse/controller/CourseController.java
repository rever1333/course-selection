package com.xuan.selectcourse.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuan.selectcourse.pojo.Class;
import com.xuan.selectcourse.pojo.Course;
import com.xuan.selectcourse.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private CourseService courseService;

    //分pages查询所有Course Information
    @RequestMapping("/all")
    public ModelAndView showPage(@RequestParam(defaultValue = "1") int page,@RequestParam(defaultValue = "5") int size){
        Page<Course> coursePage=courseService.findPage(page, size);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("coursePage",coursePage);
        modelAndView.setViewName("course_all");
        return modelAndView;
    }

    //AdminAdd Course
    @RequestMapping("/add")
    @ResponseBody  //只Backjson数据，不进行跳转
    public String addCourse(Course course){
        List<Course> courseList = courseService.findByCourseName(course.getCourseName());
        if (courseList.size()==0){
            courseService.addCourse(course);
            return "Added successfully";
        }else {
            return "Add failed";
        }

    }

    //AdminDeleteCourse
    @RequestMapping("/delete")
    @ResponseBody
    public String deleteCourse(Integer courseId){
        //根据id查询出Course
        Course course = courseService.findCourseById(courseId);
        //根据CourseidDeleteCourse Information
        courseService.deleteCourse(course.getId());
        //根据Courseid在Course安排表中Delete有该Course的所有信息

        return "Delete成功";
    }

    //Admin跳转到EditCourse Information
    @RequestMapping("/updateById")
    public ModelAndView updateById(Integer id){
        Course course = courseService.findCourseById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("course",course);
        modelAndView.setViewName("course_update");
        return modelAndView;
    }

    //AdminEditCourse Information
    @RequestMapping("/update")
    @ResponseBody
    public String updateCourse(Course course){
        courseService.updateCourse(course);
        return "Edit成功";
    }

    //TeacherOpen Courses查询Course库所有Course
    @RequestMapping("/findAll")
    public ModelAndView findClass(){
        List<Course> courseList =courseService.findCourse();
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject("courseList",courseList);
        modelAndView.setViewName("syllabus_add");
        return modelAndView;
    }

}
