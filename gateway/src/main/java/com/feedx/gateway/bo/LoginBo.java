package com.feedx.gateway.bo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginBo {
    @NotBlank(message = "账号不能为空")
    private String account;
    @NotBlank(message = "密码不能为空")
    private String password;
    /**
     * 是否为移动端登录
     */
    private boolean mobile = false;

    /**
     * 验证码key
     */
    @NotBlank(message = "验证码key不能为空")
    private String key;

    /**
     * 验证码内容
     */
    @NotBlank(message = "验证码不能为空")
    private String verityCode;

}
