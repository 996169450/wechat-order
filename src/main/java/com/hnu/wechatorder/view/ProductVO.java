package com.hnu.wechatorder.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @Classname ProductVO
 * @Description TODO
 * @Created by chengqiufeng
 * @Date 2018/12/23 11:27
 */
@Data
public class ProductVO {

    @JsonProperty("type")
    private Integer CategoryType;

    @JsonProperty("name")
    private String CategoryName;

    @JsonProperty("foods")
    private List<ProductInfoVO> productInfoVOList;
}