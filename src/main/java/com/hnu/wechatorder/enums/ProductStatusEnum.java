package com.hnu.wechatorder.enums;


import lombok.Getter;

/**
 * @Classname ProductStatusEnum
 * @Description 商品状态
 * @Created by chengqiufeng
 * @Date 2018/12/22 21:41
 */
@Getter
public enum ProductStatusEnum {
    UP(0, "上架"),                    //可以通过自定义字母UP,DOWN来找到对应的代码编号0,1
    DOWN(1,"下架");

    private Integer code;

    private String message;

    ProductStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}