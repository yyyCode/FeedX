package com.yqz.recommend.service.impl;

import com.yqz.recommend.dto.CandidateItem;
import com.yqz.recommend.service.RecallService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ContentRecallService implements RecallService {
    @Override
    public List<CandidateItem> recall(String userId) {
        return List.of();
    }
}
