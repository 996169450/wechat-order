package com.hnu.wechatorder.service.impl;

import com.hnu.wechatorder.dao.ProductCategoryMapper;
import com.hnu.wechatorder.model.ProductCategory;
import com.hnu.wechatorder.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Classname CategoryServiceImpl
 * @Description TODO
 * @Created by chengqiufeng
 * @Date 2018/12/22 20:13
 */
@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private ProductCategoryMapper productCategoryMapper;

    @Override
    public ProductCategory findOne(Integer categoryId) {
        return productCategoryMapper.selectByPrimaryKey(categoryId);
    }

    @Override
    public List<ProductCategory> findAll() {
        return productCategoryMapper.selectAll();
    }

    @Override
    public List<ProductCategory> findByCategoryTypeList(List<Integer> categoryTypeList) {
        return productCategoryMapper.selectByCategoryTypeList(categoryTypeList);
    }

    @Override
    public int addCategory(ProductCategory productCategory) {
        return productCategoryMapper.insert(productCategory);
    }

    @Override
    public int updateCategory(ProductCategory productCategory) {
        return productCategoryMapper.updateByPrimaryKeySelective(productCategory);
    }
}