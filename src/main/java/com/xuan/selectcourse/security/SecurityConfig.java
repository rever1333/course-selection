package com.xuan.selectcourse.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//Security配置类
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    //Spring Security配置
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //自定义表单Sign In
        http.formLogin()
                .loginPage("/admin_login") //自定义Sign In
                .usernameParameter("username")  //用户名
                .passwordParameter("password")  //Password
                .loginProcessingUrl("/user/login")  //表单提交路径
                .successHandler(new MyLoginSuccessHandler()) //Sign In成功处理器
                .failureForwardUrl("/login_fail"); //Sign-in Failed

        //权限拦截配置
        http.authorizeRequests()
                .antMatchers("/user/login").permitAll() //Sign In不需要认证
                .antMatchers("/login_fail").permitAll()  //Sign-in Failed不需要认证
                .antMatchers("/admin_login").permitAll() //Sign In不需要认证
                .antMatchers("/checkCode*").permitAll()  //Verification Code不需要认证
                .antMatchers("/backstage/**").permitAll()   //静态资源放行
                .antMatchers("/**").authenticated();     //其余都需要认证

        //退出Sign In
        http.logout()
                .logoutUrl("/user/logout") //退出Sign In路径
                .logoutSuccessUrl("/admin_login")  //退出Sign In成功后跳转的
                .clearAuthentication(true)   //退出成功后清除认证信息
                .invalidateHttpSession(true); //退出成功后清除session

        http.addFilterBefore(new BeforeLoginFilter(), UsernamePasswordAuthenticationFilter.class);


        //关闭csrf防护
        http.csrf().disable();
        //开启跨域访问
        http.cors();
    }

    //Password加密器
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
