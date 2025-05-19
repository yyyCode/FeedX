package com.yqz.core.entity;

import java.util.List;

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
