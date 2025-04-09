package com.yqz.count.dto;


import lombok.Data;

@Data
public class StatisticsDTO {
    private Long collectCount;

    private Long commentCount;

    private Long likeCount;

    private Long viewCount;

}