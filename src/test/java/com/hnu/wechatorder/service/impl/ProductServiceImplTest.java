package com.hnu.wechatorder.service.impl;

import com.hnu.wechatorder.enums.ProductStatusEnum;
import com.hnu.wechatorder.model.ProductInfo;
import com.hnu.wechatorder.service.ProductService;
import com.hnu.wechatorder.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ProductServiceImplTest {

    @Autowired
    private ProductService productService;

    @Test
    public void offSaleTest(){
        ProductInfo productInfo = productService.offSale("6d4a82e249db4071a1290adf8272811e");
        log.info("{}", JsonUtil.toJson(productInfo));
    }
}