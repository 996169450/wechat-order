package com.hnu.wechatorder.service.impl;

import com.hnu.wechatorder.model.SellerInfo;
import com.hnu.wechatorder.service.SellerService;
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
public class SellerServiceImplTest {

    private static final String OPENID = "dsadsa";

    @Autowired
    private SellerService sellerService;

    @Test
    public void findSellerInfoByOpenid(){
        SellerInfo sellerInfo = sellerService.findSellerInfoByOpenid(OPENID);
        log.info("{}",sellerInfo);
    }

}