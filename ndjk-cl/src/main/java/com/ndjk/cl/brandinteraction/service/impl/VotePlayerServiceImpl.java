package com.ndjk.cl.brandinteraction.service.impl;

import com.ndjk.cl.brandinteraction.dao.VotePlayerMapper;
import com.ndjk.cl.brandinteraction.model.VotePlayer;
import com.ndjk.cl.brandinteraction.service.VotePlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wl on 2018/1/20.
 */
@Service
public class VotePlayerServiceImpl implements VotePlayerService {
    @Autowired
    private VotePlayerMapper votePlayerMapper;

    @Override
    public int insert(VotePlayer votePlayer) {
        return votePlayerMapper.insertSelective(votePlayer);
    }

    @Override
    public List<VotePlayer> listAll() {
        return votePlayerMapper.listAll();
    }
}
