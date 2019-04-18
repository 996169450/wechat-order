package com.hnu.wechatorder.dao;

import com.hnu.wechatorder.model.SellerInfo;
import org.apache.ibatis.annotations.Select;

public interface SellerInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(SellerInfo record);

    int insertSelective(SellerInfo record);

    SellerInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SellerInfo record);

    int updateByPrimaryKey(SellerInfo record);

    @Select("select * from seller_info where openid=#{openid}")
    SellerInfo selectByOpenid(String openid);

    @Select("select * from seller_info where username=#{username} and password=#{password}")
    SellerInfo selectByAccAndPwd(String username,String password);

    @Select("select * from seller_info where username=#{username}")
    SellerInfo selectByUsername(String username);
}