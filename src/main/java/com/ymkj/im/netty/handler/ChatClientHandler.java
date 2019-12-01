package com.ymkj.im.netty.handler;

import com.ymkj.im.util.DateUtil;
import com.ymkj.im.util.Robot;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

public class ChatClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String message = (String) msg;
//        System.out.println(message);
//        String answer = Robot.send(message);
        String answer = "1";
        Thread.sleep(10000l);
        ctx.writeAndFlush(answer+"\r\n");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
