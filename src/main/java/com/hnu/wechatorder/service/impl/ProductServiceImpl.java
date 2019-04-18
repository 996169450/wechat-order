package com.hnu.wechatorder.service.impl;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hnu.wechatorder.dao.ProductInfoMapper;
import com.hnu.wechatorder.dto.CartDTO;
import com.hnu.wechatorder.enums.ProductStatusEnum;
import com.hnu.wechatorder.enums.ResultEnum;
import com.hnu.wechatorder.exception.SellException;
import com.hnu.wechatorder.model.ProductInfo;
import com.hnu.wechatorder.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Classname ProductServiceImpl
 * @Description TODO
 * @Created by chengqiufeng
 * @Date 2018/12/22 21:21
 */
@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    ProductInfoMapper productInfoMapper;

    @Override
    public ProductInfo findOne(String productId) {
        return productInfoMapper.selectByPrimaryKey(productId);
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return productInfoMapper.selectByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public PageInfo<ProductInfo> findAll(Integer page, Integer size) {
        PageHelper.startPage(page,size);
        List<ProductInfo> productInfoList = productInfoMapper.selectAll();
        PageInfo<ProductInfo> productInfoPageInfo = new PageInfo<>(productInfoList);
        return productInfoPageInfo;
    }

//    @Override
//    public List<ProductInfo> findAll(PageBounds pageBounds) {
//        return productInfoMapper.selectAll(pageBounds);
//    }

    @Override
    public Integer addProduct(ProductInfo productInfo) {
        return productInfoMapper.insert(productInfo);
    }

    @Override
    @Transactional
    public void increaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO: cartDTOList){
            ProductInfo productInfo = productInfoMapper.selectByPrimaryKey(cartDTO.getProductId());
            if(productInfo == null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            int result = productInfo.getProductStock()+cartDTO.getProductQuantity();
            productInfo.setProductStock(result);
            productInfoMapper.updateByPrimaryKey(productInfo);
        }
    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO: cartDTOList){
            ProductInfo productInfo = productInfoMapper.selectByPrimaryKey(cartDTO.getProductId());
            if(productInfo == null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            int result = productInfo.getProductStock() - cartDTO.getProductQuantity();
            //TODO 这里在多线程时存在超卖问题，后面会使用redis的锁机制来解决
            if (result < 0) {
                throw  new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }

            productInfo.setProductStock(result);
            productInfoMapper.updateByPrimaryKey(productInfo);
        }
    }

    @Override
    public ProductInfo onSale(String productId) {
        ProductInfo productInfo = productInfoMapper.selectByPrimaryKey(productId);
        if(productInfo == null){
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }

        if(productInfo.getProductStatusEnum() == ProductStatusEnum.UP){
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }

        //更新
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        productInfoMapper.updateByPrimaryKey(productInfo);
        return productInfo;
    }

    @Override
    public ProductInfo offSale(String productId) {
        ProductInfo productInfo = productInfoMapper.selectByPrimaryKey(productId);
        if(productInfo == null){
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }

        if(productInfo.getProductStatusEnum() == ProductStatusEnum.DOWN){
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }

        //更新
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        productInfoMapper.updateByPrimaryKey(productInfo);
        return productInfo;
    }

    @Override
    public Integer saveOrUpdate(ProductInfo productInfo) {
        return productInfoMapper.insertOrUpdate(productInfo);
    }
}