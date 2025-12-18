package com.yqz.user.controller;

import com.yqz.core.domain.CommonResult;
import com.yqz.redis.service.RedisService;
import com.yqz.user.po.User;
import com.yqz.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

/**
 * @Description: TODO
 * @Author MiSinG
 * @Date 2023/11/1
 * @Version V1.0
 **/
@RestController
@RequestMapping("/api/user")

public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisService redisService;

    @PostMapping("/login")
    public CommonResult<String> login(@RequestBody User loginUser){
        //查询登录的用户
        User user=userService.login(loginUser.getUsername(),loginUser.getPassword());
        if (user==null){
            return CommonResult.fail("用户名或密码错误");
        }else{
           return CommonResult.ok("登录成功");
        }
    }
}
