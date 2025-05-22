package com.yqz.item.service.impl;

import com.alibaba.fastjson2.JSON;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yqz.core.domain.CommonResult;
import com.yqz.core.utils.SnowFlakeUtil;
import com.yqz.item.mapper.VoucherMapper;
import com.yqz.item.po.Voucher;
import com.yqz.item.service.VoucherService;
import com.yqz.kafka.constant.KafkaConstant;
import com.yqz.user.api.UserServiceI;
import com.yqz.user.dto.UserDTO;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;

/**
* @author 86139
* @description 针对表【voucher】的数据库操作Service实现
* @createDate 2023-11-03 22:03:23
*/
@Service
public class VoucherServiceImpl extends ServiceImpl<VoucherMapper, Voucher>
    implements VoucherService {

    @Override
    public CommonResult<String> secKill(Long userId, Long voucherId) {
        return null;
    }
}




