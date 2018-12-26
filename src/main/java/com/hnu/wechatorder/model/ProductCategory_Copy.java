package com.hnu.wechatorder.model;

//import javax.persistence.Entity;                           //这些都是jpa包下的注解
//import javax.persistence.GeneratedValue;
//import javax.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Table;
import java.util.Date;

/**
 * @Classname ProductCategory
 * @Description TODO
 * @Created by chengqiufeng
 * @Date 2018/12/20 13:37
 */
//@Table(name = "tb_product_category")
//@Entity
@Data
@DynamicUpdate
public class ProductCategory_Copy {
//    @Id
//    @GeneratedValue
    //类目id
    private Integer categoryId;

    //类目名称
    private String categoryName;

    //类目编号
    private Integer categoryType;

    //创建时间
    private Date createTime;

    //更新时间
    private Date updateTime;

}