package com.ndjk.cl.brandservice.service;

import com.ndjk.cl.brandservice.model.BrandService;

import java.util.List;

/**
 * Created by zfwlz on 2017/12/21.
 */
public interface BrandServiceService {

    /**
     * 新增
     * @param brandService
     */
    int insertSelective(BrandService brandService);

    /**
     * 修改
     * @param brandService
     */
    void updateSelective(BrandService brandService);

    /**
     * 更新订单服务信息
     * @param orderId
     * @param priceJsonStr
     */
    void updateServiceByOrderId(Integer orderId, String priceJsonStr);

    /**
     * 删除
     * @param id
     */
    void deleteSelective (Integer id);

    /**
     * 根据type查询所有
     * @param type
     * @return
     */
    List<BrandService> selectByType(int type);

    /**
     * 根据id 查询
     * @param id
     * @return
     */
    BrandService selectById(int id);

    /**
     * 排序
     * @param idStr 逗号隔开的字符串
     */
    void sortBrandServcie(String idStr);
}
