package com.ndjk.cl.brandservice.service.impl;

import com.ndjk.cl.brandservice.dao.BrandServiceMapper;
import com.ndjk.cl.brandservice.model.BrandService;
import com.ndjk.cl.brandservice.service.BrandServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zfwlz on 2017/12/21.
 */
@Service("brandServiceService")
public class BrandServiceServiceImpl implements BrandServiceService{
    @Autowired
    private BrandServiceMapper brandServiceMapper;


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

}
