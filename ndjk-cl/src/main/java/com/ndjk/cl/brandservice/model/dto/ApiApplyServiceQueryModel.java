package com.ndjk.cl.brandservice.model.dto;

import com.ndjk.cl.brandservice.model.BrandService;
import com.ndjk.cl.brandservice.model.OrderService;
import com.ndjk.cl.brandservice.model.ServiceOrder;

import java.io.Serializable;
import java.util.List;

/**
 * api查询服务列表model
 * Created by zfwlz on 2018/1/7.
 */
public class ApiApplyServiceQueryModel extends ServiceOrder implements Serializable{

    List<BrandService> brandServiceList;

    String count;

    public List<BrandService> getBrandServiceList() {
        return brandServiceList;
    }

    public void setBrandServiceList(List<BrandService> brandServiceList) {
        this.brandServiceList = brandServiceList;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
