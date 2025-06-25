package com.yqz.core.entity;

import lombok.Data;

import java.util.List;

/**
 * 用户画像
 */
@Data
public class UserProfile {
    private Long userId;
    private String gender;
    private Integer age;
    private List<String> interestTags;
    private List<Integer> activeHours;
    private List<String> preferredContentType;
    private List<String> interactionKeywords;
}
