package com.hnu.wechatorder.service.impl;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hnu.wechatorder.dao.OrderDetailMapper;
import com.hnu.wechatorder.dao.OrderMasterMapper;
import com.hnu.wechatorder.dao.ProductInfoMapper;
import com.hnu.wechatorder.dto.CartDTO;
import com.hnu.wechatorder.dto.OrderDTO;
import com.hnu.wechatorder.enums.OrderStatusEnum;
import com.hnu.wechatorder.enums.PayStatusEnum;
import com.hnu.wechatorder.enums.ResultEnum;
import com.hnu.wechatorder.exception.SellException;
import com.hnu.wechatorder.model.OrderDetail;
import com.hnu.wechatorder.model.OrderMaster;
import com.hnu.wechatorder.model.ProductInfo;
import com.hnu.wechatorder.service.OrderService;
import com.hnu.wechatorder.service.ProductService;
import com.hnu.wechatorder.util.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Classname OrderServiceImpl
 * @Description TODO
 * @Created by chengqiufeng
 * @Date 2018/12/26 13:41
 */
@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    ProductInfoMapper productInfoMapper;

    @Autowired
    OrderDetailMapper orderDetailMapper;

    @Autowired
    OrderMasterMapper orderMasterMapper;

    @Autowired
    ProductService productService;

    @Override
//    @Transactional    //由于productService.decreaseStock(cartDTOList)子方法里面设置了事务，这里就不用再次设置了，会传播过来
    public OrderDTO create(OrderDTO orderDTO) {
        String orderId = StringUtil.getUUID();
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
        //1.查询商品（数量，价格）
        for (OrderDetail orderDetail: orderDTO.getOrderDetailList()){
            ProductInfo productInfo = productInfoMapper.selectByPrimaryKey(orderDetail.getProductId());
            if(productInfo == null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            //2.计算总价
            orderAmount = productInfo.getProductPrice().multiply(new BigDecimal(orderDetail.getProductQuantity())).add(orderAmount);

            //3.写入数据库（order_master和order_detail）
            orderDetail.setDetailId(StringUtil.getUUID());
            orderDetail.setOrderId(orderId);
            BeanUtils.copyProperties(productInfo,orderDetail,new String[]{"createTime","updateTime"});
            orderDetailMapper.insert(orderDetail);
        }
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderId(orderId);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterMapper.insert(orderMaster);

        //4.扣库存
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().map(e-> new CartDTO(e.getProductId(),e.getProductQuantity())).collect(Collectors.toList());
        productService.decreaseStock(cartDTOList);
        return orderDTO;
    }

//    @Override
//    public OrderDTO findOne(String orderId) {
//
//    }
//
//    @Override
//    public PageList<OrderDTO> findList(String buyerOpenid, PageBounds pageBounds) {
//
//    }
//
//    @Override
//    public int cancel(OrderDTO orderDTO) {
//
//    }
//
//    @Override
//    public int finish(OrderDTO orderDTO) {
//
//    }
//
//    @Override
//    public int paid(OrderDTO orderDTO) {
//
//    }
}