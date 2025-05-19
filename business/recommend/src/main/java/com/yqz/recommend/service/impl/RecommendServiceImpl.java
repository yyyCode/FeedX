package com.yqz.recommend.service.impl;

import com.yqz.recommend.entity.FeedItemDTO;
import com.yqz.recommend.service.RecommendService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@DubboService
public class RecommendServiceImpl implements RecommendService {
    @Override
    public List<FeedItemDTO> recommend(Long userId) {
        return List.of();
    }
}
