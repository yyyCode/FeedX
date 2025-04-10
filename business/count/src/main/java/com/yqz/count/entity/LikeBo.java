package com.yqz.count.entity;

import lombok.Data;

/**
 * @Description: TODO
 * @Author MiSinG
 * @Date 2024/3/17
 * @Version V1.0
 **/
@Data
public class LikeBo {

    private Long itemId;

    private Long userId;

    private Integer type;
}
