package com.ndjk.cl.brandservice.dao;

import com.ndjk.cl.brandservice.model.ServiceOrder;

import java.util.List;

public interface ServiceOrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ServiceOrder record);

    int insertSelective(ServiceOrder record);

    ServiceOrder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ServiceOrder record);

    int updateByPrimaryKey(ServiceOrder record);

    List<ServiceOrder> selectListByKgId(int kgId);
}