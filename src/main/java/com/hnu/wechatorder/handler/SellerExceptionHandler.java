package com.hnu.wechatorder.handler;

import com.hnu.wechatorder.exception.SellerAuthorizeException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * 统一捕获并且处理异常
 */
@ControllerAdvice
public class SellerExceptionHandler {

    @ExceptionHandler(value = SellerAuthorizeException.class)
    public ModelAndView handlerAuthorizeException(){
        return new ModelAndView("redirect:/seller/login");
    }
}
