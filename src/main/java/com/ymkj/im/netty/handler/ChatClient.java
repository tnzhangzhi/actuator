package com.ymkj.im.netty.handler;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class ChatClient {

    public static void main(String[] args) throws Exception{
        String host = "127.0.0.1";
        int port = 8082;
        EventLoopGroup workgroup = new NioEventLoopGroup();
        try{
            Bootstrap b = new Bootstrap();
            b.group(workgroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE,true);
            b.handler(new ChannelInitializer<SocketChannel>() {

                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
                    ch.pipeline().addLast(new StringDecoder());
                    ch.pipeline().addLast(new StringEncoder());
                    ch.pipeline().addLast(new ChatClientHandler());
                }
            });

            ChannelFuture f = b.connect(host,port).sync();
            f.channel().closeFuture().sync();
        }finally {
            workgroup.shutdownGracefully();
        }
    }
}
