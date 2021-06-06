package com.example.blog.controller;

import com.example.blog.entity.Blog;
import com.example.blog.service.BlogService;
import com.example.springframework.annotation.Autowired;
import com.example.springframework.annotation.Controller;
import com.example.springframework.annotation.RequestMapping;
import com.example.springframework.annotation.ResponseBody;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author zhougaojun
 */
@Slf4j
@Controller
public class BlogController {

    @Autowired
    private BlogService blogService;

    @ResponseBody
    @RequestMapping("/blog/find-id")
    public Blog getBlog(Integer id) {
        log.info("id = {}", id);
        return blogService.findById(id);
    }

    @ResponseBody
    @RequestMapping("/blog/list")
    public List<Blog> list() {
        return blogService.list();
    }
}
