package com.ndjk.cl.brandservice.model.searchModel;

import com.ndjk.cl.utils.PageUtil;
import com.ndjk.cl.utils.RdPage;

import java.io.Serializable;

/**
 * Created by zfwlz on 2018/1/8.
 */
public class SearchKindergartensModel extends PageUtil implements Serializable{

    private String name;
    private int state;

    public SearchKindergartensModel(int page, int size) {
        super(page, size);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
