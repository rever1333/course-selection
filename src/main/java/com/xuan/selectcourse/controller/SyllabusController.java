package com.xuan.selectcourse.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xuan.selectcourse.bean.SyllabusVO;
import com.xuan.selectcourse.pojo.*;
import com.xuan.selectcourse.pojo.Class;
import com.xuan.selectcourse.service.CourseService;
import com.xuan.selectcourse.service.SyllabusService;
import com.xuan.selectcourse.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/syllabus")
public class SyllabusController {
    @Autowired
    private SyllabusService syllabusService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private TeacherService teacherService;
    //Teacher分pages查询Open Courses信息
    @RequestMapping("/all")
    public ModelAndView showTeacherSyllabusPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size,
            String teacherName){
        ModelAndView modelAndView = new ModelAndView();
        IPage<SyllabusVO> syllabusVOIPage = syllabusService.showOpenCoursePage(page, size, teacherName);
        modelAndView.addObject("syllabusVOIPage",syllabusVOIPage);
        modelAndView.setViewName("syllabus_all");
        return modelAndView;
    }

    //Add Teaching Plan
    @RequestMapping("/add")
    @ResponseBody  //只Backjson数据，不进行跳转
    public String addSyllabus(SyllabusVO syllabusVO,String username){
        syllabusService.addSyllabus(syllabusVO,username);
        return "Added successfully";
    }

    //DeleteTeaching Plan
    @RequestMapping("/delete")
    @ResponseBody
    public String deleteSyllabus(Integer syllabusId){
        //根据idDelete出Teaching Plan
        syllabusService.deleteSyllabus(syllabusId);
        return "Delete成功";
    }

    //查询Course并跳转到EditTeaching Plan Information
    @RequestMapping("/updateById")
    public ModelAndView findCourseAndUpdateSyllabus(Integer id){
        List<Course> courseList =courseService.findCourse();
        Syllabus syllabus=syllabusService.findSyllabusById(id);
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject("courseList",courseList);
        modelAndView.addObject("syllabus",syllabus);
        modelAndView.setViewName("syllabus_update");
        return modelAndView;

    }

    //TeacherEditTeaching Plan Information
    @RequestMapping("/update")
    @ResponseBody
    public String updateSyllabus (Syllabus syllabus,String courseName,String username){

        Integer courseId = courseService.findIdByCourseName(courseName);
        syllabus.setCourse_id(courseId);
        Integer teacherId = teacherService.findIdByUsername(username);
        syllabus.setTeacher_id(teacherId);
        System.out.println(syllabus);
        syllabusService.updateSyllabus(syllabus);
        return "Edit成功";
    }

    //Student查看可选Teaching Plan
    @RequestMapping("/selectCourse")
    public ModelAndView showStudentSelectSyllabusPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size,
            String username){
        ModelAndView modelAndView = new ModelAndView();
        IPage<SyllabusVO> syllabusVOIPage = syllabusService.showSelectCoursePage(page, size, username);
        modelAndView.addObject("syllabusVOIPage",syllabusVOIPage);
        modelAndView.setViewName("syllabus_all_select");
        return modelAndView;
    }

    //Student查看已选Teaching Plan
    @RequestMapping("/selectedCourse")
    public ModelAndView showStudentSelectedSyllabusPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size,
            String username){
        ModelAndView modelAndView = new ModelAndView();
        IPage<SyllabusVO> syllabusVOIPage = syllabusService.showSelectedCoursePage(page, size, username);
        modelAndView.addObject("syllabusVOIPage",syllabusVOIPage);
        modelAndView.setViewName("syllabus_all_selected");
        return modelAndView;
    }

    //StudentDrop
    @RequestMapping("/deselect")
    @ResponseBody
    public String studentDeselect(String username,Integer syllabusId){
        syllabusService.deselect(username,syllabusId);
        return "Dropped successfully";
    }

    //StudentCourse Selection
    @RequestMapping("/selectSyllabus")
    @ResponseBody
    public String studentSelectCourse(String username,Integer syllabusId){
        syllabusService.selectCourse(username,syllabusId);
        return "Selected successfully";
    }
}
