package com.ndjk.cl.brandinteraction.service.impl;

import com.ndjk.cl.brandinteraction.dao.ContentManageMapper;
import com.ndjk.cl.brandinteraction.model.ContentManage;
import com.ndjk.cl.brandinteraction.service.ContentManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wl on 2018/1/21.
 */
@Service
public class ContentManageServiceImpl implements ContentManageService{
    @Autowired
    private ContentManageMapper contentManageMapper;
    @Override
    public int insertContentManage(ContentManage contentManage) {
        return contentManageMapper.insertSelective(contentManage);
    }

    @Override
    public List<ContentManage> listContent(ContentManage contentManage, int currentPage, int pageSize) {
        return contentManageMapper.listAll(contentManage,currentPage,pageSize);
    }

    @Override
    public int updateContentManage(ContentManage contentManage) {
        return contentManageMapper.updateByPrimaryKeySelective(contentManage);
    }
}
