package com.ndjk.cl.operaterecord.dao;

import com.ndjk.cl.operaterecord.model.OperateRecord;

public interface OperateRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OperateRecord record);

    int insertSelective(OperateRecord record);

    OperateRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OperateRecord record);

    int updateByPrimaryKey(OperateRecord record);
}