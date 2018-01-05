package com.ndjk.cl.brandservice.dao;

import com.ndjk.cl.brandservice.model.SanYangInfo;

public interface SanYangInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SanYangInfo record);

    int insertSelective(SanYangInfo record);

    SanYangInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SanYangInfo record);

    int updateByPrimaryKey(SanYangInfo record);
}