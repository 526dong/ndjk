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
        serviceOrder.setOrderNo("1231231");
        serviceOrder.setPhone(applyServiceReq.getPhone());
        serviceOrder.setWork(applyServiceReq.getWork());
        serviceOrder.setState(1);
        Integer bsId = this.brandServiceOrderService.insertSelective(serviceOrder);

        //2、插入订单和服务的关系数据
        List<String> freeServiceList = JSON.parseArray(applyServiceReq.getFreeServices(), String.class);
        if(freeServiceList == null || freeServiceList.size()== 0){
            return new BaseResponseModel(400,"操作失败");
        }
        for(String freeServcie:freeServiceList){
            Integer servcieId =Integer.valueOf(JSON.parseObject(freeServcie).getString("id"));
            Integer count =Integer.valueOf(JSON.parseObject(freeServcie).getString("count"));
            OrderService orderService = new OrderService();
            orderService.setCount(count);
            orderService.setOrderId(bsId);
            orderService.setServiceId(servcieId);
            this.brandOrderServiceService.insertSelective(orderService);
        }
        String[] nofreeServices = applyServiceReq.getNoFreeServices().split(",");
        if(nofreeServices ==null || nofreeServices.length == 0){
            return new BaseResponseModel(400,"操作失败");
        }
        for(int i=0;i<nofreeServices.length;i++){
            OrderService orderService = new OrderService();
            orderService.setCount(1);
            orderService.setOrderId(bsId);
            orderService.setServiceId(Integer.valueOf(nofreeServices[i]));
            this.brandOrderServiceService.insertSelective(orderService);
        }
        return new BaseResponseModel(200,"操作成功");
    }

    /**
     * 登录
     * @param account 账号
     * @param password 密码
     * @param code 验证码
     * @return
     */
    @RequestMapping(value = "/brandservice/login")
    @ResponseBody
    public Object login(String account,String password,String code){
        Kindergarten kindergarten = this.kindergartenService.selectByLoginName(account);
        if(kindergarten == null){
            return new BaseResponseModel(400,"账号或密码错误");
        }
        if(kindergarten.getStatus() != 1){
            return new BaseResponseModel(400,"账号或密码错误");
        }
        if(!password.endsWith(kindergarten.getPassword())){
            return new BaseResponseModel(400,"账号或密码错误");
        }
        if(!code.equals("123456")){
            return new BaseResponseModel(400,"账号或密码错误");
        }
        return new LoginRespModel(200,"登录成功",kindergarten.getId());
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
     * 修改密码
     * @param updatePwReq
     * @return
     */
    @RequestMapping(value = "/brandservice/updatepassword")
    @ResponseBody
    public Object updatepassword(UpdatePwReq updatePwReq) {
        String result = this.kindergartenService.updatePassWord(updatePwReq.getKgId(),
                updatePwReq.getOldPassword(),updatePwReq.getNewPassword());
        if(result == null){
            return  new BaseResponseModel(200,"操作成功");
        }
        return new BaseResponseModel(400,result);
    }
}
