package com.yqz.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yqz.order.bo.OrderCreateBo;
import com.yqz.order.po.Order;

/**
* @author 86139
* @description 针对表【order】的数据库操作Service
* @createDate 2023-11-29 22:03:52
*/
public interface OrderService extends IService<Order> {

    boolean createOrder(OrderCreateBo orderCreateBo);


    void testShardingJDBC();

}
