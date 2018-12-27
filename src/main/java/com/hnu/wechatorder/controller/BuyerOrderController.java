package com.hnu.wechatorder.controller;

import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.hnu.wechatorder.converter.OrderForm2OrderDTO;
import com.hnu.wechatorder.dto.OrderDTO;
import com.hnu.wechatorder.enums.ResultEnum;
import com.hnu.wechatorder.exception.SellException;
import com.hnu.wechatorder.form.OrderForm;
import com.hnu.wechatorder.service.OrderService;
import com.hnu.wechatorder.util.ResultUtil;
import com.hnu.wechatorder.view.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Classname BuyerOrderController
 * @Description TODO
 * @Created by chengqiufeng
 * @Date 2018/12/27 14:13
 */
@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private OrderService orderService;

    //创建订单
    @PostMapping("/create")
    public ResultVO<Map<String,String>> create(@Valid OrderForm orderForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.error("【创建订单】参数不正确，orderForm={}",orderForm);
            throw new SellException(ResultEnum.PARAM_REEOR.getCode(),bindingResult.getFieldError().getDefaultMessage());
        }
        OrderDTO orderDTO = OrderForm2OrderDTO.convert(orderForm);
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("【创建订单】购物车不能为空");
            throw new SellException(ResultEnum.CART_EMPTY);
        }

        OrderDTO createResult = orderService.create(orderDTO);
        Map<String,String> map = new HashMap<>();
        map.put("orderId",createResult.getOrderId());

        return ResultUtil.success(map);
    }

    //订单列表
    @GetMapping("/list")
    public ResultVO<List<OrderDTO>> list(@RequestParam("openid") String openid,
                                         @RequestParam(value = "page", defaultValue = "0") Integer page,
                                         @RequestParam(value = "size", defaultValue = "10") Integer size){
        if(StringUtils.isEmpty(openid)){
            log.error("【查询订单列表】openid为空");
            throw new SellException(ResultEnum.PARAM_REEOR);
        }

        PageBounds pageBounds = new PageBounds(page,size, Order.formString("update_time.desc"));
        PageList<OrderDTO> orderDTOPageList = orderService.findList(openid,pageBounds);

        return ResultUtil.success(orderDTOPageList);
    }

    //订单详情
    @GetMapping("/detail")
    public ResultVO<OrderDTO> detail(@RequestParam("openid") String openid,
                                     @RequestParam("oderId") String oderId){
        if(StringUtils.isEmpty(openid)){
            log.error("【查询订单详情】openid为空");
            throw new SellException(ResultEnum.PARAM_REEOR);
        }
        if(StringUtils.isEmpty(openid)){
            log.error("【查询订单详情】oderId为空");
            throw new SellException(ResultEnum.PARAM_REEOR);
        }

        //TODO 不安全的做法，需要对openid进行校验，之后改进
        OrderDTO orderDTO = orderService.findOne(oderId);
        return ResultUtil.success(orderDTO);

    }

    //取消订单
    @PostMapping("cancel")
    public ResultVO cancel(@RequestParam("openid") String openid,
                           @RequestParam("oderId") String oderId){
        if(StringUtils.isEmpty(openid)){
            log.error("【取消订单】openid为空");
            throw new SellException(ResultEnum.PARAM_REEOR);
        }
        if(StringUtils.isEmpty(openid)){
            log.error("【取消订单】oderId为空");
            throw new SellException(ResultEnum.PARAM_REEOR);
        }
        //TODO 不安全的做法，需要对openid进行校验，之后改进
        OrderDTO orderDTO = orderService.findOne(oderId);
        orderService.cancel(orderDTO);  //直接调用取消方法就行了，因为如果取消有问题会抛异常，我们捕获就行了
        return ResultUtil.success();
    }
}