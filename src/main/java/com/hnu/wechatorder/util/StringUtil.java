package com.hnu.wechatorder.util;

import java.util.UUID;

/**
 * @Classname StringUtil
 * @Description TODO
 * @Created by chengqiufeng
 * @Date 2018/12/23 10:49
 */
public class StringUtil {
    //加入synchronized关键字，防止多线程会生成重复的UUID
    public static synchronized String getUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }
}