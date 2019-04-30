package com.hnu.wechatorder.controller;

import com.github.pagehelper.PageInfo;
import com.hnu.wechatorder.exception.SellException;
import com.hnu.wechatorder.form.ProductForm;
import com.hnu.wechatorder.model.ProductCategory;
import com.hnu.wechatorder.model.ProductInfo;
import com.hnu.wechatorder.service.CategoryService;
import com.hnu.wechatorder.service.ProductService;
import com.hnu.wechatorder.util.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/seller/product")
@Slf4j
public class SellerProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 订单列表
     * @param page  第几页，从第1页开始
     * @param size  一页有多少条数据
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page",defaultValue = "1") Integer page,
                             @RequestParam(value = "size",defaultValue = "10") Integer size,
                             Map<String,Object> map){
        PageInfo<ProductInfo> productInfoPageInfo = productService.findAll(page,size);
        map.put("productInfoPageInfo",productInfoPageInfo);
        return new ModelAndView("product/list",map);
    }

    /**
     * 商品上架
     * @param productId
     * @param map
     * @return
     */
    @GetMapping("/on_sale")
    public ModelAndView onSale(@RequestParam("productId") String productId,
                               Map<String,Object> map){
        try {
            productService.onSale(productId);
        } catch (SellException e) {
           map.put("msg",e.getMessage());
           map.put("url","/sell/seller/product/list");
           return new ModelAndView("common/error");
        }

        map.put("url","/sell/seller/product/list");
        return new ModelAndView("common/success");
    }

    /**
     * 商品下架
     * @param productId
     * @param map
     * @return
     */
    @GetMapping("/off_sale")
    public ModelAndView offSale(@RequestParam("productId") String productId,
                               Map<String,Object> map){
        try {
            productService.offSale(productId);
        } catch (SellException e) {
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/product/list");
            return new ModelAndView("common/error");
        }

        map.put("url","/sell/seller/product/list");
        return new ModelAndView("common/success");
    }


    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "productId",required = false) String productId,
                                Map<String,Object> map){
        if(!StringUtils.isEmpty(productId)){
            ProductInfo productInfo = productService.findOne(productId);
            map.put("productInfo",productInfo);
        }

        //查询所有类目
        List<ProductCategory> productCategoryList = categoryService.findAll();
        map.put("productCategoryList",productCategoryList);

        return new ModelAndView("product/index",map);
    }

    /**
     * 保存/更新
     * @param form
     * @param bindingResult
     * @param map
     * @return
     */
    @PostMapping("/save_or_update")
//    @CachePut(cacheNames = "product" , key = "123")    //每次访问都会先执行方法，然后把return结果缓存
    @CacheEvict(cacheNames = "product" , key = "123")    //每次访问都会清空缓存
    public ModelAndView saveOrUpdate(@Valid ProductForm form,
                             BindingResult bindingResult,
                             Map<String,Object> map){
        if (bindingResult.hasErrors()){
            map.put("msg",bindingResult.getFieldError().getDefaultMessage());
            map.put("url","/sell/seller/product/index");
            return new ModelAndView("common/error",map);
        }

        ProductInfo productInfo = new ProductInfo();
        BeanUtils.copyProperties(form,productInfo);

        if (StringUtils.isEmpty(form.getProductId())){
            productInfo.setProductId(KeyUtil.genUniqueKey());
        }

        try{
            productService.saveOrUpdate(productInfo);
        }catch (Exception e){
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/product/index");
            return new ModelAndView("common/error",map);
        }

        map.put("url","/sell/seller/product/list");
        return new ModelAndView("common/success",map);
    }

}
