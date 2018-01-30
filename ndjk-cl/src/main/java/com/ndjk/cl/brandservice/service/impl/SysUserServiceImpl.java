package com.ndjk.cl.brandservice.service.impl;

import com.ndjk.cl.brandservice.dao.SysUserMapper;
import com.ndjk.cl.brandservice.model.SysUser;
import com.ndjk.cl.brandservice.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wl on 2018/1/30.
 */
@Service
public class SysUserServiceImpl implements SysUserService{
    @Autowired
    private SysUserMapper sysUserMapper;
    @Override
    public int saveUser(SysUser sysUser) {
        return sysUserMapper.insertSelective(sysUser);
    }

    @Override
    public List<SysUser> listAll(SysUser sysUser, int page, int size) {
        return sysUserMapper.listAll(sysUser,page,size);
    }

    @Override
    public int updateUser(SysUser sysUser) {
        return sysUserMapper.updateByPrimaryKeySelective(sysUser);
    }

    @Override
    public int deleteUser(Long id) {
        return sysUserMapper.deleteByPrimaryKey(id);
    }

}
