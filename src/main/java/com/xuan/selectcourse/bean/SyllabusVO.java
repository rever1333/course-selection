package com.xuan.selectcourse.bean;

import lombok.Data;

//Course安排表关联Teacher表关联Course表
@Data
public class SyllabusVO {
    private Integer id;
    private Integer class_hour;
    private Integer credit;
    private String address;
    private Integer number;
    private Integer teacher_id;
    private Integer course_id;
    private String teacherName;
    private String courseName;
}
