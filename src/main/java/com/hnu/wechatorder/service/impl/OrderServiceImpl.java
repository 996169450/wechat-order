package com.hnu.wechatorder.service.impl;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hnu.wechatorder.converter.OrderMaster2OrderDTO;
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
import com.hnu.wechatorder.util.KeyUtil;
import com.hnu.wechatorder.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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
@Slf4j
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
    @Transactional    //由于productService.decreaseStock(cartDTOList)子方法里面设置了事务，这里就不用再次设置了，会传播过来
    public OrderDTO create(OrderDTO orderDTO) {
        String orderId = KeyUtil.genUniqueKey();
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
            orderDetail.setDetailId(KeyUtil.genUniqueKey());
            orderDetail.setOrderId(orderId);
            BeanUtils.copyProperties(productInfo,orderDetail,new String[]{"createTime","updateTime"});
            orderDetailMapper.insert(orderDetail);
        }
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterMapper.insert(orderMaster);

        //4.扣库存
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().map(e-> new CartDTO(e.getProductId(),e.getProductQuantity())).collect(Collectors.toList());
        productService.decreaseStock(cartDTOList);
        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {
        OrderMaster orderMaster = orderMasterMapper.selectByPrimaryKey(orderId);
        if(orderMaster == null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        List<OrderDetail> orderDetailList = orderDetailMapper.findByOrderId(orderId);
        if(CollectionUtils.isEmpty(orderDetailList)){
            throw  new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }

    @Override
    public PageList<OrderDTO> findList(String buyerOpenid, PageBounds pageBounds) {
        //注意，这里不需要OrderDetail,因为查询订单列表是不会显示订单详情的
        PageList<OrderMaster> orderMasterPageList = orderMasterMapper.findByBuyerOpenId(buyerOpenid, pageBounds);
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTO.convert(orderMasterPageList);
        PageList<OrderDTO> orderDTOPageList = new PageList<>(orderDTOList, orderMasterPageList.getPaginator());
        return orderDTOPageList;
    }

    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
        //1.判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("【取消订单】订单状态不正确，orderId={}，orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //2.修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        OrderMaster orderMaster = OrderMaster2OrderDTO.convert(orderDTO);
        int row = orderMasterMapper.updateByPrimaryKey(orderMaster);
        if (row<0){
            log.error("【取消订单】更新失败，orderMaster={}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        //3.返还库存
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("【取消订单】订单中无商品详情，orderDTO={}",orderDTO);
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream()
                .map(e->new CartDTO(e.getProductId(),e.getProductQuantity()))
                .collect(Collectors.toList());
        productService.increaseStock(cartDTOList);

        //4.如果已支付，需要退款
        if(orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())){
            //TODO
        }
        return orderDTO;
    }

    @Override
    public OrderDTO finish(OrderDTO orderDTO) {
        //判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("【完结订单】订单状态不正确，orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        OrderMaster orderMaster = OrderMaster2OrderDTO.convert(orderDTO);
        int row = orderMasterMapper.updateByPrimaryKey(orderMaster);
        if (row<0){
            log.error("【完结订单】更新失败，orderMaster={}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }

    @Override
    public OrderDTO paid(OrderDTO orderDTO) {
        //1.判断订单状态
        //判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("【支付订单】订单状态不正确，orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //2.判断支付状态
        if(!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())){
            log.error("【支付订单】订单支付状态不正确，orderDTO={}",orderDTO);
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }

        //3.修改支付状态
        //修改订单状态
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster orderMaster = OrderMaster2OrderDTO.convert(orderDTO);
        int row = orderMasterMapper.updateByPrimaryKey(orderMaster);
        if (row<0){
            log.error("【支付订单】更新失败，orderMaster={}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }
}