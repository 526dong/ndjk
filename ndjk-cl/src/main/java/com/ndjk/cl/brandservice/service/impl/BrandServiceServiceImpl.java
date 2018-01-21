package com.ndjk.cl.brandservice.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.ndjk.cl.Goods.model.GoodsCategory;
import com.ndjk.cl.brandservice.dao.BrandServiceMapper;
import com.ndjk.cl.brandservice.dao.OrderServiceMapper;
import com.ndjk.cl.brandservice.dao.ServiceOrderMapper;
import com.ndjk.cl.brandservice.model.BrandService;
import com.ndjk.cl.brandservice.model.OrderService;
import com.ndjk.cl.brandservice.model.ServiceOrder;
import com.ndjk.cl.brandservice.model.req.ServiceAcceptParam;
import com.ndjk.cl.brandservice.service.BrandOrderServiceService;
import com.ndjk.cl.brandservice.service.BrandServiceService;
import com.ndjk.cl.utils.JsonUtils;
import com.ndjk.cl.utils.MyRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zfwlz on 2017/12/21.
 */
@Service("brandServiceService")
public class BrandServiceServiceImpl implements BrandServiceService{
    @Autowired
    private BrandServiceMapper brandServiceMapper;
    @Autowired
    private BrandOrderServiceService brandOrderServiceService;
    @Autowired
    private OrderServiceMapper orderServiceMapper;
    @Autowired
    private ServiceOrderMapper serviceOrderMapper;
    /**
     * 新增
     * @param brandService
     */
    public int insertSelective(BrandService brandService){

        int bsId = this.brandServiceMapper.insertSelective(brandService);
        return bsId;
    }

    /**
     * 修改
     * @param brandService
     */
    public void updateSelective(BrandService brandService){
        this.brandServiceMapper.updateByPrimaryKeySelective(brandService);
    }

    /**
     * 更新订单服务信息
     * 1、删除订单服务关联信息
     * 2、更新订单服务信息
     * 3、更新订单服务关联信息
     * @param orderId
     * @param priceJsonStr
     */
    @Override
    public void updateServiceByOrderId(Integer orderId, String priceJsonStr) {
        //2、更新订单服务信息
        //判空处理
        if (StringUtils.isEmpty(priceJsonStr)) {
            throw new MyRuntimeException("品牌服务模块报错：该订单下无服务");
        }
        JSONArray jsonArray = JSONArray.parseArray(priceJsonStr);
        if (jsonArray == null || jsonArray.size() == 0) {
            throw new MyRuntimeException("品牌服务模块报错：该订单下无服务");
        }
        //json to entity
        BigDecimal totalPrice = new BigDecimal("0");
        for (int i = 0; i < jsonArray.size(); i++) {
            ServiceAcceptParam serviceAcceptParam = JsonUtils.parse(jsonArray.get(i).toString(), ServiceAcceptParam.class);
            //修改单个订单服务的价格
            OrderService orderService = new OrderService();
            orderService.setId(serviceAcceptParam.getId());
            orderService.setPrice(serviceAcceptParam.getPrice());
            orderServiceMapper.updateByPrimaryKeySelective(orderService);
            //计算总价格
            totalPrice = totalPrice.add(serviceAcceptParam.getPrice());
        }
        //修改共订单信息
        ServiceOrder serviceOrder = new ServiceOrder();
        serviceOrder.setId(orderId);
        serviceOrder.setPrice(totalPrice);
        serviceOrder.setState(2);
        serviceOrderMapper.updateByPrimaryKeySelective(serviceOrder);
    }

    /**
     * 保存订单服务关联信息
     * @param orderId
     * @param brandService
     */
    private void saveOrderServiceRelate(Integer orderId, BrandService brandService){
        OrderService orderService = new OrderService();
        orderService.setOrderId(orderId);
        orderService.setServiceId(brandService.getId());
        orderService.setCount(brandService.getCount());
        orderService.setPrice(brandService.getPrice());
        orderService.setCreateTime(new Date());
        //保存订单服务关联信息
        brandOrderServiceService.insertSelective(orderService);
    }

    /**
     * 删除
     * @param id
     */
    public void deleteSelective (Integer id){
        this.brandServiceMapper.deleteByPrimaryKey(id);
    }

    /**
     * 根据type查询所有
     * @param type
     * @return
     */
    public List<BrandService> selectByType(int type){
        return this.brandServiceMapper.selectByType(type);
    }

    /**
     * 根据id 查询
     * @param id
     * @return
     */
    public BrandService selectById(int id){
        return this.brandServiceMapper.selectByPrimaryKey(id);
    }

    /**
     * 排序
     * @param idStr 逗号隔开的字符串
     */
    public void sortBrandServcie(String idStr){
        List<BrandService> brandServiceList = new ArrayList<>();
        String[] ids = idStr.split(",");
        for(int i=0;i<ids.length;i++){
            BrandService brandService = new BrandService();
            brandService.setId(Integer.valueOf(ids[i]));
            brandService.setSort(i+1);
            this.brandServiceMapper.updateByPrimaryKey(brandService);
        }
    }
}
