package com.hnu.wechatorder.enums;

import lombok.Getter;

/**
 * @Classname OrderStatusEnum
 * @Description TODO
 * @Created by chengqiufeng
 * @Date 2018/12/25 21:27
 */
@Getter
public enum OrderStatusEnum {
    NEW(0, "新订单"),
    FINISHED(1, "完结"),
    CANCEL(2, "已取消"),
    ;

    private Integer code;

    private String message;

    OrderStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}