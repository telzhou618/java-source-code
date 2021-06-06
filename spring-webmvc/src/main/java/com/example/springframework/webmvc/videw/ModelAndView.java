package com.example.springframework.webmvc.videw;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * @author zhougaojun
 */
@Getter
@Setter
public class ModelAndView {

    private Object view;

    private Map<String, Object> model;

    public ModelAndView(Object view, Map<String, Object> model) {
        this.view = view;
        this.model = model;
    }
}
