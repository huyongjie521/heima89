package com.itheima.demo;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class SecurityUserService implements UserDetailsService {
   private HashMap map = new HashMap();
   //将map作为数据库来用，使用initData()方法来map
    public void initData(){
        User user1 = new User();
        user1.setUsername("zhangsan");
        user1.setPassword("123456");

        User user2 = new User();
        user2.setUsername("lisi");
        user2.setPassword("123456");

        map.put(user1.getUsername(),user1);
        map.put(user2.getUsername(),user2);

    }

    /**
     *
     * @param username:即登录页面用户提交的用户名
     * @return
     * @throws UsernameNotFoundException
     */

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //初始化map，将map作为数据库来使用
        initData();
        return null;
    }
}
