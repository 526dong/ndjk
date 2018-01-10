package com.ndjk.cl.brandservice.model.searchModel;

import com.ndjk.cl.utils.PageUtil;
import com.ndjk.cl.utils.RdPage;

import java.io.Serializable;

/**
 * Created by zfwlz on 2018/1/8.
 */
public class SearchKindergartensModel extends PageUtil implements Serializable{

    private String kgName;
    private int status;

    public SearchKindergartensModel(int page, int size) {
        super(page, size);
    }

    public String getKgName() {
        return kgName;
    }

    public void setKgName(String kgName) {
        this.kgName = kgName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
