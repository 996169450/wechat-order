package com.hnu.wechatorder.handler;

import com.hnu.wechatorder.exception.SellException;
import com.hnu.wechatorder.exception.SellerAuthorizeException;
import com.hnu.wechatorder.util.ResultUtil;
import com.hnu.wechatorder.view.ResultVO;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 统一捕获并且处理异常
 */
@ControllerAdvice
public class SellerExceptionHandler {

    /**
     * 卖家登陆异常捕获
     * @return
     */
    @ExceptionHandler(value = SellerAuthorizeException.class)
    public ModelAndView handlerAuthorizeException(){
        return new ModelAndView("redirect:/seller/login");
    }

    /**
     *自定义SellException捕获
     * @param e
     * @return
     */
    @ExceptionHandler(value = SellException.class)
    @ResponseBody
    public ResultVO handlerSellException(SellException e){
        return ResultUtil.error(e.getCode(),e.getMessage());
    }
}
