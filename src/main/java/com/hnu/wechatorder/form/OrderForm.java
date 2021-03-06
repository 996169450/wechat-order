package com.hnu.wechatorder.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @Classname OrderForm
 * @Description TODO
 * @Created by chengqiufeng
 * @Date 2018/12/27 14:58
 */
@Data
public class OrderForm {

    /**
     * 买家姓名
     */
    @NotEmpty(message = "姓名必填")
    private String name;

    /**
     * 买家手机号
     */
    private String phone;

    /**
     * 买家地址
     */
    @NotEmpty(message = "地址必填")
    private String address;

    /**
     * 买家微信openoid
     */
    @NotEmpty(message = "openoid必填")
    private String openid;

    /**
     * 购物车
     */
    @NotEmpty(message = "购物车不能为空")
    private String items;
}