package com.ndjk.cl.brandinteraction.service.impl;

import com.ndjk.cl.brandinteraction.dao.VoteListMapper;
import com.ndjk.cl.brandinteraction.model.VoteList;
import com.ndjk.cl.brandinteraction.service.VoteListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wl on 2018/1/20.
 */
@Service
public class VoteListServiceImpl implements VoteListService{
    @Autowired
    private VoteListMapper voteListMapper;

    @Override
    public int insert(VoteList voteList) {
        return voteListMapper.insertSelective(voteList);
    }
}
