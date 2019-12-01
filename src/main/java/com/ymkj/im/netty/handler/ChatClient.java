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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatClient {

    public static void main(String[] args) throws Exception{
        ChatClient.run();
    }

    public static void run(){
//        ExecutorService executorService = Executors.newFixedThreadPool(300);
//        for(int i=0;i<300;i++) {
//            executorService.submit(new Runnable() {
//                @Override
//                public void run() {
                    String host = "106.15.229.93";
                    int port = 8082;
                    EventLoopGroup workgroup = new NioEventLoopGroup();
                    try {
                        Bootstrap b = new Bootstrap();
                        b.group(workgroup);
                        b.channel(NioSocketChannel.class);
                        b.option(ChannelOption.SO_KEEPALIVE, true);
                        b.option(ChannelOption.CONNECT_TIMEOUT_MILLIS,100000);
                        b.handler(new ChannelInitializer<SocketChannel>() {

                            @Override
                            protected void initChannel(SocketChannel ch) throws Exception {
                                ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
                                ch.pipeline().addLast(new StringDecoder());
                                ch.pipeline().addLast(new StringEncoder());
                                ch.pipeline().addLast(new ChatClientHandler());
                            }
                        });
                        for(int i=0;i<3000;i++) {
                            ChannelFuture f = b.connect(host, port).sync();
//                            f.channel().closeFuture().sync();
                            System.out.println(i);
                        }
                        ChannelFuture f = b.connect(host, port).sync();
                        f.channel().closeFuture().sync();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        workgroup.shutdownGracefully();
                    }
//                }
//            });
//        }
    }
}
