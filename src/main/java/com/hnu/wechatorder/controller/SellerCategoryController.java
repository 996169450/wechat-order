package com.hnu.wechatorder.controller;

import com.hnu.wechatorder.exception.SellException;
import com.hnu.wechatorder.form.CategoryForm;
import com.hnu.wechatorder.model.ProductCategory;
import com.hnu.wechatorder.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/seller/category")
public class SellerCategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 类目列表
     * @param map
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list(Map<String,Object> map){
        List<ProductCategory> productCategoryList = categoryService.findAll();
        map.put("productCategoryList",productCategoryList);
        return new ModelAndView("category/list",map);
    }

    /**
     * 展示
     * @param categoryId
     * @param map
     * @return
     */
    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "categoryId",required = false) Integer categoryId,
                              Map<String,Object> map){
        if(categoryId != null){
            ProductCategory productCategory = categoryService.findOne(categoryId);
            map.put("productCategory",productCategory);
        }

        return new ModelAndView("category/index",map);
    }

    /**
     * 新增/展示
     * @param categoryForm
     * @param bindingResult
     * @param map
     * @return
     */
    @PostMapping("/save_or_update")
    public ModelAndView saveAndUpdate(@Valid CategoryForm categoryForm,
                                      BindingResult bindingResult,
                                      Map<String,Object> map){
        if (bindingResult.hasErrors()){
            map.put("msg",bindingResult.getFieldError().getDefaultMessage());
            map.put("url","/sell/seller/category/index");
            return new ModelAndView("common/error",map);
        }
        ProductCategory productCategory = new ProductCategory();
        BeanUtils.copyProperties(categoryForm,productCategory);

        try {
            if (productCategory.getCategoryId() == null){
                categoryService.addCategory(productCategory);
            }else {
                 categoryService.updateCategory(productCategory);
            }
        } catch (SellException e) {
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/category/index");
            return new ModelAndView("common/error",map);
        }
        map.put("url","/sell/seller/category/list");
        return new ModelAndView("common/success",map);

    }
}
