package com.ndjk.cl.brandinteraction.service.impl;

import com.ndjk.cl.brandinteraction.dao.ColumnListMapper;
import com.ndjk.cl.brandinteraction.dao.ContentManageMapper;
import com.ndjk.cl.brandinteraction.model.ColumnList;
import com.ndjk.cl.brandinteraction.model.ContentManage;
import com.ndjk.cl.brandinteraction.model.vo.ContentManageVo;
import com.ndjk.cl.brandinteraction.service.ContentManageService;
import com.ndjk.cl.sys.dao.SysAppConfigMapper;
import com.ndjk.cl.sys.model.SysAppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wl on 2018/1/21.
 */
@Service
public class ContentManageServiceImpl implements ContentManageService {
    @Autowired
    private ContentManageMapper contentManageMapper;
    @Autowired
    private ColumnListMapper columnListMapper;
    @Autowired
    private SysAppConfigMapper sysAppConfigMapper;

    @Override
    public int insertContentManage(ContentManage contentManage) {
        return contentManageMapper.insertSelective(contentManage);
    }

    @Override
    public List<ContentManage> listContent(ContentManageVo contentManageVo, int currentPage, int pageSize) {
        List<ContentManage> contentManages = contentManageMapper.listAll(contentManageVo, currentPage, pageSize);
        for (ContentManage contentManage : contentManages) {
            ColumnList columnList = columnListMapper.selectByPrimaryKey(contentManage.getColumnId());
            contentManage.setColumnIdStr(columnList.getColumnName());
            Map<String, Object> param = new HashMap<>();
            param.put("code", contentManage.getColumnType());
            SysAppConfig selective = sysAppConfigMapper.findSelective(param);
            contentManage.setColumnTypeStr(selective.getName());
        }
        return contentManages;
    }

    @Override
    public int updateContentManage(ContentManage contentManage) {
        return contentManageMapper.updateByPrimaryKeySelective(contentManage);
    }

    @Override
    public int deleteContentManage(Long id) {
        return contentManageMapper.deleteByPrimaryKey(id);
    }
}
