package com.hnu.wechatorder.dao;

import com.hnu.wechatorder.model.ProductInfo;
import com.hnu.wechatorder.util.StringUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

/**
 * @Classname ProductInfoMapperTest
 * @Description TODO
 * @Created by chengqiufeng
 * @Date 2018/12/23 10:43
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoMapperTest {

    @Autowired
    ProductInfoMapper productInfoMapper;

    @Test
    public void insertTest(){
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId(StringUtil.getUUID());
        productInfo.setProductName("豆花小铺");
        productInfo.setProductPrice(new BigDecimal(3.0));
        productInfo.setProductStock(200);
        productInfo.setProductDescription("小丸子最爱喝的豆花");
        productInfo.setProductIcon("http://xxx.png");
//        productInfo.setProductStatus(0);
        productInfo.setCategoryType(2);
        productInfoMapper.insert(productInfo);
    }
}