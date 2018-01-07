package com.ndjk.cl.brandservice.model;

import java.util.List;
import java.util.Map;

/**
 * @Description 订单服务数据包
 * @author Created by xzd on 2018/1/6.
 */
public class OrderServicePackage {
    public static class Order {
        /**
         * 订单id
         */
        private Integer orderId;

        /**
         * 订单编号
         */
        private String orderNo;

        /**
         * 订单服务列表
         */
        private List<Map<String, Object>> orderServiceList;

        public Integer getOrderId() {
            return orderId;
        }

        public void setOrderId(Integer orderId) {
            this.orderId = orderId;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public List<Map<String, Object>> getOrderServiceList() {
            return orderServiceList;
        }

        public void setOrderServiceList(List<Map<String, Object>> orderServiceList) {
            this.orderServiceList = orderServiceList;
        }
    }

}
