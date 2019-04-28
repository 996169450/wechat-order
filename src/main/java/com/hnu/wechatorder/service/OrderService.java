package com.hnu.wechatorder.service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.github.pagehelper.PageInfo;
import com.hnu.wechatorder.dto.OrderDTO;

/**
 * @Classname OrderService
 * @Description TODO
 * @Created by chengqiufeng
 * @Date 2018/12/26 13:08
 */
public interface OrderService {

    /**创建订单*/
    OrderDTO create(OrderDTO orderDTO);

    /**查询单个订单*/
    OrderDTO findOne(String orderId);

    /**查询订单列表*/
    PageList<OrderDTO> findList(String buyerOpenid, PageBounds pageBounds);

    /**取消订单*/
    OrderDTO cancel(OrderDTO orderDTO);

    /**完结订单*/
    OrderDTO finish(OrderDTO orderDTO);

    /**支付订单（仅修改状态）*/
    OrderDTO paid(OrderDTO orderDTO);

    /**查询所有订单*/
    PageInfo<OrderDTO> findAll(Integer page, Integer size);
}