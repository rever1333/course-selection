package com.xuan.selectcourse.security;

import com.xuan.selectcourse.pojo.Permission;
import com.xuan.selectcourse.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//自定义认证授权逻辑
@Service
public class MyUserDetailService implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //1.认证
        com.xuan.selectcourse.pojo.User user = userService.findUserByUsername(username);
        if (user==null){
            throw new UsernameNotFoundException("用户不存在");
        }


        //2.授权
        List<com.xuan.selectcourse.pojo.User> userPermissions = userService.findPermissionByUsername(username);
        List<GrantedAuthority> grantedAuthorities =new ArrayList();
        for (com.xuan.selectcourse.pojo.User user1:userPermissions) {
            List<Permission> permissions = user1.getPermissions();
            for (Permission permission:permissions
                 ) {
                grantedAuthorities.add(new SimpleGrantedAuthority(permission.getUrl()));
            }

        }



        //封装为UserDetail对象
        UserDetails userDetails = User.withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(grantedAuthorities)  //grantedAuthorities
                .build();
        return userDetails;
    }

}
