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
     *查询所有上架的商品列表（买家端用）
     */
    List<ProductInfo> findUpAll();

    /**
     *分页查询所有商品（卖家后台用）
     */
    PageInfo<ProductInfo> findAll(Integer page, Integer size);

    /**
     * 添加商品（卖家后台用）
     */
    Integer addProduct(ProductInfo productInfo);

    //加库存
    void increaseStock(List<CartDTO> cartDTOList);

    //减库存
    void  decreaseStock(List<CartDTO> cartDTOList);

    //上架（卖家后台用）
    ProductInfo onSale(String productId);

    //下架（卖家后台用）
    ProductInfo offSale(String productId);

    /**
     * 新增或者修改（公用一个接口，卖家后台用）
     */
    Integer saveOrUpdate(ProductInfo productInfo);
}