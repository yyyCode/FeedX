package com.yqz.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yqz.user.mapper.UserMapper;
import com.yqz.user.po.User;
import com.yqz.user.service.UserService;
import org.springframework.stereotype.Service;


/**
* @author 86139
* @description 针对表【user】的数据库操作Service实现
* @createDate 2023-11-01 16:39:06
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {


    @Override
    public User login(String username, String password) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername,username)
                .eq(User::getPassword,password);
        return getOne(wrapper);
    }



    public User register(User user) {
        // 检查用户名是否已存在
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, user.getUsername());
        User existingUser = getOne(wrapper);

        if (existingUser != null) {
            throw new RuntimeException("用户名已存在");
        }

        // 保存新用户
        save(user);
        return user;
    }
}




