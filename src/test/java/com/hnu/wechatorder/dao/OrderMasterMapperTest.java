package com.hnu.wechatorder.dao;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hnu.wechatorder.model.OrderMaster;
import com.hnu.wechatorder.util.KeyUtil;
import com.hnu.wechatorder.util.StringUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

/**
 * @Classname OrderMasterMapperTest
 * @Description TODO
 * @Created by chengqiufeng
 * @Date 2018/12/26 0:22
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterMapperTest {

    @Autowired
    private OrderMasterMapper orderMasterMapper;

    private String buyerOpenid = "110110";

    @Test
    public void insertTest(){
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId(KeyUtil.genUniqueKey());
        orderMaster.setBuyerName("秋香哥");
        orderMaster.setBuyerPhone("16487965413");
        orderMaster.setBuyerAddress("飞机场");
        orderMaster.setBuyerOpenid(buyerOpenid);
        orderMaster.setOrderAmount(new BigDecimal(16.0));

        orderMasterMapper.insert(orderMaster);
    }

    @Test
    public void findByBuyerOpenIdTest(){
        PageBounds pageBounds = new PageBounds(0,3);
        PageList<OrderMaster> orderMasterPageList = orderMasterMapper.findByBuyerOpenId(buyerOpenid, pageBounds);   //PageList本身就是一个ArrayList，查出来的数据都存在里面了
        System.out.print(orderMasterPageList.get(0).getBuyerName());
    }

    @Test
    public void selectByPrimaryKeyTest(){
        String orderId = "050dfa106c2d4ff4b2da57c04a2159b1";
        OrderMaster orderMaster = orderMasterMapper.selectByPrimaryKey(orderId);
        Assert.assertTrue("查找指定订单",orderMaster!=null);
    }
}