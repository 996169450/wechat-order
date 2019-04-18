package com.hnu.wechatorder.service;

import com.hnu.wechatorder.dto.OrderDTO;

public interface PushMessageService {

    /**
     * 订单状态更新通知
     * @param orderDTO
     */
    void orderStatusUpdatePush(OrderDTO orderDTO);
}