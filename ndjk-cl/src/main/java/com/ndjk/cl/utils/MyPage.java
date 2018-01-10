package com.ndjk.cl.utils;

import java.util.List;

/**
 * @Discription
 * @Date Create in 2017/12/11 11:45
 * @Author wdl
 * @Modified by:
 */
public class MyPage<T> {

    /**数据*/
    private List<T> data;
    /**条件对象*/
    private Object condition;

    private RdPage page;

    public MyPage(Object condition) {
        this.condition=condition;
    }

    public MyPage(Object condition, int total, int currentPage, int pageSize) {
        this.condition=condition;
        page = new RdPage(total, currentPage, pageSize);
    }

    public MyPage(List<T> data, RdPage page) {
        this.data=data;
        this.page=page;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data=data;
    }

    public RdPage getPage() {
        return page;
    }

    public void setPage(RdPage page) {
        this.page=page;
    }

    public Object getCondition() {
        return condition;
    }

    public void setCondition(Object condition) {
        this.condition=condition;
    }
}
