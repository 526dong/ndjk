package com.ndjk.cl.brandservice.dao;

import com.ndjk.cl.brandservice.model.OrderService;

import java.util.List;

public interface OrderServiceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderService record);

    int insertSelective(OrderService record);

    OrderService selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderService record);

    int updateByPrimaryKey(OrderService record);

    List<OrderService> selectListByOrderId(int orderId);
}