package com.hnu.wechatorder.controller;

import com.hnu.wechatorder.config.WechatAccountConfig;
import com.hnu.wechatorder.enums.ResultEnum;
import com.hnu.wechatorder.exception.SellException;
import com.hnu.wechatorder.service.WechatMessageService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.TreeSet;

@Controller
@RequestMapping("/wechat")
@Slf4j
public class WechatController {

    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private WechatAccountConfig wechatAccountConfig;

    @Autowired
    private WechatMessageService wechatMessageService;

    /**
     * 换取code
     * @param returnUrl  {前端配置的sellUrl}/#/
     * @return
     */
    @GetMapping("/authorize")
    public String authorize(@RequestParam("returnUrl") String returnUrl){
        //1.配置
        //2.调用方法
        String url = wechatAccountConfig.getProjectUrl() + "/sell/wechat/userInfo";
        String redirectUrl = wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAuth2Scope.SNSAPI_USERINFO, URLEncoder.encode(returnUrl));
//        String redirectUrl = String.format("https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=%s&connect_redirect=1#wechat_redirect", wxMpConfigStorage.getAppId(), url, WxConsts.OAuth2Scope.SNSAPI_USERINFO, StringUtils.trimToEmpty(returnUrl));
        return "redirect:"+redirectUrl;
    }

    /**
     * 通过code换取openid
     * @param code
     * @param returnUrl {前端配置的sellUrl}/#/
     * @return
     */
    @GetMapping("/userInfo")
    public String userInfo(@RequestParam("code") String code,@RequestParam("state") String returnUrl){
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken;
        try {
            wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
        }catch (WxErrorException e){
            log.error("【微信网页授权】{}",e);
            throw new SellException(ResultEnum.WECHAT_MP_ERROR);
        }
        String openid = wxMpOAuth2AccessToken.getOpenId();
        return "redirect:"+returnUrl+"?openid="+openid;            /**"redirect:"重定向的地址不带http://协议会自动带上当前路径前缀*/
    }

//    /**
//     *验证服务器，用于在公众号上与用户接发消息
//     * @param request
//     * @param response
//     */
//    @GetMapping("/verify")
//    public void index(HttpServletRequest request, HttpServletResponse response){
//        log.info("微信接入服务器");
//        String signature = request.getParameter("signature");
//        String timestamp = request.getParameter("timestamp");
//        String nonce = request.getParameter("nonce");
//        String token = "weixin";
//        String echostr = request.getParameter("echostr");
//        if (verifyInfo(signature, timestamp, nonce, token)) {
//            log.info("echostr为:{}", echostr);
//            if (echostr != null) {
//                try {
//                    response.getWriter().write(echostr);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        } else {
//            log.info("signature为:{}", signature);
//            log.info("timestamp为:{}", timestamp);
//            log.info("nonce为:{}", nonce);
//            log.info("token为:{}", token);
//        }
//    }
//
//    public Boolean verifyInfo(String signature, String timestamp, String nonce,
//                              String token) {
//        TreeSet<String> set = new TreeSet<>();
//        set.add(token);
//        set.add(timestamp);
//        set.add(nonce);
//        StringBuilder sBuilder = new StringBuilder();
//        for (String item : set) {
//            sBuilder.append(item);
//        }
//        String sign = DigestUtils.sha1Hex(sBuilder.toString());
//        return signature.equalsIgnoreCase(sign);
//    }

    @PostMapping("/verify")
    public void index(HttpServletRequest request, HttpServletResponse response){
        String respXml = null;
        PrintWriter out = null;
        try {
            //将请求、响应的编码设置为utf-8
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");

            //调用核心业务逻辑
            respXml = wechatMessageService.processRequest(request);

            //响应消息
            out = response.getWriter();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            out.print(respXml);
            out.close();
        }

    }

}
