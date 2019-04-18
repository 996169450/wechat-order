package com.hnu.wechatorder.controller;

import com.hnu.wechatorder.constant.CookieConstant;
import com.hnu.wechatorder.constant.RedisConstant;
import com.hnu.wechatorder.enums.ResultEnum;
import com.hnu.wechatorder.model.SellerInfo;
import com.hnu.wechatorder.service.SellerService;
import com.hnu.wechatorder.util.CookieUtil;
import com.hnu.wechatorder.util.MD5Util;
import com.hnu.wechatorder.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/seller")
public class SellerUserController {

    @Autowired
    private SellerService sellerService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 单纯跳转到登陆页面
     * @return
     */
    @GetMapping("/login")
    public ModelAndView login(){
        return new ModelAndView("login");
    }

    /**
     * 登陆页面提交表单后的验证
     * @param username
     * @param password
     * @param map
     * @return
     */
    @PostMapping("/login")
    public ModelAndView login(@RequestParam("username") String username,
                              @RequestParam("password") String password,
                              HttpServletResponse response,
                              Map<String,Object> map){
        //1.username和password去数据库里面匹配
        SellerInfo sellerInfo = sellerService.findSellerByUsername(username);
            //如果不存在此用户名或者密码不正确，跳到错误页面
        if (sellerInfo == null || !sellerInfo.getPassword().equals(MD5Util.getMD5(password))){
            map.put("msg", ResultEnum.LOGIN_FAIL.getMessage());
            map.put("url","/sell/seller/order/list");
            return new ModelAndView("common/error",map);
        }

        //2.设置token至redis
        String token = StringUtil.getUUID();
        Integer expire = RedisConstant.EXPIRE;
        stringRedisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX,token),username,expire, TimeUnit.SECONDS);

        //3.设置token至cookies
        CookieUtil.set(response, CookieConstant.TOKEN, token, expire);

        return new ModelAndView("redirect:/seller/order/list");     //跳转最好用带http的绝对路径
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request,
                               HttpServletResponse response,
                               Map<String,String> map){
        //1.从Cookie里面查询
        Cookie cookie = CookieUtil.get(request,CookieConstant.TOKEN);
        if (cookie != null){
            //2.清除Redis
            stringRedisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX,cookie.getValue()));

            //3.清除Cookie(将Cookie的过期时间设置为0)
            CookieUtil.set(response,CookieConstant.TOKEN,null,0);
        }

        map.put("msg",ResultEnum.LOGOUT_SUCCESS.getMessage());
        map.put("url","/sell/seller/order/list");
        return new ModelAndView("common/success",map);
    }
}
