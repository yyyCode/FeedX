package com.yqz.recommend.service;

import com.yqz.recommend.dto.RankedItem;
import com.yqz.recommend.vo.RecommendVo;

import java.util.List;

public interface RecommendService {
    List<RecommendVo> recommend(String userId);
}
