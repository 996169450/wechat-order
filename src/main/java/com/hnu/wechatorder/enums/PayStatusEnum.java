package com.hnu.wechatorder.enums;

import lombok.Getter;

/**
 * @Classname PayStatusEnum
 * @Description TODO
 * @Created by chengqiufeng
 * @Date 2018/12/26 0:02
 */
@Getter
public enum PayStatusEnum implements CodeEnum{
    WAIT(0, "等待支付"),
    SUCCESS(1, "支付成功")
    ;

    private Integer code;

    private String message;

    PayStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}