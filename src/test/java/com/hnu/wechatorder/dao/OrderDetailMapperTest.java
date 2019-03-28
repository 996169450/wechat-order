package com.hnu.wechatorder.dao;

import com.hnu.wechatorder.model.OrderDetail;
import com.hnu.wechatorder.util.KeyUtil;
import com.hnu.wechatorder.util.StringUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Classname OrderDetailMapperTest
 * @Description TODO
 * @Created by chengqiufeng
 * @Date 2018/12/26 12:37
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailMapperTest {

    @Autowired
    OrderDetailMapper orderDetailMapper;

    @Test
    public void insertTest(){
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId(KeyUtil.genUniqueKey());
        orderDetail.setOrderId("f7f3ec566539483485ab67f51662436a");
        orderDetail.setProductId("6d4a82e249db4071a1290adf8272811e");
        orderDetail.setProductName("豆花小铺");
        orderDetail.setProductPrice(new BigDecimal(3.5));
        orderDetail.setProductQuantity(2);
        orderDetail.setProductIcon("http://xxx.png");

        orderDetailMapper.insert(orderDetail);
    }

    @Test
    public void findByOrderIdTest(){
        List<OrderDetail> orderDetailList = orderDetailMapper.findByOrderId("f7f3ec566539483485ab67f51662436a");
        System.out.print(orderDetailList);
    }

}