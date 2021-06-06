package com.example.springframework.util;

import com.example.blog.controller.BlogController;
import com.example.springframework.annotation.Controller;
import com.example.springframework.annotation.Service;
import org.junit.Test;

/**
 * @author zhougaojun
 */
public class AnnotationUtilsTest {

    @Test
    public void testIsAnyAnnotationPresent() {
        System.out.println(AnnotationUtils.isAnyAnnotationPresent(new Class[]{Service.class, Controller.class}, BlogController.class));
    }

    @Test
    public void testIsAnnotationPresent() {
        System.out.println(AnnotationUtils.isAnnotationPresent(Controller.class, BlogController.class));
    }

    @Test
    public void testFindAnnotation() {
        System.out.println(AnnotationUtils.findAnnotation(Controller.class, BlogController.class).toString());
    }
}