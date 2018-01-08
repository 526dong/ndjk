package com.ndjk.cl.utils;

import java.io.Serializable;

/**
 * 分页Model
 *
 * @author xx
 * @version 2.0
 * @since 2014年1月28日
 */
public class RdPage implements Serializable {

    /**
     * 默认分页起始页面，第一页
     */
    public static final int PAGE_NUM_DEFAULT = 1;

    /**
     * 默认分页条数，10条
     */
    public static final int PAGE_SIZE_DEFAULT = 10;

    /**
     * 总数
     */
    private int total;
    /**
     * 总页数
     */
    private int pages;
    /**
     * 当前页数，从1开始
     */
    private int current;
    /**
     * 每页条数
     */
    private int pageSize;

    private int offset;

    public RdPage() {
    }

    public RdPage(int current, int pageSize) {
        this.current = current;
        this.pageSize = pageSize;
    }

    public RdPage(int total, int current, int pageSize) {
        this.total = total;
        this.pages = total / pageSize;
        if (total % pageSize != 0) {
            this.pages = this.pages + 1;
        }
        int temp = 0;
        if ((temp = current - pages > 0 ? pages : current) == pages || (temp = current <= 0 ? 1 : current) == 1) {
            this.current = temp;
        } else {
            this.current = current;
        }
        this.pageSize = pageSize;


        this.offset = (this.current - 1) * pageSize;

    }

    public long getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
