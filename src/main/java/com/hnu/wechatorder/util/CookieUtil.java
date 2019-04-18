package com.hnu.wechatorder.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class CookieUtil {

    /**
     * 设置Cookie到HttpServletResponse
     * @param response
     * @param name
     * @param value
     * @param maxAge
     */
    public static void set(HttpServletResponse response,String name,String value,Integer maxAge){
        Cookie cookie = new Cookie(name,value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    /**
     * 从HttpServletRequest获取指定Cookie
     * @param request
     * @param name
     * @return
     */
    public static Cookie get(HttpServletRequest request,String name){
        Map<String,Cookie> cookieMap = readCookie(request);
        if (cookieMap.containsKey(name)){
            return cookieMap.get(name);
        }else {
            return null;
        }
    }

    /**
     * 将从HttpServletRequest获取的Cookie[]封装成Map
     * @param request
     * @return
     */
    private static Map<String,Cookie> readCookie(HttpServletRequest request){
        Map<String,Cookie> cookieMap = new HashMap<>();
        Cookie[] cookies = request.getCookies();
        if (cookies != null){
            for (Cookie cookie: cookies) {
                cookieMap.put(cookie.getName(),cookie);
            }
        }
        return cookieMap;
    }
}
