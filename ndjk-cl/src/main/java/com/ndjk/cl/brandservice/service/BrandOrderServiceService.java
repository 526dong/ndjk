package com.ndjk.cl.brandservice.service;

import com.ndjk.cl.brandservice.model.OrderService;

import java.util.List;

/**
 * 订单服务关系
 * Created by zfwlz on 2017/12/26.
 */
public interface BrandOrderServiceService {

    /**
     * 新增
     * @param orderService
     */
    void insertSelective(OrderService orderService);
}
