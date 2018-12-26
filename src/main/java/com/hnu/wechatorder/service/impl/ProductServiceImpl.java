package com.hnu.wechatorder.service.impl;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
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
    public List<ProductInfo> findAll(PageBounds pageBounds) {
        return productInfoMapper.selectAll(pageBounds);
    }

    @Override
    public int addProduct(ProductInfo productInfo) {
        return productInfoMapper.insert(productInfo);
    }

    @Override
    public void increaseStock(List<CartDTO> cartDTOList) {

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
}