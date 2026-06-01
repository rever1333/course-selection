package com.xuan.selectcourse.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuan.selectcourse.bean.SyllabusVO;
import com.xuan.selectcourse.mapper.CourseMapper;
import com.xuan.selectcourse.mapper.StudentMapper;
import com.xuan.selectcourse.mapper.SyllabusMapper;
import com.xuan.selectcourse.mapper.TeacherMapper;
import com.xuan.selectcourse.pojo.Course;
import com.xuan.selectcourse.pojo.Student;
import com.xuan.selectcourse.pojo.Syllabus;
import com.xuan.selectcourse.pojo.Teacher;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SyllabusService {
    @Autowired
    private SyllabusMapper syllabusMapper;
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private StudentMapper studentMapper;
    //多表分pages查询Open Courses表信息
    public IPage<SyllabusVO> showOpenCoursePage(int page, int size, String condition){
        QueryWrapper<SyllabusVO> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher.name",condition);
        IPage<SyllabusVO> syllabusVOIPage = syllabusMapper.showSyllabusVOPage(new Page<>(page, size), wrapper);
        return syllabusVOIPage;
    }

    //Add Teaching Plan
    public void addSyllabus(SyllabusVO syllabusVO,String username){
        //将syllabusVO对象的属性复制给syllabus
        Syllabus syllabus = new Syllabus();
        BeanUtils.copyProperties(syllabusVO,syllabus);
        //查询出Courseid并添加到syllabus对象
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        wrapper.eq("courseName",syllabusVO.getCourseName());
        Course course = courseMapper.selectOne(wrapper);
        syllabus.setCourse_id(course.getId());
        //查询出Teacherid并添加到syllabus对象
        QueryWrapper<Teacher> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("username",username);
        Teacher teacher = teacherMapper.selectOne(wrapper1);
        syllabus.setTeacher_id(teacher.getId());
        syllabusMapper.insert(syllabus);

    }

    //DeleteTeaching Plan
    public void deleteSyllabus(Integer syllabusId){
        syllabusMapper.deleteById(syllabusId);
    }

    //根据id查询Teaching Plan
    public Syllabus findSyllabusById(Integer id){
        return syllabusMapper.selectById(id);
    }

    //EditTeaching Plan
    public void updateSyllabus(Syllabus syllabus){
        syllabusMapper.updateById(syllabus);
    }

    //多表分pages查询可选Teaching Plan
    public IPage<SyllabusVO> showSelectCoursePage(int page, int size, String username){
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        wrapper.eq("student.username",username);
        IPage<SyllabusVO> syllabusVOIPage = syllabusMapper.showSelectSyllabusVOPage(new Page<>(page, size), wrapper);
        return syllabusVOIPage;
    }

    //多表分pages查询已选Teaching Plan
    public IPage<SyllabusVO> showSelectedCoursePage(int page, int size, String username){
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        wrapper.eq("student.username",username);
        IPage<SyllabusVO> syllabusVOIPage = syllabusMapper.showSelectedSyllabusVOPage(new Page<>(page, size), wrapper);
        return syllabusVOIPage;
    }

    //Course Selection
    public void selectCourse(String username,Integer syllabusId){
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        Student student = studentMapper.selectOne(wrapper);
        syllabusMapper.selectCourse(student.getId(),syllabusId);
        Syllabus syllabus = syllabusMapper.selectById(syllabusId);
        syllabus.setNumber(syllabus.getNumber()+1);
        syllabusMapper.updateById(syllabus);
    }

    //Drop
    public void deselect(String username,Integer syllabusId){
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        Student student = studentMapper.selectOne(wrapper);
        syllabusMapper.deselect(student.getId(),syllabusId);
        Syllabus syllabus = syllabusMapper.selectById(syllabusId);
        syllabus.setNumber(syllabus.getNumber()-1);
        syllabusMapper.updateById(syllabus);
    }


}
