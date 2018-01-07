package com.ndjk.cl.brandservice.dao;

import com.ndjk.cl.brandservice.model.OrderService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderServiceMapper {
    int deleteByPrimaryKey(Integer id);

    /**
     * 通过orderId删除订单服务关联信息
     * @param orderId
     */
    void deleteRelateByOrderId(@Param("orderId") Integer orderId);

    int insert(OrderService record);

    int insertSelective(OrderService record);

    OrderService selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderService record);

    int updateByPrimaryKey(OrderService record);


}