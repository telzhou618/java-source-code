package com.dubbo.framework;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author telzhou
 * @since 2021/6/11
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Invocation  implements Serializable {

    private static final long serialVersionUID = -2587506839526563553L;
    private String className;
    private String methodName;
    private Class[] paramTypes;
    private Object[] params;
}
