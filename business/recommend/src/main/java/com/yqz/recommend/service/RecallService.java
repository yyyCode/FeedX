package com.yqz.recommend.service;

import com.yqz.recommend.dto.CandidateItem;

import java.util.List;

public interface RecallService {
    List<CandidateItem> recall(String userId);
}
