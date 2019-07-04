package com.yunche.novels.service.impl;

import com.yunche.novels.bean.User;
import com.yunche.novels.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author yunche
 * @date 2019/04/14
 */
@Configuration
public class MyUserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) {
        // 模拟一个用户，替代数据库获取逻辑
        User user = userService.getUserByName(username);
//
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
//        user.setUserName(username);
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        // 输出加密后的密码
        System.out.println(user.getPassword());

        return new org.springframework.security.core.userdetails.User(username, user.getPassword(), user.isEnabled(),
                user.isAccountNonExpired(), user.isCredentialsNonExpired(),
                user.isAccountNonLocked(), AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
