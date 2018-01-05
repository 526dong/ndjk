package com.ndjk.manage.kindergarten.controller;

import com.ndjk.cl.brandservice.model.Kindergarten;
import com.ndjk.cl.brandservice.model.resp.JsonResult;
import com.ndjk.cl.brandservice.service.KindergartenService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Created by xzd on 2018/1/2.
 * @Description 幼儿园
 */
@Controller
@RequestMapping(value = "/manage/kindergarten")
public class KindergartenController {
    private static Logger logger = LogManager.getLogger(KindergartenController.class);

    @Autowired
    private KindergartenService kindergartenService;

    /**
     * 查询幼儿园用户列表
     * @param request
     * @return object
     */
    @RequestMapping(value = "/findAll", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult findAll(HttpServletRequest request) {
        //查询条件
        String name = request.getParameter("name");
        String state = request.getParameter("state");
        List<Kindergarten> kindergartens = new ArrayList<>();
        try {
            //通过姓名和状态进行幼儿园列表查询
            kindergartens = kindergartenService.selectListByNameAndState(name, state);
            return JsonResult.ok(kindergartens, "幼儿园列表查询成功");
        } catch (Exception e) {
            logger.error("幼儿园模块报错：幼儿园列表查询异常!", e);
            return JsonResult.error(400, "幼儿园列表查询失败");
        }
    }

    /**
     * 新增幼儿园账号
     * @param kindergarten
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult add(Kindergarten kindergarten) {
        //处理新增账号中account属性
        String account = kindergarten.getAccount();
        if (account != null && !"".equals(account)) {
            kindergarten.setLoginName(account);
        }
        //创建时间
        kindergarten.setCreateTime(new Date());
        try {
            kindergartenService.insertSelective(kindergarten);
            return JsonResult.ok("保存成功");
        } catch (Exception e) {
            logger.error("幼儿园模块报错：保存异常!", e);
            return JsonResult.error(400,"保存失败");
        }
    }

    /**
     * 更新幼儿园账号
     * @param kindergarten
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult update(Kindergarten kindergarten) {
        //更新时间
        kindergarten.setUpdateTime(new Date());
        try {
            kindergartenService.updateSelective(kindergarten);
            return JsonResult.ok("更新成功");
        } catch (Exception e) {
            logger.error("幼儿园模块报错：更新异常!", e);
            return JsonResult.error(400, "更新失败");
        }
    }

    /**
     * 删除幼儿园账号
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult delete(@RequestParam Integer id) {
        try {
            kindergartenService.deleteById(id);
            return JsonResult.ok("删除成功");
        } catch (Exception e) {
            logger.error("幼儿园模块报错：删除异常!", e);
            return JsonResult.error(400, "删除失败");
        }
    }

    /**
     * 重置幼儿园账号密码
     * @param kindergarten
     * @return
     */
    @RequestMapping(value = "/resetPwd", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult resetPwd(Kindergarten kindergarten) {
        try {
            kindergartenService.updatePwdById(kindergarten);
            return JsonResult.ok("密码重置成功");
        } catch (Exception e) {
            logger.error("幼儿园模块报错：密码重置异常!", e);
            return JsonResult.error(400, "密码重置失败");
        }
    }
}
