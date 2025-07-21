package com.yqz.recommend.service.impl;

import com.yqz.recommend.dto.CandidateItem;
import com.yqz.redis.service.RedisService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class FeatureService {

    public void enrichFeatures(Map<String, Object> userProfile, List<CandidateItem> candidates) {
        for (CandidateItem candidate : candidates) {
            Map<String, Object> features = candidate.getFeatures();
            userProfile.forEach(features::put);
            // 可加衍生特征计算
        }
    }

}
