package com.ndjk.cl.brandservice.dao;

import com.ndjk.cl.brandservice.model.ServiceOrder;
import com.ndjk.cl.brandservice.model.dto.ApiApplyServiceQueryModel;

import java.util.List;
import java.util.Map;

public interface ServiceOrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ServiceOrder record);

    int insertSelective(ServiceOrder record);

    ServiceOrder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ServiceOrder record);

    int updateByPrimaryKey(ServiceOrder record);

    List<ApiApplyServiceQueryModel> selectApplyListByKgId(int kgId);

    /**
     * 通过订单id查询服务列表
     * @param orderId
     * @return
     */
    List<Map<String, Object>> selectServiceByOrderId(Integer orderId);

    /**
     * 查询服务列表
     * @return
     */
    List<Map<String, Object>> selectList(Map<String, Object> params);

    /**
     * 查询服务列表总数
     * @param params
     * @return
     */
    Integer selectListCount(Map<String, Object> params);
}