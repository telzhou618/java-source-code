package com.example.blog.service;

import com.example.blog.entity.Blog;

import java.util.List;

/**
 * @author zhougaojun
 */
public interface BlogService {

    Blog findById(Integer id);

    List<Blog> list();
}
