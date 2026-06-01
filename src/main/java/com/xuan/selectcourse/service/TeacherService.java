package com.xuan.selectcourse.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuan.selectcourse.mapper.TeacherMapper;
import com.xuan.selectcourse.pojo.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherService {
    @Autowired
    private TeacherMapper teacherMapper;

    //分pages查询所有Teacher Information
    public Page<Teacher> findPage(int page,int size){
        Page selectPage=teacherMapper.selectPage(new Page(page,size),null);
        return selectPage;
    }

    //模糊查询Teacher Information
    public Page<Teacher> findPageByLike(int page,int size,String condition){
        QueryWrapper<Teacher> wrapper = new QueryWrapper();
         wrapper.eq("sex", condition)
                .or().eq("position", condition)
                .or().eq("name", condition)
                .or().like("username", condition);
        Page selectPage = teacherMapper.selectPage(new Page(page, size), wrapper);
        return selectPage;
    }


    //AdminAdd TeacherAccount
    public Integer addTeacher(Teacher teacher){
         return teacherMapper.insert(teacher);
    }

    //DeleteTeacherAccount
    public void deleteTeacher(Integer id){
        teacherMapper.deleteById(id);
    }

    //根据id查找Teacher Information
    public  Teacher findTeacherById(Integer id){
        return teacherMapper.selectById(id);
    }

    //EditTeacher Information
    public void updateTeacher(Teacher teacher){
        teacherMapper.updateById(teacher);
    }

    //根据TeacherAccount查找Teacherid
    public Integer findIdByUsername(String username){
        QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        Teacher teacher = teacherMapper.selectOne(wrapper);
        return teacher.getId();
    }




}
