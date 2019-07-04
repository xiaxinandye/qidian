package com.yunche.novels.config;

import com.yunche.novels.service.impl.MyUserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/**
 * @ClassName: MySecurityConfig
 * @Description: 自定义 Security 配置
 * @author: yunche
 * @date: 2019/02/016
 */
@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyAuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private MyUserDetailServiceImpl userDetailService;

    @Autowired
    private DataSource dataSource;

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        jdbcTokenRepository.setCreateTableOnStartup(false);
        return jdbcTokenRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/").permitAll()
                .and()
                .formLogin()
                //指定登录页的路径
                .loginPage("/")
                //指定自定义form表单请求的路径
                .loginProcessingUrl("/userlogin")
                .successHandler(authenticationSuccessHandler) // 处理登录成功
                .failureUrl("/login/error")
                .defaultSuccessUrl("/success")
                //必须允许所有用户访问我们的登录页（例如未验证的用户，否则验证流程就会进入死循环）
                //这个formLogin().permitAll()方法允许所有用户基于表单登录访问/login这个page。
                .permitAll()
                .and()
                .rememberMe()
                .tokenRepository(persistentTokenRepository()) // 配置 token 持久化仓库
//                .tokenValiditySeconds(3600) // remember 过期时间，单为秒
                .userDetailsService(userDetailService) // 处理自动登录逻辑
        ;
    }
}