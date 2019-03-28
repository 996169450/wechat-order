package com.hnu.wechatorder.model;

import com.hnu.wechatorder.enums.OrderStatusEnum;
import com.hnu.wechatorder.enums.PayStatusEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderMaster {
    /**订单id*/
    private String orderId;

    /**买家名字*/
    private String buyerName;

    /**买家手机号*/
    private String buyerPhone;

    /**买家地址*/
    private String buyerAddress;

    /**买家openid*/
    private String buyerOpenid;

    /**订单总金额*/
    private BigDecimal orderAmount;

    /**订单状态，默认为0新下单*/
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();

    /**支付状态，默认为0未支付*/
    private Integer payStatus = PayStatusEnum.WAIT.getCode();

    private Date createTime;

    private Date updateTime;

}