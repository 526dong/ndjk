package com.ndjk.cl.brandservice.model.resp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zfwlz on 2017/12/31.
 */
public class ApplyServiceListRespModel extends BaseResponseModel implements Serializable{
    private static final long serialVersionUID = 6678793661866388605L;

    private List<ApplyServiceListModel> data;

    public ApplyServiceListRespModel(Integer code, String message) {
        super(code, message);
        this.data = new ArrayList<>();
    }

    public ApplyServiceListRespModel(Integer code, String message, List<ApplyServiceListModel> data) {
        super(code, message);
        this.data = data;
    }

    public List<ApplyServiceListModel> getData() {
        return data;
    }

    public void setData(List<ApplyServiceListModel> data) {
        this.data = data;
    }
}
