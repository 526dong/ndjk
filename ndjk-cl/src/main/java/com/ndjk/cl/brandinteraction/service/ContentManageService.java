package com.ndjk.cl.brandinteraction.service;

import com.ndjk.cl.brandinteraction.model.ContentManage;
import com.ndjk.cl.brandinteraction.model.VotePlayer;

import java.util.List;

/**
 * Created by wl on 2018/1/21.
 */
public interface ContentManageService {
    //内容发布
    int insertContentManage(ContentManage contentManage);

    //内容列表
    List<ContentManage> listContent(ContentManage contentManage, int currentPage, int pageSize);

    //内容修改
    int updateContentManage(ContentManage contentManage);
}
