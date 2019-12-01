package com.ymkj.im.netty.handler;

import com.ymkj.im.util.ContextHolder;
import com.ymkj.im.util.DateUtil;
import com.ymkj.im.util.Robot;
import com.ymkj.im.websocket.WebServer;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

public class ChatServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String message = "0";
//        String answer = Robot.send(message);
        ctx.writeAndFlush(message+"\r\n");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        WebServer webServer = (WebServer) ContextHolder.get(WebServer.class);
        String message = (String) msg;
        String answer = "2";
        ctx.writeAndFlush(answer+"\r\n");
//        webServer.sendMsg("她:"+message);
//        webServer.sendMsg("我:"+answer);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
