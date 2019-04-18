package com.hnu.wechatorder.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hnu.wechatorder.dao.OrderMasterMapper;
import com.hnu.wechatorder.model.OrderMaster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private OrderMasterMapper orderMasterMapper;

    @GetMapping("/list")
    public PageInfo<OrderMaster> list(@RequestParam(value = "page",defaultValue = "1") Integer page,
                                      @RequestParam(value = "size",defaultValue = "2") Integer size){
        PageHelper.startPage(page,size);
        List<OrderMaster> list = orderMasterMapper.selectAll();
        PageInfo<OrderMaster> orderMasterPageInfo = new PageInfo<>(list);
        return orderMasterPageInfo;
    }
}
