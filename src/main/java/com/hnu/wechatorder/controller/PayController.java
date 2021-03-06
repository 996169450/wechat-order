package com.hnu.wechatorder.controller;

import com.hnu.wechatorder.dto.OrderDTO;
import com.hnu.wechatorder.enums.ResultEnum;
import com.hnu.wechatorder.exception.SellException;
import com.hnu.wechatorder.service.OrderService;
import com.hnu.wechatorder.service.PayService;
import com.hnu.wechatorder.service.PushMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/pay")
@Slf4j
public class PayController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PayService payService;

    @Autowired
    private PushMessageService pushMessageService;

    @GetMapping("/create")
    public ModelAndView create(@RequestParam("orderId") String orderId,
                               @RequestParam("returnUrl") String returnUrl,   // http://wsililovecqf.top/#/order/1556848243266718011
                               Map<String,Object> map){
        //1.查询是否有此订单
        OrderDTO orderDTO = orderService.findOne(orderId);
        if(orderDTO == null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        //2.发起支付
        //todo
        /*PayResponse payResponse = payService.create(orderDTO);
        map.put("payResponse",payResponse);
        map.put("returnUrl",returnUrl);

        return new ModelAndView("pay/create",map);*/

        //修改支付状态
        orderService.paid(orderDTO);

        //推送微信模板消息
        pushMessageService.orderSuccessPush(orderDTO);

        return new ModelAndView("redirect:"+returnUrl);
    }

    @PostMapping("/notify")
    public ModelAndView notify(@RequestBody String notifyData){
        payService.notify(notifyData);

        //返回给微信处理结果（否则微信会一直发送异步通知）
        return new ModelAndView("pay/success");
    }
}
