package com.example.blog.controller;

import com.example.springframework.annotation.Controller;
import com.example.springframework.annotation.RequestMapping;

/**
 * @author zhougaojun
 */
@Controller
public class UserController {

    @RequestMapping("/user/index")
    public String userIndex(String username) {
        System.out.println(username);
        return "index";
    }
}
