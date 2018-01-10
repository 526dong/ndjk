package com.ndjk.cl.utils;

import java.io.Serializable;

/**
 * Created by zfwlz on 2018/1/8.
 */
public class PageUtil implements Serializable{
    private int beginPage; //开始条数
    private int pageSize;      //查询数量

    public PageUtil() {
    }

    public PageUtil(int page, int size) {
        this.beginPage = size*(page-1);
        this.pageSize = size;
    }

    public int getBeginPage() {
        return beginPage;
    }

    public void setBeginPage(int beginPage) {
        this.beginPage = beginPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}

