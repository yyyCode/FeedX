package com.yqz.feed.entity.vo;


import com.yqz.core.utils.bean.BeanUtils;
import com.yqz.count.dto.StatisticsDTO;
import lombok.Data;

/**
 * @Description: TODO
 * @Author MiSinG
 * @Date 2024/3/4
 * @Version V1.0
 **/
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
