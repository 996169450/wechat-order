package com.hnu.wechatorder.exception;

import com.hnu.wechatorder.enums.ResultEnum;

/**
 * @Classname SellException
 * @Description TODO
 * @Created by chengqiufeng
 * @Date 2018/12/26 14:34
 */
public class SellException extends RuntimeException {

    private Integer code;

    public SellException(ResultEnum resultEnum){
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public SellException(Integer code, String message){
        super(message);
        this.code = code;
    }
}