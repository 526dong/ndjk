package com.ndjk.cl.brandservice.model.resp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zfwlz on 2017/12/31.
 */
public class GetBrandServiceListRespModel extends BaseResponseModel implements Serializable{

    private static final long serialVersionUID = 8953271519410031325L;

    private List<GetBrandServiceInfo> data;

    public GetBrandServiceListRespModel() {
    }

    public GetBrandServiceListRespModel(Integer code, String message) {
        super(code, message);
        this.data = new ArrayList<>();
    }

    public GetBrandServiceListRespModel(Integer code, String message, List<GetBrandServiceInfo> data) {
        super(code, message);
        this.data = data;
    }

    public List<GetBrandServiceInfo> getData() {
        return data;
    }

    public void setData(List<GetBrandServiceInfo> data) {
        this.data = data;
    }
}
