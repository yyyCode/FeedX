package com.yqz.recommend.dto;

import lombok.Data;

import java.util.Map;

@Data
public class UserProfile {
    private Long userId;
    private Map<String, Object> features;  // 如兴趣标签权重、历史行为统计

}
