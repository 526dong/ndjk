package com.ndjk.cl.sys.dao;

import com.ndjk.cl.sys.model.SysAppConfig;

import java.util.List;
import java.util.Map;

public interface SysAppConfigMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table nd_sys_app_config
     *
     * @mbggenerated Fri Jan 12 10:34:40 CST 2018
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table nd_sys_app_config
     *
     * @mbggenerated Fri Jan 12 10:34:40 CST 2018
     */
    int insert(SysAppConfig record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table nd_sys_app_config
     *
     * @mbggenerated Fri Jan 12 10:34:40 CST 2018
     */
    int insertSelective(SysAppConfig record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table nd_sys_app_config
     *
     * @mbggenerated Fri Jan 12 10:34:40 CST 2018
     */
    SysAppConfig selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table nd_sys_app_config
     *
     * @mbggenerated Fri Jan 12 10:34:40 CST 2018
     */
    int updateByPrimaryKeySelective(SysAppConfig record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table nd_sys_app_config
     *
     * @mbggenerated Fri Jan 12 10:34:40 CST 2018
     */
    int updateByPrimaryKey(SysAppConfig record);

    List<SysAppConfig> listSelective(Map<String, Object> param);

    SysAppConfig findSelective(Map<String, Object> param);

    SysAppConfig findByStatusAndType(Map<String, Object> configMap);

    int updateSelective(Map<String, Object> param);
    /**
     * @Author: wl
     * @Description: 模糊查询通过code
     * @Date: 2018/1/8  15:50
     *
     */
    List<SysAppConfig> listByCode(String code);

}