package com.hnu.wechatorder.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Classname ProductInfoVO
 * @Description TODO
 * @Created by chengqiufeng
 * @Date 2018/12/23 11:41
 */
@Data
public class ProductInfoVO implements Serializable {

    private static final long serialVersionUID = 245582319082499280L;

    @JsonProperty("id")
    private  String productId;

    @JsonProperty("name")
    private  String productName;

    @JsonProperty("price")
    private BigDecimal productPrice;

    @JsonProperty("description")
    private  String productDescription;

    @JsonProperty("icon")
    private  String productIcon;
}