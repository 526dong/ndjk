package com.ndjk.manage.brandservice.controller;

import com.ndjk.cl.brandservice.model.Kindergarten;
import com.ndjk.cl.brandservice.model.OrderService;
import com.ndjk.cl.brandservice.model.ServiceOrder;
import com.ndjk.cl.brandservice.model.resp.JsonResult;
import com.ndjk.cl.brandservice.service.BrandOrderServiceService;
import com.ndjk.cl.brandservice.service.BrandServiceOrderService;
import com.ndjk.cl.brandservice.service.BrandServiceService;
import com.ndjk.cl.brandservice.service.KindergartenService;
import com.ndjk.cl.utils.ControllerUtil;
import com.ndjk.manage.kindergarten.controller.KindergartenController;
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
import java.util.Map;

/**
 * @author Created by zfwlz on 2017/12/17.
 * @Description 品牌服务controller
 */
@Controller
@RequestMapping(value = "/manage/brandservice")
public class BrandServiceController {
    private static Logger logger = LogManager.getLogger(BrandServiceController.class);

    @Autowired
    private BrandServiceService brandServiceService;
    @Autowired
    private BrandServiceOrderService serviceOrderService;
    @Autowired
    private KindergartenService kindergartenService;

    /**
     * 搜索服务列表接口
     * @param request
     * @return object
     */
    @RequestMapping(value = "/searchservicelist", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult searchservicelist(HttpServletRequest request) {
        //查询条件-参数集合
        Map<String, Object> params = ControllerUtil.requestMap(request);
        List<Map<String, Object>> serviceOrders = new ArrayList<>();
        try {
            serviceOrders = serviceOrderService.selectList(params);
            return JsonResult.ok(serviceOrders, "服务列表查询成功");
        } catch (Exception e) {
            logger.error("品牌服务模块报错：服务列表查询异常!", e);
            return JsonResult.error(400, "服务列表查询失败");
        }
    }

    /**
     * 添加幼儿园账号接口
     * @param kindergarten
     * @return
     */
    @RequestMapping(value = "/addkgacount", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult addkgacount(Kindergarten kindergarten) {
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
            logger.error("幼儿园服务模块报错：新增幼儿园账号异常!", e);
            return JsonResult.error(400,"保存失败");
        }
    }

    /**
     * 受理接口
     * @param kindergarten
     * @return
     */
    @RequestMapping(value = "/accept", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult accept(Kindergarten kindergarten) {
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
     * 发货接口
     * @param id
     * @return
     */
    @RequestMapping(value = "/delivery", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult delivery(@RequestParam Integer id) {
        try {
            kindergartenService.deleteById(id);
            return JsonResult.ok("删除成功");
        } catch (Exception e) {
            logger.error("幼儿园模块报错：删除异常!", e);
            return JsonResult.error(400, "删除失败");
        }
    }

    /**
     * 查询物流接口
     * @param kindergarten
     * @return
     */
    @RequestMapping(value = "/checklogistics", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult checklogistics(Kindergarten kindergarten) {
        try {
            kindergartenService.updatePwdById(kindergarten);
            return JsonResult.ok("密码重置成功");
        } catch (Exception e) {
            logger.error("幼儿园模块报错：密码重置异常!", e);
            return JsonResult.error(400, "密码重置失败");
        }
    }
}