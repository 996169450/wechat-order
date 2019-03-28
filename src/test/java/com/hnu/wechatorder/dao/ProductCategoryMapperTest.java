package com.hnu.wechatorder.dao;

import com.hnu.wechatorder.model.ProductCategory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname ProductCategoryMapperTest
 * @Description TODO
 * @Created by chengqiufeng
 * @Date 2018/12/22 14:05
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryMapperTest {

    @Autowired
    private ProductCategoryMapper productCategoryMapper;

//    @Test
//    public void insertByMap(){
//        Map<String, Object> map = new HashMap<>();
//        map.put("categoryName","女生最爱");
//        map.put("categoryType","2");
//        productCategoryMapper.insertByMap(map);
//
//    }

//    @Test
//    public void findBycategoryType(){
//        ProductCategory_Copy productCategory = productCategoryMapper.findBycategoryType(1);
//        System.out.print("+++++");
//
//    }

    @Test
    public void insertTest(){
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName("男生最爱");
        productCategory.setCategoryType(3);

        productCategoryMapper.insert(productCategory);
    }

    @Test
    public void selectByCategoryTypeListTest(){
        List<Integer> list = new ArrayList<>();
        list.add(1);
         List<ProductCategory> productCategoryList = productCategoryMapper.selectByCategoryTypeList(list);
        System.out.print(productCategoryList.toString());

    }

}