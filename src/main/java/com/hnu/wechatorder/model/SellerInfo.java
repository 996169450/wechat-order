package com.hnu.wechatorder.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SellerInfo {
    private String id;

    private String username;

    private String password;

    private String openid;

    private Date createTime;

    private Date updateTime;

}