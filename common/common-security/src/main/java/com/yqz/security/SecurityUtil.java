package com.yqz.security;


import jakarta.annotation.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;

public class SecurityUtil {

    @Resource
    private PasswordEncoder passwordEncoder;

    public String saltEncryption(String original,String salt){
        return passwordEncoder.encode(original + salt);
    }
}
