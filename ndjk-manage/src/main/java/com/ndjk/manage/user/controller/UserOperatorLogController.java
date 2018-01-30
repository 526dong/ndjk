package com.ndjk.manage.user.controller;

import com.ndjk.cl.brandservice.model.SysUser;
import com.ndjk.cl.brandservice.model.resp.JsonResult;
import com.ndjk.cl.user.model.UserOperatorLog;
import com.ndjk.cl.user.service.UserOperatorLogService;
import com.ndjk.cl.utils.GsonUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by wl on 2018/1/30.
 */
@Controller
public class UserOperatorLogController {
    private static Logger logger = LogManager.getLogger(UserOperatorLogController.class);
@Autowired
private UserOperatorLogService userOperatorLogService;
    //用户列表
    @RequestMapping("/manage/sysuserlog/listUserLog")
    @ResponseBody
    public JsonResult listUserLog(@RequestParam String searchParams, @RequestParam Integer page, @RequestParam Integer size,
                               HttpServletResponse response) {
        UserOperatorLog userOperatorLog = GsonUtil.fromJson(UserOperatorLog.class, searchParams);
        try {
            if(userOperatorLog==null){
                userOperatorLog=new UserOperatorLog();
            }
            List<UserOperatorLog> userOperatorLogs = userOperatorLogService.listAll(userOperatorLog, page, size);
            if (userOperatorLogs != null && userOperatorLogs.size() > 0) {
                return JsonResult.ok(userOperatorLogs, "用户日志查询成功");
            } else {
                return JsonResult.error(400, "用户日志查询出错");
            }
        } catch (Exception e) {
            logger.error("查询用户日志列表异常", e);
            return JsonResult.error(400, "用户日志查询出错");
        }
    }
}
