package com.hnu.wechatorder.service.impl;

import com.hnu.wechatorder.dao.SellerInfoMapper;
import com.hnu.wechatorder.model.SellerInfo;
import com.hnu.wechatorder.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerInfoMapper sellerInfoMapper;

    @Override
    public SellerInfo findSellerInfoByOpenid(String openid) {
        return sellerInfoMapper.selectByOpenid(openid);
    }

    @Override
    public SellerInfo findSellerByAccAndPwd(String username, String password) {
        return sellerInfoMapper.selectByAccAndPwd(username,password);
    }

    @Override
    public SellerInfo findSellerByUsername(String username) {
        return sellerInfoMapper.selectByUsername(username);
    }
}
