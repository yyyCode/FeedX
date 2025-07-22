package com.yqz.recommend.service.impl;

import com.yqz.recommend.dto.CandidateItem;
import com.yqz.recommend.dto.RankedItem;
import com.yqz.recommend.dto.UserProfile;
import com.yqz.recommend.service.Ranker;
import com.yqz.recommend.service.RecallService;
import com.yqz.recommend.service.RecommendService;
import com.yqz.recommend.vo.RecommendVo;
import com.yqz.redis.service.RedisService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class RecommendServiceImpl implements RecommendService {

    @Resource
    private RecallService recallService;

    @Resource
    private RedisService redisService;

    @Resource
    private FeatureService featureService;

    @Resource
    private Ranker ranker;


    @Override
    public List<RecommendVo> recommend(String userId) {
        // 1.召回数据
        List<CandidateItem> candidates = recallService.recall(userId);

        // 2.获取用户画像
        Map<String, Object> userProfile = redisService.getUserProfile("user_profile:"+userId);

        // 3.拼装特征
        featureService.enrichFeatures(userProfile,candidates);


        // 4.排序
        ranker.rank(new UserProfile(), candidates);


        return List.of();
    }


}
