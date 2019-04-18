package com.hnu.wechatorder.service;

import com.hnu.wechatorder.model.ProductCategory;

import java.util.List;

/**
 * @Classname CategoryService
 * @Description TODO
 * @Created by chengqiufeng
 * @Date 2018/12/22 20:07
 */
public interface CategoryService {

    ProductCategory findOne(Integer categoryId);

    List<ProductCategory> findAll();

    List<ProductCategory> findByCategoryTypeList(List<Integer> categoryTypeList);

    Integer addCategory(ProductCategory productCategory);

    Integer updateCategory(ProductCategory productCategory);

}