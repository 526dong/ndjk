package com.ndjk.api.brandservice.controller;

import com.alibaba.fastjson.JSON;
import com.ndjk.cl.brandservice.model.BrandService;
import com.ndjk.cl.brandservice.model.Kindergarten;
import com.ndjk.cl.brandservice.model.OrderService;
import com.ndjk.cl.brandservice.model.ServiceOrder;
import com.ndjk.cl.brandservice.model.req.ApplyServiceReq;
import com.ndjk.cl.brandservice.model.resp.*;
import com.ndjk.cl.brandservice.service.BrandOrderServiceService;
import com.ndjk.cl.brandservice.service.BrandServiceOrderService;
import com.ndjk.cl.brandservice.service.BrandServiceService;
import com.ndjk.cl.brandservice.service.KindergartenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 品牌服务
 * Created by zfwlz on 2017/12/17.
 */
@Controller
@RequestMapping(value = "/api")
public class BrandServiceController {
    @Autowired
    private BrandServiceOrderService brandServiceOrderService;
    @Autowired
    private BrandOrderServiceService brandOrderServiceService;
    @Autowired
    private KindergartenService kindergartenService;
    @Autowired
    private BrandServiceService brandServiceService;

    //生成流水号使用的静态常量
    private final static String str62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final static int pixLen = 36;
    private static volatile int pixOne = 0;
    private static volatile int pixTwo = 0;
    private static volatile int pixThree = 0;
    private static volatile int pixFour = 0;
    private static final AtomicInteger ATOM_INT = new AtomicInteger(0);
    private static final int MAX_36 = 36 * 36 * 36 * 36;
    /**
     * 申请服务
     * @param applyServiceReq
     * @return
     */
    @RequestMapping(value = "/brandservice/applyservice")
    @ResponseBody
    public Object applyService(ApplyServiceReq applyServiceReq){
        //1、生成订单，返回订单id
        ServiceOrder serviceOrder = new ServiceOrder();
        serviceOrder.setActivityName(applyServiceReq.getActivityName());
        serviceOrder.setApplyName(applyServiceReq.getApplyName());
        serviceOrder.setIsShipping(2);
        serviceOrder.setKgId(applyServiceReq.getKgId());
        serviceOrder.setOrderNo(createSerialNumber());
        serviceOrder.setPhone(applyServiceReq.getPhone());
        serviceOrder.setWork(applyServiceReq.getWork());
        serviceOrder.setState(1);
        this.brandServiceOrderService.insertSelective(serviceOrder);

        //2、插入订单和服务的关系数据
        List<String> freeServiceList = JSON.parseArray(applyServiceReq.getFreeServices(), String.class);
        if(freeServiceList != null || freeServiceList.size() != 0){
            for(String freeServcie:freeServiceList){
                Integer servcieId =Integer.valueOf(JSON.parseObject(freeServcie).getString("id"));
                Integer count =Integer.valueOf(JSON.parseObject(freeServcie).getString("count"));
                OrderService orderService = new OrderService();
                orderService.setCount(count);
                orderService.setOrderId(serviceOrder.getId());
                orderService.setServiceId(servcieId);
                this.brandOrderServiceService.insertSelective(orderService);
            }
        }
        String[] nofreeServices = applyServiceReq.getNoFreeServices().split(",");
        if(nofreeServices !=null || nofreeServices.length != 0){
            for(int i=0;i<nofreeServices.length;i++){
                OrderService orderService = new OrderService();
                orderService.setCount(1);
                orderService.setOrderId(serviceOrder.getId());
                orderService.setServiceId(Integer.valueOf(nofreeServices[i]));
                this.brandOrderServiceService.insertSelective(orderService);
            }
        }
        if((nofreeServices ==null || nofreeServices.length == 0) &&
                (freeServiceList == null || freeServiceList.size() == 0)){
            return new BaseResponseModel(200,"操作失败");
        }
        return new BaseResponseModel(200,"操作成功");
    }

    /**
     * 查询服务列表
     * @param type
     * @return
     */
    @RequestMapping(value = "/brandservice/getservicelist")
    @ResponseBody
    public Object getservicelist(int type){
        List<BrandService> brandServices = this.brandServiceService.selectByType(type);
        if(brandServices ==null || brandServices.size()<1){
            return new GetBrandServiceListRespModel(400,"服务列表为空");
        }
        List<GetBrandServiceInfo> getBrandServiceInfos = new ArrayList<>();
        for(BrandService brandService:brandServices){
            GetBrandServiceInfo getBrandServiceInfo =
                    new GetBrandServiceInfo(brandService.getName(),brandService.getImageurl());
            getBrandServiceInfo.setId(brandService.getId());
            getBrandServiceInfo.setType(brandService.getType());
            getBrandServiceInfo.setTypeStr("服务类型说明");
            getBrandServiceInfos.add(getBrandServiceInfo);
        }
        return new GetBrandServiceListRespModel(200,"操作成功",getBrandServiceInfos);
    }

    /**
     * 查询申请服务列表
     * @param kgId
     * @return
     */
    @RequestMapping(value = "/brandservice/selectKgApplyServiceList")
    @ResponseBody
    public Object selectKgApplyServiceList(int kgId){
        List<ApplyServiceListModel> selectApplyServiceListRespModels =
                this.brandServiceOrderService.selectByKgId(kgId);
        if (selectApplyServiceListRespModels == null || selectApplyServiceListRespModels.size()<1){
            return new ApplyServiceListRespModel(400,"申请服务列表为空");
        }
        return new ApplyServiceListRespModel(200,"操作成功",selectApplyServiceListRespModels);
    }

    /**
     * 生成15位流水号(同一秒钟可生成160万个不重复的流水号)
     *
     * @return
     */
    public String createSerialNumber() {
        StringBuilder sb = new StringBuilder(15);// 创建一个StringBuilder
        sb.append(Long.toHexString(System.currentTimeMillis()));// 先添加当前时间的毫秒值的16进制
        pixFour++;
        if (pixFour == pixLen) {
            pixFour = 0;
            pixThree++;
            if (pixThree == pixLen) {
                pixThree = 0;
                pixTwo++;
                if (pixTwo == pixLen) {
                    pixTwo = 0;
                    pixOne++;
                    if (pixOne == pixLen) {
                        pixOne = 0;
                    }
                }
            }
        }
        return sb.append(str62.charAt(pixOne)).append(str62.charAt(pixTwo))
                .append(str62.charAt(pixThree)).append(str62.charAt(pixFour)).toString();
    }
}
