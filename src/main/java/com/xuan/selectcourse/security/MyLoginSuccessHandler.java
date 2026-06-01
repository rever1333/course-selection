package com.xuan.selectcourse.security;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xuan.selectcourse.mapper.AdminMapper;
import com.xuan.selectcourse.mapper.StudentMapper;
import com.xuan.selectcourse.mapper.TeacherMapper;
import com.xuan.selectcourse.mapper.UserMapper;
import com.xuan.selectcourse.pojo.Admin;
import com.xuan.selectcourse.pojo.Student;
import com.xuan.selectcourse.pojo.Teacher;
import com.xuan.selectcourse.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//Sign In成功后处理器
@Component
public class MyLoginSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private TeacherMapper teacherMapper;

    public static MyLoginSuccessHandler myLoginSuccessHandler;

    @PostConstruct
    public void init() {
        myLoginSuccessHandler = this;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //获取用户Sign In信息
        UserDetails userDetails =(UserDetails) authentication.getPrincipal();
        //获取用户Account
        String username = userDetails.getUsername();
        //查询Account身份
        QueryWrapper<User>  wrapper= new QueryWrapper<>();
        wrapper.eq("username",username);
        User user = myLoginSuccessHandler.userMapper.selectOne(wrapper);
        int status = user.getStatus();
        if (status==2){           //Admin身份
            QueryWrapper<Admin> wrapper1 = new QueryWrapper<>();
            wrapper1.eq("username",username);
            Admin admin = myLoginSuccessHandler.adminMapper.selectOne(wrapper1);
            request.getSession().setAttribute("name",admin.getName());
        }else if (status==0){     //Teacher身份
            QueryWrapper<Teacher> wrapper1 = new QueryWrapper<>();
            wrapper1.eq("username",username);
            Teacher teacher = myLoginSuccessHandler.teacherMapper.selectOne(wrapper1);
            request.getSession().setAttribute("name",teacher.getName());
        }else {                   //Student身份
            QueryWrapper<Student> wrapper1 = new QueryWrapper<>();
            wrapper1.eq("username",username);
            Student student = myLoginSuccessHandler.studentMapper.selectOne(wrapper1);
            request.getSession().setAttribute("name",student.getName());
        }

        response.sendRedirect("/index");
    }
}
