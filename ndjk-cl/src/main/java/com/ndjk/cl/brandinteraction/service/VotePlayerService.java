package com.ndjk.cl.brandinteraction.service;

import com.ndjk.cl.brandinteraction.model.VotePlayer;

import java.util.List;

/**
 * Created by wl on 2018/1/20.
 */
public interface VotePlayerService {
    /**
     * @Author: wl
     * @Description: 插入投票详情
     * @Date: 2018/1/20  10:21
     * @Version: 2.0
     *
     */
    int insert(VotePlayer votePlayer);

    //投票数展示

    List<VotePlayer> listAll(VotePlayer votePlayer, int currentPage, int pageSize);
}
