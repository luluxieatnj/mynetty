package com.xll.netty7;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.net.InetSocketAddress;

public class MyServer {
   /* public static void main(String[] args) throws Exception {
        NioEventLoopGroup boosGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boosGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new WebSocketServerInitializer());

            ChannelFuture future = bootstrap.bind(8899).sync();
            future.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully(); // 优雅关闭
            boosGroup.shutdownGracefully();
        }
    }*/
   public static void main(String[] args) throws Exception {
       EventLoopGroup bossGroup = new NioEventLoopGroup();
       EventLoopGroup workerGroup = new NioEventLoopGroup();

       try {
           ServerBootstrap serverBootstrap = new ServerBootstrap();
           serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).
                   handler(new LoggingHandler(LogLevel.INFO)).
                   childHandler(new WebSocketServerInitializer());

           ChannelFuture channelFuture = serverBootstrap.bind(new InetSocketAddress(8899)).sync();
           channelFuture.channel().closeFuture().sync();
       } finally {
           bossGroup.shutdownGracefully();
           workerGroup.shutdownGracefully();
       }
   }
}
