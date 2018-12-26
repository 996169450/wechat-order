package com.hnu.wechatorder.dao;

import com.hnu.wechatorder.model.ProductCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductCategoryMapper {
    int deleteByPrimaryKey(Integer categoryId);

    int insert(ProductCategory record);

    int insertSelective(ProductCategory record);

    ProductCategory selectByPrimaryKey(Integer categoryId);

    int updateByPrimaryKeySelective(ProductCategory record);

    int updateByPrimaryKey(ProductCategory record);

    List<ProductCategory> selectAll();

    List<ProductCategory> selectByCategoryTypeList(@Param("categoryTypeList")List<Integer> categoryTypeList);
}