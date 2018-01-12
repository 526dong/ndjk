package com.ndjk.cl.brandinteraction.service.impl;

import com.ndjk.cl.brandinteraction.dao.ThumbsViewDetailMapper;
import com.ndjk.cl.brandinteraction.model.ThumbsViewDetail;
import com.ndjk.cl.brandinteraction.service.ThumbsViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wl on 2018/1/12.
 */
@Service("thumbsViewService")
public class ThumbsViewServiceImpl implements ThumbsViewService {
    @Autowired
    private ThumbsViewDetailMapper thumbsViewDetailMapper;

    @Override
    public int insert(ThumbsViewDetail thumbsViewDetail) {
        return thumbsViewDetailMapper.insertSelective(thumbsViewDetail);
    }
}
