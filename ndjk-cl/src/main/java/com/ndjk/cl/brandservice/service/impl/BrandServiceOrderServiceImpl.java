package com.ndjk.cl.brandservice.service.impl;

import com.ndjk.cl.brandservice.dao.ServiceOrderMapper;
import com.ndjk.cl.brandservice.model.BrandService;
import com.ndjk.cl.brandservice.model.OrderService;
import com.ndjk.cl.brandservice.model.ServiceOrder;
import com.ndjk.cl.brandservice.model.resp.ApplyServiceListModel;
import com.ndjk.cl.brandservice.service.BrandOrderServiceService;
import com.ndjk.cl.brandservice.service.BrandServiceOrderService;
import com.ndjk.cl.brandservice.service.BrandServiceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zfwlz on 2017/12/26.
 */
@Service("brandServiceOrderService")
public class BrandServiceOrderServiceImpl implements BrandServiceOrderService{

    public static final Logger logger = LoggerFactory.getLogger(BrandServiceOrderServiceImpl.class);

    @Autowired
    private ServiceOrderMapper serviceOrderMapper;
    @Autowired
    private BrandOrderServiceService brandOrderServiceService;
    @Autowired
    private BrandServiceService brandServiceService;
    /**
     * 新增订单 返回订单id
     * @param serviceOrder
     * @return
     */
    public Integer insertSelective(ServiceOrder serviceOrder){
        return this.serviceOrderMapper.insertSelective(serviceOrder);
    }

    /**
     * 根据幼儿园id查询服务列表
     * @param kgId
     * @return
     */
    public List<ApplyServiceListModel> selectByKgId(int kgId){
        //查询用户所有的订单
        List<ServiceOrder> serviceOrderList = this.serviceOrderMapper.selectListByKgId(kgId);
        if(serviceOrderList == null || serviceOrderList.size() < 1){
            return null;
        }
        List<ApplyServiceListModel> selectApplyServiceListRespModels = new ArrayList<>();
        for(ServiceOrder serviceOrder:serviceOrderList) {
            //构造订单信息返回参数
            ApplyServiceListModel selectApplyServiceListRespModel =
                    new ApplyServiceListModel(serviceOrder.getActivityName(),
                            serviceOrder.getCreateTime(),String.valueOf(serviceOrder.getState()));


            List<OrderService> orderServices = this.brandOrderServiceService.selectListByOrderId(serviceOrder.getId());
            if(orderServices == null || orderServices.size()<1){
                logger.info("订单"+serviceOrder.getId()+"服务为null");
            }
            StringBuffer sb = new StringBuffer();
            for(OrderService orderService:orderServices){
                //构造订单服务信息

                BrandService brandService = this.brandServiceService.selectById(orderService.getServiceId());
                sb.append(brandService.getName());
                sb.append("*");
                sb.append(orderService.getCount());
                sb.append(",");
            }
            sb.deleteCharAt(sb.length()-1);
            selectApplyServiceListRespModel.setDetail(sb.toString());
            selectApplyServiceListRespModels.add(selectApplyServiceListRespModel);
        }
        return selectApplyServiceListRespModels;
    }

}
