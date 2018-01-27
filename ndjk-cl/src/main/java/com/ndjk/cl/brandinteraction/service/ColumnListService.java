package com.ndjk.cl.brandinteraction.service;

import com.ndjk.cl.brandinteraction.model.ColumnList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wl on 2018/1/21.
 */
public interface ColumnListService {
    //插入栏目
    int insertColumn(ColumnList columnList);
    //查询栏目
    List<ColumnList> listAll();
}
