package com.yqz.recommend.service.impl;

import com.yqz.core.entity.UserProfile;
import com.yqz.recommend.entity.FeedItemDTO;
import com.yqz.recommend.service.RecallStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HotRecall implements RecallStrategy {

    @Autowired
    private FeedItemRepository feedItemRepository;

    @Override
    public List<FeedItemDTO> recall(Long userId, UserProfile profile) {
        return feedItemRepository.findHotItemsLast24h();
    }
}
