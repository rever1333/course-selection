package com.xuan.selectcourse.bean;

import lombok.Data;

//带有状态的权限，状态表示角色是否拥有该权限
@Data
public class PermissionWithStatus {
    private Integer id;
    private String permission;  //Permission Name
    private String url;  //Permission Detail
    private Boolean roleHas;        //角色是否拥有该权限
}
