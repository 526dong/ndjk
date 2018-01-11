package com.ndjk.cl.brandservice.service;

import com.ndjk.cl.brandservice.model.OrderServicePackage;
import com.ndjk.cl.brandservice.model.OrderService;
import com.ndjk.cl.brandservice.model.ServiceOrder;
import com.ndjk.cl.brandservice.model.resp.ApplyServiceListModel;

import java.util.List;
import java.util.Map;

/**
 * 平拍服务订单
 * Created by zfwlz on 2017/12/26.
 */
public interface BrandServiceOrderService {

    /**
     * 新增订单 返回订单id
     * @param serviceOrder
     * @return
     */
    Integer insertSelective(ServiceOrder serviceOrder);

    /**
     * 根据幼儿园id查询服务列表
     * @param bgId
     * @return
     */
    List<ApplyServiceListModel> selectByKgId(int bgId);

    /**
     * 通过订单id查询服务列表
     * @param orderId
     * @return
     */
    OrderServicePackage.Order selectServiceByOrderId(Integer orderId);

    /**
     * 查询服务列表
     * @return
     */
    List<Map<String, Object>> selectList(Map<String, Object> params);

    /**
     * 查询服务列表总数
     * @Author: zfw
     * @since: 2018/1/11  15:41
     */
    Integer selectListCOunt(Map<String, Object> params);
}
