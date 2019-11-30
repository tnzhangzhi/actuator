package com.ymkj.im.websocket;

import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

@Component
@ServerEndpoint("/webchart")
public class WebServer {

    private static Session session;
    private AtomicLong atomicLong = new AtomicLong();

    @OnOpen
    public void onOpen(Session session) throws IOException {
        this.session = session;
        session.getBasicRemote().sendText("欢迎进入聊天室");
    }

    @OnClose
    public void onClose(){

    }

    public void sendMsg(String msg) throws IOException {
        long count = atomicLong.incrementAndGet();
        if(session!=null) {
            session.getBasicRemote().sendText(msg+"--"+count);
        }else{
            System.out.println("websocket 回话还没开启");
        }
    }
}
