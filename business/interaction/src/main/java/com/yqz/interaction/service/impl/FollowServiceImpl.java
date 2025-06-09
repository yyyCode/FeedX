package com.yqz.interaction.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yqz.interaction.api.FollowServiceIRPC;
import com.yqz.interaction.mapper.FollowMapper;
import com.yqz.interaction.po.Follow;
import com.yqz.interaction.service.FollowService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    @Resource
    private FollowMapper followMapper;

    @Override
    public void follow(Long followerId, Long followingId) {
        Follow follow = new Follow();
        follow.setFollowerId(followerId);
        follow.setFollowingId(followingId);

        followMapper.insert(follow);
    }

    @Override
    public void cancel(Long followerId, Long followingId) {
        LambdaQueryWrapper<Follow> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Follow::getFollowerId, followerId)
                    .eq(Follow::getFollowingId, followingId);

        followMapper.delete(queryWrapper);
    }

    /**
     * 获取关注者数量
     * @param userId userId
     * @return
     */
    @Override
    public Long getFollowerCount(Long userId) {
        LambdaQueryWrapper<Follow> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Follow::getFollowingId, userId);
        return followMapper.selectCount(queryWrapper);
    }

    /**
     * 获取粉丝数量
     * @param userId userId
     * @return
     */
    @Override
    public List<Long> listFansId(Long userId) {
       return followMapper.selectFansIds(userId);
    }


    /**
     * 获取关注者Id
     * @param userId userId
     * @return
     */
    @Override
    public List<Long> listSuperId(Long userId) {
        LambdaQueryWrapper<Follow> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Follow::getFollowingId, userId);
        List<Long> collect = list(wrapper)
                .stream()
                .mapToLong(Follow::getFollowerId)
                .boxed()
                .collect(Collectors.toList());
        // TODO: 2024/3/5 待优化 大V判断可以标识出来或者由计数服务计算
        return collect
                .stream()
                .filter(userId1 -> getFollowerCount(userId1) >= 1000L)
                .collect(Collectors.toList());

    }
}




