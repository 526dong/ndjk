package com.ndjk.cl.brandservice.service.impl;

import com.ndjk.cl.brandservice.dao.ServiceOrderMapper;
import com.ndjk.cl.brandservice.model.BrandService;
import com.ndjk.cl.brandservice.model.OrderService;
import com.ndjk.cl.brandservice.model.ServiceOrder;
import com.ndjk.cl.brandservice.model.dto.ApiApplyServiceQueryModel;
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
import java.util.Map;

/**
 * Created by zfwlz on 2017/12/26.
 */
@Service("brandServiceOrderService")
public class BrandServiceOrderServiceImpl implements BrandServiceOrderService{

    public static final Logger logger = LoggerFactory.getLogger(BrandServiceOrderServiceImpl.class);

    @Autowired
    private ServiceOrderMapper serviceOrderMapper;
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
        List<ApiApplyServiceQueryModel> apiApplyServiceModels = this.serviceOrderMapper.selectApplyListByKgId(kgId);
        if(apiApplyServiceModels == null || apiApplyServiceModels.size() < 1){
            return null;
        }
        List<ApplyServiceListModel> selectApplyServiceListRespModels = new ArrayList<>();
        for(ApiApplyServiceQueryModel apiApplyServiceQueryModel:apiApplyServiceModels) {
            //构造订单信息返回参数
            ApplyServiceListModel selectApplyServiceListRespModel =
                    new ApplyServiceListModel(apiApplyServiceQueryModel.getActivityName(),
                            apiApplyServiceQueryModel.getCreateTime(),String.valueOf(apiApplyServiceQueryModel.getState()));

            List<BrandService> brandServices =apiApplyServiceQueryModel.getBrandServiceList();
            if(brandServices == null || brandServices.size()<1){
                logger.info("订单"+apiApplyServiceQueryModel.getId()+"服务为null");
            }
            StringBuffer sb = new StringBuffer();
            for(BrandService brandService:brandServices){
                //构造订单服务信息

                sb.append(brandService.getName());
                sb.append("*");
                sb.append(brandService.getCount());
                sb.append(",");
            }
            sb.deleteCharAt(sb.length()-1);
            selectApplyServiceListRespModel.setDetail(sb.toString());
            selectApplyServiceListRespModels.add(selectApplyServiceListRespModel);
        }
        return selectApplyServiceListRespModels;
    }

    /**
     * 查询服务列表
     * @return
     */
    @Override
    public List<Map<String, Object>> selectList(Map<String, Object> params) {
        return this.serviceOrderMapper.selectList(params);
    }

}
