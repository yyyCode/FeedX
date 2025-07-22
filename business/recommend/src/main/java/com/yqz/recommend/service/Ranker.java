package com.yqz.recommend.service;

import com.yqz.recommend.dto.CandidateItem;
import com.yqz.recommend.dto.RankedItem;
import com.yqz.recommend.dto.UserProfile;

import java.util.List;

public interface Ranker {
    List<RankedItem> rank(UserProfile userProfile, List<CandidateItem> candidates);
}
