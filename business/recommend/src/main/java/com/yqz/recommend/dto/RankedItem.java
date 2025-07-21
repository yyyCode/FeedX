package com.yqz.recommend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RankedItem {
    private Long itemId;
    private Double score;
}
