package com.yqz.feed.entity.vo;

import com.yqz.item.dto.ItemDTO;
import com.yqz.user.dto.UserDTO;
import lombok.Data;

/**
 * @Description: TODO
 * @Author MiSinG
 * @Date 2024/3/4
 * @Version V1.0
 **/
@Data
public class FeedVo {

    private ItemDTO item;

    private UserDTO author;

    private StatisticsVo statistics;

    private Integer isLike;

    private Integer isCollect;

    private Integer isFollow;

}
