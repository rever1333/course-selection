package com.xuan.selectcourse.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.xuan.selectcourse.bean.SyllabusVO;
import com.xuan.selectcourse.pojo.Student;
import com.xuan.selectcourse.pojo.Syllabus;
import org.apache.ibatis.annotations.Param;

public interface SyllabusMapper extends BaseMapper<Syllabus> {
    //Teacher分pages查询Teaching Plan
    IPage<SyllabusVO> showSyllabusVOPage(IPage<SyllabusVO> page, @Param(Constants.WRAPPER)QueryWrapper<SyllabusVO> wrapper);

    //Student分pages查询可选Teaching Plan
    IPage<SyllabusVO> showSelectSyllabusVOPage(IPage<SyllabusVO> page, @Param(Constants.WRAPPER)QueryWrapper<Student> wrapper);

    //Student分pages查询已选Teaching Plan
    IPage<SyllabusVO> showSelectedSyllabusVOPage(IPage<SyllabusVO> page, @Param(Constants.WRAPPER)QueryWrapper<Student> wrapper);

    //Drop
    void deselect(@Param("studentId") Integer studentId,@Param("syllabusId") Integer syllabusId);

    //Course Selection
    void selectCourse(@Param("studentId") Integer studentId,@Param("syllabusId") Integer syllabusId);

}

