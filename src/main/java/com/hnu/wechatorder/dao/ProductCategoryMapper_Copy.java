package com.hnu.wechatorder.dao;

import com.hnu.wechatorder.model.ProductCategory_Copy;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

/**
 * @Classname ProductCategoryMapper
 * @Description TODO
 * @Created by chengqiufeng
 * @Date 2018/12/22 14:01
 */
public interface ProductCategoryMapper_Copy {

    @Insert("insert into product_category(category_name, category_type) values(#{categoryName},#{categoryType})")
    int insertByMap(Map<String, Object> map);

    @Select("select * from product_category where category_type = #{categoryType}")
    ProductCategory_Copy findBycategoryType(int categoryType);
}