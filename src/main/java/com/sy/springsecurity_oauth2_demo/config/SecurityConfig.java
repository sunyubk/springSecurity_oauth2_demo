package com.sy.springsecurity_oauth2_demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @ClassName SecurityConfig
 * @Description TODO
 * @Author sy
 * @Date 2021/9/7 20:24
 * @Version 1.0
 **/
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    /**
     * 密码模式中使用
     * @return authenticationManager
     * @throws Exception exception
     */
    @Override
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    /**
     * 将 PasswordEncoder 交由 spring 管理
     * @return BCryptPasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 自定义逻辑
     * @return User
     */
    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        return username -> {
            //1.根据用户名去数据库查询，如果不存在抛出 UsernameNotFoundException 异常
            if (!"admin".equals(username)) {
                throw new UsernameNotFoundException("用户名不存在");
            }
            //2.在数据库中查询到用户的相关信息进行整理，例如角色，权限标志，路由等
            Set<String> dbAuthsSet = new HashSet<>();
            dbAuthsSet.add("admin");
            dbAuthsSet.add("user");
            //设置角色，必须 ROLE_ 开头，硬性要求
            dbAuthsSet.add("ROLE_abc");
            // 配合自定义access方法使用
            dbAuthsSet.add("/main.html");

            //3.将权限信息等转换为List，传入 security 的 User。
            List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(dbAuthsSet.toArray(new String[]{}));

            //比较密码（注册时已经加密过），如果匹配成功返回 UserDetails，这里就是在数据库中查询的密码
            String password = passwordEncoder().encode("1");
            return new User(username,password,authorities);
        };

    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                //放开一些请求
                .antMatchers(
                        "/oauth/**",
                        "/login/**",
                        "/logout/**"
                ).permitAll()
                .anyRequest()
                .authenticated()
                .and()
                //放开所有表单提交
                .formLogin()
                .permitAll()
                .and()
                .csrf().disable();
    }
}
