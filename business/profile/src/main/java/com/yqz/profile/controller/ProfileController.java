package com.yqz.profile.controller;

import com.yqz.core.entity.UserProfile;
import com.yqz.profile.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @GetMapping("/{userId}")
    public UserProfile getProfile(@PathVariable Long userId) {
        return profileService.getUserProfile(userId);
    }
}