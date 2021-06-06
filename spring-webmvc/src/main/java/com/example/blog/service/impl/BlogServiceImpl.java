package com.example.blog.service.impl;

import com.example.blog.entity.Blog;
import com.example.blog.service.BlogService;
import com.example.springframework.annotation.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhougaojun
 */
@Service
public class BlogServiceImpl implements BlogService {
    @Override
    public Blog findById(Integer id) {
        return new Blog().setId(1).setTitle("标题1").setContent("内容1");
    }

    @Override
    public List<Blog> list() {
        List<Blog> blogs = new ArrayList<>();
        blogs.add(new Blog().setId(1).setTitle("标题1").setContent("内容1"));
        blogs.add(new Blog().setId(2).setTitle("标题2").setContent("内容2"));
        return blogs;
    }
}
