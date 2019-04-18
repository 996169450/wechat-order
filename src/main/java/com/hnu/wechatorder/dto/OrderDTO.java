package com.hnu.wechatorder.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hnu.wechatorder.enums.OrderStatusEnum;
import com.hnu.wechatorder.enums.PayStatusEnum;
import com.hnu.wechatorder.model.OrderDetail;
import com.hnu.wechatorder.util.EnumUtil;
import com.hnu.wechatorder.util.serializer.Date2LongSerializer;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Classname OrderDTO
 * @Description OrderMaster的数据传输对象,用于在各个层之间传递数据
 * @Created by chengqiufeng
 * @Date 2018/12/26 13:30
 */
@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
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

    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;

    private List<OrderDetail> orderDetailList;

    @JsonIgnore  //对象序列化的时候会忽略这个字段
    public OrderStatusEnum getOrderStatusEnum(){
        return EnumUtil.getByCode(orderStatus,OrderStatusEnum.class);
    }

    @JsonIgnore
    public PayStatusEnum getPayStatusEnum(){
        return EnumUtil.getByCode(payStatus,PayStatusEnum.class);
    }
}