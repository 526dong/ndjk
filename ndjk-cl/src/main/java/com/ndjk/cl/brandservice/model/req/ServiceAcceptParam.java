package com.ndjk.cl.brandservice.model.req;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by zfwlz on 2018/1/21.
 */
public class ServiceAcceptParam{

    private Integer id;

    private BigDecimal price;

    public ServiceAcceptParam() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
