package com.yqz.recommend.service.impl;

import com.yqz.core.entity.UserProfile;
import com.yqz.recommend.entity.FeedItemDTO;
import com.yqz.recommend.service.RankService;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SimpleRankService implements RankService {
    @Override
    public List<FeedItemDTO> rank(Long userId, UserProfile profile, List<FeedItemDTO> items) {
        return items.stream()
                .sorted(Comparator.comparingDouble(item -> -score(item, profile)))
                .collect(Collectors.toList());
    }

    private double score(FeedItemDTO item, UserProfile profile) {
        double interestScore = profile.getInterestTags().stream()
                .filter(tag -> item.getTags().contains(tag))
                .count();
        double popularity = item.getLikeNum() + item.getCommentNum();
        double timeDecay = 1.0 / (1 + Duration.between(item.getPublishTime(), Instant.now()).toHours());
        return 0.5 * interestScore + 0.3 * popularity + 0.2 * timeDecay;
    }
}
