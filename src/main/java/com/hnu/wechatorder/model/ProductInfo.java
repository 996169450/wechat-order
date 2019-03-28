package com.hnu.wechatorder.model;

import com.hnu.wechatorder.enums.ProductStatusEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ProductInfo {
    private String productId;

    private String productName;

    private BigDecimal productPrice;

    private Integer productStock;

    private String productDescription;

    private String productIcon;

    private Integer productStatus = ProductStatusEnum.UP.getCode();

    private Integer categoryType;

    private Date createTime;

    private Date updateTime;

}