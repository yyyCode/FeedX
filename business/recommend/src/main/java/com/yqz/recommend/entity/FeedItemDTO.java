package com.yqz.recommend.entity;

import java.time.Instant;
import java.util.List;

@Data
public class FeedItemDTO {
    private Long id;
    private Long userId;
    private String title;
    private List<String> tags;
    private Integer likeNum;
    private Integer commentNum;
    private Instant publishTime;
}
