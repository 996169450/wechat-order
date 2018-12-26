package com.hnu.wechatorder.view;

import lombok.Data;

/**
 * @Classname ResultVO
 * @Description http请求返回的最外层对象
 * @Created by chengqiufeng
 * @Date 2018/12/23 11:23
 */
@Data
public class ResultVO<T> {

    /*返回的状态码*/
    private Integer code;

    /*返回的信息*/
    private String msg;

    /*返回的数据*/
    private T data;
}