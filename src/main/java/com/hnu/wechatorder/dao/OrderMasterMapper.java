package com.hnu.wechatorder.dao;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hnu.wechatorder.model.OrderMaster;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface OrderMasterMapper {
    int deleteByPrimaryKey(String orderId);

    int insert(OrderMaster record);

    int insertSelective(OrderMaster record);

    OrderMaster selectByPrimaryKey(String orderId);

    int updateByPrimaryKeySelective(OrderMaster record);

    int updateByPrimaryKey(OrderMaster record);

    PageList<OrderMaster> findByBuyerOpenId(String buyerOpenid, PageBounds pageBounds);

//    @Select("select * from order_master")
//    PageList<OrderMaster> selectAll();               //这里不能用PageList作为接受数据的对象

    List<OrderMaster> selectAll();
}