package com.ndjk.cl.brandinteraction.service.impl;

import com.ndjk.cl.brandinteraction.dao.ThumbsViewListMapper;
import com.ndjk.cl.brandinteraction.model.ThumbsViewList;
import com.ndjk.cl.brandinteraction.service.ThumbsViewListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wl on 2018/1/12.
 */
@Service("thumbsViewListService")
public class ThumbsViewListServiceImpl implements ThumbsViewListService{
    @Autowired
    private ThumbsViewListMapper thumbsViewListMapper;
    @Override
    public int updateByPrimaryKeySelective(ThumbsViewList record) {
        return thumbsViewListMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public ThumbsViewList findSelective(Long id) {
        return thumbsViewListMapper.selectByPrimaryKey(id);
    }
}
