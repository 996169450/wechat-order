package com.hnu.wechatorder.service;

import com.hnu.wechatorder.model.SellerInfo;

public interface SellerService {

    SellerInfo findSellerInfoByOpenid(String openid);

    SellerInfo findSellerByAccAndPwd(String username,String password);

    SellerInfo findSellerByUsername(String username);
}
