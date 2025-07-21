package com.yqz.recommend.service.impl;

import com.yqz.recommend.dto.CandidateItem;
import com.yqz.recommend.dto.RankedItem;
import com.yqz.recommend.service.Ranker;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PythonModelRanker implements Ranker {
    @Override
    public List<RankedItem> rank(String userId, List<CandidateItem> candidates) {
        return List.of();
    }
}
