package com.sy.springsecurity_oauth2_demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * @ClassName ResourceServerConfig
 * @Description TODO 资源服务器，根据令牌才能获取资源
 * @Author sy
 * @Date 2021/9/7 20:51
 * @Version 1.0
 **/
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                //全部拦截
                .anyRequest().authenticated()
                .and()
                //设置放行资源
                .requestMatchers()
                .antMatchers("/user/**");
    }
}
