package com.yqz.order.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.yqz.order.bo.OrderCreateBo;
import com.yqz.order.mapper.OrderMapper;
import com.yqz.order.po.Order;
import com.yqz.order.service.OrderService;
import org.springframework.stereotype.Service;


/**
* @author 86139
* @description 针对表【order】的数据库操作Service实现
* @createDate 2023-11-29 22:03:52
*/
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order>
    implements OrderService {


    @Override
    public boolean createOrder(OrderCreateBo orderCreateBo) {
        return false;
    }

    @Override
    public void testShardingJDBC() {

    }
}




