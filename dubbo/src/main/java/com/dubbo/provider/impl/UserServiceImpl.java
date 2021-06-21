package com.dubbo.provider.impl;


import com.dubbo.api.User;
import com.dubbo.api.UserService;
import com.dubbo.framework.Url;
import lombok.AllArgsConstructor;

/**
 * @author zhou1
 * @since 2021/6/11
 */
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private Url url;

    @Override
    public User findUser(Integer id) {
        return new User().setId(id).setUsername("tom").setAge(18).setUrl(url);
    }
}
