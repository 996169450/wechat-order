package com.hnu.wechatorder.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonUtil {

    /**
     * 将对象转换成json字符串格式
     * @param object
     * @return
     */
    public static String toJson(Object object){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        return gson.toJson(object);
    }
}
