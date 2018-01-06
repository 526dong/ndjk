package com.ndjk.cl.brandservice.dao;

import com.ndjk.cl.brandservice.model.ServiceOrder;

import java.util.List;
import java.util.Map;

public interface ServiceOrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ServiceOrder record);

    int insertSelective(ServiceOrder record);

    ServiceOrder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ServiceOrder record);

    int updateByPrimaryKey(ServiceOrder record);

    List<ServiceOrder> selectListByKgId(int kgId);

    /**
     * 查询服务列表
     * @return
     */
    List<Map<String, Object>> selectList(Map<String, Object> params);
}