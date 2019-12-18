package com.itheima.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 自定义认证服务类的使用步骤：
 *      1、创建自定义认证服务类
 *      2、配置认证服务类
 */
@Component
public class SpringSecurityUserService implements UserDetailsService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    //定义一个Map，将其模拟为一个数据库
    private Map map = new HashMap<String,User>();

    private void init(){
        User user1 = new User();
        user1.setUsername("zhangsan");
        user1.setPassword(passwordEncoder.encode("1234"));

        User user2 = new User();
        user2.setUsername("lisi");
        user2.setPassword(passwordEncoder.encode("1234"));

        map.put(user1.getUsername(),user1);
        map.put(user2.getUsername(),user2);

    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //数据库初始化
        init();
        //通过用户名到数据库查询用户
        User user = (User) map.get(username);
        //给用户授权
        List<GrantedAuthority> list = new ArrayList<>();
        /**
         * 给用户授权的规则如下：
         *      配置用户角色：ROLE_角色名称，全字母大写
         *          ROLE_ADMIN、ROLE_MANAGER
         *      配置用户权限：大小写都可，没有ROLE_前缀
         *          add、update、delete、edit
         *
         *          有ROLE_MANAGER 没有add,不能访问。
         *          没有ROLE_MANAGER 有add,能访问。
         *
         *
         *
         */
        if(user.getUsername().equals("zhangsan")){
            list.add(new SimpleGrantedAuthority("ROLE_MANAGER"));

            list.add(new SimpleGrantedAuthority("add"));
        }
        if(user.getUsername().equals("lisi")){
            list.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),list);
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode = passwordEncoder.encode("1234");
        System.out.println(encode);
        String encode2 = passwordEncoder.encode("1234");
        System.out.println(encode2);

        boolean flag1 = passwordEncoder.matches("1234", encode);
        boolean flag2 = passwordEncoder.matches("1234", encode2);

        System.out.println(flag1);
        System.out.println(flag2);
    }
}
