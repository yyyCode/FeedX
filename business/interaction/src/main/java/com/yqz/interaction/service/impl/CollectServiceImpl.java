package com.yqz.interaction.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yqz.interaction.mapper.CollectMapper;
import com.yqz.interaction.po.Collect;
import com.yqz.interaction.service.CollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

/**
* @author 86139
* @description 针对表【collect】的数据库操作Service实现
* @createDate 2024-02-29 18:25:41
*/
@Service
public class CollectServiceImpl extends ServiceImpl<CollectMapper, Collect>
    implements CollectService {

    @Override
    public void collect(Long itemId, Long userId) {

    }

    @Override
    public void cancel(Long itemId, Long userId) {

    }

    @Override
    public List<Long> listCollect(Integer current, Long userId) {
        return List.of();
    }
}




