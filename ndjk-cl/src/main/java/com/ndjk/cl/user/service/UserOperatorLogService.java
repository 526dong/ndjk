package com.ndjk.cl.user.service;

import com.ndjk.cl.brandservice.model.SysUser;
import com.ndjk.cl.user.model.UserOperatorLog;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wl on 2018/1/30.
 */
public interface UserOperatorLogService {
  int save(UserOperatorLog userOperatorLog);

  List<UserOperatorLog> listAll(UserOperatorLog userOperatorLog, int page, int size);
}
