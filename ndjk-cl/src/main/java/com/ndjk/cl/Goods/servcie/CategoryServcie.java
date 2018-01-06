package com.ndjk.cl.Goods.servcie;

import com.ndjk.cl.Goods.model.GoodsCategory;

/**
 * 商品分类
 * Created by zfwlz on 2018/1/4.
 */
public interface CategoryServcie {

    /**
     * 新增
     * @param goodsCategory
     * @return
     */
    int insertSelective(GoodsCategory goodsCategory);

    /**
     * 删除
     * @param id
     */
    void delCategoryServcie(int id);

    /**
     * 修改服务
     * @param goodsCategory
     */
    void updateCategoryServcie(GoodsCategory goodsCategory);

    /**
     * 排序
     * @param idStr 逗号隔开的字符串
     */
    void sortCategoryServcie(String idStr);
}
