package com.hnu.wechatorder.aspect;

import com.hnu.wechatorder.constant.CookieConstant;
import com.hnu.wechatorder.constant.RedisConstant;
import com.hnu.wechatorder.exception.SellerAuthorizeException;
import com.hnu.wechatorder.util.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@Slf4j
public class SellerAuthorizeAspect {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    //定义切入点
    @Pointcut("execution(public * com.hnu.wechatorder.controller.Seller*.*(..)) && !execution(public * com.hnu.wechatorder.controller.SellerUserController.*(..))")
    public void verify(){}

    @Before("verify()")
    public void doVerify(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        //查询Cookie
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if (cookie == null){
            log.warn("【登陆校验】Cookie中查不到Token");
            throw new SellerAuthorizeException();
        }

        //查询Redis
        String tokenValue = stringRedisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_PREFIX,cookie.getValue()));
        if (StringUtils.isEmpty(tokenValue)){
            log.warn("【登陆校验】Redis中查不到Token");
            throw new SellerAuthorizeException();
        }

    }
}
