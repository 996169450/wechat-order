package com.hnu.wechatorder.enums;

import lombok.Getter;

/**
 * @Classname ResultEnum
 * @Description TODO
 * @Created by chengqiufeng
 * @Date 2018/12/26 14:32
 */
@Getter
public enum ResultEnum {

    PRODUCT_NOT_EXIST(10, "商品不存在"),
    PRODUCT_STOCK_ERROR(11, "商品库存不正确"),
    ;

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}