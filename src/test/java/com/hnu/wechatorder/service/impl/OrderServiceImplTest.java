package com.hnu.wechatorder.service.impl;

import com.hnu.wechatorder.dto.OrderDTO;
import com.hnu.wechatorder.model.OrderDetail;
import com.hnu.wechatorder.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname OrderServiceImplTest
 * @Description TODO
 * @Created by chengqiufeng
 * @Date 2018/12/26 16:01
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceImplTest {

    private final String BUYER_OPENID = "88888888";

    @Autowired
    OrderServiceImpl orderServiceIml;

    @Test
    public void createTest(){
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName("刘泽宇");
        orderDTO.setBuyerPhone("1546946512");
        orderDTO.setBuyerAddress("湖南大学");
        orderDTO.setBuyerOpenid(BUYER_OPENID);

        //购物车
        List<OrderDetail> orderDetailList = new ArrayList<>();
        OrderDetail o1 = new OrderDetail();
        o1.setProductId("aad05882a1af459e8b065445e56602b4");
        o1.setProductQuantity(2);

        OrderDetail o2 = new OrderDetail();
        o2.setProductId("7baa95d95d4d41c9ba45902a7440fe49");
        o2.setProductQuantity(1);
        orderDetailList.add(o1);
        orderDetailList.add(o2);

        orderDTO.setOrderDetailList(orderDetailList);
        orderServiceIml.create(orderDTO);

    }
}