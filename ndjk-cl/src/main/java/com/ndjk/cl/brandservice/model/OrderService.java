package com.ndjk.cl.brandservice.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OrderService implements Serializable{
    private static final long serialVersionUID = 2091651467638856574L;
    private Integer id;

    private Integer orderId;

    private Integer serviceId;

    private BigDecimal price;

    private Integer count;

    private Date createTime;

    public OrderService() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}