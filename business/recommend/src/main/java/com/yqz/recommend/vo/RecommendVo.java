package com.yqz.recommend.vo;

import com.yqz.feed.entity.vo.StatisticsVo;
import com.yqz.item.dto.ItemDTO;
import com.yqz.user.dto.UserDTO;
import lombok.Data;

@Data
public class RecommendVo {
    private ItemDTO item;

    private UserDTO author;

    private StatisticsVo statistics;

    private Integer isLike;

    private Integer isCollect;

    private Integer isFollow;
}
