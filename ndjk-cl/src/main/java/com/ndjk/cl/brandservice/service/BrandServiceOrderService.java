package com.ndjk.cl.brandservice.service;

import com.ndjk.cl.brandservice.model.ServiceOrder;
import com.ndjk.cl.brandservice.model.resp.ApplyServiceListModel;

import java.util.List;

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
}
