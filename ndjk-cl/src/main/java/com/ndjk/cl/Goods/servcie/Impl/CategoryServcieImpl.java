package com.ndjk.cl.Goods.servcie.Impl;

import com.ndjk.cl.Goods.dao.GoodsCategoryMapper;
import com.ndjk.cl.Goods.model.GoodsCategory;
import com.ndjk.cl.Goods.servcie.CategoryServcie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 商品分类
 * Created by zfwlz on 2018/1/4.
 */
@Service("categoryServcie")
public class CategoryServcieImpl implements CategoryServcie{

    @Autowired
    private GoodsCategoryMapper goodsCategoryMapper;
    /**
     * 新增
     * @param goodsCategory
     * @return
     */
    public int insertSelective(GoodsCategory goodsCategory){
       return this.goodsCategoryMapper.insertSelective(goodsCategory);
    }

    /**
     * 删除
     * @param id
     */
    public void delCategoryServcie(int id){
        this.goodsCategoryMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改服务
     * @param goodsCategory
     */
    public void updateCategoryServcie(GoodsCategory goodsCategory){
        GoodsCategory goodsCategory1 = this.goodsCategoryMapper.selectByPrimaryKey(goodsCategory.getId());
        if(goodsCategory1 == null){
            return;
        }
        this.updateCategoryServcie(goodsCategory);
    }

    /**
     * 排序
     * @param idStr 逗号隔开的字符串
     */
    public void sortCategoryServcie(String idStr){
        if(idStr == null || "".equals(idStr)){
            return;
        }
        List<GoodsCategory> goodsCategoryList = new ArrayList<>();
        String[] ids = idStr.split(",");
        for(int i=0;i<ids.length;i++){
            GoodsCategory goodsCategory = this.goodsCategoryMapper.selectByPrimaryKey(Integer.valueOf(ids[i]));
            if(goodsCategory != null){
                goodsCategoryList.add(goodsCategory);
            }
        }
        //便利list 重新给sort字段赋值  从小到大
        if(goodsCategoryList == null || goodsCategoryList.size() <=0){
            return;
        }
        int i = 1;
        for(GoodsCategory goodsCategory:goodsCategoryList){
            goodsCategory.setSort(1);
            i++;
        }
    }
}
