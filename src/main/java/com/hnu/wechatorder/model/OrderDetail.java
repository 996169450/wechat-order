package com.hnu.wechatorder.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetail {
    private String detailId;

    /**订单id*/
    private String orderId;

    /**商品id*/
    private String productId;

    /**商品名称*/
    private String productName;

    /**商品单价*/
    private BigDecimal productPrice;

    /**商品数量*/
    private Integer productQuantity;

    /**商品小图*/
    private String productIcon;

    private Date createTime;

    private Date updateTime;

}