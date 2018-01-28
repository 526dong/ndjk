package com.ndjk.cl.brandinteraction.model.vo;

import com.ndjk.cl.brandinteraction.model.ContentManage;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by wl on 2018/1/28.
 */
public class ContentManageVo extends ContentManage {
    private Integer thumbsNumStart;
    private Integer thumbsNumEnd;
    private Integer viewsNumStart;
    private Integer viewsNumEnd;
    private Integer collectionNumStart;
    private Integer collectionNumEnd;
    private Date create_timeStart;
    private Date create_timeEnd;

    public ContentManageVo() {
    }

    public Integer getThumbsNumStart() {
        return thumbsNumStart;
    }

    public void setThumbsNumStart(Integer thumbsNumStart) {
        this.thumbsNumStart = thumbsNumStart;
    }

    public Integer getThumbsNumEnd() {
        return thumbsNumEnd;
    }

    public void setThumbsNumEnd(Integer thumbsNumEnd) {
        this.thumbsNumEnd = thumbsNumEnd;
    }

    public Integer getViewsNumStart() {
        return viewsNumStart;
    }

    public void setViewsNumStart(Integer viewsNumStart) {
        this.viewsNumStart = viewsNumStart;
    }

    public Integer getViewsNumEnd() {
        return viewsNumEnd;
    }

    public void setViewsNumEnd(Integer viewsNumEnd) {
        this.viewsNumEnd = viewsNumEnd;
    }

    public Integer getCollectionNumStart() {
        return collectionNumStart;
    }

    public void setCollectionNumStart(Integer collectionNumStart) {
        this.collectionNumStart = collectionNumStart;
    }

    public Integer getCollectionNumEnd() {
        return collectionNumEnd;
    }

    public void setCollectionNumEnd(Integer collectionNumEnd) {
        this.collectionNumEnd = collectionNumEnd;
    }

    public Date getCreate_timeStart() {
        return create_timeStart;
    }

    public void setCreate_timeStart(Date create_timeStart) {
        this.create_timeStart = create_timeStart;
    }

    public Date getCreate_timeEnd() {
        return create_timeEnd;
    }

    public void setCreate_timeEnd(Date create_timeEnd) {
        this.create_timeEnd = create_timeEnd;
    }
}
