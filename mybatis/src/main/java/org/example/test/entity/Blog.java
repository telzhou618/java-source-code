package org.example.test.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author zhou1
 */

@Getter
@Setter
@ToString
public class Blog implements Serializable {

    private Integer id;
    private String title;
    private String content;
}
