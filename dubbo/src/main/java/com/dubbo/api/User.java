package com.dubbo.api;

import com.dubbo.framework.Url;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Map;

/**
 * @author telzhou
 * @since 2021/6/11
 */
@Getter
@Setter
@Accessors(chain = true)
@ToString
public class User implements Serializable {

    private static final long serialVersionUID = 4212754933542124173L;
    private Integer id;
    private String username;
    private Integer age;

    /**
     * 服务提供方信息
     */
    private Url url;

}
