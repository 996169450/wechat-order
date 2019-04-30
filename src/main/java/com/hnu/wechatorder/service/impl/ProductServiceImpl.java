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
import com.hnu.wechatorder.service.RedisLock;
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
    private ProductInfoMapper productInfoMapper;

    @Autowired
    private RedisLock redisLock;

    private static final int TIMEOUT = 10 * 1000;  //redis锁超时时间，10s

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
    public void decreaseStock(List<CartDTO> cartDTOList) {  //这里在多线程时存在超卖问题，使用redis的锁机制来解决
        for (CartDTO cartDTO: cartDTOList){
            //加锁，在这里加锁可以做到细粒度控制，只对添加到购物车的商品加锁(在查询之前就要加锁)
            long time = System.currentTimeMillis() + TIMEOUT;
            if (!redisLock.lock(cartDTO.getProductId(),String.valueOf(time))){
                throw new SellException(ResultEnum.LOCK_FAIL);   //获取锁失败的话，直接抛异常，返回一个处理结果
            }

            ProductInfo productInfo = productInfoMapper.selectByPrimaryKey(cartDTO.getProductId());
            if(productInfo == null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            int result = productInfo.getProductStock() - cartDTO.getProductQuantity();

            if (result < 0) {
                throw  new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }

            productInfo.setProductStock(result);
            productInfoMapper.updateByPrimaryKey(productInfo);

            //解锁
            redisLock.unlock(cartDTO.getProductId(),String.valueOf(time));
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