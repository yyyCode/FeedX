package com.yqz.recommend.service;

import com.yqz.recommend.entity.FeedItemDTO;

import java.util.List;

public interface RecallStrategy {
    List<FeedItemDTO> recall(Long userId, UserProfile profile);
}
