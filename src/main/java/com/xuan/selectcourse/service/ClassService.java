package com.xuan.selectcourse.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xuan.selectcourse.mapper.ClassMapper;
import com.xuan.selectcourse.pojo.Class;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassService {
    @Autowired
    private ClassMapper classMapper;

    //查询所有Class
    public List<Class> findClass(){

        List<Class> classList=classMapper.selectList(null);
        return classList;
    }

    //根据Class Name称查询Class信息
    public Class findByClassName(String className){
        QueryWrapper<Class> wrapper=new QueryWrapper<>();
        wrapper.eq("className",className);
        return classMapper.selectOne(wrapper);
    }
}
