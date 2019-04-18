package com.hnu.wechatorder.dao;

import com.hnu.wechatorder.model.SellerInfo;
import com.hnu.wechatorder.util.KeyUtil;
import com.hnu.wechatorder.util.MD5Util;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SellerInfoMapperTest {

    @Autowired
    private SellerInfoMapper sellerInfoMapper;

    @Test
    public void insertSelectiveTest(){
        SellerInfo sellerInfo = new SellerInfo();
        sellerInfo.setId(KeyUtil.genUniqueKey());
        sellerInfo.setOpenid("abc");
        sellerInfo.setUsername("admin");
        sellerInfo.setPassword(MD5Util.getMD5("admin"));
        sellerInfoMapper.insertSelective(sellerInfo);
    }

}