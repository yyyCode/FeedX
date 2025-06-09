package com.yqz.interaction.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yqz.interaction.po.Follow;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
* @author 86139
* @description 针对表【follow】的数据库操作Mapper
* @createDate 2024-02-28 22:02:33
* @Entity com.ysm.interaction.po.Follow
*/
@Mapper
public interface FollowMapper extends BaseMapper<Follow> {
    // 在 FollowMapper.java 接口中添加
    default List<Long> selectFansIds(Long userId) {
        LambdaQueryWrapper<Follow> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Follow::getFollowerId)
                .eq(Follow::getFollowingId, userId);
        return selectObjs(wrapper)
                .stream()
                .map(id -> (Long) id)
                .collect(Collectors.toList());
    }



}




