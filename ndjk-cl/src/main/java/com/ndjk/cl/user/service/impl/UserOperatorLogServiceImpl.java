package com.ndjk.cl.user.service.impl;

import com.ndjk.cl.user.dao.UserOperatorLogMapper;
import com.ndjk.cl.user.model.UserOperatorLog;
import com.ndjk.cl.user.service.UserOperatorLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wl on 2018/1/30.
 */
@Service
public class UserOperatorLogServiceImpl implements UserOperatorLogService{
    @Autowired
    private UserOperatorLogMapper userOperatorLogMapper;
    @Override
    public int save(UserOperatorLog userOperatorLog) {
        return userOperatorLogMapper.insertSelective(userOperatorLog);
    }

    @Override
    public List<UserOperatorLog> listAll(UserOperatorLog userOperatorLog, int page, int size) {
        return userOperatorLogMapper.listAll(userOperatorLog,page,size);
    }
}
