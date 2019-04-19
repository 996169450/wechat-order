package com.hnu.wechatorder.service.impl;

import com.hnu.wechatorder.dto.OrderDTO;
import com.hnu.wechatorder.service.OrderService;
import com.hnu.wechatorder.service.PushMessageService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class PushMessageServiceImplTest {

    @Autowired
    private PushMessageService pushMessageService;

    @Autowired
    private OrderService orderService;

    @Test
    public void orderStatusUpdatePush() {
        OrderDTO orderDTO = orderService.findOne("1554111833884825143");
        pushMessageService.orderStatusUpdatePush(orderDTO);
    }
}