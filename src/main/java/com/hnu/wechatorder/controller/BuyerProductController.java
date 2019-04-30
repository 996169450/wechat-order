package com.hnu.wechatorder.controller;

import com.hnu.wechatorder.model.ProductCategory;
import com.hnu.wechatorder.model.ProductInfo;
import com.hnu.wechatorder.service.CategoryService;
import com.hnu.wechatorder.service.ProductService;
import com.hnu.wechatorder.util.ResultUtil;
import com.hnu.wechatorder.view.ProductInfoVO;
import com.hnu.wechatorder.view.ProductVO;
import com.hnu.wechatorder.view.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Classname BuyerProductController
 * @Description TODO
 * @Created by chengqiufeng
 * @Date 2018/12/23 11:21
 */
@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @GetMapping("/list")
    @Cacheable(cacheNames = "product", key = "123" , unless = "#result.getCode() != 0")
    public ResultVO<List<ProductVO>> list(){
        //1.查询所有的上架商品
        List<ProductInfo> productInfoList = productService.findUpAll();

        //2.查询上架商品的所有类目（一次性查完）
        //   (Java8 lambda表达式)
        List<Integer> categoryTypeList = productInfoList.stream().map(e->e.getCategoryType()).collect(Collectors.toList());
        List<ProductCategory> productCategoryList = categoryService.findByCategoryTypeList(categoryTypeList);     //注意底层sql需要对list为空的情况做判断，否则会报错

        //3.数据拼装
        List<ProductVO> productVOList = new ArrayList<>();
        for (ProductCategory productCategory:productCategoryList) {
            ProductVO productVO = new ProductVO();
            productVO.setCategoryType(productCategory.getCategoryType());
            productVO.setCategoryName(productCategory.getCategoryName());
            List<ProductInfoVO> productInfoVOList = new ArrayList<>();
            for (ProductInfo productInfo:productInfoList){
                if (productInfo.getCategoryType().equals(productCategory.getCategoryType())){
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo,productInfoVO);   //spring提供的一个对象属性复制方法
                    productInfoVOList.add(productInfoVO);
                }
            }
            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);
        }

        //4.返回结果
        return ResultUtil.success(productVOList);
    }
}