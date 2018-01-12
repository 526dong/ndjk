package com.ndjk.cl.sys.service.impl;

import com.ndjk.cl.brandservice.model.OrderService;
import com.ndjk.cl.sys.dao.SysAppConfigMapper;
import com.ndjk.cl.sys.model.SysAppConfig;
import com.ndjk.cl.sys.service.SysAppConfigService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * Created by wl on 2018/1/12.
 */
public class SysAppConfigServiceImpl implements SysAppConfigService{
    @Autowired
    private SysAppConfigMapper sysAppConfigMapper;
    @Override
    public void insertSelective(SysAppConfig sysAppConfig) {

    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        return sysAppConfigMapper.deleteByPrimaryKey(id);
    }

    @Override
    public SysAppConfig selectByPrimaryKey(Long id) {
        return sysAppConfigMapper.selectByPrimaryKey(id);
    }

    public int updateSelective(Map<String, Object> paramMap) {
        return sysAppConfigMapper.updateSelective(paramMap);
    }


    public List<SysAppConfig> listSelective(Map<String, Object> paramMap) {
        return sysAppConfigMapper.listSelective(paramMap);
    }


    public SysAppConfig findSelective(Map<String, Object> paramMap) {
        return sysAppConfigMapper.findSelective(paramMap);
    }

    /**
     * 根据状态和类型查找配置
     * @param configMap
     * @Author wl
     * @Date
     */
    public SysAppConfig findByStatusAndType(Map<String, Object> configMap) {
        return sysAppConfigMapper.findByStatusAndType(configMap);
    }
}
