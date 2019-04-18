package com.hnu.wechatorder.service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.pagehelper.PageInfo;
import com.hnu.wechatorder.dto.CartDTO;
import com.hnu.wechatorder.model.ProductInfo;

import java.util.List;

/**
 * @Classname ProductService
 * @Description TODO
 * @Created by chengqiufeng
 * @Date 2018/12/22 21:05
 */
public interface ProductService {
    ProductInfo findOne(String productId);

    /**
     *查询所有上架的商品列表
     */
    List<ProductInfo> findUpAll();

    /**
     *分页查询所有商品
     */
    PageInfo<ProductInfo> findAll(Integer page, Integer size);

    Integer addProduct(ProductInfo productInfo);

    //加库存
    void increaseStock(List<CartDTO> cartDTOList);

    //减库存
    void  decreaseStock(List<CartDTO> cartDTOList);

    //上架
    ProductInfo onSale(String productId);

    //下架
    ProductInfo offSale(String productId);

    Integer saveOrUpdate(ProductInfo productInfo);
}