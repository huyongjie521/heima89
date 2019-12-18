package com.itheima.security;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.pojo.Permission;
import com.itheima.pojo.Role;
import com.itheima.pojo.User;
import com.itheima.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class SpringSecurityUserService implements UserDetailsService {

    @Reference
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //通过用户名从数据库查询到用户信息
        User queryUser =userService.findByUsername(username);

        //配置权限
        List<GrantedAuthority> list = new ArrayList<>();

        //获取用户所有的角色
        Set<Role> roles = queryUser.getRoles();
        for (Role role : roles) {
            //给用户赋予角色
            list.add(new SimpleGrantedAuthority(role.getKeyword()));
            //遍历所有的权限
            Set<Permission> permissions = role.getPermissions();
            for (Permission permission : permissions) {
                list.add(new SimpleGrantedAuthority(permission.getKeyword()));
            }
        }


        return new org.springframework.security.core.userdetails.User(queryUser.getUsername(),queryUser.getPassword(),list);
    }
}
