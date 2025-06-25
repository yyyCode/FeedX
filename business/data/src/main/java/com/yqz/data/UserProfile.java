package com.yqz.data;




import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

@Data
public class UserProfile implements Serializable {
    private String userId;
    private Map<String, Integer> actionCount; // 行为类型计数，如click、like等
    private Map<String, Double> interests;    // 兴趣偏好分数

    public UserProfile() {}

    public UserProfile(String userId, Map<String, Integer> actionCount, Map<String, Double> interests) {
        this.userId = userId;
        this.actionCount = actionCount;
        this.interests = interests;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Map<String, Integer> getActionCount() {
        return actionCount;
    }

    public void setActionCount(Map<String, Integer> actionCount) {
        this.actionCount = actionCount;
    }

    public Map<String, Double> getInterests() {
        return interests;
    }

    public void setInterests(Map<String, Double> interests) {
        this.interests = interests;
    }
} 