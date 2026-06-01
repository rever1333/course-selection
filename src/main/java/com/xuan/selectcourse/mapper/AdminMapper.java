package com.xuan.selectcourse.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xuan.selectcourse.pojo.Admin;
import org.apache.ibatis.annotations.Param;


public interface AdminMapper extends BaseMapper<Admin> {
    //查询Admin的详情
    Admin findDesc(Integer id);
    //DeleteAdmin的所有角色
    void deleteAdminAllRoles(Integer id);
    //给Admin添加角色
    void addAdminRole(@Param("aid")Integer aid, @Param("rid")Integer rid);
}
