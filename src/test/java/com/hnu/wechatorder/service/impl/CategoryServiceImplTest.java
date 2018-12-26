package com.hnu.wechatorder.service.impl;

import com.hnu.wechatorder.model.ProductCategory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname CategoryServiceImplTest
 * @Description TODO
 * @Created by chengqiufeng
 * @Date 2018/12/22 20:31
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceImplTest {

    @Autowired
    CategoryServiceImpl categoryService;

    @Test
    public void findOne(){
        ProductCategory productCategory = categoryService.findOne(1);
        System.out.print(productCategory);
    }

    @Test
    public void findAll(){
        List<ProductCategory> productCategoryList = categoryService.findAll();
        System.out.print(productCategoryList.toString());
    }

    @Test
    public void findByCategoryTypeList(){
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        List<ProductCategory> productCategoryList = categoryService.findByCategoryTypeList(list);
        System.out.print(productCategoryList.toString());
    }

    @Test
    public void addCategory(){
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName("男生最爱");
        productCategory.setCategoryType(3);
        categoryService.addCategory(productCategory);
    }

    @Test
    public void updateCategory(){
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName("外星人最爱");
        productCategory.setCategoryId(3);
        categoryService.updateCategory(productCategory);
    }
}