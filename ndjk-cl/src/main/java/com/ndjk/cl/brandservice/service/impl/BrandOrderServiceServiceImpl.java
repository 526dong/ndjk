package com.ndjk.cl.brandservice.service.impl;

import com.ndjk.cl.brandservice.dao.OrderServiceMapper;
import com.ndjk.cl.brandservice.model.OrderService;
import com.ndjk.cl.brandservice.service.BrandOrderServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zfwlz on 2017/12/26.
 */
@Service("BrandOrderServiceService")
public class BrandOrderServiceServiceImpl implements BrandOrderServiceService {

    @Autowired
    private OrderServiceMapper orderServiceMapper;
    /**
     * 新增
     * @param orderService
     */
    public void insertSelective(OrderService orderService){
        this.orderServiceMapper.insertSelective(orderService);
    }

    /**
     * 通过orderId删除订单服务关联信息
     * @param orderId
     */
    @Override
    public void deleteRelateByOrderId(Integer orderId) {
        this.orderServiceMapper.deleteRelateByOrderId(orderId);
    }

    /**
     * 根据订单id 查询服务列表
     * @param orderId
     * @return
     */
    @Override
    public List<OrderService> selectListByOrderId(int orderId){
        return this.orderServiceMapper.selectListByOrderId(orderId);
    }
}
