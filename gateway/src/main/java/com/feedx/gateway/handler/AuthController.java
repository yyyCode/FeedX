package com.feedx.gateway.handler;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.feedx.gateway.bo.LoginBo;
import com.yqz.core.domain.CommonResult;
import com.yqz.user.api.UserServiceI;
import com.yqz.user.dto.UserDTO;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
@RestController
@RequestMapping("/auth")
public class AuthController {

    @DubboReference
    private UserServiceI userServiceI;

    @PostMapping("/login")
    public CommonResult<HashMap<String,Object>> doLogin(LoginBo loginBo) {
        UserDTO userDTO = userServiceI.getUserDTOByLogin(loginBo.getAccount(), loginBo.getPassword());
        System.out.println(userDTO.toString());
        StpUtil.login(123456);
        HashMap<String, Object> hashMap = new HashMap<>();
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        hashMap.put("userInfo",123456);
        hashMap.put("tokenInfo",tokenInfo);
        return CommonResult.ok(hashMap,"登录成功");

    }
}
