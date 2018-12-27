package com.hnu.wechatorder.service.impl;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hnu.wechatorder.dto.OrderDTO;
import com.hnu.wechatorder.model.OrderDetail;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class OrderServiceImplTest {

    private final String BUYER_OPENID = "88888888";

    private final String ORDER_ID = "086bae6c4dc74a2d8702b101107629dc";

    private final String OPEN_ID = "110110";

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
        OrderDTO result = orderServiceIml.create(orderDTO);

        log.info("【创建订单】，result = {}",result);
    }

    @Test
    public void findOneTest(){
        OrderDTO result = orderServiceIml.findOne(ORDER_ID);
        log.info("【查询单个订单】，result = {}",result);
    }

    @Test
    public void findListTest(){
        PageBounds pageBounds = new PageBounds(0,3);
        PageList<OrderDTO> orderDTOPageList = orderServiceIml.findList(OPEN_ID,pageBounds);
        log.info("【查询订单列表】，result = {}",orderDTOPageList);
    }

    @Test
    public void cancelTest(){
        OrderDTO orderDTO = orderServiceIml.findOne(ORDER_ID);
        orderDTO =  orderServiceIml.cancel(orderDTO);
        log.info("【取消订单】，result = {}",orderDTO);
    }

    @Test
    public void finishTest(){
        OrderDTO orderDTO = orderServiceIml.findOne(ORDER_ID);
        orderDTO =  orderServiceIml.finish(orderDTO);
        log.info("【取消订单】，result = {}",orderDTO);
    }

    @Test
    public void paidTest(){
        OrderDTO orderDTO = orderServiceIml.findOne(ORDER_ID);
        orderDTO =  orderServiceIml.paid(orderDTO);
        log.info("【支付订单】，result = {}",orderDTO);
    }
}