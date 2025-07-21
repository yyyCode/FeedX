package com.yqz.recommend.service;

import com.yqz.recommend.dto.RankedItem;

import java.util.List;

public interface RecommendService {
    List<RankedItem> recommend(String userId);
}
