package com.ndjk.manage.kindergarten.controller;

import com.ndjk.cl.brandservice.model.Kindergarten;
import com.ndjk.cl.brandservice.model.resp.JsonResult;
import com.ndjk.cl.brandservice.service.KindergartenService;
import com.ndjk.manage.aspect.Record;
import org.apache.http.HttpResponse;
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
 * @Description 幼儿园
 * @author Created by xzd on 2018/1/2.
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
    @RequestMapping(value = "/findAll")
    @ResponseBody
    public JsonResult findAll(HttpResponse response,HttpServletRequest request) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        //查询条件
        String name = request.getParameter("name");
        String state = request.getParameter("state");
        return kindergartenService.selectListByNameAndState(name, state,1,10);
    }

    /**
     * 新增幼儿园账号
     * @param kindergarten
     * @return
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public JsonResult add(HttpResponse response,Kindergarten kindergarten) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        //创建时间
        kindergarten.setCreateTime(new Date());
        try {
            kindergartenService.insertSelective(kindergarten);
            return JsonResult.ok("保存成功");
        } catch (Exception e) {
            logger.error("幼儿园模块报错：新增幼儿园账号异常异常!", e);
            return JsonResult.error(400,"保存失败");
        }
    }

    /**
     * 更新幼儿园账号
     * @param kindergarten
     * @return
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public JsonResult update(HttpResponse response,Kindergarten kindergarten) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        if(kindergarten == null){
            return new JsonResult(500,"请输入正确的参数");
        }
        //更新时间
        kindergarten.setUpdateTime(new Date());
        try {
            kindergartenService.updateSelective(kindergarten);
            return JsonResult.ok("更新成功");
        } catch (Exception e) {
            logger.error("幼儿园模块报错：幼儿园账号更新异常!", e);
            return JsonResult.error(400, "更新失败");
        }
    }

    /**
     * 删除幼儿园账号
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public JsonResult delete(HttpResponse response,@RequestParam Integer id) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        try {
            kindergartenService.deleteById(id);
            return JsonResult.ok("删除成功");
        } catch (Exception e) {
            logger.error("幼儿园模块报错：幼儿园账号删除异常!", e);
            return JsonResult.error(400, "删除失败");
        }
    }

    /**
     * 重置幼儿园账号密码
     * @param kindergarten
     * @return
     */
    @RequestMapping(value = "/resetPwd")
    @ResponseBody
    public JsonResult resetPwd(HttpResponse response,Kindergarten kindergarten) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        try {
            kindergartenService.updatePwdById(kindergarten);
            return JsonResult.ok("密码重置成功");
        } catch (Exception e) {
            logger.error("幼儿园模块报错：幼儿园账号密码重置异常!", e);
            return JsonResult.error(400, "密码重置失败");
        }
    }
}
