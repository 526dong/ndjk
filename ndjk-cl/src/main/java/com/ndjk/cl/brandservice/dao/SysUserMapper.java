package com.ndjk.cl.brandservice.dao;

import com.ndjk.cl.brandinteraction.model.ContentManage;
import com.ndjk.cl.brandinteraction.model.vo.ContentManageVo;
import com.ndjk.cl.brandservice.model.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    SysUser selectByLoginName(String loginName);

    //用户列表
    List<SysUser> listAll(@Param("sysUser") SysUser sysUser,@Param("offset") int offset, @Param("pageSize") int pageSiz);

}