package com.yqz.item.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yqz.core.utils.bean.BeanUtils;
import com.yqz.interaction.api.FollowServiceIRPC;
import com.yqz.item.api.ItemServiceIRPC;
import com.yqz.item.dto.ItemDTO;
import com.yqz.item.mapper.ItemMapper;
import com.yqz.item.po.Item;
import com.yqz.item.service.ItemService;
import com.yqz.minio.MinioUtil;
import com.yqz.redis.service.RedisService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author 86139
 * @description 针对表【item】的数据库操作Service实现
 * @createDate 2024-02-26 17:34:23
 */
@Service
@DubboService
public class ItemServiceImpl extends ServiceImpl<ItemMapper, Item>
        implements ItemServiceIRPC, ItemService {

    @Override
    public void postItem(MultipartFile file, Item itemBo) throws Exception {

    }

    @Override
    public List<Item> listItem(String targetId) {
        return List.of();
    }

    @Override
    public ItemDTO getItem(Long id) {
        return null;
    }
}




