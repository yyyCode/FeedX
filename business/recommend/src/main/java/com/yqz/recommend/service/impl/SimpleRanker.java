package com.yqz.recommend.service.impl;

import com.yqz.recommend.dto.CandidateItem;
import com.yqz.recommend.dto.RankedItem;
import com.yqz.recommend.service.Ranker;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SimpleRanker implements Ranker {
    @Override
    public List<RankedItem> rank(String userId, List<CandidateItem> candidates) {
        // 仅用特征 item_popularity 排序示例
        return candidates.stream()
                .map(c -> new RankedItem(c.getItemId(),
                        (Double) c.getFeatures().getOrDefault("item_popularity", 0.0)))
                .sorted(Comparator.comparingDouble(RankedItem::getScore).reversed())
                .collect(Collectors.toList());
    }

}
