package com.xuan.selectcourse.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xuan.selectcourse.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {
    //自定义查询用户权限
    List<User> findPermissionsByUsername(String username);

    //根据AccountDelete用户的所有角色
    void deleteRoleByUsername(String username);

    //Add Teacher或Student角色
    void addUserRole(@Param("uid") Integer uid,@Param("rid") Integer rid);

    //DeleteTeacher或Student角色
    void deleteUserRole(Integer id);

}
