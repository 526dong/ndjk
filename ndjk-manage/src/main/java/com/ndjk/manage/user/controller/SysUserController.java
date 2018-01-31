package com.ndjk.manage.user.controller;

import com.ndjk.cl.brandinteraction.model.ContentManage;
import com.ndjk.cl.brandinteraction.model.vo.ContentManageVo;
import com.ndjk.cl.brandservice.model.SysUser;
import com.ndjk.cl.brandservice.model.resp.JsonResult;
import com.ndjk.cl.brandservice.service.SysUserService;
import com.ndjk.cl.utils.GsonUtil;
import com.ndjk.manage.brandinteraction.controller.ContentManageController;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by wl on 2018/1/30.
 */
@Controller
public class SysUserController {
    private static Logger logger = LogManager.getLogger(SysUserController.class);
    @Autowired
    private SysUserService sysUserService;
    //用户列表
    @RequestMapping("/manage/sysuser/listUser")
    @ResponseBody
    public JsonResult listUser(@RequestParam String searchParams, @RequestParam Integer page, @RequestParam Integer size,
                                        HttpServletResponse response) {
        SysUser sysUser = GsonUtil.fromJson(SysUser.class, searchParams);
        try {
            if(sysUser==null){
                sysUser=new SysUser();
            }
            List<SysUser> sysUsers = sysUserService.listAll(sysUser, page-1, size);
            if (sysUsers != null && sysUsers.size() > 0) {
                return JsonResult.ok(sysUsers, "用户查询成功");
            } else {
                return JsonResult.error(400, "用户查询出错");
            }
        } catch (Exception e) {
            logger.error("查询用户列表异常", e);
            return JsonResult.error(400, "用户查询出错");
        }
    }

    @RequestMapping(value = "/manage/sysuser/saveUser")
    @ResponseBody
    public JsonResult saveUser(SysUser sysUser) {
        int i = sysUserService.saveUser(sysUser);
        if (i > 0) {
            return JsonResult.ok(i, "保存用户成功");
        } else {
            return JsonResult.error(400, "保存用户出错");
        }
    }

    //用户修改
    @RequestMapping(value = "/manage/sysuser/updateUser")
    @ResponseBody
    public JsonResult updateUser(SysUser sysUser) {
        int i = sysUserService.updateUser(sysUser);
        if (i > 0) {
            return JsonResult.ok(i, "用户修改成功");
        } else {
            return JsonResult.error(400, "用户修改出错");
        }
    }

    //用户删除
    @RequestMapping(value = "/manage/sysuser/deleteUser")
    @ResponseBody
    public JsonResult deleteUser(Long id) {
        int i = sysUserService.deleteUser(id);
        if (i > 0) {
            return JsonResult.ok(i, "用户删除成功");
        } else {
            return JsonResult.error(400, "用户删除出错");
        }
    }
}
