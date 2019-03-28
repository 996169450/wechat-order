package com.hnu.wechatorder.service.impl;

import com.hnu.wechatorder.dto.OrderDTO;
import com.hnu.wechatorder.enums.ResultEnum;
import com.hnu.wechatorder.exception.SellException;
import com.hnu.wechatorder.service.OrderService;
import com.hnu.wechatorder.service.PayService;
import com.hnu.wechatorder.util.JsonUtil;
import com.hnu.wechatorder.util.MathUtil;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PayServiceImpl implements PayService {

    private static final String ORDER_NAME = "微信点餐";   //订单名称，任意

    @Autowired
    private BestPayServiceImpl bestPayService;

    @Autowired
    private OrderService orderService;

    @Override
    public PayResponse create(OrderDTO orderDTO) {
        PayRequest payRequest = new PayRequest();
        payRequest.setOpenid(orderDTO.getBuyerOpenid());
        payRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        payRequest.setOrderId(orderDTO.getOrderId());
        payRequest.setOrderName(ORDER_NAME);
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("【微信支付】发起支付，payRequest={}", JsonUtil.toJson(payRequest));

        PayResponse payResponse = bestPayService.pay(payRequest);
        log.info("【微信支付】发起支付，payResponse={}",JsonUtil.toJson(payResponse));
        return payResponse;
    }

    @Override
    public PayResponse notify(String notifyData) {
        //1.验证签名（确定异步通知是微信发过来的）
        //2.支付的状态（有异步通知不一定就支付成功了，需要校验）
        //3.支付金额（支付金额是否一致）
        //4.下单人==支付人

        PayResponse payResponse = bestPayService.asyncNotify(notifyData);
        log.info("【微信支付】异步通知，payResponse={}",payResponse);

        //接收到异步通知，修改订单状态
            //查询订单
        OrderDTO orderDTO = orderService.findOne(payResponse.getOrderId());
            //判断订单是否存在
        if(orderDTO == null){
            log.error("【微信支付】异步通知,订单不存在，orderId={}",payResponse.getOrderId());
            throw  new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
            //判断金额是否一致  0.10   0.1
        if(!MathUtil.equals(orderDTO.getOrderAmount().doubleValue(),payResponse.getOrderAmount())){
            log.error("【微信支付】异步通知,订单金额不一致，orderId={}，微信通知金额={}，系统金额={}",payResponse.getOrderId(),payResponse.getOrderAmount(),orderDTO.getOrderAmount());
            throw new SellException(ResultEnum.WXPAY_NOTIFY_MONEY_VERIFY_ERROR);
        }

        orderService.paid(orderDTO);

        return payResponse;
    }

    @Override
    public RefundResponse refund(OrderDTO orderDTO) {
        RefundRequest refundRequest = new RefundRequest();
        refundRequest.setOrderId(orderDTO.getOrderId());
        refundRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        refundRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("【微信退款】refundRequest={}",JsonUtil.toJson(refundRequest));

        RefundResponse refundResponse = bestPayService.refund(refundRequest);
        log.info("【微信退款】refundResponse={}",JsonUtil.toJson(refundResponse));

        return refundResponse;
    }
}
