package com.dubbo.framework;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author zhou1
 * @since 2021/6/21
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Url implements Serializable {

    /**
     * 协议表示
     */
    private String scheme;
    /**
     * 主机名称或IP
     */
    private String hostname;
    /**
     * 端口
     */
    private Integer port;

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        Url target = (Url) obj;
        return scheme.equals(target.getScheme())
                && hostname.equals(target.getHostname())
                && port.equals(target.getPort());
    }
}
