package com.ndjk.cl.brandinteraction.service;

import com.ndjk.cl.brandinteraction.model.ThumbsViewList;

/**
 * Created by wl on 2018/1/12.
 */
public interface ThumbsViewListService {

    //更新点赞列表
    int updateByPrimaryKeySelective(ThumbsViewList record);

    //查询点赞总表通过id
    ThumbsViewList findSelective(Long id);
}
