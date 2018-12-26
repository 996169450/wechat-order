package com.hnu.wechatorder.dto;

import com.hnu.wechatorder.enums.OrderStatusEnum;
import com.hnu.wechatorder.enums.PayStatusEnum;
import com.hnu.wechatorder.model.OrderDetail;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Classname OrderDTO
 * @Description OrderMaster的数据传输对象
 * @Created by chengqiufeng
 * @Date 2018/12/26 13:30
 */
@Data
public class OrderDTO {

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
    private Integer orderStatus;

    /**支付状态，默认为0未支付*/
    private Integer payStatus;

    private Date createTime;

    private Date updateTime;

    private List<OrderDetail> orderDetailList;
}