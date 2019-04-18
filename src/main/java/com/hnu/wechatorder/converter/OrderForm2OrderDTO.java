package com.hnu.wechatorder.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hnu.wechatorder.dto.OrderDTO;
import com.hnu.wechatorder.enums.ResultEnum;
import com.hnu.wechatorder.exception.SellException;
import com.hnu.wechatorder.form.OrderForm;
import com.hnu.wechatorder.model.OrderDetail;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname OrderForm2OrderDTO
 * @Description TODO
 * @Created by chengqiufeng
 * @Date 2018/12/27 15:06
 */
@Slf4j
public class OrderForm2OrderDTO {

    public static OrderDTO convert(OrderForm orderForm){
        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());

        //json字符串转换成对象
        Gson gson = new Gson();
        List<OrderDetail> orderDetailList;
        try {    //转换可能出错，如传入的格式不正确，这个时候try catch一下
            orderDetailList = gson.fromJson(orderForm.getItems(),new TypeToken<List<OrderDetail>>(){}.getType());
        }catch (Exception e){
            log.error("【对象转换】错误，string={}",orderForm.getItems());
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        orderDTO.setOrderDetailList(orderDetailList);

        return orderDTO;

    }
}