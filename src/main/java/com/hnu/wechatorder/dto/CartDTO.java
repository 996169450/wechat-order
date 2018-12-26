package com.hnu.wechatorder.dto;

import lombok.Data;

/**
 * @Classname CartDTO
 * @Description 购物车的数据传输对象
 * @Created by chengqiufeng
 * @Date 2018/12/26 15:41
 */
@Data
public class CartDTO {

    private String productId;

    private Integer productQuantity;

    public CartDTO() {
    }

    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}