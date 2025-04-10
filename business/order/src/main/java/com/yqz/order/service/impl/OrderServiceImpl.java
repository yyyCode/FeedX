package com.yqz.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yqz.core.utils.bean.BeanUtils;
import com.yqz.order.bo.OrderCreateBo;
import com.yqz.order.mapper.OrderMapper;
import com.yqz.order.po.Order;
import com.yqz.order.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* @author 86139
* @description 针对表【order】的数据库操作Service实现
* @createDate 2023-11-29 22:03:52
*/
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order>
    implements OrderService {

    @Override
    public boolean createOrder(OrderCreateBo orderCreateBo){
        Order order = new Order();
        BeanUtils.copyBeanProp(order, orderCreateBo);
        return save(order);
    }

    @Override
    @Transactional
    public void testShardingJDBC() {
        Order order = new Order();
        order.setCustomerId(123456L);
        order.setSellerId(1234567L);
        order.setOrderTitle("test标题");
        order.setOrderDetail("详情");
        order.setGoodId(1234567L);
        order.setGoodNums(1);
        order.setTotalMoney(10000L);
        order.setPay((byte)0);

        save(order);

        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Order::getCustomerId,123456L);
        Order order1 = getOne(wrapper);
        System.out.println(order1);

        order1.setSellerId(12345678L);
        updateById(order1);

        Order byId = getById(order1.getId());
        System.out.println("byId = " + byId);

    }


}




