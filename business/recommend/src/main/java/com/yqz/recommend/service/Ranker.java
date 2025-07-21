package com.yqz.recommend.service;

import com.yqz.recommend.dto.CandidateItem;
import com.yqz.recommend.dto.RankedItem;

import java.util.List;

public interface Ranker {
    List<RankedItem> rank(String userId, List<CandidateItem> candidates);
}
