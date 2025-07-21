package com.yqz.recommend.dto;

import lombok.Data;

import java.util.Map;

@Data
public class CandidateItem {
    private Long itemId;
    private Map<String, Object> features;  // 例如用户画像匹配度、内容热度、标签相似度等
}
