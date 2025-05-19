package com.yqz.recommend.service;

import com.yqz.core.entity.UserProfile;
import com.yqz.recommend.entity.FeedItemDTO;

import java.util.List;

public interface RankService {
    List<FeedItemDTO> rank(Long userId, UserProfile profile, List<FeedItemDTO> items);
}
