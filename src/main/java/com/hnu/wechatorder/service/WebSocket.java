package com.hnu.wechatorder.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@ServerEndpoint("/webSocket")
@Slf4j
public class WebSocket {

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    //concurrent包的线程安全Set，用来存放每个客户端对应的WebSocket对象。
    private static CopyOnWriteArraySet<WebSocket> webSocketSet = new CopyOnWriteArraySet<>();

    /**
     * 建立连接调用
     * @param session
     */
    @OnOpen
    public void onOpen(Session session){
        this.session = session;
        webSocketSet.add(this);
        log.info("【websocket消息】有新的连接，总数:{}",webSocketSet.size());
    }

    /**
     * 关闭连接调用
     */
    @OnClose
    public void onClose(){
        webSocketSet.remove(this);
        log.info("【websocket消息】连接断开，总数:{}",webSocketSet.size());
    }

    /**
     * 发生错误时调用
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error){
        log.info("【websocket消息】发生错误:{}",error);
    }

    /**
     * 接受到客户端发送来的消息时调用
     * @param message
     */
    @OnMessage
    public void onMessage(String message){
        log.info("【websocket消息】收到客户端发送的消息:{}",message);
    }

    /**
     * 群发消息
     * @param message
     */
    public void sendMessage(String message){
        log.info("【websocket消息】广播消息，message:{}",message);
        for (WebSocket webSocket : webSocketSet){
            try {
                webSocket.session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
