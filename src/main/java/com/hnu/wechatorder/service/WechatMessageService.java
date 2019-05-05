package com.hnu.wechatorder.service;

import com.hnu.wechatorder.model.message.RespMessage;
import com.hnu.wechatorder.model.message.TextMessage;
import com.hnu.wechatorder.util.MessageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

@Service
@Slf4j
public class WechatMessageService {
    public static String processRequest(HttpServletRequest request) throws Exception {
        // 返回的xml数据
        String resqXml = null;
        // 返回的文本内容
        String resqContext = null;

        // 将request交给消息处理类
        Map<String, String> map = MessageUtils.parseXml(request);

        // 获取消息类型
        String msgType = map.get("MsgType");

        // 封装返回消息
        TextMessage textMessage = new TextMessage();
        textMessage.setFromUserName(map.get("ToUserName"));    //回复的消息FromUserName和ToUserName跟接受到的消息是反过来的
        textMessage.setToUserName(map.get("FromUserName"));
        textMessage.setCreateTime(new Date().getTime());

        // 根据不同消息类型做出相应的处理
        if (msgType.equals(MessageUtils.REQ_MESSAGE_TYPE_TEXT)) {          // 文本消息
            textMessage.setMsgType(MessageUtils.REQ_MESSAGE_TYPE_TEXT);
            resqContext = "<a href=\"http://t.cn/EozgqvJ\">丸子铺</a>";
        }else if (msgType.equals(MessageUtils.REQ_MESSAGE_TYPE_IMAGE)) {   // 图片消息
            resqContext = "您发送了图片消息";
        }else if (msgType.equals(MessageUtils.REQ_MESSAGE_TYPE_EVENT)) {   //事件消息
           //获取事件类型
            String eventType = map.get("EVENT");

            if(eventType.equals(MessageUtils.EVENT_TYPE_SUBSCRIBE)){
           //关注
                resqContext = "谢谢你的关注";
            }

            if(eventType.equals(MessageUtils.EVENT_TYPE_UNSUBSCRIBE)){
           //TODO取消关注

            }

            if(eventType.equals(MessageUtils.EVENT_TYPE_CLICK)){
              //TODO自定义菜单

            }

            if(eventType.equals(MessageUtils.EVENT_TYPE_SCAN)){
             //TODO扫描二维码

            }
        }

        //设置返回内容
        textMessage.setContent(resqContext);

        //将对象转为xml数据
        resqXml = MessageUtils.messageToXml(textMessage);

        return resqXml;
    }

}
