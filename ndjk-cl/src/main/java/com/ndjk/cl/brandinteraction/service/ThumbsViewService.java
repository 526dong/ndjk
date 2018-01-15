package com.ndjk.cl.brandinteraction.service;

import com.ndjk.cl.brandinteraction.model.ThumbsViewDetail;

/**
 * Created by wl on 2018/1/12.
 */
public interface ThumbsViewService {

    //点赞记录
    int insert(ThumbsViewDetail thumbsViewDetail);

    //通过微信id查询该微信是否点过赞
    ThumbsViewDetail findThubsViewDetail(String openid);

    //删除该点赞
    int deleteById(Long id);
}
