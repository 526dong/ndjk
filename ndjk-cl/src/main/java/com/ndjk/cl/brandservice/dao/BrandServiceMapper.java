package com.ndjk.cl.brandservice.dao;

import com.ndjk.cl.brandservice.model.BrandService;

import java.util.List;

public interface BrandServiceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BrandService record);

    int insertSelective(BrandService record);

    BrandService selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BrandService record);

    int updateByPrimaryKey(BrandService record);

    /**
     * 根据type查询所有
     * @param type
     * @return
     */
    List<BrandService> selectByType(Integer type);

    List<BrandService>  selectByOrderId(Integer orderId);
}