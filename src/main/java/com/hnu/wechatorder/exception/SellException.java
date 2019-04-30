package com.hnu.wechatorder.exception;

import com.hnu.wechatorder.enums.ResultEnum;
import lombok.Getter;

/**
 * @Classname SellException
 * @Description TODO
 * @Created by chengqiufeng
 * @Date 2018/12/26 14:34
 */
@Getter
public class SellException extends RuntimeException {      //spring 对于 RuntimeException 异常才会进行事务回滚。

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