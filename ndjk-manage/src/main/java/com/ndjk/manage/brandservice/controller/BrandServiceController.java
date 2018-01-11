package com.ndjk.manage.brandservice.controller;

import com.ndjk.cl.brandservice.model.Kindergarten;
import com.ndjk.cl.brandservice.model.OrderServicePackage;
import com.ndjk.cl.brandservice.model.resp.JsonResult;
import com.ndjk.cl.brandservice.service.BrandServiceOrderService;
import com.ndjk.cl.brandservice.service.BrandServiceService;
import com.ndjk.cl.brandservice.service.KindergartenService;
import com.ndjk.cl.utils.ControllerUtil;
import com.ndjk.cl.utils.MyRuntimeException;
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
 * @Description 品牌服务controller
 * @author Created by zfwlz on 2017/12/17.
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
    @RequestMapping(value = "/searchservicelist")
    @ResponseBody
    public JsonResult searchservicelist(HttpServletRequest request,int page,int size) {
        //查询条件-参数集合
        Map<String, Object> params = ControllerUtil.requestMap(request);
        //尽量初始化集合大小
        List<Map<String, Object>> serviceOrders = new ArrayList<>(10000);
        try {
            //查询总数

            //查询分页后数据
            params.put("beginPage",(page-1)*size);
            params.put("size",size);
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
    @RequestMapping(value = "/addkgacount")
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
            logger.error("品牌服务模块报错：新增幼儿园账号异常!", e);
            return JsonResult.error(400,"保存失败");
        }
    }

    /**
     * 查询订单服务接口
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/selectOrderService")
    @ResponseBody
    public JsonResult selectOrderService(@RequestParam(required = true) Integer orderId) {
        //订单服务数据包
        OrderServicePackage.Order orderEntity = new OrderServicePackage.Order();
        try {
            //通过订单id查询订单服务信息
            orderEntity = serviceOrderService.selectServiceByOrderId(orderId);
            return JsonResult.ok(orderEntity, "订单服务列表查询成功");
        } catch (Exception e) {
            logger.error("品牌服务模块报错：订单服务列表查询异常!", e);
            return JsonResult.error(400, "订单服务列表查询失败");
        }
    }

    /**
     * 受理接口
     * @param orderId
     * @param priceJsonStr
     * @return
     */
    @RequestMapping(value = "/accept")
    @ResponseBody
    public JsonResult accept(@RequestParam(required = true) Integer orderId, String priceJsonStr) {
        try {
            brandServiceService.updateServiceByOrderId(orderId, priceJsonStr);
            return JsonResult.ok("受理成功");
        } catch (Exception e) {
            if (e instanceof MyRuntimeException) {
                return JsonResult.error(400, e.getMessage());
            } else {
                logger.error("品牌服务模块报错：受理异常!", e);
                return JsonResult.error(400, "受理失败");
            }
        }
    }

    /**
     * 发货接口
     * @param orderId
     * @param courier
     * @param couNo
     * @return
     */
    @RequestMapping(value = "/delivery")
    @ResponseBody
    public JsonResult delivery(@RequestParam(required = true) Integer orderId, String courier, String couNo) {
        try {
            return JsonResult.ok("发货成功");
        } catch (Exception e) {
            logger.error("品牌服务模块报错：发货异常!", e);
            return JsonResult.error(400, "发货失败");
        }
    }

    /**
     * 查询物流接口
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/checklogistics")
    @ResponseBody
    public JsonResult checklogistics(@RequestParam(required = true) Integer orderId) {
        try {
            return JsonResult.ok("物流查询成功");
        } catch (Exception e) {
            logger.error("品牌服务模块报错：物流查询异常!", e);
            return JsonResult.error(400, "物流查询失败");
        }
    }
}
