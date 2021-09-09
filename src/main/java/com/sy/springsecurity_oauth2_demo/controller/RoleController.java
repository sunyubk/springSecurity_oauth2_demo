package com.sy.springsecurity_oauth2_demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName RoleController
 * @Description TODO
 * @Author sy
 * @Date 2021/9/8 22:44
 * @Version 1.0
 **/
@RestController
@RequestMapping("/role")
public class RoleController {

    @GetMapping("/getRole")
    public Object getRole() {
        String s = "管理员";
        return s;
    }
}
