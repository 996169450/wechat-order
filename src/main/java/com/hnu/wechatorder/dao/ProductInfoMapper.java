package com.hnu.wechatorder.dao;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.hnu.wechatorder.model.ProductInfo;

import java.util.List;

public interface ProductInfoMapper {
    int deleteByPrimaryKey(String productId);

    int insert(ProductInfo record);

    int insertSelective(ProductInfo record);

    ProductInfo selectByPrimaryKey(String productId);

    int updateByPrimaryKeySelective(ProductInfo record);

    int updateByPrimaryKey(ProductInfo record);

    List<ProductInfo> selectByProductStatus(int productStatus);

    List<ProductInfo> selectAll(PageBounds pageBounds);

}