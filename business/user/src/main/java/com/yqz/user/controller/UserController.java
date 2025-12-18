package com.yqz.user.controller;

import com.yqz.core.domain.CommonResult;
import com.yqz.redis.service.RedisService;
import com.yqz.user.po.User;
import com.yqz.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/login")
    public CommonResult<String> login(String username, String password){

        User user=userService.login(username,password);
        if (user==null){
            return CommonResult.fail("用户名或密码错误");
        }else{
           return CommonResult.ok("登录成功");
        }
    }
}
