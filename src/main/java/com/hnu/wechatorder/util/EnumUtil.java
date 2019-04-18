package com.hnu.wechatorder.util;

import com.hnu.wechatorder.enums.CodeEnum;

public class EnumUtil {

    public static <T extends CodeEnum> T getByCode(Integer code, Class<T> enumClass){
        for (T item: enumClass.getEnumConstants()){
            if(code.equals(item.getCode())){
                return item;
            }
        }
        return null;
    }
}
