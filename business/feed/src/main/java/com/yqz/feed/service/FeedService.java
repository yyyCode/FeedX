package com.yqz.feed.service;

import com.yqz.feed.entity.vo.FeedVo;

import java.util.List;

public interface FeedService {
    List<FeedVo> feed(Long userId, Integer size);
}