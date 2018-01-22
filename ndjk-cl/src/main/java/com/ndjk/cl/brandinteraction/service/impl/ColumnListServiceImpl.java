package com.ndjk.cl.brandinteraction.service.impl;

import com.ndjk.cl.brandinteraction.dao.ColumnListMapper;
import com.ndjk.cl.brandinteraction.model.ColumnList;
import com.ndjk.cl.brandinteraction.service.ColumnListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wl on 2018/1/21.
 */
@Service
public class ColumnListServiceImpl implements ColumnListService{
    @Autowired
    private ColumnListMapper columnListMapper;
    @Override
    public int insertColumn(ColumnList columnList) {
        return columnListMapper.insertSelective(columnList);
    }
}
