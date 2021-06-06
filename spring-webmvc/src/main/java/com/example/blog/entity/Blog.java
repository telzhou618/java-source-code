package com.example.blog.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author zhougaojun
 */
@Getter
@Setter
@Accessors(chain = true)
@ToString
public class Blog {

    private Integer id;
    private String title;
    private String content;
}
