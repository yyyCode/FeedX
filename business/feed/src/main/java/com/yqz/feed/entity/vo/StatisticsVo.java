package com.yqz.feed.entity.vo;


import com.yqz.core.utils.bean.BeanUtils;
import com.yqz.count.dto.StatisticsDTO;
import lombok.Data;

/**
 * 统计信息
 */
@Data
public class StatisticsVo {
    private Long collectCount;

    private Long commentCount;

    private Long likeCount;

    private Long viewCount;

    public static StatisticsVo getStatisticsVo(StatisticsDTO statisticsDTO){
        StatisticsVo statisticsVo = new StatisticsVo();
        BeanUtils.copyBeanProp(statisticsVo,statisticsDTO);
        return statisticsVo;
    }

}
