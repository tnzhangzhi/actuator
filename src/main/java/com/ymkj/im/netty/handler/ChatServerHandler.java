package com.ymkj.im.netty.handler;

import com.ymkj.im.util.Robot;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ChatServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String message = "你好，欢迎光临!";
        System.out.println(message);
        String answer = Robot.send(message);
        ctx.writeAndFlush(answer+"\r\n");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String message = (String) msg;
        System.out.println(message);
        String answer = Robot.send(message);
        ctx.writeAndFlush(answer+"\r\n");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
