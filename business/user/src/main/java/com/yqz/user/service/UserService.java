package com.yqz.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yqz.user.po.User;


/**
* @author 86139
* @description 针对表【user】的数据库操作Service
* @createDate 2023-11-01 16:39:06
*/
public interface UserService extends IService<User> {

    User login(String username, String password);

}
