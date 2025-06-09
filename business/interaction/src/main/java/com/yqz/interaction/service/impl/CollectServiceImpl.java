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

    @Autowired
    private KafkaTemplate kafkaTemplate;


    @Override
    public void collect(Long itemId, Long userId) {
        Collect collect = new Collect();
        collect.setItemId(itemId);
        collect.setUserId(userId);
      if (save(collect)) {
            // 发送消息队列，异步更新计数
            kafkaTemplate.send("item_count_collect", itemId + ":1");
      }
    }

    @Override
    public void cancel(Long itemId, Long userId) {
        LambdaQueryWrapper<Collect> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Collect::getItemId, itemId)
                    .eq(Collect::getUserId, userId);
        if (remove(queryWrapper)) {
            // 发送消息队列，异步更新计数
            kafkaTemplate.send("item_count_collect", itemId + ":-1");
        }

    }

    @Override
    public List<Long> listCollect(Integer current, Long userId) {
        // 创建分页对象
        Page<Collect> collectPage = new Page<>(current,20);

        // 创建查询条件
        LambdaQueryWrapper<Collect> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Collect::getId,Collect::getItemId)
                .eq(Collect::getUserId,userId)
                .orderByDesc(Collect::getCreatedAt);

        // 执行分页查询
        return page(collectPage,wrapper)
                .getRecords() // 结果转换
                .stream()
                .flatMapToLong(collect -> LongStream.of(collect.getId()))
                .boxed()
                .collect(Collectors.toList());
    }
}




