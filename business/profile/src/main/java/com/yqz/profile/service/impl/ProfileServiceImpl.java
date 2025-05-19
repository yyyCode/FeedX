package com.yqz.profile.service.impl;

import com.yqz.core.entity.UserProfile;
import com.yqz.profile.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private RedisTemplate<String, UserProfile> redisTemplate;

    @Override
    public UserProfile getUserProfile(Long userId) {
        return redisTemplate.opsForValue().get("user:profile:" + userId);
    }
}
