package com.yqz.interaction.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yqz.interaction.api.FollowServiceIRPC;
import com.yqz.interaction.mapper.FollowMapper;
import com.yqz.interaction.po.Follow;
import com.yqz.interaction.service.FollowService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 86139
 * @description 针对表【follow】的数据库操作Service实现
 * @createDate 2024-02-28 22:02:33
 */
@Service
@DubboService
public class FollowServiceImpl extends ServiceImpl<FollowMapper, Follow> implements FollowServiceIRPC, FollowService {

    @Override
    public void follow(Long followerId, Long followingId) {

    }

    @Override
    public void cancel(Long followerId, Long followingId) {

    }

    @Override
    public Long getFollowerCount(Long userId) {
        return 0L;
    }

    @Override
    public List<Long> listFansId(Long userId) {
        return List.of();
    }

    @Override
    public List<Long> listSuperId(Long userId) {
        return List.of();
    }
}




