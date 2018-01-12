package com.ndjk.cl.sys.service;

import com.ndjk.cl.brandservice.model.OrderService;
import com.ndjk.cl.sys.model.SysAppConfig;

import java.util.List;
import java.util.Map;

/**
 * Created by wl on 2018/1/12.
 */
public interface SysAppConfigService {

    /**
     * 新增
     * @param sysAppConfig
     */
    void insertSelective(SysAppConfig sysAppConfig);
    /**
     * @Author: wl
     * @Description: 删除
     * @Date: 2018/1/12  10:43
     *
     */
    int deleteByPrimaryKey(Long id);
    /**
     * @Author: wl
     * @Description: 通过主键查询
     * @Date: 2018/1/12  10:44
     *
     */
    SysAppConfig selectByPrimaryKey(Long id);


    int updateSelective(Map<String, Object> paramMap);

    List<SysAppConfig> listSelective(Map<String, Object> paramMap);

    SysAppConfig findSelective(Map<String, Object> paramMap);

    SysAppConfig findByStatusAndType(Map<String, Object> configMap);

}
