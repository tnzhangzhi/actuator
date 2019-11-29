package com.ymkj.im.websocket;

import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@Component
@ServerEndpoint("/webchart")
public class WebServer {

    private Session session;

    @OnOpen
    public void onOpen(Session session) throws IOException {
        this.session = session;
        session.getBasicRemote().sendText("欢迎进入聊天室");
    }

    @OnClose
    public void onClose(){

    }
}
