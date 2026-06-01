package com.xuan.selectcourse.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.List;

@Data
public class User {
    @TableId
    private Integer id;
    private String username;
    private String password;
    private int status;
    @TableField(exist = false) //不是数据库的字段
    private List<Permission> permissions;
}
