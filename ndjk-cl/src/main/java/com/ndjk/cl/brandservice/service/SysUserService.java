package com.ndjk.cl.brandservice.service;

import com.ndjk.cl.brandservice.model.SysUser;

import java.util.List;

/**
 * Created by wl on 2018/1/30.
 */
public interface SysUserService {
    int saveUser(SysUser sysUser);

    List<SysUser> listAll(SysUser sysUser,int page,int size);

    int updateUser(SysUser sysUser);

    int deleteUser(Long id);
}
