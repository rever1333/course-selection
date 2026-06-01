package com.xuan.selectcourse.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xuan.selectcourse.mapper.UserMapper;
import com.xuan.selectcourse.pojo.Teacher;
import com.xuan.selectcourse.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private BCryptPasswordEncoder encoder;

    //根据账户查询用户
    public User findUserByUsername(String username){
        QueryWrapper<User> wrapper = new QueryWrapper();
        wrapper.eq("username",username);
        User user = userMapper.selectOne(wrapper);
        return user;
    }

    //添加用户
    public Integer addUser(User user){
        user.setPassword(encoder.encode("123456"));
        userMapper.insert(user);
        return user.getId();
    }

    //Delete用户
    public Integer delete(String username){
        QueryWrapper<User> wrapper = new QueryWrapper();
        wrapper.eq("username",username);
        User user = userMapper.selectOne(wrapper);
        userMapper.delete(wrapper);
        return user.getId();
    }

    //根据用户Account查询用户权限
    public List<User> findPermissionByUsername(String username){
        return userMapper.findPermissionsByUsername(username);
    }

    //根据用户AccountDelete用户所有角色
    public void deleteRoleByUsername(String username){
        userMapper.deleteRoleByUsername(username);
    }

    //添加用户时添加角色
    public void addUserRole(Integer uid,Integer rid){
        userMapper.addUserRole(uid,rid);
    }
    //Delete用户时Delete角色
    public void deleteUserRole(Integer id){
        userMapper.deleteUserRole(id);
    }

}
